FROM node:latest

WORKDIR /dashboard

COPY . .

RUN npm install -g npm@latest
RUN npm install -D @sveltejs/adapter-node
RUN npm install dotenv

RUN npm run build

CMD ["node", "-r", "dotenv/config", "build"]
