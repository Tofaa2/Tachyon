package net.tachyon.entity.pathfinding;

import com.extollit.gaming.ai.path.model.IBlockObject;
import com.extollit.gaming.ai.path.model.IColumnarSpace;
import com.extollit.gaming.ai.path.model.IInstanceSpace;
import net.tachyon.instance.TachyonChunk;
import net.tachyon.instance.Instance;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PFInstanceSpace implements IInstanceSpace {

    private final Instance instance;
    private final Map<TachyonChunk, PFColumnarSpace> chunkSpaceMap = new ConcurrentHashMap<>();

    public PFInstanceSpace(Instance instance) {
        this.instance = instance;
    }

    @Override
    public IBlockObject blockObjectAt(int x, int y, int z) {
        final short blockStateId = instance.getBlockStateId(x, y, z);
        return PFBlockObject.getBlockObject(blockStateId);
    }

    @Override
    public IColumnarSpace columnarSpaceAt(int cx, int cz) {
        final TachyonChunk chunk = instance.getChunk(cx, cz);
        if (chunk == null) {
            return null;
        }

        return chunkSpaceMap.computeIfAbsent(chunk, c -> {
            final PFColumnarSpace cs = new PFColumnarSpace(this, c);
            c.setColumnarSpace(cs);
            return cs;
        });
    }

    public Instance getInstance() {
        return instance;
    }
}
