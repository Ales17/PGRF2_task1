package raster;

import java.util.ArrayList;
import java.util.List;

public class VertexBuffer<V> {
    private List<V> buffer;

    public VertexBuffer() {
        buffer = new ArrayList<>();
    }

    public void add(V element) {
        buffer.add(element);
    }

    public List<V> getBuffer() {
        return buffer;
    }

    public int getSize() {
        return buffer.size();
    }
}
