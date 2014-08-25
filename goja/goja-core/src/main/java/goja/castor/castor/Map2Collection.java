
package goja.castor.castor;

import goja.castor.Castor;
import goja.castor.FailToCastObjectException;

import java.util.Collection;
import java.util.Map;


@SuppressWarnings({"unchecked", "rawtypes"})
public class Map2Collection extends Castor<Map, Collection> {

    @Override
    public Collection cast(Map src, Class<?> toType, String... args)
            throws FailToCastObjectException {
        Collection coll = createCollection(src, toType);
        coll.add(src);
        return coll;
    }

}
