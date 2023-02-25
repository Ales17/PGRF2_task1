package render;

import model.Part;
import model.Solid;
import model.Vertex;
import raster.LineRasterizer;
import raster.TriangleRasterizer;
import transforms.Col;

import java.util.List;

public class Renderer {
    private TriangleRasterizer triangleRasterizer;
    private LineRasterizer lineRasterizer;

    public Renderer(TriangleRasterizer triangleRasterizer, LineRasterizer lineRasterizer) {
        this.triangleRasterizer = triangleRasterizer;
        this.lineRasterizer = lineRasterizer;
    }

    public void render(Solid solid) {
        // Projít part buffer
        for (Part part : solid.getPartBuffer()) {
            switch (part.getType()) {


                case LINE:
                    int linestart = part.getIndex();
                    for (int i = 0; i < part.getCount(); i++) {
                        int indexA = linestart;
                        int indexB = linestart + 1;
                        linestart += 2;
                        Vertex a = solid.getVertexBuffer().get(solid.getIndexBuffer().get(indexA));
                        Vertex b = solid.getVertexBuffer().get(solid.getIndexBuffer().get(indexB));
                        lineRasterizer.rasterize(a.getPosition(), b.getPosition(), new Col(0x0000ff));
                    }
                    break;
                case TRIANGLE:
                    int start = part.getIndex();
                    // Zjistit zacatek
                    //int start = part.getIndex();
                    for (int i = 0; i < part.getCount(); i++) {
                        // Spocitat pozice v IB
                        int indexA = start;
                        int indexB = start + 1;
                        int indexC = start + 2;
                        start += 3;// Zvetsit index o 3
                        // Z index bufferu zjistit indexy do vertex bufferu
                        // Vytahnu vertex z VB na zaklade indexu z IB
                        Vertex a = solid.getVertexBuffer().get(solid.getIndexBuffer().get(indexA));
                        Vertex b = solid.getVertexBuffer().get(solid.getIndexBuffer().get(indexB));
                        Vertex c = solid.getVertexBuffer().get(solid.getIndexBuffer().get(indexC));

                        // TODO: posílat vertex
                        triangleRasterizer.rasterize(a.getPosition(), b.getPosition(), c.getPosition(), new Col(0xFF0000)); // Poslat do rasterizeru
                    }
                    break;
            }
        }
    }

    public void render(List<Solid> scene) {
        for (Solid solid : scene) {
            render(solid);
        }
    }
}
