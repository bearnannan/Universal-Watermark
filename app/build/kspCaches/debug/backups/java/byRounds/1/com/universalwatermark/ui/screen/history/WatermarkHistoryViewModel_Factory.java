package com.universalwatermark.ui.screen.history;

import com.universalwatermark.data.local.db.dao.WatermarkHistoryDao;
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
public final class WatermarkHistoryViewModel_Factory implements Factory<WatermarkHistoryViewModel> {
  private final Provider<WatermarkHistoryDao> historyDaoProvider;

  public WatermarkHistoryViewModel_Factory(Provider<WatermarkHistoryDao> historyDaoProvider) {
    this.historyDaoProvider = historyDaoProvider;
  }

  @Override
  public WatermarkHistoryViewModel get() {
    return newInstance(historyDaoProvider.get());
  }

  public static WatermarkHistoryViewModel_Factory create(
      Provider<WatermarkHistoryDao> historyDaoProvider) {
    return new WatermarkHistoryViewModel_Factory(historyDaoProvider);
  }

  public static WatermarkHistoryViewModel newInstance(WatermarkHistoryDao historyDao) {
    return new WatermarkHistoryViewModel(historyDao);
  }
}
