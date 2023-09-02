package model;

import com.github.wnameless.apt.NamedResource;
import com.github.wnameless.apt.NamedResource.Naming;
import com.github.wnameless.apt.NamedResource.NamingFormat;

@NamedResource(singular = @Naming(value = "MountEverest", format = NamingFormat.UPPER_CAMEL),
    literalSingularConstants = { //
        NamingFormat.LOWER_CAMEL, NamingFormat.UPPER_CAMEL, //
        NamingFormat.LOWER_UNDERSCORE, NamingFormat.UPPER_UNDERSCORE, //
        NamingFormat.LOWER_HYPHEN //
    })
public class LiteralSingularNamedResource {}
