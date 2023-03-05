package model;

import transforms.Mat4;
import transforms.Mat4Identity;

import java.util.ArrayList;

public abstract class Solid {

    private ArrayList<Vertex> vertexBuffer;
    private ArrayList<Integer> indexBuffer;
    private ArrayList<Part> partBuffer;

    Mat4 model = new Mat4Identity();
    public Solid() { // ArrayList !!!
         vertexBuffer = new ArrayList<>();
         indexBuffer = new ArrayList<>();
         partBuffer = new ArrayList<>();
    }

    public Mat4 getModelMatrix() {
        return model;
    }

    public void setModelMatrix(Mat4 model) {
        this.model = model;
    }

    public void setVertexBuffer(ArrayList<Vertex> vertexBuffer) {
        this.vertexBuffer = vertexBuffer;
    }

    public void setIndexBuffer(ArrayList<Integer> indexBuffer) {
        this.indexBuffer = indexBuffer;
    }

    public void setPartBuffer(ArrayList<Part> partBuffer) {
        this.partBuffer = partBuffer;
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
