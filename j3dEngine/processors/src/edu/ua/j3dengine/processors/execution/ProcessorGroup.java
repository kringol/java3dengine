package edu.ua.j3dengine.processors.execution;

import edu.ua.j3dengine.processors.Processor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;
import java.util.LinkedList;

@XmlRootElement
public class ProcessorGroup {

    @XmlAttribute
    private String name;

    @XmlAttribute
    private int priority;

    @XmlElement(name="processor")
    private List<String> processors;

    public ProcessorGroup() {
    }

    public ProcessorGroup(String name, int priority) {
        this.name = name;
        this.priority = priority;
        this.processors = new LinkedList<String>();
    }

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
        String id = processor.getId();
        if (this.processors.contains(id)) {
            System.out.println("Ignoring processor '" + processor + "' because it already exists on this group");
        } else {
            this.processors.add(id);
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
