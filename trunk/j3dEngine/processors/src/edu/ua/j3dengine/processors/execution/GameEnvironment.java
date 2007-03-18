package edu.ua.j3dengine.processors.execution;

import org.xith3d.render.base.Xith3DEnvironment;
import org.xith3d.render.Canvas3D;
import org.xith3d.render.Canvas3DFactory;
import org.xith3d.render.config.DisplayMode;
import org.xith3d.render.config.DisplayModeSelector;
import org.xith3d.render.config.OpenGLLayer;
import org.xith3d.scenegraph.Node;
import org.xith3d.scenegraph.BranchGroup;
import edu.ua.j3dengine.core.geometry.impl.XithGeometry;
import edu.ua.j3dengine.core.mgmt.GameObjectManager;

import javax.vecmath.Vector3f;
import java.awt.*;


public class GameEnvironment {

    private static GameEnvironment instance;

    private Xith3DEnvironment environment;
    private Canvas3D canvas;

    private static final Vector3f VEC_UP = new Vector3f(0,1,0);


    public static synchronized GameEnvironment getInstance(){
        if (instance == null){
            instance = new GameEnvironment();
        }

        return instance;
    }
    private GameEnvironment() {
        initialize();
    }
    
    private void initialize(){
        environment = new Xith3DEnvironment();
        environment = new Xith3DEnvironment();

        //todo (pablius) change this to full screen
        DisplayMode mode = DisplayModeSelector.getImplementation( OpenGLLayer.JOGL_AWT ).getBestMode( 1024, 768 );

        canvas = Canvas3DFactory.createWindowed( OpenGLLayer.JOGL_AWT, mode, "RenderCanvas");

        environment.addCanvas(canvas);

        //load main geometry into scenegraph
        Node node = ((XithGeometry) GameObjectManager.getInstance().getWorld().getGeometry()).getSceneGraphNode();
        BranchGroup mainBranchGroup = new BranchGroup(node);
        environment.addBranchGraph(mainBranchGroup);

        environment.getView().lookAt(new Vector3f(0,0,0), new Vector3f(100,0,0), VEC_UP);

        //set default camera to world
        GameObjectManager.getInstance().setDefaultCamera(environment.getView());

    }




    public Xith3DEnvironment getEnvironment() {
        return environment;
    }

    public Window getWindow() {
        return (Window) canvas.get3DPeer().getWindow();
    }
}
