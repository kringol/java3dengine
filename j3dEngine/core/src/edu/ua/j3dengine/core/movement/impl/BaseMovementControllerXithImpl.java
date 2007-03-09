package edu.ua.j3dengine.core.movement.impl;

import org.xith3d.scenegraph.Transform3D;
import org.xith3d.scenegraph.TransformGroup;
import edu.ua.j3dengine.core.movement.MovementController;
import edu.ua.j3dengine.core.DynamicGameObject;
import edu.ua.j3dengine.core.geometry.impl.DynamicGeometryImpl;


public abstract class BaseMovementControllerXithImpl extends MovementController {


    public BaseMovementControllerXithImpl(DynamicGameObject targetObject) {
        super(targetObject);
    }


    public final void performConcreteUpdate(long elapsedTime) {
        assert getTargetGeometry() instanceof DynamicGeometryImpl;

        TransformGroup tg = ((DynamicGeometryImpl)getTargetGeometry()).getTargetTransformGroup();
        Transform3D transform = tg.getTransform();
        transform = updateTransform(transform, elapsedTime);
        tg.setTransform(transform);

    }

    protected abstract Transform3D updateTransform(Transform3D transform, long elapsedMillis);


}
