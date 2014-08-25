
package goja.castor.castor;


import goja.lang.Strings;

public class String2SqlDate extends DateTimeCastor<String, java.sql.Date> {

    @Override
    public java.sql.Date cast(String src, Class<?> toType, String... args) {
        if (Strings.isBlank(src))
            return null;

        return new java.sql.Date(toDate(src).getTime());

    }

}
