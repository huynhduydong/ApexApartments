FROM ubuntu:22.04

WORKDIR /opt/tomcat

RUN apt update -y && apt install -y openjdk-17-jdk wget unzip maven

# Đặt biến môi trường cho JAVA_HOME
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV TOMCAT_HOME=/opt/tomcat

# Tải và cài đặt Tomcat
RUN wget https://dlcdn.apache.org/tomcat/tomcat-8/v8.5.100/bin/apache-tomcat-8.5.100.tar.gz
RUN tar xzvf apache-tomcat-*.tar.gz -C /opt/tomcat --strip-components=1

COPY ./xml/* /opt/tomcat/conf/

# RUN mvn tomcat7:deploy
# COPY /opt/tomcat/webapps /opt/tomcat/webapps

# Cấu hình cổng để Tomcat lắng nghe
EXPOSE 8080

# Thiết lập lệnh khởi động Tomcat
CMD ["/opt/tomcat/bin/catalina.sh" ,"run"]
