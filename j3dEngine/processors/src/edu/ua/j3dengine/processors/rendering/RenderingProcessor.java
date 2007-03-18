package edu.ua.j3dengine.processors.rendering;

import edu.ua.j3dengine.core.mgmt.GameObjectManager;
import edu.ua.j3dengine.core.geometry.impl.XithGeometry;
import edu.ua.j3dengine.core.Camera;
import edu.ua.j3dengine.core.World;
import edu.ua.j3dengine.core.GameObjectFactory;
import edu.ua.j3dengine.processors.Processor;
import org.xith3d.render.Canvas3D;
import org.xith3d.render.Canvas3DFactory;
import org.xith3d.render.base.Xith3DEnvironment;
import org.xith3d.render.config.DisplayModeSelector;
import org.xith3d.render.config.OpenGLLayer;
import org.xith3d.render.config.DisplayMode;
import org.xith3d.scenegraph.Node;
import org.xith3d.scenegraph.BranchGroup;

import javax.vecmath.Vector3f;
import java.awt.*;


public class RenderingProcessor extends Processor {

    private Xith3DEnvironment environment;
    private Canvas3D canvas;

    private static RenderingProcessor instance;
    private static final Vector3f VEC_UP = new Vector3f(0,1,0);

    private RenderingProcessor() {
        super("RenderingProcessor");
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

        canvas = Canvas3DFactory.createWindowed( OpenGLLayer.JOGL_AWT, mode, "RenderCanvas");

        environment.addCanvas(canvas);

        //load main geometry into scenegraph
        Node node = ((XithGeometry)GameObjectManager.getInstance().getWorld().getGeometry()).getSceneGraphNode();
        BranchGroup mainBranchGroup = new BranchGroup(node);
        environment.addBranchGraph(mainBranchGroup);

        environment.getView().lookAt(new Vector3f(0,0,0), new Vector3f(100,0,0), VEC_UP);

        //set default camera to world
        GameObjectManager.getInstance().setDefaultCamera(environment.getView());
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
