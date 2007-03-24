package edu.ua.j3dengine.processors.execution;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.LinkedList;

@XmlRootElement
public class Processors {

    @XmlElement(name="processorGroup")
    private List<ProcessorGroup> processorGroups;


    public Processors() {
        processorGroups = new LinkedList<ProcessorGroup>();
    }

    public Processors(List<ProcessorGroup> processorGroups) {
        this.processorGroups = processorGroups;
    }

    public void addProcessorGroup(ProcessorGroup group) {
        processorGroups.add(group);
    }


    public List<ProcessorGroup> getProcessorGroups() {
        return processorGroups;
    }

    public void setProcessorGroups(List<ProcessorGroup> processorGroups) {
        this.processorGroups = processorGroups;
    }
}
