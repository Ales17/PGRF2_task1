package model;

public class Arrow extends Solid{

    public Arrow() {
        // Vertex buffergetVertexBuffer().add(new Vertex(0, 0, 0));

        getVertexBuffer().add(new Vertex(0, 0, 0.2));
        getVertexBuffer().add(new Vertex(0.7, 0, 0.9));
        getVertexBuffer().add(new Vertex(1, 0, 0));
        getVertexBuffer().add(new Vertex(0.7, 0.3, 0));
        getVertexBuffer().add(new Vertex(0.7, -0.3, 0));
        // Index buffer
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
