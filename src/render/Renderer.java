package render;

import model.Part;
import model.Solid;
import model.Vertex;
import raster.LineRasterizer;
import raster.TriangleRasterizer;
import transforms.*;
import utils.Lerp;

public class Renderer {
    private TriangleRasterizer triangleRasterizer;
    Mat4 viewMatrix = new Mat4Identity();
    Mat4 projectionMatrix = new Mat4Identity();

    //// !!!
    public Renderer(TriangleRasterizer triangleRasterizer) {
        this.triangleRasterizer = triangleRasterizer;
    }

    public void render(Solid solid) {
        Mat4 trans = solid.getModelMatrix().mul(viewMatrix.mul(projectionMatrix));
        for (Part part : solid.getPartBuffer()
        ) {
            int start;
            switch (part.getType()) {

                //// Usecka
                case LINE:
                    start = part.getStartIndex();
                    for (int i = 0; i < part.getCount(); i++) {
                        int indexA = start;
                        int indexB = start + 1;

                        Vertex a = solid.getVertexBuffer().get(solid.getIndexBuffer().get(indexA));
                        Vertex b = solid.getVertexBuffer().get(solid.getIndexBuffer().get(indexB));
                        a = a.mul(trans);
                        b = b.mul(trans);

                        triangleRasterizer.rasterize(a, b);
                        start += 2;
                    }
                    break;
                case TRIANGLE:
                    start = part.getStartIndex();////

                    for (int i = 0; i < part.getCount(); i++) {
                        //// Spocitat pozice v IB
                        int indexA = start;
                        int indexB = start + 1;
                        int indexC = start + 2;

                        // Z index bufferu zjistit indexy do vertex bufferu
                        // Vertex z VB na zaklade indexu z IB
                        Vertex a = solid.getVertexBuffer().get(solid.getIndexBuffer().get(indexA));
                        Vertex b = solid.getVertexBuffer().get(solid.getIndexBuffer().get(indexB));
                        Vertex c = solid.getVertexBuffer().get(solid.getIndexBuffer().get(indexC));
                        // triangleRasterizer.rasterize(a, b, c, new Col(0xFF0000)); // Poslat do rasterizeru
                        a = a.mul(trans);
                        b = b.mul(trans);
                        c = c.mul(trans);


                        triangleRasterizer.rasterize(
                                new Vertex(a.mul(1/a.getOne())),
                                new Vertex(b.mul(1/b.getOne())),
                                new Vertex(c.mul(1/c.getOne()))
                        );
                        start += 3;//// Zvetsit index o 3
                    }
                    break;
            }
        }
    }

    public void setProjectionMatrix(Mat4 projection) {
        projectionMatrix = projectionMatrix.mul(projection);
    }

    public void setView(Mat4 view) {
        viewMatrix = viewMatrix.mul(view);
    }

}
