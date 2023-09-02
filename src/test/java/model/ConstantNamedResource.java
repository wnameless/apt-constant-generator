package model;

import com.github.wnameless.apt.NamedResource;
import com.github.wnameless.apt.NamedResource.Constant;

@NamedResource(constants = { //
    @Constant(name = "DEATH_CARD", value = "Spade-1"), //
    @Constant(name = "BEER_CARD", value = "Diamond-7")})
public class ConstantNamedResource {}
