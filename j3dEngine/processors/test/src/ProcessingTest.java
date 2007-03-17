import junit.framework.TestCase;
import edu.ua.j3dengine.processors.GameLogicProcessor;
import edu.ua.j3dengine.processors.execution.ProcessorLoopThread;

public class ProcessingTest extends TestCase {
    

    public ProcessingTest(){
        super("ProcessingTest");
    }

    public void testBasicLoop(){
        ThreadGroup group = new ThreadGroup("ProcessingGroup");

        GameLogicProcessor gameLogicProcessor = GameLogicProcessor.getInstance();
        gameLogicProcessor.initialize();

        ProcessorLoopThread thread = ProcessorLoopThread.create(group,
                "MainLoopThread",
                Thread.MAX_PRIORITY,
                gameLogicProcessor);

        thread.start();

        thread.deactivate();
    }


}
