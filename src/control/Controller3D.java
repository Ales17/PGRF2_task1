package control;

import model.Arrow;
import model.Solid;
import model.TestModel;
import model.Vertex;
import raster.*;
import render.Renderer;
import transforms.Col;
import transforms.Point3D;
import view.Panel;

import java.awt.event.*;

public class Controller3D implements Controller {
    private final Panel panel;

    private final ZBuffer zBuffer;
    private final TriangleRasterizer triangleRasterizer;
    private final LineRasterizer lineRasterizer;
    private final Renderer renderer;

    public Controller3D(Panel panel) {
        this.panel = panel;
        this.zBuffer = new ZBuffer(panel.getRaster());
        this.triangleRasterizer = new TriangleRasterizer(zBuffer);
        this.lineRasterizer = new LineRasterizer(zBuffer);
        this.renderer = new Renderer(triangleRasterizer, lineRasterizer);
        initObjects(panel.getRaster());
        initListeners();
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
  /* DEBUG
  Vertex l11 = new Vertex(1, 1, 0.4, new Col(0xff0000));
     Vertex l12 = new Vertex(-1, -.7, 0.4, new Col(0xff0000));
        lineRasterizer.rasterize( l11, l12 );

        Vertex t11 = new Vertex(-1, -1, 0.8); // Bottom left
        Vertex t12 = new Vertex(0, -1, 0.2); // Bottom right
        Vertex t13 = new Vertex(-1, 0, 0.9); // Top
        triangleRasterizer.rasterize(t11, t12, t13, new Col(0x00ff00));

       */


        Solid arrow = new Arrow();

        renderer.render(arrow);
        panel.repaint();
    }

}
