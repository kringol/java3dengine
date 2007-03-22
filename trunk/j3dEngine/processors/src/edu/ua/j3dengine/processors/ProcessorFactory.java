package edu.ua.j3dengine.processors;

public class ProcessorFactory {

    public static Processor createProcessor(String className) throws ProcessorValidationException {

        try {
            Class<?> aClass = Class.forName(className);
            return (Processor) aClass.newInstance();
        } catch (ClassNotFoundException e) {
            throw new ProcessorValidationException("Process: '" + className + "' does not exist", e);
        } catch (IllegalAccessException e) {
            throw new ProcessorValidationException(e);
        } catch (InstantiationException e) {
            throw new ProcessorValidationException(e);
        }
    }
}
