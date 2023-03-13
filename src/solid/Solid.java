package solid;

import model.Part;
import model.Vertex;
import transforms.Mat4;
import transforms.Mat4Identity;

import java.util.ArrayList;
import java.util.List;

public abstract class Solid {
    private List<Vertex> vertexBuffer = new ArrayList<>();
    private List<Integer> indexBuffer = new ArrayList<>();
    private List<Part> partBuffer = new ArrayList<>();
    Mat4 model = new Mat4Identity();

    public List<Integer> getIndexBuffer() {
        return indexBuffer;
    }
    public List<Vertex> getVertexBuffer() {
        return vertexBuffer;
    }


    public List<Part> getPartBuffer() {
        return partBuffer;
    }

    public Solid() {
    }
    public Mat4 getModelMatrix() {
        return model;
    }

    public void transform(Mat4 matrix) {
        this.model = matrix.mul(this.model);
    }
}
