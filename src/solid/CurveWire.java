package solid;

import model.Vertex;
import transforms.Cubic;
import transforms.Point3D;

public class CurveWire extends Solid{
    public CurveWire(){
        // řidící body
        Point3D points[] = new Point3D[4];
        //-1 -1 -1
        //1 1 -1
        //1 -1 1
        //-1 1 1
        points[0] = new Point3D(-1, -1, -1);
        points[1] = new Point3D(1, 1, -1);
        points[2] = new Point3D(1, -1, 1);
        points[3] = new Point3D(-1, 1, 1);

        // výpočet bodů
        // matice Ferguson - bázová...,
        // pošlu pointy
        Cubic cubic = new Cubic(Cubic.FERGUSON, points);
        // výpočet bodů
        // nultý můžeme vygenerovat před forem, poslat do VB, začít od 1, když v první iteraci budou 2 vertexy
        Vertex v = new Vertex(cubic.compute(0));
        getVertexBuffer().add(v);
for(int i = 0; i < 100; i++){
    double t = i / 100.0;

    v = new Vertex(cubic.compute(t));
    getVertexBuffer().add(v);
    getIndexBuffer().add(getVertexBuffer().size() - 2);
    getIndexBuffer().add(getVertexBuffer().size() - 1);

}

//TODO part buffer

    }   // cubic compute očekává double od 0 do 1, potřebujeme převést
    // compute vrací Point3D, což je pozice, tu pošleme do vertexBufferu
    // vertex neumí Point3D, musíme převést

}
