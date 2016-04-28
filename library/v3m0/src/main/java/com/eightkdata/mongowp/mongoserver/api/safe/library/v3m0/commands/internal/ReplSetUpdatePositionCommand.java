
package com.eightkdata.mongowp.mongoserver.api.safe.library.v3m0.commands.internal;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.eightkdata.mongowp.OpTime;
import com.eightkdata.mongowp.bson.BsonArray;
import com.eightkdata.mongowp.bson.BsonDocument;
import com.eightkdata.mongowp.bson.BsonObjectId;
import com.eightkdata.mongowp.bson.BsonValue;
import com.eightkdata.mongowp.exceptions.BadValueException;
import com.eightkdata.mongowp.exceptions.NoSuchKeyException;
import com.eightkdata.mongowp.exceptions.TypesMismatchException;
import com.eightkdata.mongowp.fields.ArrayField;
import com.eightkdata.mongowp.fields.DocField;
import com.eightkdata.mongowp.fields.LongField;
import com.eightkdata.mongowp.fields.ObjectIdField;
import com.eightkdata.mongowp.fields.TimestampField;
import com.eightkdata.mongowp.mongoserver.api.safe.library.v3m0.commands.internal.ReplSetUpdatePositionCommand.ReplSetUpdatePositionArgument;
import com.eightkdata.mongowp.mongoserver.api.safe.library.v3m0.pojos.MemberConfig;
import com.eightkdata.mongowp.server.api.impl.AbstractCommand;
import com.eightkdata.mongowp.server.api.tools.Empty;
import com.eightkdata.mongowp.utils.BsonArrayBuilder;
import com.eightkdata.mongowp.utils.BsonDocumentBuilder;
import com.eightkdata.mongowp.utils.BsonReaderTool;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

/**
 *
 */
public class ReplSetUpdatePositionCommand extends AbstractCommand<ReplSetUpdatePositionArgument, Empty> {

    public static final ReplSetUpdatePositionCommand INSTANCE = new ReplSetUpdatePositionCommand();

    private static final String COMMAND_FIELD_NAME = "replSetUpdatePosition";

    private ReplSetUpdatePositionCommand() {
        super(COMMAND_FIELD_NAME);
    }

    @Override
    public Class<? extends ReplSetUpdatePositionArgument> getArgClass() {
        return ReplSetUpdatePositionArgument.class;
    }

    @Override
    public ReplSetUpdatePositionArgument unmarshallArg(BsonDocument requestDoc)
            throws TypesMismatchException, NoSuchKeyException, BadValueException {
    	return ReplSetUpdatePositionArgument.unmarshall(requestDoc);
    }

    @Override
    public BsonDocument marshallArg(ReplSetUpdatePositionArgument request) {
        return request.marshall();
    }

    @Override
    public Class<? extends Empty> getResultClass() {
        return Empty.class;
    }

    @Override
    public BsonDocument marshallResult(Empty reply) {
        return null;
    }

    @Override
    public Empty unmarshallResult(BsonDocument resultDoc) {
        return Empty.getInstance();
    }

    public static class ReplSetUpdatePositionArgument {
        private static final ArrayField UPDATE_ARRAY_FIELD = new ArrayField("optimes");

        private static final ImmutableSet<String> VALID_FIELD_NAMES = ImmutableSet.of(
        		COMMAND_FIELD_NAME, UPDATE_ARRAY_FIELD.getFieldName()
        );

        private final ImmutableList<UpdateInfo> updates;

        public ReplSetUpdatePositionArgument(
                @Nonnull List<UpdateInfo> updates) {
            this.updates = ImmutableList.copyOf(updates);
        }

        @Nonnull
        public ImmutableList<UpdateInfo> getUpdates() throws IllegalStateException {
            return updates;
        }
        
        private static ReplSetUpdatePositionArgument unmarshall(BsonDocument doc) throws TypesMismatchException, NoSuchKeyException, BadValueException {
            if (doc.containsKey("handshake")) {
                throw new IllegalArgumentException("A handshake command wrapped "
                        + "inside a replSetUpdatePosition has been recived, but it "
                        + "has been treated by the replSetUpdatePosition command");
            }
            BsonReaderTool.checkOnlyHasFields("UpdateInfoArgs", doc, VALID_FIELD_NAMES);

            ImmutableList.Builder<UpdateInfo> updateInfo = ImmutableList.builder();
            BsonArray updateArray = BsonReaderTool.getArray(doc, UPDATE_ARRAY_FIELD);
            for (BsonValue<?> element : updateArray) {
                updateInfo.add(new UpdateInfo(element.asDocument()));
            }

            return new ReplSetUpdatePositionArgument(updateInfo.build());
        }

        private BsonDocument marshall() {
            BsonDocumentBuilder updateInfo = new BsonDocumentBuilder();
            BsonArrayBuilder updatesBuilder = new BsonArrayBuilder(updates.size());
            for (UpdateInfo update : updates) {
            	updatesBuilder.add(update.marshall());
            }
            updateInfo.append(UPDATE_ARRAY_FIELD, updatesBuilder.build());
            return updateInfo.build();
        }

        public static class UpdateInfo {
            private static final ObjectIdField MEMBER_RID_FIELD = new ObjectIdField("_id");
            private static final DocField MEMBER_CONFIG_FIELD = new DocField("config");
            private static final TimestampField OP_TIME_FIELD = new TimestampField("optime");
            private static final LongField MEMBER_ID_FIELD = new LongField("memberId");
            private static final LongField CONFIG_VERSION_FIELD = new LongField("cfgver");

            private static final ImmutableSet<String> VALID_FIELD_NAMES = ImmutableSet.of(
            		MEMBER_RID_FIELD.getFieldName(), MEMBER_CONFIG_FIELD.getFieldName(),
            		OP_TIME_FIELD.getFieldName(), MEMBER_ID_FIELD.getFieldName(),
                    CONFIG_VERSION_FIELD.getFieldName()
            );
            
            private final BsonObjectId rid;
            private final OpTime ts;
            private final long cfgVer;
            private final long memberId;
            @Nullable
            private final MemberConfig config;

            public UpdateInfo(BsonObjectId rid, OpTime ts, long cfgVer, long memberId, MemberConfig config) {
                this.rid = rid;
                this.ts = ts;
                this.cfgVer = cfgVer;
                this.memberId = memberId;
                this.config = config;
            }

            public UpdateInfo(BsonDocument doc) throws BadValueException, TypesMismatchException, NoSuchKeyException {
                BsonReaderTool.checkOnlyHasFields("UpdateInfoArgs", doc, VALID_FIELD_NAMES);

                ts = BsonReaderTool.getOpTime(doc, OP_TIME_FIELD);
                cfgVer = BsonReaderTool.getLong(doc, CONFIG_VERSION_FIELD, -1);
                rid = BsonReaderTool.getObjectId(doc, MEMBER_RID_FIELD, null);
                memberId = BsonReaderTool.getLong(doc, MEMBER_ID_FIELD, -1);
                BsonDocument configDoc = BsonReaderTool.getDocument(doc, MEMBER_CONFIG_FIELD, null);
                if (configDoc != null) {
                    this.config = MemberConfig.fromDocument(configDoc);
                }
                else {
                    this.config = null;
                }
            }

            public BsonObjectId getRid() {
                return rid;
            }

            public OpTime getTs() {
                return ts;
            }

            public long getCfgVer() {
                return cfgVer;
            }

            public long getMemberId() {
                return memberId;
            }

            @Nullable
            public MemberConfig getConfig() {
                return config;
            }
            
            private BsonDocument marshall() {
                BsonDocumentBuilder updateInfo = new BsonDocumentBuilder();
                updateInfo.append(MEMBER_RID_FIELD, rid);
                updateInfo.append(OP_TIME_FIELD, ts);
                updateInfo.append(MEMBER_ID_FIELD, memberId);
                updateInfo.append(CONFIG_VERSION_FIELD, cfgVer);
                if (config != null) {
                	updateInfo.append(MEMBER_CONFIG_FIELD, config.toBSON());
                }
                return updateInfo.build();
            }
        }
    }
}
