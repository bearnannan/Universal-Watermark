package com.universalwatermark.engine.renderer;

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
public final class WatermarkRenderer_Factory implements Factory<WatermarkRenderer> {
  private final Provider<Context> contextProvider;

  private final Provider<TextRenderer> textRendererProvider;

  private final Provider<BitmapProcessor> bitmapProcessorProvider;

  public WatermarkRenderer_Factory(Provider<Context> contextProvider,
      Provider<TextRenderer> textRendererProvider,
      Provider<BitmapProcessor> bitmapProcessorProvider) {
    this.contextProvider = contextProvider;
    this.textRendererProvider = textRendererProvider;
    this.bitmapProcessorProvider = bitmapProcessorProvider;
  }

  @Override
  public WatermarkRenderer get() {
    return newInstance(contextProvider.get(), textRendererProvider.get(), bitmapProcessorProvider.get());
  }

  public static WatermarkRenderer_Factory create(Provider<Context> contextProvider,
      Provider<TextRenderer> textRendererProvider,
      Provider<BitmapProcessor> bitmapProcessorProvider) {
    return new WatermarkRenderer_Factory(contextProvider, textRendererProvider, bitmapProcessorProvider);
  }

  public static WatermarkRenderer newInstance(Context context, TextRenderer textRenderer,
      BitmapProcessor bitmapProcessor) {
    return new WatermarkRenderer(context, textRenderer, bitmapProcessor);
  }
}
