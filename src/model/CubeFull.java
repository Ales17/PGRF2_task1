package model;

import solid.Solid;
import transforms.Col;

public class CubeFull extends Solid {

    public CubeFull(){
    // vertexy
    getVertexBuffer().add(new Vertex(-0.5, -0.5, -0.5, new Col(0xFF0000))); // 0
    getVertexBuffer().add(new Vertex(-0.5, -0.5,  0.5, new Col(0xFF0000))); // 1
    getVertexBuffer().add(new Vertex(-0.5,  0.5, -0.5, new Col(0xFF0000))); // 2
    getVertexBuffer().add(new Vertex(-0.5,  0.5,  0.5, new Col(0xFF0000))); // 3
    getVertexBuffer().add(new Vertex( 0.5, -0.5, -0.5, new Col(0xFF0000))); // 4
    getVertexBuffer().add(new Vertex( 0.5, -0.5,  0.5, new Col(0xFF0000))); // 5
    getVertexBuffer().add(new Vertex( 0.5,  0.5, -0.5, new Col(0xFF0000))); // 6
    getVertexBuffer().add(new Vertex( 0.5,  0.5,  0.5, new Col(0xFF0000))); // 7



    // indexy
    // horní stěna
    getIndexBuffer().add(2);
    getIndexBuffer().add(6);
    getIndexBuffer().add(3);
    getIndexBuffer().add(3);
    getIndexBuffer().add(6);
    getIndexBuffer().add(7);

    // spodní stěna
    getIndexBuffer().add(0);
    getIndexBuffer().add(1);
    getIndexBuffer().add(4);
    getIndexBuffer().add(4);
    getIndexBuffer().add(1);
    getIndexBuffer().add(5);

    // přední stěna
    getIndexBuffer().add(0);
    getIndexBuffer().add(4);
    getIndexBuffer().add(2);
    getIndexBuffer().add(2);
    getIndexBuffer().add(4);
    getIndexBuffer().add(6);

    // zadní stěna
    getIndexBuffer().add(1);
    getIndexBuffer().add(5);
    getIndexBuffer().add(3);
    getIndexBuffer().add(3);
    getIndexBuffer().add(5);
    getIndexBuffer().add(7);

    // levá stěna
    getIndexBuffer().add(0);
    getIndexBuffer().add(2);
    getIndexBuffer().add(1);
    getIndexBuffer().add(1);
    getIndexBuffer().add(2);
    getIndexBuffer().add(3);

    // pravá stěna
    getIndexBuffer().add(4);
    getIndexBuffer().add(5);
    getIndexBuffer().add(6);
    getIndexBuffer().add(6);
    getIndexBuffer().add(5);
    getIndexBuffer().add(7);

    getPartBuffer().add(new Part(TopologyType.TRIANGLE, 0, 12));}
}
