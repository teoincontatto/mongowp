
package com.eightkdata.mongowp.mongoserver.api.commands;

import com.eightkdata.mongowp.mongoserver.callback.MessageReplier;

/**
 *
 */
public interface Reply {

    public void reply(MessageReplier replier);
}