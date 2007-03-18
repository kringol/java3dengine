package edu.ua.j3dengine.core.mgmt;

import static edu.ua.j3dengine.utils.ValidationUtils.*;

import edu.ua.j3dengine.core.exception.ModelLoadingException;
import org.xith3d.loaders.models.base.Model;
import org.xith3d.loaders.models.base.ModelLoader;

import org.xith3d.loaders.models.impl.dae.DaeLoader;
import org.xith3d.loaders.models.impl.tds.TDSLoader;
import org.xith3d.loaders.models.impl.obj.OBJLoader;
import org.xith3d.loaders.models.impl.cal3d.Cal3dLoader;
import org.xith3d.loaders.models.util.precomputed.PrecomputedAnimatedModel;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ResourceManager {

    private static ResourceManager instance;

    private Map<String, Model> models;
    private Map<String, PrecomputedAnimatedModel> precomputedModels;


    private ResourceManager() {
        models = new HashMap<String, Model>();
        precomputedModels = new HashMap<String, PrecomputedAnimatedModel>();
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

    public PrecomputedAnimatedModel getPrecomputedCal3DModel(String modelPath){
        PrecomputedAnimatedModel model = precomputedModels.get(modelPath);

        if (model == null){
            Cal3dLoader loader = (Cal3dLoader)ModelFormat.CAL3D.getLoader();

            try {
                model = loader.loadPrecomputedModel(modelPath);
            } catch (IOException e) {
                throw new ModelLoadingException("Model could not be loaded.", e);
            }
            precomputedModels.put(modelPath, model);
        }

        return model;
    }

    public Model getModel(String modelPath, ModelFormat modelFormat) {

        validateNotNull(modelPath);

        Model model = models.get(modelPath);
        if (model == null) {

            ModelLoader loader;

            if (modelFormat == null){
                modelFormat = findFormatByExtension(modelPath);
            }

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

    private ModelFormat findFormatByExtension(String modelPath) {
        ModelFormat modelFormat = null;

        for (ModelFormat format : ModelFormat.values()) {
            if ( modelPath.contains(format.getFileExtension()) ){
                modelFormat = format;
                break;
            }
        }

        return modelFormat;
    }


    public static enum ModelFormat {
        CAL3D{
            public ModelLoader getLoader() {
                return new Cal3dLoader(Cal3dLoader.LOADER_INVERT_V_COORD);
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
