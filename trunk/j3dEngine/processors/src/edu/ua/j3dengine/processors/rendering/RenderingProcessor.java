package edu.ua.j3dengine.processors.rendering;

import edu.ua.j3dengine.core.mgmt.GameObjectManager;
import edu.ua.j3dengine.processors.Processor;
import edu.ua.j3dengine.processors.execution.GameEnvironment;
import org.xith3d.render.base.Xith3DEnvironment;


public class RenderingProcessor extends Processor {

    private Xith3DEnvironment environment;

    public static final String TYPE = "processor.render";

    public RenderingProcessor() {
        super("RenderingProcessor");
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
