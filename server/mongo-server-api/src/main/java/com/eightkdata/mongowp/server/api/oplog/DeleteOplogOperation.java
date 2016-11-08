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
package com.eightkdata.mongowp.server.api.oplog;

import com.eightkdata.mongowp.OpTime;
import com.eightkdata.mongowp.bson.BsonDocument;
import com.eightkdata.mongowp.bson.BsonValue;
import com.eightkdata.mongowp.fields.BooleanField;
import com.eightkdata.mongowp.fields.DocField;
import com.eightkdata.mongowp.utils.BsonDocumentBuilder;
import javax.annotation.concurrent.Immutable;

/**
 *
 */
@Immutable
public class DeleteOplogOperation extends CollectionOplogOperation {
    private static final long serialVersionUID = 1L;
    
    private static final DocField FILFER_FIELD = new DocField("o");
    private static final BooleanField JUST_ONE_FIELD = new BooleanField("b");


    private final BsonDocument filter;
    private final boolean justOne;

    public DeleteOplogOperation(
            BsonDocument filter,
            String database,
            String collection,
            OpTime optime,
            long h,
            OplogVersion version,
            boolean fromMigrate,
            boolean justOne) {
        super(database, collection, optime, h, version, fromMigrate);
        this.filter = filter;
        this.justOne = justOne;
    }

    public BsonDocument getFilter() {
        return filter;
    }

    public boolean isJustOne() {
        return justOne;
    }

    @Override
    public OplogOperationType getType() {
        return OplogOperationType.DELETE;
    }

    @Override
    public BsonValue<?> getDocId() {
        return getFilter().get("_id");
    }

    @Override
    public BsonDocumentBuilder toDescriptiveBson() {
        return super.toDescriptiveBson()
                .append(OP_FIELD, getType().getOplogName())
                .append(FILFER_FIELD, filter)
                .append(JUST_ONE_FIELD, justOne);
    }

    @Override
    public <Result, Arg> Result accept(OplogOperationVisitor<Result, Arg> visitor, Arg arg) {
        return visitor.visit(this, arg);
    }

}
