FROM eclipse-temurin:21-jre
LABEL authors="Harshal Gothriya"


RUN apt-update
CMD ["echo","This is my first image"]

ENTRYPOINT ["top", "-b"]