This branch is a WIP implementation of the complete Minecraft 1.21 protocol in c# as a server library

The goal of this project, (and mostly the project itself) heavily inspired by [Minestom](https://minestom.net/), which is to be a complete Minecraft server side game engine.

The project is currently in a very early stage, and is for the most part, unusable. However, the project is being actively developed and will be updated frequently.
The project structure:
```plaintext
Root:
    Tachyon.sln The solution file
    Server: The actual library
    Server.Impl: A basic test implementation of the server
    Tools: Various basic tools, scripts etc that are used to make development easier, for example data driven content code generation
```

Packet naming scheme
```plaintext
    [SendingSide][ConnectionState][PacketName]
    SendingSide: Client ( the client sent the packet) or Server (the server sent the packet)
    Ex: ServerConfigurationDisconnectPacket  | ClientStatusPingRequestpacket
```