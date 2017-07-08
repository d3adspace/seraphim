# Seraphim

A simple and lightweight key-value in memory store built on top of 
https://github.com/D3adspaceEnterprises/skylla - Seraphim uses https://github.com/D3adspaceEnterprises/hawkings for local consumer management. The intention is to provide an easy to use in memory store for little network applications. You can also take a look at https://github.com/D3adspaceEnterprises/reincarnation if youre searching for a pub sub service. 

# Installation / Usage

- Install [Maven](http://maven.apache.org/download.cgi)
- Clone this repo
- Install: ```mvn clean install```

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


package de.d3adspace.seraphim.example;

import de.d3adspace.seraphim.CacheFactory;
import de.d3adspace.seraphim.cache.Cache;
import java.util.UUID;

/**
 * @author Felix 'SasukeKawaii' Klauke
 */
public class SeraphimClientExample {
	
	public static void main(String[] args) {
		Cache<UUID, UUID> cache = CacheFactory.connectToRemoteCache("localhsost", 1337);
		
		UUID uniqueIdKey = UUID.randomUUID();
		UUID uniqueIdValue = UUID.randomUUID();
		
		cache.put(uniqueIdKey, uniqueIdValue);
		
		System.out.println(cache.get(uniqueIdKey));
		
		cache.get(uniqueIdKey, new Consumer<UUID>() {
    			@Override
    			public void accept(UUID uuid) {
    				System.out.println(uuid);
    			}
    });	
	}
}
```

