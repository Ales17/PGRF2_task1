package raster;

public interface Raster<E> {
//// Interface - metody
void clear();

    void setClearValue(E color);

    int getWidth();

    int getHeight();

    E getElement(int x, int y);

    void setElement(int x, int y, E value);

//    default boolean isInside(int x, int y) {
//        // Kontrola vykresleni mimo
//        if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
//            return false;
//        } else {
//            return true;
//        }
//    }

}

