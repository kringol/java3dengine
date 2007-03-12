package edu.ua.j3dengine.core.mgmt;

import edu.ua.j3dengine.core.GameObject;
import edu.ua.j3dengine.core.World;
import edu.ua.j3dengine.core.DynamicGameObject;
import static edu.ua.j3dengine.utils.Utils.logDebug;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Collection;
import java.util.Collections;


public class GameObjectManager {

    private static GameObjectManager instance;

    private boolean initialized = false;
    private File worldConfigFile;
    private World world;

    private GameObjectManager() {
    }

    //concurrent access before initialization is highly improvable, so no need to 'synchronize' this method
    public static GameObjectManager getInstance() {
        if (instance == null) {
            instance = new GameObjectManager();
        }
        return instance;
    }

    public void loadWorld(String worldConfigFilePath) throws WorldInitializationException {
        loadWorld(new File(worldConfigFilePath));
    }

    public void loadWorld(File worldConfigFile) throws WorldInitializationException {
        if (initialized){
            throw new IllegalStateException("A World has already been loaded.");
        }

        this.worldConfigFile = worldConfigFile;

        if (!worldConfigFile.exists()) {
            throw new IllegalArgumentException("World Config File '" + worldConfigFile.getAbsolutePath() + "' does not exists.");
        }

        //todo (pablius) add validation with XMLSchema

        Object worldObject;
        try {
            JAXBContext ctx = createJAXBContext();

            Unmarshaller um = ctx.createUnmarshaller();

            worldObject = um.unmarshal(worldConfigFile);
        } catch (JAXBException e) {
            throw new WorldInitializationException(e);
        }

        if (worldObject == null) {
            throw new WorldInitializationException("World could not be loaded!");
        }

        World unmarshalledWorld;
        try {
            unmarshalledWorld = (World) worldObject;
        } catch (ClassCastException e) {
            throw new WorldInitializationException("World could not be loaded!", e);
        }

        this.world = unmarshalledWorld;

        initialized = true;
        logDebug("World '" + unmarshalledWorld + "' initialized successfully!");

    }

    public long getGameTime(){
        return getWorld().getGameTime();
    }

    public long getGameSeconds(){
        return getGameTime() / 1000;
    }

    /**
     * Returns elapsed game time since last update and the previous one.
     */
    public long getElapsedTime(){
        return getWorld().getElapsedTime();
    }

    public long getElapsedSeconds(){
        return getElapsedTime() / 1000;
    }


    public World getWorld() {
        if (!initialized){
            throw new IllegalStateException("No World has been loaded yet.");
        }
        assert world != null;
        return world;
    }


    public boolean isInitialized() {
        return initialized;
    }

    public GameObject lookupGameObject(String name){
        return getWorld().getGameObject(name);
    }

    public Collection<GameObject> getAllGameObjects(){
        return getWorld().getAllGameObjects();
    }

    public Collection<DynamicGameObject> getAllDynamicObjects(){
        return getWorld().getAllDynamicObjects();
    }


    public static class WorldInitializationException extends Exception {

        public WorldInitializationException() {
        }

        public WorldInitializationException(String message) {
            super(message);
        }

        public WorldInitializationException(String message, Throwable cause) {
            super(message, cause);
        }

        public WorldInitializationException(Throwable cause) {
            super(cause);
        }
    }

    public static JAXBContext createJAXBContext() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(
                "edu.ua.j3dengine.core:" +
                "edu.ua.j3dengine.core.state:" +
                "edu.ua.j3dengine.core.geometry:" +
                "edu.ua.j3dengine.core.behavior");
        return ctx;
    }


}
