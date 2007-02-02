package org.test.xith3d.loaders.adapters;


import org.xith3d.loaders.models.impl.obj.OBJLoader;
import org.xith3d.scenegraph.Group;
import org.test.xith3d.loaders.ModelLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;

/**
 * Created by IntelliJ IDEA.
 * User: Pablo
 * Date: Jan 27, 2007
 * Time: 1:43:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class OBJLoaderAdapter implements ModelLoader {
     private OBJLoader loader;


    public OBJLoaderAdapter() {
        this.loader = new OBJLoader();
    }

    public Group loadScene(String resourcePath) {
        File file = null;
        file = new File(resourcePath);

        URL modelResource = null;

        try {
            modelResource = file.toURL();
        } catch (MalformedURLException e) {
            throw new ModelLoadingException(e);
        }

        Group scene = null;
        try {
            scene = loader.loadScene(modelResource);
        } catch (FileNotFoundException e) {
            throw new ModelLoadingException(e);
        } catch (IOException e) {
            throw new ModelLoadingException(e);
        }
        return scene;
    }
}
