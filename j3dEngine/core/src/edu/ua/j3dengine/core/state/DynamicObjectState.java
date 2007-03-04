package edu.ua.j3dengine.core.state;

import static edu.ua.j3dengine.utils.AssertionUtils.*;
import edu.ua.j3dengine.core.behavior.Behavior;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DynamicObjectState extends State {


    private boolean activated = false;
    private Behavior activationBehavior;
    private Behavior deactivationBehavior;
    private Behavior normalBehavior;


    private DynamicObjectState(String name, Behavior activationBehavior, Behavior deactivationBehavior, Behavior normalBehavior) {
        super(name);
        assertNotNull(normalBehavior, "DynamicObjectStates must have a normal behavior.");
        this.activationBehavior = activationBehavior;
        this.deactivationBehavior = deactivationBehavior;
        this.normalBehavior = normalBehavior;
    }

    private DynamicObjectState(){}

    public static DynamicObjectState create(String name, Behavior activationBehavior, Behavior deactivationBehavior, Behavior normalBehavior) {
        return new DynamicObjectState(name, activationBehavior, deactivationBehavior, normalBehavior);
    }

    public void activate() {
        if (activationBehavior != null){
            activationBehavior.execute();
            activated = true;
        }
    }

    public void deactivate() {
        if (deactivationBehavior != null){
            deactivationBehavior.execute();
            activated = false;
        }
    }


    public Behavior getNormalBehavior() {
        if (!isActivated()){
            throw new IllegalStateException("State '"+getName()+"' has not been activated.");
        }
        assert normalBehavior != null;
        return normalBehavior;
    }


    public boolean isActivated() {
        return activated;
    }
}
