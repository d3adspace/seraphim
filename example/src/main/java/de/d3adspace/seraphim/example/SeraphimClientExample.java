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

package de.d3adspace.seraphim.example;

import de.d3adspace.seraphim.CacheFactory;
import de.d3adspace.seraphim.cache.Cache;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * @author Felix 'SasukeKawaii' Klauke
 */
public class SeraphimClientExample {

    public static void main(String[] args) {
        final Cache<UUID, UUID> cache = CacheFactory.connectToRemoteCache("127.0.0.1", 8080);

        final UUID uniqueIdKey = UUID.randomUUID();
        UUID uniqueIdValue = UUID.randomUUID();

        cache.put(uniqueIdKey, uniqueIdValue);

        cache.get(uniqueIdKey, new Consumer<UUID>() {
            @Override
            public void accept(UUID uuid) {
                System.out.println(uuid);
            }
        });

        double fullStart = System.currentTimeMillis();

        double trys = 100000;

        final int[] fails = {0};

        for (int i = 0; i < trys; i++) {

        }

        final int[] i = {0};

        while (i[0] < trys + 1) {
            final long start = System.currentTimeMillis();

            UUID uuid = cache.get(uniqueIdKey);

            if (uuid == null) fails[0]++;

            System.out.println("Try " + i[0] + " took " + (System.currentTimeMillis() - start));

            i[0]++;
        }

        double totalTime = System.currentTimeMillis() - fullStart;
        System.out.println("Average: " + totalTime / trys);
        System.out.println("Fails: " + fails[0]);
        System.out.println("Took " + totalTime + "ms for a total of " + trys + " requests.");

		/*System.out.println("Now doing async shit.");

		final int[] times = {0};

		for (int j = 0; j < 100000; j++) {
			cache.get(uniqueIdKey, new Consumer<UUID>() {
				@Override
				public void accept(UUID uuid) {
					System.out.println("Uff: " + uuid);
					times[0]++;

					System.out.println(times[0]);
				}
			});
		}*/
    }
}
