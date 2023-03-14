package view;

import raster.ImageBuffer;
import raster.TriangleRasterizer;
import render.Renderer;

import javax.swing.*;
import java.awt.*;


public class Panel extends JPanel {
    public static final int WIDTH = 800, HEIGHT = 600;

    private ImageBuffer raster;
    private Renderer renderer;

    public Panel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        raster = new ImageBuffer(WIDTH, HEIGHT);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        raster.repaint(g);
    }

    public ImageBuffer getRaster() {
        return raster;
    }

    public void clear() {
        raster.clear();
    }



}
