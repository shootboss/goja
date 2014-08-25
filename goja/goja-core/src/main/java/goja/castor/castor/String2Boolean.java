
package goja.castor.castor;

import goja.castor.Castor;
import goja.lang.Lang;
import goja.lang.Strings;

public class String2Boolean extends Castor<String, Boolean> {

    @Override
    public Boolean cast(String src, Class<?> toType, String... args) {
        return !Strings.isBlank(src) && Lang.parseBoolean(src);
    }

}
