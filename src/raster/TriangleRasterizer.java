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

    public TriangleRasterizer(ZBuffer zBuffer) {
        this.lerp = new Lerp<>();
        this.zBuffer = zBuffer;
    }

    public void rasterize(Vertex v1, Vertex v2, Vertex v3, Col color) {
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
        for (int y = (int) a.y; y <= b.y; y++) {
            double t1 = (y - a.y) / (b.y - a.y);
            int x1 = (int) ((1 - t1) * a.x + t1 * b.x);
            double t2 = (y - a.y) / (c.y - a.y);
            int x2 = (int) ((1 - t2) * a.x + t2 * c.x);
            if (x1 > x2) {
                int temp_x = x1;
                x1 = x2;
                x2 = temp_x;
            }
            double z = a.z + (b.z - a.z) * t1 + (c.z - a.z) * t2;
            for (int x = x1; x <= x2; x++) {
                // prevent division by zero
                if (x2 - x1 == 0) {
                    continue;
                }
                double t =  (x - x1) / (x2 - x1);
                Vertex v = lerp.lerp(v1, v2, t);
                zBuffer.drawWithZTest(x, y, z, color);
            }
        }

        for (int y = (int) b.y; y <= (int) c.y; y++) {
            double t1 = (y - b.y) / (c.y - b.y);
            int x1 = (int) ((1 - t1) * b.x + t1 * c.x);
            double t2 = (y - a.y) / (c.y - a.y);
            int x2 = (int) ((1 - t2) * a.x + t2 * c.x);
            if (x1 > x2) {
                int temp_x = x1;
                x1 = x2;
                x2 = temp_x;
            }
            double z = a.z + (b.z - a.z) * t1 + (c.z - a.z) * t2;
            for (int x = x1; x < x2; x++) {
                zBuffer.drawWithZTest(x, y, z, color);
            }
        }
    }
        private Vec3D transformToWindow(Point3D p) {
        return p.ignoreW().mul(new Vec3D(1, -1, 1)).add(new Vec3D(1, 1, 0)).mul(new Vec3D((zBuffer.getWidth() - 1) / 2., (zBuffer.getHeight() - 1) / 2., 1));
    }
}
