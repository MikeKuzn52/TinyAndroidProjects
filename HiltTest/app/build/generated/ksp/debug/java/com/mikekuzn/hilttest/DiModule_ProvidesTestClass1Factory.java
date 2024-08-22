package com.mikekuzn.hilttest;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
    "KotlinInternalInJava"
})
public final class DiModule_ProvidesTestClass1Factory implements Factory<TestClass1> {
  private final DiModule module;

  public DiModule_ProvidesTestClass1Factory(DiModule module) {
    this.module = module;
  }

  @Override
  public TestClass1 get() {
    return providesTestClass1(module);
  }

  public static DiModule_ProvidesTestClass1Factory create(DiModule module) {
    return new DiModule_ProvidesTestClass1Factory(module);
  }

  public static TestClass1 providesTestClass1(DiModule instance) {
    return Preconditions.checkNotNullFromProvides(instance.providesTestClass1());
  }
}
