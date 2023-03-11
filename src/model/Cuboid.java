package model;

import solid.Solid;
import transforms.Col;

public class Cuboid extends Solid {
    public Cuboid() {


        Col c1 = new Col(0xff0000);
        Col c2 = new Col(0x00ff00);
        Col c3 = new Col(0x0000ff);

        Col c4 = new Col(0, 125, 0);
        Col c5 = new Col(0, 0, 250);
        Col c6 = new Col(0, 0, 125);
        Col c7 = new Col(250, 250, 0);
        Col c8 = new Col(125, 200, 0);
        Col c9 = new Col(250, 0, 250);
        Col c10 = new Col(125, 0, 125);
        Col c11 = new Col(0, 250, 250);
        Col c12 = new Col(0, 125, 125);
        double xMove = 1;
        double yMove = 1.75;
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
        getIndexBuffer().add(8);
        getIndexBuffer().add(9);
        getIndexBuffer().add(10);
        getIndexBuffer().add(8);
        getIndexBuffer().add(10);
        getIndexBuffer().add(11);
        // RIGHT
        getIndexBuffer().add(12);
        getIndexBuffer().add(13);
        getIndexBuffer().add(14);
        getIndexBuffer().add(12);
        getIndexBuffer().add(14);
        getIndexBuffer().add(15);
        // TOP
        getIndexBuffer().add(16);
        getIndexBuffer().add(17);
        getIndexBuffer().add(18);
        getIndexBuffer().add(16);
        getIndexBuffer().add(18);
        getIndexBuffer().add(19);
        // BOTTOM
        getIndexBuffer().add(20);
        getIndexBuffer().add(21);
        getIndexBuffer().add(22);
        getIndexBuffer().add(20);
        getIndexBuffer().add(22);
        getIndexBuffer().add(23);




        getVertexBuffer().add(new Vertex(1+xMove, 0+yMove, 0, c1));//v1
        getVertexBuffer().add(new Vertex(1+xMove, 0+yMove, 0, c1));//v2
        getVertexBuffer().add(new Vertex(1+xMove, 1+yMove, 0, c1));//v3
        getVertexBuffer().add(new Vertex(0+xMove, 1+yMove, 0, c1));//v4

        getVertexBuffer().add(new Vertex(0+xMove, 0+yMove, 2, c3));//v5
        getVertexBuffer().add(new Vertex(1+xMove, 0+yMove, 2, c3));//v6
        getVertexBuffer().add(new Vertex(1+xMove, 1+yMove, 2, c3));//v7
        getVertexBuffer().add(new Vertex(0+xMove, 1+yMove, 2, c3));//v8

        getVertexBuffer().add(new Vertex(0+xMove, 0+yMove, 0, c6));//v1
        getVertexBuffer().add(new Vertex(0+xMove, 1+yMove, 0, c6));//v4
        getVertexBuffer().add(new Vertex(0+xMove, 1+yMove, 2, c6));//v8
        getVertexBuffer().add(new Vertex(0+xMove, 0+yMove, 2, c6));//v5

        getVertexBuffer().add(new Vertex(1+xMove, 0+yMove, 0, c7));//v2
        getVertexBuffer().add(new Vertex(1+xMove, 1+yMove, 0, c7));//v3
        getVertexBuffer().add(new Vertex(1+xMove, 1+yMove, 2, c7));//v7
        getVertexBuffer().add(new Vertex(1+xMove, 0+yMove, 2, c7));//v6

        getVertexBuffer().add(new Vertex(0+xMove, 0+yMove, 0, c9));//v1
        getVertexBuffer().add(new Vertex(1+xMove, 0+yMove, 0, c9));//v2
        getVertexBuffer().add(new Vertex(1+xMove, 0+yMove, 2, c9));//v6
        getVertexBuffer().add(new Vertex(0+xMove, 0+yMove, 2, c9));//v5

        getVertexBuffer().add(new Vertex(0+xMove, 1+yMove, 0, c11));//v4
        getVertexBuffer().add(new Vertex(1+xMove, 1+yMove, 0, c11));//v3
        getVertexBuffer().add(new Vertex(1+xMove, 1+yMove, 2, c11));//v7
        getVertexBuffer().add(new Vertex(0+xMove, 1+yMove, 2, c11));//v8

        getPartBuffer().add(new Part(TopologyType.TRIANGLE, 0, 12));




    }
}
