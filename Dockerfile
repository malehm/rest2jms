FROM airhacks/wildfly
LABEL MAINTAINER="Mario Lehmann"
COPY target/rest2jms.war ${DEPLOYMENT_DIR}
RUN ${WILDFLY_HOME}/bin/add-user.sh admin Admin#007 --silent
ENTRYPOINT ${WILDFLY_HOME}/bin/standalone.sh -b=0.0.0.0 -c standalone-full.xml -Djboss.bind.address.management=0.0.0.0
EXPOSE 9990