package edu.ua.j3dengine.core;

import static edu.ua.j3dengine.utils.ValidationUtils.validateNotNull;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

@XmlRootElement
public class World extends DynamicGameObject {

    private static final String DEFAULT_WORLD_NAME = "World";

    private long elapsedTime;
    private long gameTime;

    @XmlElementWrapper
    private Map<String, GameObject> worldObjects;

    /**
     * Created for fast access to dynamic objects.
     */
    private Map<String, DynamicGameObject> dynamicObjects;

    private World(String name) {
        super(name);
        this.gameTime = System.currentTimeMillis();
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
    }

    public void storeDynamicObject(DynamicGameObject dynamicObject){
        dynamicObjects.put(dynamicObject.getName(), dynamicObject);
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
