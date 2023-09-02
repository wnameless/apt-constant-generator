package com.github.wnameless.apt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * {@link NamedResource} is an APT annotation. It allows annotation processor to produce a generated
 * class, which contains Java Constants. All the Java Constant values are derived by annotated class
 * name.
 * 
 * @author Wei-Ming Wu
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface NamedResource {

  InjectType injectable() default InjectType.NONE;

  public enum InjectType {
    NONE, JAKARTA, JAVAX;
  }

  boolean markerInterface()

  default true;

  String classNamePrefix()

  default "NR";

  String classNameSuffix()

  default "";

  Naming singular() default @Naming(value = "", format = NamingFormat.UPPER_CAMEL);

  Naming plural() default @Naming(value = "", format = NamingFormat.UPPER_CAMEL);

  InferredConstant resource() default @InferredConstant(name = "RESOURCE", plural = false,
      format = NamingFormat.LOWER_HYPHEN);

  InferredConstant resources() default @InferredConstant(name = "RESOURCES", plural = true,
      format = NamingFormat.LOWER_HYPHEN);

  InferredConstant resourcePath() default @InferredConstant(name = "RESOURCE_PATH", plural = true,
      format = NamingFormat.LOWER_HYPHEN, prefix = "/");

  NamingFormat[] literalSingularConstants() default {};

  NamingFormat[] literalPluralConstants() default {};

  InferredConstant[] inferredConstants() default {};

  Constant[] constants() default {};

  BooleanConstant[] booleanConstants() default {};

  ByteConstant[] byteConstants() default {};

  CharConstant[] charConstants() default {};

  ShortConstant[] shortConstants() default {};

  IntConstant[] intConstants() default {};

  LongConstant[] longConstants() default {};

  FloatConstant[] floatConstants() default {};

  DoubleConstant[] doubleConstants() default {};

  ClassConstant[] classConstants() default {};

  EnumConstant[] enumConstants() default {};

  public enum NamingFormat {

    UPPER_CAMEL, LOWER_CAMEL, UPPER_UNDERSCORE, LOWER_UNDERSCORE, LOWER_HYPHEN;

  }

  @Target(ElementType.ANNOTATION_TYPE)
  @Retention(RetentionPolicy.SOURCE)
  public @interface Naming {

    String value();

    NamingFormat format();

  }

  @Target(ElementType.ANNOTATION_TYPE)
  @Retention(RetentionPolicy.SOURCE)
  public @interface InferredConstant {

    String name();

    boolean plural();

    NamingFormat format();

    String prefix()

    default "";

    String suffix() default "";

  }

  @Target(ElementType.ANNOTATION_TYPE)
  @Retention(RetentionPolicy.SOURCE)
  public @interface Constant {

    String name();

    String value();

  }

  @Target(ElementType.ANNOTATION_TYPE)
  @Retention(RetentionPolicy.SOURCE)
  public @interface BooleanConstant {

    String name();

    boolean value();

  }

  @Target(ElementType.ANNOTATION_TYPE)
  @Retention(RetentionPolicy.SOURCE)
  public @interface ByteConstant {

    String name();

    byte value();

  }

  @Target(ElementType.ANNOTATION_TYPE)
  @Retention(RetentionPolicy.SOURCE)
  public @interface CharConstant {

    String name();

    char value();

  }

  @Target(ElementType.ANNOTATION_TYPE)
  @Retention(RetentionPolicy.SOURCE)
  public @interface ShortConstant {

    String name();

    short value();

  }

  @Target(ElementType.ANNOTATION_TYPE)
  @Retention(RetentionPolicy.SOURCE)
  public @interface IntConstant {

    String name();

    int value();

  }

  @Target(ElementType.ANNOTATION_TYPE)
  @Retention(RetentionPolicy.SOURCE)
  public @interface LongConstant {

    String name();

    long value();

  }

  @Target(ElementType.ANNOTATION_TYPE)
  @Retention(RetentionPolicy.SOURCE)
  public @interface FloatConstant {

    String name();

    float value();

  }

  @Target(ElementType.ANNOTATION_TYPE)
  @Retention(RetentionPolicy.SOURCE)
  public @interface DoubleConstant {

    String name();

    double value();

  }

  @Target(ElementType.ANNOTATION_TYPE)
  @Retention(RetentionPolicy.SOURCE)
  public @interface ClassConstant {

    String name();

    Class<?> value();

  }

  @Target(ElementType.ANNOTATION_TYPE)
  @Retention(RetentionPolicy.SOURCE)
  public @interface EnumConstant {

    String name();

    Class<? extends Enum<?>> valueType();

    String valueKey();

  }

}
