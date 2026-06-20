package com.universalwatermark.di;

import com.universalwatermark.data.local.db.WatermarkDatabase;
import com.universalwatermark.data.local.db.dao.WatermarkHistoryDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideHistoryDaoFactory implements Factory<WatermarkHistoryDao> {
  private final Provider<WatermarkDatabase> databaseProvider;

  public DatabaseModule_ProvideHistoryDaoFactory(Provider<WatermarkDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public WatermarkHistoryDao get() {
    return provideHistoryDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideHistoryDaoFactory create(
      Provider<WatermarkDatabase> databaseProvider) {
    return new DatabaseModule_ProvideHistoryDaoFactory(databaseProvider);
  }

  public static WatermarkHistoryDao provideHistoryDao(WatermarkDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideHistoryDao(database));
  }
}
