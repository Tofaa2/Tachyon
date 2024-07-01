using Server.Chat;
using Server.Network.Connection;

namespace Server.Entity;

public class Player : Entity
{
    
    public readonly string Username;
    public readonly PlayerConnection Connection;
    public Settings PlayerSettings { get; set; }
    
    public Player(Guid uuid, string username, PlayerConnection connection) : base(uuid)
    {
        Username = username;
        Connection = connection;
    }


    public enum Hand
    {
        Left = 0,
        Right = 1
    }

    public enum GameMode : int
    {
        Survival = 0,
        Creative = 1,
        Adventure = 2,
        Spectator = 3
    }
    
    public class Settings
    {
        
        public static readonly int CapeEnabled = 0x01;
        public static readonly int JacketEnabled = 0x02;
        public static readonly int LeftSleeveEnabled = 0x04;
        public static readonly int RightSleeveEnabled = 0x08;
        public static readonly int LeftPantsLegEnabled = 0x10;
        public static readonly int RightPantsLegEnabled = 0x20;
        public static readonly int HatEnabled = 0x40;

        public string Locale { get; set; }
        public ChatMode ChatMode { get; set; }
        public byte ViewDistance { get; set; }
        public byte SkinData { get; set; }
        public bool ChatColorsEnabled { get; set; }
        public Hand MainHand { get; set; }
        public bool EnableTextFiltering { get; set; }
        public bool AllowServerListings { get; set; }
        
        public bool IsCapeEnabled => (SkinData & CapeEnabled) == CapeEnabled;
        public bool IsJacketEnabled => (SkinData & JacketEnabled) == JacketEnabled;
        public bool IsLeftSleeveEnabled => (SkinData & LeftSleeveEnabled) == LeftSleeveEnabled;
        public bool IsRightSleeveEnabled => (SkinData & RightSleeveEnabled) == RightSleeveEnabled;
        public bool IsLeftPantsLegEnabled => (SkinData & LeftPantsLegEnabled) == LeftPantsLegEnabled;
        public bool IsRightPantsLegEnabled => (SkinData & RightPantsLegEnabled) == RightPantsLegEnabled;
        public bool IsHatEnabled => (SkinData & HatEnabled) == HatEnabled;

    }
    
    
}