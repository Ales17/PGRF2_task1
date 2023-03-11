package render;


import solid.Solid;
import model.Part;
import model.Vertex;
import raster.TriangleRasterizer;
import transforms.Mat4;
import transforms.Mat4Identity;

public class Renderer {
    private TriangleRasterizer triangleRasterizer;
    boolean wireframe = false;
    Mat4 viewMatrix = new Mat4Identity();
    Mat4 projection = new Mat4Identity();

    public Renderer(TriangleRasterizer triangleRasterizer) {
        this.triangleRasterizer = triangleRasterizer;
    }



    public void render(Solid solid) {
        Mat4 trans = solid.getModelMatrix().mul(viewMatrix.mul(projection));
        for (Part part : solid.getPartBuffer()
        ) {
            int start;
            switch (part.getType()) {
                case LINE -> {
                    start = part.getStartIndex();
                    for (int i = 0; i < part.getCount(); i++) {
                        int indexA = start;
                        int indexB = start + 1;
                        Vertex a = solid.getVertexBuffer().get(solid.getIndexBuffer().get(indexA));
                        Vertex b = solid.getVertexBuffer().get(solid.getIndexBuffer().get(indexB));
                        a = a.mul(trans);
                        b = b.mul(trans);

                        triangleRasterizer.prepareLine(a, b);
                        start += 2;
                    }
                }
                case TRIANGLE -> {
                    start = part.getStartIndex();
                    for (int i = 0; i < part.getCount(); i++) {
                        int indexA = start;
                        int indexB = start + 1;
                        int indexC = start + 2;
                        Vertex a = solid.getVertexBuffer().get(solid.getIndexBuffer().get(indexA));
                        Vertex b = solid.getVertexBuffer().get(solid.getIndexBuffer().get(indexB));
                        Vertex c = solid.getVertexBuffer().get(solid.getIndexBuffer().get(indexC));

                        a = a.mul(trans);
                        b = b.mul(trans);
                        c = c.mul(trans);
                        if (wireframe) {
                            triangleRasterizer.prepareLine(a, b);
                            triangleRasterizer.prepareLine(b, c);
                            triangleRasterizer.prepareLine(c, a);
                        } else {
                            triangleRasterizer.prepare(
                                    new Vertex(a.mul(1 / a.getOne())),
                                    new Vertex(b.mul(1 / b.getOne())),
                                    new Vertex(c.mul(1 / c.getOne())));

                            start += 3;
                        }
                    }
                }
                case POINT -> {
                    start = part.getStartIndex();
                    for (int i = 0; i < part.getCount(); i++) {
                        Vertex a = solid.getVertexBuffer().get(solid.getIndexBuffer().get(start));
                        a = a.mul(trans);
                        triangleRasterizer.prepare(a);
                        start++;
                    }
                }
                case TRIANGLE_STRIP -> {
                    System.out.println("Triangle strip");
                }
            }

        }
    }


    public void setProjection(Mat4 projection) {
        this.projection = this.projection.mul(projection);
    }

    public void setView(Mat4 view) {
        viewMatrix = viewMatrix.mul(view);
    }

    public void setWireframe(boolean wireframe) {
        this.wireframe = wireframe;
    }

    public boolean isWireframe() {
        return wireframe;
    }

    public void render(Scene scene) {
        scene.getSolids().forEach(this::render);
    }

}
