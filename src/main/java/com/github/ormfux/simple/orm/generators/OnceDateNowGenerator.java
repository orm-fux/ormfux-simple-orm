package com.github.ormfux.simple.orm.generators;

import java.util.Date;

import com.github.ormfux.common.utils.DateUtils;
import com.github.ormfux.common.utils.NullableUtils;

/**
 * A generator that creates a date representing the current date and time.
 * The date is only created when there is not yet a previous value.
 */
public class OnceDateNowGenerator implements ValueGenerator<Date> {
    
    /** {@inheritDoc} */
    @Override
    public Date generate(final Object previousValue) {
        if (NullableUtils.isNull(previousValue)) {
            return DateUtils.now();
        } else {
            return (Date) previousValue;
        }
    }
}
