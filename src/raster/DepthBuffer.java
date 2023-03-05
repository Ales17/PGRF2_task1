package raster;

public class DepthBuffer implements Raster<Double> {

    private final double[][] buffer; //// Buffer
    private final int width, height; //// Šířka a výška

    private double clearValue = 1; //// Výchozí hodnota

    //// Konstruktor
    public DepthBuffer(int width, int height) {
        buffer = new double[width][height];
        this.width = width;
        this.height = height;

        /* for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                buffer[i][j] = clearValue;
            }
        }*/
        clear();
    }

    //// Všude nastavit vlastnost clear value
    @Override
    public void clear() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                buffer[i][j] = clearValue;
            }
        }
    }

    @Override
    public void setClearValue(Double value) {
        this.clearValue = value;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public Double getValue(int x, int y) {
        //// Kontrola mimo buffer
        if (isInside(x, y)) return buffer[x][y];
        ////return null;
        return clearValue;
    }


    @Override
    public void setEValue(int x, int y, Double value) {
        if (isInside(x, y)) {
            buffer[x][y] = value;
        }

    }

}