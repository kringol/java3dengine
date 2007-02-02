package org.test.xith3d.loaders;

import org.xith3d.loaders.scene.Scene;
import org.xith3d.scenegraph.Group;

/**
 * Created by IntelliJ IDEA.
 * User: Pablo
 * Date: Jan 27, 2007
 * Time: 1:26:50 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ModelLoader {
 
    Group loadScene(String resourcePath);
}
