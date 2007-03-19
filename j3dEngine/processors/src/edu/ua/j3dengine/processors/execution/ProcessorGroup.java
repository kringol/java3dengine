package edu.ua.j3dengine.processors.execution;

import edu.ua.j3dengine.processors.Processor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class ProcessorGroup {

    @XmlElement
    private List<String> processors;

    @XmlElement
    private String name;

    @XmlElement
    private int priority;


    public ProcessorGroup(String name, int priority, List<Processor> processors) {
        addProcessors(processors);
        this.name = name;
        this.priority = priority;
    }

    public void addProcessors(List<Processor> processors) {
        for (Processor processor : processors) {
            addProcessor(processor);
        }
    }

    public void addProcessor(Processor processor) {
        String type = processor.getType();
        if (this.processors.contains(type)) {
            System.out.println("Ignoring processor '" + processor + "' because a process of that type already exists on this group");
        } else {
            this.processors.add(type);
        }
    }


    public List<String> getProcessors() {
        return processors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
