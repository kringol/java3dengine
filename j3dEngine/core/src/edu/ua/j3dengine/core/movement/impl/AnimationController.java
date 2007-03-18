package edu.ua.j3dengine.core.movement.impl;

import static edu.ua.j3dengine.utils.Utils.*;

import edu.ua.j3dengine.core.movement.MovementController;
import edu.ua.j3dengine.core.DynamicGameObject;
import edu.ua.j3dengine.core.mgmt.GameObjectManager;
import edu.ua.j3dengine.core.geometry.impl.XithGeometry;
import org.xith3d.loaders.models.util.precomputed.PrecomputedAnimatedModel;
import org.xith3d.loaders.models.util.precomputed.Animation;
import org.xith3d.behaviors.Animatable;


public class AnimationController extends MovementController {

    private Animatable animatable;

    public AnimationController(DynamicGameObject targetObject) {
        super(targetObject);
    }


    public void initialize() {
        XithGeometry geom = (XithGeometry) getTargetGeometry();
        assert geom != null;
        if (geom.getSceneGraphNode() instanceof Animatable) {
            animatable = (Animatable) geom.getSceneGraphNode();
        } else {
            logDebug("AnimationController cannot be initialized. Model is not animatable. It will execute without operation.");
        }
    }

    public boolean isInitialized() {
        return animatable != null;
    }

    protected void performConcreteUpdate(long elapsedTime) {
        if (isInitialized()) {
            animatable.executeOperation(GameObjectManager.getInstance().getGameTime(), elapsedTime);
        }
    }


    public void changeAnimation(String animationName, boolean loopAnimation) {
        if (!(animatable instanceof PrecomputedAnimatedModel)) {
            throw new UnsupportedOperationException("This model cannot change its animation.");
        }

        PrecomputedAnimatedModel model = (PrecomputedAnimatedModel) animatable;

        Animation animation = model.getAnimations().get(animationName);
        if (animation == null) {
            throw new IllegalArgumentException("Wrong animation name. Animation '" + animationName + "' does not exist for object '" + getTargetObject().getName() + "'");
        }
        model.play(animation, loopAnimation);
    }

    public void changeAnimation(String animationName) {
        changeAnimation(animationName, true);
    }

    public void stopAnimation(){
        animatable.stopAnimation();
    }

    public void startAnimation(){
        animatable.startAnimation(GameObjectManager.getInstance().getGameTime());
    }
}
