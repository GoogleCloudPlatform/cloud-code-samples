FROM docker.io/node:8

WORKDIR /backend

COPY package*.json ./

RUN npm install

COPY . .

CMD ["npm", "--inspect=9229", "start"]
