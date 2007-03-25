package edu.ua.j3dengine.demo;


import edu.ua.j3dengine.core.Camera;
import edu.ua.j3dengine.core.DynamicGameObject;
import edu.ua.j3dengine.core.World;
import edu.ua.j3dengine.core.behavior.Behavior;
import edu.ua.j3dengine.core.geometry.Geometry;
import edu.ua.j3dengine.core.geometry.impl.ModelAdapterGeometry;
import edu.ua.j3dengine.core.geometry.impl.GeometryXithImpl;
import edu.ua.j3dengine.core.mgmt.GameObjectManager;
import edu.ua.j3dengine.core.mgmt.WorldInitializationException;
import edu.ua.j3dengine.core.movement.CameraMovementController;
import edu.ua.j3dengine.core.state.DynamicObjectState;
import static edu.ua.j3dengine.gapi.GameActions.changeAnimation;
import static edu.ua.j3dengine.gapi.GameActions.startAnimation;
import edu.ua.j3dengine.processors.GameLogicProcessor;
import edu.ua.j3dengine.processors.execution.GameEnvironment;
import edu.ua.j3dengine.processors.execution.ProcessorLoopThread;
import edu.ua.j3dengine.processors.input.InputProcessor;
import edu.ua.j3dengine.processors.input.MouseManager;
import edu.ua.j3dengine.processors.rendering.RenderingProcessor;
import org.xith3d.loaders.models.util.precomputed.PrecomputedAnimatedModel;
import org.xith3d.scenegraph.Light;
import org.xith3d.scenegraph.AmbientLight;
import org.xith3d.scenegraph.Background;
import org.xith3d.scenegraph.Transform;

import javax.vecmath.Vector3f;
import javax.vecmath.Color3f;


public class SimpleWorldDemo {

    public static void main(String[] args) {

        World world = World.create("SimpleWorld");

        try {
            GameObjectManager.getInstance().loadWorld(world);
        } catch (WorldInitializationException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        DynamicGameObject archer = new DynamicGameObject("Archer");
        Geometry geom = new ModelAdapterGeometry("resources\\cal3d\\archer\\Archer.cfg", null, true);
        ((ModelAdapterGeometry)geom).setTransform(new Transform().addRotationX(-90).addScale(10).addRotationZ(-90).getTransform());
        
        Behavior animatedBehavior = new AnimBehavior(archer);
        Behavior initB = new SetAnimBehavior(archer);
        DynamicObjectState state1 = new DynamicObjectState("normal_state", initB, null, animatedBehavior);
        archer.addState(state1);
        //archer.initializeMovementController();
        archer.setGeometry(geom);
        archer.setInitialState(state1.getName());

        world.addGameObject(archer);

        //initialize
        GameEnvironment.getInstance();

        Behavior cameraBehav = new CameraBehavior(world.getDefaultCamera());

        DynamicObjectState camState = new DynamicObjectState("MovingState", null, null, cameraBehav);
        world.getDefaultCamera().addState(camState);
        world.getDefaultCamera().setCurrentState(camState);

        GameEnvironment.getInstance().getEnvironment().getView().lookAt(new Vector3f(-100, 0, -100), new Vector3f(1000, 0, 1000), new Vector3f(0,1,0));
        Light light = new AmbientLight(true);
        light.setColor(new Color3f(1,1,1));
//        Background background = new Background();
//        background.setColor(new Color3f(0.17f, 0.65f, 0.92f));//supposed to be sky color
        GameEnvironment.getInstance().getEnvironment().getRootGroup().addChild(light);
//        GameEnvironment.getInstance().getEnvironment().getRootGroup().addChild(background);
        startProcessors();



        /*
//        final World world = World.create("SampleWorld");
//        try {
//            GameObjectManager.getInstance().loadWorld(world);
//        } catch (WorldInitializationException e) {
//            e.printStackTrace();
//        }
//        final DynamicGameObject object = GameObjectFactory.getInstance().createDynamicObject("jeep_geom", model);
//        world.addGameObject(object);
//        object.initializeMovementController();
//        ((BasicMovementController)object.getMovementController()).setDirection(new Vector3f(-1,0,0));
//        ((BasicMovementController)object.getMovementController()).setSpeed(1);

        
         */
    }

    private static class CameraBehavior extends Behavior {

        private Camera targetCamera;
        private Vector3f direction;
        private CameraMovementController movementController;
        private float speed;

        public CameraBehavior(Camera targetCamera) {
            this.targetCamera = targetCamera;
            movementController = (CameraMovementController) targetCamera.getMovementController();
            direction = new Vector3f(-1, 5, -1);
            speed = 1;
        }


        public void execute() {
            double elapsedTime = GameObjectManager.getInstance().getElapsedSeconds();


            //todo uncomment this
//            if (direction != null && speed != 0) {
//                Vector3f translationVector = new Vector3f(movementController.getLocation());
//
//                double delta = (speed * elapsedTime);
//
//                translationVector.x += direction.x * delta;
//                translationVector.y += direction.y * delta;
//                translationVector.z += direction.z * delta;
//
//
//                movementController.setLocation(translationVector);
//            }


        }
    }

    private static void startProcessors() {
        RenderingProcessor renderProcessor = new RenderingProcessor();
        InputProcessor inputProcessor = new InputProcessor();
        GameLogicProcessor logicProcessor = new GameLogicProcessor();

        ThreadGroup group = new ThreadGroup("ProcessingGroup");
        ProcessorLoopThread thread = ProcessorLoopThread.create(group,
                "MainLoopThread",
                Thread.MAX_PRIORITY,
                inputProcessor,
                logicProcessor,
                renderProcessor);

        thread.start();


        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            //ignore
        }
        //group.interrupt();
        thread.deactivate();
    }


    private static class SetAnimBehavior extends Behavior {
        private DynamicGameObject targetObject;

        public SetAnimBehavior(DynamicGameObject targetObject) {
            super("SetAnimBehavior");
            this.targetObject = targetObject;
        }


        public void execute() {
            changeAnimation("Archer", "walk", true);
            startAnimation("Archer");
//            PrecomputedAnimatedModel animated = (PrecomputedAnimatedModel) ((XithGeometry) targetObject.getGeometry()).getSceneGraphNode();
//            Animation anim = animated.getAnimations().get("walk");
//            assert anim != null;
//            animated.play(anim, true);
        }
    }

    private static class AnimBehavior extends Behavior {

        private DynamicGameObject targetObject;
        PrecomputedAnimatedModel animated;

        public AnimBehavior(DynamicGameObject targetObject) {
            super("AnimBehavior");
            this.targetObject = targetObject;

        }


        public void execute() {

//            if (animated == null) {
//                animated = (PrecomputedAnimatedModel) ((XithGeometry) targetObject.getGeometry()).getSceneGraphNode();
//            }
//            animated.executeOperation(GameObjectManager.getInstance().getGameTime(), GameObjectManager.getInstance().getElapsedTime());




//            logDebug("executing anim behavior");
//
//            int xDelta = MouseManager.getXDelta();
//            int yDelta = MouseManager.getYDelta();
//            if (xDelta != 0 || yDelta != 0){
//                logDebug("\n------\nxDelta = " + xDelta + ", yDelta = " + yDelta + "\n--------");
//            }
//            boolean pressed = KeyboardManager.isKeyPressed(KeyCode.VK_SPACE);
//            logDebug("pressed:"+pressed);

        }
    }
}
