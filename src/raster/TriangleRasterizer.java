package raster;

import transforms.Col;
import transforms.Point3D;
import transforms.Vec3D;

import java.awt.*;

public class TriangleRasterizer {
    private final ZBuffer zBuffer;

    public TriangleRasterizer(ZBuffer zBuffer) {

        this.zBuffer = zBuffer;
    }
    // COL TO awt COLOR


    public void rasterize(Point3D p1, Point3D p2, Point3D p3, Col color) {
        // Transformace do okna
        Vec3D a = transformToWindow(p1);
        Vec3D b = transformToWindow(p2);
        Vec3D c = transformToWindow(p3);

        // Debug účely
        /*
        Graphics graphics = zBuffer.getImageBuffer().getGraphics();
        graphics.setColor(new Color(0x2FFF00));

       graphics.drawLine((int) a.x, (int) a.y, (int) b.x, (int) b.y);
       graphics.drawLine((int) b.x, (int) b.y, (int) c.x, (int) c.y);
        graphics.drawLine((int) c.x, (int) c.y, (int) a.x, (int) a.y);*/

        if (p1.y >= p2.y) {
            if (p1.y >= p3.y) {
                a = transformToWindow(p1);
                if (p2.y >= p3.y) {
                    b = transformToWindow(p2);
                    c = transformToWindow(p3);
                } else {
                    b = transformToWindow(p3);
                    c = transformToWindow(p2);
                }
            } else {
                a = transformToWindow(p3);
                b = transformToWindow(p1);
                c = transformToWindow(p2);

            }
        } else {
            if (p2.y >= p3.y) {
                a = transformToWindow(p2);
                if (p1.y >= p3.y) {
                    b = transformToWindow(p1);
                    c = transformToWindow(p3);

                } else {
                    b = transformToWindow(p3);
                    c = transformToWindow(p1);
                }
            } else {
                a = transformToWindow(p3);
                b = transformToWindow(p2);
                c = transformToWindow(p1);
            }
        }


        for (int y = (int) a.y; y <= b.y; y++) {
            // interpolační koeficient pro AB
            double t1 = (y - a.y) / (b.y - a.y);
            int x1 = (int) ((1 - t1) * a.x + t1 * b.x);

            // interpolační koeficient pro AC
            double t2 = (y - a.y) / (c.y - a.y);
            int x2 = (int) ((1 - t2) * a.x + t2 * c.x);

            if (x1 > x2) {
                int temp_x = x1;
                x1 = x2;
                x2 = temp_x;
            }
            // TODO: spočítat z hodnotu

            double z = a.z + (b.z - a.z) * t1 + (c.z - a.z) * t2;


            // vykreslit pixely mezi x1 a x2
            // TODO: pozor, x1 < x2
            for (int x = x1; x <= x2; x++) {
                //         zBuffer.drawWithZTest(x, y, 0.3,color);
                zBuffer.drawWithZTest(x, y, z, color);
                // TODO interpolace celého vertexu
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
                // Tady tu hodnotu Z už nevidí
                zBuffer.drawWithZTest(x, y, z, color);
            }
        }

    }

    private Vec3D transformToWindow(Point3D p) {
        return p.ignoreW().mul(new Vec3D(1, -1, 1)).add(new Vec3D(1, 1, 0)).mul(new Vec3D((zBuffer.getWidth() - 1) / 2., (zBuffer.getHeight() - 1) / 2., 1));
    }
}
