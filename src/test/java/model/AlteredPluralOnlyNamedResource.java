package model;

import com.github.wnameless.apt.NamedResource;
import com.github.wnameless.apt.NamedResource.Naming;
import com.github.wnameless.apt.NamedResource.NamingFormat;

@NamedResource(plural = @Naming(value = "alt-pl-name-rscs", format = NamingFormat.LOWER_HYPHEN))
public class AlteredPluralOnlyNamedResource {}

