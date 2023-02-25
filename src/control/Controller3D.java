package control;

import model.Arrow;
import model.Solid;
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
        /*
        lineRasterizer.rasterize(
                new Point3D(1.0, -1.0, 0.5),
                new Point3D(-1.0, 1.0, 0.5),
                new Col(0xff0000));
        triangleRasterizer.rasterize(
                new Point3D(1.0, 1.0, 0.6),
                new Point3D(-1.0, 0.0, 0.2),
                 new Point3D(0.0, -1.0, 0.6)
        , new Col(0x00FFa0));
*/


              Solid arrow = new Arrow();
         renderer.render(arrow);
        panel.repaint();
    }

}
