docker build . -t benkemannen:latest && sudo -u qwderunner docker run -p 8181:8181 -v /etc/cert/botamusique.pem:/etc/cert/botamusique.pem -v /var/music:/var/music benkemannen:latest 
