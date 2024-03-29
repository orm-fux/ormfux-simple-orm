package com.github.ormfux.simple.orm.generators;

import static com.github.ormfux.common.utils.NullableUtils.isNull;

/**
 * A generator that increments a value of type "long" by {@code 1}.
 */
public class LongIncrementGenerator implements ValueGenerator<Long> {
    
    /** {@inheritDoc} */
    @Override
    public Long generate(final Object previousValue) {
        if (isNull(previousValue)) {
            return 0L;
        } else {
            return ((Long) previousValue) + 1;
        }
    }
    
}
