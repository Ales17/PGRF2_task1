package control;

import model.*;
import render.Renderer;
import raster.*;
import shaders.*;
import solid.*;
import transforms.*;
import view.Panel;

import java.awt.*;
import java.awt.event.*;

public class Controller3D implements Controller {
    private final Panel panel;
    private int width, height;
    private Camera camera = new Camera()
            .withPosition(new Vec3D(-0.1,-0.95,2.2))
            .withAzimuth(-5)
            .withZenith(-1)
            .withFirstPerson(true);
    private ZBuffer ZBuffer;
    ShaderConstantColor shaderConst;
    double cameraSpeed = 0.1;
    private TriangleRasterizer triangleRasterizer;
    Renderer renderer;
    Mat4 projection = new Mat4Identity();
    Point2D prevPoint;
    int cuttingMode = 0;

    Cube cube = new Cube();
     //Axis axis = new Axis();
     ArrowX arX = new ArrowX();
        ArrowY arY = new ArrowY();
        ArrowZ arZ = new ArrowZ();

     Pyramid pyramid = new Pyramid();
    public Controller3D(Panel panel) {
        this.panel = panel;
        // Měníme shader pomocí setteru
        //triangleRasterizer.setShader(new ShaderInterpolatedColor());
        initObjects(panel.getRaster());
        initListeners(panel);
        redraw();

        Shader greenShader = v -> {
            return new Col(0x00ff00);
        };
        Col color = new Col(0x00ff00);
        Shader colorShader = v -> new Col(color);

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
                prevPoint = new Point2D(e.getX(),e.getY());
            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                panel.clear();
                double dx = (e.getX() - prevPoint.getX());
                double dy = (e.getY() - prevPoint.getY());
                double zenith = camera.getZenith() - (Math.PI * dy) / (panel.getHeight() - 1);
                if (zenith > Math.PI / 2) zenith = Math.PI / 2;
                if (zenith < -Math.PI / 2) zenith = -Math.PI / 2;
                camera = camera.withZenith(zenith);
                double azimuth = camera.getAzimuth() + ((Math.PI * dx) / (panel.getWidth() - 1));
                camera = camera.withAzimuth((azimuth % (Math.PI * 2)));
                prevPoint = new Point2D(e.getX(), e.getY());
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
                switch (e.getKeyCode()) {
                    // WSAD
                    case KeyEvent.VK_W -> {
                        camera = camera.forward(cameraSpeed);

                    }
                    case KeyEvent.VK_S -> {
                        camera = camera.backward(cameraSpeed);

                    }
                    case KeyEvent.VK_A -> {
                        camera = camera.left(cameraSpeed);

                    }
                    case KeyEvent.VK_D -> {
                        camera = camera.right(cameraSpeed);

                    }
                    // ARROWS
                    case KeyEvent.VK_UP -> {
                        camera = camera.up(cameraSpeed);

                    }
                    case KeyEvent.VK_DOWN -> {
                        camera = camera.down(cameraSpeed);

                    }
                    // Another functions
                    case KeyEvent.VK_C -> {
                        camera = new Camera()
                                .withPosition(new Vec3D(-0.3,-0.8,1.7))
                                .withAzimuth(-5)
                                .withZenith(-1)
                                .withFirstPerson(true);

                    }
                }
                triangleRasterizer = new TriangleRasterizer(ZBuffer);
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
        renderer.render(arX);
        renderer.render(arY);
        renderer.render(arZ);

        panel.repaint();
    }




}
