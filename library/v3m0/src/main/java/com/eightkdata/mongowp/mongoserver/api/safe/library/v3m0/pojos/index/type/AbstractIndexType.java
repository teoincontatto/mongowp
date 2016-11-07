/*
 *     This file is part of ToroDB.
 *
 *     ToroDB is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     ToroDB is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with ToroDB. If not, see <http://www.gnu.org/licenses/>.
 *
 *     Copyright (c) 2014, 8Kdata Technology
 *     
 */

package com.eightkdata.mongowp.mongoserver.api.safe.library.v3m0.pojos.index.type;

import com.eightkdata.mongowp.bson.BsonValue;

public abstract class AbstractIndexType implements IndexType {
    
    public final BsonValue<?> bsonValue;
    public final String name;
    
    protected AbstractIndexType(BsonValue<?> bsonValue) {
        this.name = bsonValue.isString() ? bsonValue.asString().getValue() : bsonValue.toString();
        this.bsonValue = bsonValue;
    }

    @Override
    public BsonValue<?> toBsonValue() {
        return bsonValue;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equalsToBsonValue(BsonValue<?> bsonValue) {
        return this.bsonValue.equals(bsonValue);
    }
    
    protected boolean sameNumber(BsonValue<?> bsonValue) {
        return bsonValue.isNumber() && 
                toBsonValue().asInt32()
                    .equals(bsonValue.asInt32());
    }

}