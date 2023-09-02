package model;

import com.github.wnameless.apt.NamedResource;
import com.github.wnameless.apt.NamedResource.InferredConstant;
import com.github.wnameless.apt.NamedResource.NamingFormat;

@NamedResource(inferredConstants = {
    @InferredConstant(name = "ROOT", plural = true, format = NamingFormat.UPPER_CAMEL,
        prefix = "/"),
    @InferredConstant(name = "LIST_ITEM_TEMPLATE", plural = false,
        format = NamingFormat.LOWER_UNDERSCORE, suffix = "_{}")})
public class InferredConstantsNamedResource {}
