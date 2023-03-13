package model;

import model.Part;
import model.TopologyType;
import model.Vertex;
import solid.Solid;
import transforms.Point3D;

public class Spiral extends Solid {
    public Spiral() {
        // parametry spiraly
        double a = 0.3;
        double b = 0.3;
        double c = 0.1;

        // řídící body bikubické křivky
        Point3D points[] = new Point3D[4];
        points[0] = new Point3D(-b, 0, 0);
        points[1] = new Point3D(-b/2, b/2, c/2);
        points[2] = new Point3D(b/2, -b/2, c);
        points[3] = new Point3D(b, 0, 1.5*c);

        // vytvoreni bodu na spirale
        for (int i = 0; i < 101; i++) {
            double t = i / 10.0;
            double x = a * Math.cos(t) + 2.5;
            double y = b * Math.sin(t)+ 2.5;
            double z = c * t;
            Vertex v = new Vertex(new Point3D(x, y, z));
            getVertexBuffer().add(v);
            if (i > 0) {
                getIndexBuffer().add(getVertexBuffer().size() - 2);
                getIndexBuffer().add(getVertexBuffer().size() - 1);
            }
        }
        // index buffer
        for (int i = 0; i < 200; i++) {
            getIndexBuffer().add(i);
        }
        // části
        getPartBuffer().add(new Part(TopologyType.LINE_STRIP, 0, 200));
    }
}
