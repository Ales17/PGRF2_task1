package model;

import transforms.*;

import java.util.Optional;

public class Vertex implements Vectorizable<Vertex> {
    double one;
    private Point3D position; //// Pozice vrcholu
    private Col color; //// Barva vrcholu
    private Vec2D textureCoordinates; //// Texturová souřadnice vrcholu
    private Vec3D normalVector; //// Normálový vektor vrcholu

    boolean isWire = false;

    //// Konstruktor vrcholu
    public Vertex(double x, double y, double z, Col color) {
        position = new Point3D(x, y, z, 1);
        normalVector = new Vec3D(0, 0, 0);
        textureCoordinates = new Vec2D(1, 1);
        this.color = color;
        one = 1;
    }

    //// Konstruktor vrcholu s texturovou souřadnicí
    public Vertex(Vertex vertex) {
        position = vertex.position;
        color = vertex.color;
        normalVector = vertex.normalVector;
        textureCoordinates = vertex.textureCoordinates;
        one = 1;
    }

    public Vertex(Point3D position) {
        //TODO implement
    }
    //// Gettery a settery
    public Col getColor() {
        return color;
    }

    public void setPosition(Vec3D v) {
        position = new Point3D(v.getX(), v.getY(), v.getZ(), position.getW());
    }
    public Point3D getPosition() {
        return position;
    }

    public double getOne() {
        return one;
    }

    public Vec3D getNormalVector() {
        return normalVector;
    }
    public Vec2D getTextureCoordinates() {
        return textureCoordinates;
    }

    public void setTextureCoordinates(Vec2D textureCoordinates) {
        this.textureCoordinates = textureCoordinates;
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

    public Vertex(Point3D point, Col color, Vec2D textureCoordinates, Vec3D normalVector, double one) {
        this.position = point;
        this.color = color;
        this.textureCoordinates = textureCoordinates;
        this.normalVector = normalVector;
        this.one = one;
    }
    public Vertex add(Vertex mul) {
        return new Vertex(
                position.add(mul.position),
                color.add(mul.color),
                textureCoordinates.add(mul.textureCoordinates),
                normalVector.add(mul.normalVector),
                one + mul.one
        );
    }
    @Override
    public Vertex mul(double d) { ////
        return new Vertex(
                position.mul(d),
                color.mul(d),
                textureCoordinates.mul(d),
                normalVector.mul(d),
                one * d
        );
    }

    public Optional<Vertex> dehom() {
        if (position.getW() == 0.)
            return Optional.empty();
        return Optional.of( new Vertex(
                new Point3D(position.getX() / position.getW(), position.getY() / position.getW(), position.getZ() / position.getW(), 1.0),
                new Col(color.getR() / position.getW(), color.getG() / position.getW(), color.getB() / position.getW()),
                new Vec2D(textureCoordinates.getX() / position.getW(), textureCoordinates.getY() / position.getW()),
                new Vec3D(normalVector.getX() / position.getW(), normalVector.getY() / position.getW(), normalVector.getZ() / position.getW()),
                one / position.getW()
        ));
    }


}