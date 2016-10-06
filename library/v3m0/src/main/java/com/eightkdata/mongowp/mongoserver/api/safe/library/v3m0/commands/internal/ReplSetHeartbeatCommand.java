package com.eightkdata.mongowp.mongoserver.api.safe.library.v3m0.commands.internal;

import com.eightkdata.mongowp.bson.BsonDocument;
import com.eightkdata.mongowp.exceptions.*;
import com.eightkdata.mongowp.fields.*;
import com.eightkdata.mongowp.mongoserver.api.safe.library.v3m0.commands.internal.ReplSetHeartbeatCommand.ReplSetHeartbeatArgument;
import com.eightkdata.mongowp.mongoserver.api.safe.library.v3m0.commands.internal.ReplSetHeartbeatReply;
import com.eightkdata.mongowp.mongoserver.api.safe.library.v3m0.pojos.ReplSetProtocolVersion;
import com.eightkdata.mongowp.server.api.impl.AbstractCommand;
import com.eightkdata.mongowp.utils.BsonDocumentBuilder;
import com.eightkdata.mongowp.utils.BsonReaderTool;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.google.common.net.HostAndPort;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 */
public class ReplSetHeartbeatCommand extends AbstractCommand<ReplSetHeartbeatArgument, ReplSetHeartbeatReply>{
    private static final StringField COMMAND_FIELD = new StringField("replSetHeartbeat");
	
    public static final ReplSetHeartbeatCommand INSTANCE = new ReplSetHeartbeatCommand();

    private ReplSetHeartbeatCommand() {
        super(COMMAND_FIELD.getFieldName());
    }

    @Override
    public Class<? extends ReplSetHeartbeatArgument> getArgClass() {
        return ReplSetHeartbeatArgument.class;
    }

    @Override
    public ReplSetHeartbeatArgument unmarshallArg(BsonDocument requestDoc)
            throws TypesMismatchException, NoSuchKeyException, BadValueException {
        return ReplSetHeartbeatArgument.unmarshall(requestDoc);
    }

    @Override
    public BsonDocument marshallArg(ReplSetHeartbeatArgument request) {
        return request.marshall();
    }

    @Override
    public Class<? extends ReplSetHeartbeatReply> getResultClass() {
        return ReplSetHeartbeatReply.class;
    }

    @Override
    public BsonDocument marshallResult(ReplSetHeartbeatReply reply) {
        //TODO(gortiz): Right now the commands API does not allow to specify
        //a context, so it is impossible to know if we are using replication
        //v1 or v0. V0 is used by default
        return ReplSetHeartbeatReplyMarshaller.marshall(reply, false);
    }

    @Override
    public ReplSetHeartbeatReply unmarshallResult(BsonDocument replyDoc) 
            throws NoSuchKeyException, TypesMismatchException,
            FailedToParseException, MongoException {
        return ReplSetHeartbeatReplyMarshaller.unmarshall(replyDoc);
    }

    public static class ReplSetHeartbeatArgument {

        private static final BooleanField CHECK_EMPTY_FIELD_NAME = new BooleanField("checkEmpty");
        private static final LongField PROTOCOL_VERSION_FIELD = new LongField("pv");
        private static final LongField CONFIG_VERSION_FIELD = new LongField("v");
        private static final LongField SENDER_ID_FIELD = new LongField("fromId");
        private static final HostAndPortField SENDER_HOST_FIELD = new HostAndPortField("from");

        private static final Set<String> VALID_FIELD_NAMES = Sets.newHashSet(
                CHECK_EMPTY_FIELD_NAME.getFieldName(),
                PROTOCOL_VERSION_FIELD.getFieldName(),
                CONFIG_VERSION_FIELD.getFieldName(),
                SENDER_ID_FIELD.getFieldName(),
                COMMAND_FIELD.getFieldName(),
                SENDER_HOST_FIELD.getFieldName()
        );

        private final @Nonnull ReplSetProtocolVersion protocolVersion;
        private final long configVersion;
        private final @Nullable Integer senderId;
        private final String setName;
        private final @Nullable HostAndPort senderHost;
        private final boolean checkEmpty;

        public ReplSetHeartbeatArgument(
                boolean checkEmpty,
                @Nonnull ReplSetProtocolVersion protocolVersion,
                long configVersion,
                @Nullable Integer senderId,
                String setName,
                @Nullable HostAndPort senderHost) {
            this.checkEmpty = checkEmpty;
            this.protocolVersion = protocolVersion;
            this.configVersion = configVersion;
            this.senderId = senderId;
            this.setName = setName;
            this.senderHost = senderHost;
        }

        /**
         *
         * @return the protocol version the sender is using
         */
        @Nonnull
        public ReplSetProtocolVersion getProtocolVersion() {
            return protocolVersion;
        }

        /**
         *
         * @return the replica set configuration version the sender is using
         */
        public long getConfigVersion() {
            return configVersion;
        }

        /**
         *
         * @return the id of the sender on the replica set configuration
         */
        @Nullable
        public Integer getSenderId() {
            return senderId;
        }

        /**
         *
         * @return the replica set name of the sender
         */
        public String getSetName() {
            return setName;
        }

        /**
         *
         * @return the host and port of the sender node
         */
        @Nullable
        public HostAndPort getSenderHost() {
            return senderHost;
        }

        private BsonDocument marshall() {
            BsonDocumentBuilder result = new BsonDocumentBuilder();

            result.append(COMMAND_FIELD, setName);
            result.append(PROTOCOL_VERSION_FIELD, protocolVersion.getVersionId());
            result.append(CONFIG_VERSION_FIELD, configVersion);
            if (senderHost == null) {
                result.append(SENDER_HOST_FIELD, "");
            }
            else {
                result.append(SENDER_HOST_FIELD, senderHost);
            }

            if (senderId != null) {
                result.append(SENDER_ID_FIELD, senderId.longValue());
            }
            if (checkEmpty) {
                result.append(CHECK_EMPTY_FIELD_NAME, true);
            }
            return result.build();
        }

        /**
         *
         * @param bson
         * @param command
         * @return
         * @throws MongoException
         */
        private static ReplSetHeartbeatArgument unmarshall(BsonDocument bson) 
                throws BadValueException, TypesMismatchException, NoSuchKeyException {

            BsonReaderTool.checkOnlyHasFields("ReplSetHeartbeatArgs", bson, VALID_FIELD_NAMES);

            boolean checkEmpty = BsonReaderTool.getBoolean(bson, CHECK_EMPTY_FIELD_NAME, false);

            ReplSetProtocolVersion protocolVersion = ReplSetProtocolVersion.fromVersionId(
                    BsonReaderTool.getLong(bson, PROTOCOL_VERSION_FIELD)
            );

            long configVersion = BsonReaderTool.getLong(bson, CONFIG_VERSION_FIELD);

            Integer senderId;
            long senderIdLong = BsonReaderTool.getLong(bson, SENDER_ID_FIELD, -1);
            assert senderIdLong < Integer.MAX_VALUE;
            senderId = senderIdLong == -1 ? null : (int) senderIdLong;

            String setName = BsonReaderTool.getString(bson, COMMAND_FIELD);

            HostAndPort senderHost = BsonReaderTool.getHostAndPort(bson, SENDER_HOST_FIELD, null);
            
            return new ReplSetHeartbeatArgument(checkEmpty, protocolVersion, configVersion,
                    senderId, setName, senderHost);
        }

        public static class Builder {
            private @Nonnull ReplSetProtocolVersion protocolVersion;
            private long configVersion;
            private @Nullable Integer senderId;
            private String setName;
            private @Nullable HostAndPort senderHost;
            private boolean checkEmpty;
            private boolean built = false;

            public Builder(@Nonnull ReplSetProtocolVersion protocolVersion) {
                this.protocolVersion = protocolVersion;
            }

            public Builder setProtocolVersion(@Nonnull ReplSetProtocolVersion protocolVersion) {
                Preconditions.checkState(!built);
                this.protocolVersion = protocolVersion;
                return this;
            }

            public Builder setConfigVersion(long configVersion) {
                Preconditions.checkState(!built);
                this.configVersion = configVersion;
                return this;
            }

            public Builder setSenderId(@Nullable Integer senderId) {
                Preconditions.checkState(!built);
                this.senderId = senderId;
                return this;
            }

            public Builder setSetName(String setName) {
                Preconditions.checkState(!built);
                this.setName = setName;
                return this;
            }

            public Builder setSenderHost(@Nullable HostAndPort senderHost) {
                Preconditions.checkState(!built);
                this.senderHost = senderHost;
                return this;
            }

            public Builder setCheckEmpty(boolean checkEmpty) {
                Preconditions.checkState(!built);
                this.checkEmpty = checkEmpty;
                return this;
            }

            public ReplSetHeartbeatArgument build() {
                Preconditions.checkState(!built);
                built = true;
                return new ReplSetHeartbeatArgument(checkEmpty, protocolVersion, configVersion, senderId, setName, senderHost);
            }
        }

    }

}
