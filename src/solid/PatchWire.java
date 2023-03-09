package solid;

import transforms.Bicubic;
import transforms.Point3D;

public class PatchWire extends Solid {
    public PatchWire() {
        //TODO ridici body
        // 16 řídících bodů
        Point3D points[] = new Point3D[16];
        points[0] = new Point3D(-1, -1, -1);
        points[1] = new Point3D(1, 1, -1);
        points[2] = new Point3D(1, -1, 1);
        points[3] = new Point3D(-1, 1, 1);
        points[4] = new Point3D(-1, -1, -1);
        points[5] = new Point3D(1, 1, -1);
        points[6] = new Point3D(1, -1, 1);
        points[7] = new Point3D(-1, 1, 1);
        points[8] = new Point3D(-1, -1, -1);
        points[9] = new Point3D(1, 1, -1);
        points[10] = new Point3D(1, -1, 1);
        points[11] = new Point3D(-1, 1, 1);
        points[12] = new Point3D(-1, -1, -1);
        points[13] = new Point3D(1, 1, -1);
        points[14] = new Point3D(1, -1, 1);
        points[15] = new Point3D(-1, 1, 1);

        //todo instance

        // for cykly v sobě

        for(int i = 1; i < 100; i++){
           double t = i / 100.0;

           for (int j = 1; j <= 100; j++) {
               double u = j / 100.0;

               // zařídit drátový model

               // drátový model požadován
               // bonus

           }
        }

    }
}
