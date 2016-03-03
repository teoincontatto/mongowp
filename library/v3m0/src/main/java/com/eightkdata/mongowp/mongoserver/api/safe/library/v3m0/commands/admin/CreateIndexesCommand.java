
package com.eightkdata.mongowp.mongoserver.api.safe.library.v3m0.commands.admin;

import com.eightkdata.mongowp.bson.BsonArray;
import com.eightkdata.mongowp.bson.BsonDocument;
import com.eightkdata.mongowp.bson.BsonValue;
import com.eightkdata.mongowp.exceptions.BadValueException;
import com.eightkdata.mongowp.exceptions.NoSuchKeyException;
import com.eightkdata.mongowp.exceptions.TypesMismatchException;
import com.eightkdata.mongowp.fields.ArrayField;
import com.eightkdata.mongowp.fields.BooleanField;
import com.eightkdata.mongowp.fields.IntField;
import com.eightkdata.mongowp.fields.StringField;
import com.eightkdata.mongowp.mongoserver.api.safe.library.v3m0.commands.admin.CreateIndexesCommand.CreateIndexesArgument;
import com.eightkdata.mongowp.mongoserver.api.safe.library.v3m0.commands.admin.CreateIndexesCommand.CreateIndexesResult;
import com.eightkdata.mongowp.mongoserver.api.safe.library.v3m0.pojos.IndexOptions;
import com.eightkdata.mongowp.server.api.impl.AbstractCommand;
import com.eightkdata.mongowp.utils.BsonDocumentBuilder;
import com.eightkdata.mongowp.utils.BsonReaderTool;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;

/**
 *
 */
public class CreateIndexesCommand extends AbstractCommand<CreateIndexesArgument, CreateIndexesResult> {
    private static final String COMMAND_NAME = "createIndexes";
    public static final CreateIndexesCommand INSTANCE = new CreateIndexesCommand();

    public CreateIndexesCommand() {
        super(COMMAND_NAME);
    }

    @Override
    public Class<? extends CreateIndexesArgument> getArgClass() {
        return CreateIndexesArgument.class;
    }

    @Override
    public CreateIndexesArgument unmarshallArg(BsonDocument requestDoc)
            throws TypesMismatchException, NoSuchKeyException, BadValueException {
        return CreateIndexesArgument.unmarshall(requestDoc);
    }

    @Override
    public BsonDocument marshallArg(CreateIndexesArgument request) {
        return request.marshall();
    }

    @Override
    public Class<? extends CreateIndexesResult> getResultClass() {
        return CreateIndexesResult.class;
    }

    @Override
    public BsonDocument marshallResult(CreateIndexesResult reply) {
        return reply.marshall();
    }

    @Override
    public CreateIndexesResult unmarshallResult(BsonDocument replyDoc) throws TypesMismatchException, NoSuchKeyException {
        return CreateIndexesResult.unmarshall(replyDoc);
    }

    public static class CreateIndexesArgument {
        private final static StringField COLLECTION_FIELD = new StringField(COMMAND_NAME);
        private final static ArrayField INDEXES_FIELD = new ArrayField("indexes");

        private final String collection;
        private final List<IndexOptions> indexesToCreate;

        public CreateIndexesArgument(String collection, List<IndexOptions> indexesToCreate) {
            this.collection = collection;
            this.indexesToCreate = indexesToCreate;
        }

        public String getCollection() {
            return collection;
        }

        public List<IndexOptions> getIndexesToCreate() {
            return Collections.unmodifiableList(indexesToCreate);
        }

        private BsonDocument marshall() {
            List<BsonValue<?>> list = new ArrayList<>(indexesToCreate.size());
            for (IndexOptions indexToCreate : indexesToCreate) {
                list.add(indexToCreate.marshall());
            }

            return new BsonDocumentBuilder()
                    .append(COLLECTION_FIELD, collection)
                    .append(INDEXES_FIELD, list)
                    .build();
        }

        private static CreateIndexesArgument unmarshall(BsonDocument requestDoc)
                throws TypesMismatchException, NoSuchKeyException, BadValueException {
            String collection = BsonReaderTool.getString(requestDoc, COLLECTION_FIELD);

            BsonArray optionsArray = BsonReaderTool.getArray(requestDoc, INDEXES_FIELD);
            List<IndexOptions> indexes = Lists.newArrayListWithCapacity(optionsArray.size());
            for (BsonValue element : optionsArray) {
                if (!element.isDocument()) {
                    throw new BadValueException("The element " + element
                            + " inside " + INDEXES_FIELD + " array is not a document");
                }
                IndexOptions options = IndexOptions.unmarshall(element.asDocument());
                indexes.add(options);
            }
            return new CreateIndexesArgument(collection, indexes);
        }
    }

    public static class CreateIndexesResult {
        private static final IntField INDEX_BEFORE_FIELD = new IntField("numIndexesBefore");
        private static final IntField INDEX_AFTER_FIELD = new IntField("numIndexesAfter");
        private static final StringField NOTE_FIELD = new StringField("note");
        private static final BooleanField CREATED_COLLECTION_FIELD = new BooleanField("createdCollectionAutomatically");

        private final int numIndexesBefore;
        private final int numIndexesAfter;
        @Nullable
        private final String note;
        private final boolean createdCollectionAutomatically;

        public CreateIndexesResult(
                int numIndexesBefore,
                int numIndexesAfter,
                @Nullable String note,
                boolean createdCollectionAutomatically) {
            this.numIndexesBefore = numIndexesBefore;
            this.numIndexesAfter = numIndexesAfter;
            this.note = note;
            this.createdCollectionAutomatically = createdCollectionAutomatically;
        }

        public int getNumIndexesBefore() {
            return numIndexesBefore;
        }

        public int getNumIndexesAfter() {
            return numIndexesAfter;
        }

        @Nullable
        public String getNote() {
            return note;
        }

        public boolean isCreatedCollectionAutomatically() {
            return createdCollectionAutomatically;
        }

        private static CreateIndexesResult unmarshall(BsonDocument replyDoc) throws TypesMismatchException, NoSuchKeyException {
            boolean createdCollecion = BsonReaderTool.getBooleanOrNumeric(replyDoc, CREATED_COLLECTION_FIELD, false);
            int indexesBefore = BsonReaderTool.getInteger(replyDoc, INDEX_BEFORE_FIELD);
            int indexesAfter = BsonReaderTool.getInteger(replyDoc, INDEX_AFTER_FIELD);
            String note = BsonReaderTool.getString(replyDoc, NOTE_FIELD, null);
            return new CreateIndexesResult(indexesBefore, indexesAfter, note, createdCollecion);
        }

        private BsonDocument marshall() {
            BsonDocumentBuilder builder = new BsonDocumentBuilder();
            if (createdCollectionAutomatically) {
                builder.append(CREATED_COLLECTION_FIELD, true);
            }
            builder.append(INDEX_BEFORE_FIELD, numIndexesBefore)
                    .append(INDEX_AFTER_FIELD, numIndexesAfter);
            if (note != null) {
                builder.append(NOTE_FIELD, note);
            }
            return builder.build();
        }


    }
}
