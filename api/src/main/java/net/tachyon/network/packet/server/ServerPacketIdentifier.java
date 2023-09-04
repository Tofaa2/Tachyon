package net.tachyon.network.packet.server;

public class ServerPacketIdentifier {

    public static final int LOGIN_DISCONNECT = 0x00;
    public static final int LOGIN_ENCRYPTION_REQUEST = 0x01;
    public static final int LOGIN_SUCCESS = 0x02;
    public static final int LOGIN_SET_COMPRESSION = 0x03;
    public static final int LOGIN_PLUGIN_REQUEST = 0x04;

    public static final int KEEP_ALIVE = 0x00;
    public static final int JOIN_GAME = 0x01;
    public static final int CHAT_MESSAGE = 0x02;
    public static final int TIME_UPDATE = 0x03;
    public static final int ENTITY_EQUIPMENT = 0x04;
    public static final int SPAWN_POSITION = 0x05;
    public static final int UPDATE_HEALTH = 0x06;
    public static final int RESPAWN = 0x07;
    public static final int PLAYER_POSITION_AND_LOOK = 0x08;
    public static final int HELD_ITEM_CHANGE = 0x09;
    public static final int USE_BED = 0x0A;
    public static final int ENTITY_ANIMATION = 0x0B;
    public static final int SPAWN_PLAYER = 0x0C;
    public static final int COLLECT_ITEM = 0x0D;
    public static final int SPAWN_OBJECT = 0x0E;
    public static final int SPAWN_MOB = 0x0F;
    public static final int SPAWN_PAINTING = 0x10;
    public static final int SPAWN_EXPERIENCE_ORB = 0x11;
    public static final int ENTITY_VELOCITY = 0x12;
    public static final int DESTROY_ENTITIES = 0x13;
    public static final int ENTITY = 0x14;
    public static final int ENTITY_RELATIVE_MOVE = 0x15;
    public static final int ENTITY_LOOK = 0x16;
    public static final int ENTITY_LOOK_AND_RELATIVE_MOVE = 0x17;
    public static final int ENTITY_TELEPORT = 0x18;
    public static final int ENTITY_HEAD_LOOK = 0x19;
    public static final int ENTITY_STATUS = 0x1A;
    public static final int ATTACH_ENTITY = 0x1B;
    public static final int ENTITY_METADATA = 0x1C;
    public static final int ENTITY_EFFECT = 0x1D;
    public static final int REMOVE_ENTITY_EFFECT = 0x1E;
    public static final int SET_EXPERIENCE = 0x1F;
    public static final int ENTITY_PROPERTIES = 0x20;
    public static final int CHUNK_DATA = 0x21;
    public static final int MULTI_BLOCK_CHANGE = 0x22;
    public static final int BLOCK_CHANGE = 0x23;
    public static final int BLOCK_ACTION = 0x24;
    public static final int BLOCK_BREAK_ANIMATION = 0x25;
    public static final int EXPLOSION = 0x27;
    public static final int EFFECT = 0x28;
    public static final int SOUND_EFFECT = 0x29;
    public static final int PARTICLE = 0x2A;
    public static final int CHANGE_GAME_STATE = 0x2B;
    public static final int OPEN_WINDOW = 0x2D;
    public static final int CLOSE_WINDOW = 0x2E;
    public static final int SET_SLOT = 0x2F;
    public static final int WINDOW_ITEMS = 0x30;
    public static final int WINDOW_PROPERTY = 0x31;
    public static final int WINDOW_CONFIRMATION = 0x32;
    // TODO(koesie10): Update sign packet
    public static final int MAP_DATA = 0x34;
    public static final int UPDATE_BLOCK_ENTITY = 0x35;
    public static final int OPEN_SIGN_EDITOR = 0x36;
    public static final int STATISTICS = 0x37;
    public static final int PLAYER_LIST_ITEM = 0x38;
    public static final int PLAYER_ABILITIES = 0x39;
    public static final int TAB_COMPLETE = 0x3A;
    public static final int SCOREBOARD_OBJECTIVE = 0x3B;
    public static final int UPDATE_SCORE = 0x3C;
    public static final int DISPLAY_SCOREBOARD = 0x3D;
    public static final int TEAMS = 0x3E;
    public static final int PLUGIN_MESSAGE = 0x3F;
    public static final int DISCONNECT = 0x40;
    public static final int SERVER_DIFFICULTY = 0x41;
    public static final int COMBAT_EVENT = 0x42;
    public static final int CAMERA = 0x43;
    public static final int WORLD_BORDER = 0x44;
    public static final int TITLE = 0x45;
    // Set Compression packet is broken
    public static final int PLAYER_LIST_HEADER_AND_FOOTER = 0x47;
    public static final int RESOURCE_PACK_SEND = 0x48;

}
