package raster;

import model.Vertex;
import transforms.Col;
import transforms.Point3D;
import transforms.Vec3D;
import utils.Lerp;

import java.awt.*;

public class TriangleRasterizer {
    private final ZBuffer zBuffer;
    // LERP pro interpolace
    private final Lerp<Vertex> lerp;
    private final int width;
    private final int height;
    private int cuttingMode = 1;
    public void setCuttingMode(int cuttingMode) {
        this.cuttingMode = cuttingMode;
    }

    public TriangleRasterizer(ZBuffer zBuffer) {
        this.lerp = new Lerp<>();
        this.zBuffer = zBuffer;
        width = zBuffer.getImageBuffer().getWidth();
        height = zBuffer.getImageBuffer().getHeight();
    }

    public void rasterize(Vertex v1, Vertex v2, Vertex v3 ) {
        Vec3D a, b, c;

        if (v1.getPosition().getY() >= v2.getPosition().getY()) {
            if (v1.getPosition().getY() >= v3.getPosition().getY()) {
                a = transformToWindow(v1.getPosition());
                if (v2.getPosition().getY() >= v3.getPosition().getY()) {
                    b = transformToWindow(v2.getPosition());
                    c = transformToWindow(v3.getPosition());
                } else {
                    b = transformToWindow(v3.getPosition());
                    c = transformToWindow(v2.getPosition());
                }
            } else {
                a = transformToWindow(v3.getPosition());
                b = transformToWindow(v1.getPosition());
                c = transformToWindow(v2.getPosition());
            }
        } else {
            if (v2.getPosition().getY() >= v3.getPosition().getY()) {
                a = transformToWindow(v2.getPosition());
                if (v1.getPosition().getY() >= v3.getPosition().getY()) {
                    b = transformToWindow(v1.getPosition());
                    c = transformToWindow(v3.getPosition());
                } else {
                    b = transformToWindow(v3.getPosition());
                    c = transformToWindow(v1.getPosition());
                }
            } else {
                a = transformToWindow(v3.getPosition());
                b = transformToWindow(v2.getPosition());
                c = transformToWindow(v1.getPosition());
            }
        }
        for (int y = (int) a.getY(); y <= b.getY(); y++) {
            double t1 = (y - a.getY()) / (b.getY() - a.getY());
            int x1 = (int) ((1 - t1) * a.getX() + t1 * b.getX());
            double t2 = (y - a.getY()) / (c.getY() - a.getY());
            int x2 = (int) ((1 - t2) * a.getX() + t2 * c.getX());
            if (x1 > x2) {
                int temp_x = x1;
                x1 = x2;
                x2 = temp_x;
            }
            double z = a.getZ() + (b.getZ() - a.getZ()) * t1 + (c.getZ() - a.getZ()) * t2;
            for (int x = x1; x <= x2; x++) {
                // prevent division by zero
                if (x2 - x1 == 0) {
                    continue;
                }
                double t =  (x - x1) / (x2 - x1);
                Vertex v = lerp.lerp(v1, v2, t);
                zBuffer.drawWithZTest(x, y, z,  v.getColor());
            }
        }

        for (int y = (int) b.getY(); y <= (int) c.getY(); y++) {
            double t1 = (y - b.getY()) / (c.getY() - b.getY());
            int x1 = (int) ((1 - t1) * b.getX() + t1 * c.getX());
            double t2 = (y - a.getY()) / (c.getY() - a.getY());
            int x2 = (int) ((1 - t2) * a.getX() + t2 * c.getX());
            if (x1 > x2) {
                int temp_x = x1;
                x1 = x2;
                x2 = temp_x;
            }
            double z = a.getZ() + (b.getZ() - a.getZ()) * t1 + (c.getZ() - a.getZ()) * t2;

            for (int x = x1; x < x2; x++) {
                zBuffer.drawWithZTest(x, y, z,    lerp.lerp(v1, v2, t1).getColor()  );
            }
        }
    }
        private Vec3D transformToWindow(Point3D p) {
        return p.ignoreW().mul(new Vec3D(1, -1, 1)).add(new Vec3D(1, 1, 0)).mul(new Vec3D((zBuffer.getWidth() - 1) / 2., (zBuffer.getHeight() - 1) / 2., 1));
    }
}
