package model;

public class TestModel extends Solid {

    public TestModel() {  getVertexBuffer().add(new Vertex(0, 0, 0));
                   getVertexBuffer().add(new Vertex(-.5, 0, 0));
                     getVertexBuffer().add(new Vertex(0, 1, 0));
            getVertexBuffer().add(new Vertex(0, 0, 0));
            getVertexBuffer().add(new Vertex(.5, 0, 0));
            getVertexBuffer().add(new Vertex(0, 1, 0));

            getIndexBuffer().add(0);
            getIndexBuffer().add(1);
            getIndexBuffer().add(2);
            getIndexBuffer().add(3);
            getIndexBuffer().add(4);
            getIndexBuffer().add(5);


            getPartBuffer().add(new Part(TopologyType.TRIANGLE, 0, 1));
            getPartBuffer().add(new Part(TopologyType.TRIANGLE, 3, 1));


    }
}
