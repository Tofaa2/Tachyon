package net.tachyon.sound;

import net.kyori.adventure.key.Key;
import net.tachyon.namespace.NamespaceID;
import org.checkerframework.checker.nullness.qual.NonNull;

public enum SoundEvent implements net.kyori.adventure.sound.Sound.Type {
    AMBIENT_CAVE_CAVE("ambient.cave.cave"),

    AMBIENT_WEATHER_RAIN("ambient.weather.rain"),

    AMBIENT_WEATHER_THUNDER("ambient.weather.thunder"),

    GAME_PLAYER_HURT_FALL_BIG("game.player.hurt.fall.big"),

    GAME_PLAYER_HURT_FALL_SMALL("game.player.hurt.fall.small"),

    GAME_NEUTRAL_HURT_FALL_BIG("game.neutral.hurt.fall.big"),

    GAME_NEUTRAL_HURT_FALL_SMALL("game.neutral.hurt.fall.small"),

    GAME_HOSTILE_HURT_FALL_BIG("game.hostile.hurt.fall.big"),

    GAME_HOSTILE_HURT_FALL_SMALL("game.hostile.hurt.fall.small"),

    GAME_PLAYER_HURT("game.player.hurt"),

    GAME_NEUTRAL_HURT("game.neutral.hurt"),

    GAME_HOSTILE_HURT("game.hostile.hurt"),

    GAME_PLAYER_DIE("game.player.die"),

    GAME_NEUTRAL_DIE("game.neutral.die"),

    GAME_HOSTILE_DIE("game.hostile.die"),

    DIG_CLOTH("dig.cloth"),

    DIG_GRASS("dig.grass"),

    DIG_GRAVEL("dig.gravel"),

    DIG_SAND("dig.sand"),

    DIG_SNOW("dig.snow"),

    DIG_STONE("dig.stone"),

    DIG_WOOD("dig.wood"),

    FIRE_FIRE("fire.fire"),

    FIRE_IGNITE("fire.ignite"),

    ITEM_FIRECHARGE_USE("item.fireCharge.use"),

    FIREWORKS_BLAST("fireworks.blast"),

    FIREWORKS_BLAST_FAR("fireworks.blast_far"),

    FIREWORKS_LARGEBLAST("fireworks.largeBlast"),

    FIREWORKS_LARGEBLAST_FAR("fireworks.largeBlast_far"),

    FIREWORKS_LAUNCH("fireworks.launch"),

    FIREWORKS_TWINKLE("fireworks.twinkle"),

    FIREWORKS_TWINKLE_FAR("fireworks.twinkle_far"),

    LIQUID_LAVA("liquid.lava"),

    LIQUID_LAVAPOP("liquid.lavapop"),

    GAME_NEUTRAL_SWIM_SPLASH("game.neutral.swim.splash"),

    GAME_PLAYER_SWIM_SPLASH("game.player.swim.splash"),

    GAME_HOSTILE_SWIM_SPLASH("game.hostile.swim.splash"),

    GAME_PLAYER_SWIM("game.player.swim"),

    GAME_NEUTRAL_SWIM("game.neutral.swim"),

    GAME_HOSTILE_SWIM("game.hostile.swim"),

    LIQUID_WATER("liquid.water"),

    MINECART_BASE("minecart.base"),

    MINECART_INSIDE("minecart.inside"),

    MOB_BAT_DEATH("mob.bat.death"),

    MOB_BAT_HURT("mob.bat.hurt"),

    MOB_BAT_IDLE("mob.bat.idle"),

    MOB_BAT_LOOP("mob.bat.loop"),

    MOB_BAT_TAKEOFF("mob.bat.takeoff"),

    MOB_BLAZE_BREATHE("mob.blaze.breathe"),

    MOB_BLAZE_DEATH("mob.blaze.death"),

    MOB_BLAZE_HIT("mob.blaze.hit"),

    MOB_GUARDIAN_HIT("mob.guardian.hit"),

    MOB_GUARDIAN_IDLE("mob.guardian.idle"),

    MOB_GUARDIAN_DEATH("mob.guardian.death"),

    MOB_GUARDIAN_ELDER_HIT("mob.guardian.elder.hit"),

    MOB_GUARDIAN_ELDER_IDLE("mob.guardian.elder.idle"),

    MOB_GUARDIAN_ELDER_DEATH("mob.guardian.elder.death"),

    MOB_GUARDIAN_LAND_HIT("mob.guardian.land.hit"),

    MOB_GUARDIAN_LAND_IDLE("mob.guardian.land.idle"),

    MOB_GUARDIAN_LAND_DEATH("mob.guardian.land.death"),

    MOB_GUARDIAN_CURSE("mob.guardian.curse"),

    MOB_GUARDIAN_ATTACK("mob.guardian.attack"),

    MOB_GUARDIAN_FLOP("mob.guardian.flop"),

    MOB_CAT_HISS("mob.cat.hiss"),

    MOB_CAT_HITT("mob.cat.hitt"),

    MOB_CAT_MEOW("mob.cat.meow"),

    MOB_CAT_PURR("mob.cat.purr"),

    MOB_CAT_PURREOW("mob.cat.purreow"),

    MOB_CHICKEN_HURT("mob.chicken.hurt"),

    MOB_CHICKEN_PLOP("mob.chicken.plop"),

    MOB_CHICKEN_SAY("mob.chicken.say"),

    MOB_CHICKEN_STEP("mob.chicken.step"),

    MOB_COW_HURT("mob.cow.hurt"),

    MOB_COW_SAY("mob.cow.say"),

    MOB_COW_STEP("mob.cow.step"),

    MOB_CREEPER_DEATH("mob.creeper.death"),

    MOB_CREEPER_SAY("mob.creeper.say"),

    MOB_ENDERDRAGON_END("mob.enderdragon.end"),

    MOB_ENDERDRAGON_GROWL("mob.enderdragon.growl"),

    MOB_ENDERDRAGON_HIT("mob.enderdragon.hit"),

    MOB_ENDERDRAGON_WINGS("mob.enderdragon.wings"),

    MOB_ENDERMEN_DEATH("mob.endermen.death"),

    MOB_ENDERMEN_HIT("mob.endermen.hit"),

    MOB_ENDERMEN_IDLE("mob.endermen.idle"),

    MOB_ENDERMEN_PORTAL("mob.endermen.portal"),

    MOB_ENDERMEN_SCREAM("mob.endermen.scream"),

    MOB_ENDERMEN_STARE("mob.endermen.stare"),

    MOB_GHAST_AFFECTIONATE_SCREAM("mob.ghast.affectionate_scream"),

    MOB_GHAST_CHARGE("mob.ghast.charge"),

    MOB_GHAST_DEATH("mob.ghast.death"),

    MOB_GHAST_FIREBALL("mob.ghast.fireball"),

    MOB_GHAST_MOAN("mob.ghast.moan"),

    MOB_GHAST_SCREAM("mob.ghast.scream"),

    MOB_HORSE_ANGRY("mob.horse.angry"),

    MOB_HORSE_ARMOR("mob.horse.armor"),

    MOB_HORSE_BREATHE("mob.horse.breathe"),

    MOB_HORSE_DEATH("mob.horse.death"),

    MOB_HORSE_DONKEY_ANGRY("mob.horse.donkey.angry"),

    MOB_HORSE_DONKEY_DEATH("mob.horse.donkey.death"),

    MOB_HORSE_DONKEY_HIT("mob.horse.donkey.hit"),

    MOB_HORSE_DONKEY_IDLE("mob.horse.donkey.idle"),

    MOB_HORSE_GALLOP("mob.horse.gallop"),

    MOB_HORSE_HIT("mob.horse.hit"),

    MOB_HORSE_IDLE("mob.horse.idle"),

    MOB_HORSE_JUMP("mob.horse.jump"),

    MOB_HORSE_LAND("mob.horse.land"),

    MOB_HORSE_LEATHER("mob.horse.leather"),

    MOB_HORSE_SKELETON_DEATH("mob.horse.skeleton.death"),

    MOB_HORSE_SKELETON_HIT("mob.horse.skeleton.hit"),

    MOB_HORSE_SKELETON_IDLE("mob.horse.skeleton.idle"),

    MOB_HORSE_SOFT("mob.horse.soft"),

    MOB_HORSE_WOOD("mob.horse.wood"),

    MOB_HORSE_ZOMBIE_DEATH("mob.horse.zombie.death"),

    MOB_HORSE_ZOMBIE_HIT("mob.horse.zombie.hit"),

    MOB_HORSE_ZOMBIE_IDLE("mob.horse.zombie.idle"),

    MOB_IRONGOLEM_DEATH("mob.irongolem.death"),

    MOB_IRONGOLEM_HIT("mob.irongolem.hit"),

    MOB_IRONGOLEM_THROW("mob.irongolem.throw"),

    MOB_IRONGOLEM_WALK("mob.irongolem.walk"),

    MOB_MAGMACUBE_BIG("mob.magmacube.big"),

    MOB_MAGMACUBE_JUMP("mob.magmacube.jump"),

    MOB_MAGMACUBE_SMALL("mob.magmacube.small"),

    MOB_PIG_DEATH("mob.pig.death"),

    MOB_PIG_SAY("mob.pig.say"),

    MOB_PIG_STEP("mob.pig.step"),

    MOB_RABBIT_HURT("mob.rabbit.hurt"),

    MOB_RABBIT_IDLE("mob.rabbit.idle"),

    MOB_RABBIT_HOP("mob.rabbit.hop"),

    MOB_RABBIT_DEATH("mob.rabbit.death"),

    MOB_SHEEP_SAY("mob.sheep.say"),

    MOB_SHEEP_SHEAR("mob.sheep.shear"),

    MOB_SHEEP_STEP("mob.sheep.step"),

    MOB_SILVERFISH_HIT("mob.silverfish.hit"),

    MOB_SILVERFISH_KILL("mob.silverfish.kill"),

    MOB_SILVERFISH_SAY("mob.silverfish.say"),

    MOB_SILVERFISH_STEP("mob.silverfish.step"),

    MOB_SKELETON_DEATH("mob.skeleton.death"),

    MOB_SKELETON_HURT("mob.skeleton.hurt"),

    MOB_SKELETON_SAY("mob.skeleton.say"),

    MOB_SKELETON_STEP("mob.skeleton.step"),

    MOB_SLIME_ATTACK("mob.slime.attack"),

    MOB_SLIME_BIG("mob.slime.big"),

    MOB_SLIME_SMALL("mob.slime.small"),

    MOB_SPIDER_DEATH("mob.spider.death"),

    MOB_SPIDER_SAY("mob.spider.say"),

    MOB_SPIDER_STEP("mob.spider.step"),

    MOB_VILLAGER_DEATH("mob.villager.death"),

    MOB_VILLAGER_HAGGLE("mob.villager.haggle"),

    MOB_VILLAGER_HIT("mob.villager.hit"),

    MOB_VILLAGER_IDLE("mob.villager.idle"),

    MOB_VILLAGER_NO("mob.villager.no"),

    MOB_VILLAGER_YES("mob.villager.yes"),

    MOB_WITHER_DEATH("mob.wither.death"),

    MOB_WITHER_HURT("mob.wither.hurt"),

    MOB_WITHER_IDLE("mob.wither.idle"),

    MOB_WITHER_SHOOT("mob.wither.shoot"),

    MOB_WITHER_SPAWN("mob.wither.spawn"),

    MOB_WOLF_BARK("mob.wolf.bark"),

    MOB_WOLF_DEATH("mob.wolf.death"),

    MOB_WOLF_GROWL("mob.wolf.growl"),

    MOB_WOLF_HOWL("mob.wolf.howl"),

    MOB_WOLF_HURT("mob.wolf.hurt"),

    MOB_WOLF_PANTING("mob.wolf.panting"),

    MOB_WOLF_SHAKE("mob.wolf.shake"),

    MOB_WOLF_STEP("mob.wolf.step"),

    MOB_WOLF_WHINE("mob.wolf.whine"),

    MOB_ZOMBIE_DEATH("mob.zombie.death"),

    MOB_ZOMBIE_HURT("mob.zombie.hurt"),

    MOB_ZOMBIE_INFECT("mob.zombie.infect"),

    MOB_ZOMBIE_METAL("mob.zombie.metal"),

    MOB_ZOMBIE_REMEDY("mob.zombie.remedy"),

    MOB_ZOMBIE_SAY("mob.zombie.say"),

    MOB_ZOMBIE_STEP("mob.zombie.step"),

    MOB_ZOMBIE_UNFECT("mob.zombie.unfect"),

    MOB_ZOMBIE_WOOD("mob.zombie.wood"),

    MOB_ZOMBIE_WOODBREAK("mob.zombie.woodbreak"),

    MOB_ZOMBIEPIG_ZPIG("mob.zombiepig.zpig"),

    MOB_ZOMBIEPIG_ZPIGANGRY("mob.zombiepig.zpigangry"),

    MOB_ZOMBIEPIG_ZPIGDEATH("mob.zombiepig.zpigdeath"),

    MOB_ZOMBIEPIG_ZPIGHURT("mob.zombiepig.zpighurt"),

    NOTE_BASS("note.bass"),

    NOTE_BASSATTACK("note.bassattack"),

    NOTE_BD("note.bd"),

    NOTE_HARP("note.harp"),

    NOTE_HAT("note.hat"),

    NOTE_PLING("note.pling"),

    NOTE_SNARE("note.snare"),

    PORTAL_PORTAL("portal.portal"),

    PORTAL_TRAVEL("portal.travel"),

    PORTAL_TRIGGER("portal.trigger"),

    RANDOM_ANVIL_BREAK("random.anvil_break"),

    RANDOM_ANVIL_LAND("random.anvil_land"),

    RANDOM_ANVIL_USE("random.anvil_use"),

    RANDOM_BOW("random.bow"),

    RANDOM_BOWHIT("random.bowhit"),

    RANDOM_BREAK("random.break"),

    RANDOM_BURP("random.burp"),

    RANDOM_CHESTCLOSED("random.chestclosed"),

    RANDOM_CHESTOPEN("random.chestopen"),

    GUI_BUTTON_PRESS("gui.button.press"),

    RANDOM_CLICK("random.click"),

    RANDOM_DOOR_CLOSE("random.door_close"),

    RANDOM_DOOR_OPEN("random.door_open"),

    RANDOM_DRINK("random.drink"),

    RANDOM_EAT("random.eat"),

    RANDOM_EXPLODE("random.explode"),

    RANDOM_FIZZ("random.fizz"),

    GAME_TNT_PRIMED("game.tnt.primed"),

    CREEPER_PRIMED("creeper.primed"),

    DIG_GLASS("dig.glass"),

    GAME_POTION_SMASH("game.potion.smash"),

    RANDOM_LEVELUP("random.levelup"),

    RANDOM_ORB("random.orb"),

    RANDOM_POP("random.pop"),

    RANDOM_SPLASH("random.splash"),

    RANDOM_SUCCESSFUL_HIT("random.successful_hit"),

    RANDOM_WOOD_CLICK("random.wood_click"),

    RECORDS_11("records.11"),

    RECORDS_13("records.13"),

    RECORDS_BLOCKS("records.blocks"),

    RECORDS_CAT("records.cat"),

    RECORDS_CHIRP("records.chirp"),

    RECORDS_FAR("records.far"),

    RECORDS_MALL("records.mall"),

    RECORDS_MELLOHI("records.mellohi"),

    RECORDS_STAL("records.stal"),

    RECORDS_STRAD("records.strad"),

    RECORDS_WAIT("records.wait"),

    RECORDS_WARD("records.ward"),

    STEP_CLOTH("step.cloth"),

    STEP_GRASS("step.grass"),

    STEP_GRAVEL("step.gravel"),

    STEP_LADDER("step.ladder"),

    STEP_SAND("step.sand"),

    STEP_SNOW("step.snow"),

    STEP_STONE("step.stone"),

    STEP_WOOD("step.wood"),

    TILE_PISTON_IN("tile.piston.in"),

    TILE_PISTON_OUT("tile.piston.out"),

    MUSIC_MENU("music.menu"),

    MUSIC_GAME("music.game"),

    MUSIC_GAME_CREATIVE("music.game.creative"),

    MUSIC_GAME_END("music.game.end"),

    MUSIC_GAME_END_DRAGON("music.game.end.dragon"),

    MUSIC_GAME_END_CREDITS("music.game.end.credits"),

    MUSIC_GAME_NETHER("music.game.nether");

    private final String id;
    private final NamespaceID namespaceID;

    SoundEvent(String id) {
        this.id = id;
        this.namespaceID = NamespaceID.from("minecraft", id);
    }

    public String getId() {
        return id;
    }

    @Override
    public @NonNull Key key() {
        return namespaceID.key();
    }
}
