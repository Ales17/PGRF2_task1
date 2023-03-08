package raster;

import model.Shader;
import model.Vertex;
import transforms.Point3D;
import transforms.Vec3D;
import java.util.Optional;

public class TriangleRasterizer {

    private final ZBuffer zBuffer;

    private final int width;
    private final int height;
//    private final Shader shader;
    private int modeCut = 1;

    public void setCuttingMode(int modeCut) {
        this.modeCut = modeCut;
    }

    public TriangleRasterizer(ZBuffer zBuffer/*, Shader shader*/) {
        this.zBuffer = zBuffer;
        width = zBuffer.getImageBuffer().getWidth();
        height = zBuffer.getImageBuffer().getHeight();
        /*this.shader = shader;*/

    }
    public void rasterize(Vertex v1){
        if (fastClip(v1.getPosition()))
            return;
        Optional<Vertex> v1Dehomog = v1.dehomog();
        if(v1Dehomog.isEmpty())
            return;
        Vec3D newP1 = transform(v1Dehomog.get().getPosition());
        v1.setPosition(newP1);
        zBuffer.drawPixelWithTest((int)v1.getPosition().getX(),(int)v1.getPosition().getY(),v1.getPosition().getZ(),v1.getColor());
    }
    public void rasterize(Vertex p1, Vertex p2) {


        if (fastClip(p1.getPosition()) || fastClip(p2.getPosition()));



        Optional<Vertex> v1Dehomog = p1.dehomog();
        Optional<Vertex> v2Dehomog = p2.dehomog();
        if (v1Dehomog.isEmpty() || v2Dehomog.isEmpty())
            return;
        Vec3D newP1 = transform(v1Dehomog.get().getPosition());
        Vec3D newP2 = transform(v2Dehomog.get().getPosition());
        p1.setPosition(newP1);
        p2.setPosition(newP2);
        trivialLine(p1, p2);
    }

    private void trivialLine(Vertex p1, Vertex p2) {
        double dx = p2.getPosition().getX() - p1.getPosition().getX();
        double dy = p2.getPosition().getY() - p1.getPosition().getY();
        Vertex temp;
        if (Math.abs(dy) < Math.abs(dx)) {
            if (p2.getPosition().getX() < p1.getPosition().getX()) {
                temp =p1;
                p1 =p2;
                p2=temp;
            }
            for (int i = (int)p1.getPosition().getX();i<p2.getPosition().getX();i++){
                double t = (i - p1.getPosition().getX()) / (p2.getPosition().getX() - p1.getPosition().getX());
                Vertex v = p1.mul(1 - t).add(p2.mul(t));
                zBuffer.drawPixelWithTest((int)v.getPosition().getX(),(int)v.getPosition().getY(),v.getPosition().getZ(), p1.getColor()
                        /*, shader.shade(v)*/);
            }

        } else if(p2.getPosition().getX() == p1.getPosition().getX()||Math.abs(dy) > Math.abs(dx)) {
            if (p2.getPosition().getY() < p1.getPosition().getY()) {
                temp =p1;
                p1 =p2;
                p2=temp;
            }
            for (int i = (int)p1.getPosition().getY();i<p2.getPosition().getY();i++){
                double t = (i - p1.getPosition().getY()) / (p2.getPosition().getY() - p1.getPosition().getY());
                Vertex v =  p1.mul(1 - t).add(p2.mul(t));



                zBuffer.drawPixelWithTest((int)v.getPosition().getX(),(int)v.getPosition().getY(),v.getPosition().getZ(), p2.getColor() /*shader.shade(v*/);
            }

        }
    }

    public void rasterize(Vertex p1, Vertex p2, Vertex p3) {


        if (fastClip(p1.getPosition()) || fastClip(p2.getPosition()) || fastClip(p3.getPosition()));

        if (p2.getPosition().getZ()<0)
        {
            double s1 = (0 - p1.getPosition().getZ())/(p2.getPosition().getZ() - p2.getPosition().getZ()); //odečti minimem děl rozsahem
            Vertex ab = p2.mul(1-s1).add(p1.mul(s1));
            double s2 = (0 - p1.getPosition().getZ())/(p1.getPosition().getZ() - p3.getPosition().getZ());
            Vertex ac = p3.mul(1-s1).add(p1.mul(s2));
            simpleScanlineTriangle(p1,ab,ac,1);

        }
        if (p3.getPosition().getZ()<0)
        {
            double s1 = (0 - p3.getPosition().getZ())/(p3.getPosition().getZ() - p2.getPosition().getZ());
            Vertex ab = p2.mul(1-s1).add(p3.mul(s1));
            double s2 = (0 - p3.getPosition().getZ())/(p3.getPosition().getZ() - p3.getPosition().getZ());
            Vertex ac = p1.mul(1-s1).add(p3.mul(s2));
            simpleScanlineTriangle(p1,ab,ac,1);
            simpleScanlineTriangle(p1,ab,ac,1);
            return;

        }
        simpleScanlineTriangle(p1,p2,p3,1);

        Optional<Vertex> v1Dehomog = p1.dehomog();
        Optional<Vertex> v2Dehomog = p2.dehomog();
        Optional<Vertex> v3Dehomog = p3.dehomog();
        if (v1Dehomog.isEmpty() || v2Dehomog.isEmpty() || v3Dehomog.isEmpty())
            return;

        Vec3D newP1 = transform(v1Dehomog.get().getPosition());
        Vec3D newP2 = transform(v2Dehomog.get().getPosition());
        Vec3D newP3 = transform(v3Dehomog.get().getPosition());
        p1.setPosition(newP1);
        p2.setPosition(newP2);
        p3.setPosition(newP3);

        Vertex temp;
        if (p1.getPosition().getY() > p2.getPosition().getY()) {
            temp =p1;
            p1 =p2;
            p2=temp;
        }
        if (p2.getPosition().getY() > p3.getPosition().getY()) {
            temp =p2;
            p2 =p3;
            p3=temp;
        }
        if (p1.getPosition().getY() > p2.getPosition().getY()) {
            temp =p1;
            p1 =p2;
            p2=temp;
        }
        for (int y = (int) p1.getPosition().getY()+1; y < p2.getPosition().getY(); y++) {
            simpleScanlineTriangle(p1, p2, p3, y);
        }
        for (int y = (int) p2.getPosition().getY()+1; y < p3.getPosition().getY(); y++) {
            simpleScanlineTriangle(p3, p2, p1, y);
        }
    }
    private void simpleScanlineTriangle(Vertex a, Vertex b, Vertex c, int y) {
        double s1 = (y - a.getPosition().getY()) / (b.getPosition().getY() - a.getPosition().getY());
        double s2 = (y - a.getPosition().getY()) / (c.getPosition().getY() - a.getPosition().getY());
        Vertex v12 = a.mul(1 - s1).add(b.mul(s1));
        Vertex v13 = a.mul(1 - s2).add(c.mul(s2));
        if (v12.getPosition().getX() > v13.getPosition().getX()) {
            Vertex temp =v12;
            v12 =v13;
            v13=temp;
        }
        for (int x = (int) v12.getPosition().getX(); x < v13.getPosition().getX(); x++) {
            double t = (x - v12.getPosition().getX()) / (v13.getPosition().getX() - v12.getPosition().getX());
            Vertex point = v12.mul(1 - t).add(v13.mul(t));
            zBuffer.drawPixelWithTest(x, y, point.getPosition().getZ(),  a.getColor() /*shader.shade(point)*/);
        }
    }

    private Vec3D transform(Point3D p) {
        return p.ignoreW()
                .mul(new Vec3D(1, -1, 1))
                .add(new Vec3D(1, 1, 0))
                .mul(new Vec3D((width - 1) / (float) 2, (height - 1) / (float) 2, 1));
    }

    public ZBuffer getzBuffer() {
        return zBuffer;
    }

    private boolean fastClip(Point3D p) {
        if (p.getW() < p.getX() || p.getX() < -p.getW()) return true;
        if (p.getW() < p.getY() || p.getY() < -p.getW()) return true;
        return p.getW() < p.getZ() || p.getZ() < 0;
    }
}
