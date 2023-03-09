package model;

import solid.Solid;
import transforms.Col;

public class Pyramid extends Solid {


    public Pyramid() {



            getVertexBuffer().add(new Vertex(0,0,0,new Col(0,1,0)));//v1
            getVertexBuffer().add(new Vertex(0.3,0,0,new Col(0,1,1)));//v2
            getVertexBuffer().add(new Vertex(0.3,0.3,0,new Col(0,1,0)));//v3
            getVertexBuffer().add(new Vertex(0,0.3,0,new Col(0,1,0)));//v4
            getVertexBuffer().add(new Vertex(0.15,0.15,0.3,new Col(0,1,0)));//v5

            getIndexBuffer().add(0);
            getIndexBuffer().add(1);
            getIndexBuffer().add(2);

            getIndexBuffer().add(0);
            getIndexBuffer().add(2);
            getIndexBuffer().add(3);

            getIndexBuffer().add(0);
            getIndexBuffer().add(3);
            getIndexBuffer().add(4);

            getIndexBuffer().add(0);
            getIndexBuffer().add(4);
            getIndexBuffer().add(1);

            getIndexBuffer().add(1);
            getIndexBuffer().add(2);
            getIndexBuffer().add(4);

            getIndexBuffer().add(2);
            getIndexBuffer().add(3);
            getIndexBuffer().add(4);

             getPartBuffer().add(new Part(TopologyType.TRIANGLE, 0, 6));

    }

}