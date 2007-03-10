package edu.ua.j3dengine.core.mgmt;

import edu.ua.j3dengine.core.exception.ModelLoadingException;
import org.xith3d.loaders.models.base.Model;
import org.xith3d.loaders.models.impl.dae.DaeLoader;

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

    public Model getModel(String modelPath){
        Model model = models.get(modelPath);
        if (model == null){
            try {
                model = DaeLoader.getInstance().loadModel(modelPath);
            } catch (IOException e) {
                throw new ModelLoadingException("Model could not be loaded.", e);
            }
            models.put(modelPath, model);
        }
        return model;
    }

    
}
