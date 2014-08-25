
package goja.castor.castor;

import goja.castor.Castor;
import goja.castor.FailToCastObjectException;

import java.sql.Date;
import java.sql.Timestamp;


public class SqlDate2Timestamp extends Castor<Date, Timestamp> {

    @Override
    public Timestamp cast(Date src, Class<?> toType, String... args)
            throws FailToCastObjectException {
        return new Timestamp(src.getTime());
    }

}
