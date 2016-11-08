/*
 * MongoWP - MongoWP: Bson Netty
 * Copyright © 2014 8Kdata Technology (www.8kdata.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.eightkdata.mongowp.bson.netty.annotations;

import io.netty.buffer.ByteBuf;
import java.lang.annotation.*;

/**
 * This annotation is used to identify when a {@link ByteBuf} might not be 
 * {@linkplain Tight}.
 *
 * The ByteBuf annotated with that can contain more bytes than the expected, but
 * its {@linkplain ByteBuf#readerIndex() readerIndex} must still point to the
 * first significative byte.
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD})
@Documented
public @interface Loose {
}
