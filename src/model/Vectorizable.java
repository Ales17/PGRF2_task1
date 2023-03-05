package model;

import transforms.Mat4;

public interface Vectorizable<V> {

    V mul (double d);

    V add(V v);
}
