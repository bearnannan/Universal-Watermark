package com.universalwatermark.data.local.datastore;

import android.content.Context;
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
public final class UserProfileDataStore_Factory implements Factory<UserProfileDataStore> {
  private final Provider<Context> contextProvider;

  public UserProfileDataStore_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public UserProfileDataStore get() {
    return newInstance(contextProvider.get());
  }

  public static UserProfileDataStore_Factory create(Provider<Context> contextProvider) {
    return new UserProfileDataStore_Factory(contextProvider);
  }

  public static UserProfileDataStore newInstance(Context context) {
    return new UserProfileDataStore(context);
  }
}
