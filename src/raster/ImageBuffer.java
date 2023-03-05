package raster;

import transforms.Col;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageBuffer implements Raster<Col> {
    private final BufferedImage img; //// BufferedImage
    private Col color; //// Barva
    //// Konstruktor
    public ImageBuffer(int width, int height)
    {
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }
    //// Překreslení
    public void repaint(Graphics graphics) {
        graphics.drawImage(img, 0, 0, null);
    }
    //// Kreslení
    public void draw(ImageBuffer raster) {
        Graphics graphics = img.getGraphics();
        graphics.setColor(new Color(color.getRGB()));
        graphics.fillRect(0, 0, getWidth(), getHeight());
        graphics.drawImage(raster.img, 0, 0, null);
    }

    @Override
    public Col getValue(int x, int y) {
        return new Col( img.getRGB(x, y));
    }

    @Override
    public void setEValue(int x, int y, Col value) {
        img.setRGB(x, y, value.getRGB());
    }
    //// Gettery a settery
    public Graphics getGraphics() {
        return img.getGraphics();
    }
    //// Vymaže
    @Override
    public void clear() {
        Graphics g = img.getGraphics();
        g.setColor(new Color(color.getRGB()));
        g.clearRect(0, 0, img.getWidth(), img.getHeight());
    }
    //// Nastaví barvu
    @Override
    public void setClearValue(Col color) {
        this.color = color;
    }
     //// Vrátí šířku
    @Override
    public int getWidth() {
        return img.getWidth();
    }
     //// Vrátí výšku
    @Override
    public int getHeight() {
        return img.getHeight();
    }



}

