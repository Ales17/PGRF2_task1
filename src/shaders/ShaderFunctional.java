package shaders;

import model.Vertex;
import transforms.Col;
// Shader namapovat na klávesu (oba dva typy shaderu) - součást TriangleRasterizer, v Controlleru3D volat setShader
// Poslat interpolovaný nebo konstantní
// Pro každý pixel volám shade, který mi vrátí barvu
@FunctionalInterface
public interface Shader {
    Col shade(Vertex vertex);
}
