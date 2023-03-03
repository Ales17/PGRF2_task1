package render;

import model.Part;
import model.Solid;
import model.Vertex;
import raster.LineRasterizer;
import raster.TriangleRasterizer;
import transforms.Camera;
import transforms.Col;
import transforms.Mat4;
import transforms.Mat4Identity;
import utils.Lerp;

import java.util.List;

public class Renderer {
    private TriangleRasterizer triangleRasterizer;
    private LineRasterizer lineRasterizer;
    private Lerp<Vertex> lerp;
    private Mat4Identity model;
    private Mat4Identity projection;
    private Mat4Identity view;



    public Renderer(TriangleRasterizer triangleRasterizer, LineRasterizer lineRasterizer) {
        this.triangleRasterizer = triangleRasterizer;
        this.lineRasterizer = lineRasterizer;
         model = new Mat4Identity();
        projection = new Mat4Identity();
        view = new Mat4Identity();
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


                        lineRasterizer.rasterize(a, b);
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

                        // Posílat vertex
                        triangleRasterizer.rasterize(a, b, c,
                                new Col(0xFF0000)); // Poslat do rasterizeru
                    }
                    break;
            }
        }
    }


    private void renderTriangle(Vertex a, Vertex b, Vertex c) {
        // FAST CLIP - zahodime veci, ktere jsou mimo scenu SLIDE 99
        // orezani dle z slide 103
        // todo seradit vrchole dle z, kde Az je max
        // vertex pomocny gettery pro vraceni primo Z

        double zMin = 0;
        if (a.getPosition().getZ() < zMin) {
            return;
        }

        if (b.getPosition().getZ() < zMin) {
            double t1 = (zMin - a.getPosition().getZ()) / (b.getPosition().getZ() - a.getPosition().getZ());
            Vertex vab = lerp.lerp(a, b, t1);

            double t2 = (zMin - a.getPosition().getZ()) / (c.getPosition().getZ() - a.getPosition().getZ());
        }

        if (c.getPosition().getZ() < zMin) {//todo dodelat}


            // slide 103
        }



        /*public void render (List < Solid > scene) {
            for (Solid solid : scene) {
                render(solid);
            }
        }*/
    }



}
