
package goja.castor.castor;

import com.alibaba.fastjson.JSON;
import goja.castor.Castor;
import goja.castor.FailToCastObjectException;

import java.util.Map;


@SuppressWarnings({"rawtypes"})
public class Map2String extends Castor<Map, String> {

    @Override
    public String cast(Map src, Class<?> toType, String... args) throws FailToCastObjectException {
        return JSON.toJSONString(src);
    }

}
