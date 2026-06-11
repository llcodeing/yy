# 使用OpenJDK 17 官方镜像
FROM yuhuitongxing.tencentcloudcr.com/base/eclipse-temurin:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 将打包好的 JAR 复制到镜像中
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# 暴露应用端口
EXPOSE 8080

# 启动命令
ENTRYPOINT ["java","-jar","app.jar"]