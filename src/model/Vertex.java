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

import transforms.*;

public class Vertex implements Vectorizable<Vertex> {

    double one;
    private Point3D position; //// Pozice vrcholu
    private Col color; //// Barva vrcholu
    private Vec2D textureCoordinates; //// Texturová souřadnice vrcholu
    private Vec3D normalVector; //// Normálový vektor vrcholu

    //// Konstruktor vrcholu
    public Vertex(double x, double y, double z, Col color) {
        position = new Point3D(x, y, z, 1);
        this.color = color;
        textureCoordinates = new Vec2D(1, 1);
        normalVector = new Vec3D(0, 0, 0);
        one = 1;
    }

    //// Konstruktor vrcholu s texturovou souřadnicí
    public Vertex(Vertex vertex) {
        position = vertex.position;
        color = vertex.color;
        textureCoordinates = vertex.textureCoordinates;
        normalVector = vertex.normalVector;
        one = vertex.one;
    }


    public Vertex(Point3D point, Col color, Vec2D textureCoordinates, Vec3D normalVector, double one) {
        this.position = point;
        this.color = color;
        this.textureCoordinates = textureCoordinates;
        this.normalVector = normalVector;
        this.one = one;
    }

    //// Gettery a settery
    public Col getColor() {
        return color;
    }

    public void setColor(Col color) {
        this.color = color;
    }

    public Point3D getPosition() {
        return position;
    }

    public void setPosition(Vec3D v) {
        this.position = new Point3D(v.getX(), v.getY(), v.getZ(), position.getW());
    }

    public double getOne() {
        return one;
    }

    public Vec2D getTextureCoordinates() {
        return textureCoordinates;
    }

    public void setTextureCoordinates(Vec2D textureCoordinates) {
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


    public Vertex mul(Mat4 transMat) {
        return new Vertex(position.mul(transMat), color, textureCoordinates, normalVector, one);
    }


    @Override
    public Vertex mul(double d) {
        return new Vertex(
                position.mul(d),
                color.mul(d),
                textureCoordinates.mul(d),
                normalVector.mul(d),
                one * d
        );
    }

    public Vertex add(Vertex vertex) {
        return new Vertex(position.add(vertex.position), color, textureCoordinates, normalVector, one * vertex.getOne());
    }


    public Vertex toWindow(int width, int height) {
        double x = (position.getX() + 1) * width / 2;
        double y = (-position.getY() + 1) * height / 2;
        return new Vertex(x, y, position.getZ(), color);
    }


}