
package goja.castor.castor;

import java.lang.reflect.Array;

import com.alibaba.fastjson.JSON;
import goja.castor.Castor;
import goja.exceptions.FailToCastObjectException;
import goja.lang.Lang;
import goja.lang.StringKit;

public class String2Array extends Castor<String, Object> {

    public String2Array() {
        this.fromClass = String.class;
        this.toClass = Array.class;
    }

    @Override
    public Object cast(String src, Class<?> toType, String... args)
            throws FailToCastObjectException {
        if (StringKit.isQuoteByIgnoreBlank(src, '[', ']')) {
            return JSON.parseObject(src, toType);
        }
        String[] ss = StringKit.splitIgnoreBlank(src);
        return Lang.array2array(ss, toType.getComponentType());
    }

}
