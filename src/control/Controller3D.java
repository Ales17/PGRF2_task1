package control;

import model.*;
import raster.ImageBuffer;
import raster.TriangleRasterizer;
import raster.ZBuffer;
import render.Renderer;
import render.Scene;
import shaders.ShaderFunctional;
import shaders.ShaderConstant;
import solid.*;
import transforms.*;
import view.Panel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Controller3D implements Controller {
    private final Panel panel;
    ShaderConstant shaderConst;
    double cameraSpeed = 0.1;
    double cameraRotationSpeed = 0.1;
    Renderer renderer;
    Mat4 projection = new Mat4Identity();

    Point2D prevPoint;
    AxisList axisList = AxisList.X;
    boolean ortho = false;
    Camera defaultCamera = new Camera()
            .withFirstPerson(true)
            .withPosition(new Vec3D(0.66, -1.70, 5.40))
            .withAzimuth(-5.00)
            .withZenith(-1.00)
            .withRadius(1.00);
    //Camera().withPosition(new Vec3D(-0.1, -0.4, 2.7)).withAzimuth(-5).withZenith(-1).withFirstPerson(true);
    Cube cube = new Cube();
     ArrowX arX = new ArrowX();
    ArrowY arY = new ArrowY();
    ArrowZ arZ = new ArrowZ();
    Pyramid pyramid = new Pyramid();
    CurveWire curveWire = new CurveWire();
    SpiralWire spiralWire = new SpiralWire();

    boolean isWireframeMode = false;

    private int width, height;
    private Camera camera = defaultCamera;
    private ZBuffer ZBuffer;
    private Scene scene;
    private TriangleRasterizer triangleRasterizer;
    // Active axis
    private RotationAxis rotationAxis = RotationAxis.X;

    public Controller3D(Panel panel) {
        this.panel = panel;
        // Měníme shader pomocí setteru
        //triangleRasterizer.setShader(new ShaderInterpolatedColor());
        initObjects(panel.getRaster());
        initListeners(panel);
        redraw();

        ShaderFunctional greenShaderFunctional = v -> {
            return new Col(0x00ff00);
        };
        Col color = new Col(0x00ff00);
        ShaderFunctional colorShaderFunctional = v -> new Col(color);

    }

    public void rotate(RotationAxis rotationAxis, double angle) {
        Mat4 rotation = new Mat4Rot(angle, rotationAxis.getAxis());
        for (Solid solid : scene.getSolids()) {
            solid.transform(rotation);
        }
    }

    public void initObjects(ImageBuffer raster) {
        raster.setClearValue(new Col(0xff0000));
        ZBuffer = new ZBuffer(panel.getRaster());
        projection = new Mat4PerspRH(Math.PI / 3, panel.getHeight() / (float) panel.getWidth(), 0.1, 25);
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

                switch (e.getKeyCode()) {
                    //XYZ
                    case KeyEvent.VK_X -> {
                        axisList = AxisList.X;
                    }
                    case KeyEvent.VK_Y -> {
                        axisList = AxisList.Y;
                    }
                    case KeyEvent.VK_Z -> {
                        axisList = AxisList.Z;
                    }
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
                            projection = new Mat4PerspRH(Math.PI / 3, panel.getHeight() / (float) panel.getWidth(), 0.1, 25);
                            ortho = true;
                        }
                    }
                    case KeyEvent.VK_LEFT -> {
                        rotate(-1);

                    }
                    case KeyEvent.VK_RIGHT -> {
                        rotate(1);

                    }
                    case KeyEvent.VK_E -> {
              isWireframeMode = !isWireframeMode;
                    }
                }


                panel.clear();
                redraw();
            }
        });


    }

    private void rotate(int rot) {
        switch (axisList) {
            case X -> {
                rotate(RotationAxis.X, cameraRotationSpeed * rot);
            }
            case Y -> {
                rotate(RotationAxis.Y, cameraRotationSpeed * rot);
            }
            case Z -> {
                rotate(RotationAxis.Z, cameraRotationSpeed * rot);
            }
        }
    }

    private void redraw() {
        width = panel.getRaster().getWidth();
        height = panel.getRaster().getHeight();
        triangleRasterizer.getzBuffer().getDepthBuffer().clear();
        renderer = new Renderer(triangleRasterizer);
        renderer.setWireframe(isWireframeMode);
        renderer.setProjection(projection);
        renderer.setView(camera.getViewMatrix());
        this.scene = new Scene();
        scene.addSolid(arX);
        scene.addSolid(arY);
        scene.addSolid(arZ);
        scene.addSolid(cube);
        scene.addSolid(pyramid);
        scene.addSolid(spiralWire);

        renderer.render(scene);

        panel.repaint();
    }


    public void resetScene() {
        // resetuje kameru a projekci
        camera = defaultCamera;
        projection = new Mat4PerspRH(Math.PI / 3, panel.getHeight() / (float) panel.getWidth(), 0.1, 25);
        // resetuje režim střihu
        // reset - osy a objekty
        axisList = AxisList.X;
        cube = new Cube();
        arX = new ArrowX();
        arY = new ArrowY();
        arZ = new ArrowZ();
        pyramid = new Pyramid();
        spiralWire = new SpiralWire();
        // resetuje renderer a scénu
        renderer = new Renderer(triangleRasterizer);
        renderer.setWireframe(isWireframeMode);
        scene = new Scene();
        // resetuje Z-buffer
        ZBuffer.getDepthBuffer().clear();
        // resetuje panel a překreslí scénu
        panel.clear();
        redraw();
    }


}
