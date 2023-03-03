package raster;

import model.Vertex;
import transforms.Col;
import transforms.Point3D;
import transforms.Vec3D;

public class LineRasterizer {
    private final ZBuffer zBuffer;

    public LineRasterizer(ZBuffer zBuffer) {
        this.zBuffer = zBuffer;
    }

    public void rasterize(Vertex v1, Vertex v2) {
        Vec3D a = transformToWindow(v1.getPosition());
        Vec3D b = transformToWindow(v2.getPosition());
        int x1 = (int) a.x;
        int y1 = (int) a.y;
        int x2 = (int) b.x;
        int y2 = (int) b.y;
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;
        Col color = new Col(0xff0000); // = v1.getColor();
        // draw with z test
        while (true) {
            if (x1 == x2 && y1 == y2) break;
            int e2 = 2 * err;
            if (e2 > -dy) { err -= dy; x1 += sx; }
            if (e2 < dx) { err += dx; y1 += sy; }
            zBuffer.drawWithZTest(x1, y1, v1.getPosition().z, color);
        }
    }






    private Vec3D transformToWindow(Point3D p) {
        return p.ignoreW()
                .mul(new Vec3D(1, -1, 1))
                .add(new Vec3D(1, 1, 0))
                .mul(new Vec3D((zBuffer.getWidth() - 1) / 2., (zBuffer.getHeight() - 1) / 2., 1));
    }
}
