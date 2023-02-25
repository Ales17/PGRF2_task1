package model;

import transforms.Col;
import transforms.Mat4;
import transforms.Point3D;

public class Vertex implements Vectorizable<Vertex> {
    private Point3D position;
    private Col color;

    // TODO add texture coordinates
    // TODO add normal vector
    // TODO perspektivně korektní interpolace



    public Point3D getPosition() {
        return position;
    }

    public Col getColor() {
        return color;
    }

    public Vertex(double x, double y, double z) {
        this.position = new Point3D(x, y, z);
        this.color = new Col(0x0000ff);
        // TODO správně nastavit barvu



    }

    // TODO vytvořit interface
    // TODO transform - vrátí nový Vertex- vstupuje matice

    public Vertex transform() {
         return null;
    }


    // TODO dehomog
    // TODO transform to window

    @Override
    public Vertex mul(double k) {
        return null;
        // TODO přinásobí skalár ke každému atributu vertexu

    }

    // TODO transform
    @Override
    public Vertex add(Vertex vertex) {

        // TODO přičte jednotlivé atributy
        return null;

    }

    // TODO transform vrátí nový vertex - vstupuje matice, dehomog, transform to window


}
