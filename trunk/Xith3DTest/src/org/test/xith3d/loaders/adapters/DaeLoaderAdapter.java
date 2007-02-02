package org.test.xith3d.loaders.adapters;


import org.test.xith3d.loaders.ModelLoader;
import org.xith3d.loaders.models.impl.dae.DaeLoader;
import org.xith3d.scenegraph.Group;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: Pablo
 * Date: Jan 27, 2007
 * Time: 1:31:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class DaeLoaderAdapter implements ModelLoader {

    private DaeLoader loader;


    public DaeLoaderAdapter() {
        this.loader = new DaeLoader();
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

            //loader.setFlags(DaeLoader.LOAD_ALL);
            scene = loader.loadScene(modelResource);
        } catch (Exception e) {
            throw new ModelLoadingException(e);
        }
        return scene;
    }
}
