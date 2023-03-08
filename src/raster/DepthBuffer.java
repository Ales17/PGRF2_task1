package raster;

public class DepthBuffer implements Raster<Double> {

    private final double[][] depth;
    private final int width, height;
    private double clearValue = 1;

    public DepthBuffer(int width, int height) {
        depth = new double[width][height];
        this.width = width;
        this.height = height;
        clear();

    }

    @Override
    public void clear() {
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                depth[i][j] = clearValue;
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
    public Double getElement(int x, int y) {
        if (isInside(x, y))
            return depth[x][y];
        return clearValue;
    }

    private boolean isInside(int x, int y) {
        return (x >= 0 && y >= 0 && x < width && y < height);
    }

    @Override
    public void setElement(int x, int y, Double value) {
        if (isInside(x, y))
            depth[x][y] = value;
    }
}
