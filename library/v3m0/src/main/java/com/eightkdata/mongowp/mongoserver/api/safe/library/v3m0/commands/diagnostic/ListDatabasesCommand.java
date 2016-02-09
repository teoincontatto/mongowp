
package com.eightkdata.mongowp.mongoserver.api.safe.library.v3m0.commands.diagnostic;

import com.eightkdata.mongowp.bson.BsonArray;
import com.eightkdata.mongowp.bson.BsonDocument;
import com.eightkdata.mongowp.bson.BsonType;
import com.eightkdata.mongowp.bson.BsonValue;
import com.eightkdata.mongowp.bson.annotations.NotMutable;
import com.eightkdata.mongowp.exceptions.BadValueException;
import com.eightkdata.mongowp.exceptions.NoSuchKeyException;
import com.eightkdata.mongowp.exceptions.TypesMismatchException;
import com.eightkdata.mongowp.fields.*;
import com.eightkdata.mongowp.server.api.impl.AbstractCommand;
import com.eightkdata.mongowp.mongoserver.api.safe.library.v3m0.commands.diagnostic.ListDatabasesCommand.ListDatabasesReply;
import com.eightkdata.mongowp.mongoserver.api.safe.library.v3m0.tools.EmptyCommandArgumentMarshaller;
import com.eightkdata.mongowp.server.api.tools.Empty;
import com.eightkdata.mongowp.utils.BsonArrayBuilder;
import com.eightkdata.mongowp.utils.BsonDocumentBuilder;
import com.eightkdata.mongowp.utils.BsonReaderTool;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class ListDatabasesCommand extends AbstractCommand<Empty, ListDatabasesReply>{
    private static final Logger LOGGER
            = LoggerFactory.getLogger(ListDatabasesCommand.class);
    public static final ListDatabasesCommand INSTANCE = new ListDatabasesCommand();
    private static final String COMMAND_NAME = "listDatabases";

    public ListDatabasesCommand() {
        super(COMMAND_NAME);
    }

    @Override
    public boolean isAdminOnly() {
        return true;
    }

    @Override
    public Class<? extends Empty> getArgClass() {
        return Empty.class;
    }

    @Override
    public Empty unmarshallArg(BsonDocument requestDoc) {
        return Empty.getInstance();
    }

    @Override
    public BsonDocument marshallArg(Empty request) {
        return EmptyCommandArgumentMarshaller.marshallEmptyArgument(this);
    }

    @Override
    public Class<? extends ListDatabasesReply> getResultClass() {
        return ListDatabasesReply.class;
    }

    @Override
    public BsonDocument marshallResult(ListDatabasesReply reply) {
        return reply.marshall();
    }

    @Override
    public ListDatabasesReply unmarshallResult(BsonDocument replyDoc)
            throws TypesMismatchException, NoSuchKeyException, BadValueException {
        return ListDatabasesReply.unmarshall(replyDoc);
    }

    public static class ListDatabasesReply {

        private static final ArrayField DATABASES_FIELD = new ArrayField("databases");
        private static final LongField TOTAL_SIZE_FIELD = new LongField("totalSize");
        private static final LongField TOTAL_SIZE_MB_FIELD = new LongField("totalSizeMb");

        private final List<DatabaseEntry> databases;
        private final long totalSize;

        public ListDatabasesReply(@NotMutable List<DatabaseEntry> databases, long sizeOnDisk) {
            this.databases = Collections.unmodifiableList(databases);
            this.totalSize = sizeOnDisk;

            long temp = 0;
            for (DatabaseEntry database : databases) {
                temp += database.getSizeOnDisk();
            }
            if (temp != sizeOnDisk) {
                LOGGER.warn("Inconsistent data provided to " + getClass()
                        + " constructor. Recived a total size of {} but {} was "
                        + "calculated. Using provided value",
                        sizeOnDisk,
                        temp);
            }
        }

        public List<DatabaseEntry> getDatabases() {
            return databases;
        }

        /**
         *
         * @return the total size of all databases file on disk in bytes
         */
        public long getTotalSize() {
            return totalSize;
        }

        private static ListDatabasesReply unmarshall(
                BsonDocument replyDoc) throws TypesMismatchException, NoSuchKeyException {
            BsonArray arr = BsonReaderTool.getArray(replyDoc, DATABASES_FIELD);

            int i = 0;
            ArrayList<DatabaseEntry> databases = Lists.newArrayListWithCapacity(arr.size());
            for (BsonValue element : arr) {
                if (!element.isDocument()) {
                    String field = DATABASES_FIELD.getFieldName() + "." + i;
                    throw new TypesMismatchException(
                            field,
                            BsonType.DOCUMENT,
                            element.getType(),
                            "Element " + field + " is not a document as it was "
                            + "expected but a " + element.getType());
                }
                databases.add(new DatabaseEntry(element.asDocument()));
                i++;
            }

            long sizeOnDisk = BsonReaderTool.getNumeric(replyDoc, TOTAL_SIZE_FIELD).longValue();
            return new ListDatabasesReply(databases, sizeOnDisk);
        }

        private BsonDocument marshall() {
            BsonArrayBuilder arr = new BsonArrayBuilder();
            for (DatabaseEntry database : databases) {
                arr.add(database.marshall());
            }

            return new BsonDocumentBuilder()
                    .append(DATABASES_FIELD, arr.build())
                    .append(TOTAL_SIZE_FIELD, totalSize)
                    .append(TOTAL_SIZE_MB_FIELD, totalSize / (1000 * 1000))
                    .build();
        }

        public static class DatabaseEntry {
            private static final StringField NAME_FIELD = new StringField("name");
            private static final LongField SIZE_ON_DISK_FIELD = new LongField("sizeOnDisk");
            private static final BooleanField EMPTY_FIELD = new BooleanField("empty");

            private final String name;
            private final long sizeOnDisk;
            private final boolean empty;

            public DatabaseEntry(String name, long sizeOnDisk, boolean empty) {
                this.name = name;
                this.sizeOnDisk = sizeOnDisk;
                this.empty = empty;
            }

            private DatabaseEntry(BsonDocument doc) throws TypesMismatchException, NoSuchKeyException {
                name = BsonReaderTool.getString(doc, NAME_FIELD);
                sizeOnDisk = BsonReaderTool.getNumeric(doc, SIZE_ON_DISK_FIELD).longValue();
                empty = BsonReaderTool.getBoolean(doc, EMPTY_FIELD);
            }

            /**
             *
             * @return the name of the database
             */
            public String getName() {
                return name;
            }

            /**
             *
             * @return the total size of the database file on disk in bytes
             */
            public long getSizeOnDisk() {
                return sizeOnDisk;
            }

            /**
             *
             * @return whether the databse has data
             */
            public boolean isEmpty() {
                return empty;
            }

            private BsonDocument marshall() {
                return new BsonDocumentBuilder()
                        .append(NAME_FIELD, name)
                        .append(SIZE_ON_DISK_FIELD, sizeOnDisk)
                        .append(EMPTY_FIELD, empty)
                        .build();
            }
        }
    }
}
