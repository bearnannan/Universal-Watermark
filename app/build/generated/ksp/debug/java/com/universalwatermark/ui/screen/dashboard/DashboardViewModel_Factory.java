package com.universalwatermark.ui.screen.dashboard;

import android.app.Application;
import com.universalwatermark.data.local.datastore.SettingsDataStore;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<Application> appProvider;

  private final Provider<SettingsDataStore> settingsDataStoreProvider;

  public DashboardViewModel_Factory(Provider<Application> appProvider,
      Provider<SettingsDataStore> settingsDataStoreProvider) {
    this.appProvider = appProvider;
    this.settingsDataStoreProvider = settingsDataStoreProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(appProvider.get(), settingsDataStoreProvider.get());
  }

  public static DashboardViewModel_Factory create(Provider<Application> appProvider,
      Provider<SettingsDataStore> settingsDataStoreProvider) {
    return new DashboardViewModel_Factory(appProvider, settingsDataStoreProvider);
  }

  public static DashboardViewModel newInstance(Application app,
      SettingsDataStore settingsDataStore) {
    return new DashboardViewModel(app, settingsDataStore);
  }
}
