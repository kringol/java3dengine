package edu.ua.j3dengine.core.mgmt;

import edu.ua.j3dengine.core.*;
import static edu.ua.j3dengine.utils.Utils.logDebug;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Collection;

import org.xith3d.scenegraph.View;


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

    public void loadWorld(World world) throws WorldInitializationException {
        if (initialized) {
            throw new IllegalStateException("A World has already been loaded.");
        }
        this.world = world;

        this.world.loadWorldGeometry();


        initialized = true;
        logDebug("World '" + this.world + "' initialized successfully!");
    }

    public void loadWorld(File worldConfigFile) throws WorldInitializationException {
        if (initialized) {
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

        world.loadWorldGeometry();


        initialized = true;
        logDebug("World '" + unmarshalledWorld + "' initialized successfully!");

    }

    public void setDefaultCamera(View view){
        Camera camera = GameObjectFactory.getInstance().createCamera(null, view);
        getWorld().setDefaultCamera(camera);
    }

    public Camera getDefaultCamera(){
        return getWorld().getDefaultCamera();
    }

    public long getGameTime() {
        return getWorld().getGameTime();
    }

    public long getGameSeconds() {
        return getGameTime() / 1000;
    }

    /**
     * Returns elapsed game time since last update and the previous one.
     */
    public long getElapsedTime() {
        return getWorld().getElapsedTime();
    }

    public double getElapsedSeconds() {
        return getElapsedTime() / 1000d;
    }


    public World getWorld() {
        if (!isInitialized()) {
            throw new IllegalStateException("No World has been loaded yet.");
        }
        assert world != null;
        return world;
    }




    public boolean isInitialized() {
        return initialized;
    }

    public GameObject lookupGameObject(String name) {
        return getWorld().getGameObject(name);
    }

    public Collection<GameObject> getAllGameObjects() {
        return getWorld().getAllGameObjects();
    }

    public Collection<DynamicGameObject> getAllDynamicObjects() {
        return getWorld().getAllDynamicObjects();
    }


    public static JAXBContext createJAXBContext() throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(
                "edu.ua.j3dengine.core:" +
                        "edu.ua.j3dengine.core.state:" +
                        "edu.ua.j3dengine.core.geometry.impl:" +
                        "edu.ua.j3dengine.core.behavior");
        return ctx;
    }


}
