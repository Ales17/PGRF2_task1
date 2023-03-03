package model;

import transforms.Col;

public class Cube extends Solid{

    public Cube() {
        // Vertex buffer
        getVertexBuffer().add(new Vertex(-0.5, -0.5, -0.5, new Col(0x0000ff))); // 0
        getVertexBuffer().add(new Vertex(0.5, -0.5, -0.5, new Col(0x0000ff))); // 1
        getVertexBuffer().add(new Vertex(0.5, 0.5, -0.5, new Col(0x0000ff))); // 2
        getVertexBuffer().add(new Vertex(-0.5, 0.5, -0.5, new Col(0x0000ff))); // 3
        getVertexBuffer().add(new Vertex(-0.5, -0.5, 0.5, new Col(0x00ff00))); // 4
        getVertexBuffer().add(new Vertex(0.5, -0.5, 0.5, new Col(0x00ff00))); // 5
        getVertexBuffer().add(new Vertex(0.5, 0.5, 0.5, new Col(0x00ff00))); // 6
        getVertexBuffer().add(new Vertex(-0.5, 0.5, 0.5, new Col(0x00ff00))); // 7

        // Index buffer
        // LINE
        getIndexBuffer().add(0);
        getIndexBuffer().add(1);
        getIndexBuffer().add(1);
        getIndexBuffer().add(2);
        getIndexBuffer().add(2);
        getIndexBuffer().add(3);
        getIndexBuffer().add(3);
        getIndexBuffer().add(0);

        getIndexBuffer().add(0);
        getIndexBuffer().add(4);
        getIndexBuffer().add(1);
        getIndexBuffer().add(5);
        getIndexBuffer().add(2);
        getIndexBuffer().add(6);
        getIndexBuffer().add(3);
        getIndexBuffer().add(7);

        getIndexBuffer().add(4);
        getIndexBuffer().add(5);
        getIndexBuffer().add(5);
        getIndexBuffer().add(6);
        getIndexBuffer().add(6);
        getIndexBuffer().add(7);
        getIndexBuffer().add(7);
        getIndexBuffer().add(4);


        // Part buffer
        getPartBuffer().add(new Part(TopologyType.LINE, 0, 12));
    }
}
