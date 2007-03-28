package edu.ua.j3dengine.core.geometry.impl;

import edu.ua.j3dengine.core.geometry.BaseGeometry;
import org.xith3d.scenegraph.Node;
import org.xith3d.scenegraph.SceneGraphObject;
import org.xith3d.scenegraph.Transform3D;

import javax.vecmath.Tuple3f;


public class GeometryXithImpl extends BaseGeometry implements XithGeometry {

    private Node node;
    private Node movementReferenceNode;


    private GeometryXithImpl() {
    }

    public GeometryXithImpl(String name, Node node) {
        super(name);
        this.node = node;
    }


    public Tuple3f getLocation() {
        return node.getLocalToVworld().getTranslation();
    }


    public Node getMovementReferenceNode() {
        return movementReferenceNode != null ? movementReferenceNode : node;
    }

    public void setMovementReferenceNode(Node movementReferenceNode) {
        this.movementReferenceNode = movementReferenceNode;
    }

    public Node getSceneGraphNode() {
        return node;
    }


    public boolean isSeparatedModel() {
        return true;
    }


    public Transform3D getPreTransform() {
        return null;
    }


    public boolean loadLater() {
        return false;
    }
}
