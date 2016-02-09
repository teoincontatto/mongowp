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

package com.eightkdata.mongowp.bson;

/**
 *
 */
public interface BsonDouble extends BsonNumber<Double> {

    /**
     * Return true if the difference between this value and the given one is
     * less than a error <em>delta</em>
     * @param other
     * @param delta the acceptable error
     * @return true if they are close enough
     */
    boolean simmilar(BsonDouble other, double delta);

    /**
     * Two BsonDouble values are equal if the double values their contain are
     * equal.
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj);

    /**
     * The hashCode of a BsonDouble is the hashCode of the value it contains.
     *
     * @return
     * @see Double#hashCode() 
     */
    @Override
    public int hashCode();
}
