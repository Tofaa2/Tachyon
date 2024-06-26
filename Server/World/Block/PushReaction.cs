namespace Server.World.Block;

public enum PushReaction
{
    
    Normal,
    Destroy,
    PushOnly,
    Block,
    
}

public static class PushReactionAddition
{
    
    public static PushReaction FromString(string value)
    {
        return value.ToLower() switch
        {
            "normal" => PushReaction.Normal,
            "destroy" => PushReaction.Destroy,
            "push_only" => PushReaction.PushOnly,
            "block" => PushReaction.Block,
            _ => throw new ArgumentException("Invalid push reaction: " + value)
        };
    }
    
    
}