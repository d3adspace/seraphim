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

package de.d3adspace.seraphim.server.cache;

import de.d3adspace.seraphim.cache.CacheEntry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Server Side CacheImpl providing basic CRUD Operations.
 *
 * @author Felix 'SasukeKawaii' Klauke, Nathalie0hneHerz
 */
public class ServerCache {

    /**
     * Underlying Map
     */
    private final Map<Object, CacheEntry> cache;

    /**
     * Create a new cache
     */
    public ServerCache() {
        this.cache = new ConcurrentHashMap<>();
    }

    /**
     * Retrieve an object from the cache.
     *
     * @param key The key.
     * @return The value.
     */
    public Object get(Object key) {
        CacheEntry cacheEntry = this.cache.get(key);

        if (cacheEntry == null) {
            return null;
        }

        // Check expire
        long expire = cacheEntry.getExpire();

        if (expire != -1) {
            long entrance = cacheEntry.getEntrance();

            if ((System.currentTimeMillis() - entrance) > expire) {
                this.cache.remove(key);
                return null;
            }
        }

        return cacheEntry.getValue();
    }

    /**
     * Remove an object from the cache
     *
     * @param key The key.
     */
    public void remove(Object key) {
        this.cache.remove(key);
    }

    /**
     * Add a new object to the cache.
     *
     * @param key        The key.
     * @param cacheEntry The entry in the cache.
     */
    public void put(Object key, CacheEntry cacheEntry) {
        this.cache.put(key, cacheEntry);
    }

    /**
     * Removes all elements from the cache.
     */
    public void invalidateAll() {
        this.cache.clear();
    }
}
