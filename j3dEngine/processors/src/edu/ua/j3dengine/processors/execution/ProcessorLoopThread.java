package edu.ua.j3dengine.processors.execution;

import static edu.ua.j3dengine.utils.ValidationUtils.*;
import static edu.ua.j3dengine.utils.Utils.*;

import edu.ua.j3dengine.processors.Processor;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


public class ProcessorLoopThread extends Thread {

    private List<Processor> processors;
    private Boolean active = Boolean.FALSE;

    private ProcessorLoopThread(ThreadGroup group, String name, int priority, Processor... processors) {
        super(group, name);

        validateInsideRange(priority, Thread.MIN_PRIORITY, Thread.MAX_PRIORITY);
        this.setPriority(priority);

        this.processors = new LinkedList<Processor>();
        for (Processor processor : processors) {
            this.processors.add(processor);
        }
    }

    public static ProcessorLoopThread create(ThreadGroup group, String name, int priority, Collection<Processor> processors){
        return create(group, name, priority, processors.toArray(new Processor[processors.size()]));
    }

    public static ProcessorLoopThread create(ThreadGroup group, String name, int priority, Processor... processors){
        return new ProcessorLoopThread(group, name, priority, processors);
    }

    public synchronized void deactivate(){
        this.active = false;
        for (Processor processor : processors) {
            processor.release();
        }
    }

    @Override
    public synchronized void start() {
        activate();
        super.start();
    }

    private void activate() {
        this.active = true;
        for (Processor processor : processors) {
            processor.initialize();
        }
    }

    @Override
    public void run() {
        while (active){
            for (Processor processor : processors) {
                processor.execute();
            }
        }
        logDebug("'" + getName() + "' processor Loop has finished its execution.");
    }
}
