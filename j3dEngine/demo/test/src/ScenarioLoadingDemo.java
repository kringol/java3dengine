import org.xith3d.render.base.Xith3DEnvironment;
import org.xith3d.render.canvas.Canvas3DWrapper;
import org.xith3d.render.loop.RenderLoop;
import org.xith3d.render.CanvasPeer;
import org.xith3d.render.Option;
import org.xith3d.scenegraph.*;
import org.xith3d.loaders.models.base.Model;
import org.xith3d.geometry.GeoSphere;
import org.xith3d.spatial.bounds.Frustum;

import javax.swing.*;
import javax.vecmath.Color3f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;
import javax.vecmath.Point2f;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseEvent;

import edu.ua.j3dengine.core.mgmt.ResourceManager;

public class ScenarioLoadingDemo {

    public String visibleText;

        private static final int MAX_FPS = 100;

        private Xith3DEnvironment environment;
        private Window window;
        private TransformGroup tgReference;

        private volatile boolean rendering = true;

        private Color3f white = new Color3f(1, 1, 1);
        private Color3f black = new Color3f(0, 0, 0);
        private Color3f blue = new Color3f(0, 0, 1);
        private Color3f red = new Color3f(1, 0, 0);
        private Color3f green = new Color3f(0,1,0);


        private Color3f specular = new Color3f(0.9f, 0.9f, 0.9f);
        private volatile int wheelRotation;
        private volatile Tuple3f mouseMovement;
        private volatile boolean isRecenteringMouse;

        private static final Vector3f VECTOR_UP_ORIENTATION = new Vector3f(0, 1, 0);

        private static final float MOUSE_SENSITIVITY = 5;

        public void showBasicScene() {

            environment = createEnvironment();

            BranchGroup bg = new BranchGroup();
            environment.addBranchGraph(bg);

            addListeners();

            defineView();

            defineBackground(bg);

            setAmbientLight(bg);

            setDirectionalLight(bg);

            TransformGroup firstTG = addSphere(bg);

            bg.addChild(firstTG);


            loadModel(firstTG);



            startRenderLoop();


        }

        private void loadModel(TransformGroup firstTG) {
            TransformGroup spaceTG = new TransformGroup();
            TransformGroup translateGroup = new TransformGroup();
            Transform3D t3d = new Transform3D();
            t3d.setTranslation(1000, 0, 0);
           // t3d.setScale(3);
//            Transform3D t3d2 = new Transform3D();
//            t3d2.rotAxis(new Vector3f(1,0,0), -90);
//            t3d.add(t3d2);
//            Transform3D t3d3 = new Transform3D();
//            t3d3.scaleTranslation(2);
//            t3d.add(t3d3);
            translateGroup.setTransform(t3d);

            Model model = null;
            try {
                //model = ResourceManager.getInstance().getModel("resources\\3ds\\drazinsunhawk\\Drazisunhawk1.1.3ds", ResourceManager.ModelFormat.TDS);
                model = ResourceManager.getInstance().getModel("resources\\collada\\shuttle\\shuttle.dae");
                
                assert model != null : "Scene should not be null!";

                translateGroup.addChild(spaceTG);
                spaceTG.addChild(model);
                firstTG.addChild(translateGroup);

                //set reference to the spaceship transform group so it can be translated
                tgReference = spaceTG;

            } catch (Exception e) {
                throw new RuntimeException("Cancelling scene loading", e);
            }

            //final String objectName = "DraziSunHa";
            final String objectName = "spaceship";
            SceneGraphObject object = model.getNamedObject(objectName);


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
            Canvas3DWrapper canvas = (Canvas3DWrapper)environment.getCanvas();

            //canvas.addMouseWheelListener(new MyMouseWheelListener());

            //canvas.addMouseMotionListener(new MyMouseMotionListener());
        }

        private void startRenderLoop() {
            RenderLoop renderLoop = new RenderLoop() {
                protected void onFPSCountIntervalHit(double fps) {
                    //System.out.println("fps = " + fps);
                }
            };
            renderLoop.setMaxFPS(MAX_FPS);
            renderLoop.addRenderEngine(environment);


            renderLoop.begin();

            while(!rendering){
                try {
                    Thread.currentThread().wait(300);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted thread '"+Thread.currentThread().getName()+"' ---------- \n" + e);
                }
                if (renderLoop.isRendering()){
                    rendering = true;
                }
            }
        }

        private TransformGroup addSphere(BranchGroup bg) {
            Material material = new Material(blue, black, blue, specular, 1f);
            material.setLightingEnabled(true);

            Appearance appearance = new Appearance();
            appearance.setMaterial(material);

            GeoSphere sph = new GeoSphere(8, GeometryArray.NORMALS, 15f);
            sph.setAppearance(appearance);
            String sphereName = "mysphere";
            sph.setName(sphereName);
            TransformGroup transformGroup = new TransformGroup();
            transformGroup.addChild(sph);
            Transform3D t3d = new Transform3D();
            t3d.setTranslation(100, 0, 0);
            transformGroup.setTransform(t3d);


            return transformGroup;
        }

        private void setDirectionalLight(BranchGroup bg) {
            Vector3f lightDirection = new Vector3f(1, 0, 0);
            Light lightSource = new DirectionalLight(true, white, lightDirection);//new PointLight(white,lightLocation, lightDirection);
            //bg.addChild(lightSource);

            lightDirection = new Vector3f(1, -2, 1);
            lightSource = new DirectionalLight(true, green, lightDirection);
            bg.addChild(lightSource);

        }

        private void setAmbientLight(BranchGroup bg) {
            Light light = new AmbientLight(true);
            light.setColor(white);
            bg.addChild(light);
        }

        private void defineBackground(BranchGroup bg) {
            Background background = new Background();
            background.setColor(new Color3f(0.17f, 0.65f, 0.92f));//supposed to be sky color
            bg.addChild(background);
        }

        private void defineView() {
            Vector3f viewLocation = new Vector3f(-100, 0, 0);
            Vector3f viewDirection = new Vector3f(viewLocation);
            viewDirection.add(new Vector3f(1,0,0));
            environment.getView().lookAt(
                    viewLocation,
                    viewDirection,
                    VECTOR_UP_ORIENTATION);
        }

        private void changeView(Tuple3f viewLocation, Tuple3f viewDirection){
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

        private void moveView(int dx, int dy){
            View view = environment.getView();
//        Vector3f facingDirection = view.getFacingDirection();
//        facingDirection.x += dx;
//        facingDirection.y += dy;
            int width = environment.getCanvas().getWidth();//todo (pablius) optimize
            int height = environment.getCanvas().getHeight();
            float deltaX = (float)dx / (width/2);
            float deltaY = (float)dy / (height/2);
            //System.out.println("\nMOVING: deltaY = "+deltaY + ", deltaX = "+deltaX);
            view.setCenterOfView(new Point2f(deltaX, deltaY));
        }

        private void zoomView(int units){
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

            Canvas3DWrapper canvas = Canvas3DWrapper.createFullscreen(CanvasPeer.OpenGLLayer.getDefault(),
                    Canvas3DWrapper.Resolution.DESKTOP,
                    Canvas3DWrapper.ColorDepth.B32);

//        createStandalone(CanvasPeer.OpenGLLayer.JOGL,
//                Canvas3DWrapper.Resolution.DESKTOP,
//                Canvas3DWrapper.ColorDepth.B32,
//                "BasicScene");


            environment.addCanvas(canvas);
            canvas.setRenderOption(Option.USE_TEXTURES, true);
            canvas.setRenderOption(Option.USE_LIGHTING, true);
            canvas.setRenderOption(Option.USE_TEXTURES, true);
           // canvas.setRenderOption(Option.USE_VERTEX_BUFFER_CACHING, true);
            //set window
            window = (Window)canvas.getWindow();


            //set invisible cursor
            Cursor invisibleCursor =
                Toolkit.getDefaultToolkit().createCustomCursor(
                Toolkit.getDefaultToolkit().getImage(""),
                new Point(0,0),
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

            private void doZoom(int wheelRotation){
                if (wheelRotation != 0){
                    //limit translation, it may move too fast - should be limited in the size of the translation vector
                    wheelRotation = Math.abs(wheelRotation)< 5 ? wheelRotation : ((int)Math.signum(wheelRotation)) * 5;
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

            private Point getCenterLocation(){
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
                        (centerLocation.x == e.getX() && centerLocation.y == e.getY()))
                {
                    //System.out.println("Recentering event processed.");
                    isRecenteringMouse = false;
                }
                else {
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
