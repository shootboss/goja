
package goja.castor.castor;

import goja.lang.Times;

import java.util.Calendar;


public class Calendar2String extends DateTimeCastor<Calendar, String> {

    @Override
    public String cast(Calendar src, Class<?> toType, String... args) {
        return Times.sDT(src.getTime());
    }

}
