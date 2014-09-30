
package goja.castor.castor;

import goja.lang.StringKit;

import java.sql.Timestamp;

public class String2Timestamp extends DateTimeCastor<String, Timestamp> {

    @Override
    public Timestamp cast(String src, Class<?> toType, String... args) {
        if (StringKit.isBlank(src))
            return null;

        return new Timestamp(toDate(src).getTime());

    }

}
