
package goja.castor.castor;

import goja.castor.Castor;
import goja.castor.FailToCastObjectException;

import java.util.Collection;


@SuppressWarnings({"unchecked", "rawtypes"})
public class Collection2Collection extends Castor<Collection, Collection> {

    @Override
    public Collection cast(Collection src, Class<?> toType, String... args)
            throws FailToCastObjectException {
        Collection coll = createCollection(src, toType);
        coll.addAll(src);
        return coll;
    }

}
