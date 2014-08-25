
package goja.castor.castor;

import goja.castor.Castor;
import goja.castor.FailToCastObjectException;

import java.sql.Timestamp;
import java.util.Date;


public class Datetime2Timpestamp extends Castor<Date, Timestamp> {

    @Override
    public Timestamp cast(Date src, Class<?> toType, String... args)
            throws FailToCastObjectException {
        return new Timestamp(src.getTime());
    }

}
