package org.test.xith3d.loaders.adapters;

import org.xith3d.loaders.scene.Scene;
import org.xith3d.loaders.tds.newdawn.TDSLoader;
import org.xith3d.loaders.tds.newdawn.model.TDSModel;
import org.xith3d.scenegraph.Group;

import org.test.xith3d.loaders.ModelLoader;

import java.net.URL;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: Pablo
 * Date: Jan 30, 2007
 * Time: 9:12:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class NewdawnTDSLoaderAdapter implements ModelLoader {

    private TDSLoader loader;


    public NewdawnTDSLoaderAdapter() {
        loader = new TDSLoader();
    }


    public Group loadScene(String resourcePath) {
        //URL resource = TDSLoader.class.getClassLoader().getResource(resourcePath);

        //assert resource != null;

        Group scene = null;
        try {
            scene = loader.load(resourcePath);
        } catch (Exception e) {
            throw new ModelLoadingException(e);
        }

        return scene;
    }
}
