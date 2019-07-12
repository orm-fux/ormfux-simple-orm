package com.github.ormfux.simple.orm.generators;

import java.util.Date;

import com.github.ormfux.common.utils.DateUtils;

/**
 * A generator that creates a date representing the current date and time.
 */
public class DateNowGenerator implements ValueGenerator<Date> {
    
    /** {@inheritDoc} */
    @Override
    public Date generate(Object previousValue) {
        return DateUtils.now();
    }
}
