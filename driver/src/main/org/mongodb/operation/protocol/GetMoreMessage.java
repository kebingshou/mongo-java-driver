/*
 * Copyright (c) 2008 - 2013 10gen, Inc. <http://10gen.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mongodb.operation.protocol;

import org.mongodb.connection.ChannelAwareOutputBuffer;
import org.mongodb.operation.GetMore;

public class GetMoreMessage extends RequestMessage {
    private final GetMore getMore;

    public GetMoreMessage(final String collectionName, final GetMore getMore, final MessageSettings settings) {
        super(collectionName, OpCode.OP_GETMORE, settings);
        this.getMore = getMore;
    }

    public long getCursorId() {
        return getMore.getServerCursor().getId();
    }

    @Override
    protected RequestMessage encodeMessageBody(final ChannelAwareOutputBuffer buffer, final int messageStartPosition) {
        writeGetMore(buffer);
        return null;
    }

    private void writeGetMore(final ChannelAwareOutputBuffer buffer) {
        buffer.writeInt(0);
        buffer.writeCString(getCollectionName());
        buffer.writeInt(getMore.getNumberToReturn());
        buffer.writeLong(getMore.getServerCursor().getId());
    }

}