package net.tachyon.stat;

public enum StatisticType {
    LEAVE_GAME("minecraft:leave_game"),

    PLAY_ONE_MINUTE("minecraft:play_one_minute"),

    TIME_SINCE_DEATH("minecraft:time_since_death"),

    TIME_SINCE_REST("minecraft:time_since_rest"),

    SNEAK_TIME("minecraft:sneak_time"),

    WALK_ONE_CM("minecraft:walk_one_cm"),

    CROUCH_ONE_CM("minecraft:crouch_one_cm"),

    SPRINT_ONE_CM("minecraft:sprint_one_cm"),

    WALK_ON_WATER_ONE_CM("minecraft:walk_on_water_one_cm"),

    FALL_ONE_CM("minecraft:fall_one_cm"),

    CLIMB_ONE_CM("minecraft:climb_one_cm"),

    FLY_ONE_CM("minecraft:fly_one_cm"),

    WALK_UNDER_WATER_ONE_CM("minecraft:walk_under_water_one_cm"),

    MINECART_ONE_CM("minecraft:minecart_one_cm"),

    BOAT_ONE_CM("minecraft:boat_one_cm"),

    PIG_ONE_CM("minecraft:pig_one_cm"),

    HORSE_ONE_CM("minecraft:horse_one_cm"),

    AVIATE_ONE_CM("minecraft:aviate_one_cm"),

    SWIM_ONE_CM("minecraft:swim_one_cm"),

    STRIDER_ONE_CM("minecraft:strider_one_cm"),

    JUMP("minecraft:jump"),

    DROP("minecraft:drop"),

    DAMAGE_DEALT("minecraft:damage_dealt"),

    DAMAGE_DEALT_ABSORBED("minecraft:damage_dealt_absorbed"),

    DAMAGE_DEALT_RESISTED("minecraft:damage_dealt_resisted"),

    DAMAGE_TAKEN("minecraft:damage_taken"),

    DAMAGE_BLOCKED_BY_SHIELD("minecraft:damage_blocked_by_shield"),

    DAMAGE_ABSORBED("minecraft:damage_absorbed"),

    DAMAGE_RESISTED("minecraft:damage_resisted"),

    DEATHS("minecraft:deaths"),

    MOB_KILLS("minecraft:mob_kills"),

    ANIMALS_BRED("minecraft:animals_bred"),

    PLAYER_KILLS("minecraft:player_kills"),

    FISH_CAUGHT("minecraft:fish_caught"),

    TALKED_TO_VILLAGER("minecraft:talked_to_villager"),

    TRADED_WITH_VILLAGER("minecraft:traded_with_villager"),

    EAT_CAKE_SLICE("minecraft:eat_cake_slice"),

    FILL_CAULDRON("minecraft:fill_cauldron"),

    USE_CAULDRON("minecraft:use_cauldron"),

    CLEAN_ARMOR("minecraft:clean_armor"),

    CLEAN_BANNER("minecraft:clean_banner"),

    CLEAN_SHULKER_BOX("minecraft:clean_shulker_box"),

    INTERACT_WITH_BREWINGSTAND("minecraft:interact_with_brewingstand"),

    INTERACT_WITH_BEACON("minecraft:interact_with_beacon"),

    INSPECT_DROPPER("minecraft:inspect_dropper"),

    INSPECT_HOPPER("minecraft:inspect_hopper"),

    INSPECT_DISPENSER("minecraft:inspect_dispenser"),

    PLAY_NOTEBLOCK("minecraft:play_noteblock"),

    TUNE_NOTEBLOCK("minecraft:tune_noteblock"),

    POT_FLOWER("minecraft:pot_flower"),

    TRIGGER_TRAPPED_CHEST("minecraft:trigger_trapped_chest"),

    OPEN_ENDERCHEST("minecraft:open_enderchest"),

    ENCHANT_ITEM("minecraft:enchant_item"),

    PLAY_RECORD("minecraft:play_record"),

    INTERACT_WITH_FURNACE("minecraft:interact_with_furnace"),

    INTERACT_WITH_CRAFTING_TABLE("minecraft:interact_with_crafting_table"),

    OPEN_CHEST("minecraft:open_chest"),

    SLEEP_IN_BED("minecraft:sleep_in_bed"),

    OPEN_SHULKER_BOX("minecraft:open_shulker_box"),

    OPEN_BARREL("minecraft:open_barrel"),

    INTERACT_WITH_BLAST_FURNACE("minecraft:interact_with_blast_furnace"),

    INTERACT_WITH_SMOKER("minecraft:interact_with_smoker"),

    INTERACT_WITH_LECTERN("minecraft:interact_with_lectern"),

    INTERACT_WITH_CAMPFIRE("minecraft:interact_with_campfire"),

    INTERACT_WITH_CARTOGRAPHY_TABLE("minecraft:interact_with_cartography_table"),

    INTERACT_WITH_LOOM("minecraft:interact_with_loom"),

    INTERACT_WITH_STONECUTTER("minecraft:interact_with_stonecutter"),

    BELL_RING("minecraft:bell_ring"),

    RAID_TRIGGER("minecraft:raid_trigger"),

    RAID_WIN("minecraft:raid_win"),

    INTERACT_WITH_ANVIL("minecraft:interact_with_anvil"),

    INTERACT_WITH_GRINDSTONE("minecraft:interact_with_grindstone"),

    TARGET_HIT("minecraft:target_hit"),

    INTERACT_WITH_SMITHING_TABLE("minecraft:interact_with_smithing_table");

    private String namespaceID;

    StatisticType(String namespaceID) {
        this.namespaceID = namespaceID;
    }

    public int getId() {
        return ordinal();
    }

    public String getNamespaceID() {
        return namespaceID;
    }

    public static StatisticType fromId(int id) {
        if (id >= 0 && id < values().length) {
            return values()[id];
        }
        return null;
    }
}
