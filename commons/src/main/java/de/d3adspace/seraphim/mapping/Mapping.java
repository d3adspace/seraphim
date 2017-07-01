/*
 * Copyright (c) 2017 D3adspace
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

package de.d3adspace.seraphim.mapping;

import de.d3adspace.skylla.commons.buffer.SkyllaBuffer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Mapping layer of seraphim.
 *
 * @author Felix 'SasukeKawaii' Klauke
 */
public class Mapping {
	
	/**
	 * Write an object directly as an bytearray to the buffer.
	 *
	 * TODO: Implement logic into Skylla
	 *
	 * @param skyllaBuffer The buffer.
	 * @param key The object to write.
	 */
	public void write(SkyllaBuffer skyllaBuffer, Object key) {
		try {
			byte[] bytes = serialize(key);
			skyllaBuffer.writeInt(bytes.length);
			skyllaBuffer.writeBytes(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Read an object from the next byte array.
	 *
	 * TODO: Implement logic into Skylla
	 *
	 * @param skyllaBuffer The buffer.
	 * @return the deserialized value.
	 */
	public Object read(SkyllaBuffer skyllaBuffer) {
		int length = skyllaBuffer.readInt();
		byte[] bytes = new byte[length];
		skyllaBuffer.readBytes(bytes);
		
		try {
			return deserialize(bytes);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Serialize an object to a byte array.
	 *
	 * @param obj The object to serialize.
	 *
	 * @return The byte array.
	 *
	 * @throws IOException on serializing failure
	 */
	private byte[] serialize(Object obj) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(obj);
		return out.toByteArray();
	}
	
	/**
	 * Deserialize an Object froma bytestream
	 *
	 * @param data The data to deserialze from.
	 *
	 * @return The object.
	 *
	 * @throws IOException on deserialization failure.
	 * @throws ClassNotFoundException If there is no serialized class.
	 */
	private Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream is = new ObjectInputStream(in);
		return is.readObject();
	}
}
