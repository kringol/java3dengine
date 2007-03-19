package edu.ua.j3dengine.processors.execution;

import edu.ua.j3dengine.processors.Processor;
import edu.ua.j3dengine.processors.ProcessorFactory;
import edu.ua.j3dengine.processors.ProcessorValidationException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ProcessorLoopBuilder {

    public static List<ProcessorLoopThread> buildProcessorLoops() throws FileNotFoundException, JAXBException {
        FileReader reader = new FileReader(CONFIG_FILE);

        Unmarshaller um = createJAXBContext().createUnmarshaller();
        Object o = um.unmarshal(reader);

        return processObject(o);
    }

    private static List<ProcessorLoopThread> processObject(Object o) {
        List<ProcessorLoopThread> result = new ArrayList<ProcessorLoopThread>();

        if (o instanceof List) {
            List<ProcessorGroup> processorGroups = (List<ProcessorGroup>) o;
            for (ProcessorGroup processorGroup : processorGroups) {

                String name = processorGroup.getName();
                int priority = processorGroup.getPriority();

                List<String> list = processorGroup.getProcessors();

                List<Processor> processors = buildProcessors(list);

                ThreadGroup threadGroup = new ThreadGroup(name);

                ProcessorLoopThread processorLoop = ProcessorLoopThread.create(threadGroup, name, priority, processors);
                result.add(processorLoop);
            }

        } else {
            throw new RuntimeException("Unable to load processors. Expected 'List', found '" + o.getClass() + "'");
        }

        return result;
    }

    private static List<Processor> buildProcessors(List<String> list) {

        List<Processor> processors = new LinkedList<Processor>();
        for (String s : list) {

            try {
                Processor processor = ProcessorFactory.createProcessor(s);
                processors.add(processor);
            } catch (ProcessorValidationException e) {
                e.printStackTrace();
            }
        }

        return processors;
    }

    private static JAXBContext createJAXBContext() throws JAXBException {
        return JAXBContext.newInstance(
                "edu.ua.j3dengine.processors.execution");
    }

    private static final String CONFIG_FILE = "conf/processors.xml";


}
