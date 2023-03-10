package model;

import transforms.Vec3D;

public enum RotationAxis {
    X(new Vec3D(1, 0, 0)),
    Y(new Vec3D(0, 1, 0)),
    Z(new Vec3D(0, 0, 1));

    private final Vec3D axis;

    RotationAxis(Vec3D axis) {
        this.axis = axis;
    }

    public Vec3D getAxis() {
        return axis;
    }
}

