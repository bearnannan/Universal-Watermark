package com.universalwatermark;

import androidx.hilt.work.HiltWorkerFactory;
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
public final class WatermarkApplication_MembersInjector implements MembersInjector<WatermarkApplication> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  public WatermarkApplication_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
  }

  public static MembersInjector<WatermarkApplication> create(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new WatermarkApplication_MembersInjector(workerFactoryProvider);
  }

  @Override
  public void injectMembers(WatermarkApplication instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
  }

  @InjectedFieldSignature("com.universalwatermark.WatermarkApplication.workerFactory")
  public static void injectWorkerFactory(WatermarkApplication instance,
      HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }
}
