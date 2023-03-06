package model;

import transforms.Mat4;
import transforms.Mat4Identity;

import java.util.ArrayList;
import java.util.List;

public abstract class Solid {

    private List<Vertex> vertexBuffer = new ArrayList<>();
    private List<Integer> indexBuffer = new ArrayList<>();
    private List<Part> partBuffer = new ArrayList<>()

    Mat4 model = new Mat4Identity();
    public Solid() {
    }

    public Mat4 getModelMatrix() {
        return model;
    }

    public void setModelMatrix(Mat4 model) {
        this.model = model;
    }

    public List<Vertex> getVertexBuffer() {
        return vertexBuffer;
    }

    public List<Integer> getIndexBuffer() {
        return indexBuffer;
    }

    public List<Part> getPartBuffer() {
        return partBuffer;
    }



}
