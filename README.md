# Seraphim

A simple and lightweight key-value in memory store built on top of 
https://github.com/D3adspaceEnterprises/skylla - Seraphim uses https://github.com/D3adspaceEnterprises/hawkings for local consumer management. The intention is to provide an easy to use in memory store for little network applications. You can also take a look at https://github.com/D3adspaceEnterprises/reincarnation if youre searching for a pub sub service. 

# Installation / Usage

- Install [Maven](http://maven.apache.org/download.cgi)
- Clone this repo
- Installh: ```mvn clean install```

**Maven dependencies**

_Client:_
```xml
<dependency>
    <groupId>de.d3adspace</groupId>
    <artifactId>seraphim-client</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

_Server:_
```xml
<dependency>
    <groupId>de.d3adspace</groupId>
    <artifactId>seraphim-server</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

_Commons:_
```xml
<dependency>
    <groupId>de.d3adspace</groupId>
    <artifactId>seraphim-commons</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

# Example:
```java
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

package de.d3adspace.seraphim.example;

import de.d3adspace.seraphim.server.SeraphimServer;

/**
 * @author Felix 'SasukeKawaii' Klauke
 */
public class SeraphimServerExample {
	
	public static void main(String[] args) {
		SeraphimServer seraphimServer = SeraphimServerFactory.createServer("localhost", 1337);
		seraphimServer.start();
	}
}


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

package de.d3adspace.seraphim.example;

import de.d3adspace.seraphim.CacheFactory;
import de.d3adspace.seraphim.cache.Cache;
import java.util.UUID;

/**
 * @author Felix 'SasukeKawaii' Klauke
 */
public class SeraphimClientExample {
	
	public static void main(String[] args) {
		Cache<UUID, UUID> cache = CacheFactory.connectToRemoteCache("localhsot", 1337);
		
		UUID uniqueIdKey = UUID.randomUUID();
		UUID uniqueIdValue = UUID.randomUUID();
		
		cache.put(uniqueIdKey, uniqueIdValue);
		
		System.out.println(cache.get(uniqueIdKey));
	}
}
```

