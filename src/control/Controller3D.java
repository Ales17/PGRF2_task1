package control;

import raster.ImageBuffer;
import raster.Raster;
import raster.TriangleRasterizer;
import raster.ZBuffer;
import transforms.Col;
import transforms.Point3D;
import view.Panel;

import java.awt.event.*;

public class Controller3D implements Controller {
    private final Panel panel;

    private final ZBuffer zBuffer;
    private final TriangleRasterizer triangleRasterizer;
    public Controller3D(Panel panel) {
        this.panel = panel;
        this.zBuffer = new ZBuffer(panel.getRaster());
        this.triangleRasterizer = new TriangleRasterizer(zBuffer);
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

        triangleRasterizer.rasterize(
                new Point3D(-1.0, -1.0, 0.1),
                new Point3D(1.0, 0.0, 0.3),
                new Point3D(0.0, 1.0, 0.5),
        new Col(0x2FFF00));

        triangleRasterizer.rasterize(
                new Point3D(1.0, 1.0, 0.3),
                new Point3D(-1.0, 0.0, 0.3),
                new Point3D(0.0, -1.0, 0.3),
        new Col(0x2FFFFF));
       panel.repaint();
    }

}
