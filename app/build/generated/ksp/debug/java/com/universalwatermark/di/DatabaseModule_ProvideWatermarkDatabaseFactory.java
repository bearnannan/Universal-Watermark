package com.universalwatermark.di;

import android.content.Context;
import com.universalwatermark.data.local.db.WatermarkDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideWatermarkDatabaseFactory implements Factory<WatermarkDatabase> {
  private final Provider<Context> contextProvider;

  public DatabaseModule_ProvideWatermarkDatabaseFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public WatermarkDatabase get() {
    return provideWatermarkDatabase(contextProvider.get());
  }

  public static DatabaseModule_ProvideWatermarkDatabaseFactory create(
      Provider<Context> contextProvider) {
    return new DatabaseModule_ProvideWatermarkDatabaseFactory(contextProvider);
  }

  public static WatermarkDatabase provideWatermarkDatabase(Context context) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideWatermarkDatabase(context));
  }
}
