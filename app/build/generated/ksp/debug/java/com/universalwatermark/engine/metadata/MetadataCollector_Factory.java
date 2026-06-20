package com.universalwatermark.engine.metadata;

import android.content.Context;
import com.universalwatermark.data.local.datastore.UserProfileDataStore;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class MetadataCollector_Factory implements Factory<MetadataCollector> {
  private final Provider<ExifDataExtractor> exifDataExtractorProvider;

  private final Provider<DeviceInfoCollector> deviceInfoCollectorProvider;

  private final Provider<UserProfileDataStore> userProfileDataStoreProvider;

  private final Provider<Context> contextProvider;

  public MetadataCollector_Factory(Provider<ExifDataExtractor> exifDataExtractorProvider,
      Provider<DeviceInfoCollector> deviceInfoCollectorProvider,
      Provider<UserProfileDataStore> userProfileDataStoreProvider,
      Provider<Context> contextProvider) {
    this.exifDataExtractorProvider = exifDataExtractorProvider;
    this.deviceInfoCollectorProvider = deviceInfoCollectorProvider;
    this.userProfileDataStoreProvider = userProfileDataStoreProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public MetadataCollector get() {
    return newInstance(exifDataExtractorProvider.get(), deviceInfoCollectorProvider.get(), userProfileDataStoreProvider.get(), contextProvider.get());
  }

  public static MetadataCollector_Factory create(
      Provider<ExifDataExtractor> exifDataExtractorProvider,
      Provider<DeviceInfoCollector> deviceInfoCollectorProvider,
      Provider<UserProfileDataStore> userProfileDataStoreProvider,
      Provider<Context> contextProvider) {
    return new MetadataCollector_Factory(exifDataExtractorProvider, deviceInfoCollectorProvider, userProfileDataStoreProvider, contextProvider);
  }

  public static MetadataCollector newInstance(ExifDataExtractor exifDataExtractor,
      DeviceInfoCollector deviceInfoCollector, UserProfileDataStore userProfileDataStore,
      Context context) {
    return new MetadataCollector(exifDataExtractor, deviceInfoCollector, userProfileDataStore, context);
  }
}
