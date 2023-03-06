
package control;

import model.*;
import raster.*;
import render.Renderer;
import transforms.*;
import view.Panel;

import java.awt.event.*;

public class Controller3D implements Controller {
    private final Panel panel; //// Panel aplikace
    private int width, height; //// Velikost panelu
    private final ZBuffer zBuffer;
    private final TriangleRasterizer triangleRasterizer;
    private final LineRasterizer lineRasterizer;
    Renderer renderer;


    private Mat4 model = new Mat4Identity();
    private Camera camera;
    private double cameraSpeed = 0.1;
    private int oldAz, oldZen;
    private int x, y, z;
    public Controller3D(Panel panel) {
        this.panel = panel;
        this.zBuffer = new ZBuffer(panel.getRaster());
        this.triangleRasterizer = new TriangleRasterizer(zBuffer);
        this.lineRasterizer = new LineRasterizer(zBuffer);
        this.renderer = new Renderer(triangleRasterizer, lineRasterizer);
        initObjects(panel.getRaster());
        initListeners();
        this.camera = new Camera();

        oldAz = 15;
        oldZen = -20;
        x = -8;
        y = -2;
        z = 3;

        panel.requestFocus();
        panel.requestFocusInWindow();
        redraw();
    }

    public void initObjects(ImageBuffer raster) {
        raster.setClearValue(new Col(0x101010));
    }

    @Override
    public void initListeners() {
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                panel.resize();
                initObjects(panel.getRaster());
            }
        });

    }
    private void redraw() {

        panel.clear();

// debug
//  Vertex l11 = new Vertex(1, 1, 0.4, new Col(0xff0000));
//     Vertex l12 = new Vertex(-1, -.7, 0.4, new Col(0xff0000));
//        lineRasterizer.rasterize( l11, l12 );
//
//        Vertex t11 = new Vertex(-1, -1, 0.8); // Bottom left
//        Vertex t12 = new Vertex(0, -1, 0.2); // Bottom right
//        Vertex t13 = new Vertex(-1, 0, 0.9); // Top
//        triangleRasterizer.rasterize(t11, t12, t13, new Col(0x00ff00));








        Solid arrow = new Arrow();
        Solid cube = new Cube();
        Solid triangle = new Triangle();



        Mat4 viewMatrix = camera.getViewMatrix();



        renderer.render(cube);
        renderer.render(triangle);

        panel.repaint();
    }

}



