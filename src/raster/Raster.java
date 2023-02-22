package raster;

public interface Raster<E> {

    void clear();

    void setClearValue(E value);

    int getWidth();

    int getHeight();

    E getValue(int x, int y);

    void setEValue(int x, int y, E value);
    default boolean isInside(int x, int y) {

        //getWith()  getHeight() todo dodelat
        return true;
    }

}

