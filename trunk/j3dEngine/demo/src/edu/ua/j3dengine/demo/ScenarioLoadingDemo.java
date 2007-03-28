package edu.ua.j3dengine.demo;

import edu.ua.j3dengine.core.mgmt.ResourceManager;
import edu.ua.j3dengine.core.mgmt.GameObjectManager;
import edu.ua.j3dengine.core.mgmt.WorldInitializationException;
import edu.ua.j3dengine.core.movement.impl.DefaultMovementController;
import edu.ua.j3dengine.core.movement.BasicMovementController;
import edu.ua.j3dengine.core.DynamicGameObject;
import edu.ua.j3dengine.core.GameObjectFactory;
import edu.ua.j3dengine.core.World;
import edu.ua.j3dengine.core.geometry.impl.GeometryXithImpl;
import org.xith3d.geometry.*;
import org.xith3d.geometry.Rectangle;
import org.xith3d.loaders.models.base.Model;
import org.xith3d.loaders.models.util.precomputed.PrecomputedAnimatedModel;
import org.xith3d.loaders.models.util.precomputed.Animation;
import org.xith3d.loaders.texture.TextureLoader;
import org.xith3d.render.Canvas3D;

import org.xith3d.render.Option;
import org.xith3d.render.Canvas3DFactory;
import org.xith3d.render.config.OpenGLLayer;
import org.xith3d.render.config.DisplayModeSelector;
import org.xith3d.render.config.DisplayMode;
import org.xith3d.render.base.Xith3DEnvironment;
import org.xith3d.render.loop.RenderLoop;
import org.xith3d.scenegraph.*;
import org.xith3d.spatial.bounds.Frustum;

import javax.swing.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point2f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.*;
import java.util.ArrayList;


public class ScenarioLoadingDemo {

    public String visibleText;

    private static final int TIME = 100;
    private static final int MAX_FPS = TIME;

    private Xith3DEnvironment environment;
    private Window window;
    private TransformGroup tgReference;

    private volatile boolean rendering = true;

    private Color3f white = new Color3f(1, 1, 1);
    private Color3f black = new Color3f(0, 0, 0);
    private Color3f blue = new Color3f(0, 0, 1);
    private Color3f red = new Color3f(1, 0, 0);
    private Color3f green = new Color3f(0, 1, 0);


    private Color3f specular = new Color3f(0.9f, 0.9f, 0.9f);
    private volatile int wheelRotation;
    private volatile Tuple3f mouseMovement;
    private volatile boolean isRecenteringMouse;

    private static final Vector3f VECTOR_UP_ORIENTATION = new Vector3f(0, 1, 0);

    private static final float MOUSE_SENSITIVITY = 5;
    private static final int TIMEOUT = 60;

    public void showBasicScene() {

        environment = createEnvironment();

        BranchGroup bg = new BranchGroup();
        //bg.setAllBounds(new BoundingSphere(new Vector3f(0,0,0), 100000f));
        environment.addBranchGraph(bg);

        addListeners();

        defineView();

        defineBackground(bg);
        //loadSkyBox(bg);
        //loadSkyBox2(bg);

        setAmbientLight(bg);

        //setDirectionalLight(bg);

        TransformGroup firstTG = addSphere(bg);

        addAxes(bg);

        bg.addChild(firstTG);


        loadModel(firstTG);
        //loadModel2(firstTG);

        bg.addChild(createFloor());

        startRenderLoop();


    }

    private Group createFloor() {
        Rectangle floor = new Rectangle("resources\\textures\\grass.jpg", false, 1000f, 1000f);
        Material material = new Material(true);
        floor.getAppearance().setMaterial(material);
        
        return (new Transform(floor).setTranslationY(0).addRotationX(-90f));
    }


    private void loadSkyBox(Group root) {
        //Texture texture = TextureLoader.getInstance().getTexture("resources\\images\\Nuages.jpg");
        TextureLoader textureloader = TextureLoader.getInstance();
        Texture top = textureloader.getTexture("resources\\skyboxes\\normal\\top.png");
        Texture bottom = textureloader.getTexture("resources\\skyboxes\\normal\\bottom.png");
        Texture right = textureloader.getTexture("resources\\skyboxes\\normal\\right.png");
        Texture left = textureloader.getTexture("resources\\skyboxes\\normal\\left.png");
        Texture front = textureloader.getTexture("resources\\skyboxes\\normal\\front.png");
        Texture back = textureloader.getTexture("resources\\skyboxes\\normal\\back.png");

        SkyBox background = new SkyBox(front, right, back, left, top, bottom);

        root.addChild(background);
    }

    private void loadSkyBox2(Group root) {
        //Texture texture = TextureLoader.getInstance().getTexture("resources\\images\\Nuages.jpg");
        TextureLoader textureloader = TextureLoader.getInstance();
        Texture top = textureloader.getTexture("resources\\skyboxes\\skymatter\\pos_y.jpg");
        Texture bottom = textureloader.getTexture("resources\\skyboxes\\skymatter\\neg_y.jpg");
        Texture right = textureloader.getTexture("resources\\skyboxes\\skymatter\\pos_x.jpg");
        Texture left = textureloader.getTexture("resources\\skyboxes\\skymatter\\neg_x.jpg");
        Texture front = textureloader.getTexture("resources\\skyboxes\\skymatter\\pos_z.jpg");
        Texture back = textureloader.getTexture("resources\\skyboxes\\skymatter\\neg_z.jpg");

        SkyBox background = new SkyBox(front, right, back, left, top, bottom);

        root.addChild(background);
    }


    private void loadModel2(TransformGroup firstTG){


        //model = ResourceManager.getInstance().getModel("resources\\obj\\interior.obj", ResourceManager.ModelFormat.WAVEFRONT);
        //model = ResourceManager.getInstance().getModel("resources\\obj\\ferrari\\ferrari.obj", ResourceManager.ModelFormat.WAVEFRONT);
        //
        PrecomputedAnimatedModel model = null;

        model = ResourceManager.getInstance().getPrecomputedCal3DModel("resources\\cal3d\\archer\\Archer.cfg");
//        try {
//            model = new Cal3dLoader(Cal3dLoader.LOADER_INVERT_V_COORD).loadPrecomputedModel("resources\\cal3d\\archer\\Archer.cfg");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        firstTG.addChild(new Transform().addRotationX(-90).addScale(10).addRotationZ(-90).add(model));
        firstTG.addChild(createFloor());

        final PrecomputedAnimatedModel animated = model;
        new Thread("animator"){
            private long time = 0;
            int i = 0;

            @Override
            public void run() {
                while(!rendering){
                    try {
                        Thread.sleep(TIME);
                    } catch (InterruptedException e) {
                        //ignore
                    }
                }
                Animation animation = animated.getAnimations().get("walk");
                animated.play(animation, true);
                //animated.startAnimation(0);
                while(rendering){

                    animated.executeOperation(time, TIME);


                    if (time > 0 && time % 5000 == 0){
                        i = (i+1) % 3;
                        animated.play(animated.getAnimation(i), true);
                    }

                    try {
                        Thread.sleep(TIME);
                    } catch (InterruptedException e) {
                        //ignore
                    }
                    time += TIME;
                }
            }
        }.start();

       
    }




     private void loadModel3(TransformGroup firstTG){


        //model = ResourceManager.getInstance().getModel("resources\\obj\\interior.obj", ResourceManager.ModelFormat.WAVEFRONT);
        //model = ResourceManager.getInstance().getModel("resources\\obj\\ferrari\\ferrari.obj", ResourceManager.ModelFormat.WAVEFRONT);
        //
        PrecomputedAnimatedModel model = null;

        model = ResourceManager.getInstance().getPrecomputedCal3DModel("resources\\cal3d\\archer\\Archer.cfg");
//        try {
//            model = new Cal3dLoader(Cal3dLoader.LOADER_INVERT_V_COORD).loadPrecomputedModel("resources\\cal3d\\archer\\Archer.cfg");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        firstTG.addChild(new Transform().addRotationX(-90).addScale(10).addRotationZ(-90).add(model));
        firstTG.addChild(createFloor());

        final PrecomputedAnimatedModel animated = model;
        new Thread("animator"){
            private long time = 0;
            int i = 0;

            @Override
            public void run() {
                while(!rendering){
                    try {
                        Thread.sleep(TIME);
                    } catch (InterruptedException e) {
                        //ignore
                    }
                }
                Animation animation = animated.getAnimations().get("walk");
                animated.play(animation, true);
                //animated.startAnimation(0);
                while(rendering){

                    animated.executeOperation(time, TIME);


                    if (time > 0 && time % 10000 == 0){
                        i = (i+1) % 3;
                        animated.play(animated.getAnimation(i), true);
                    }

                    try {
                        Thread.sleep(TIME);
                    } catch (InterruptedException e) {
                        //ignore
                    }
                    time += TIME;
                }
            }
        }.start();


    }

    private void loadModel(TransformGroup firstTG) {
        TransformGroup spaceTG = new TransformGroup();

        TransformGroup bottomGroup;
        TransformGroup translateGroup = new TransformGroup();
        bottomGroup = translateGroup;

        TransformGroup temp = new TransformGroup();
        temp.addChild(translateGroup);
        translateGroup = temp;

        translateGroup.setTransform(new Transform().addScale(15).addRotationY(180).getTransform());


        Model model = null;
        try {
            //model = ResourceManager.getInstance().getModel("resources\\3ds\\drazinsunhawk\\Drazisunhawk1.1.3ds", ResourceManager.ModelFormat.TDS);
           // model = ResourceManager.getInstance().getModel("resources\\collada\\shuttle\\shuttle.dae");
            //model = ResourceManager.getInstance().getModel("resources\\3ds\\jeep\\jeep1.3ds");
            //model = ResourceManager.getInstance().getModel("resources\\3ds\\3dm-Henry\\3dm-Henry.3ds");
            model = ResourceManager.getInstance().getModel("resources\\jeep2.ase");
            model.setShowBounds(false, false);

            assert model != null : "Scene should not be null!";

            bottomGroup.addChild(spaceTG);
            spaceTG.addChild(model);
            firstTG.addChild(translateGroup);

            //set reference to the spaceship transform group so it can be translated
            tgReference = spaceTG;

        } catch (Exception e) {
            throw new RuntimeException("Cancelling scene loading", e);
        }

        //final String objectName = "DraziSunHa";
        //final String objectName = "spaceship";

        final java.util.List<TransformGroup> wheels = new ArrayList<TransformGroup>();
        wheels.add((TransformGroup)model.getNamedObject("frw"));
        wheels.add((TransformGroup)model.getNamedObject("flw"));
        wheels.add((TransformGroup)model.getNamedObject("rrw"));
        wheels.add((TransformGroup)model.getNamedObject("rlw"));

        final TransformGroup steeringWheel = (TransformGroup)model.getNamedObject("lsteer");

        final TransformGroup mutableGroup = bottomGroup;


        Thread mover2 = new Thread() {


            short sign = 1;
            float angle = 0;
            float fastAngle = 0;

            @Override
            public void run() {

                while (!rendering) {
                    try {
                        Thread.sleep(TIME);
                    } catch (InterruptedException e) {
                        //ignore
                    }
                }
                while (rendering) {

                    steeringWheel.setTransform(new Transform().addRotationX(angle).getTransform());
                    for (TransformGroup wheel : wheels) {
                        wheel.setTransform(new Transform().addRotationY(0).addRotationZ(-fastAngle).getTransform());
                        
                    }

                   // rotateView(angle);
                    angle = angle + 0.5f * sign;
                    if (angle >= 50 || angle <= -50){
                        sign *= -1;
                    }

                    fastAngle = (fastAngle + 10f) % 360;

                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException e) {
                        //ignore
                    }
                }
            }
        };
        mover2.start();

        //todo (pablius) use the new movementcontrollers




        /*final MovementController mC = VelocityMovementController.create(tgReference, objectName);
        ((VelocityMovementController)mC).setDirection(new Vector3f(-50, 25, 50));
        ((VelocityMovementController)mC).setSpeed(50);
        final MovementController mC2 = RotationMovementController.create(tgReference, objectName);
        ((RotationMovementController)mC2).setRotationAxis(new Vector3f(0,1,0));
        ((RotationMovementController)mC2).setRotationSpeed(25);

        Thread mover = new Thread(){

            public void run() {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    //ignore
                }
                while(!rendering){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        //ignore
                    }
                }

                while(rendering){
                    mC.update();
                    mC2.update();
                }
            }
        };
        mover.start();*/
    }


    private void addListeners() {
//            Canvas3D canvas = (Canvas3DWrapper)environment.getCanvas();
//            canvas.addResizeListener();

        //canvas.addMouseWheelListener(new MyMouseWheelListener());

        //canvas.addMouseMotionListener(new MyMouseMotionListener());
    }

    private void startRenderLoop() {
        RenderLoop renderLoop = new RenderLoop() {
            protected void onFPSCountIntervalHit(double fps) {
                //System.out.println("fps = " + fps);
            }
        };
        //renderLoop.setMaxFPS(MAX_FPS);
        renderLoop.addRenderEngine(environment);
//            window.setVisible(true);
        renderLoop.begin();

        while (!rendering) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                System.out.println("Interrupted thread '" + Thread.currentThread().getName() + "' ---------- \n" + e);
            }
            if (renderLoop.isRendering()) {
                rendering = true;
            }
        }

        long count = TIMEOUT;
        while (count > 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Interrupted thread '" + Thread.currentThread().getName() + "' ---------- \n" + e);
            }
            count -= 1;
        }
        renderLoop.end();
        rendering = false;
        //System.exit(0);
    }

    private TransformGroup addSphere(BranchGroup bg) {
        Material material = new Material(blue, black, blue, specular, 1f);
         material.setLightingEnabled(true);

        Appearance appearance = new Appearance();
        appearance.setMaterial(material);

        //GeoSphere sph = new GeoSphere(8, GeometryArray.NORMALS, 15f);
        Shape3D sph = new Sphere("resources\\textures\\deathstart.jpg", true, 32, 32, 15);
        sph.setAppearance(appearance);
        String sphereName = "mysphere";
        sph.setName(sphereName);
        TransformGroup transformGroup = new TransformGroup();
        //transformGroup.addChild(sph);
        Transform3D t3d = new Transform3D();
        t3d.setTranslation(TIME, 0, 0);
        transformGroup.setTransform(t3d);


        /*
        Shape3D sph = new Sphere( "deathstar.jpg", true, 32, 32, radius );
            sph.setName( "the rotating sphere" );

            Material mat = new Material();
            mat.setEmissiveColor( new Color3f( 0.1f, 0.1f, 0.1f ) );
            mat.setColorTarget( Material.NONE );
            mat.setLightingEnabled( true );
            sph.getAppearance().setMaterial( mat );

            light = new DirectionalLight( true, new Color3f( Color.YELLOW ), new Vector3f( -1f, -1f, -1f ) );

            switchColor();

            this.addChild( sph );
            this.addChild( light );
         */

        return transformGroup;
    }

    private void addAxes(Group bg){
        final Vector3f origin = new Vector3f(0, 0, 0);
        Line yAxis = new Line(origin, new Vector3f(0, 10000, 0), 5, new Color3f(1, 0 ,0), Line.LinePattern.DASHED);
        bg.addChild(yAxis);
        Line xAxis = new Line(origin, new Vector3f(10000, 0, 0), 5, new Color3f(0, 1 ,0), Line.LinePattern.DASHED);
        bg.addChild(xAxis);
        Line zAxis = new Line(origin, new Vector3f(0, 0, 10000), 5, new Color3f(0, 0 ,1), Line.LinePattern.DASHED);
        bg.addChild(zAxis);
    }

    private void setDirectionalLight(BranchGroup bg) {
        Vector3f lightDirection = new Vector3f(1, 0, 0);
        Light lightSource = new DirectionalLight(true, white, lightDirection);//new PointLight(white,lightLocation, lightDirection);
        //bg.addChild(lightSource);

        lightDirection = new Vector3f(0, -1, 0);
        lightSource = new DirectionalLight(true, white, lightDirection);
        bg.addChild(lightSource);

    }

    private void setAmbientLight(BranchGroup bg) {
        Light light = new AmbientLight(true);
        light.setColor(white);
        bg.addChild(light);
    }

    private void defineBackground(BranchGroup bg) {
        Background background = new Background();
       // background.setColor(new Color3f(0.17f, 0.65f, 0.92f));//supposed to be sky color
         background.setColor(new Color3f(1,1,1));
        bg.addChild(background);
    }

    private void defineView() {
        Vector3f viewLocation = new Vector3f(-180, 100,-180);
        Vector3f viewFocus = new Vector3f(1000,0,1000);
        //viewDirection.add(new Vector3f(1,0,1));
        VECTOR_UP_ORIENTATION.normalize();
        environment.getView().lookAt(
                viewLocation,
                viewFocus,
                VECTOR_UP_ORIENTATION);
        environment.getView().setBackClipDistance(100000f);
        

    }



    private void rotateView(float angle) {
        float radAngle = (float)Math.toRadians(angle % 360);
        View view = environment.getView();
        Transform3D t = new Transform3D();
        t.rotY(radAngle);
        view.getTransform().add(t);

    }

    private void changeView(Tuple3f viewLocation, Tuple3f viewDirection) {
        View view = environment.getView();

        viewLocation = viewLocation != null ? viewLocation : view.getPosition();

        Vector3f facingDirection = view.getFacingDirection();
        facingDirection.add(viewLocation);
        viewDirection = viewDirection != null ? viewDirection : facingDirection;
        Frustum frustum = null;


        view.lookAt(
                viewLocation,
                viewDirection,
                VECTOR_UP_ORIENTATION);
    }

    private void moveView(int dx, int dy) {
        View view = environment.getView();
//        Vector3f facingDirection = view.getFacingDirection();
//        facingDirection.x += dx;
//        facingDirection.y += dy;
        int width = environment.getCanvas().getWidth();//todo (pablius) optimize
        int height = environment.getCanvas().getHeight();
        float deltaX = (float) dx / (width / 2);
        float deltaY = (float) dy / (height / 2);
        //System.out.println("\nMOVING: deltaY = "+deltaY + ", deltaX = "+deltaX);
        view.setCenterOfView(new Point2f(deltaX, deltaY));
    }

    private void zoomView(int units) {
        View view = environment.getView();
        Tuple3f viewLocation = view.getPosition();
        final Tuple3f viewDirection = view.getFacingDirection();

        //System.out.println("Before moving view: \nviewLocation = " + viewLocation);

        int variation = -5 * units;
        final Vector3f normalizedViewDirection = new Vector3f(viewDirection);
        normalizedViewDirection.normalize();
        normalizedViewDirection.scale(variation);


        viewLocation.add(normalizedViewDirection);

        //System.out.println("After moving view: \nviewLocation = " + viewLocation);
        view.setPosition(viewLocation);
    }


    private Xith3DEnvironment createEnvironment() {

        Xith3DEnvironment environment = new Xith3DEnvironment();

        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        java.awt.DisplayMode displayMode = device.getDisplayMode();

        DisplayMode mode = DisplayModeSelector.getImplementation( OpenGLLayer.JOGL_AWT).getBestMode( displayMode.getWidth(), displayMode.getHeight() );
        //Canvas3D canvas = Canvas3DFactory.create(OpenGLLayer.LWJGL, new DisplayMode(OpenGLLayer.LWJGL, displayMode.getWidth(), displayMode.getHeight(), 16, DisplayMode.getDefaultFrequency()), false, "Canvas");
        Canvas3D canvas = Canvas3DFactory.createWindowed( OpenGLLayer.JOGL_AWT, mode, "RenderCanvas");
        //Canvas3D canvas = Canvas3DWrapper.createStandalone(CanvasPeer.OpenGLLayer.JOGL_AWT, Canvas3DWrapper.Resolution.RES_1024X768, Canvas3DWrapper.ColorDepth.B16, "Canvas");

        environment.addCanvas(canvas);
        canvas.setRenderOption(Option.ENABLE_WIREFRAME_MODE, true);

//            window = (Window)canvas.get3DPeer().getWindow();

//            JFrame frame = new JFrame();
//            window = frame;
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setUndecorated(true);
//            frame.setResizable(false);

//            GraphicsDevice gDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
//            if (!gDevice.isFullScreenSupported()){
//                throw new RuntimeException("Fullscreen is not supported!");
//            }
//
//            DisplayMode dMode = gDevice.getDisplayMode();
//            dMode = new DisplayMode(dMode.getWidth(), dMode.getHeight(), 16, DisplayMode.REFRESH_RATE_UNKNOWN);
        //gDevice.setDisplayMode(dMode);

//            gDevice.setFullScreenWindow(window);

//            Canvas3DWrapper canvasWrapper = new Canvas3DJPanel();

//            Canvas3DWrapper canvas = Canvas3DWrapper.createFullscreen(CanvasPeer.OpenGLLayer.getDefault(),
//                    Canvas3DWrapper.Resolution.DESKTOP,
//                    Canvas3DWrapper.ColorDepth.B32);
//            canvas = new Canvas3DWrapper()

//        createStandalone(CanvasPeer.OpenGLLayer.JOGL,
//                Canvas3DWrapper.Resolution.DESKTOP,
//                Canvas3DWrapper.ColorDepth.B32,
//                "BasicScene");

         
//        canvas.setRenderOption(Option.USE_TEXTURES, true);
//        canvas.setRenderOption(Option.USE_LIGHTING, true);

        // canvas.setRenderOption(Option.USE_VERTEX_BUFFER_CACHING, true);
        //set window

        //set invisible cursor
        Cursor invisibleCursor =
                Toolkit.getDefaultToolkit().createCustomCursor(
                        Toolkit.getDefaultToolkit().getImage(""),
                        new Point(0, 0),
                        "invisible");
        // window.setCursor(invisibleCursor);//todo uncomment


        return environment;
    }

    public static void main(String[] args) {
        try {
            new ScenarioLoadingDemo().showBasicScene();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }


    private class MyMouseWheelListener implements java.awt.event.MouseWheelListener {
        private int despCount = 0;

        public void mouseWheelMoved(MouseWheelEvent e) {
            wheelRotation = e.getWheelRotation();
            //System.out.println("\n----------------\nwheelRotation = " + wheelRotation);
            doZoom(wheelRotation);

//            if (tgReference != null){
//                despCount += wheelRotation * -10;
//                Transform3D t3d = new Transform3D();
//                t3d.setTranslation(despCount, despCount /2 , despCount * 3.2f);
//                tgReference.setTransform(t3d);
//            }

        }

        private void doZoom(int wheelRotation) {
            if (wheelRotation != 0) {
                //limit translation, it may move too fast - should be limited in the size of the translation vector
                wheelRotation = Math.abs(wheelRotation) < 5 ? wheelRotation : ((int) Math.signum(wheelRotation)) * 5;
                zoomView(wheelRotation);
            }
        }

    }

    private class MyMouseMotionListener implements java.awt.event.MouseMotionListener {
        private Point lastLocation;
        private Point centerLocation;
        private Robot robot;


        public MyMouseMotionListener() {
            lastLocation = new Point();
            centerLocation = new Point();
            try {
                robot = new Robot();
            } catch (AWTException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        public void mouseDragged(MouseEvent e) {
            //do nothing
        }

        private Point getCenterLocation() {
            Point result = new Point();
            result.x = window.getWidth() / 2;
            result.y = window.getHeight() / 2;
            return result;
        }

        //TODO (pablius) implement!
        public synchronized void mouseMoved(MouseEvent e) {
            centerLocation = getCenterLocation();//TODO (pablius) remove from here if unnecessary
            // this event is from re-centering the mouse - ignore it
            if (isRecenteringMouse ||
                    (centerLocation.x == e.getX() && centerLocation.y == e.getY())) {
                //System.out.println("Recentering event processed.");
                isRecenteringMouse = false;
            } else {
                int dx = e.getX() - lastLocation.x;
                int dy = e.getY() - lastLocation.y;
                //System.out.println("Mouse moved: dy = " + dy + ", dx = " + dx);

                //moveView(dx, dy);   //todo uncomment
                recenterMouse();  //todo uncomment
            }

            lastLocation.x = e.getX();
            lastLocation.y = e.getY();

        }

        private void recenterMouse() {
            if (robot != null && window.isShowing()) {
                centerLocation = getCenterLocation();//TODO (pablius) remove from here if unnecessary
                SwingUtilities.convertPointToScreen(centerLocation, window);
                isRecenteringMouse = true;
                robot.mouseMove(centerLocation.x, centerLocation.y);
            }
        }
    }


}
