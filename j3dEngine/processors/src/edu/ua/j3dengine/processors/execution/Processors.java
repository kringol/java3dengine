package edu.ua.j3dengine.processors.execution;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Processors {

    @XmlElement
    private List<ProcessorGroup> processorGroups;


    public Processors(List<ProcessorGroup> processorGroups) {
        this.processorGroups = processorGroups;
    }


    public List<ProcessorGroup> getProcessorGroups() {
        return processorGroups;
    }

    public void setProcessorGroups(List<ProcessorGroup> processorGroups) {
        this.processorGroups = processorGroups;
    }
}
