package edu.ua.j3dengine.core.geometry.impl;

import edu.ua.j3dengine.core.geometry.BaseGeometry;
import org.xith3d.scenegraph.Node;
import org.xith3d.scenegraph.SceneGraphObject;

import javax.vecmath.Tuple3f;


public class GeometryXithImpl extends BaseGeometry implements XithGeometry {

    private Node node;


    private GeometryXithImpl() {
    }

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


    public boolean isSeparatedModel() {
        return true;
    }
}
