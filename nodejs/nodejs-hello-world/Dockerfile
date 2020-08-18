# Use base node 8 image from Docker hub
FROM node:8

WORKDIR /hello-world

# Copy package.json and install dependencies
COPY package*.json ./
RUN npm install

# Copy rest of the application csource code
COPY . .

# Run app.js
ENTRYPOINT ["node", "src/app.js"]
