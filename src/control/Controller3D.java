
package control;

import model.*;
import raster.*;
import render.Renderer;
import transforms.*;
import view.Panel;

import java.awt.*;
import java.awt.event.*;



public class Controller3D implements Controller {
    private final Panel panel; //// Panel aplikace


    private int width, height; //// Velikost panelu
    private Camera camera = new Camera()
            .withPosition(new Vec3D(-0.36,-0.73,1.82))
            .withAzimuth(-4.58)
            .withZenith(-1.33)
            .withFirstPerson(true);
    private ZBuffer zBuffer;
    private TriangleRasterizer triangleRasterizer;
    private LineRasterizer lineRasterizer;
    Renderer renderer;
    Mat4 projection = new Mat4Identity();

    int modeCut=0;
Cube cube = new Cube();
    public Controller3D(Panel panel) {
        this.panel = panel;
        initObjects(panel.getRaster());
        initListeners(panel);
        redraw();
    }

    public void initObjects(ImageBuffer raster) {
        raster.setClearValue(new Col(0xff0000));
        zBuffer = new ZBuffer(panel.getRaster());
        projection = new Mat4PerspRH(Math.PI / 3,
                panel.getHeight()/ (float) panel.getWidth(),
                0.1,
                30);
        triangleRasterizer = new TriangleRasterizer(zBuffer);
    }

    @Override
    public void initListeners(Panel panel) {


    }
    private void redraw() {
        width = panel.getRaster().getWidth();
        height = panel.getRaster().getHeight();
        Graphics g = panel.getRaster().getGraphics();

        g.setColor(Color.white);

        redraw3D();
        panel.repaint();
    }



    private void redraw3D() {

        triangleRasterizer.getzBuffer().getDepthBuffer().clear();//vyčistit si  buffer vždycky před každým kreslením

        triangleRasterizer.setModeCut(modeCut);
        renderer = new Renderer(triangleRasterizer);
        renderer.setProjectionMatrix(projection);
        renderer.setView(camera.getViewMatrix());

       /* renderer.render(ar);
        renderer.render(arrowZ);
        renderer.render(arrowY);
        renderer.render(axesX);
        renderer.render(cube);
        renderer.render(cube2);*/


    }



}



