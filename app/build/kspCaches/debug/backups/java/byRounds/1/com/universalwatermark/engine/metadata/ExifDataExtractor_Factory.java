package com.universalwatermark.engine.metadata;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class ExifDataExtractor_Factory implements Factory<ExifDataExtractor> {
  private final Provider<Context> contextProvider;

  public ExifDataExtractor_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public ExifDataExtractor get() {
    return newInstance(contextProvider.get());
  }

  public static ExifDataExtractor_Factory create(Provider<Context> contextProvider) {
    return new ExifDataExtractor_Factory(contextProvider);
  }

  public static ExifDataExtractor newInstance(Context context) {
    return new ExifDataExtractor(context);
  }
}
