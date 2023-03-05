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
    private LineRasterizer lineRasterizer;
    private Lerp<Vertex> lerp;
    private Mat4 model = new Mat4Identity();
    private Mat4 projection = new Mat4Identity();

    //// !!!
    public Renderer(TriangleRasterizer triangleRasterizer, LineRasterizer lineRasterizer) {
        this.triangleRasterizer = triangleRasterizer;
        this.lineRasterizer = lineRasterizer;

    }

    public void render(Solid solid) {


        // Projít part buffer
        for (Part part : solid.getPartBuffer()) {
            switch (part.getType()) {


                case LINE:
                    int linestart = part.getStartIndex();
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
                    int start = part.getStartIndex();
                    // Zjistit zacatek
                    //int start = part.getIndex();
                    for (int i = 0; i < part.getCount(); i++) {
                        // Spocitat pozice v IB
                        int indexA = start;
                        int indexB = start + 1;
                        int indexC = start + 2;
                        start += 3;// Zvetsit index o 3
                        // Z index bufferu zjistit indexy do vertex bufferu
                        // Vertex z VB na zaklade indexu z IB
                        Vertex a = solid.getVertexBuffer().get(solid.getIndexBuffer().get(indexA));
                        Vertex b = solid.getVertexBuffer().get(solid.getIndexBuffer().get(indexB));
                        Vertex c = solid.getVertexBuffer().get(solid.getIndexBuffer().get(indexC));
                        // triangleRasterizer.rasterize(a, b, c, new Col(0xFF0000)); // Poslat do rasterizeru
                        renderTriangle(a, b, c);
                    }
                    break;
            }
        }
    }

    public void setModel(Mat4 model) {
        this.model = model;
    }

    public void setProjection(Mat4 projection) {
        this.projection = projection;
    }





    private void renderTriangle(Vertex a, Vertex b, Vertex c) {
/*
         FAST CLIP - zahodime veci, ktere jsou mimo scenu SLIDE 99
         Pokud celý trojúhelník leží mimo scénu, tak ho zahodíme
         Trojúhelník ABC (Ax, Ay, Az, Aw) (Bx, By, Bz, Bw) (Cx, Cy, Cz, Cw)
         Podmínka:
         - všechna x jsou menší než -w (objekt je moc vlevo)
            - všechna x jsou větší než w (objekt je moc vpravo)
            - všechna y jsou menší než -w
            - všechna y jsou větší než w
            - všechna z jsou menší než 0 (objekt je moc daleko)
            - všechna z jsou větší než w (objekt je moc blízko)
            Potom trojúhelník nekreslíme
         - Pokud alespoň část zasahuje do zobratovacího objemu,
         viditelnou část určíme a vykreslíme
*/




        // orezani dle z slide 103
        // - hledáme, kudy prochází rovina z=0
        // - nutno provést seřazení dle z, aby platilo v1.z >= v2.z >= v3.z
        // todo seradit vrcholy dle z, kde Az je max
        // vertex pomocny gettery pro vraceni primo Z

        double zMin = 0;
        if (a.getPosition().getZ() < zMin) {
            return;
        }

        if (b.getPosition().getZ() < zMin) {
            double t1 = (zMin - a.getPosition().getZ()) /
                    (b.getPosition().getZ() - a.getPosition().getZ());
            Vertex vab = lerp.lerp(a, b, t1);

            double t2 = (zMin - a.getPosition().getZ()) /
                    (c.getPosition().getZ() - a.getPosition().getZ());
            Vertex vac = lerp.lerp(a, c, t2);
            renderTriangle(vab, b, c);
            renderTriangle(vab, c, vac);
            return;
        }
        if (c.getPosition().getZ() < zMin) {
            // TODO Aplikace projekční matice, výpočet homogenních souřadnic
            double t1 = (zMin - a.getPosition().getZ()) / (c.getPosition().getZ() - a.getPosition().getZ());
            Vertex vac = lerp.lerp(a, c, t1);
            double t2 = (zMin - b.getPosition().getZ()) / (c.getPosition().getZ() - b.getPosition().getZ());
            Vertex vbc = lerp.lerp(b, c, t2);
            renderTriangle(a, b, vac);
            renderTriangle(vac, b, vbc);
            renderTriangle(vbc, vac, c);
            return;
        }
         setProjection( new Mat4PerspRH(Math.PI / 4, 1, 1, 10));
        setModel(new Mat4RotX(Math.PI / 4).mul(new Mat4RotY(Math.PI / 4)));
        // projekční matice
        a.getPosition().mul(projection);
        b.getPosition().mul(projection);
        c.getPosition().mul(projection);
        // modelová matice
        a.getPosition().mul(model);
        b.getPosition().mul(model);
        c.getPosition().mul(model);

        // rasterizace trojúhelníku
        triangleRasterizer.rasterize(a, b, c, new Col(0xFF0000));
    }
}