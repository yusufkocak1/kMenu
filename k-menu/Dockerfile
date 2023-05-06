# Base image
FROM node:16.14.0-alpine3.14

# Set working directory
WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy project files
COPY . .

# Build the app
RUN npm run build

# Expose port 8081
EXPOSE 8081

# Run the app
CMD ["npm", "run", "serve"]
