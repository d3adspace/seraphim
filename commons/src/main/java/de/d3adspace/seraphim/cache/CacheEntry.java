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

package de.d3adspace.seraphim.cache;

/**
 * Representing an object in a cache.
 *
 * @author Felix 'SasukeKawaii' Klauke
 */
public class CacheEntry<ValueType> {

    /**
     * The underlying value.
     */
    private ValueType value;

    /**
     * Time when the value was written to the cache.
     */
    private long entrance = System.currentTimeMillis();

    /**
     * Time to live.
     */
    private long expire;

    /**
     * Create a new entry.
     *
     * @param value  The value.
     * @param expire The time to live.
     */
    public CacheEntry(ValueType value, long expire) {
        this.value = value;
        this.expire = expire;
    }

    /**
     * Get the underlying value.
     *
     * @return The value.
     */
    public ValueType getValue() {
        return value;
    }

    /**
     * Update the underlying value.
     *
     * @param value The new value.
     */
    public void setValue(ValueType value) {
        this.value = value;
    }

    /**
     * Get the time to live.
     *
     * @return The ttl.
     */
    public long getExpire() {
        return expire;
    }

    /**
     * Set the time to live.
     *
     * @param expire The new time to live.
     */
    public void setExpire(long expire) {
        this.expire = expire;
    }

    /**
     * Find out when the first underlying value was written to the cache.
     *
     * @return The time.
     */
    public long getEntrance() {
        return entrance;
    }

    /**
     * Update the writing time.
     *
     * @param entrance The time.
     */
    public void setEntrance(long entrance) {
        this.entrance = entrance;
    }
}
