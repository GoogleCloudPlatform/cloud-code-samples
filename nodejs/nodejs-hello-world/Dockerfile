# Use base node 12-slim image from Docker hub
FROM node:12-slim

WORKDIR /hello-world

# Copy package.json and install dependencies
COPY package*.json ./
RUN npm install

# Copy rest of the application csource code
COPY . .

# Run index.js with debugging port when container launches
ENTRYPOINT ["node", "--inspect=9229", "src/index.js"]
