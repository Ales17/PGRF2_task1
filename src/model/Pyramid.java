package model;

import solid.Solid;
import transforms.Col;

public class Pyramid extends Solid {


    public Pyramid() {

     getIndexBuffer().add(0);
        getIndexBuffer().add(1);
        getIndexBuffer().add(2);
        getIndexBuffer().add(0);
        getIndexBuffer().add(2);
        getIndexBuffer().add(3);
        getIndexBuffer().add(0);
        getIndexBuffer().add(1);
        getIndexBuffer().add(4);
        getIndexBuffer().add(1);
        getIndexBuffer().add(2);
        getIndexBuffer().add(4);
        getIndexBuffer().add(2);
        getIndexBuffer().add(3);
        getIndexBuffer().add(4);
        getIndexBuffer().add(3);
        getIndexBuffer().add(0);
getIndexBuffer().add(4);
double xMove = 0.5;
double yMove = 1.0;
        getVertexBuffer().add(new Vertex(0+xMove,0+yMove,0,new Col(255,110,222)));//v1
        getVertexBuffer().add(new Vertex(1+xMove,0+yMove,0,new Col(111,44,222)));//v1
        getVertexBuffer().add(new Vertex(1+xMove,1+yMove,0,new Col(0,55,155)));//v1
        getVertexBuffer().add(new Vertex(0+xMove,1+yMove,0,new Col(150,44,33)));//v1
        getVertexBuffer().add(new Vertex(0.5+xMove,0.5+yMove,1,new Col(0,0,222)));//v1

        getPartBuffer().add(new Part(TopologyType.TRIANGLE,0,6));



    }

}