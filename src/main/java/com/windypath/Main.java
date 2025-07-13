package com.windypath;

import groovy.lang.GroovyShell;
import io.javalin.Javalin;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    private static final GroovyShell groovyShell = new GroovyShell();
    public static void main(String[] args) {
        Javalin.create().get("/getNumber", ctx -> { //获取数字
            int number = MyClass.getNumber();
            ctx.result("getNumber: " + number);
        }).post("/redefine", ctx -> {
            String classFilePath = ctx.queryParam("classFilePath");
            String packageName = ctx.queryParam("packageName");
            String className = ctx.queryParam("className");

            log.info("start redefine class : classFilePath={}, packageName={}, className={}", classFilePath, packageName, className);
            boolean result = false;
            if (HotUpdater.redefineClass(classFilePath, packageName, className)) {
                log.info("redefine class success");
                result = true;
            } else {
                log.error("redefine class fail");
            }

            ctx.result("redefine class result: " + result);
        }).post("groovy", ctx -> {
            String groovyScript = ctx.queryParam("groovyScript");
            groovyShell.getClassLoader().clearCache();
            groovyShell.evaluate(groovyScript);
            ctx.result("groovy execute finish");
        }).start(8080);
        log.info("Server started!");
    }
}