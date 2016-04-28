
package com.eightkdata.mongowp.mongoserver.api.safe.library.v3m0.commands.internal;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.eightkdata.mongowp.bson.BsonDocument;
import com.eightkdata.mongowp.bson.BsonObjectId;
import com.eightkdata.mongowp.exceptions.BadValueException;
import com.eightkdata.mongowp.exceptions.NoSuchKeyException;
import com.eightkdata.mongowp.exceptions.TypesMismatchException;
import com.eightkdata.mongowp.fields.HostAndPortField;
import com.eightkdata.mongowp.fields.IntField;
import com.eightkdata.mongowp.fields.ObjectIdField;
import com.eightkdata.mongowp.fields.StringField;
import com.eightkdata.mongowp.mongoserver.api.safe.library.v3m0.commands.internal.ReplSetElectCommand.ReplSetElectArgument;
import com.eightkdata.mongowp.mongoserver.api.safe.library.v3m0.commands.internal.ReplSetElectCommand.ReplSetElectReply;
import com.eightkdata.mongowp.server.api.impl.AbstractCommand;
import com.eightkdata.mongowp.utils.BsonDocumentBuilder;
import com.eightkdata.mongowp.utils.BsonReaderTool;
import com.google.common.net.HostAndPort;

/**
 *
 */
public class ReplSetElectCommand extends AbstractCommand<ReplSetElectArgument, ReplSetElectReply>{
	public static final IntField COMMAND_FIELD = new IntField("replSetElect");
	
    public static final ReplSetElectCommand INSTANCE = new ReplSetElectCommand();

    private ReplSetElectCommand() {
        super(COMMAND_FIELD.getFieldName());
    }

    @Override
    public Class<? extends ReplSetElectArgument> getArgClass() {
        return ReplSetElectArgument.class;
    }

    @Override
    public ReplSetElectArgument unmarshallArg(BsonDocument requestDoc)
            throws TypesMismatchException, NoSuchKeyException, BadValueException {
        return ReplSetElectArgument.unmarshall(requestDoc);
    }

    @Override
    public BsonDocument marshallArg(ReplSetElectArgument request) {
        return request.marshall();
    }

    @Override
    public Class<? extends ReplSetElectReply> getResultClass() {
        return ReplSetElectReply.class;
    }

    @Override
    public BsonDocument marshallResult(ReplSetElectReply reply) {

        return new BsonDocumentBuilder(2)
                .append(ReplSetElectReply.VOTE_FIELD, reply.getVote())
                .append(ReplSetElectReply.ROUND_FIELD, reply.getRound())
                .build();
    }

    @Override
    public ReplSetElectReply unmarshallResult(BsonDocument resultDoc) 
    		throws BadValueException, TypesMismatchException, NoSuchKeyException {
        return ReplSetElectReply.unmarshall(resultDoc);
    }

    public static class ReplSetElectArgument {

        private static final StringField SET_NAME_FIELD = new StringField("set");
        private static final HostAndPortField WHO_FIELD = new HostAndPortField("who");
        private static final IntField CLIENT_ID_FIELD = new IntField("whoid");
        private static final IntField CFG_VER_FIELD = new IntField("cfgver");
        private static final ObjectIdField ROUND_FIELD = new ObjectIdField("round");

        private final @Nonnull String replSetName;
        private final @Nullable HostAndPort who;
        private final int clientId;
        private final int cfgVersion;
        private final @Nonnull BsonObjectId round;

        public ReplSetElectArgument(
                @Nonnull String setName,
                int clientId,
                int cfgVersion,
                @Nonnull BsonObjectId round) {
            this.replSetName = setName;
            this.who = null;
            this.clientId = clientId;
            this.cfgVersion = cfgVersion;
            this.round = round;
        }

        public ReplSetElectArgument(
                @Nonnull String setName,
                @Nonnull HostAndPort who,
                int clientId,
                int cfgVersion,
                @Nonnull BsonObjectId round) {
            this.replSetName = setName;
            this.who = who;
            this.clientId = clientId;
            this.cfgVersion = cfgVersion;
            this.round = round;
        }

        /**
         * The name of the set
         * @return 
         */
        @Nonnull
        public String getReplSetName() {
            return replSetName;
        }

        /**
         * @return replSet id of the member that sent the replSetFresh command
         */
        public int getClientId() {
            return clientId;
        }

        /**
         * 
         * @return replSet config version that the member who sent the command thinks it has
         */
        public int getCfgVersion() {
            return cfgVersion;
        }

        /**
         * 
         * @return unique ID for this election
         */
        @Nonnull
        public BsonObjectId getRound() {
            return round;
        }

        private BsonDocument marshall() {
        	BsonDocumentBuilder builder = new BsonDocumentBuilder();
        	builder.append(COMMAND_FIELD, 1);
        	builder.append(SET_NAME_FIELD, replSetName);
        	if (who != null) {
        		builder.append(WHO_FIELD, who);
        	}
        	builder.append(CLIENT_ID_FIELD, clientId);
        	builder.append(CFG_VER_FIELD, cfgVersion);
        	builder.append(ROUND_FIELD, round);
        	
        	return builder.build();
        }
        
        public static ReplSetElectArgument unmarshall(
                BsonDocument bson) throws TypesMismatchException, NoSuchKeyException {
            String setName = BsonReaderTool.getString(bson, SET_NAME_FIELD);
            int cliendId = BsonReaderTool.getInteger(bson, CLIENT_ID_FIELD);
            int cfgversion = BsonReaderTool.getNumeric(bson, CFG_VER_FIELD).intValue();
            BsonObjectId round = BsonReaderTool.getObjectId(bson, ROUND_FIELD);

            return new ReplSetElectArgument(setName, cliendId, cfgversion, round);
        }

    }
    public static class ReplSetElectReply {

        private static final IntField VOTE_FIELD = new IntField("vote");
        private static final ObjectIdField ROUND_FIELD = new ObjectIdField("round");

        private final int vote;
        private final BsonObjectId round;

        public ReplSetElectReply(int vote, BsonObjectId round) {
            this.vote = vote;
            this.round = round;
        }

        public int getVote() {
            return vote;
        }

        public BsonObjectId getRound() {
            return round;
        }
        
        private static ReplSetElectReply unmarshall(BsonDocument doc) 
        		throws BadValueException, TypesMismatchException, NoSuchKeyException {
        	if (!doc.get(VOTE_FIELD.getFieldName()).isInt32()) {
        		throw new BadValueException("wrong type vote argument in replSetElect command: " + 
        				doc.get(VOTE_FIELD.getFieldName()).getType());
        	}
        	int vote = BsonReaderTool.getInteger(doc, VOTE_FIELD);
        	BsonObjectId round = BsonReaderTool.getObjectId(doc, ROUND_FIELD);
        	
        	return new ReplSetElectReply(vote, round);
        }
    }

}
