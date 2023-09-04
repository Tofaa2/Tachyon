package net.tachyon.listener;

import net.tachyon.MinecraftServer;
import net.tachyon.Tachyon;
import net.tachyon.block.BlockManager;
import net.tachyon.coordinate.Point;
import net.tachyon.data.Data;
import net.tachyon.entity.TachyonEntity;
import net.tachyon.entity.GameMode;
import net.tachyon.entity.TachyonPlayer;
import net.tachyon.event.player.PlayerBlockInteractEvent;
import net.tachyon.event.player.PlayerBlockPlaceEvent;
import net.tachyon.event.player.PlayerUseItemOnBlockEvent;
import net.tachyon.instance.TachyonChunk;
import net.tachyon.instance.Instance;
import net.tachyon.block.Block;
import net.tachyon.coordinate.BlockFace;
import net.tachyon.instance.block.TachyonBlockManager;
import net.tachyon.block.CustomBlock;
import net.tachyon.block.rule.BlockPlacementRule;
import net.tachyon.inventory.PlayerInventory;
import net.tachyon.item.ItemStack;
import net.tachyon.item.Material;
import net.tachyon.network.packet.client.play.ClientPlayerBlockPlacementPacket;
import net.tachyon.network.packet.server.play.BlockChangePacket;
import net.tachyon.coordinate.Direction;
import net.tachyon.utils.chunk.ChunkUtils;
import net.tachyon.utils.validate.Check;

import java.util.Set;

public class BlockPlacementListener {

    private static final BlockManager BLOCK_MANAGER = Tachyon.getServer().getBlockmanager();

    public static void listener(ClientPlayerBlockPlacementPacket packet, TachyonPlayer player) {
        if (UseItemListener.useItemListener(packet, player)) {
            return;
        }

        final PlayerInventory playerInventory = player.getInventory();
        final BlockFace blockFace = packet.blockFace;
        final Point blockPosition = packet.blockPosition;
        final Direction direction = blockFace.toDirection();

        final Instance instance = player.getInstance();
        if (instance == null)
            return;

        // Prevent outdated/modified client data
        if (!ChunkUtils.isLoaded(instance.getChunkAt(blockPosition))) {
            // Client tried to place a block in an unloaded chunk, ignore the request
            return;
        }

        final ItemStack usedItem = player.getItemInHand();

        // Interact at block
        final boolean cancel = false; //usedItem.onUseOnBlock(player, blockPosition, direction);
        PlayerBlockInteractEvent playerBlockInteractEvent = new PlayerBlockInteractEvent(player, blockPosition, blockFace);
        playerBlockInteractEvent.setCancelled(cancel);
        playerBlockInteractEvent.setBlockingItemUse(cancel);
        player.callCancellableEvent(PlayerBlockInteractEvent.class, playerBlockInteractEvent, () -> {
            final CustomBlock customBlock = instance.getCustomBlock(blockPosition);
            if (customBlock != null) {
                final Data data = instance.getBlockData(blockPosition);
                final boolean blocksItem = customBlock.onInteract(player, blockPosition, data);
                if (blocksItem) {
                    playerBlockInteractEvent.setBlockingItemUse(true);
                }
            }
        });

        if (playerBlockInteractEvent.isBlockingItemUse()) {
            return;
        }

        final Material useMaterial = usedItem.material();

        // Verify if the player can place the block
        boolean canPlaceBlock = true;
        {
            if (useMaterial == Material.AIR) { // Can't place air
                return;
            }

            //Check if the player is allowed to place blocks based on their game mode
            if (player.getGameMode() == GameMode.SPECTATOR) {
                canPlaceBlock = false; //Spectators can't place blocks
            } else if (player.getGameMode() == GameMode.ADVENTURE) {
                //Check if the block can placed on the block
                canPlaceBlock = usedItem.canPlaceOn(instance.getBlock(blockPosition).getName());
            }
        }

        // Get the newly placed block position
        final int offsetX = blockFace == BlockFace.WEST ? -1 : blockFace == BlockFace.EAST ? 1 : 0;
        final int offsetY = blockFace == BlockFace.BOTTOM ? -1 : blockFace == BlockFace.TOP ? 1 : 0;
        final int offsetZ = blockFace == BlockFace.NORTH ? -1 : blockFace == BlockFace.SOUTH ? 1 : 0;

        blockPosition.add(offsetX, offsetY, offsetZ);

        if (!canPlaceBlock) {
            //Send a block change with AIR as block to keep the client in sync,
            //using refreshChunk results in the client not being in sync
            //after rapid invalid block placements
            player.getPlayerConnection().sendPacket(new BlockChangePacket(blockPosition, Block.AIR.toStateId((byte) 0)));
            return;
        }

        final TachyonChunk chunk = instance.getChunkAt(blockPosition);

        Check.stateCondition(!ChunkUtils.isLoaded(chunk),
                "A player tried to place a block in the border of a loaded chunk " + blockPosition);

        // The concerned chunk will be send to the player if an error occur
        // This will ensure that the player has the correct version of the chunk
        boolean refreshChunk = false;

        if (useMaterial.isBlock()) {
            if (!chunk.isReadOnly()) {
                final Block block = useMaterial.getBlock();
                final Set<TachyonEntity> entities = instance.getChunkEntities(chunk);
                // Check if the player is trying to place a block in an entity
                boolean intersect = player.getBoundingBox().intersect(blockPosition);
                if (!intersect && block.isSolid()) {
                    for (TachyonEntity entity : entities) {
                        // 'player' has already been checked
                        if (entity == player)
                            continue;

                        intersect = entity.getBoundingBox().intersect(blockPosition);
                        if (intersect)
                            break;
                    }
                }

                if (!intersect) {
                    byte metadata = 0;
                    if (block.getVariations() != null) {
                        metadata = (byte)usedItem.damage();
                    }

                    // BlockPlaceEvent check
                    PlayerBlockPlaceEvent playerBlockPlaceEvent = new PlayerBlockPlaceEvent(player, block.toStateId(metadata), blockPosition);
                    playerBlockPlaceEvent.consumeBlock(player.getGameMode() != GameMode.CREATIVE);

                    player.callEvent(PlayerBlockPlaceEvent.class, playerBlockPlaceEvent);
                    if (!playerBlockPlaceEvent.isCancelled()) {

                        // BlockPlacementRule check
                        short blockStateId = playerBlockPlaceEvent.getBlockStateId();
                        final Block resultBlock = Block.fromStateId(blockStateId);
                        final BlockPlacementRule blockPlacementRule = BLOCK_MANAGER.getBlockPlacementRule(resultBlock);
                        if (blockPlacementRule != null) {
                            // Get id from block placement rule instead of the event
                            blockStateId = blockPlacementRule.blockPlace(instance, resultBlock, blockFace, blockPosition, player);
                        }
                        final boolean placementRuleCheck = blockStateId != BlockPlacementRule.CANCEL_CODE;

                        if (placementRuleCheck) {

                            // Place the block
                            final short customBlockId = playerBlockPlaceEvent.getCustomBlockId();
                            final Data blockData = playerBlockPlaceEvent.getBlockData(); // Possibly null
                            instance.setSeparateBlocks(blockPosition.blockX(), blockPosition.blockY(), blockPosition.blockZ(),
                                    blockStateId, customBlockId, blockData);

                            // Block consuming
                            if (playerBlockPlaceEvent.doesConsumeBlock()) {
                                // Consume the block in the player's hand
                                final ItemStack newUsedItem = usedItem.withAmount((byte) (usedItem.amount() - 1));
                                playerInventory.setItemInHand(newUsedItem);
                            }
                        } else {
                            refreshChunk = true;
                        }
                    } else {
                        refreshChunk = true;
                    }
                } else {
                    refreshChunk = true;
                }
            } else {
                refreshChunk = true;
            }
        } else {
            // Player didn't try to place a block but interacted with one
            final Point usePosition = blockPosition.subtract(offsetX, offsetY, offsetZ);
            PlayerUseItemOnBlockEvent event = new PlayerUseItemOnBlockEvent(player, usedItem, usePosition, direction);
            player.callEvent(PlayerUseItemOnBlockEvent.class, event);
            refreshChunk = true;
        }

        // Refresh chunk section if needed
        if (refreshChunk) {
            chunk.sendChunkSectionUpdate(ChunkUtils.getSectionAt(blockPosition.blockY()), player);
        }

        player.getInventory().refreshSlot(player.getHeldSlot());
    }

}
