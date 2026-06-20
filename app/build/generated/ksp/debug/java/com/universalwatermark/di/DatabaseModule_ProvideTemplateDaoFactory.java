package com.universalwatermark.di;

import com.universalwatermark.data.local.db.WatermarkDatabase;
import com.universalwatermark.data.local.db.dao.WatermarkTemplateDao;
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
public final class DatabaseModule_ProvideTemplateDaoFactory implements Factory<WatermarkTemplateDao> {
  private final Provider<WatermarkDatabase> databaseProvider;

  public DatabaseModule_ProvideTemplateDaoFactory(Provider<WatermarkDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public WatermarkTemplateDao get() {
    return provideTemplateDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideTemplateDaoFactory create(
      Provider<WatermarkDatabase> databaseProvider) {
    return new DatabaseModule_ProvideTemplateDaoFactory(databaseProvider);
  }

  public static WatermarkTemplateDao provideTemplateDao(WatermarkDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideTemplateDao(database));
  }
}
