package edu.ua.j3dengine.core.movement;


public interface AnimatedMovementController extends BasicMovementController {

    void changeAnimation(String animationName, boolean loopAnimation);

    void changeAnimation(String animationName);

    void stopAnimation();

    void startAnimation();
    
}
