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

package de.d3adspace.seraphim;

import de.d3adspace.seraphim.cache.Cache;
import de.d3adspace.seraphim.cache.CacheEntry;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Felix 'SasukeKawaii' Klauke
 */
public class SeraphimCache<KeyType, ValueType> implements Cache<KeyType, ValueType> {
	
	private final Map<KeyType, CacheEntry<ValueType>> cache;
	
	SeraphimCache() {
		this.cache = new ConcurrentHashMap<>();
	}
	
	@Override
	public void put(KeyType key, ValueType value) {
		this.put(key, value, -1);
	}
	
	@Override
	public void put(KeyType key, ValueType value, long timeToLive) {
		CacheEntry<ValueType> cacheEntry = new CacheEntry<>(value, timeToLive);
		
		this.cache.put(key, cacheEntry);
	}
	
	@Override
	public ValueType get(KeyType key) {
		CacheEntry<ValueType> cacheEntry = this.cache.get(key);
		
		if (cacheEntry == null) {
			return null;
		}
		
		long expire = cacheEntry.getExpire();
		
		if (expire != -1) {
			long entrance = cacheEntry.getEntrance();
			
			if ((System.currentTimeMillis() - entrance) > expire) {
				this.invalidate(key);
				return null;
			}
		}
		
		return cacheEntry.getValue();
	}
	
	@Override
	public boolean isPresent(KeyType key) {
		return this.get(key) != null;
	}
	
	@Override
	public void invalidate(KeyType key) {
		this.cache.remove(key);
	}
}
