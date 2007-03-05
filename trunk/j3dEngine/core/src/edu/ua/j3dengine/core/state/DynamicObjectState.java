package edu.ua.j3dengine.core.state;

import edu.ua.j3dengine.core.behavior.Behavior;
import static edu.ua.j3dengine.utils.ValidationUtils.validateNotNull;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DynamicObjectState extends State {


    private boolean activated = false;

    @XmlElement
    private Behavior activationBehavior;
    
    @XmlElement
    private Behavior deactivationBehavior;

    @XmlElement
    private Behavior normalBehavior;


    public DynamicObjectState(String name, Behavior activationBehavior, Behavior deactivationBehavior, Behavior normalBehavior) {
        super(name);
        validateNotNull(normalBehavior, "DynamicObjectStates must have a normal behavior.");
        this.activationBehavior = activationBehavior;
        this.deactivationBehavior = deactivationBehavior;
        this.normalBehavior = normalBehavior;
    }

    private DynamicObjectState(){}

    

    public void activate() {
        if (activationBehavior != null){
            activationBehavior.execute();
        }
        activated = true;
    }

    public void deactivate() {
        if (deactivationBehavior != null){
            deactivationBehavior.execute();
        }
        activated = false;
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
