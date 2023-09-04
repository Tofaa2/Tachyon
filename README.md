# Tachyon
 A lightweight fast multi-threaded 1.8 legacy Minecraft server implementation


## Usage
WARNING: Tachyon is not usage ready just yet. Though the server works, modifying it to your liking is not possible at the current state of the API. This is still being actively working on

Example of a Tachyon server configuration file:
```json
{
  "brand": "Tachyon",
  "host": "0.0.0.0:25565",
  "online-mode": false,
  "target-tps": 20,
  "max-players": 100,
  "motd": [
    "A tachyon server",
    "Welcome to my server"
  ],
  "difficulty": "NORMAL",
  "chunk-view-distance": 8,
  "entity-view-distance": 5,
  "packet": {
    "compression-threshold": 256,
    "rate-limit-limit": 300,
    "size-limit": 30000,
    "caching": true,
    "grouping": true
  },
  "thread": {
    "netty-thread-count": -1,
    "scheduler-thread-count": 1,
    "block-batch-thread-count": 4,
    "chunk-saving-thread-count": 4
  },
  "proxy": {
    "bungee": false,
    "velocity": false
  }
}
```

brand: Represents the server brand sent to the player, by default this is Tachyon <br/>
host: The host and port the server will bind to<br/>
online-mode: Whether or not the server will authenticate players with Mojang<br/>
target-tps: The target TPS the server will try to run at. By default this is 20 as per minecrafts set TPS<br/>
max-players: The maximum amount of players that can be on the server at once.<br/>
motd: The motd's that will be sent to the player when they ping the server. You can set multiple for Tachyon to iterate between them randomly<br/>
difficulty: The difficulty of the server. This can be set to PEACEFUL, EASY, NORMAL, or HARD. As Tachyon is a barebones server, this has literally zero effect in game, unless you decide to implement something for it.<br/>
chunk-view-distance: The amount of chunks the player can see around them. This is a radius, so a value of 8 would be 16x16 chunks around the player.<br/>
entity-view-distance: The amount of entities the player can see around them. This is a radius, so a value of 5 would be 10x10 chunks around the player.<br/>
packet: The packet settings for the server. This is a bit more advanced, and you should probably leave it alone unless you know what you're doing.<br/>
    compression-threshold: The amount of bytes a packet must be before it is compressed. By default this is 256, which is the same as vanilla.<br/>
    rate-limit-limit: The amount of packets that can be sent per Tachyon server tick.<br/>
    size-limit: The maximum allowed size for a packet from a player before their connection is terminated.<br/>
    caching: Whether or not to cache packets. This is a bit more advanced, and you should probably leave it alone unless you know what you're doing.<br/>
    grouping: Whether or not Tachyon needs to group packets. This is a performance benefit just like caching and you should probably leave it unless you know what you are doing.<br/>
thread: The thread settings for the server. This is a bit more advanced, and you should probably leave it alone unless you know what you're doing.<br/>
    netty-thread-count: The amount of threads netty will use. By default this is -1, which means it will use the amount of cores on your CPU.<br/>
    scheduler-thread-count: The amount of threads the scheduler will use. By default this is 1, which means it will use 1 thread.<br/>
    block-batch-thread-count: The amount of threads the block batcher will use. By default this is 4, which means it will use 4 threads.<br/>
    chunk-saving-thread-count: The amount of threads the chunk saver will use. By default this is 4, which means it will use 4 threads.<br/>
proxy: These are the settings to support Tachyon as a backend server for your network.<br/>
    bungee: Whether or not Tachyon should act as a backend server to a bungee proxy.<br/>
    velocity: Whether or not Tachyon should act as a backend server to a velocity proxy. If using velocity, you must also specify the "secret" value in the same configuration section.<br/>