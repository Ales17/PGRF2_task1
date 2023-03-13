package raster;

public interface Raster<E> {
//// Interface - metody
void clear();

    void setClearValue(E color);

    int getWidth();

    int getHeight();

    E getElement(int x, int y);

    void setElement(int x, int y, E value);



}

