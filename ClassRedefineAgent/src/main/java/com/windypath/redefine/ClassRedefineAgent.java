package com.windypath.redefine;

import java.io.File;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.nio.file.Files;
public class ClassRedefineAgent {
    public static void agentmain(String arg, Instrumentation inst) throws Exception {
        String[] args = arg.split("@");
        String[] classes = args[0].split(",");
        String[] filePath = args[1].split(",");
        ClassDefinition[] definitions = new ClassDefinition[classes.length];

        for (int i = 0; i < classes.length; ++i) {
            Class<?> clazz = Class.forName(classes[i]);
            byte[] bytes = Files.readAllBytes((new File(filePath[i])).toPath());
            definitions[i] = new ClassDefinition(clazz, bytes);
        }

        inst.redefineClasses(definitions);
    }
}
