package utils;

import model.Vectorizable;
import model.Vertex;

public class Lerp<V extends Vectorizable<V>> {
    public V lerp(V v1, V v2, double t) {
        //int x2 = (int) ((1-t) * v1 + t * v2);
        return v1.mul(1 - t).add((v2.mul(t)));
    }
}
