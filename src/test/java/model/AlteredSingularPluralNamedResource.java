package model;

import com.github.wnameless.apt.NamedResource;
import com.github.wnameless.apt.NamedResource.Naming;
import com.github.wnameless.apt.NamedResource.NamingFormat;

@NamedResource(singular = @Naming(value = "altSingNameRsc", format = NamingFormat.LOWER_CAMEL),
    plural = @Naming(value = "altSingNameRscs", format = NamingFormat.LOWER_CAMEL))
public class AlteredSingularPluralNamedResource {}
