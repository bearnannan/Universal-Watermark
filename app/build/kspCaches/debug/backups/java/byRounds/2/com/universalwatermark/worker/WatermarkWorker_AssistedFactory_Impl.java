package com.universalwatermark.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import dagger.internal.DaggerGenerated;
import dagger.internal.InstanceFactory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class WatermarkWorker_AssistedFactory_Impl implements WatermarkWorker_AssistedFactory {
  private final WatermarkWorker_Factory delegateFactory;

  WatermarkWorker_AssistedFactory_Impl(WatermarkWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public WatermarkWorker create(Context p0, WorkerParameters p1) {
    return delegateFactory.get(p0, p1);
  }

  public static Provider<WatermarkWorker_AssistedFactory> create(
      WatermarkWorker_Factory delegateFactory) {
    return InstanceFactory.create(new WatermarkWorker_AssistedFactory_Impl(delegateFactory));
  }

  public static dagger.internal.Provider<WatermarkWorker_AssistedFactory> createFactoryProvider(
      WatermarkWorker_Factory delegateFactory) {
    return InstanceFactory.create(new WatermarkWorker_AssistedFactory_Impl(delegateFactory));
  }
}
