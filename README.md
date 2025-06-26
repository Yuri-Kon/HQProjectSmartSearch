# 课程信息管理系统

本项目为“服务工程实践”课程的作业内容，使用 Docker Compose 管理，实现了前后端与数据库的一体化部署。

包含组件：

- 后端：Spring Boot 项目（端口 `8080`）
- 前端：静态网页服务（端口 `80`）
- 数据库：MySQL 8，初始建库与建表由 `init.sql` 自动完成

---

## 快速启动

### 环境要求

请先确保你本地已安装：

- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/install/)

### 启动步骤

1. 克隆或解压本项目
2. 进入项目目录，执行：

```bash
# 推荐首次构建 + 启动
docker-compose build
docker-compose up -d

# 后续可直接启动
docker-compose up -d
```

3. 关闭服务

```bash
docker-compose down
```

如果想删除数据库数据,可以运行:

```bash
docker-compose down -v
```

## 默认服务端口

| 服务     | 地址                                             |
| ------ | ---------------------------------------------- |
| 前端页面   | [http://localhost](http://localhost)           |
| 后端 API | [http://localhost:8080](http://localhost:8080) |
| 数据库    | 主机端口：3306（MySQL）                               |

## 数据库初始化说明

系统启动时自动执行 `init.sql`，完成以下操作：

- 创建数据库 hgd
- 创建用户 yurikon
- 初始表结构和测试数据（如有）

数据库连接配置已内置于后端环境变量中：

```env
SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/hgd
SPRING_DATASOURCE_USERNAME=yurikon
SPRING_DATASOURCE_PASSWORD=123456
```

## 示例账号

| 角色  | 账号       | 密码     |
| --- | -------- | ------ |
| 教师端 | panda | 123456 |
| 学生端 | GASSFY | 123456 |

## 项目结构说明

```csharp
your-project/
├── docker-compose.yml      # 服务定义文件
├── init.sql                # MySQL 初始化 SQL
├── HQ-project-back/        # 后端代码目录
├── HQFrontEnd/             # 前端代码目录
└── README.md               # 使用说明
```