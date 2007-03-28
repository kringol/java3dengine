package edu.ua.j3dengine.processors.execution;

import edu.ua.j3dengine.core.geometry.impl.XithGeometry;
import edu.ua.j3dengine.core.mgmt.GameObjectManager;
import edu.ua.j3dengine.core.mgmt.ResourceManager;
import edu.ua.j3dengine.utils.Constants;
import org.xith3d.render.Canvas3D;
import org.xith3d.render.Canvas3DFactory;
import org.xith3d.render.Option;
import org.xith3d.render.RenderOptions;
import org.xith3d.render.base.Xith3DEnvironment;
import org.xith3d.render.config.DisplayMode;
import org.xith3d.render.config.DisplayModeSelector;
import org.xith3d.render.config.OpenGLLayer;
import org.xith3d.scenegraph.*;
import org.xith3d.loaders.texture.TextureLoader;
import org.xith3d.loaders.models.base.Model;
import org.xith3d.loaders.models.util.precomputed.PrecomputedAnimatedModel;
import org.xith3d.geometry.*;

import javax.vecmath.Vector3f;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Color4f;
import java.awt.*;
import java.awt.Rectangle;


public class GameEnvironment {

    private static GameEnvironment instance;

    private Xith3DEnvironment environment;
    private Canvas3D canvas;

    private static final Vector3f VEC_UP = new Vector3f(0, 1, 0);
    private static final float DEFAULT_BACK_CLIP_DISTANCE = 50000f;
    private static final float DEFAULT_FIELD_OF_VIEW = 0.5f;


    public static synchronized GameEnvironment getInstance() {
        if (instance == null) {
            instance = new GameEnvironment();
        }

        return instance;
    }

    private GameEnvironment() {
        initialize();
    }

    private void initialize() {
        environment = new Xith3DEnvironment();

        //todo (pablius) change this to full screen if possible
        DisplayMode mode = DisplayModeSelector.getImplementation(OpenGLLayer.JOGL_AWT).getBestMode(1024, 768);

        canvas = Canvas3DFactory.createWindowed(OpenGLLayer.JOGL_AWT, mode, "j3dEngine");

        environment.addCanvas(canvas);

        //load main geometry into scenegraph
        Node node = ((XithGeometry) GameObjectManager.getInstance().getWorld().getGeometry()).getSceneGraphNode();
        BranchGroup branchGroup = null;
        if (Boolean.getBoolean(Constants.TEST_MODE_PROPERTY)){
            branchGroup = createTestScenario();
            branchGroup.addChild(node);
        }else{
            branchGroup = new BranchGroup(node);
        }
        environment.addBranchGraph(branchGroup);
        environment.getView().setBackClipDistance(DEFAULT_BACK_CLIP_DISTANCE);
        environment.getView().lookAt(new Vector3f(0, 0, 0), new Vector3f(100, 0, 100), VEC_UP);
        environment.getView().setFieldOfView(DEFAULT_FIELD_OF_VIEW);
        environment.checkRenderPreferences();
        environment.getUniverse().forceRefill(true);environment.getUniverse().renderOnce();

        //set default camera to world
        GameObjectManager.getInstance().setDefaultCamera(environment.getView());
    }


    public Xith3DEnvironment getEnvironment() {
        return environment;
    }


    public Canvas3D getCanvas() {
        return canvas;
    }

    public Window getWindow() {
        return (Window) canvas.get3DPeer().getWindow();
    }

    public Object getComponent() {
        return canvas.get3DPeer().getComponent();
    }

    public BranchGroup createTestScenario(){
        BranchGroup root = new BranchGroup();


        loadSkyBox(root);
//        //Model model = ResourceManager.getInstance().getModel("resources\\3ds\\jeep\\jeep1.3ds");
//        PrecomputedAnimatedModel model = ResourceManager.getInstance().getPrecomputedCal3DModel("resources\\cal3d\\archer\\Archer.cfg");
//        TransformGroup tg0 = new TransformGroup();
//        TransformGroup tg = new TransformGroup(new Transform().addRotationX(-90).addScale(10).addRotationZ(-90).getTransform());
//        tg0.addChild(tg);
//        root.addChild(tg0);
//        //tg.addChild(model);
//        TransformGroup tg2 = new TransformGroup();tg.addChild(tg2);tg=tg2;
//        tg2 = new TransformGroup();tg.addChild(tg2);tg2.addChild(model);
//

       // addDirectionalLight(root);

        //addSpotLight(root);
        addAmbientLight(root);
        //addBackground(root);
        addFloor(root);
        return root;
    }

    private void addFloor(Group root) {
        org.xith3d.geometry.Rectangle floor = new org.xith3d.geometry.Rectangle("resources\\textures\\grass.jpg", false, 20000f, 20000f);
        //org.xith3d.geometry.Rectangle floor = new org.xith3d.geometry.Rectangle(new Color4f(0,1,0,0),20000f, 20000f);
        Color3f black = new Color3f(0, 0, 0);
        //Material material = new Material(black,black, new Color3f(0,1,0), new Color3f(1,1,1), 20.0f);
        Material material = new Material(false);

        //material.setDiffuseColor(1,1,1);
        //material.setSpecularColor(0,0,1);
        floor.getAppearance().setMaterial(material);
        TransformGroup tg = new Transform(floor).setTranslationY(0).addRotationX(-90f);
        root.addChild(tg);
    }

    private void addBackground(Group root) {
        Background background = new Background();
        background.setColor(new Color3f(0.17f, 0.65f, 0.92f));//supposed to be sky color
        root.addChild(background);
    }

    private void addAmbientLight(Group root) {
        Light light = new AmbientLight(true);
        light.setColor(new Color3f(1,1,1));
        root.addChild(light);
    }

    private void addDirectionalLight(Group root) {
        Light light = new DirectionalLight(new Color3f(1,1,1), new Vector3f(-1, -5, -1));
        
        root.addChild(light);
    }

    private void addSpotLight(Group root) {
        //new Vector3f(-100, 50, -100), new Vector3f(1000, 0, 1000)

        //Light light = new SpotLight(true, new Color3f(1,1,1), new Vector3f(-50, 50, -50), new Vector3f(1,0,0), new Vector3f(0,0,0),(float) Math.toRadians(20), 0);
        Light light = new PointLight(true, new Color3f(1,1,1), new Point3f(0,50,0), new Point3f(-1,0,0));
        //Light light = new SpotLight(true);
        //light.setColor(new Color3f(1,1,1));
        root.addChild(light);
    }


    private void loadSkyBox(Group root) {
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

}
