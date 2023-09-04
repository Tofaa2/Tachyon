package net.tachyon.entity.pathfinding;

import com.extollit.gaming.ai.path.model.ColumnarOcclusionFieldList;
import com.extollit.gaming.ai.path.model.IBlockDescription;
import com.extollit.gaming.ai.path.model.IColumnarSpace;
import com.extollit.gaming.ai.path.model.IInstanceSpace;
import net.tachyon.instance.TachyonChunk;

public class PFColumnarSpace implements IColumnarSpace {

    private final ColumnarOcclusionFieldList occlusionFieldList = new ColumnarOcclusionFieldList(this);
    private final PFInstanceSpace instanceSpace;
    private final TachyonChunk chunk;


    public PFColumnarSpace(PFInstanceSpace instanceSpace, TachyonChunk chunk) {
        this.instanceSpace = instanceSpace;
        this.chunk = chunk;
    }

    @Override
    public IBlockDescription blockAt(int x, int y, int z) {
        final short blockStateId = chunk.getBlockStateId(x, y, z);
        return PFBlockDescription.getBlockDescription(blockStateId);
    }

    @Override
    public int metaDataAt(int x, int y, int z) {
        return 0;
    }

    @Override
    public ColumnarOcclusionFieldList occlusionFields() {
        return occlusionFieldList;
    }

    @Override
    public IInstanceSpace instance() {
        return instanceSpace;
    }
}
