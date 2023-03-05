package raster;

import transforms.Col;

public class ZBuffer { //// Třída pro zbuffer
    private ImageBuffer imageBuffer; //// Obrazek
    private DepthBuffer depthBuffer; //// Hloubka
    //// Konstruktor
    public ZBuffer(ImageBuffer imageBuffer) {
        this.imageBuffer = imageBuffer;
        this.depthBuffer = new DepthBuffer(imageBuffer.getWidth(), imageBuffer.getHeight());
    }
    //// Metoda na vykreslení s testem
    public void drawWithZTest(int x, int y, double z, Col color){
    double oldZ = depthBuffer.getValue(x,y);
    if(oldZ > z){
        imageBuffer.setEValue(x,y,color);
        depthBuffer.setEValue(x,y,z);
    }

        // Načtenou hodnotu porovnám s Z, které vstoupilo do metody (nové)
        // tak -> obravím a nastavím nové z v paměti hloubky
        // pokud je nové < než staré - obarvím pixel


    }



    public int getWidth() {
        return imageBuffer.getWidth();
    }
    public int getHeight() {
        return imageBuffer.getHeight();
    }
    //// Gettery
    public ImageBuffer getImageBuffer() {
        return imageBuffer;
    }
    public DepthBuffer getDepthBuffer() {
        return depthBuffer;
    }

}



