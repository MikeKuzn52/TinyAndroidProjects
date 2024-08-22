package com.mikekuzn.hilttest;

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
    "KotlinInternalInJava"
})
public final class TestClass2_Factory implements Factory<TestClass2> {
  @Override
  public TestClass2 get() {
    return newInstance();
  }

  public static TestClass2_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static TestClass2 newInstance() {
    return new TestClass2();
  }

  private static final class InstanceHolder {
    private static final TestClass2_Factory INSTANCE = new TestClass2_Factory();
  }
}
