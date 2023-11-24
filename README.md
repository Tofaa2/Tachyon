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

## Settings Reference
| Setting | Description                                                                                               |
| --- |-----------------------------------------------------------------------------------------------------------|
| brand | The brand of the server. This is displayed in the server list                                             |
| host | The host and port the server will bind to                                                                 |
| online-mode | Whether or not the server will authenticate players with Mojang                                           |
| target-tps | The target TPS the server will try to maintain                                                            |
| max-players | The maximum amount of players that can be on the server at once                                           |
| motd | The message of the day. This is displayed in the server list                                              |
| difficulty | The difficulty of the server, this doesnt affect anything at the moment besides simply showing the client |
| chunk-view-distance | The amount of chunks the server will send to the client                                                   |
| entity-view-distance | The amount of entities the server will send to the client                                                 |
| packet.compression-threshold | The amount of bytes a packet must be before it is compressed and sent to the client. -1 to disable |
| packet.rate-limit-limit | The amount of packets that can be sent to a client per second                                              |
| packet.size-limit | The amount of bytes that can be sent to a client per second                                                |
| packet.caching | Whether or not the server will cache packets                                                               |
| packet.grouping | Whether or not the server will group packets                                                               |
| thread.netty-thread-count | The amount of netty threads to use. -1 to use the amount of processors                                    |
| thread.scheduler-thread-count | The amount of scheduler threads to use. -1 to use the amount of processors                            |
| thread.block-batch-thread-count | The amount of block batch threads to use. -1 to use the amount of processors                        |
| thread.chunk-saving-thread-count | The amount of chunk saving threads to use. -1 to use the amount of processors                      |
| proxy.bungee | Whether or not the server is behind a BungeeCord proxy                                                      |
| proxy.velocity | Whether or not the server is behind a Velocity proxy                                                        |

## Building
Tachyon uses Gradle as its build system. To build Tachyon, its made from two subprojects, the `api` and the `server` projects.
To build the full server, run the `shadowJar` project for the `server` project. This will build the server and its dependencies into a single jar file.
Keep in mind Tachyon depends on JAVA 17 to function.

## Credits

- [Minestom](https://minestom.net/) for the inspiration and some (a lot) of the code written in this project. Best server software out there for custom development
- [PacketEvents](https:://github.com/retrooper/packetevents) for some inspiration on how to handle packets and wrappers