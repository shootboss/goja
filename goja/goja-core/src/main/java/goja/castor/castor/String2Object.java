
package goja.castor.castor;

import com.alibaba.fastjson.JSON;
import goja.castor.Castor;
import goja.exceptions.FailToCastObjectException;
import goja.lang.Mirror;
import goja.lang.StringKit;

public class String2Object extends Castor<String, Object> {

    @Override
    public Object cast(String src, Class<?> toType, String... args)
            throws FailToCastObjectException {
        if (StringKit.isQuoteByIgnoreBlank(src, '{', '}'))
            return JSON.parseObject(src, toType);
        return Mirror.me(toType).born(src);
    }

}
