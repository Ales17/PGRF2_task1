package model;

import transforms.Col;

public class Axis extends Solid{
    public Axis() {
        // xyz axis

        // x axis
        getIndexBuffer().add(0);
        getIndexBuffer().add(1);

        getVertexBuffer().add(new Vertex(0,0,0,new Col(1,0,0.)));//v1
        getVertexBuffer().add(new Vertex(1,0,0,new Col(1,0,0.)));//v1

        // y axis
        getIndexBuffer().add(2);
        getIndexBuffer().add(3);

        getVertexBuffer().add(new Vertex(0,0,0,new Col(0,1,0.)));//v1
        getVertexBuffer().add(new Vertex(0,1,0,new Col(0,1,0.)));//v1

        // z axis
        getIndexBuffer().add(4);
        getIndexBuffer().add(5);

        getVertexBuffer().add(new Vertex(0,0,0,new Col(0,0,1.)));//v1
        getVertexBuffer().add(new Vertex(0,0,1,new Col(0,0,1.)));//v1

        getPartBuffer().add(new Part(TopologyType.LINE,0,3));


    }
}
