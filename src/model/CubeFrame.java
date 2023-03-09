package model;

import solid.Solid;
import transforms.Col;

public class CubeFrame extends Solid {
    public CubeFrame()
    {

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

        Col c1 = new Col(0xff0000);
        Col c2 = new Col(0x00ff00);
        Col c3 = new Col(0x0000ff);
        getVertexBuffer().add(new Vertex(-0.5,-0.5,-0.5, c1));//0
        getVertexBuffer().add(new Vertex(0.5,-0.5,-0.5, c2));//1
        getVertexBuffer().add(new Vertex(0.5,0.5,-0.5, c3));//2
        getVertexBuffer().add(new Vertex(-0.5,0.5,-0.5, c1 ));//3
        getVertexBuffer().add(new Vertex(-0.5,-0.5,0.5,c2 ));//4
        getVertexBuffer().add(new Vertex(0.5,-0.5,0.5,c3 ));//5
        getVertexBuffer().add(new Vertex(0.5,0.5,0.5,c1 ));//6
        getVertexBuffer().add(new Vertex(-0.5,0.5,0.5, c2));//7

        getPartBuffer().add(new Part(TopologyType.LINE,0,12));


    }
}
