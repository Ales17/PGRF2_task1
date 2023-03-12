package shaders;

import model.Vertex;
import transforms.Col;

public class ShaderConstant implements ShaderFunctional {
    @Override
    public Col shade(Vertex vertex) {
          return new Col(220,50,50);

    }
}
