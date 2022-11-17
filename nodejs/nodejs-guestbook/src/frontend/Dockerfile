# Use base node 18-alpine image from Docker hub
FROM node:19-alpine

WORKDIR /frontend

# Copy package.json and install dependencies
COPY package*.json ./
RUN npm install

# Copy rest of the application csource code
COPY . .

# Run app.js
ENTRYPOINT ["node", "app.js"]
