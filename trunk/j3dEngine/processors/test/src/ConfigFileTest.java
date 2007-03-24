import junit.framework.TestCase;
import edu.ua.j3dengine.processors.GameLogicProcessor;
import edu.ua.j3dengine.processors.Processor;
import edu.ua.j3dengine.processors.rendering.RenderingProcessor;
import edu.ua.j3dengine.processors.input.InputProcessor;
import edu.ua.j3dengine.processors.execution.ProcessorLoopThread;
import edu.ua.j3dengine.processors.execution.Processors;
import edu.ua.j3dengine.processors.execution.ProcessorGroup;
import edu.ua.j3dengine.processors.execution.ProcessorLoopBuilder;
import edu.ua.j3dengine.core.World;
import edu.ua.j3dengine.core.mgmt.GameObjectManager;
import static edu.ua.j3dengine.utils.Utils.logDebug;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.JAXBException;
import java.io.StringWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ConfigFileTest extends TestCase {



    public ConfigFileTest() {
        super("ProcessorConfigTest");
    }

    public void testGenerateConfigFile() throws JAXBException, IOException {

        ProcessorGroup group1 = new ProcessorGroup("Group 1",9);

        Processor input = new InputProcessor();
        Processor render = new RenderingProcessor();
        Processor gameLogic = new GameLogicProcessor();

        group1.addProcessor(gameLogic);
        group1.addProcessor(render);

        ProcessorGroup group2 = new ProcessorGroup("Group 2",7);
        group2.addProcessor(input);

        //should be ignored
        group2.addProcessor(input);
        assertTrue(group2.getProcessors().size()==1);

        Processors processors = new Processors();
        processors.addProcessorGroup(group1);
        processors.addProcessorGroup(group2);

        JAXBContext ctx = ProcessorLoopBuilder.createJAXBContext();

        Marshaller m =  ctx.createMarshaller();

        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter writer = new StringWriter();

        m.marshal(processors, writer);

        System.out.println("writer.toString() = \n" + writer.toString());

        FileWriter fileWriter = new FileWriter(CONFIG_FILE);

        m.marshal(processors, fileWriter);

        fileWriter.close();

    }

    public void testReadConfigFile() throws JAXBException, IOException {
        List<ProcessorLoopThread> list = ProcessorLoopBuilder.buildProcessorLoops(CONFIG_FILE);
        assertTrue(!list.isEmpty());
        for (ProcessorLoopThread processorLoopThread : list) {
            System.out.println("processorLoopThread = " + processorLoopThread + " - ProcessorCount: " + processorLoopThread.getProcessors().size());
        }
    }

    private static final String CONFIG_FILE = "processors/test/processors.xml";


}
