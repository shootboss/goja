
package goja.castor.castor;


import goja.lang.StringKit;

public class String2Datetime extends DateTimeCastor<String, java.util.Date> {

    @Override
    public java.util.Date cast(String src, Class<?> toType, String... args) {
        // 处理空白
        if (StringKit.isBlank(src))
            return null;
        return toDate(src);
    }

}
