# === 构建阶段 ===
FROM node:20 AS builder

WORKDIR /app

# 复制前端代码
COPY . .

# 安装依赖并构建生产包
RUN npm install && npm run build

# === 运行阶段 ===
FROM nginx:alpine

# 复制构建后的静态文件到 nginx
COPY --from=builder /app/dist /usr/share/nginx/html

# 可选：自定义 nginx 配置
# COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
