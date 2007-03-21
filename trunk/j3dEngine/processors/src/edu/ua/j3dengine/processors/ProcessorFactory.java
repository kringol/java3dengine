package edu.ua.j3dengine.processors;

import edu.ua.j3dengine.processors.rendering.RenderingProcessor;

public class ProcessorFactory {

    public static Processor createProcessor(String type) throws ProcessorValidationException {

        if (InputProcessor.TYPE.equals(type)) {
            return new InputProcessor();
        }
        if (GameLogicProcessor.TYPE.equals(type)) {
            return new GameLogicProcessor();
        }
        if (RenderingProcessor.TYPE.equals(type)) {
            return new RenderingProcessor();
        }

        throw new ProcessorValidationException("Process of type: '" + type + "' does not exist");
    }

}
