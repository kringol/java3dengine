package edu.ua.j3dengine.core.geometry.impl;

import org.xith3d.scenegraph.Node;
import edu.ua.j3dengine.core.geometry.Geometry;


public interface XithGeometry extends Geometry {

    Node getSceneGraphNode();

    /**
     * Specifies wheter the geometry is part of the world geometry or is loaded from a different model.
     * @return
     */
    boolean isSeparatedModel();
}
