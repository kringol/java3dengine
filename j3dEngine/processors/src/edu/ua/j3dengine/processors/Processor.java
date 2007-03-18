package edu.ua.j3dengine.processors;


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
    }

    public abstract String getType();

    public abstract void performConcreteRelease();

}
