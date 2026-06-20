package com.universalwatermark.engine.template;

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
public final class TemplateEngine_Factory implements Factory<TemplateEngine> {
  @Override
  public TemplateEngine get() {
    return newInstance();
  }

  public static TemplateEngine_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static TemplateEngine newInstance() {
    return new TemplateEngine();
  }

  private static final class InstanceHolder {
    private static final TemplateEngine_Factory INSTANCE = new TemplateEngine_Factory();
  }
}
