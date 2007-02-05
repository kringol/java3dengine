package org.test.xith3d;

import org.xith3d.geometry.GeoSphere;
import org.xith3d.render.CanvasPeer;
import org.xith3d.render.Option;
import org.xith3d.render.base.Xith3DEnvironment;
import org.xith3d.render.canvas.Canvas3DWrapper;
import org.xith3d.render.loop.RenderLoop;
import org.xith3d.scenegraph.*;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;
import javax.vecmath.Tuple3f;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Pablo
 * Date: Feb 4, 2007
 * Time: 7:11:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class BasicShapesTest {
    private static final int MAX_FPS = 50;

    private Xith3DEnvironment environment;

    private volatile boolean rendering = true;

    private Color3f white = new Color3f(1, 1, 1);
    private Color3f black = new Color3f(0, 0, 0);
    private Color3f blue = new Color3f(0, 0, 1);
    private Color3f red = new Color3f(1, 0, 0);

    private Color3f specular = new Color3f(0.9f, 0.9f, 0.9f);
    private volatile int wheelRotation;
    private volatile Tuple3f mouseMovement;

    private static final Vector3f VECTOR_UP_ORIENTATION = new Vector3f(0, 1, 0);

    public void showBasicScene() {

        environment = createEnvironment();


        BranchGroup bg = new BranchGroup();
        environment.addBranchGraph(bg);

        Canvas3DWrapper canvas = (Canvas3DWrapper)environment.getCanvas();
        canvas.addMouseWheelListener(new MouseWheelListener(){

            public void mouseWheelMoved(MouseWheelEvent e) {
                wheelRotation = e.getWheelRotation();
                System.out.println("\n----------------\nwheelRotation = " + wheelRotation);
            }
        });

        canvas.addMouseMotionListener(new MouseMotionListener(){
            private Point lastPosition;

            public void mouseDragged(MouseEvent e) {
                //do nothing
            }

            //TODO (pablius) implement!
            public void mouseMoved(MouseEvent e) {
                Point currentPoint = e.getPoint();
                if (lastPosition != null){
                    //Vector3f currentPosition = new Vector3f(currentPoint.get)
                }else{
                    lastPosition = currentPoint;
                }
            }
        });

        defineView();

        defineBackground(bg);

        setAmbientLight(bg);

        setDirectionalLight(bg);

        addSphere(bg);

        startRenderLoop();

        new Thread() {

            public void run() {
                Thread.currentThread().setName("Wheel rotation thread");

                while(!rendering){
                    try{
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted thread '"+Thread.currentThread().getName()+"' ---------- \n" + e);
                    }
                }

                while(rendering){
                    if (wheelRotation != 0){
                        zoomView(wheelRotation);
                        wheelRotation = 0;
                    }
                    try {
                        Thread.sleep(50L);
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted thread '"+Thread.currentThread().getName()+"' ---------- \n" + e);
                    }
                }
            }
        }.start();
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

    private void addSphere(BranchGroup bg) {
        Material material = new Material(blue, black, blue, specular, 1f);
        material.setLightingEnable(true);

        Appearance appearance = new Appearance();
        appearance.setMaterial(material);

        GeoSphere sph = new GeoSphere(8, GeometryArray.NORMALS, 15f);
        sph.setAppearance(appearance);

        TransformGroup transformGroup = new TransformGroup();
        transformGroup.addChild(sph);
        Transform3D t3d = new Transform3D();
        t3d.setTranslation(510, 0, 0);
        transformGroup.setTransform(t3d);

        bg.addChild(transformGroup);
    }

    private void setDirectionalLight(BranchGroup bg) {
        Vector3f lightDirection = new Vector3f(1, 0, 0);
        Light lightSource = new DirectionalLight(true, white, lightDirection);//new PointLight(white,lightLocation, lightDirection);
        //bg.addChild(lightSource);

        lightDirection = new Vector3f(1, 2, 1);
        lightSource = new DirectionalLight(true, red, lightDirection);
        bg.addChild(lightSource);

    }

    private void setAmbientLight(BranchGroup bg) {
        Light light = new AmbientLight(false);
        light.setColor(white);
        bg.addChild(light);
    }

    private void defineBackground(BranchGroup bg) {
        Background background = new Background();
        background.setColor(new Color3f(0.17f, 0.65f, 0.92f));//supposed to be sky color
        bg.addChild(background);
    }

    private void defineView() {
        Vector3f viewLocation = new Vector3f(400, 0, 0);
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

        view.lookAt(
                viewLocation,
                viewDirection,
                VECTOR_UP_ORIENTATION);
    }

    private void zoomView(int units){
        View view = environment.getView();
        Tuple3f viewLocation = view.getPosition();
        final Tuple3f viewDirection = view.getFacingDirection();

        System.out.println("Before moving view: \nviewLocation = " + viewLocation);

        viewDirection.scale(-5 * units);
        viewLocation.add(viewDirection);

        System.out.println("After moving view: \nviewLocation = " + viewLocation);
        view.setPosition(viewLocation);
    }



    private Xith3DEnvironment createEnvironment() {
        Xith3DEnvironment environment = new Xith3DEnvironment();

        Canvas3DWrapper canvas = Canvas3DWrapper.createStandalone(CanvasPeer.OpenGLLayer.JOGL,
                Canvas3DWrapper.Resolution.RES_1024X768,
                Canvas3DWrapper.ColorDepth.B32,
                "BasicScene");


        environment.addCanvas(canvas);
        canvas.setRenderOption(Option.USE_TEXTURES, true);
        return environment;
    }

    public static void main(String[] args) {
        new BasicShapesTest().showBasicScene();
    }


}
