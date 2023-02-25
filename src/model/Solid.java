package model;

import java.util.ArrayList;

public abstract class Solid {

    // IB, VB, PB
    // IB - index buffer
    // VB - vertex buffer
    // PB - part buffer

    private ArrayList<Vertex> vertexBuffer;
    private ArrayList<Integer> indexBuffer;
    private ArrayList<Part> partBuffer;


    public Solid() {
        vertexBuffer = new ArrayList<>();
        indexBuffer = new ArrayList<>();
        partBuffer = new ArrayList<>();
    }


    public ArrayList<Vertex> getVertexBuffer() {
        return vertexBuffer;
    }

    public ArrayList<Integer> getIndexBuffer() {
        return indexBuffer;
    }

    public ArrayList<Part> getPartBuffer() {
        return partBuffer;
    }

}
