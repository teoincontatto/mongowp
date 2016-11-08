/*
 * MongoWP - Mongo Server: API
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
package com.eightkdata.mongowp.server.api.impl;

import com.eightkdata.mongowp.OpTime;
import com.eightkdata.mongowp.ErrorCode;

/**
 *
 */
public class DeleteOpResult extends SimpleWriteOpResult {
    private static final long serialVersionUID = 1L;

    private final long deletedDocsCounter;

    public DeleteOpResult(
            long deletedDocsCounter,
            ErrorCode error,
            String errorDesc,
            ReplicationInformation replInfo,
            ShardingInformation shardInfo,
            OpTime optime) {
        super(error, errorDesc, replInfo, shardInfo, optime);
        this.deletedDocsCounter = deletedDocsCounter;
    }

    public long getDeletedDocsCounter() {
        return deletedDocsCounter;
    }

    @Override
    public long getN() {
        return deletedDocsCounter;
    }
}
