package raster;

import transforms.Col;

public class ZBuffer {
    private ImageBuffer imageBuffer;
    private DepthBuffer depthBuffer;

    public ZBuffer(ImageBuffer imageBuffer) {
        this.imageBuffer = imageBuffer;
        this.depthBuffer = new DepthBuffer(imageBuffer.getWidth(), imageBuffer.getHeight());
    }
    public ImageBuffer getImageBuffer() {

        return imageBuffer;
    }
    public void drawWithZTest(int x, int y, double z, Col color){
    double oldZ = depthBuffer.getValue(x,y);
    if(oldZ > z){
        imageBuffer.setEValue(x,y,color);
        depthBuffer.setEValue(x,y,z);
    }
        // TODO: Implementace zbufferu jako takového
        // Načetnou hodnotu porovnám s Z, které vstoupilo do metody (nové)
        // tak -> obravím a nastavím nové z v paměti hloubky
        // pokud, je nové < než staré
        //obarvim pixel






    }
    public int getWidth() {
        return imageBuffer.getWidth();
    }
    public int getHeight() {
        return imageBuffer.getHeight();
    }
}



