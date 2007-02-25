package org.test.xith3d.movement;

import org.xith3d.scenegraph.TransformGroup;
import org.xith3d.scenegraph.Node;
import org.xith3d.scenegraph.Group;

import java.util.List;
import java.util.ArrayList;

public class Utils {

    public static TransformGroup insertTransformGroup(Group parentGroup, TransformGroup newTG){

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

    public static TransformGroup insertTransformGroup(Group parentGroup){
        return insertTransformGroup(parentGroup, null);
    }
}
