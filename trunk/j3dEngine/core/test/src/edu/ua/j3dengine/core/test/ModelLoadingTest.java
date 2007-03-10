package edu.ua.j3dengine.core.test;

import edu.ua.j3dengine.core.geometry.Geometry;
import edu.ua.j3dengine.core.geometry.impl.ModelAdapterGeometry;
import edu.ua.j3dengine.core.mgmt.ResourceManager;
import junit.framework.TestCase;
import org.xith3d.loaders.models.base.Model;
import org.xith3d.loaders.models.impl.tds.TDSLoader;
import org.xith3d.scenegraph.SceneGraphObject;

import javax.vecmath.Vector3f;
import javax.vecmath.Tuple3f;
import java.io.File;
import java.io.IOException;

public class ModelLoadingTest extends TestCase {

    private static final String BASE_DIR = "resources";
    private static final String COLLADA_MODEL_PATH = "collada\\shuttle\\shuttle.dae";
    private static final String TDS_MODEL_PATH = "3ds\\drazinsunhawk\\Drazisunhawk1.1.3ds";


    public void testColladaLoading() throws IOException {

        File file = new File(new File(BASE_DIR), COLLADA_MODEL_PATH);

        assertTrue("Resource should be available.", file.exists());

        Model model = ResourceManager.getInstance().getModel(file.getAbsolutePath());

        assertNotNull("Model should have been loaded correctly, but its null.", model);

        SceneGraphObject sgo = model.getNamedObject("spaceship");

        assertNotNull("Model has not been completely loaded.", sgo);

    }

    public void testAdapterGeometryCreation() {

        File file = new File(new File(BASE_DIR), COLLADA_MODEL_PATH);

        assertTrue("Resource should be available.", file.exists());

        Geometry geometry = new ModelAdapterGeometry(file.getAbsolutePath(), "spaceship");

        Geometry adaptee = ((ModelAdapterGeometry)geometry).getAdapteeGeometry();

        assertNotNull("The geometry could not be created!.", adaptee);

        assertNotNull("Location should be provided.", adaptee.getLocation());

        Vector3f vector = new Vector3f(0, 0, 0);
        Tuple3f location = geometry.getLocation();
        assertTrue(vector.x == location.x && vector.y == location.y && vector.z == location.z);
    }

    public void testTdsModelLoading() throws IOException {
        File file = new File(new File(BASE_DIR), TDS_MODEL_PATH);

        assertTrue("Resource should be available.", file.exists());

        Model model = ResourceManager.getInstance().getModel(file.getAbsolutePath(), ResourceManager.ModelFormat.TDS);

        assertNotNull("Model should have been loaded correctly, but its null.", model);

        SceneGraphObject sgo = model.getNamedObject("DraziSunHa");

        assertNotNull("Model has not been completely loaded.", sgo);

        //test if is has been cached
        Model model2 = ResourceManager.getInstance().getModel(file.getAbsolutePath());

        assertTrue("Model should have been cached.", model == model2);

    }
}
