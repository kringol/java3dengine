package org.test.xith3d;


import org.test.xith3d.loaders.ModelLoaderFactory;
import org.test.xith3d.loaders.ModelLoader;
import org.xith3d.scenegraph.*;
import org.xith3d.render.*;
import org.xith3d.render.lwjgl.RenderPeerImpl;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Pablo
 * Date: Jan 26, 2007
 * Time: 11:15:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class AnyLoaderTest implements Runnable {

    protected static final String OBJ_MODEL_FILENAME = "resources/models/obj/battletank_obj/battletank.obj";
    protected static final String DAE_MODEL_FILENAME = "duck.dae";
    protected static final String TDS_MODEL_FILENAME = "resources/models/3ds/basicScene.3ds";


    View view;
    double lightAngle;
    double modelAngle;
    float anim = 0.0f;

    DirectionalLight light;
    TransformGroup tg = new TransformGroup();


    public AnyLoaderTest() throws Exception {


        Frame frame = new Frame();

        VirtualUniverse universe = new VirtualUniverse();
        view = new View();
        view.setProjectionPolicy(View.PERSPECTIVE_PROJECTION);
        universe.addView(view);
        Locale locale = new Locale();
        universe.addLocale(locale);

        view.setBackClipDistance(500.0f);

        // setup view
        view.getTransform().lookAt(
                new Vector3f(0, 2, 2),  // location of eye (cubes)
                new Vector3f(0, 0, 0),     // center of view (cubes)
                new Vector3f(0, 1, 0));    // vector pointing up

        RenderPeer rp = new RenderPeerImpl();
        CanvasPeer cp = rp.makeCanvas(frame, 800, 600, 32, false);
        frame.setSize(800, 600);
        Canvas3D canvas = new Canvas3D();
        canvas.set3DPeer(cp);
        view.addCanvas3D(canvas);

        RenderOptions options = new RenderOptions();

        options.setOption(Option.USE_SHADOWS, true);
        options.setOption(Option.USE_LIGHTING, true);

        cp.setRenderOptions(options);

        BranchGroup root = new BranchGroup();
        locale.addBranchGraph(root);

        Color3f ambientColor = new Color3f(0.5f, 0.5f, 0.5f);
        AmbientLight ambientLightNode = new AmbientLight(ambientColor);
        root.addChild(ambientLightNode);

        light = new DirectionalLight(true, new Color3f(1f, 1f, 1f), new Vector3f(0, 0, 1));
        root.addChild(light);

        //TDSLoader.setDebug(true);


        Group scene = null;
        ModelLoader loader = ModelLoaderFactory.createModelLoader(ModelLoaderFactory.ModelLoaderType.NEWDAWN_TDS_LOADER);
        long start = System.currentTimeMillis();
        scene = loader.loadScene(TDS_MODEL_FILENAME);

        
        System.out.println("Load took: " + ((System.currentTimeMillis() - start) / 1000.0) + " seconds");

        //scene= new DaeLoader().load(modelResource);
        //scene = DaeLoader.loadViaCollada(modelResource);

//        URL resource = TDSLoader.class.getClassLoader().getResource(MODEL_FILENAME);
//        System.out.println("resource = " + resource);
//        model = new TDSLoader().load(MODEL_FILENAME, false);
//

        tg.addChild(scene);
//        Geometry geo = Cube.createCubeViaTriangles(0, 0, 0, 1, true);
//		Shape3D sh = new Shape3D(geo, new Appearance());
        //tg.addChild(sh);
        root.addChild(tg);


        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setVisible(true);

        new Thread(this).start();


    }


    public void run() {
        boolean done = false;

        int count = 0;
        while (true) {

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            view.renderOnce();
            lightAngle -= 0.01;
            Vector3f dir = new Vector3f((float) -Math.cos(lightAngle), 0, (float) Math.sin(lightAngle));
            light.setDirection(dir);

//            modelAngle += 0.01;
//            Transform3D yrot = new Transform3D();
//            yrot.rotY((float) modelAngle);
//            Transform3D xrot = new Transform3D();
//            xrot.rotX((float) modelAngle);
//            xrot.rotX((float) -Math.PI/2);
//            yrot.mul(xrot);
//            tg.setTransform(yrot);
//
//            anim += 0.005f;
//            if (anim > 1) {
//                done = true;
//                anim = 0;
//            }
//            model.setTime(anim);


            //int xDir = (count) % 24 - 12;
            //int xDir = count;

            view.getTransform().lookAt(
                new Vector3f(10, 5, 100),  // location of eye (cubes)
                new Vector3f(0, 0, 0),     // center of view (cubes)
                new Vector3f(0, 1, 0));    // vector pointing up
            count++;
        }

    }

    public static void main(String[] args) throws Exception {
        new AnyLoaderTest();

    }
}
