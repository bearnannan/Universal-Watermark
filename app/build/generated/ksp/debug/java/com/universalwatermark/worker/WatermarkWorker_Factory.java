package com.universalwatermark.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.universalwatermark.data.local.datastore.SettingsDataStore;
import com.universalwatermark.data.local.datastore.UserProfileDataStore;
import com.universalwatermark.data.local.db.dao.WatermarkHistoryDao;
import com.universalwatermark.engine.crypto.CryptoManager;
import com.universalwatermark.engine.metadata.MetadataCollector;
import com.universalwatermark.engine.renderer.WatermarkRenderer;
import dagger.internal.DaggerGenerated;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
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
public final class WatermarkWorker_Factory {
  private final Provider<MetadataCollector> metadataCollectorProvider;

  private final Provider<WatermarkRenderer> watermarkRendererProvider;

  private final Provider<WatermarkHistoryDao> historyDaoProvider;

  private final Provider<SettingsDataStore> settingsDataStoreProvider;

  private final Provider<UserProfileDataStore> userProfileDataStoreProvider;

  private final Provider<CryptoManager> cryptoManagerProvider;

  public WatermarkWorker_Factory(Provider<MetadataCollector> metadataCollectorProvider,
      Provider<WatermarkRenderer> watermarkRendererProvider,
      Provider<WatermarkHistoryDao> historyDaoProvider,
      Provider<SettingsDataStore> settingsDataStoreProvider,
      Provider<UserProfileDataStore> userProfileDataStoreProvider,
      Provider<CryptoManager> cryptoManagerProvider) {
    this.metadataCollectorProvider = metadataCollectorProvider;
    this.watermarkRendererProvider = watermarkRendererProvider;
    this.historyDaoProvider = historyDaoProvider;
    this.settingsDataStoreProvider = settingsDataStoreProvider;
    this.userProfileDataStoreProvider = userProfileDataStoreProvider;
    this.cryptoManagerProvider = cryptoManagerProvider;
  }

  public WatermarkWorker get(Context context, WorkerParameters workerParams) {
    return newInstance(context, workerParams, metadataCollectorProvider.get(), watermarkRendererProvider.get(), historyDaoProvider.get(), settingsDataStoreProvider.get(), userProfileDataStoreProvider.get(), cryptoManagerProvider.get());
  }

  public static WatermarkWorker_Factory create(
      Provider<MetadataCollector> metadataCollectorProvider,
      Provider<WatermarkRenderer> watermarkRendererProvider,
      Provider<WatermarkHistoryDao> historyDaoProvider,
      Provider<SettingsDataStore> settingsDataStoreProvider,
      Provider<UserProfileDataStore> userProfileDataStoreProvider,
      Provider<CryptoManager> cryptoManagerProvider) {
    return new WatermarkWorker_Factory(metadataCollectorProvider, watermarkRendererProvider, historyDaoProvider, settingsDataStoreProvider, userProfileDataStoreProvider, cryptoManagerProvider);
  }

  public static WatermarkWorker newInstance(Context context, WorkerParameters workerParams,
      MetadataCollector metadataCollector, WatermarkRenderer watermarkRenderer,
      WatermarkHistoryDao historyDao, SettingsDataStore settingsDataStore,
      UserProfileDataStore userProfileDataStore, CryptoManager cryptoManager) {
    return new WatermarkWorker(context, workerParams, metadataCollector, watermarkRenderer, historyDao, settingsDataStore, userProfileDataStore, cryptoManager);
  }
}
