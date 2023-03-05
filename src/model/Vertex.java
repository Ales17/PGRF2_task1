/*
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

    public Vertex(double x, double y, double z) {
        this.position = new Point3D(x, y, z);
        this.color = new Col(0x0000ff);
        // TODO správně nastavit barvu


    }

    // pomocna metoda
    public double getZ(Vertex vertex) {
        return vertex.getPosition().getZ();


    }

    public Point3D getPosition() {
        return position;
    }

    public Col getColor() {
        return color;
    }

    // TODO vytvořit interface
    // TODO transform - vrátí nový Vertex- vstupuje matice

    public Vertex transform(Mat4 matrix) {


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
*/
package model;

import transforms.Col;
import transforms.Point3D;
import transforms.Vec3D;

public class Vertex implements Vectorizable<Vertex> {

    private Point3D position; //// Pozice vrcholu
    private Col color; //// Barva vrcholu
    private Vec3D textureCoordinates; //// Texturová souřadnice vrcholu
    private Vec3D normalVector; //// Normálový vektor vrcholu

    public Vertex(double x, double y, double z)
    {
        this(x, y, z, new Col(0, 0, 0));
    }

    public Vertex(double x, double y, double z, Col color) {
        this.position = new Point3D(x, y, z);
        this.color = color;
        this.textureCoordinates = new Vec3D(0, 0, 0);
        this.normalVector = new Vec3D(0, 0, 0);
    }

    public Point3D getPosition() {
        return position;
    }



    public void setPosition(Point3D position) {
        this.position = position;
    }

    public Col getColor() {
        return color;
    }

    public void setColor(Col color) {
        this.color = color;
    }

    public Vec3D getTextureCoordinates() {
        return textureCoordinates;
    }

    public void setTextureCoordinates(Vec3D textureCoordinates) {
        this.textureCoordinates = textureCoordinates;
    }

    public Vec3D getNormalVector() {
        return normalVector;
    }

    public void setNormalVector(Vec3D normalVector) {
        this.normalVector = normalVector;
    }

    public double getZ(Vertex vertex) {
        return vertex.getPosition().getZ();
    }

    @Override
    public Vertex mul(double k) {
        return new Vertex(
                position.getX() * k,
                position.getY() * k,
                position.getZ() * k,
                color.mul(k)
        );
    }

    @Override
    public Vertex add(Vertex vertex) {
        Point3D newPosition = position.add(vertex.getPosition());
        Col newColor = color.add(vertex.getColor());
        return new Vertex(newPosition.getX(), newPosition.getY(), newPosition.getZ(), newColor);
    }





    public Vertex toWindow(int width, int height) {
        double x = (position.getX() + 1) * width / 2;
        double y = (-position.getY() + 1) * height / 2;
        return new Vertex(x, y, position.getZ(), color);
    }


}