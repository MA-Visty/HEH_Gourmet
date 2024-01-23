FROM openjdk:17
ARG JAR_FILE
ENV STRIPE_SECRET_KEY=INVALID
ENV DB_URL=INVALID
ENV DB_USERNAME=root
ENV DB_PASSWORD=#@heh_pass@#
LABEL authors="yato202010"
RUN mkdir "/opt/heh_gourmet/"
COPY $JAR_FILE /opt/heh_gourmet/app.jar

ENTRYPOINT ["java","-jar","/opt/heh_gourmet/app.jar"]