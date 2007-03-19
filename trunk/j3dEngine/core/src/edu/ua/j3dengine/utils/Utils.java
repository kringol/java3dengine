package edu.ua.j3dengine.utils;

import java.util.Map;
import java.util.HashMap;


public class Utils {

    public static final String SEVERITY_PROPERTY = "j3dengine.log.severity";

    private static LogSeverity severity = LogSeverity.DEBUG;


    private Utils() {
    }

    public static void logDebug(String msg){
        if (severity.weight() <= LogSeverity.DEBUG.weight()){
            System.out.println("[DEBUG]: "+msg);
        }
    }


    public static enum LogSeverity {
        DEBUG{
            public int weight() {
                return 1;
            }},
        INFO{
            public int weight() {
                return 2;
            }},
        WARNING{
            public int weight() {
                return 3;
            }},
        SEVERE{
            public int weight() {
                return 4;
            }},
        FATAL{
            public int weight() {
                return 5;
            }};

        public abstract int weight();
    }

    static{

        String severityProperty = System.getProperty(SEVERITY_PROPERTY, null);
        LogSeverity severity = LogSeverity.DEBUG;
        if (severityProperty != null){
            try {
                severity = LogSeverity.valueOf(severityProperty.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("[LOG]: Error parsing value '"+severityProperty+"'. Setting log to '"+LogSeverity.DEBUG+"'.");
            }
        }

        Utils.severity = severity;
        System.out.println("[LOG] severity set to '" + severity + "'");

    }
}
