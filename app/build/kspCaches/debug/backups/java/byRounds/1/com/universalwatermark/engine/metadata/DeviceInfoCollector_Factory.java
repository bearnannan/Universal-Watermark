package com.universalwatermark.engine.metadata;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class DeviceInfoCollector_Factory implements Factory<DeviceInfoCollector> {
  @Override
  public DeviceInfoCollector get() {
    return newInstance();
  }

  public static DeviceInfoCollector_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static DeviceInfoCollector newInstance() {
    return new DeviceInfoCollector();
  }

  private static final class InstanceHolder {
    private static final DeviceInfoCollector_Factory INSTANCE = new DeviceInfoCollector_Factory();
  }
}
