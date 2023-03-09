package model;

import solid.Solid;
import transforms.Col;

public class Cube extends Solid {

    public Cube(){
        getIndexBuffer().add(0);
        getIndexBuffer().add(1);
        getIndexBuffer().add(2);
        getIndexBuffer().add(3);

        getIndexBuffer().add(4);
        getIndexBuffer().add(5);
        getIndexBuffer().add(6);
        getIndexBuffer().add(7);

        getIndexBuffer().add(8);
        getIndexBuffer().add(9);
        getIndexBuffer().add(10);
        getIndexBuffer().add(11);


        getIndexBuffer().add(12);
        getIndexBuffer().add(13);
        getIndexBuffer().add(14);
        getIndexBuffer().add(15);

        getIndexBuffer().add(16);
        getIndexBuffer().add(17);
        getIndexBuffer().add(18);
        getIndexBuffer().add(19);

        getIndexBuffer().add(20);
        getIndexBuffer().add(21);
        getIndexBuffer().add(22);
        getIndexBuffer().add(23);

        getIndexBuffer().add(24);
        getIndexBuffer().add(25);
        getIndexBuffer().add(26);
        getIndexBuffer().add(27);

        getVertexBuffer().add(new Vertex(0,0,0,new Col(0xff0000)));//v2
        getVertexBuffer().add(new Vertex(0.3,0,0,new Col(0x00ff00)));//v3
        getVertexBuffer().add(new Vertex(0.3,0.3,0,new Col(0xffff00)));//v4
        getVertexBuffer().add(new Vertex(0,0.3,0,new Col(0xababab)));//v5

        getVertexBuffer().add(new Vertex(0,0,0,new Col(0xfafacc)));//v2
        getVertexBuffer().add(new Vertex(0,0.3,0,new Col(0xcc0011)));//v3
        getVertexBuffer().add(new Vertex(0.3,0.3,0,new Col(0xcaff00)));//v4
        getVertexBuffer().add(new Vertex(0.3,0,0,new Col(0x00abff)));//v5


        getVertexBuffer().add(new Vertex(0,0,0.3,new Col(0x00bbff)));//v2
        getVertexBuffer().add(new Vertex(0.3,0,0.3,new Col(0x00aaf0)));//v3
        getVertexBuffer().add(new Vertex(0.3,0.3,0.3,new Col(0xff3f00)));//v4
        getVertexBuffer().add(new Vertex(0,0.3,0.3,new Col(0x0000ff)));//v5

        getVertexBuffer().add(new Vertex(0,0,0.3,new Col(0x0050ff)));//v2
        getVertexBuffer().add(new Vertex(0,0.3,0.3,new Col(0x00ff00)));//v3
        getVertexBuffer().add(new Vertex(0.3,0.3,0.3,new Col(0xffff00)));//v4
        getVertexBuffer().add(new Vertex(0.3,0,0.3,new Col(0x0000ff)));//v5

        getVertexBuffer().add(new Vertex(0,0,0.3,new Col(0x000ccc)));//v2
        getVertexBuffer().add(new Vertex(0,0.3,0.3,new Col(0x00ffff)));//v3
        getVertexBuffer().add(new Vertex(0,0,0,new Col(0xffff00)));//v2
        getVertexBuffer().add(new Vertex(0.3,0,0,new Col(0.,1.,1)));//v3


        getVertexBuffer().add(new Vertex(0.3,0.3,0,new Col(0xffff00)));//v4
        getVertexBuffer().add(new Vertex(0,0.3,0,new Col(0x0000ff)));//v5
        getVertexBuffer().add(new Vertex(0.3,0.3,0.3,new Col(0xffff00)));//v4
        getVertexBuffer().add(new Vertex(0.3,0,0.3,new Col(0x0000ff)));//v5

        getVertexBuffer().add(new Vertex(0,0,0,new Col(0x0000ff)));//v2
        getVertexBuffer().add(new Vertex(0.3,0,0,new Col(0x00ffff)));//v3
        getVertexBuffer().add(new Vertex(0.3,0.3,0.3,new Col(0xffff00)));//v4
        getVertexBuffer().add(new Vertex(0.3,0,0.3,new Col(0x0000ff)));//v5


        getPartBuffer().add(new Part(TopologyType.TRIANGLE,0,1));
        getPartBuffer().add(new Part(TopologyType.TRIANGLE,4,1));
        getPartBuffer().add(new Part(TopologyType.TRIANGLE,8,1));
        getPartBuffer().add(new Part(TopologyType.TRIANGLE,12,1));
        getPartBuffer().add(new Part(TopologyType.TRIANGLE,16,1));
        getPartBuffer().add(new Part(TopologyType.TRIANGLE,20,1));
        getPartBuffer().add(new Part(TopologyType.TRIANGLE,24,1));

    }

    }