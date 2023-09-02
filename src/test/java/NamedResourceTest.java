import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.Month;
import org.junit.jupiter.api.Test;
import org.springframework.core.annotation.AnnotationUtils;
import com.github.wnameless.apt.INamedResource;
import model.APrefixSuffixNamedResourceZ;
import model.NRAlteredPluralOnlyNamedResource;
import model.NRAlteredSingularNamedResource;
import model.NRAlteredSingularPluralNamedResource;
import model.NRConstantNamedResource;
import model.NRConstantsNamedResource;
import model.NRInferredConstantsNamedResource;
import model.NRJakartaNamedResource;
import model.NRJavaxNamedResource;
import model.NRLiteralPluralNamedResource;
import model.NRLiteralSingularNamedResource;
import model.NRNoMarkerNamedResource;
import model.NRSimpleNamedResource;
import model.SimpleNamedResource;

public class NamedResourceTest {

  @Test
  public void testDefault() {
    assertEquals("simple-named-resource", NRSimpleNamedResource.RESOURCE);
    assertEquals("simple-named-resources", NRSimpleNamedResource.RESOURCES);
    assertEquals("/simple-named-resources", NRSimpleNamedResource.RESOURCE_PATH);
    assertEquals("SimpleNamedResource", NRSimpleNamedResource.SINGULAR);
    assertEquals("SimpleNamedResources", NRSimpleNamedResource.PLURAL);
    assertEquals("SimpleNamedResource", NRSimpleNamedResource.CLASS_SIMPLE_NAME);
    assertEquals(SimpleNamedResource.class.getName(), NRSimpleNamedResource.CLASS_NAME);
    assertEquals(SimpleNamedResource.class.getPackageName(), NRSimpleNamedResource.PACKAGE_NAME);
    assertEquals("SimpleNamedResource", NRSimpleNamedResource.UPPER_CAMEL_SINGULAR);
    assertEquals("simpleNamedResource", NRSimpleNamedResource.LOWER_CAMEL_SINGULAR);
    assertEquals("SIMPLE_NAMED_RESOURCE", NRSimpleNamedResource.UPPER_UNDERSCORE_SINGULAR);
    assertEquals("simple_named_resource", NRSimpleNamedResource.LOWER_UNDERSCORE_SINGULAR);
    assertEquals("simple-named-resource", NRSimpleNamedResource.LOWER_HYPHEN_SINGULAR);
    assertEquals("SimpleNamedResources", NRSimpleNamedResource.UPPER_CAMEL_PLURAL);
    assertEquals("simpleNamedResources", NRSimpleNamedResource.LOWER_CAMEL_PLURAL);
    assertEquals("SIMPLE_NAMED_RESOURCES", NRSimpleNamedResource.UPPER_UNDERSCORE_PLURAL);
    assertEquals("simple_named_resources", NRSimpleNamedResource.LOWER_UNDERSCORE_PLURAL);
    assertEquals("simple-named-resources", NRSimpleNamedResource.LOWER_HYPHEN_PLURAL);
  }

  @Test
  public void testInjectable() {
    assertNull(
        AnnotationUtils.findAnnotation(NRSimpleNamedResource.class, jakarta.inject.Named.class));
    assertNull(
        AnnotationUtils.findAnnotation(NRSimpleNamedResource.class, javax.inject.Named.class));
    assertNotNull(
        AnnotationUtils.findAnnotation(NRJakartaNamedResource.class, jakarta.inject.Named.class));
    assertNotNull(
        AnnotationUtils.findAnnotation(NRJavaxNamedResource.class, javax.inject.Named.class));
  }

  @Test
  public void testMarkerInterface() {
    assertTrue(INamedResource.class.isAssignableFrom(NRSimpleNamedResource.class));
    assertFalse(INamedResource.class.isAssignableFrom(NRNoMarkerNamedResource.class));
  }

  @Test
  public void testClassNamePrefixAndSuffix() {
    assertEquals("APrefixSuffixNamedResourceZ", APrefixSuffixNamedResourceZ.class.getSimpleName());
  }

  @Test
  public void testSingular() {
    assertEquals("alt-sing-name-rsc", NRAlteredSingularNamedResource.SINGULAR);
    assertEquals("alt-sing-name-rscs", NRAlteredSingularNamedResource.PLURAL);
  }

  @Test
  public void testPluralOnly() {
    assertEquals("AlteredPluralOnlyNamedResource", NRAlteredPluralOnlyNamedResource.SINGULAR);
    assertEquals("alt-pl-name-rscs", NRAlteredPluralOnlyNamedResource.PLURAL);
  }

  @Test
  public void testSingularPlural() {
    assertEquals("altSingNameRsc", NRAlteredSingularPluralNamedResource.SINGULAR);
    assertEquals("altSingNameRscs", NRAlteredSingularPluralNamedResource.PLURAL);
  }

  @Test
  public void testDefaultInferredConstant() {
    assertEquals("simple-named-resource", NRSimpleNamedResource.RESOURCE);
    assertEquals("simple-named-resources", NRSimpleNamedResource.RESOURCES);
    assertEquals("/simple-named-resources", NRSimpleNamedResource.RESOURCE_PATH);
  }

  @Test
  public void testLiteralSingularConstants() {
    assertEquals("mountEverest", NRLiteralSingularNamedResource.mountEverest);
    assertEquals("MountEverest", NRLiteralSingularNamedResource.MountEverest);
    assertEquals("mount_everest", NRLiteralSingularNamedResource.mount_everest);
    assertEquals("MOUNT_EVEREST", NRLiteralSingularNamedResource.MOUNT_EVEREST);
  }

  @Test
  public void testLiteralPluralConstants() {
    assertEquals("bellBottoms", NRLiteralPluralNamedResource.bellBottoms);
    assertEquals("BellBottoms", NRLiteralPluralNamedResource.BellBottoms);
    assertEquals("bell_bottoms", NRLiteralPluralNamedResource.bell_bottoms);
    assertEquals("BELL_BOTTOMS", NRLiteralPluralNamedResource.BELL_BOTTOMS);
  }

  @Test
  public void testInferredConstants() {
    assertEquals("/InferredConstantsNamedResources", NRInferredConstantsNamedResource.ROOT);
    assertEquals("inferred_constants_named_resource_{}",
        NRInferredConstantsNamedResource.LIST_ITEM_TEMPLATE);
  }

  @Test
  public void testConstants() {
    assertEquals("Spade-1", NRConstantNamedResource.DEATH_CARD);
    assertEquals("Diamond-7", NRConstantNamedResource.BEER_CARD);
  }

  @Test
  public void testPrimitiveConstants() {
    assertEquals(false, NRConstantsNamedResource.LIE);
    assertEquals(8, NRConstantsNamedResource.BITS);
    assertEquals('y', NRConstantsNamedResource.YES);
    assertEquals(1970, NRConstantsNamedResource.UNIX_EPOCH_YEAR);
    assertEquals(225000000, NRConstantsNamedResource.MARS_DISTANCE_KM);
    assertEquals(9460730777119564L, NRConstantsNamedResource.LIGHT_YEAR_M);
    assertEquals(1.618f, NRConstantsNamedResource.GOLDEN_RATIO);
    assertEquals(3.14159265358979323846, NRConstantsNamedResource.PI);
  }

  @Test
  public void testClassConstants() {
    assertEquals(Integer.class, NRConstantsNamedResource.INTEGER_CLASS);
  }

  @Test
  public void testEnumConstants() {
    assertEquals(Month.SEPTEMBER, NRConstantsNamedResource.MONTH_SEPTEMBER);
  }

  @Test
  public void testUnamedPackage() {
    assertEquals("", NRUnamedPackageNamedResource.class.getPackageName());
  }

}
