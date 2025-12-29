FROM hmctspublic.azurecr.io/base/java:21-distroless

COPY build/libs/cftlib-wa.jar /opt/app/

EXPOSE 9191
CMD [ "cftlib-wa.jar" ]