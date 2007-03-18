package edu.ua.j3dengine.processors.rendering;

import edu.ua.j3dengine.processors.Processor;
import edu.ua.j3dengine.core.mgmt.GameObjectManager;
import edu.ua.j3dengine.core.geometry.impl.XithGeometry;
import org.xith3d.render.base.Xith3DEnvironment;
import org.xith3d.render.Canvas3D;
import org.xith3d.render.CanvasPeer;
import org.xith3d.render.canvas.Canvas3DWrapper;
import org.xith3d.scenegraph.Node;

import java.awt.*;


// todo (pablius) implementation should be tested -> no estoy seguro de q funcione!
public class RenderingProcessor extends Processor {

    private Xith3DEnvironment environment;
    private Canvas3D canvas;

    private static RenderingProcessor instance;

    private RenderingProcessor() {
        super("GameLogicProcessor");
    }

    public synchronized static RenderingProcessor getInstance() {
        if (instance == null) {
            instance = new RenderingProcessor();
        }
        return instance;
    }

    public void performConcreteInitialize() {
        environment = new Xith3DEnvironment();

        /* GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        java.awt.DisplayMode displayMode = device.getDisplayMode();
        */
        //TODO (Pablius) change this impl to new api if it works!
//        canvas = Canvas3DWrapper.createStandalone(CanvasPeer.OpenGLLayer.JOGL_AWT, Canvas3DWrapper.Resolution.RES_1024X768, Canvas3DWrapper.ColorDepth.B16, "RenderCanvas");
//
//        environment.addCanvas(canvas);
//
//        //load main geometry into scenegraph
//        Node node = ((XithGeometry)GameObjectManager.getInstance().getWorld().getGeometry()).getSceneGraphNode();
//        environment.getRootGroup().addChild(node);

    }

    public void performConcreteExecute() {
        long time = GameObjectManager.getInstance().getGameTime();
        long frameTime = GameObjectManager.getInstance().getElapsedTime();

        environment.render(time, frameTime);
    }

    public void performConcreteRelease() {
        environment.destroy();
    }


    public Xith3DEnvironment getEnvironment() {
        return environment;
    }

    public Window getWindow() {
        return (Window) canvas.get3DPeer().getWindow();
    }


}
