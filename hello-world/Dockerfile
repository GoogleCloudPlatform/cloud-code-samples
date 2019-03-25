FROM docker.io/node:8

WORKDIR /hello-world

COPY package*.json ./

RUN npm install

COPY . .

EXPOSE 8080

CMD ["npm", "start"]  
