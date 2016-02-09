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
 * along with bson-netty. If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2016 8Kdata.
 * 
 */

package com.eightkdata.mongowp.bson.netty.pool;

import io.netty.buffer.ByteBuf;

/**
 *
 */
public class OnlyLikelyStringPoolPolicy extends StringPoolPolicy {

    private OnlyLikelyStringPoolPolicy() {
    }

    @Override
    public boolean apply(boolean likelyCacheable, ByteBuf input) {
        return likelyCacheable;
    }

    @Override
    public String toString() {
        return "only likely";
    }

    public static OnlyLikelyStringPoolPolicy getInstance() {
        return OnlyLikelyStringPoolPolicyHolder.INSTANCE;
    }

    private static class OnlyLikelyStringPoolPolicyHolder {
        private static final OnlyLikelyStringPoolPolicy INSTANCE = new OnlyLikelyStringPoolPolicy();
    }

    //@edu.umd.cs.findbugs.annotations.SuppressFBWarnings(value = "UPM_UNCALLED_PRIVATE_METHOD")
    private Object readResolve()  {
        return OnlyLikelyStringPoolPolicy.getInstance();
    }
 }
