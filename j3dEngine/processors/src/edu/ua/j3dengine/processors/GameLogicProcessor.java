package edu.ua.j3dengine.processors;

import edu.ua.j3dengine.core.DynamicGameObject;
import edu.ua.j3dengine.core.World;
import edu.ua.j3dengine.core.mgmt.GameObjectManager;

import java.util.Collection;


public class GameLogicProcessor extends Processor {

    private static GameLogicProcessor instance;

    private GameLogicProcessor() {
        super("GameLogicProcessor");
    }

    public synchronized static GameLogicProcessor getInstance() {
        if (instance == null) {
            instance = new GameLogicProcessor();
        }
        return instance;
    }


    public void performConcreteInitialize() {
        GameObjectManager.getInstance();
    }

    public void performConcreteRelease() {
        //do nothing
    }

    public void performConcreteExecute() {
        World world = GameObjectManager.getInstance().getWorld();
        world.update();

        Collection<DynamicGameObject> objects = GameObjectManager.getInstance().getAllDynamicObjects();
        for (DynamicGameObject object : objects) {
            object.update();
        }
    }

    public String getType() {
        return TYPE;
    }

    public static final String TYPE = "processor.gamelogic";
}
