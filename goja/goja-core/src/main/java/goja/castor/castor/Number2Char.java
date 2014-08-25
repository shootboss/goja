
package goja.castor.castor;


import goja.castor.Castor;
import goja.castor.FailToCastObjectException;

public class Number2Char extends Castor<Number, Character> {

    @Override
    public Character cast(Number src, Class<?> toType, String... args)
            throws FailToCastObjectException {
        return (char) src.intValue();
    }

}
