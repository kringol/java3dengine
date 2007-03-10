package edu.ua.j3dengine.core.geometry.impl;

import edu.ua.j3dengine.core.geometry.Geometry;
import org.xith3d.scenegraph.Node;

import javax.vecmath.Tuple3f;


public class GeometryXithImpl extends Geometry implements XithGeometry {

    private Node node;

    public GeometryXithImpl(String name, Node node) {
        super(name);
        this.node = node;
    }


    public Tuple3f getLocation() {
        return node.getLocalToVworld().getTranslation();
    }


    public Node getSceneGraphNode() {
        return node;
    }
}
