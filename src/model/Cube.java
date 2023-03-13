package model;

import solid.Solid;
import transforms.Col;

public class Cube extends Solid {

    public Cube() {

        double xMove = 0.2;
        double yMove = 0.2;
        double zMove = 0.2;


        Vertex v1 = new Vertex(0 + xMove, 0 + yMove, 0 + zMove, new Col(0x333aaa));
        Vertex v2 = new Vertex(1 + xMove, 0 + yMove, 0 + zMove, new Col(0x333aaa));
        Vertex v3 = new Vertex(1 + xMove, 1 + yMove, 0 + zMove, new Col(0x333aaa));
        Vertex v4 = new Vertex(0 + xMove, 1 + yMove, 0 + zMove, new Col(0x11bbff));
        Vertex v5 = new Vertex(0 + xMove, 0 + yMove, 1 + zMove, new Col(0x11bbff));
        Vertex v6 = new Vertex(1 + xMove, 0 + yMove, 1 + zMove, new Col(0x333aaa));
        Vertex v7 = new Vertex(1 + xMove, 1 + yMove, 1 + zMove, new Col(0x333aaa));
        Vertex v8 = new Vertex(0 + xMove, 1 + yMove, 1 + zMove, new Col(0x333aaa));


        getVertexBuffer().add(v1);
        getVertexBuffer().add(v2);
        getVertexBuffer().add(v3);
        getVertexBuffer().add(v4);
        getVertexBuffer().add(v5);
        getVertexBuffer().add(v6);
        getVertexBuffer().add(v7);
        getVertexBuffer().add(v8);


        // FRONT
        getIndexBuffer().add(0);
        getIndexBuffer().add(1);
        getIndexBuffer().add(2);
        getIndexBuffer().add(0);
        getIndexBuffer().add(2);
        getIndexBuffer().add(3);
        // BACK
        getIndexBuffer().add(4);
        getIndexBuffer().add(5);
        getIndexBuffer().add(6);
        getIndexBuffer().add(4);
        getIndexBuffer().add(6);
        getIndexBuffer().add(7);
        // LEFT
        getIndexBuffer().add(0);
        getIndexBuffer().add(3);
        getIndexBuffer().add(7);
        getIndexBuffer().add(0);
        getIndexBuffer().add(7);
        getIndexBuffer().add(4);
        // RIGHT
        getIndexBuffer().add(1);
        getIndexBuffer().add(2);
        getIndexBuffer().add(6);
        getIndexBuffer().add(1);
        getIndexBuffer().add(6);
        getIndexBuffer().add(5);
        // TOP
        getIndexBuffer().add(0);
        getIndexBuffer().add(1);
        getIndexBuffer().add(5);
        getIndexBuffer().add(0);
        getIndexBuffer().add(5);
        getIndexBuffer().add(4);
        // BOTTOM
        getIndexBuffer().add(3);
        getIndexBuffer().add(2);
        getIndexBuffer().add(6);
        getIndexBuffer().add(3);
        getIndexBuffer().add(6);
        getIndexBuffer().add(7);


        getPartBuffer().add(new Part(TopologyType.TRIANGLE, 0, 12));
    }

}