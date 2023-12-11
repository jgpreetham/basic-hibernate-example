FROM ubuntu:jammy

ARG POSTGRES_HOST
ARG POSTGRES_PORT
ARG POSTGRES_DATABASE
ARG POSTGRES_USER
ARG POSTGRES_PASSWORD

WORKDIR /root
COPY . /root

RUN apt update -qq && apt install -y --no-install-recommends \
    openjdk-8-jdk maven

RUN sed -i "s/POSTGRES_HOST/${POSTGRES_HOST}/g" src/main/resources/hibernate.cfg.xml && \
    sed -i "s/POSTGRES_PORT/${POSTGRES_PORT}/g" src/main/resources/hibernate.cfg.xml && \
    sed -i "s/POSTGRES_DATABASE/${POSTGRES_DATABASE}/g" src/main/resources/hibernate.cfg.xml && \
    sed -i "s/POSTGRES_USER/${POSTGRES_USER}/g" src/main/resources/hibernate.cfg.xml && \
    sed -i "s/POSTGRES_PASSWORD/${POSTGRES_PASSWORD}/g" src/main/resources/hibernate.cfg.xml

RUN mvn dependency:copy-dependencies && \
    apt remove  -y openjdk-11-jre-headless

ENTRYPOINT ["./run_tests.sh"]
