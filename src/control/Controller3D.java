package control;

import model.*;
import raster.*;
import render.*;
import render.Renderer;
import solid.*;
import transforms.*;
import view.Panel;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class Controller3D implements Controller {
    // Settings - default values
    final Camera defaultCamera = new Camera().withFirstPerson(true).withPosition(new Vec3D(0.66, -1.70, 5.40)).withAzimuth(-5.00).withZenith(-1.00).withRadius(1.00);
    private final Panel panel;
    JCheckBox checkBoxWireframe = new JCheckBox("Wireframe");
    JCheckBox checkBoxOrthogonal = new JCheckBox("Orthogonal");
    JPanel controlPanel = new JPanel();
    JRadioButton radioX = new JRadioButton("X");
    JRadioButton radioY = new JRadioButton("Y");
    JRadioButton radioZ = new JRadioButton("Z");
    Renderer renderer;
    double CAM_SPEED = 0.1;
    RotationAxis rotationAxis = RotationAxis.X;
    int selectedObjectIndex = 0;
    boolean isSceneRotated = true;
    boolean orthogonalProjection = false;
    boolean isWireframeMode = false;
    Mat4 projection = new Mat4Identity();
    // Objects
    ArrowX arX = new ArrowX();
    ArrowY arY = new ArrowY();
    ArrowZ arZ = new ArrowZ();
    Cube cube = new Cube();
    Pyramid pyramid = new Pyramid();
    Spiral spiral = new Spiral();
    Point2D prevPoint;
    private Camera camera = defaultCamera;
    private List<Solid> rotateList = new ArrayList<>();
    private int width, height;
    private ZBuffer ZBuffer;
    private Scene scene;
    private TriangleRasterizer triangleRasterizer;

    public Controller3D(Panel panel) {
        this.panel = panel;
        // TODO Changing shader with setter - triangleRasterizer.setShader(new ShaderInterpolatedColor());
        initObjects(panel.getRaster());
        radioX.setSelected(true);
        initListeners(panel);
        redraw();
    }

    private void toggleObject() {
        selectedObjectIndex = (selectedObjectIndex >= rotateList.size() - 1) ? 0 : selectedObjectIndex + 1;
    }

    private void prepareRotation(boolean negativeDirection, int selectedObjectIndex) {
        int dir = negativeDirection ? -1 : 1;
        RotationAxis axis = null;
        switch (rotationAxis) {
            case X -> axis = RotationAxis.X;
            case Y -> axis = RotationAxis.Y;
            case Z -> axis = RotationAxis.Z;
        }
        rotate(axis, CAM_SPEED * dir, isSceneRotated ? null : rotateList.get(selectedObjectIndex));
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

                    // Cam movement WSAD
                    case KeyEvent.VK_W -> camera = camera.forward(CAM_SPEED);
                    case KeyEvent.VK_S -> camera = camera.backward(CAM_SPEED);
                    case KeyEvent.VK_A -> camera = camera.left(CAM_SPEED);
                    case KeyEvent.VK_D -> camera = camera.right(CAM_SPEED);
                    // Cam UP, DOWN
                    case KeyEvent.VK_UP -> camera = camera.up(CAM_SPEED);
                    case KeyEvent.VK_DOWN -> camera = camera.down(CAM_SPEED);
                    //
                    case KeyEvent.VK_C -> resetScene();
                    case KeyEvent.VK_NUMPAD2 -> prepareRotation(true, selectedObjectIndex);
                    case KeyEvent.VK_NUMPAD8 -> prepareRotation(false, selectedObjectIndex);
                    case KeyEvent.VK_G -> isSceneRotated = !isSceneRotated;
                    case KeyEvent.VK_H -> toggleObject();
                    case KeyEvent.VK_O -> {
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

        checkBoxWireframe.addActionListener(e -> handleAction(!isWireframeMode, rotationAxis));
        radioX.addActionListener(e -> handleAction(isWireframeMode, RotationAxis.X));
        radioY.addActionListener(e -> handleAction(isWireframeMode, RotationAxis.Y));
        radioZ.addActionListener(e -> handleAction(isWireframeMode, RotationAxis.Z));


    }

    private void handleAction(boolean isWireframeMode, RotationAxis rotationAxis) {
        this.isWireframeMode = isWireframeMode;
        this.rotationAxis = rotationAxis;
        panel.requestFocus();
        panel.clear();
        redraw();
    }

    private void redraw() {
        controlPanel.add(checkBoxOrthogonal);
        controlPanel.add(checkBoxWireframe);

        panel.add(controlPanel);

        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(radioX);
        radioGroup.add(radioY);
        radioGroup.add(radioZ);
        controlPanel.add(new JLabel("Rotation Axis: "));
        controlPanel.add(radioX);
        controlPanel.add(radioY);
        controlPanel.add(radioZ);
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
        rotateList.add(cube);
        rotateList.add(pyramid);
        rotateList.add(spiral);
        renderer.render(scene);
        panel.repaint();
    }


    public void resetScene() {
        camera = defaultCamera;
        projection = new Mat4PerspRH(Math.PI / 3, panel.getHeight() / (float) panel.getWidth(), 0.1, 25);
        rotationAxis = RotationAxis.X;
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