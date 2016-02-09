/*
 * This file is part of MongoWP.
 *
 * MongoWP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MongoWP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with bson. If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2016 8Kdata.
 * 
 */

package com.eightkdata.mongowp.bson.impl;

import com.eightkdata.mongowp.bson.abst.AbstractBsonInt64;

/**
 *
 */
public class PrimitiveBsonInt64 extends AbstractBsonInt64 {

    private static final long serialVersionUID = 2881925179255803046L;

    private static final PrimitiveBsonInt64 ZERO = new PrimitiveBsonInt64(0);
    private static final PrimitiveBsonInt64 ONE = new PrimitiveBsonInt64(1);

    private final long value;

    private PrimitiveBsonInt64(long value) {
        this.value = value;
    }

    public static PrimitiveBsonInt64 newInstance(long value) {
        if (value == 0) {
            return ZERO;
        }
        if (value == 1) {
            return ONE;
        }
        return new PrimitiveBsonInt64(value);
    }

    @Override
    public long longValue() {
        return value;
    }

    @Override
    public Long getValue() {
        return value;
    }
}
