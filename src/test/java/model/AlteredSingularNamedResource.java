package model;

import com.github.wnameless.apt.NamedResource;
import com.github.wnameless.apt.NamedResource.Naming;
import com.github.wnameless.apt.NamedResource.NamingFormat;

@NamedResource(singular = @Naming(value = "alt-sing-name-rsc", format = NamingFormat.LOWER_HYPHEN))
public class AlteredSingularNamedResource {}
