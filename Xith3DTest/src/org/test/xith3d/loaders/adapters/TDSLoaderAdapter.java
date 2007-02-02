package org.test.xith3d.loaders.adapters;

import org.test.xith3d.loaders.ModelLoader;
import org.xith3d.loaders.tds.kinostudios.TDSLoader;
import org.xith3d.scenegraph.Group;

import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: Pablo
 * Date: Jan 27, 2007
 * Time: 1:46:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class TDSLoaderAdapter implements ModelLoader {

    private TDSLoader loader;


    public TDSLoaderAdapter() {
        loader = new TDSLoader();
    }


    public Group loadScene(String resourcePath) {
        URL resource = TDSLoader.class.getClassLoader().getResource(resourcePath);

        //assert resource != null;

        Group scene = null;
        try {
            scene = loader.load(resourcePath).getSceneGroup();
        } catch (Exception e) {
            throw new ModelLoadingException(e);
        }

        return scene;
    }
}
