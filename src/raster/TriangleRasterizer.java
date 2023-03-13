package raster;

import model.Vertex;
import shaders.ShaderFunctional;
import shaders.ShaderConstant;
import shaders.ShaderInterpolated;
import transforms.Point3D;
import transforms.Vec3D;
import utils.Lerp;

import java.util.Optional;

public class TriangleRasterizer {

    private final ZBuffer zBuffer;

    private final int width;
    private final int height;
    private final Lerp<Vertex> lerp;
    private ShaderConstant shaderConst = new ShaderConstant();
    private ShaderInterpolated shaderInterpolated = new ShaderInterpolated();



    public TriangleRasterizer(ZBuffer zBuffer) {
        this.zBuffer = zBuffer;
        this.lerp = new Lerp<>();
        width = zBuffer.getImageBuffer().getWidth();
        height = zBuffer.getImageBuffer().getHeight();
    }

    // TODO ShaderInterpolatedColor



    public void prepare(Vertex a) {
        if (fastClip(a.getPosition()))
            return;
        Optional<Vertex> aDehom = a.dehom();
        if (aDehom.isEmpty())
            return;
        Vec3D newP1 = transform(aDehom.get().getPosition());
        a.setPosition(newP1);
        zBuffer.drawWithZTest((int) a.getPosition().getX(), (int) a.getPosition().getY(), a.getPosition().getZ(), shaderInterpolated.shade(a));
    }


    public void prepareLine(Vertex a, Vertex b) {
        if (fastClip(a.getPosition()) || fastClip(b.getPosition())) ;
        Optional<Vertex> dehomA = a.dehom();
        Optional<Vertex> dehomB = b.dehom();
        if (dehomA.isEmpty() || dehomB.isEmpty())
            return;
        Vec3D newP1 = transform(dehomA.get().getPosition());
        Vec3D newP2 = transform(dehomB.get().getPosition());
        a.setPosition(newP1);
        b.setPosition(newP2);
        rasterizeLine(a, b);
    }
    public void prepareWire(Vertex a, Vertex b) {
        if (fastClip(a.getPosition()) || fastClip(b.getPosition())) ;
        Optional<Vertex> dehomA = a.dehom();
        Optional<Vertex> dehomB = b.dehom();
        if (dehomA.isEmpty() || dehomB.isEmpty())
            return;
        Vec3D newP1 = transform(dehomA.get().getPosition());
        Vec3D newP2 = transform(dehomB.get().getPosition());
        Vertex tempA = new Vertex(a);
        Vertex tempB = new Vertex(b);
        tempA.setPosition(newP1);
        tempB.setPosition(newP2);
        rasterizeLine(tempA, tempB);
    }
    public void rasterizeLine(Vertex a, Vertex b) {
        double dx = b.getPosition().getX() - a.getPosition().getX();
        double dy = b.getPosition().getY() - a.getPosition().getY();
        Vertex temp;
        if (Math.abs(dy) < Math.abs(dx)) {
            if (b.getPosition().getX() < a.getPosition().getX()) {
                temp = a;
                a = b;
                b = temp;
            }
            for (int i = (int) a.getPosition().getX(); i < b.getPosition().getX(); i++) {
                double t = (i - a.getPosition().getX()) / (b.getPosition().getX() - a.getPosition().getX());
                Vertex v = lerp.lerp(a, b, t);
                zBuffer.drawWithZTest((int) v.getPosition().getX(), (int) v.getPosition().getY(), v.getPosition().getZ(), shaderInterpolated.shade(v) /*a.getColor()*/);
            }

        } else if (b.getPosition().getX() == a.getPosition().getX() || Math.abs(dy) > Math.abs(dx)) {
            if (b.getPosition().getY() < a.getPosition().getY()) {
                temp = a;
                a = b;
                b = temp;
            }
            for (int i = (int) a.getPosition().getY(); i < b.getPosition().getY(); i++) {
                double t = (i - a.getPosition().getY()) / (b.getPosition().getY() - a.getPosition().getY());
                Vertex v = lerp.lerp(a, b, t);
                zBuffer.drawWithZTest((int) v.getPosition().getX(), (int) v.getPosition().getY(), v.getPosition().getZ(), shaderInterpolated.shade(v));
            }

        }
    }

    public void prepare(Vertex a, Vertex b, Vertex c) {


        if (fastClip(a.getPosition()) || fastClip(b.getPosition()) || fastClip(c.getPosition())) return;

        if (b.getPosition().getZ() < 0) {
            double s1 = (0 - a.getPosition().getZ()) / (b.getPosition().getZ() - b.getPosition().getZ());
            Vertex ab = lerp.lerp(b, a, s1);
            double s2 = (0 - a.getPosition().getZ()) / (a.getPosition().getZ() - c.getPosition().getZ());
            Vertex ac = lerp.lerp(c, a, s2);
            renderTriangle(a, ab, ac, 1);

        }
        if (c.getPosition().getZ() < 0) {
            double s1 = (0 - c.getPosition().getZ()) / (c.getPosition().getZ() - b.getPosition().getZ());
            Vertex ab = lerp.lerp(b, c, s1);
            double s2 = (0 - c.getPosition().getZ()) / (c.getPosition().getZ() - c.getPosition().getZ());
            Vertex ac = lerp.lerp(a, c, s2);
            renderTriangle(a, ab, ac, 1);
            renderTriangle(a, ab, ac, 1);
            return;

        }
        renderTriangle(a, b, c, 1);

        Optional<Vertex> aDehom = a.dehom();
        Optional<Vertex> bDehom = b.dehom();
        Optional<Vertex> cDehom = c.dehom();
        if (aDehom.isEmpty() || bDehom.isEmpty() || cDehom.isEmpty())
            return;

        Vec3D newA = transform(aDehom.get().getPosition());
        Vec3D newB = transform(bDehom.get().getPosition());
        Vec3D newC = transform(cDehom.get().getPosition());
        a.setPosition(newA);
        b.setPosition(newB);
        c.setPosition(newC);

        Vertex temp;
        if (a.getPosition().getY() > b.getPosition().getY()) {
            temp = a;
            a = b;
            b = temp;
        }
        if (b.getPosition().getY() > c.getPosition().getY()) {
            temp = b;
            b = c;
            c = temp;
        }
        if (a.getPosition().getY() > b.getPosition().getY()) {
            temp = a;
            a = b;
            b = temp;
        }
        for (int y = (int) a.getPosition().getY() + 1; y < b.getPosition().getY(); y++) {
            renderTriangle(a, b, c, y);
        }
        for (int y = (int) b.getPosition().getY() + 1; y < c.getPosition().getY(); y++) {
            renderTriangle(c, b, a, y);
        }
    }

    private void renderTriangle(Vertex a, Vertex b, Vertex c, int y) {
        double s1 = (y - a.getPosition().getY()) / (b.getPosition().getY() - a.getPosition().getY());
        double s2 = (y - a.getPosition().getY()) / (c.getPosition().getY() - a.getPosition().getY());
        Vertex v12 = lerp.lerp(a, b, s1);
        Vertex v13 = lerp.lerp(a, c, s2);
        if (v12.getPosition().getX() > v13.getPosition().getX()) {
            Vertex temp = v12;
            v12 = v13;
            v13 = temp;
        }
        for (int x = (int) v12.getPosition().getX(); x < v13.getPosition().getX(); x++) {
            double t = (x - v12.getPosition().getX()) / (v13.getPosition().getX() - v12.getPosition().getX());
            Vertex point = lerp.lerp(v12, v13, t);
            zBuffer.drawWithZTest(x, y, point.getPosition().getZ(), shaderInterpolated.shade(v12));
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

