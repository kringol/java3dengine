package edu.ua.j3dengine.processors.rendering;

import edu.ua.j3dengine.core.mgmt.GameObjectManager;
import edu.ua.j3dengine.core.geometry.impl.XithGeometry;
import edu.ua.j3dengine.core.Camera;
import edu.ua.j3dengine.core.World;
import edu.ua.j3dengine.core.GameObjectFactory;
import edu.ua.j3dengine.processors.Processor;
import edu.ua.j3dengine.processors.execution.GameEnvironment;
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

    private static RenderingProcessor instance;

    public static final String TYPE = "processor.render";

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
        environment = GameEnvironment.getInstance().getEnvironment();
    }

    public void performConcreteExecute() {
        long time = GameObjectManager.getInstance().getGameTime();
        long frameTime = GameObjectManager.getInstance().getElapsedTime();

        environment.render(time, frameTime);
    }

    public void performConcreteRelease() {
        environment.destroy();
    }


    public String getType() {
        return TYPE;
    }


}
