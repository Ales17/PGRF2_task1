package model;

import solid.Solid;
import transforms.Col;

public class Cube extends Solid {

    public Cube() {


        Col c1 = new Col( 255, 55, 222);
        Col c2 = new Col(11, 110, 44);
        Col c3 = new Col(0, 125, 111);
        Col c4 = new Col( 5, 110, 5);

        double xMove = 0.2;
        double yMove = 0.2;
        double zMove = 0.2;
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




        getVertexBuffer().add(new Vertex(1+xMove, 0+yMove, 0+zMove, c1));//v1
        getVertexBuffer().add(new Vertex(1+xMove, 0+yMove, 0+zMove, c2));//v2
        getVertexBuffer().add(new Vertex(1+xMove, 1+yMove, 0+zMove, c2));//v3
        getVertexBuffer().add(new Vertex(0+xMove, 1+yMove, 0+zMove, c1));//v4

        getVertexBuffer().add(new Vertex(0+xMove, 0+yMove, 1+zMove, c3));//v5
        getVertexBuffer().add(new Vertex(1+xMove, 0+yMove, 1+zMove, c4));//v6
        getVertexBuffer().add(new Vertex(1+xMove, 1+yMove, 1+zMove, c4));//v7
        getVertexBuffer().add(new Vertex(0+xMove, 1+yMove, 1+zMove, c3));//v8

        getVertexBuffer().add(new Vertex(0+xMove, 0+yMove, 0+zMove, c2));//v1
        getVertexBuffer().add(new Vertex(0+xMove, 1+yMove, 0+zMove, c1));//v4
        getVertexBuffer().add(new Vertex(0+xMove, 1+yMove, 1+zMove, c2));//v8
        getVertexBuffer().add(new Vertex(0+xMove, 0+yMove, 1+zMove, c1));//v5

        getVertexBuffer().add(new Vertex(1+xMove, 0+yMove, 0+zMove, c4));//v2
        getVertexBuffer().add(new Vertex(1+xMove, 1+yMove, 0+zMove, c4));//v3
        getVertexBuffer().add(new Vertex(1+xMove, 1+yMove, 1+zMove, c1));//v7
        getVertexBuffer().add(new Vertex(1+xMove, 0+yMove, 1+zMove, c1));//v6

        getVertexBuffer().add(new Vertex(0+xMove, 0+yMove, 0+zMove, c1));//v1
        getVertexBuffer().add(new Vertex(1+xMove, 0+yMove, 0+zMove, c4));//v2
        getVertexBuffer().add(new Vertex(1+xMove, 0+yMove, 1+zMove, c4));//v6
        getVertexBuffer().add(new Vertex(0+xMove, 0+yMove, 1+zMove, c1));//v5

        getVertexBuffer().add(new Vertex(0+xMove, 1+yMove, 0+zMove, c1));//v4
        getVertexBuffer().add(new Vertex(1+xMove, 1+yMove, 0+zMove, c2));//v3
        getVertexBuffer().add(new Vertex(1+xMove, 1+yMove, 1+zMove, c1));//v7
        getVertexBuffer().add(new Vertex(0+xMove, 1+yMove, 1+zMove, c2));//v8

        getPartBuffer().add(new Part(TopologyType.TRIANGLE, 0, 12));


    }

}