/*
 * Copyright (c) 2017 - 2019 D3adspace
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.d3adspace.seraphim.protocol.packet;

import de.d3adspace.skylla.commons.buffer.SkyllaBuffer;
import de.d3adspace.skylla.commons.protocol.packet.SkyllaPacket;
import de.d3adspace.skylla.commons.protocol.packet.SkyllaPacketMeta;

/**
 * Response for getting values from a server side cache.
 *
 * @author Felix 'SasukeKawaii' Klauke
 */
@SkyllaPacketMeta(id = 4)
public class PacketGetResponse extends SkyllaPacket {

  /**
   * Id of the client side callback
   */
  private int callbackId;

  /**
   * The value to cache
   */
  private Object value;

  /**
   * Create a new PacketGet Response.
   *
   * @param callbackId The callbackId.
   * @param value The value.
   */
  public PacketGetResponse(int callbackId, Object value) {
    this.callbackId = callbackId;
    this.value = value;
  }

  /**
   * Packet constructor
   */
  public PacketGetResponse() {
  }

  /**
   * Get the value to cache.
   *
   * @return The value.
   */
  public Object getValue() {
    return value;
  }

  /**
   * Get the callback id.
   *
   * @return The callback id.
   */
  public int getCallbackId() {
    return callbackId;
  }

  @Override
  public void write(SkyllaBuffer skyllaBuffer) {
    skyllaBuffer.writeInt(callbackId);
    skyllaBuffer.writeObject(value);
  }

  @Override
  public void read(SkyllaBuffer skyllaBuffer) {
    callbackId = skyllaBuffer.readInt();
    value = skyllaBuffer.readObject();
  }

  @Override
  public String toString() {
    return "PacketGetResponse{" +
        "callbackId=" + callbackId +
        ", value=" + value +
        '}';
  }
}
