package edu.ua.j3dengine.core.geometry.impl;

import static edu.ua.j3dengine.utils.ValidationUtils.*;

import org.xith3d.scenegraph.TransformGroup;
import org.xith3d.scenegraph.Group;
import org.xith3d.scenegraph.Node;
import org.xith3d.scenegraph.GroupNode;

import java.util.List;
import java.util.ArrayList;


public class Utils {
    public static TransformGroup insertTransformGroup(Node node, TransformGroup newTG){
        validateNotNull(node);
        
        TransformGroup tg = newTG != null ? newTG : new TransformGroup();
//
//        if (node instanceof GroupNode){
//            return insertTransformGroup((GroupNode)node, tg);
//        }else{
            GroupNode parent = node.getParent();
            if (parent != null){
                node.removeFromParentGroup();
                tg.addChild(node);
                parent.addChild(tg);
            }else{
                throw new IllegalArgumentException("Node cannot be root.");
            }
//        }

        return tg;
    }
    public static TransformGroup insertTransformGroup(Node node){
        return insertTransformGroup(node, null);
    }

    private static TransformGroup insertTransformGroup(GroupNode parentGroup, TransformGroup newTG){

        TransformGroup tg = newTG != null ? newTG : new TransformGroup();

        List<Node> children = parentGroup.getChildren();

        List<Node> childrenList = new ArrayList<Node>();

        for (Node child : children) {
            childrenList.add(child);
        }

        for (Node node : childrenList) {
            node.removeFromParentGroup();
            tg.addChild(node);
        }

        parentGroup.addChild(tg);
        return tg;
    }

    private static TransformGroup insertTransformGroup(GroupNode parentGroup){
        return insertTransformGroup(parentGroup, null);
    }
}
