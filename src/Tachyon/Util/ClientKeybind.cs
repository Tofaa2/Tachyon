namespace Tachyon.Util;

public record ClientKeybind(string Key)
{

    // Movement
    public static readonly ClientKeybind Jump = new("key.jump");
    public static readonly ClientKeybind Sneak = new("key.sneak");
    public static readonly ClientKeybind Sprint = new("key.sprint");
    public static readonly ClientKeybind StrafeLeft = new("key.left");
    public static readonly ClientKeybind StrafeRight = new("key.right");
    public static readonly ClientKeybind WalkBack = new("key.back");
    public static readonly ClientKeybind WalkForward = new("key.forward");
    
    // Gameplay
    public static readonly ClientKeybind Attack = new("key.attack");
    public static readonly ClientKeybind PickItem = new("key.pickItem");
    public static readonly ClientKeybind UseItem = new("key.use");

    // Inventory
    public static readonly ClientKeybind DropSelectedItem = new("key.drop");
    public static readonly ClientKeybind Inventory = new("key.inventory");
    public static readonly ClientKeybind SwapHandItems = new("key.swapOffHand");

    public static readonly ClientKeybind HotbarSlot1 = new("key.hotbar.1");
    public static readonly ClientKeybind HotbarSlot2 = new("key.hotbar.2");
    public static readonly ClientKeybind HotbarSlot3 = new("key.hotbar.3");
    public static readonly ClientKeybind HotbarSlot4 = new("key.hotbar.4");
    public static readonly ClientKeybind HotbarSlot5 = new("key.hotbar.5");
    public static readonly ClientKeybind HotbarSlot6 = new("key.hotbar.6");
    public static readonly ClientKeybind HotbarSlot7 = new("key.hotbar.7");
    public static readonly ClientKeybind HotbarSlot8 = new("key.hotbar.8");
    public static readonly ClientKeybind HotbarSlot9 = new("key.hotbar.9");
    
    // Creative Mode
    public static readonly ClientKeybind LoadToolbarActivator = new("key.loadToolbarActivator");
    public static readonly ClientKeybind SaveToolbarActivator = new("key.saveToolbarActivator");

    // Multiplayer
    public static readonly ClientKeybind ListPlayers = new("key.playerlist");
    public static readonly ClientKeybind OpenChat = new("key.chat");
    public static readonly ClientKeybind OpenCommand = new("key.command");
    public static readonly ClientKeybind SocialInteractionsMenu = new("key.socialInteractions");
    
    // Misc spectator
    public static readonly ClientKeybind HighlightPlayers = new("key.spectatorOutlines");
    
    // Misc
    public static readonly ClientKeybind Advancements = new("key.advancements");
    public static readonly ClientKeybind TakeScreenshot = new("key.screenshot");
    public static readonly ClientKeybind ToggleCinematicCamera = new("key.smoothCamera");
    public static readonly ClientKeybind ToggleFullscreen = new("key.fullscreen");
    public static readonly ClientKeybind TogglePerspective = new("key.togglePerspective");

    

}