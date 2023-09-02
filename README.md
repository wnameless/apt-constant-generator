[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.wnameless.spring.boot.up/spring-boot-up-apt/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.wnameless.spring.boot.up/spring-boot-up-apt)

spring-boot-up-apt
=============
Annotation Processing Tool(s) of Java Constant generating brought by SpringBootUp.

## `Goal` - generating classes of Constants based on Java class name
Java Constants(public static final) are widely used during development, however they are lack of flexibility. SpringBootUp APT provides a workaround toward this common issue.

## `Purpose` - easier refactoring and maintenance based on Java Constants
For example, Java annotation only accepts Constant or literal for String. Even though the annotation value may relate to class name somehow, we can't assign Class#getSimpleName or Class#getName to an annotation String attribute which makes refactoring and maintenance difficult sometimes.

## `Furthermore` - annotation driven arbitrary Constants
Arbitrary Constants such as String, primitive, Class and Enum can also be generated by annotation driven configuration and stored in the same generated class.

# Maven Repo
```xml
<dependency>
	<groupId>com.github.wnameless.spring.boot.up</groupId>
	<artifactId>spring-boot-up-data-apt</artifactId>
	<version>${newestVersion}</version>
	<!-- Newest version shows in the maven-central badge above -->
</dependency>
```
This lib uses Semantic Versioning: `{MAJOR.MINOR.PATCH}`.<br>
However, the MAJOR version is always matched the Spring Boot MAJOR version.

# DEMO
Traditional approach:
```java
@RequestMapping("/bars")
@Controller
public class BarController {}
// Spring Controller for Bar entity
```

Solution brought by spring-boot-up-apt:
```java
// NRBar is a class of constants, which is automatically generated by spring-boot-up-apt
@RequestMapping(NRBar.RESOURCE_PATH)
@Controller
public class BarController {}
```
```java
@NamedResource
public class Bar {}
```

# Quick Start
Annotate `@NamedResource` with any class, enum or interface
```java
@NamedResource
public class Bar {}
```

Maven dependencies
```xml
<dependency>
  <groupId>com.github.wnameless.spring.boot.up</groupId>
  <artifactId>spring-boot-up-apt</artifactId>
  <version>${newestVersion}</version>
</dependency>
```

Maven plugins
```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-compiler-plugin</artifactId>
  <configuration>
    <annotationProcessorPaths>
      <path>
        <groupId>com.github.wnameless.spring.boot.up</groupId>
        <artifactId>spring-boot-up-apt</artifactId>
        <version>${newestVersion}</version>
      </path>
    </annotationProcessorPaths>
  </configuration>
</plugin>
```

# Feature List<a id='top'></a>
| Name | option | Description | Since |
| --- | --- | --- | --- |
| [NamedResource](#3.0.0-1) || Generate a class of constants based on the class name | v3.0.0 |
|| [default](#3.0.0-1.1) | Default behavior of @NamedResource | v3.0.0 |
|| [injectable](#3.0.0-1.2) | Add jakarta.inject.Named or javax.inject.Named to generated class | v3.0.0 |
|| [markerInterface](#3.0.0-1.3) | Option to implement marker interface INamedResource for generated class | v3.0.0 |
|| [classNamePrefix classNameSuffix](#3.0.0-1.4) | Add prefix or suffix to generated class name | v3.0.0 |
|| [singular](#3.0.0-1.5) | Change singular name for annotated class  | v3.0.0 |
|| [plural](#3.0.0-1.6) | Change plural name for annotated class | v3.0.0 |
|| [inferredConstants](#3.0.0-1.7) | Infer a constant by target naming format | v3.0.0 |
|| [literalSingularConstants](#3.0.0-1.8) | Create constants of literal singular names in different naming formats | v3.0.0 |
|| [literalPluralConstants](#3.0.0-1.9) | Create constants of literal plural names in different naming formats | v3.0.0 |
|| [constants](#3.0.0-1.10) | Add arbitrary String constants | v3.0.0 |
|| [primitive constants](#3.0.0-1.11) | Add arbitrary primitive constants | v3.0.0 |
|| [Class constants](#3.0.0-1.12) | Add arbitrary Class constants | v3.0.0 |
|| [Enum constants](#3.0.0-1.13) | Add arbitrary Enum constants | v3.0.0 |

### [:top:](#top) NamedResource<a id='3.0.0-1'></a>
#### [:top:](#top) Annotation option: `default`<a id='3.0.0-1.1'></a>
_Config_:
```java
@NamedResource
public SimpleNamedResource {}
```
_Generated_: NRSimpleNamedResource (NR is the default class name prefix)
```java
NRSimpleNamedResource.SINGULAR // SimpleNamedResource
NRSimpleNamedResource.PLURAL   // SimpleNamedResources

NRSimpleNamedResource.CLASS_SIMPLE_NAME // SimpleNamedResource
NRSimpleNamedResource.CLASS_NAME        // com.github.wnameless.spring.boot.up.apt.model.SimpleNamedResource
NRSimpleNamedResource.PACKAGE_NAME      // com.github.wnameless.spring.boot.up.apt.model

// 5 naming formats: UPPER_CAMEL, LOWER_CAMEL, UPPER_UNDERSCORE, LOWER_UNDERSCORE, LOWER_HYPHEN
NRSimpleNamedResource.UPPER_CAMEL_SINGULAR      // SimpleNamedResource
NRSimpleNamedResource.LOWER_CAMEL_SINGULAR      // simpleNamedResource
NRSimpleNamedResource.UPPER_UNDERSCORE_SINGULAR // SIMPLE_NAMED_RESOURCE
NRSimpleNamedResource.LOWER_UNDERSCORE_SINGULAR // simple_named_resource
NRSimpleNamedResource.LOWER_HYPHEN_SINGULAR     // simple-named-resource
NRSimpleNamedResource.UPPER_CAMEL_PLURAL        // SimpleNamedResources
NRSimpleNamedResource.LOWER_CAMEL_PLURAL        // simpleNamedResources
NRSimpleNamedResource.UPPER_UNDERSCORE_PLURAL   // SIMPLE_NAMED_RESOURCES
NRSimpleNamedResource.LOWER_UNDERSCORE_PLURAL   // simple_named_resources
NRSimpleNamedResource.LOWER_HYPHEN_PLURAL       // simple-named-resources

// Default inferred constants: RESOURCE, RESOURCES, RESOURCE_PATH
NRSimpleNamedResource.RESOURCE      // simple-named-resource
NRSimpleNamedResource.RESOURCES     // simple-named-resources
NRSimpleNamedResource.RESOURCE_PATH // /simple-named-resources
```

#### [:top:](#top) Annotation option: `injectable`<a id='3.0.0-1.2'></a>
_Config_:
```java
// Default value is InjectType.NONE
@NamedResource(injectable = InjectType.JAKARTA) // InjectType.JAVAX is available
public class JakartaNamedResource {}
```
_Generated_:
```java
@Named // jakarta.inject.Named
public final class NRJakartaNamedResource implements INamedResource {
  ...
}
```

#### [:top:](#top) Annotation option: `markerInterface`<a id='3.0.0-1.3'></a>
_Config_:
```java
@NamedResource(markerInterface = false) // Default value is true
public class NoMarkerNamedResource {}
```
_Generated_:
```java
public final class NRJakartaNamedResource { // By default, all generated classes implement INamedResource marker interface
  ...
}
```

#### [:top:](#top) Annotation option: `classNamePrefix` and `classNameSuffix`<a id='3.0.0-1.4'></a>
_Config_:
```java
@NamedResource(classNamePrefix = "A", classNameSuffix = "Z")
public class PrefixSuffixNamedResource {}
```
_Generated_:
```java
public final class APrefixSuffixNamedResourceZ implements INamedResource {
  ...
}
```

#### [:top:](#top) Annotation option: `singular`<a id='3.0.0-1.5'></a>
_Config_:
```java
@NamedResource(singular = @Naming(value = "alt-sing-name-rsc", format = NamingFormat.LOWER_HYPHEN))
public class AlteredSingularNamedResource {}
```
_Generated_:
```java
// JUnit
assertEquals("alt-sing-name-rsc", NRAlteredSingularNamedResource.SINGULAR);
// singular opt affects plural opt,
// however plural opt can be overridden if both singular and plural opts are existed
assertEquals("alt-sing-name-rscs", NRAlteredSingularNamedResource.PLURAL);
```

#### [:top:](#top) Annotation option: `plural`<a id='3.0.0-1.6'></a>
_Config_:
```java
@NamedResource(plural = @Naming(value = "alt-pl-name-rscs", format = NamingFormat.LOWER_HYPHEN))
public class AlteredPluralOnlyNamedResource {}
```
_Generated_:
```java
// JUnit
// plural opt won't affect singlur opt
assertEquals("AlteredPluralOnlyNamedResource", NRAlteredPluralOnlyNamedResource.SINGULAR);
assertEquals("alt-pl-name-rscs", NRAlteredPluralOnlyNamedResource.PLURAL);
```

#### [:top:](#top) Annotation option: `inferredConstants`<a id='3.0.0-1.7'></a>
_Config_:
```java
@NamedResource(inferredConstants = {
    @InferredConstant(name = "ROOT", plural = true,
        format = NamingFormat.UPPER_CAMEL, prefix = "/"),
    @InferredConstant(name = "LIST_ITEM_TEMPLATE", plural = false,
        format = NamingFormat.LOWER_UNDERSCORE, suffix = "_{}")})
public class InferredConstantsNamedResource {}
```
_Generated_:
```java
// JUnit
assertEquals("/InferredConstantsNamedResources", NRInferredConstantsNamedResource.ROOT);
assertEquals("inferred_constants_named_resource_{}", NRInferredConstantsNamedResource.LIST_ITEM_TEMPLATE);
```

#### [:top:](#top) Annotation option: `literalSingularConstants`<a id='3.0.0-1.8'></a>
_Config_:
```java
@NamedResource(singular = @Naming(value = "MountEverest", format = NamingFormat.UPPER_CAMEL),
    literalSingularConstants = {
        NamingFormat.LOWER_CAMEL, NamingFormat.UPPER_CAMEL,
        NamingFormat.LOWER_UNDERSCORE, NamingFormat.UPPER_UNDERSCORE
    })
```
_Generated_:
```java
// JUnit
assertEquals("mountEverest", NRLiteralSingularNamedResource.mountEverest);
assertEquals("mount_everest", NRLiteralSingularNamedResource.mount_everest);
assertEquals("MountEverest", NRLiteralSingularNamedResource.MountEverest);
assertEquals("MOUNT_EVEREST", NRLiteralSingularNamedResource.MOUNT_EVEREST);
```

#### [:top:](#top) Annotation option: `literalPluralConstants`<a id='3.0.0-1.9'></a>
_Config_:
```java
@NamedResource(plural = @Naming(value = "bellBottoms", format = NamingFormat.LOWER_CAMEL),
    literalPluralConstants = {
        NamingFormat.LOWER_CAMEL, NamingFormat.UPPER_CAMEL,
        NamingFormat.LOWER_UNDERSCORE, NamingFormat.UPPER_UNDERSCORE
    })
```
_Generated_:
```java
// JUnit
assertEquals("bellBottoms", NRLiteralPluralNamedResource.bellBottoms);
assertEquals("BellBottoms", NRLiteralPluralNamedResource.BellBottoms);
assertEquals("bell_bottoms", NRLiteralPluralNamedResource.bell_bottoms);
assertEquals("BELL_BOTTOMS", NRLiteralPluralNamedResource.BELL_BOTTOMS);
```

#### [:top:](#top) Annotation option: `constants`<a id='3.0.0-1.10'></a>
_Config_:
```java
@NamedResource(constants = {
    @Constant(name = "DEATH_CARD", value = "Spade-1"),
    @Constant(name = "BEER_CARD", value = "Diamond-7")})
public class ConstantNamedResource {}
```
_Generated_:
```java
// JUnit
assertEquals("Spade-1", NRConstantNamedResource.DEATH_CARD);
assertEquals("Diamond-7", NRConstantNamedResource.BEER_CARD);
```

#### [:top:](#top) Annotation option: `primitive constants`<a id='3.0.0-1.11'></a>
_Config_:
```java
@NamedResource(
    booleanConstants = {@BooleanConstant(name = "LIE", value = false)},
    byteConstants = {@ByteConstant(name = "BITS", value = 8)},
    charConstants = {@CharConstant(name = "YES", value = 'y')},
    shortConstants = {@ShortConstant(name = "UNIX_EPOCH_YEAR", value = 1970)},
    intConstants = {@IntConstant(name = "MARS_DISTANCE_KM", value = 225000000)},
    longConstants = {@LongConstant(name = "LIGHT_YEAR_M", value = 9460730777119564L)},
    floatConstants = {@FloatConstant(name = "GOLDEN_RATIO", value = 1.618f)},
    doubleConstants = {@DoubleConstant(name = "PI", value = 3.14159265358979323846)}
)
public class ConstantsNamedResource {}
```
_Generated_:
```java
// JUnit
assertEquals(false, NRConstantsNamedResource.LIE);
assertEquals(8, NRConstantsNamedResource.BITS);
assertEquals('y', NRConstantsNamedResource.YES);
assertEquals(1970, NRConstantsNamedResource.UNIX_EPOCH_YEAR);
assertEquals(225000000, NRConstantsNamedResource.MARS_DISTANCE_KM);
assertEquals(9460730777119564L, NRConstantsNamedResource.LIGHT_YEAR_M);
assertEquals(1.618f, NRConstantsNamedResource.GOLDEN_RATIO);
assertEquals(3.14159265358979323846, NRConstantsNamedResource.PI);
```

#### [:top:](#top) Annotation option: `classConstants`<a id='3.0.0-1.12'></a>
_Config_:
```java
@NamedResource(
    classConstants = {@ClassConstant(name = "INTEGER_CLASS", value = Integer.class)}
)
public class ConstantsNamedResource {}
```
_Generated_:
```java
// JUnit
assertEquals(Integer.class, NRConstantsNamedResource.INTEGER_CLASS);
```

#### [:top:](#top) Annotation option: `enumConstants`<a id='3.0.0-1.13'></a>
_Config_:
```java
@NamedResource(
    enumConstants = {
        @EnumConstant(name = "MONTH_SEPTEMBER", valueType = Month.class, valueKey = "SEPTEMBER")}
)
public class ConstantsNamedResource {}
```
_Generated_:
```java
// JUnit
assertEquals(Month.SEPTEMBER, NRConstantsNamedResource.MONTH_SEPTEMBER);
```
_Advanced Usage_:
```java
// Lombok(@FieldNameConstants) - generates String constants for all Enum constant names
@FieldNameConstants
public enum RBG {
  @FieldNameConstants.Include RED,
  @FieldNameConstants.Include BLUE,
  @FieldNameConstants.Include GREEN;
}
```
```java
// To avoid refactoring errors in the future by using RBG.Fields.BLUE as the valueKey
@NamedResource(
    enumConstants = {
        @EnumConstant(name = "RGB_BLUE", valueType = RBG.class, valueKey = RBG.Fields.BLUE)}
)
public class ConstantsNamedResource {}
```

## MISC
| Note| Since |
| --- | --- |
| Java 17 required. | v3.0.0 |
| Spring Boot 3.0.0+ required. | v3.0.0 |
