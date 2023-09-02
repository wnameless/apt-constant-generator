package com.github.wnameless.apt;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import org.atteo.evo.inflector.English;
import com.github.wnameless.apt.NamedResource.BooleanConstant;
import com.github.wnameless.apt.NamedResource.ByteConstant;
import com.github.wnameless.apt.NamedResource.CharConstant;
import com.github.wnameless.apt.NamedResource.ClassConstant;
import com.github.wnameless.apt.NamedResource.Constant;
import com.github.wnameless.apt.NamedResource.DoubleConstant;
import com.github.wnameless.apt.NamedResource.EnumConstant;
import com.github.wnameless.apt.NamedResource.FloatConstant;
import com.github.wnameless.apt.NamedResource.InferredConstant;
import com.github.wnameless.apt.NamedResource.IntConstant;
import com.github.wnameless.apt.NamedResource.LongConstant;
import com.github.wnameless.apt.NamedResource.NamingFormat;
import com.github.wnameless.apt.NamedResource.ShortConstant;
import com.google.auto.service.AutoService;
import com.google.common.base.CaseFormat;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

/**
 * 
 * {@link NamedResourceAutoProcessor} processes all classes annotated with {@link NamedResource}
 * annotation, and then generates classes which are full of Constants based on each processing class
 * name and annotated {@link NamedResource} attributes.
 * 
 * @author Wei-Ming Wu
 * 
 */
@SupportedAnnotationTypes("com.github.wnameless.apt.NamedResource")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
@AutoService(Processor.class)
public class NamedResourceAutoProcessor extends AbstractProcessor {

  private static final org.slf4j.Logger log =
      org.slf4j.LoggerFactory.getLogger(NamedResourceAutoProcessor.class);

  private static final TypeName CLASS_OF_ANY = ParameterizedTypeName.get(ClassName.get(Class.class),
      WildcardTypeName.subtypeOf(Object.class));

  private String singularName;
  private String pluralName;

  private String upperCamelSingular;
  private String lowerCamelSingular;
  private String upperUnderscoreSingular;
  private String lowerUnderscoreSingular;
  private String lowerHyphenSingular;

  private String upperCamelPlural;
  private String lowerCamelPlural;
  private String upperUnderscorePlural;
  private String lowerUnderscorePlural;
  private String lowerHyphenPlural;

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(NamedResource.class);
    for (Element element : elements) {
      String className = ((TypeElement) element).getQualifiedName().toString();
      log.info("NamedResource: constants generating for class: {}", className);;
      String classSimpleName = element.getSimpleName().toString();
      String packageName = "";
      int lastDot = className.lastIndexOf('.');
      if (lastDot > 0) {
        packageName = className.substring(0, lastDot);
      }

      NamedResource nr = element.getAnnotation(NamedResource.class);
      singularName = nr.singular().value().trim();
      if (singularName.isEmpty()) {
        singularName = classSimpleName;
      }
      pluralName = nr.plural().value().trim();
      if (pluralName.isEmpty()) {
        pluralName = English.plural(singularName);
      }
      upperCamelSingular = CaseFormat.valueOf(nr.singular().format().toString())
          .to(CaseFormat.UPPER_CAMEL, singularName);
      lowerCamelSingular = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, upperCamelSingular);
      upperUnderscoreSingular =
          CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, upperCamelSingular);
      lowerUnderscoreSingular =
          CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, upperCamelSingular);
      lowerHyphenSingular = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, upperCamelSingular);

      upperCamelPlural = CaseFormat.valueOf(nr.plural().format().toString())
          .to(CaseFormat.UPPER_CAMEL, pluralName);
      lowerCamelPlural = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, upperCamelPlural);
      upperUnderscorePlural =
          CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, upperCamelPlural);
      lowerUnderscorePlural =
          CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, upperCamelPlural);
      lowerHyphenPlural = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, upperCamelPlural);

      TypeSpec.Builder builder =
          TypeSpec.classBuilder(nr.classNamePrefix() + classSimpleName + nr.classNameSuffix())
              .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

      // Apply jakarta.inject or javax.inject
      switch (nr.injectable()) {
        case JAKARTA:
          builder.addAnnotation(jakarta.inject.Named.class);
          break;
        case JAVAX:
          builder.addAnnotation(javax.inject.Named.class);
          break;
        default:
          break;
      }

      // Apply marker interface
      if (nr.markerInterface()) {
        builder.addSuperinterface(INamedResource.class);
      }

      InferredConstant resource = nr.resource();
      InferredConstant resources = nr.resources();
      InferredConstant resourcePath = nr.resourcePath();
      // RESUORCE
      this.nameKeyBuilder(builder, resource);
      // RESUORCES
      this.nameKeyBuilder(builder, resources);
      // RESUORCE_PATH
      this.nameKeyBuilder(builder, resourcePath);

      Stream.of(new String[][] { //
          {"SINGULAR", singularName}, //
          {"PLURAL", pluralName}, //
          {"CLASS_SIMPLE_NAME", classSimpleName}, //
          {"CLASS_NAME", className}, //
          {"PACKAGE_NAME", packageName} //
      }).forEach(data -> {
        builder.addField(constant(data[0], data[1]));
      });

      Stream.of(new String[][] { //
          {"UPPER_CAMEL_SINGULAR", upperCamelSingular}, //
          {"LOWER_CAMEL_SINGULAR", lowerCamelSingular}, //
          {"UPPER_UNDERSCORE_SINGULAR", upperUnderscoreSingular}, //
          {"LOWER_UNDERSCORE_SINGULAR", lowerUnderscoreSingular}, //
          {"LOWER_HYPHEN_SINGULAR", lowerHyphenSingular}, //
          {"UPPER_CAMEL_PLURAL", upperCamelPlural}, //
          {"LOWER_CAMEL_PLURAL", lowerCamelPlural}, //
          {"UPPER_UNDERSCORE_PLURAL", upperUnderscorePlural}, //
          {"LOWER_UNDERSCORE_PLURAL", lowerUnderscorePlural}, //
          {"LOWER_HYPHEN_PLURAL", lowerHyphenPlural} //
      }).forEach(data -> {
        builder.addField(constant(data[0], data[1]));
      });

      for (NamingFormat namingFormat : nr.literalSingularConstants()) {
        switch (namingFormat) {
          case UPPER_CAMEL:
            builder.addField(constant(upperCamelSingular, upperCamelSingular));
            break;
          case LOWER_CAMEL:
            builder.addField(constant(lowerCamelSingular, lowerCamelSingular));
            break;
          case UPPER_UNDERSCORE:
            builder.addField(constant(upperUnderscoreSingular, upperUnderscoreSingular));
            break;
          case LOWER_UNDERSCORE:
            builder.addField(constant(lowerUnderscoreSingular, lowerUnderscoreSingular));
            break;
          case LOWER_HYPHEN:
            break;
        }
      }

      for (NamingFormat namingFormat : nr.literalPluralConstants()) {
        switch (namingFormat) {
          case UPPER_CAMEL:
            builder.addField(constant(upperCamelPlural, upperCamelPlural));
            break;
          case LOWER_CAMEL:
            builder.addField(constant(lowerCamelPlural, lowerCamelPlural));
            break;
          case UPPER_UNDERSCORE:
            builder.addField(constant(upperUnderscorePlural, upperUnderscorePlural));
            break;
          case LOWER_UNDERSCORE:
            builder.addField(constant(lowerUnderscorePlural, lowerUnderscorePlural));
            break;
          case LOWER_HYPHEN:
            break;
        }
      }

      for (InferredConstant nameKey : nr.inferredConstants()) {
        nameKeyBuilder(builder, nameKey);
      }

      for (Constant constant : nr.constants()) {
        builder.addField(constant(constant.name(), constant.value()));
      }

      for (BooleanConstant constant : nr.booleanConstants()) {
        builder.addField(primitiveConstant(constant.name(), constant.value(), boolean.class, "$L"));
      }
      for (ByteConstant constant : nr.byteConstants()) {
        builder.addField(primitiveConstant(constant.name(), constant.value(), byte.class, "$L"));
      }
      for (CharConstant constant : nr.charConstants()) {
        builder.addField(primitiveConstant(constant.name(), constant.value(), char.class, "'$L'"));
      }
      for (ShortConstant constant : nr.shortConstants()) {
        builder.addField(primitiveConstant(constant.name(), constant.value(), short.class, "$L"));
      }
      for (IntConstant constant : nr.intConstants()) {
        builder.addField(primitiveConstant(constant.name(), constant.value(), int.class, "$L"));
      }
      for (LongConstant constant : nr.longConstants()) {
        builder.addField(primitiveConstant(constant.name(), constant.value(), long.class, "$LL"));
      }
      for (FloatConstant constant : nr.floatConstants()) {
        builder.addField(primitiveConstant(constant.name(), constant.value(), float.class, "$Lf"));
      }
      for (DoubleConstant constant : nr.doubleConstants()) {
        builder.addField(primitiveConstant(constant.name(), constant.value(), double.class, "$L"));
      }

      for (ClassConstant constant : nr.classConstants()) {
        List<? extends TypeMirror> typeMirrors = null;
        try {
          constant.value();
        } catch (MirroredTypesException ex) {
          typeMirrors = ex.getTypeMirrors();
        }
        if (typeMirrors == null) continue;

        TypeName typeName = TypeName.get(typeMirrors.get(0));
        FieldSpec field = FieldSpec.builder(CLASS_OF_ANY, constant.name())
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
            .initializer("$L.class", typeName).build();
        builder.addField(field);
      }

      for (EnumConstant constant : nr.enumConstants()) {
        List<? extends TypeMirror> typeMirrors = null;
        try {
          constant.valueType();
        } catch (MirroredTypesException ex) {
          typeMirrors = ex.getTypeMirrors();
        }
        if (typeMirrors == null) continue;

        TypeName typeName = TypeName.get(typeMirrors.get(0));
        FieldSpec field = FieldSpec.builder(typeName, constant.name())
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
            .initializer(typeName + ".$L", constant.valueKey()).build();
        builder.addField(field);
      }

      // Writes to file
      TypeSpec namedResourceType = builder.build();
      JavaFile javaFile = JavaFile.builder(packageName, namedResourceType).build();
      javaFile.toJavaFileObject().delete();
      try {
        javaFile.writeTo(processingEnv.getFiler());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    return false; // Returning false implies that processors are allowed to keep processing
                  // annotations
  }

  private void nameKeyBuilder(TypeSpec.Builder builder, InferredConstant constant) {
    switch (constant.format()) {
      case UPPER_UNDERSCORE:
        builder.addField(constant(constant.name(),
            constant.prefix()
                + (constant.plural() ? upperUnderscorePlural : upperUnderscoreSingular)
                + constant.suffix()));
        break;
      case LOWER_UNDERSCORE:
        builder.addField(constant(constant.name(),
            constant.prefix()
                + (constant.plural() ? lowerUnderscorePlural : lowerUnderscoreSingular)
                + constant.suffix()));
        break;
      case UPPER_CAMEL:
        builder.addField(constant(constant.name(), constant.prefix()
            + (constant.plural() ? upperCamelPlural : upperCamelSingular) + constant.suffix()));
        break;
      case LOWER_CAMEL:
        builder.addField(constant(constant.name(), constant.prefix()
            + (constant.plural() ? lowerCamelPlural : lowerCamelSingular) + constant.suffix()));
        break;
      case LOWER_HYPHEN:
        builder.addField(constant(constant.name(), constant.prefix()
            + (constant.plural() ? lowerHyphenPlural : lowerHyphenSingular) + constant.suffix()));
        break;
    }
  }

  private FieldSpec constant(String name, String value) {
    return FieldSpec.builder(String.class, name)
        .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL).initializer("$S", value)
        .build();
  }

  private FieldSpec primitiveConstant(String name, Object value, Class<?> type, String template) {
    return FieldSpec.builder(type, name)
        .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL).initializer(template, value)
        .build();
  }

}
