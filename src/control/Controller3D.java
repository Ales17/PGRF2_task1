package control;

import model.*;
import raster.ImageBuffer;
import raster.ZBuffer;
import render.Renderer;
import raster.TriangleRasterizer;
import shaders.Shader;
import shaders.ShaderConstantColor;
import shaders.ShaderInterpolatedColor;
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
    ShaderConstantColor shaderConst;
    double cameraSpeed = 0.1;
    private TriangleRasterizer triangleRasterizer;
    Renderer renderer;
    Mat4 projection = new Mat4Identity();
    Point2D oldPoint;
    int cuttingMode = 0;

    Cube cube = new Cube();
    CubeFull cubeFull = new CubeFull();
    CubeFrame cubeFrame = new CubeFrame();
    Pyramid pyramid = new Pyramid();
    Axis axis = new Axis();

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
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        camera = camera.forward(cameraSpeed);
                        break;
                    case KeyEvent.VK_DOWN:
                        camera = camera.backward(cameraSpeed);
                        break;
                    case KeyEvent.VK_LEFT:
                        camera = camera.left(cameraSpeed);
                        break;
                    case KeyEvent.VK_RIGHT:
                        camera = camera.right(cameraSpeed);
                        break;
                    case KeyEvent.VK_D:
                        camera = camera.down(cameraSpeed);
                        break;
                    case KeyEvent.VK_U:
                        camera = camera.up(cameraSpeed);
                        break;




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

        renderer.render(axis);
        renderer.render(cube);
        panel.repaint();
    }




}
