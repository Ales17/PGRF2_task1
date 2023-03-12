package solid;

import model.Part;
import model.TopologyType;
import model.Vertex;
import transforms.Cubic;
import transforms.Point3D;

public class CurveWire extends Solid {
    public CurveWire() {
        // řídící body
        Point3D points[] = new Point3D[4];
        points[0] = new Point3D(-1, -1, -1);
        points[1] = new Point3D(1, 1, -1);
        points[2] = new Point3D(1, -1, 1);
        points[3] = new Point3D(-1, 1, 1);


        // výpočet bodů
        Cubic cubic = new Cubic(Cubic.FERGUSON, points);

        // přidání nultého bodu
        Vertex v = new Vertex(pointToVertex(cubic.compute(0)));
        getVertexBuffer().add(v);

        // přidání bodů na křivce
        for (int i = 0; i < 101; i++) {
            double t = i / 100.0;

            v = new Vertex(pointToVertex(cubic.compute(t)));
            getVertexBuffer().add(v);
            getIndexBuffer().add(getVertexBuffer().size() - 2);
            getIndexBuffer().add(getVertexBuffer().size() - 1);
        }

        // line strip index buffer
        for (int i = 0; i < 100; i++) {
            getIndexBuffer().add(i);
        }

        // části
        getPartBuffer().add(new Part(TopologyType.LINE_STRIP, 0, 100));
    }

    private Vertex pointToVertex(Point3D compute) {
        // Převedeme bod na vertex
        return new Vertex(compute);
    }
}
