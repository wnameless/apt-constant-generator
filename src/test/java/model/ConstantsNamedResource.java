package model;

import java.time.Month;
import com.github.wnameless.apt.NamedResource;
import com.github.wnameless.apt.NamedResource.BooleanConstant;
import com.github.wnameless.apt.NamedResource.ByteConstant;
import com.github.wnameless.apt.NamedResource.CharConstant;
import com.github.wnameless.apt.NamedResource.ClassConstant;
import com.github.wnameless.apt.NamedResource.DoubleConstant;
import com.github.wnameless.apt.NamedResource.EnumConstant;
import com.github.wnameless.apt.NamedResource.FloatConstant;
import com.github.wnameless.apt.NamedResource.IntConstant;
import com.github.wnameless.apt.NamedResource.LongConstant;
import com.github.wnameless.apt.NamedResource.ShortConstant;

@NamedResource( //
    booleanConstants = {@BooleanConstant(name = "LIE", value = false)}, //
    byteConstants = {@ByteConstant(name = "BITS", value = 8)}, //
    charConstants = {@CharConstant(name = "YES", value = 'y')}, //
    shortConstants = {@ShortConstant(name = "UNIX_EPOCH_YEAR", value = 1970)}, //
    intConstants = {@IntConstant(name = "MARS_DISTANCE_KM", value = 225000000)}, //
    longConstants = {@LongConstant(name = "LIGHT_YEAR_M", value = 9460730777119564L)}, //
    floatConstants = {@FloatConstant(name = "GOLDEN_RATIO", value = 1.618f)}, //
    doubleConstants = {@DoubleConstant(name = "PI", value = 3.14159265358979323846)}, //
    classConstants = {@ClassConstant(name = "INTEGER_CLASS", value = Integer.class)}, //
    enumConstants = {
        @EnumConstant(name = "MONTH_SEPTEMBER", valueType = Month.class, valueKey = "SEPTEMBER")} //
)
public class ConstantsNamedResource {}
