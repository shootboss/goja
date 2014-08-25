
package goja.castor.castor;


import goja.castor.Castor;
import goja.castor.FailToCastObjectException;

public class Object2String extends Castor<Object, String> {

    @Override
    public String cast(Object src, Class<?> toType, String... args)
            throws FailToCastObjectException {
        return src.toString();
    }

}
