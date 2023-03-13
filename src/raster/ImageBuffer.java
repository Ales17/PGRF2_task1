package raster;

import transforms.Col;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageBuffer implements Raster<Col> {

    private final BufferedImage img;
    private Col color;

    public BufferedImage getImg() {
        return img;
    }

    public ImageBuffer(int width, int height) {
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    public void repaint(Graphics graphics) {
        graphics.drawImage(img, 0, 0, null);
    }



    public Graphics getGraphics() {
        return img.getGraphics();
    }

    @Override
    public Col getElement(int x, int y) {
        return new Col( img.getRGB(x, y));
    }

    @Override
    public void setElement(int x, int y, Col color) {
        if(x>=0&&y>=0&&x<getWidth()&&y<getHeight())
            img.setRGB(x, y, color.getRGB());
    }

    @Override
    public void clear() {
        Graphics g = img.getGraphics();
        g.setColor(new Color(color.getRGB()));
        g.clearRect(0, 0, img.getWidth(), img.getHeight());
    }

    @Override
    public void setClearValue(Col color) {
        this.color = color;
    }

    @Override
    public int getWidth() {
        return img.getWidth();
    }

    @Override
    public int getHeight() {
        return img.getHeight();
    }

}

