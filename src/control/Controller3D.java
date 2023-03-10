package control;

import model.AxisList;
import model.Cube;
import model.Pyramid;
import raster.ImageBuffer;
import raster.TriangleRasterizer;
import raster.ZBuffer;
import render.Renderer;
import render.Scene;
import shaders.Shader;
import shaders.ShaderConstantColor;
import solid.ArrowX;
import solid.ArrowY;
import solid.ArrowZ;
import solid.Solid;
import transforms.*;
import view.Panel;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Controller3D implements Controller {
    private final Panel panel;
    ShaderConstantColor shaderConst;
    double cameraSpeed = 0.1;
    double cameraRotationSpeed = 0.1;
    Renderer renderer;
    Mat4 projection = new Mat4Identity();
    Point2D prevPoint;
    int cuttingMode = 0;
    AxisList axisList = AxisList.X;
    Cube cube = new Cube();
    //Axis axis = new Axis();
    ArrowX arX = new ArrowX();
    ArrowY arY = new ArrowY();
    ArrowZ arZ = new ArrowZ();
    boolean ortho = false;
    Pyramid pyramid = new Pyramid();
    private int width, height;
    Camera defaultCamera = new Camera()
            .withPosition(new Vec3D(-0.1, -0.95, 2.2))
            .withAzimuth(-5)
            .withZenith(-1)
            .withFirstPerson(true);
    private Camera camera = defaultCamera;
    private ZBuffer ZBuffer;
    private Scene scene;
    private TriangleRasterizer triangleRasterizer;

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
                panel.getHeight() / (float) panel.getWidth(), 0.1, 25);
        scene = new Scene();
        triangleRasterizer = new TriangleRasterizer(ZBuffer/*, shader*/);
    }

    @Override
    public void initListeners(Panel panel) {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                prevPoint = new Point2D(e.getX(), e.getY());
            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                panel.clear();
                double x = (e.getX() - prevPoint.getX());
                double y = (e.getY() - prevPoint.getY());
                double zenith = camera.getZenith() - (Math.PI * y) / (panel.getHeight() - 1);
                if (zenith < -Math.PI / 2) zenith = -Math.PI / 2;
                if (zenith > Math.PI / 2) zenith = Math.PI / 2;
                camera = camera.withZenith(zenith);
                double azimuth = camera.getAzimuth() + ((Math.PI * x) / (panel.getWidth() - 1));
                camera = camera.withAzimuth((azimuth % (Math.PI * 2)));
                prevPoint = new Point2D(e.getX(), e.getY());
                redraw();
            }
        });
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
                    case KeyEvent.VK_C -> {
                         resetScene();
                    }
                     case KeyEvent.VK_P -> {
                        if (ortho) {
                            projection = new Mat4OrthoRH(3, 2, 0.1, 10);
                            ortho = false;
                        } else {
                            projection = new Mat4PerspRH(Math.PI / 3,
                                    panel.getHeight() / (float) panel.getWidth(),
                                    0.1,
                                    25);
                            ortho = true;
                        }
                    }
                     case KeyEvent.VK_E -> {
                        if (renderer.isWireframe()) {
                            renderer.setWireframe(false);
                        } else {
                            renderer.setWireframe(true);
                        }

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

        renderer = new Renderer(triangleRasterizer);
        renderer.setProjection(projection);
        renderer.setView(camera.getViewMatrix());

        scene.addSolid(arX);
        scene.addSolid(arY);
        scene.addSolid(arZ);
        scene.addSolid(cube);
        scene.addSolid(pyramid);

        scene.renderAll(renderer);
        panel.repaint();
    }


    public void resetScene() {
        // resetuje kameru a projekci
        camera = defaultCamera;
        projection = new Mat4PerspRH(Math.PI / 3,
                panel.getHeight() / (float) panel.getWidth(), 0.1, 25);
        // resetuje režim střihu
        cuttingMode = 0;
        // resetuje osy a objekty
        axisList = AxisList.X;
        cube = new Cube();
        arX = new ArrowX();
        arY = new ArrowY();
        arZ = new ArrowZ();
        pyramid = new Pyramid();
        // resetuje renderer a scénu
        renderer = new Renderer(triangleRasterizer);
        scene = new Scene();
        // resetuje Z-buffer
        ZBuffer.getDepthBuffer().clear();
        // resetuje panel a překreslí scénu
        panel.clear();
        redraw();
    }



}
