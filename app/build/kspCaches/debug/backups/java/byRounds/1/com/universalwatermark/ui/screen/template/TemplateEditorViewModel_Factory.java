package com.universalwatermark.ui.screen.template;

import com.universalwatermark.data.local.db.dao.WatermarkTemplateDao;
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
public final class TemplateEditorViewModel_Factory implements Factory<TemplateEditorViewModel> {
  private final Provider<WatermarkTemplateDao> templateDaoProvider;

  public TemplateEditorViewModel_Factory(Provider<WatermarkTemplateDao> templateDaoProvider) {
    this.templateDaoProvider = templateDaoProvider;
  }

  @Override
  public TemplateEditorViewModel get() {
    return newInstance(templateDaoProvider.get());
  }

  public static TemplateEditorViewModel_Factory create(
      Provider<WatermarkTemplateDao> templateDaoProvider) {
    return new TemplateEditorViewModel_Factory(templateDaoProvider);
  }

  public static TemplateEditorViewModel newInstance(WatermarkTemplateDao templateDao) {
    return new TemplateEditorViewModel(templateDao);
  }
}
