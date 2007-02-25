package org.test.xith3d.movement;

import org.xith3d.scenegraph.TransformGroup;
import org.xith3d.scenegraph.Group;
import org.xith3d.scenegraph.Transform3D;

import javax.vecmath.Vector3f;


public abstract class MovementController {

    private Group parentGroup;
    private TransformGroup transformGroup;
    private String objectName;
    private long lastMillisTime = 0;

    protected static final long MIN_ACTIVATION_TIME_MILLIS = 10L;

    protected MovementController(Group group, String objectName) {
        this.parentGroup = group;
        this.transformGroup = Utils.insertTransformGroup(group);
        this.objectName = objectName;
    }


    public final void update(){
         if (lastMillisTime == 0) {
            lastMillisTime = System.currentTimeMillis();
        } else {

            long currentMillisTime = System.currentTimeMillis();
            long deltaMillisTime = currentMillisTime - lastMillisTime;

            if (deltaMillisTime > MIN_ACTIVATION_TIME_MILLIS) {
                Transform3D transform = getTransformGroup().getTransform();
                transform = updateTransform(transform, deltaMillisTime);
                getTransformGroup().setTransform(transform);
                lastMillisTime = currentMillisTime;
            }
        }
    }

    protected abstract Transform3D updateTransform(Transform3D transform, long elapsedMillis); 


    public Group getParentGroup() {
        return parentGroup;
    }

    public TransformGroup getTransformGroup() {
        return transformGroup;
    }

    public String getObjectName() {
        return objectName;
    }


}
