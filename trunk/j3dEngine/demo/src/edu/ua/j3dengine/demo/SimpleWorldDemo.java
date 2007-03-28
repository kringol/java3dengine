package edu.ua.j3dengine.demo;


import edu.ua.j3dengine.core.Camera;
import edu.ua.j3dengine.core.DynamicGameObject;
import edu.ua.j3dengine.core.World;
import edu.ua.j3dengine.core.behavior.Behavior;
import edu.ua.j3dengine.core.geometry.Geometry;
import edu.ua.j3dengine.core.geometry.impl.ModelAdapterGeometry;
import edu.ua.j3dengine.core.mgmt.GameObjectManager;
import edu.ua.j3dengine.core.mgmt.WorldInitializationException;
import edu.ua.j3dengine.core.movement.CameraMovementController;
import edu.ua.j3dengine.core.state.DynamicObjectState;
import static edu.ua.j3dengine.gapi.GameActions.*;

import edu.ua.j3dengine.processors.GameLogicProcessor;
import edu.ua.j3dengine.processors.execution.GameEnvironment;
import edu.ua.j3dengine.processors.execution.ProcessorLoopThread;
import edu.ua.j3dengine.processors.input.InputProcessor;
import edu.ua.j3dengine.processors.input.MouseManager;
import edu.ua.j3dengine.processors.rendering.RenderingProcessor;
import static net.jtank.input.KeyCode.*;
import org.xith3d.render.Canvas3D;
import org.xith3d.scenegraph.Transform;
import org.xith3d.scenegraph.View;

import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;


public class SimpleWorldDemo {

    public static void main(String[] args) {

        World world = World.create("SimpleWorld");

        try {
            GameObjectManager.getInstance().loadWorld(world);
        } catch (WorldInitializationException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        final DynamicGameObject archer = new DynamicGameObject("Archer");
        Geometry geom = new ModelAdapterGeometry("resources\\cal3d\\archer\\Archer.cfg", null, true, true);
        //  Geometry geom = new ModelAdapterGeometry("resources\\3ds\\jeep\\jeep1.3ds", null, false);
        ((ModelAdapterGeometry)geom).setTransform(new Transform().addRotationX(-90).addScale(10).getTransform());

        KeyboardMovementBehavior keyboardBehav = new KeyboardMovementBehavior();
        Behavior initB = new WalkInit(archer);
        DynamicObjectState walking = new DynamicObjectState("walking_state", initB, null, keyboardBehav);
        initB = new IdleInit(archer);
        DynamicObjectState idle = new DynamicObjectState("idle_state", initB, null, keyboardBehav);
        initB = new AttackInit(archer);
        DynamicObjectState attack = new DynamicObjectState("attack_state", initB, null, keyboardBehav);
        archer.addState(walking);
        archer.addState(idle);
        archer.addState(attack);
        archer.setGeometry(geom);
        archer.setInitialState(idle.getName());

        //world.addGameObject(archer);

        //initialize
        GameEnvironment.getInstance();

        Behavior cameraBehav = new MouseCameraBehavior(world.getDefaultCamera());

        DynamicObjectState camState = new DynamicObjectState("MovingState", null, null, cameraBehav);
        world.getDefaultCamera().addState(camState);
        world.getDefaultCamera().setCurrentState(camState);

        GameEnvironment.getInstance().getEnvironment().getView().lookAt(new Vector3f(-100, 50, -100), new Vector3f(1000, 0, 1000), new Vector3f(0,1,0));

        startProcessors();
       // MouseManager.init(true);

        new Thread("TesterThread"){

            @Override
            public void run() {
                System.out.println("Thread is running");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                World world = GameObjectManager.getInstance().getWorld();
                world.addGameObject(archer);
                System.out.println("Archer added");
                GameEnvironment.getInstance().getEnvironment().getRootGroup().dump();
            }
        }.start();


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

    private static class MouseCameraBehavior extends Behavior {

        private static final float TWO_PI = (2.0f * (float)Math.PI);
//        private static final Vector3f XAXIS = new Vector3f(1,0,0);
//        private static final Vector3f YAXIS = new Vector3f(0,1,0);
        private static final float MAX_ANGLE_UPDOWN = (float)Math.toRadians( 80.0 );

        private int canvasWidth;
        private int canvasHeight;
        private Tuple3f viewEuler;

        private Camera targetCamera;
        private View targetView;
        private double sensitivity;
        private double wheelSensitivity;
        private CameraMovementController movementController;


        public MouseCameraBehavior(Camera targetCamera) {
            this.targetCamera = targetCamera;
            this.targetView = targetCamera.getGeometry().getView();
            movementController = (CameraMovementController) targetCamera.getMovementController();
            this.sensitivity = 0.2;
            this.wheelSensitivity = 0.5;

            this.viewEuler = new Vector3f(0,0,0);
            this.targetView.getTransform().getEuler(this.viewEuler);

            Canvas3D canvas = GameEnvironment.getInstance().getCanvas();
            this.canvasHeight = canvas.getHeight();
            this.canvasWidth = canvas.getWidth();
        }


        public void execute() {

            //update rotation
            int dX = MouseManager.getXDelta();
            int dY = MouseManager.getYDelta();
            int dWheel = MouseManager.getWheelDelta();
//            float xRotAngle = (float) sensitivity * -dX;
//            float yRotAngle = (float) sensitivity * -dY;
//            targetView.getTransform().mul(new Transform().addRotationX(xRotAngle).addRotationY(yRotAngle).getTransform());

            // calculate angle
            if (dX != 0 || dY != 0){


                viewEuler.y += (TWO_PI * -((float)dX / (float)canvasWidth) * sensitivity);
                viewEuler.x -= (TWO_PI * -((float)dY / (float)canvasHeight) * sensitivity);

                while (viewEuler.y > TWO_PI){
                    viewEuler.y -= TWO_PI;
                }

                while (viewEuler.y < 0.0f){
                    viewEuler.y += TWO_PI;
                }

                if (viewEuler.x < -MAX_ANGLE_UPDOWN){
                    viewEuler.x = -MAX_ANGLE_UPDOWN;
                }
                else if (viewEuler.x > MAX_ANGLE_UPDOWN){
                    viewEuler.x = MAX_ANGLE_UPDOWN;
                }

                targetView.getTransform().setEuler(viewEuler);
            }

            //update translation
            float variation = (float) wheelSensitivity * dWheel;
            if (Math.abs(variation) != 0){
                Camera camera = GameObjectManager.getInstance().getDefaultCamera();
                Tuple3f viewLocation = camera.getGeometry().getLocation();
                Tuple3f viewDirection = camera.getGeometry().getDirection();

                final Vector3f normalizedViewDirection = new Vector3f(viewDirection);
                normalizedViewDirection.normalize();
                normalizedViewDirection.scale(variation);
                viewLocation.add(normalizedViewDirection);
            
                setViewLocation(new Vector3f(viewLocation));
            }
        }
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

            if (direction != null && speed != 0) {
                Vector3f translationVector = new Vector3f(movementController.getLocation());

                double delta = (speed * elapsedTime);

                translationVector.x += direction.x * delta;
                translationVector.y += direction.y * delta;
                translationVector.z += direction.z * delta;

                movementController.setLocation(translationVector);
            }


        }
    }

    private static void startProcessors() {

        new Thread("Processors starter"){

            @Override
            public void run() {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    //ignore
                }

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
        }.start();

    }


    private static class WalkInit extends Behavior {
        private DynamicGameObject targetObject;

        public WalkInit(DynamicGameObject targetObject) {
            super("WalkInitBehavior");
            this.targetObject = targetObject;
        }

        public void execute() {
            changeAnimation("Archer", "walk", true);
            startAnimation("Archer");
        }
    }
    private static class AttackInit extends Behavior {
        private DynamicGameObject targetObject;

        public AttackInit(DynamicGameObject targetObject) {
            super("AttackInitBehavior");
            this.targetObject = targetObject;
        }

        public void execute() {
            changeAnimation("Archer", "attack", true);
            startAnimation("Archer");
        }
    }
    private static class IdleInit extends Behavior {
        private DynamicGameObject targetObject;

        public IdleInit(DynamicGameObject targetObject) {
            super("IdleInitBehavior");
            this.targetObject = targetObject;
        }

        public void execute() {
            changeAnimation("Archer", "idle", true);
            startAnimation("Archer");
        }
    }



    private static class KeyboardMovementBehavior extends Behavior {
        private static final Vector3f INITIAL_DIRECTION = new Vector3f(0,0,1);
        private static final int SPEED = 50;
        private boolean firstTime = true;

        public KeyboardMovementBehavior() {
            super("KeyboardMovementBehavior");
        }


        public void execute() {
            if (firstTime){ //todo move to initial state
                setSpeed("Archer", INITIAL_DIRECTION, 0);
                firstTime = false;
            }

            if (isKeyPressed(VK_SPACE)){
                setSpeed("Archer", 0);
                changeState("Archer", "attack_state");
            }
            else
            if (isKeyPressed(VK_UP) && isKeyPressed(VK_RIGHT)){
                rotate("Archer", Y_AXIS, -45);
                setSpeed("Archer", -1, 0, 1, SPEED);
                changeState("Archer", "walking_state");
            }else
            if (isKeyPressed(VK_UP) && isKeyPressed(VK_LEFT)){
                rotate("Archer", Y_AXIS, 45);
                setSpeed("Archer", 1, 0, 1, SPEED);
                changeState("Archer", "walking_state");
            }else
            if (isKeyPressed(VK_DOWN) && isKeyPressed(VK_RIGHT)){
                rotate("Archer", Y_AXIS, -135);
                setSpeed("Archer", -1, 0, -1, SPEED);
                changeState("Archer", "walking_state");
            }else
            if (isKeyPressed(VK_DOWN) && isKeyPressed(VK_LEFT)){
                rotate("Archer", Y_AXIS, 135);
                setSpeed("Archer", 1, 0, -1, SPEED);
                changeState("Archer", "walking_state");
            }else
            if (isKeyPressed(VK_DOWN)){
                rotate("Archer", Y_AXIS, 180);
                setSpeed("Archer", 0, 0, -1,SPEED);
                changeState("Archer", "walking_state");
            }else
            if (isKeyPressed(VK_UP)){
                rotate("Archer", Y_AXIS, 0);
                setSpeed("Archer", 0, 0, 1,SPEED);
                changeState("Archer", "walking_state");
            }else
            if (isKeyPressed(VK_RIGHT)){
                rotate("Archer", Y_AXIS, -90);
                setSpeed("Archer", -1, 0, 0,SPEED);
                changeState("Archer", "walking_state");
            }else
            if (isKeyPressed(VK_LEFT)){
                rotate("Archer", Y_AXIS, 90);
                setSpeed("Archer", 1, 0, 0,SPEED);
                changeState("Archer", "walking_state");
            }else{
                setSpeed("Archer", 0);
                changeState("Archer", "idle_state");
            }
        }
    }

}
