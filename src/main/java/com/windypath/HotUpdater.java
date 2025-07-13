package com.windypath;

import cn.hutool.core.io.FileUtil;
import com.sun.tools.attach.VirtualMachine;
import com.windypath.redefine.ClassRedefineAgent;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
public class HotUpdater {

    public static boolean redefineClass(String classFilePath, String packageName, String className) {
        VirtualMachine vm = null;
        try {
            vm = VirtualMachine.attach(getPid());
            String agentPath = getJarPathByClass(ClassRedefineAgent.class);
            String param = packageName + "." + className + '@' + classFilePath + File.separator + className + ".class";
            vm.loadAgent(agentPath, param);
            return true;
        } catch (Throwable e) {
            log.error("server redefine class error: ", e);
        } finally {
            if (vm != null) {
                try {
                    vm.detach();
                } catch (Throwable e) {
                    log.error("server redefine class detach error: ", e);
                }
            }
        }

        return false;
    }

    public static String getPid() {
        return ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
    }

    public static String getJarPathByClass(Class<?> clazz) {
        String jarPath = clazz.getProtectionDomain().getCodeSource().getLocation().getPath();
        jarPath = FileUtil.normalize(jarPath);
        try {
            return URLDecoder.decode(jarPath, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}