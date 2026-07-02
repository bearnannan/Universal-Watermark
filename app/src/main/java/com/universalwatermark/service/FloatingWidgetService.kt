package com.universalwatermark.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import android.content.pm.ServiceInfo
import androidx.compose.ui.platform.ComposeView
import androidx.core.app.NotificationCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.universalwatermark.ui.components.FloatingWidgetView
import android.app.Service
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.universalwatermark.data.local.datastore.SettingsDataStore
import com.universalwatermark.data.SettingsRepository
import android.util.DisplayMetrics
import android.animation.ValueAnimator
import android.view.animation.DecelerateInterpolator

class FloatingWidgetService : Service(), LifecycleOwner, SavedStateRegistryOwner, ViewModelStoreOwner {

    private val lifecycleRegistry = LifecycleRegistry(this)
    override val lifecycle: Lifecycle get() = lifecycleRegistry

    private lateinit var windowManager: WindowManager
    private var composeView: ComposeView? = null
    
    private val savedStateRegistryController = SavedStateRegistryController.create(this)
    private val store = ViewModelStore()

    override val savedStateRegistry: SavedStateRegistry
        get() = savedStateRegistryController.savedStateRegistry

    override val viewModelStore: ViewModelStore
        get() = store

    override fun onCreate() {
        super.onCreate()
        savedStateRegistryController.performRestore(null)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        createNotificationChannel()
        if (Build.VERSION.SDK_INT >= 34) { // Build.VERSION_CODES.UPSIDE_DOWN_CAKE
            startForeground(NOTIFICATION_ID, createNotification(), ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE)
        } else {
            startForeground(NOTIFICATION_ID, createNotification())
        }

        setupComposeView()
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        return START_STICKY
    }
    
    override fun onBind(intent: Intent?): IBinder? = null

    private lateinit var windowLayoutParams: WindowManager.LayoutParams
    private var screenWidth = 0
    private var screenHeight = 0
    private var preExpandX = 0
    private var preExpandY = 0

    private fun setupComposeView() {
        val displayMetrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenWidth = displayMetrics.widthPixels
        screenHeight = displayMetrics.heightPixels

        composeView = ComposeView(this).apply {
            setViewTreeLifecycleOwner(this@FloatingWidgetService)
            setViewTreeSavedStateRegistryOwner(this@FloatingWidgetService)
            setViewTreeViewModelStoreOwner(this@FloatingWidgetService)
            
            setContent {
                FloatingWidgetView(
                    onClose = {
                        CoroutineScope(Dispatchers.IO).launch {
                            val settingsDataStore = SettingsDataStore(this@FloatingWidgetService.applicationContext)
                            settingsDataStore.setServiceEnabled(false)
                            val settingsRepository = SettingsRepository(this@FloatingWidgetService.applicationContext)
                            settingsRepository.updateFloatingWidgetEnabled(false)
                        }
                        ServiceController.stopService(this@FloatingWidgetService)
                        stopSelf()
                    },
                    onFocusRequest = { focused ->
                        updateFocus(focused)
                    },
                    onDrag = { dx, dy ->
                        windowLayoutParams.x += dx.toInt()
                        windowLayoutParams.y += dy.toInt()
                        windowManager.updateViewLayout(composeView, windowLayoutParams)
                    },
                    onDragEnd = {
                        val targetX = if (windowLayoutParams.x < screenWidth / 2) 0 else screenWidth
                        val animator = ValueAnimator.ofInt(windowLayoutParams.x, targetX)
                        animator.duration = 300
                        animator.interpolator = DecelerateInterpolator()
                        animator.addUpdateListener { animation ->
                            windowLayoutParams.x = animation.animatedValue as Int
                            windowManager.updateViewLayout(composeView, windowLayoutParams)
                        }
                        animator.start()
                    }
                )
            }
        }

        val layoutFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }

        windowLayoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layoutFlag,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 0
            y = 100
        }

        windowManager.addView(composeView, windowLayoutParams)
    }

    private fun updateFocus(focused: Boolean) {
        if (focused) {
            preExpandX = windowLayoutParams.x
            preExpandY = windowLayoutParams.y
            windowLayoutParams.flags = windowLayoutParams.flags and WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE.inv()
            val targetX = (screenWidth - dpToPx(300)) / 2
            val targetY = (screenHeight - dpToPx(450)) / 2
            animateToPosition(targetX, targetY.coerceAtLeast(0))
        } else {
            windowLayoutParams.flags = windowLayoutParams.flags or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            animateToPosition(preExpandX, preExpandY)
        }
        windowManager.updateViewLayout(composeView, windowLayoutParams)
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }

    private fun animateToPosition(targetX: Int, targetY: Int) {
        val animX = ValueAnimator.ofInt(windowLayoutParams.x, targetX)
        val animY = ValueAnimator.ofInt(windowLayoutParams.y, targetY)
        
        val updateListener = ValueAnimator.AnimatorUpdateListener { 
            windowLayoutParams.x = animX.animatedValue as Int
            windowLayoutParams.y = animY.animatedValue as Int
            windowManager.updateViewLayout(composeView, windowLayoutParams)
        }
        
        animX.addUpdateListener(updateListener)
        
        animX.duration = 250
        animY.duration = 250
        animX.interpolator = DecelerateInterpolator()
        animY.interpolator = DecelerateInterpolator()
        
        animX.start()
        animY.start()
    }

    override fun onDestroy() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        
        super.onDestroy()
        store.clear()
        composeView?.let {
            windowManager.removeView(it)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Floating Widget Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }

    private fun createNotification() = NotificationCompat.Builder(this, CHANNEL_ID)
        .setContentTitle("Universal Watermark")
        .setContentText("วิดเจ็ตลอยกำลังทำงาน")
        .setTicker("Universal Watermark")
        .setSmallIcon(android.R.drawable.ic_menu_camera)
        .setPriority(NotificationCompat.PRIORITY_LOW)
        .setOngoing(true)
        .setStyle(NotificationCompat.BigTextStyle().bigText("วิดเจ็ตลอยกำลังทำงาน"))
        .build()

    companion object {
        private const val NOTIFICATION_ID = 2
        private const val CHANNEL_ID = "floating_widget_channel"
    }
}
