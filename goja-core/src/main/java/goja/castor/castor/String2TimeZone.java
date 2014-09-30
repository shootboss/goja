
package goja.castor.castor;

import java.util.TimeZone;

import goja.castor.Castor;
import goja.lang.StringKit;

public class String2TimeZone extends Castor<String, TimeZone> {

    @Override
    public TimeZone cast(String src, Class<?> toType, String... args) {
        if (StringKit.isBlank(src))
            return null;
        return TimeZone.getTimeZone(src);
    }

}
