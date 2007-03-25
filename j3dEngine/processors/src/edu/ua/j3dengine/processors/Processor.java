package edu.ua.j3dengine.processors;

import static edu.ua.j3dengine.utils.Utils.*;


public abstract class Processor {

    private String name;
    private boolean initialized = false;
    private boolean released = false;

    protected Processor(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }


    public boolean isInitialized() {
        return initialized;
    }

    public boolean isReleased() {
        return released;
    }

    public final void initialize() {
        if (released) {
            throw new IllegalStateException("Processor '" + getName() + "' has been released.");
        }
        if (initialized) {
            throw new IllegalStateException("Processor '" + getName() + "' has already been initialized.");
        }
        performConcreteInitialize();
        initialized = true;
        logInfo("Processor '" + getName() + "' has been initialized.");
    }

    public abstract void performConcreteInitialize();

    public final void execute() {
        if (!initialized) {
            throw new IllegalStateException("Processor '" + getName() + "' is not initialized.");
        }
        performConcreteExecute();
    }

    public abstract void performConcreteExecute();

    public final void release() {
        if (!initialized) {
            throw new IllegalStateException("Processor '" + getName() + "' is not initialized.");
        }
        if (released) {
            throw new IllegalStateException("Processor '" + getName() + "' has already been released.");
        }
        performConcreteRelease();
        released = true;
        logInfo("Processor '" + getName() + "' has been released.");
    }

    public abstract String getId();

    public abstract void performConcreteRelease();


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Processor processor = (Processor) o;

        if (name != null ? !name.equals(processor.name) : processor.name != null) return false;
        if (!getId().equals(processor.getId())) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (name != null ? name.hashCode() : 0);
        result = 31 * result + getId().hashCode();
        return result;
    }

    public String toString() {
        return "Name: '" + name + "' - Id: '" + getId() + "'";
    }
}
