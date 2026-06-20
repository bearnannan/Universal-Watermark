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
public final class TextRenderer_Factory implements Factory<TextRenderer> {
  @Override
  public TextRenderer get() {
    return newInstance();
  }

  public static TextRenderer_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static TextRenderer newInstance() {
    return new TextRenderer();
  }

  private static final class InstanceHolder {
    private static final TextRenderer_Factory INSTANCE = new TextRenderer_Factory();
  }
}
