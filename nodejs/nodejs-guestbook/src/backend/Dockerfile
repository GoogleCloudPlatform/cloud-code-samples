# Use base node 12-slim image from Docker hub
FROM node:12-slim

WORKDIR /backend

# Copy package.json and install dependencies
COPY package*.json ./
RUN npm install

# Copy rest of the application csource code
COPY . .

# Run app.js
ENTRYPOINT ["node", "app.js"]
