package edu.ua.j3dengine.core;

import java.util.*;

import static edu.ua.j3dengine.utils.AssertionUtils.*;

import javax.xml.bind.annotation.*;

@XmlRootElement
public class World extends GameObject {

    private static final String DEFAULT_WORLD_NAME = "World";


    @XmlElementWrapper
    private Map<String, GameObject> worldObjects;

    private World(String name) {
        super(name);
        this.worldObjects = new HashMap<String, GameObject>();
    }

    private World(){
        this(DEFAULT_WORLD_NAME);
    }

    public static World create(String name, Collection<GameObject> worldObjects){
        World world = new World(name);
        for (GameObject worldObject : worldObjects) {
            world.addGameObject(worldObject);
        }
        return world;
    }

    public Set<String> getAllGameObjectNames(){
        return Collections.unmodifiableSet(worldObjects.keySet());
    }

    public Collection<GameObject> getAllGameObjects(){
        return Collections.unmodifiableCollection(worldObjects.values());
    }

    public GameObject getGameObject(String name){
        return worldObjects.get(name);
    }

    public void addGameObject(GameObject gameObject){
        assertNotNull(gameObject);
        worldObjects.put(gameObject.getName(), gameObject);
    }


    public boolean isDynamic() {
        return false;
    }

    public GameObjectType getGameObjectType() {
        return GameObjectType.STATIC;
    }
}
