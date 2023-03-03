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
        // Všude nastavit vlastnost clear value
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
        // Kontrola mimo buffer
        if(isInside(x,y))
            return buffer[x][y];
        else
            return null;
    }


    @Override
    public void setEValue(int x, int y, Double value) {
       if(isInside(x,y))
       {
           buffer[x][y] = value;
       }

    }

}