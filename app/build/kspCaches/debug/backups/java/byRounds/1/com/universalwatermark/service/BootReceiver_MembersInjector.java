package com.universalwatermark.service;

import com.universalwatermark.data.local.datastore.SettingsDataStore;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class BootReceiver_MembersInjector implements MembersInjector<BootReceiver> {
  private final Provider<SettingsDataStore> settingsDataStoreProvider;

  public BootReceiver_MembersInjector(Provider<SettingsDataStore> settingsDataStoreProvider) {
    this.settingsDataStoreProvider = settingsDataStoreProvider;
  }

  public static MembersInjector<BootReceiver> create(
      Provider<SettingsDataStore> settingsDataStoreProvider) {
    return new BootReceiver_MembersInjector(settingsDataStoreProvider);
  }

  @Override
  public void injectMembers(BootReceiver instance) {
    injectSettingsDataStore(instance, settingsDataStoreProvider.get());
  }

  @InjectedFieldSignature("com.universalwatermark.service.BootReceiver.settingsDataStore")
  public static void injectSettingsDataStore(BootReceiver instance,
      SettingsDataStore settingsDataStore) {
    instance.settingsDataStore = settingsDataStore;
  }
}
