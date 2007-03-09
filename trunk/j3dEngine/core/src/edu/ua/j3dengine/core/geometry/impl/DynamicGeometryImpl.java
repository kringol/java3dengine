package edu.ua.j3dengine.core.geometry.impl;

import org.xith3d.scenegraph.TransformGroup;
import org.xith3d.scenegraph.Node;



public class DynamicGeometryImpl extends GeometryXithImpl {

    private TransformGroup targetTransformGroup;

    public DynamicGeometryImpl(String name, Node node) {
        super(name, node);
        this.targetTransformGroup = Utils.insertTransformGroup(node);
    }

    public TransformGroup getTargetTransformGroup() {
        return targetTransformGroup;
    }
}
