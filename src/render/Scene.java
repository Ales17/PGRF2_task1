package render;

import solid.Solid;
import transforms.Mat4;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    private List<Solid> solids = new ArrayList<>();
    public Scene() {
    }

    public List<Solid> getSolids() {
        return solids;
    }

    public void addSolid(Solid solid){
        this.solids.add(solid);
    }

    public void setTransform(Mat4 model) {
        solids.forEach(solid -> solid.setModelMatrix(model));
    }
    // render using Renderer
    public void renderAll(Renderer renderer) {
        solids.forEach(renderer::render);
    }
}
