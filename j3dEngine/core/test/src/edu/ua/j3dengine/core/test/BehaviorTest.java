package edu.ua.j3dengine.core.test;

import junit.framework.TestCase;
import edu.ua.j3dengine.core.mgmt.GameObjectManager;
import edu.ua.j3dengine.core.mgmt.ResourceManager;
import static edu.ua.j3dengine.core.test.Resources.TEST_WORLD_FILE_PATH;
import static edu.ua.j3dengine.core.test.Resources.BASE_DIR;
import static edu.ua.j3dengine.core.test.Resources.TDS_MODEL_PATH;
import edu.ua.j3dengine.core.DynamicGameObject;
import edu.ua.j3dengine.core.World;
import edu.ua.j3dengine.core.behavior.InertBehavior;
import edu.ua.j3dengine.core.behavior.impl.BasicMovementBehavior;
import edu.ua.j3dengine.core.state.DynamicObjectState;
import edu.ua.j3dengine.core.geometry.impl.ModelAdapterGeometry;

import java.io.File;

import org.xith3d.loaders.models.base.Model;
import org.xith3d.scenegraph.SceneGraphObject;


public class BehaviorTest extends TestCase {


    public BehaviorTest() {
        super("BehaviorTest");
    }

    public void testTdsLoadingAndDynamicObjectBehavior() throws GameObjectManager.WorldInitializationException {

        GameObjectManager objectManager = GameObjectManager.getInstance();

        objectManager.loadWorld(TEST_WORLD_FILE_PATH);

        File modelFile = new File(new File(BASE_DIR), TDS_MODEL_PATH);

        Model model = ResourceManager.getInstance().getModel(modelFile.getAbsolutePath(), ResourceManager.ModelFormat.TDS);

        String modelObjectName = "DraziSunHa";
        SceneGraphObject sgo = model.getNamedObject(modelObjectName);

        DynamicGameObject object = new DynamicGameObject("DraziSun Hawk");
        ModelAdapterGeometry geometry = new ModelAdapterGeometry(modelFile.getAbsolutePath(), modelObjectName);
        object.setGeometry(geometry);
        DynamicObjectState state = new DynamicObjectState("DynState",
                null, new InertBehavior(),
                new BasicMovementBehavior(object));
        object.addState(state);
        object.setInitialState(state.getName());

        World world = objectManager.getWorld();
        world.addGameObject(object);
        world.update();
        object.update();
    }
}
