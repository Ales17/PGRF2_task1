package control;

import model.*;
import raster.ImageBuffer;
import raster.ZBuffer;
import render.Renderer;
import raster.TriangleRasterizer;
import transforms.*;
import view.Panel;

import java.awt.*;
import java.awt.event.*;

public class Controller3D implements Controller {

    private final Panel panel;

    private int width, height;
    private Camera camera = new Camera()
            .withPosition(new Vec3D(-0.3,-0.8,1.7))
            .withAzimuth(-5)
            .withZenith(-1)
            .withFirstPerson(true);
    private ZBuffer ZBuffer;
    private TriangleRasterizer triangleRasterizer;
    Renderer renderer;
    Mat4 projection = new Mat4Identity();
    Point2D oldPoint;
    int cuttingMode =0;

    Cube cube = new Cube();
    CubeFrame cubeFrame = new CubeFrame();
    Axis axis = new Axis();

    Shader shader;

    public Controller3D(Panel panel) {
        this.panel = panel;
        initObjects(panel.getRaster());
        initListeners(panel);
        redraw();
    }

    public void initObjects(ImageBuffer raster) {
        raster.setClearValue(new Col(0xff0000));
        ZBuffer = new ZBuffer(panel.getRaster());
        projection = new Mat4PerspRH(Math.PI / 3,
                panel.getHeight()/ (float) panel.getWidth(),0.1,25);
//        shader = v -> {
//            return v.getColor();
//        };
        triangleRasterizer = new TriangleRasterizer(ZBuffer/*, shader*/);
    }

    @Override
    public void initListeners(Panel panel) {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                oldPoint= new Point2D(e.getX(),e.getY());
            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                panel.clear();
                double dx = (e.getX() - oldPoint.getX());
                double dy = (e.getY() - oldPoint.getY());
                double zenith = camera.getZenith() - (Math.PI * dy) / (panel.getHeight() - 1);
                if (zenith > Math.PI / 2) zenith = Math.PI / 2;
                if (zenith < -Math.PI / 2) zenith = -Math.PI / 2;
                camera = camera.withZenith(zenith);
                double azimuth = camera.getAzimuth() + ((Math.PI * dx) / (panel.getWidth() - 1));
                camera = camera.withAzimuth((azimuth % (Math.PI * 2)));
                oldPoint = new Point2D(e.getX(), e.getY());
                redraw();
            }
        });
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }
        });
        panel.requestFocus();
        panel.requestFocusInWindow();
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                Mat4 model;

                triangleRasterizer = new TriangleRasterizer(ZBuffer/*,shader*/);
                panel.clear();
                redraw();

            }
        });
    }

    private void redraw() {
        width = panel.getRaster().getWidth();
        height = panel.getRaster().getHeight();
        Graphics g = panel.getRaster().getGraphics();

        g.setColor(Color.white);


        triangleRasterizer.getzBuffer().getDepthBuffer().clear();
        triangleRasterizer.setCuttingMode(cuttingMode);
        renderer = new Renderer(triangleRasterizer);
        renderer.setProjectionMatrix(projection);
        renderer.setView(camera.getViewMatrix());

        renderer.render(axis);
        renderer.render(cube);
        panel.repaint();
    }




}
