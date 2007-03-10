package edu.ua.j3dengine.core.movement.impl;

import edu.ua.j3dengine.core.DynamicGameObject;
import edu.ua.j3dengine.core.geometry.impl.Utils;
import edu.ua.j3dengine.core.geometry.impl.XithGeometry;
import edu.ua.j3dengine.core.movement.MovementController;
import org.xith3d.scenegraph.Transform3D;
import org.xith3d.scenegraph.TransformGroup;


public abstract class BaseMovementControllerXithImpl extends MovementController {

    private TransformGroup targetTransformGroup;

    public BaseMovementControllerXithImpl(DynamicGameObject targetObject) {
        super(targetObject);
        targetTransformGroup = Utils.insertTransformGroup(((XithGeometry)targetObject).getSceneGraphNode());
    }


    protected final void performConcreteUpdate(long elapsedTime) {
        assert targetTransformGroup != null;

        Transform3D transform = targetTransformGroup.getTransform();
        transform = updateTransform(transform, elapsedTime);
        targetTransformGroup.setTransform(transform);
    }



    protected abstract Transform3D updateTransform(Transform3D transform, long elapsedMillis);


}
