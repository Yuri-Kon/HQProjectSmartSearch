# === 构建阶段 ===
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# 复制项目代码
COPY . .

# 构建 jar 包（跳过测试）
RUN mvn clean package -DskipTests

# === 运行阶段 ===
FROM eclipse-temurin:21-jdk

WORKDIR /app

# 从构建阶段复制生成的 jar
COPY --from=build /app/target/*.jar app.jar

# 暴露端口（根据你的 Spring Boot 配置）
EXPOSE 8080

# 启动服务
ENTRYPOINT ["java", "-jar", "app.jar"]
