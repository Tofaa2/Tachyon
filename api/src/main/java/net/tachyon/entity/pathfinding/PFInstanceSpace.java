package net.tachyon.entity.pathfinding;

import com.extollit.gaming.ai.path.model.IBlockObject;
import com.extollit.gaming.ai.path.model.IColumnarSpace;
import com.extollit.gaming.ai.path.model.IInstanceSpace;
import net.tachyon.world.World;
import net.tachyon.world.chunk.Chunk;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PFInstanceSpace implements IInstanceSpace {

    private final World instance;
    private final Map<Chunk, PFColumnarSpace> chunkSpaceMap = new ConcurrentHashMap<>();

    public PFInstanceSpace(World instance) {
        this.instance = instance;
    }

    @Override
    public IBlockObject blockObjectAt(int x, int y, int z) {
        final short blockStateId = instance.getBlockStateId(x, y, z);
        return PFBlockObject.getBlockObject(blockStateId);
    }

    @Override
    public IColumnarSpace columnarSpaceAt(int cx, int cz) {
        final Chunk chunk =  instance.getChunk(cx, cz);
        if (chunk == null) {
            return null;
        }
        return chunkSpaceMap.computeIfAbsent(chunk, c -> {
            final PFColumnarSpace cs = new PFColumnarSpace(this, c);
            c.setColumnarSpace(cs);
            return cs;
        });
    }

    public World getInstance() {
        return instance;
    }
}
