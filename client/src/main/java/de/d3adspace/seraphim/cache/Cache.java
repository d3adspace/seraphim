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

package de.d3adspace.seraphim.cache;

import java.util.function.Consumer;

/**
 * The basic interface for all caches.
 *
 * @author Felix 'SasukeKawaii' Klauke
 */
public interface Cache<KeyType, ValueType> {
	
	/**
	 * Store an element in the cache with an unlimited time to live.
	 *
	 * @param key The key.
	 * @param value The value.
	 */
	void put(KeyType key, ValueType value);
	
	/**
	 * Store an element in the cache with the given time to live.
	 *
	 * @param key The key.
	 * @param value The value.
	 * @param timeToLive The ttl.
	 */
	void put(KeyType key, ValueType value, long timeToLive);
	
	/**
	 * Retrieve an element from the cache or null if it isnt present
	 *
	 * @param key The key.
	 * @return The value.
	 */
	ValueType get(KeyType key);
	
	/**
	 * Get an element from the cache and let it be consumed by the consumer.
	 *
	 * @param key The key.
	 * @param consumer The consumer.
	 */
	void get(KeyType key, Consumer<ValueType> consumer);
	
	/**
	 * Check if there is a value for the given key.
	 *
	 * @param key The key.
	 * @return If there is a value for the key.
	 */
	boolean isPresent(KeyType key);
	
	/**
	 * Remove an element from the cache.
	 *
	 * @param key The key.
	 */
	void invalidate(KeyType key);
	
	/**
	 * Removes all elements from the cache.
	 */
	void invalidateAll();
}
