package edu.ua.j3dengine.core.geometry.impl;

import org.xith3d.scenegraph.Node;
import org.xith3d.scenegraph.Transform3D;
import edu.ua.j3dengine.core.geometry.Geometry;


public interface XithGeometry extends Geometry {

    Node getSceneGraphNode();

    /**
     * Specifies wheter the geometry is part of the world geometry or is loaded from a different model.
     * @return
     */
    boolean isSeparatedModel();

    /**
     * Specifies additional transform between Scenegraph and model.
     * @return The additional pre-transform or null if no transform is to be inserted inbetween.
     */
    Transform3D getPreTransform();
}
