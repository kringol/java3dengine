package edu.ua.j3dengine.core;

import static edu.ua.j3dengine.utils.ValidationUtils.*;
import static edu.ua.j3dengine.utils.Utils.*;

import edu.ua.j3dengine.core.geometry.SpatialObject;
import edu.ua.j3dengine.core.geometry.impl.GeometryXithImpl;
import edu.ua.j3dengine.core.geometry.impl.XithGeometry;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

import org.xith3d.scenegraph.BranchGroup;
import org.xith3d.scenegraph.Group;
import org.xith3d.scenegraph.Node;
import org.xith3d.scenegraph.TransformGroup;

@XmlRootElement
public class World extends DynamicGameObject {

    private static final String DEFAULT_WORLD_NAME = "World";

    private long initialSystemTime;
    private long elapsedTime;
    private long gameTime;


    @XmlElementWrapper
    private Map<String, GameObject> worldObjects;

    private Map<String, Camera> cameras;//todo (pablius) serializar las camaras!

    public static final String DEFAULT_CAMERA = "DEFAULT_CAMERA";
    /**
     * Created for fast access to dynamic objects.
     */
    private Map<String, DynamicGameObject> dynamicObjects;

    private World(String name) {
        super(name);
        this.initialSystemTime = System.currentTimeMillis();
        this.gameTime = 0L;
        this.elapsedTime = 0L;
        this.worldObjects = new HashMap<String, GameObject>();
        this.dynamicObjects = new HashMap<String, DynamicGameObject>();
        this.cameras = new HashMap<String, Camera>();

    }

    private World(String name, Collection<GameObject> worldObjects){
        this(name);
        for (GameObject worldObject : worldObjects) {
            addGameObject(worldObject);
        }

    }

    private World(){
        this(DEFAULT_WORLD_NAME);
    }

    public static World create(String name){
        return new World(name);
    }

    
    public Set<String> getAllGameObjectNames(){
        return Collections.unmodifiableSet(worldObjects.keySet());
    }

    public Collection<GameObject> getAllGameObjects(){
        return Collections.unmodifiableCollection(worldObjects.values());
    }

    public Collection<DynamicGameObject> getAllDynamicObjects(){
        return Collections.unmodifiableCollection(dynamicObjects.values());
    }

    public GameObject getGameObject(String name){
        return worldObjects.get(name);
    }

    public void addGameObject(GameObject gameObject){
        validateNotNull(gameObject);
        worldObjects.put(gameObject.getName(), gameObject);
        attachGeometryIfNecessary(gameObject);
        
        if (gameObject instanceof DynamicGameObject){
            storeDynamicObject((DynamicGameObject)gameObject);
        }
        if (gameObject instanceof Camera){
            storeCamera((Camera)gameObject);
        }
    }



    private void storeDynamicObject(DynamicGameObject dynamicObject){
        dynamicObjects.put(dynamicObject.getName(), dynamicObject);

        assert dynamicObject.getMovementController() != null;
        if (!dynamicObject.getMovementController().isInitialized()){
            dynamicObject.initializeMovementController();
        }
        logDebug(dynamicObject + " has been stored as dynamic object.");
    }

    private void storeCamera(Camera camera){
        this.cameras.put(camera.getName(), camera);
        logDebug(camera+ " has been stored as camera.");
    }


    private void attachGeometryIfNecessary(GameObject gameObject) {

        if (gameObject instanceof SpatialObject){
            if (((SpatialObject) gameObject).getGeometry() instanceof XithGeometry){
                XithGeometry geometryXith = (XithGeometry) ((SpatialObject) gameObject).getGeometry();
                if (geometryXith.isSeparatedModel()){
                    Node node = geometryXith.getSceneGraphNode();
                    if (node.getParent() != null){
                        node.detach();
                    }
                    if (geometryXith.getPreTransform() != null){
                        TransformGroup tg = new TransformGroup(geometryXith.getPreTransform());
                        tg.addChild(geometryXith.getSceneGraphNode());
                        node = tg;
                    }

                    ((Group)((XithGeometry)this.getGeometry()).getSceneGraphNode()).addChild(node);
                }
            }
        }
    }

    //initialization call
    public void initialize(){
        if (this.getGeometry() == null){
            XithGeometry geom = new GeometryXithImpl("DefaultWorldGeometry", new BranchGroup());
            setGeometry(geom);
        }

        for (String key : this.worldObjects.keySet()) {
            GameObject object = this.worldObjects.get(key);
            if (object instanceof DynamicGameObject){
                storeDynamicObject((DynamicGameObject)object);
            }
            if (object instanceof Camera){
                storeCamera((Camera)object);
            }
        }
    }

    public void setDefaultCamera(Camera defaultCamera){
        cameras.put(DEFAULT_CAMERA, defaultCamera);
        addCamera(defaultCamera);
    }

    public Camera getDefaultCamera(){
        return cameras.get(DEFAULT_CAMERA);
    }

    public void addCamera(Camera camera){
        addGameObject(camera);
    }


    @Override
    public void initializeMovementController() {
        throw new UnsupportedOperationException("World does not support MovementControllers.");
    }

    @Override
    public void update() {
        long oldGameTime = gameTime;
        gameTime = System.currentTimeMillis() - initialSystemTime;
        elapsedTime = gameTime - oldGameTime;
    }


    public long getElapsedTime() {
        return elapsedTime;
    }

    public long getGameTime() {
        return gameTime;
    }

    @Override
    public String toString() {
        return "[World]: '"+getName()+"'";
    }
}
