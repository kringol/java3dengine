package edu.ua.j3dengine.core;

import java.util.*;

import static edu.ua.j3dengine.utils.AssertionUtils.*;

public class World extends GameObject {

    private static final String DEFAULT_WORLD_NAME = "World";

    private Map<String, GameObject> worldObjects;

    private World(String name) {
        super(name);
        this.worldObjects = new HashMap<String, GameObject>();
    }

    private World(){
        this(DEFAULT_WORLD_NAME);
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

    public void putGameObject(GameObject gameObject){
        assertNotNull(gameObject);
        worldObjects.put(gameObject.getName(), gameObject);
    }

    





}
