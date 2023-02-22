package raster;

public class DepthBuffer implements Raster<Double>{

    private double[][] buffer;
    private int width, height;

    private double clearValue = 1;

    public DepthBuffer(int width, int height) {
        buffer = new double[width][height];

        this.width = width;
        this.height = height;
        //
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                buffer[i][j] = clearValue;
            }
        }
    }

    @Override
    public void clear() {
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
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

        return width;
    }
    @Override
    public int getHeight() {

        return height;
    }

    @Override
    public Double getValue(int x, int y) {
        //if(x < width && y < height) {
            return buffer[x][y];
       // }
       // return null;
    }


    @Override
    public void setEValue(int x, int y, Double value) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return;
        }


        this.buffer[x][y] = (double) value;

    }

}