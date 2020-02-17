#查看数据库
启用dev环境 默认开启了h2console。application.yaml默认h2:console.enabled是false
```yaml
h2:
    console:
      settings:
        web-allow-others: false
      path: /h2-console
      enabled: false
```
开发环境，在run config里面，Active profiles选则dev，就可以开启dev配置，这样就可以访问h2控制台了。
线上环境，建议准备一份开启配置的yaml文件，通过单独的启动命令来临时打开配置，同时主要服务器的访问端口需要打开。

```
java -jar $APP_NAME --spring.config.location=./application-h2open.yaml
```

http://127.0.0.1:8090/h2-console
jdbc:h2:file:~/.halo/db/halo
admin
123456