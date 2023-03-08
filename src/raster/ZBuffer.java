package raster;

import transforms.Col;

public class ZBuffer {
    private final ImageBuffer imageBuffer;
    private final DepthBuffer depthBuffer;

    public ZBuffer(ImageBuffer imageBuffer){
        this.imageBuffer = imageBuffer;
        this.depthBuffer = new DepthBuffer(imageBuffer.getWidth(), imageBuffer.getHeight());
    }
    public void drawPixelWithTest(int x, int y, double z, Col color){
        if(z< depthBuffer.getElement(x,y)){
            imageBuffer.setElement(x,y,color);
            depthBuffer.setElement(x,y,z);
        }
    }

    public ImageBuffer getImageBuffer() {
        return imageBuffer;
    }

    public DepthBuffer getDepthBuffer() {
        return depthBuffer;
    }
}
