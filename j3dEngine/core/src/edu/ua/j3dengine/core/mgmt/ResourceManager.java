package edu.ua.j3dengine.core.mgmt;

import edu.ua.j3dengine.core.exception.ModelLoadingException;
import org.xith3d.loaders.models.base.Model;
import org.xith3d.loaders.models.base.ModelLoader;

import org.xith3d.loaders.models.impl.dae.DaeLoader;
import org.xith3d.loaders.models.impl.tds.TDSLoader;
import org.xith3d.loaders.models.impl.obj.OBJLoader;
import org.xith3d.loaders.models.impl.cal3d.Cal3dLoader;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ResourceManager {

    private static ResourceManager instance;

    private Map<String, Model> models;


    private ResourceManager() {
        models = new HashMap<String, Model>();
    }

    //concurrent access before initialization is highly improvable, so no need to 'synchronize' this method
    public static ResourceManager getInstance() {
        if (instance == null) {
            instance = new ResourceManager();
        }
        return instance;
    }

    public Model getModel(String modelPath) {
        return getModel(modelPath, null);
    }

    public Model getModel(String modelPath, ModelFormat modelFormat) {
        Model model = models.get(modelPath);
        if (model == null) {

            ModelLoader loader;
            if (modelFormat == null) {
                loader = ModelFormat.DEFAULT.getLoader();
            } else {
                loader = modelFormat.getLoader();
            }

            try {
                model = loader.loadModel(modelPath);
            } catch (IOException e) {
                throw new ModelLoadingException("Model could not be loaded.", e);
            }

            models.put(modelPath, model);
        }
        return model;
    }


    public static enum ModelFormat {
        CAL3D{
            public ModelLoader getLoader() {
                return new Cal3dLoader(Cal3dLoader.LOADER_ROTATE_X_AXIS);
            }

            public String getFileExtension() {
                return "cfg";
            }},

        WAVEFRONT {
            public ModelLoader getLoader() {
                return OBJLoader.getInstance();
            }

            public String getFileExtension() {
                return "obj";
            }},
        TDS {
            public ModelLoader getLoader() {
                return TDSLoader.getInstance();
            }

            public String getFileExtension() {
                return "3ds";
            }},
        COLLADA {
            public ModelLoader getLoader() {
                return DaeLoader.getInstance();
            }
            public String getFileExtension() {
                return "dae";
            }},
        DEFAULT {
            public ModelLoader getLoader() {
                return COLLADA.getLoader();
            }
            public String getFileExtension() {
                return COLLADA.getFileExtension();
            }};

        public abstract String getFileExtension();

        public abstract ModelLoader getLoader();
    }


}
