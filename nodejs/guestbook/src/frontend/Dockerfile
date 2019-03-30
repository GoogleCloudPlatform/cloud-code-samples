FROM docker.io/node:8

WORKDIR /frontend

COPY package*.json ./

RUN npm install

COPY . .

EXPOSE 8080

CMD ["npm", "--inspect=9229", "start"]