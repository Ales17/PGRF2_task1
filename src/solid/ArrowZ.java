package solid;

import model.Part;
import model.TopologyType;
import model.Vertex;
import transforms.Col;

public class ArrowZ extends Solid{
    public ArrowZ() {
        getVertexBuffer().add(new Vertex(0,0,0,new Col(0x0000ff)));//v1
        getVertexBuffer().add(new Vertex(0,0,1,new Col(0x0000ff)));//v1

        getVertexBuffer().add(new Vertex(0,0.1,0.7,new Col(0x0000ff)));//v1
        getVertexBuffer().add(new Vertex(0,-0.1,0.7,new Col(0x0000ff)));//v1
        // triangle rotated 45 degrees

        // LINE
        getIndexBuffer().add(0);
        getIndexBuffer().add(1);
        // TRIANGLE
        getIndexBuffer().add(1);
        getIndexBuffer().add(2);
        getIndexBuffer().add(3);

        // Part buffer
        getPartBuffer().add(new Part(TopologyType.LINE,0,1));
        getPartBuffer().add(new Part(TopologyType.TRIANGLE,2,1));
    }
}
