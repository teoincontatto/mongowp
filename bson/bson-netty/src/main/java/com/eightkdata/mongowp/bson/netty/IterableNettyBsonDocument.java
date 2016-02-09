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

package com.eightkdata.mongowp.bson.netty;

import com.eightkdata.mongowp.bson.BsonDocument;
import com.eightkdata.mongowp.bson.abst.AbstractIterableBasedBsonDocument;
import com.eightkdata.mongowp.bson.impl.ListBasedBsonDocument;
import com.eightkdata.mongowp.bson.netty.annotations.Loose;
import com.eightkdata.mongowp.bson.netty.annotations.ModifiesIndexes;
import com.eightkdata.mongowp.bson.netty.annotations.Tight;
import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.netty.buffer.ByteBuf;
import java.io.ObjectStreamException;
import java.util.NoSuchElementException;

/**
 *
 */
@SuppressFBWarnings(value = {"SE_BAD_FIELD", "SE_NO_SERIALVERSIONID"},
        justification = "writeReplace is used")
public class IterableNettyBsonDocument extends AbstractIterableBasedBsonDocument {

    @Loose
    private final ByteBuf _byteBuf;
    private final OffHeapNettyBsonLowLevelReader offHeapReader;

    /**
     *
     * @param byteBuf it must include the final 0x00
     * @param offHeapReader
     */
    public IterableNettyBsonDocument(
            @Loose @ModifiesIndexes ByteBuf byteBuf,
            OffHeapNettyBsonLowLevelReader offHeapReader) {
        this._byteBuf = byteBuf;
        this.offHeapReader = offHeapReader;
    }

    @Override
    public UnmodifiableIterator<Entry<?>> iterator() {
        return new MyIterator(_byteBuf.slice(), offHeapReader);
    }

    private Object writeReplace() throws ObjectStreamException {
        return new ListBasedBsonDocument(Lists.newArrayList(this));
    }

    private static class MyIterator extends UnmodifiableIterator<BsonDocument.Entry<?>> {

        @Tight
        private final ByteBuf byteBuf;
        private final NettyBsonLowLevelReader reader;

        MyIterator(ByteBuf byteBuf, NettyBsonLowLevelReader reader) {
            this.byteBuf = byteBuf;
            this.reader = reader;
        }

        @Override
        public boolean hasNext() {
            return reader.hasNext(byteBuf);
        }

        @Override
        public BsonDocument.Entry<?> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            try {
                return reader.readDocumentEntry(byteBuf);
            } catch (NettyBsonReaderException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

}
