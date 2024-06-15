namespace Tachyon.Network.Packet;

public class ServerPacketIdentifier
{
        private static volatile int STATUS_ID = 0;
    private static volatile int LOGIN_ID =0;
    private static volatile int CONFIGURATION_ID =0;
    private static volatile int PLAY_ID = 0;

    public static readonly int STATUS_RESPONSE = NextStatusId();
    public static readonly int STATUS_PING_RESPONSE = NextStatusId();

    public static readonly int LOGIN_DISCONNECT = NextLoginId();
    public static readonly int LOGIN_ENCRYPTION_REQUEST = NextLoginId();
    public static readonly int LOGIN_SUCCESS = NextLoginId();
    public static readonly int LOGIN_SET_COMPRESSION = NextLoginId();
    public static readonly int LOGIN_PLUGIN_REQUEST = NextLoginId();
    public static readonly int LOGIN_COOKIE_REQUEST = NextLoginId();

    public static readonly int CONFIGURATION_COOKIE_REQUEST = NextConfigurationId();
    public static readonly int CONFIGURATION_PLUGIN_MESSAGE = NextConfigurationId();
    public static readonly int CONFIGURATION_DISCONNECT = NextConfigurationId();
    public static readonly int CONFIGURATION_FINISH_CONFIGURATION = NextConfigurationId();
    public static readonly int CONFIGURATION_KEEP_ALIVE = NextConfigurationId();
    public static readonly int CONFIGURATION_PING = NextConfigurationId();
    public static readonly int CONFIGURATION_RESET_CHAT = NextConfigurationId();
    public static readonly int CONFIGURATION_REGISTRY_DATA = NextConfigurationId();
    public static readonly int CONFIGURATION_RESOURCE_PACK_POP = NextConfigurationId();
    public static readonly int CONFIGURATION_RESOURCE_PACK_PUSH = NextConfigurationId();
    public static readonly int CONFIGURATION_COOKIE_STORE = NextConfigurationId();
    public static readonly int CONFIGURATION_TRANSFER = NextConfigurationId();
    public static readonly int CONFIGURATION_UPDATE_ENABLED_FEATURES = NextConfigurationId();
    public static readonly int CONFIGURATION_TAGS = NextConfigurationId();
    public static readonly int CONFIGURATION_SELECT_KNOWN_PACKS = NextConfigurationId();
    public static readonly int CONFIGURATION_CUSTOM_REPORT_DETAILS = NextConfigurationId();
    public static readonly int CONFIGURATION_SERVER_LINKS = NextConfigurationId();

    public static readonly int BUNDLE = NextPlayId();
    public static readonly int SPAWN_ENTITY = NextPlayId();
    public static readonly int SPAWN_EXPERIENCE_ORB = NextPlayId();
    public static readonly int ENTITY_ANIMATION = NextPlayId();
    public static readonly int STATISTICS = NextPlayId();
    public static readonly int ACKNOWLEDGE_BLOCK_CHANGE = NextPlayId();
    public static readonly int BLOCK_BREAK_ANIMATION = NextPlayId();
    public static readonly int BLOCK_ENTITY_DATA = NextPlayId();
    public static readonly int BLOCK_ACTION = NextPlayId();
    public static readonly int BLOCK_CHANGE = NextPlayId();
    public static readonly int BOSS_BAR = NextPlayId();
    public static readonly int SERVER_DIFFICULTY = NextPlayId();
    public static readonly int CHUNK_BATCH_FINISHED = NextPlayId();
    public static readonly int CHUNK_BATCH_START = NextPlayId();
    public static readonly int CHUNK_BIOMES = NextPlayId();
    public static readonly int CLEAR_TITLES = NextPlayId();
    public static readonly int TAB_COMPLETE = NextPlayId();
    public static readonly int DECLARE_COMMANDS = NextPlayId();
    public static readonly int CLOSE_WINDOW = NextPlayId();
    public static readonly int WINDOW_ITEMS = NextPlayId();
    public static readonly int WINDOW_PROPERTY = NextPlayId();
    public static readonly int SET_SLOT = NextPlayId();
    public static readonly int COOKIE_REQUEST = NextPlayId();
    public static readonly int SET_COOLDOWN = NextPlayId();
    public static readonly int CUSTOM_CHAT_COMPLETIONS = NextPlayId();
    public static readonly int PLUGIN_MESSAGE = NextPlayId();
    public static readonly int DAMAGE_EVENT = NextPlayId();
    public static readonly int DEBUG_SAMPLE = NextPlayId();
    public static readonly int DELETE_CHAT_MESSAGE = NextPlayId();
    public static readonly int DISCONNECT = NextPlayId();
    public static readonly int DISGUISED_CHAT = NextPlayId();
    public static readonly int ENTITY_STATUS = NextPlayId();
    public static readonly int EXPLOSION = NextPlayId();
    public static readonly int UNLOAD_CHUNK = NextPlayId();
    public static readonly int CHANGE_GAME_STATE = NextPlayId();
    public static readonly int OPEN_HORSE_WINDOW = NextPlayId();
    public static readonly int HIT_ANIMATION = NextPlayId();
    public static readonly int INITIALIZE_WORLD_BORDER = NextPlayId();
    public static readonly int KEEP_ALIVE = NextPlayId();
    public static readonly int CHUNK_DATA = NextPlayId();
    public static readonly int EFFECT = NextPlayId();
    public static readonly int PARTICLE = NextPlayId();
    public static readonly int UPDATE_LIGHT = NextPlayId();
    public static readonly int JOIN_GAME = NextPlayId();
    public static readonly int MAP_DATA = NextPlayId();
    public static readonly int TRADE_LIST = NextPlayId();
    public static readonly int ENTITY_POSITION = NextPlayId();
    public static readonly int ENTITY_POSITION_AND_ROTATION = NextPlayId();
    public static readonly int ENTITY_ROTATION = NextPlayId();
    public static readonly int VEHICLE_MOVE = NextPlayId();
    public static readonly int OPEN_BOOK = NextPlayId();
    public static readonly int OPEN_WINDOW = NextPlayId();
    public static readonly int OPEN_SIGN_EDITOR = NextPlayId();
    public static readonly int PING = NextPlayId();
    public static readonly int PING_RESPONSE = NextPlayId();
    public static readonly int CRAFT_RECIPE_RESPONSE = NextPlayId();
    public static readonly int PLAYER_ABILITIES = NextPlayId();
    public static readonly int PLAYER_CHAT = NextPlayId();
    public static readonly int END_COMBAT_EVENT = NextPlayId();
    public static readonly int ENTER_COMBAT_EVENT = NextPlayId();
    public static readonly int DEATH_COMBAT_EVENT = NextPlayId();
    public static readonly int PLAYER_INFO_REMOVE = NextPlayId();
    public static readonly int PLAYER_INFO_UPDATE = NextPlayId();
    public static readonly int FACE_PLAYER = NextPlayId();
    public static readonly int PLAYER_POSITION_AND_LOOK = NextPlayId();
    public static readonly int UNLOCK_RECIPES = NextPlayId();
    public static readonly int DESTROY_ENTITIES = NextPlayId();
    public static readonly int REMOVE_ENTITY_EFFECT = NextPlayId();
    public static readonly int RESET_SCORE = NextPlayId();
    public static readonly int RESOURCE_PACK_POP = NextPlayId();
    public static readonly int RESOURCE_PACK_PUSH = NextPlayId();
    public static readonly int RESPAWN = NextPlayId();
    public static readonly int ENTITY_HEAD_LOOK = NextPlayId();
    public static readonly int MULTI_BLOCK_CHANGE = NextPlayId();
    public static readonly int SELECT_ADVANCEMENT_TAB = NextPlayId();
    public static readonly int SERVER_DATA = NextPlayId();
    public static readonly int ACTION_BAR = NextPlayId();
    public static readonly int WORLD_BORDER_CENTER = NextPlayId();
    public static readonly int WORLD_BORDER_LERP_SIZE = NextPlayId();
    public static readonly int WORLD_BORDER_SIZE = NextPlayId();
    public static readonly int WORLD_BORDER_WARNING_DELAY = NextPlayId();
    public static readonly int WORLD_BORDER_WARNING_REACH = NextPlayId();
    public static readonly int CAMERA = NextPlayId();
    public static readonly int HELD_ITEM_CHANGE = NextPlayId();
    public static readonly int UPDATE_VIEW_POSITION = NextPlayId();
    public static readonly int UPDATE_VIEW_DISTANCE = NextPlayId(); // Not used by the dedicated server
    public static readonly int SPAWN_POSITION = NextPlayId();
    public static readonly int DISPLAY_SCOREBOARD = NextPlayId();
    public static readonly int ENTITY_METADATA = NextPlayId();
    public static readonly int ATTACH_ENTITY = NextPlayId();
    public static readonly int ENTITY_VELOCITY = NextPlayId();
    public static readonly int ENTITY_EQUIPMENT = NextPlayId();
    public static readonly int SET_EXPERIENCE = NextPlayId();
    public static readonly int UPDATE_HEALTH = NextPlayId();
    public static readonly int SCOREBOARD_OBJECTIVE = NextPlayId();
    public static readonly int SET_PASSENGERS = NextPlayId();
    public static readonly int TEAMS = NextPlayId();
    public static readonly int UPDATE_SCORE = NextPlayId();
    public static readonly int SET_SIMULATION_DISTANCE = NextPlayId();
    public static readonly int SET_TITLE_SUBTITLE = NextPlayId();
    public static readonly int TIME_UPDATE = NextPlayId();
    public static readonly int SET_TITLE_TEXT = NextPlayId();
    public static readonly int SET_TITLE_TIME = NextPlayId();
    public static readonly int ENTITY_SOUND_EFFECT = NextPlayId();
    public static readonly int SOUND_EFFECT = NextPlayId();
    public static readonly int START_CONFIGURATION_PACKET = NextPlayId();
    public static readonly int STOP_SOUND = NextPlayId();
    public static readonly int COOKIE_STORE = NextPlayId();
    public static readonly int SYSTEM_CHAT = NextPlayId();
    public static readonly int PLAYER_LIST_HEADER_AND_FOOTER = NextPlayId();
    public static readonly int NBT_QUERY_RESPONSE = NextPlayId();
    public static readonly int COLLECT_ITEM = NextPlayId();
    public static readonly int ENTITY_TELEPORT = NextPlayId();
    public static readonly int TICK_STATE = NextPlayId();
    public static readonly int TICK_STEP = NextPlayId();
    public static readonly int TRANSFER = NextPlayId();
    public static readonly int ADVANCEMENTS = NextPlayId();
    public static readonly int ENTITY_ATTRIBUTES = NextPlayId();
    public static readonly int ENTITY_EFFECT = NextPlayId();
    public static readonly int DECLARE_RECIPES = NextPlayId();
    public static readonly int TAGS = NextPlayId();
    public static readonly int PROJECTILE_POWER = NextPlayId();
    public static readonly int CUSTOM_REPORT_DETAILS = NextPlayId();
    public static readonly int SERVER_LINKS = NextPlayId();

    private static int NextStatusId()
    {
        return Interlocked.Increment(ref STATUS_ID);
    }

    private static int NextLoginId()
    {
        return Interlocked.Increment(ref LOGIN_ID);
    }

    private static int NextConfigurationId()
    {
        return Interlocked.Increment(ref CONFIGURATION_ID);
    }

    private static int NextPlayId() {
        return Interlocked.Increment(ref PLAY_ID);
    }
}