# 线上热更class和执行groovy脚本
# 使用方法(重载class)
启动后，在浏览器访问 http://localhost:8080/getNumber 查看当前数字：
```
getNumber: 1
```

然后调用post请求（参数如下 curl命令）:
```cmd
curl --location --request POST 'localhost:8080/redefine?classFilePath=testRedefineJar&packageName=com.windypath&className=MyClass'
```

再次访问 http://localhost:8080/getNumber 
此时为
```
getNumber: 2
```

# 使用方法(groovy脚本)
```cmd
curl --location --globoff --request POST 'localhost:8080/groovy?groovyScript=com.windypath.MyClass.numbers[0]%20%3D%20123%3Bcom.windypath.MyClass.numbers[1]%20%3D%20456%3B'
```
执行后，再次访问 http://localhost:8080/getNumber
此时为
```
getNumber: 123 / 456
```


# 如何编译ClassRedefineAgent jar包
在ClassRedefineAgent项目中，maven package后，在target/classes 目录下 执行
``` cmd
jar cvfm ../ClassRedefineAgent.jar {你的MANIFEST.MF目录位置}\MANIFEST.MF com/
```

得到jar包