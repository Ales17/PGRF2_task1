package shaders;

import model.Vertex;
import transforms.Col;

public class ShaderInterpolatedColor implements Shader{
    @Override
    public Col shade(Vertex vertex) { // Neumíme dělit, takže vynásobíme převrácenou hodnotou
        return vertex.getColor().mul(1/ vertex.getOne());


    }
}
