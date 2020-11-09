FROM python:slim
ENV DEBIAN_FRONTEND noninteractive

EXPOSE 8181

RUN apt update && \
    apt install -y opus-tools ffmpeg libmagic-dev curl tar npm && \
    rm -rf /var/lib/apt/lists/*

RUN mkdir /botamusique
COPY requirements.txt /botamusique
WORKDIR /botamusique

RUN python3 -m venv venv && \
    venv/bin/pip install wheel && \
    venv/bin/pip install -r requirements.txt

COPY . /botamusique/
RUN ls /botamusique
RUN (cd /botamusique/web && npm install && npm run build)
RUN ./venv/bin/python ./scripts/translate_templates.py --lang-dir lang/ --template-dir templates/
RUN chmod +x entrypoint.sh

ENTRYPOINT [ "/botamusique/entrypoint.sh" ]
CMD ["venv/bin/python", "mumbleBot.py"]
