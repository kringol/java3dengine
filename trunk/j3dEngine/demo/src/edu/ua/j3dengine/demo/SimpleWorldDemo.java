package edu.ua.j3dengine.demo;

import static edu.ua.j3dengine.utils.Utils.*;

import edu.ua.j3dengine.core.World;
import edu.ua.j3dengine.core.DynamicGameObject;
import edu.ua.j3dengine.core.state.DynamicObjectState;
import edu.ua.j3dengine.core.geometry.BaseGeometry;
import edu.ua.j3dengine.core.geometry.Geometry;
import edu.ua.j3dengine.core.geometry.impl.ModelAdapterGeometry;
import edu.ua.j3dengine.core.mgmt.GameObjectManager;
import edu.ua.j3dengine.core.behavior.Behavior;
import edu.ua.j3dengine.processors.rendering.RenderingProcessor;
import edu.ua.j3dengine.processors.GameLogicProcessor;
import edu.ua.j3dengine.processors.execution.ProcessorLoopThread;


public class SimpleWorldDemo {

    public static void main(String[] args) {

        World world = World.create("SimpleWorld");
        DynamicGameObject archer = new DynamicGameObject("Archer");
        Geometry geom = new ModelAdapterGeometry("resources\\cal3d\\archer\\Archer.cfg", null);
        Behavior animatedBehavior = new AnimBehavior(archer);
        DynamicObjectState state1 = new DynamicObjectState("normal_state", null, null, animatedBehavior);
        archer.addState(state1);
        archer.setInitialState(state1.getName());
        //archer.initializeMovementController();
        archer.setGeometry(geom);
        try {
            GameObjectManager.getInstance().loadWorld(world);
        } catch (GameObjectManager.WorldInitializationException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        world.addGameObject(archer);


        RenderingProcessor renderProcessor = RenderingProcessor.getInstance();
       
        ThreadGroup group = new ThreadGroup("ProcessingGroup");
        ProcessorLoopThread thread = ProcessorLoopThread.create(group,
                "MainLoopThread",
                Thread.MAX_PRIORITY,
                renderProcessor);

        thread.start();


        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            //ignore
        }

        thread.deactivate();

    }


    private static class AnimBehavior extends Behavior {

        private DynamicGameObject targetObject;

        public AnimBehavior(DynamicGameObject targetObject) {
            super("SimpleAnimBehavior");
            this.targetObject = targetObject;
        }


        public void execute() {
            logDebug("executing anim behavior");
        }
    }
}
