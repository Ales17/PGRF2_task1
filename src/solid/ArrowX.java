package solid;

import model.Part;
import model.TopologyType;
import model.Vertex;
import transforms.Col;

public class ArrowX extends Solid {

    public ArrowX() {

        getVertexBuffer().add(new Vertex(0, 0, 0, new Col(0xff0000)));
        getVertexBuffer().add(new Vertex(1, 0, 0, new Col(0xff0000)));
        getVertexBuffer().add(new Vertex(1.0, 0.0, 0.0, new Col(0xff0000)));
        getVertexBuffer().add(new Vertex(0.7, 0.1, 0.0, new Col(0xff0000)));
        getVertexBuffer().add(new Vertex(0.7, -0.1, 0.0, new Col(0xff0000)));
        // LINE
        getIndexBuffer().add(0);
        getIndexBuffer().add(1);
        // TRIANGLE
        getIndexBuffer().add(2);
        getIndexBuffer().add(3);
        getIndexBuffer().add(4);
        // Part buffer
        getPartBuffer().add(new Part(TopologyType.LINE, 0, 1));
        getPartBuffer().add(new Part(TopologyType.TRIANGLE, 2, 1));

    }
}
