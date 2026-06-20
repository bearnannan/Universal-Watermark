package com.universalwatermark.engine.renderer;

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
public final class BitmapProcessor_Factory implements Factory<BitmapProcessor> {
  @Override
  public BitmapProcessor get() {
    return newInstance();
  }

  public static BitmapProcessor_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static BitmapProcessor newInstance() {
    return new BitmapProcessor();
  }

  private static final class InstanceHolder {
    private static final BitmapProcessor_Factory INSTANCE = new BitmapProcessor_Factory();
  }
}
