package edu.ua.j3dengine.processors.rendering;

import edu.ua.j3dengine.core.mgmt.GameObjectManager;
import edu.ua.j3dengine.core.geometry.impl.XithGeometry;
import edu.ua.j3dengine.processors.Processor;
import org.xith3d.render.Canvas3D;
import org.xith3d.render.Canvas3DFactory;
import org.xith3d.render.base.Xith3DEnvironment;
import org.xith3d.render.config.DisplayModeSelector;
import org.xith3d.render.config.OpenGLLayer;
import org.xith3d.render.config.DisplayMode;
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

        //todo (pablius) change this to full screen 
        DisplayMode mode = DisplayModeSelector.getImplementation( OpenGLLayer.JOGL_AWT ).getBestMode( 1024, 768 );

        Canvas3D canvas = Canvas3DFactory.createWindowed( OpenGLLayer.JOGL_AWT, mode, "RenderCanvas");

        environment.addCanvas(canvas);

        //load main geometry into scenegraph
        Node node = ((XithGeometry)GameObjectManager.getInstance().getWorld().getGeometry()).getSceneGraphNode();
        environment.getRootGroup().addChild(node);

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
