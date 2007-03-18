package edu.ua.j3dengine.core;

import static edu.ua.j3dengine.utils.ValidationUtils.validateNotNull;
import edu.ua.j3dengine.core.geometry.SpatialObject;
import edu.ua.j3dengine.core.geometry.impl.ModelAdapterGeometry;
import edu.ua.j3dengine.core.geometry.impl.GeometryXithImpl;
import edu.ua.j3dengine.core.geometry.impl.XithGeometry;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import java.util.*;

import org.xith3d.scenegraph.BranchGroup;
import org.xith3d.scenegraph.Group;

@XmlRootElement
public class World extends DynamicGameObject {

    private static final String DEFAULT_WORLD_NAME = "World";

    private long elapsedTime;
    private long gameTime;

    @XmlElement
    private ModelAdapterGeometry worldGeometry;

    @XmlElementWrapper
    private Map<String, GameObject> worldObjects;

    /**
     * Created for fast access to dynamic objects.
     */
    private Map<String, DynamicGameObject> dynamicObjects;

    private World(String name) {
        super(name);
        this.gameTime = System.currentTimeMillis();//todo (pablius) ojo con esto-> necesita correr un update()
        this.elapsedTime = 0L;
        this.worldObjects = new HashMap<String, GameObject>();
        this.dynamicObjects = new HashMap<String, DynamicGameObject>();
    }

    private World(String name, Collection<GameObject> worldObjects){
        this(name);
        for (GameObject worldObject : worldObjects) {
            addGameObject(worldObject);
            if (worldObject.isDynamic()){
                storeDynamicObject((DynamicGameObject)worldObject);
            }
        }

    }

    private World(){
        this(DEFAULT_WORLD_NAME);
    }

    public static World create(String name, Collection<GameObject> worldObjects){
        return new World(name, worldObjects);
    }

    public static World create(String name, GameObject... worldObjects){
        List<GameObject> list = new ArrayList<GameObject>();
        for (GameObject worldObject : worldObjects) {
            list.add(worldObject);
        }
        return create(name, list);
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
    }



    private void storeDynamicObject(DynamicGameObject dynamicObject){
        dynamicObjects.put(dynamicObject.getName(), dynamicObject);

        assert dynamicObject.getMovementController() != null;
        if (!dynamicObject.getMovementController().isInitialized()){
            dynamicObject.initializeMovementController();
        }
    }

    private void attachGeometryIfNecessary(GameObject gameObject) {
        if (gameObject instanceof SpatialObject){
            XithGeometry geometryXith = (XithGeometry) ((SpatialObject) gameObject).getGeometry();
            if (geometryXith.isSeparatedModel()){
                ((Group)((XithGeometry)getGeometry()).getSceneGraphNode()).addChild(geometryXith.getSceneGraphNode());
            }
        }
    }


    public void loadWorldGeometry(){
        if (this.worldGeometry != null){
            //initialization call
            this.worldGeometry.getSceneGraphNode();
            setGeometry(this.worldGeometry);
        }else{
            XithGeometry geom = new GeometryXithImpl("DefaultWorldGeometry", new BranchGroup());
            setGeometry(geom);
        }
    }


    @Override
    public void initializeMovementController() {
        throw new UnsupportedOperationException("World does not support MovementControllers.");
    }

    @Override
    public void update() {
        gameTime = System.currentTimeMillis() - gameTime;
        elapsedTime = gameTime - elapsedTime;
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
