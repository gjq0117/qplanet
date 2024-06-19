# 基础镜像使用java8
FROM java:8
# 作者信息
MAINTAINER gjq0117@163.com
# 将jar包添加到容器并命名为app.jar
ADD planet-blog-1.0-SNAPSHOT.jar app.jar
# 暴露端口
EXPOSE 8000
EXPOSE 8010
#运行jar包
ENTRYPOINT ["nohup","java","-jar","app.jar","&"]
