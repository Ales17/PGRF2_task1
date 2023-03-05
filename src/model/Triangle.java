package model;

import transforms.Col;

public class Triangle extends Solid {

        public Triangle() {
            // Vertex buffer

            getVertexBuffer().add(new Vertex(0, 0, 0, new Col(0x00ff00)));
            getVertexBuffer().add(new Vertex(1, 0, 0, new Col(0x00ff00)));
            getVertexBuffer().add(new Vertex(0, 1, 0, new Col(0x00ff00)));

            // Index buffer

            getIndexBuffer().add(0);
            getIndexBuffer().add(1);
            getIndexBuffer().add(2);


            // Part buffer
            getPartBuffer().add(new Part(TopologyType.TRIANGLE, 0, 1));





        }
}
