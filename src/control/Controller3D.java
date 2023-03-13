package control;

import model.*;
import raster.*;
import render.*;
import solid.*;
import transforms.*;
import view.Panel;

import java.awt.event.*;
import java.util.*;

public class Controller3D implements Controller {
    private final Panel panel;
    boolean isSceneRotated = true;
    double cameraSpeed = 0.1;
    boolean orthogonalProjection = false;
    boolean isWireframeMode = false;
    Renderer renderer;
    Mat4 projection = new Mat4Identity();
    ArrowX arX = new ArrowX();
    ArrowY arY = new ArrowY();
    ArrowZ arZ = new ArrowZ();
    Cube cube = new Cube();
    Pyramid pyramid = new Pyramid();
    Spiral spiral = new Spiral();
    int selectedObjectIndex = 0;
    Point2D prevPoint;
    AxisList selectedAxis = AxisList.X;
    final Camera defaultCamera = new Camera().withFirstPerson(true).withPosition(new Vec3D(0.66, -1.70, 5.40)).withAzimuth(-5.00).withZenith(-1.00).withRadius(1.00);
    private List<Solid> objects = new ArrayList<>();
    private int width, height;
    private Camera camera = defaultCamera;
    private ZBuffer ZBuffer;
    private Scene scene;
    private TriangleRasterizer triangleRasterizer;

    public Controller3D(Panel panel) {
        this.panel = panel;
        // TODO Changing shader with setter - triangleRasterizer.setShader(new ShaderInterpolatedColor());
        initObjects(panel.getRaster());
        initListeners(panel);
        redraw();
    }

    private void toggleObject() {
        selectedObjectIndex = (selectedObjectIndex >= objects.size() - 1) ? 0 : selectedObjectIndex + 1;
    }

    private void prepareRotation(boolean negativeDirection, int selectedObjectIndex) {
        int dir = negativeDirection ? -1 : 1;
        Solid selectedObject = objects.get(selectedObjectIndex);
        RotationAxis axis = null;
        switch (selectedAxis) {
            case X -> axis = RotationAxis.X;
            case Y -> axis = RotationAxis.Y;
            case Z -> axis = RotationAxis.Z;
        }
        if (isSceneRotated) {
            rotate(axis, cameraSpeed * dir, null);
        } else {
            rotate(axis, cameraSpeed * dir, selectedObject);
        }
    }


    public void rotate(RotationAxis rotationAxis, double angle, Solid rotatedSolid) {
        Mat4 rotation = new Mat4Rot(angle, rotationAxis.getAxis());
        if (rotatedSolid != null) {
            rotateSolid(rotatedSolid, rotation);
        } else {
            for (Solid s : scene.getSolids()) {
                rotateSolid(s, rotation);
            }
        }
    }

    public void rotateSolid(Solid solid, Mat4 rotation) {
        solid.transform(rotation);
    }


    public void initObjects(ImageBuffer raster) {
        raster.setClearValue(new Col(0xff0000));
        ZBuffer = new ZBuffer(panel.getRaster());
        projection = new Mat4PerspRH(Math.PI / 3, panel.getHeight() / (float) panel.getWidth(), 0.1, 25);
        scene = new Scene();
        triangleRasterizer = new TriangleRasterizer(ZBuffer);
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
                    // Todo use gui for some of these
                    //XYZ
                    case KeyEvent.VK_X -> selectedAxis = AxisList.X;
                    case KeyEvent.VK_Y -> selectedAxis = AxisList.Y;
                    case KeyEvent.VK_Z -> selectedAxis = AxisList.Z;
                    // Cam movement WSAD
                    case KeyEvent.VK_W -> camera = camera.forward(cameraSpeed);
                    case KeyEvent.VK_S -> camera = camera.backward(cameraSpeed);
                    case KeyEvent.VK_A -> camera = camera.left(cameraSpeed);
                    case KeyEvent.VK_D -> camera = camera.right(cameraSpeed);
                    // Cam UP, DOWN
                    case KeyEvent.VK_UP -> camera = camera.up(cameraSpeed);
                    case KeyEvent.VK_DOWN -> camera = camera.down(cameraSpeed);
                    //
                    case KeyEvent.VK_C -> resetScene();
                    case KeyEvent.VK_LEFT -> prepareRotation(true, selectedObjectIndex);
                    case KeyEvent.VK_RIGHT -> prepareRotation(false, selectedObjectIndex);
                    case KeyEvent.VK_F -> isWireframeMode = !isWireframeMode;
                    case KeyEvent.VK_G -> isSceneRotated = !isSceneRotated;
                    case KeyEvent.VK_H -> toggleObject();
                    case KeyEvent.VK_J -> {
                        if (orthogonalProjection) {
                            projection = new Mat4OrthoRH(3, 2, 0.1, 10);
                            orthogonalProjection = false;
                        } else {
                            projection = new Mat4PerspRH(Math.PI / 3, panel.getHeight() / (float) panel.getWidth(), 0.1, 25);
                            orthogonalProjection = true;
                        }
                    }
                }
                panel.clear();
                redraw();
            }
        });
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
        scene.addSolid(spiral);
        objects.add(cube);
        objects.add(pyramid);
        objects.add(spiral);
        renderer.render(scene);
        panel.repaint();
        System.out.println(scene.getSolids());
    }


    public void resetScene() {
        camera = defaultCamera;
        projection = new Mat4PerspRH(Math.PI / 3, panel.getHeight() / (float) panel.getWidth(), 0.1, 25);
        selectedAxis = AxisList.X;
        cube = new Cube();
        arX = new ArrowX();
        arY = new ArrowY();
        arZ = new ArrowZ();
        pyramid = new Pyramid();
        spiral = new Spiral();
        renderer = new Renderer(triangleRasterizer);
        renderer.setWireframe(isWireframeMode);
        scene = new Scene();
        ZBuffer.getDepthBuffer().clear();
        panel.clear();
        redraw();
    }
}