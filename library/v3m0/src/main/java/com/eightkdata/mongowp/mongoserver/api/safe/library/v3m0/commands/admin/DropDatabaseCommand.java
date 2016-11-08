/*
 * MongoWP - MongoWP: v3.0 Library
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
package com.eightkdata.mongowp.mongoserver.api.safe.library.v3m0.commands.admin;

import com.eightkdata.mongowp.bson.BsonDocument;
import com.eightkdata.mongowp.exceptions.*;
import com.eightkdata.mongowp.server.api.MarshalException;
import com.eightkdata.mongowp.mongoserver.api.safe.library.v3m0.tools.EmptyCommandArgumentMarshaller;
import com.eightkdata.mongowp.server.api.impl.AbstractNotAliasableCommand;
import com.eightkdata.mongowp.server.api.tools.Empty;

/**
 *
 */
public class DropDatabaseCommand extends AbstractNotAliasableCommand<Empty, Empty> {

    public static final DropDatabaseCommand INSTANCE = new DropDatabaseCommand();

    private DropDatabaseCommand() {
        super("dropDatabase");
    }

    @Override
    public Class<? extends Empty> getArgClass() {
        return Empty.class;
    }

    @Override
    public Empty unmarshallArg(BsonDocument requestDoc) throws BadValueException,
            TypesMismatchException, NoSuchKeyException, FailedToParseException {
        return Empty.getInstance();
    }

    @Override
    public BsonDocument marshallArg(Empty request) throws MarshalException {
        return EmptyCommandArgumentMarshaller.marshallEmptyArgument(this);
    }

    @Override
    public Class<? extends Empty> getResultClass() {
        return Empty.class;
    }

    @Override
    public Empty unmarshallResult(BsonDocument resultDoc) throws
            BadValueException, TypesMismatchException, NoSuchKeyException,
            FailedToParseException, MongoException {
        return Empty.getInstance();
    }

    @Override
    public BsonDocument marshallResult(Empty result) throws MarshalException {
        return null;
    }

}
