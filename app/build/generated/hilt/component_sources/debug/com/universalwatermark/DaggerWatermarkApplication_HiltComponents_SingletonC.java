package com.universalwatermark;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.hilt.work.HiltWorkerFactory;
import androidx.hilt.work.WorkerAssistedFactory;
import androidx.hilt.work.WorkerFactoryModule_ProvideFactoryFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;
import com.universalwatermark.data.local.datastore.SettingsDataStore;
import com.universalwatermark.data.local.datastore.UserProfileDataStore;
import com.universalwatermark.data.local.db.WatermarkDatabase;
import com.universalwatermark.data.local.db.dao.WatermarkHistoryDao;
import com.universalwatermark.data.local.db.dao.WatermarkTemplateDao;
import com.universalwatermark.di.CryptoModule_ProvideCryptoManagerFactory;
import com.universalwatermark.di.DatabaseModule_ProvideHistoryDaoFactory;
import com.universalwatermark.di.DatabaseModule_ProvideTemplateDaoFactory;
import com.universalwatermark.di.DatabaseModule_ProvideWatermarkDatabaseFactory;
import com.universalwatermark.engine.crypto.CryptoManager;
import com.universalwatermark.engine.metadata.DeviceInfoCollector;
import com.universalwatermark.engine.metadata.ExifDataExtractor;
import com.universalwatermark.engine.metadata.MetadataCollector;
import com.universalwatermark.engine.renderer.BitmapProcessor;
import com.universalwatermark.engine.renderer.TextRenderer;
import com.universalwatermark.engine.renderer.WatermarkRenderer;
import com.universalwatermark.service.BootReceiver;
import com.universalwatermark.service.BootReceiver_MembersInjector;
import com.universalwatermark.ui.screen.dashboard.DashboardViewModel;
import com.universalwatermark.ui.screen.dashboard.DashboardViewModel_HiltModules;
import com.universalwatermark.ui.screen.history.WatermarkHistoryViewModel;
import com.universalwatermark.ui.screen.history.WatermarkHistoryViewModel_HiltModules;
import com.universalwatermark.ui.screen.settings.SettingsViewModel;
import com.universalwatermark.ui.screen.settings.SettingsViewModel_HiltModules;
import com.universalwatermark.ui.screen.template.TemplateEditorViewModel;
import com.universalwatermark.ui.screen.template.TemplateEditorViewModel_HiltModules;
import com.universalwatermark.worker.WatermarkWorker;
import com.universalwatermark.worker.WatermarkWorker_AssistedFactory;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideApplicationFactory;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.IdentifierNameString;
import dagger.internal.KeepFieldType;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.SingleCheck;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class DaggerWatermarkApplication_HiltComponents_SingletonC {
  private DaggerWatermarkApplication_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public WatermarkApplication_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements WatermarkApplication_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public WatermarkApplication_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements WatermarkApplication_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public WatermarkApplication_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements WatermarkApplication_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public WatermarkApplication_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements WatermarkApplication_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public WatermarkApplication_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements WatermarkApplication_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public WatermarkApplication_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements WatermarkApplication_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public WatermarkApplication_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements WatermarkApplication_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public WatermarkApplication_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends WatermarkApplication_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends WatermarkApplication_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends WatermarkApplication_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends WatermarkApplication_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(MapBuilder.<String, Boolean>newMapBuilder(4).put(LazyClassKeyProvider.com_universalwatermark_ui_screen_dashboard_DashboardViewModel, DashboardViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_universalwatermark_ui_screen_settings_SettingsViewModel, SettingsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_universalwatermark_ui_screen_template_TemplateEditorViewModel, TemplateEditorViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_universalwatermark_ui_screen_history_WatermarkHistoryViewModel, WatermarkHistoryViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_universalwatermark_ui_screen_settings_SettingsViewModel = "com.universalwatermark.ui.screen.settings.SettingsViewModel";

      static String com_universalwatermark_ui_screen_history_WatermarkHistoryViewModel = "com.universalwatermark.ui.screen.history.WatermarkHistoryViewModel";

      static String com_universalwatermark_ui_screen_dashboard_DashboardViewModel = "com.universalwatermark.ui.screen.dashboard.DashboardViewModel";

      static String com_universalwatermark_ui_screen_template_TemplateEditorViewModel = "com.universalwatermark.ui.screen.template.TemplateEditorViewModel";

      @KeepFieldType
      SettingsViewModel com_universalwatermark_ui_screen_settings_SettingsViewModel2;

      @KeepFieldType
      WatermarkHistoryViewModel com_universalwatermark_ui_screen_history_WatermarkHistoryViewModel2;

      @KeepFieldType
      DashboardViewModel com_universalwatermark_ui_screen_dashboard_DashboardViewModel2;

      @KeepFieldType
      TemplateEditorViewModel com_universalwatermark_ui_screen_template_TemplateEditorViewModel2;
    }
  }

  private static final class ViewModelCImpl extends WatermarkApplication_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<DashboardViewModel> dashboardViewModelProvider;

    private Provider<SettingsViewModel> settingsViewModelProvider;

    private Provider<TemplateEditorViewModel> templateEditorViewModelProvider;

    private Provider<WatermarkHistoryViewModel> watermarkHistoryViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.dashboardViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.settingsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.templateEditorViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.watermarkHistoryViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(4).put(LazyClassKeyProvider.com_universalwatermark_ui_screen_dashboard_DashboardViewModel, ((Provider) dashboardViewModelProvider)).put(LazyClassKeyProvider.com_universalwatermark_ui_screen_settings_SettingsViewModel, ((Provider) settingsViewModelProvider)).put(LazyClassKeyProvider.com_universalwatermark_ui_screen_template_TemplateEditorViewModel, ((Provider) templateEditorViewModelProvider)).put(LazyClassKeyProvider.com_universalwatermark_ui_screen_history_WatermarkHistoryViewModel, ((Provider) watermarkHistoryViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return Collections.<Class<?>, Object>emptyMap();
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_universalwatermark_ui_screen_template_TemplateEditorViewModel = "com.universalwatermark.ui.screen.template.TemplateEditorViewModel";

      static String com_universalwatermark_ui_screen_dashboard_DashboardViewModel = "com.universalwatermark.ui.screen.dashboard.DashboardViewModel";

      static String com_universalwatermark_ui_screen_history_WatermarkHistoryViewModel = "com.universalwatermark.ui.screen.history.WatermarkHistoryViewModel";

      static String com_universalwatermark_ui_screen_settings_SettingsViewModel = "com.universalwatermark.ui.screen.settings.SettingsViewModel";

      @KeepFieldType
      TemplateEditorViewModel com_universalwatermark_ui_screen_template_TemplateEditorViewModel2;

      @KeepFieldType
      DashboardViewModel com_universalwatermark_ui_screen_dashboard_DashboardViewModel2;

      @KeepFieldType
      WatermarkHistoryViewModel com_universalwatermark_ui_screen_history_WatermarkHistoryViewModel2;

      @KeepFieldType
      SettingsViewModel com_universalwatermark_ui_screen_settings_SettingsViewModel2;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.universalwatermark.ui.screen.dashboard.DashboardViewModel 
          return (T) new DashboardViewModel(ApplicationContextModule_ProvideApplicationFactory.provideApplication(singletonCImpl.applicationContextModule), singletonCImpl.settingsDataStoreProvider.get());

          case 1: // com.universalwatermark.ui.screen.settings.SettingsViewModel 
          return (T) new SettingsViewModel(ApplicationContextModule_ProvideApplicationFactory.provideApplication(singletonCImpl.applicationContextModule));

          case 2: // com.universalwatermark.ui.screen.template.TemplateEditorViewModel 
          return (T) new TemplateEditorViewModel(singletonCImpl.watermarkTemplateDao());

          case 3: // com.universalwatermark.ui.screen.history.WatermarkHistoryViewModel 
          return (T) new WatermarkHistoryViewModel(singletonCImpl.watermarkHistoryDao());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends WatermarkApplication_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends WatermarkApplication_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends WatermarkApplication_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<ExifDataExtractor> exifDataExtractorProvider;

    private Provider<DeviceInfoCollector> deviceInfoCollectorProvider;

    private Provider<UserProfileDataStore> userProfileDataStoreProvider;

    private Provider<MetadataCollector> metadataCollectorProvider;

    private Provider<TextRenderer> textRendererProvider;

    private Provider<BitmapProcessor> bitmapProcessorProvider;

    private Provider<WatermarkRenderer> watermarkRendererProvider;

    private Provider<WatermarkDatabase> provideWatermarkDatabaseProvider;

    private Provider<SettingsDataStore> settingsDataStoreProvider;

    private Provider<CryptoManager> provideCryptoManagerProvider;

    private Provider<WatermarkWorker_AssistedFactory> watermarkWorker_AssistedFactoryProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private WatermarkHistoryDao watermarkHistoryDao() {
      return DatabaseModule_ProvideHistoryDaoFactory.provideHistoryDao(provideWatermarkDatabaseProvider.get());
    }

    private Map<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>> mapOfStringAndProviderOfWorkerAssistedFactoryOf(
        ) {
      return Collections.<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>>singletonMap("com.universalwatermark.worker.WatermarkWorker", ((Provider) watermarkWorker_AssistedFactoryProvider));
    }

    private HiltWorkerFactory hiltWorkerFactory() {
      return WorkerFactoryModule_ProvideFactoryFactory.provideFactory(mapOfStringAndProviderOfWorkerAssistedFactoryOf());
    }

    private WatermarkTemplateDao watermarkTemplateDao() {
      return DatabaseModule_ProvideTemplateDaoFactory.provideTemplateDao(provideWatermarkDatabaseProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.exifDataExtractorProvider = DoubleCheck.provider(new SwitchingProvider<ExifDataExtractor>(singletonCImpl, 2));
      this.deviceInfoCollectorProvider = DoubleCheck.provider(new SwitchingProvider<DeviceInfoCollector>(singletonCImpl, 3));
      this.userProfileDataStoreProvider = DoubleCheck.provider(new SwitchingProvider<UserProfileDataStore>(singletonCImpl, 4));
      this.metadataCollectorProvider = DoubleCheck.provider(new SwitchingProvider<MetadataCollector>(singletonCImpl, 1));
      this.textRendererProvider = DoubleCheck.provider(new SwitchingProvider<TextRenderer>(singletonCImpl, 6));
      this.bitmapProcessorProvider = DoubleCheck.provider(new SwitchingProvider<BitmapProcessor>(singletonCImpl, 7));
      this.watermarkRendererProvider = DoubleCheck.provider(new SwitchingProvider<WatermarkRenderer>(singletonCImpl, 5));
      this.provideWatermarkDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<WatermarkDatabase>(singletonCImpl, 8));
      this.settingsDataStoreProvider = DoubleCheck.provider(new SwitchingProvider<SettingsDataStore>(singletonCImpl, 9));
      this.provideCryptoManagerProvider = DoubleCheck.provider(new SwitchingProvider<CryptoManager>(singletonCImpl, 10));
      this.watermarkWorker_AssistedFactoryProvider = SingleCheck.provider(new SwitchingProvider<WatermarkWorker_AssistedFactory>(singletonCImpl, 0));
    }

    @Override
    public void injectWatermarkApplication(WatermarkApplication watermarkApplication) {
      injectWatermarkApplication2(watermarkApplication);
    }

    @Override
    public void injectBootReceiver(BootReceiver bootReceiver) {
      injectBootReceiver2(bootReceiver);
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private WatermarkApplication injectWatermarkApplication2(WatermarkApplication instance) {
      WatermarkApplication_MembersInjector.injectWorkerFactory(instance, hiltWorkerFactory());
      return instance;
    }

    private BootReceiver injectBootReceiver2(BootReceiver instance) {
      BootReceiver_MembersInjector.injectSettingsDataStore(instance, settingsDataStoreProvider.get());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.universalwatermark.worker.WatermarkWorker_AssistedFactory 
          return (T) new WatermarkWorker_AssistedFactory() {
            @Override
            public WatermarkWorker create(Context context, WorkerParameters workerParams) {
              return new WatermarkWorker(context, workerParams, singletonCImpl.metadataCollectorProvider.get(), singletonCImpl.watermarkRendererProvider.get(), singletonCImpl.watermarkHistoryDao(), singletonCImpl.settingsDataStoreProvider.get(), singletonCImpl.userProfileDataStoreProvider.get(), singletonCImpl.provideCryptoManagerProvider.get());
            }
          };

          case 1: // com.universalwatermark.engine.metadata.MetadataCollector 
          return (T) new MetadataCollector(singletonCImpl.exifDataExtractorProvider.get(), singletonCImpl.deviceInfoCollectorProvider.get(), singletonCImpl.userProfileDataStoreProvider.get(), ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 2: // com.universalwatermark.engine.metadata.ExifDataExtractor 
          return (T) new ExifDataExtractor(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 3: // com.universalwatermark.engine.metadata.DeviceInfoCollector 
          return (T) new DeviceInfoCollector();

          case 4: // com.universalwatermark.data.local.datastore.UserProfileDataStore 
          return (T) new UserProfileDataStore(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 5: // com.universalwatermark.engine.renderer.WatermarkRenderer 
          return (T) new WatermarkRenderer(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.textRendererProvider.get(), singletonCImpl.bitmapProcessorProvider.get());

          case 6: // com.universalwatermark.engine.renderer.TextRenderer 
          return (T) new TextRenderer();

          case 7: // com.universalwatermark.engine.renderer.BitmapProcessor 
          return (T) new BitmapProcessor();

          case 8: // com.universalwatermark.data.local.db.WatermarkDatabase 
          return (T) DatabaseModule_ProvideWatermarkDatabaseFactory.provideWatermarkDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 9: // com.universalwatermark.data.local.datastore.SettingsDataStore 
          return (T) new SettingsDataStore(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 10: // com.universalwatermark.engine.crypto.CryptoManager 
          return (T) CryptoModule_ProvideCryptoManagerFactory.provideCryptoManager();

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
