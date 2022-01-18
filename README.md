# VirtuDoc
## Development Setup
There are two ways to start working on VirtuDoc. The Docker method is recommened to avoid platform-specific issues, reduce the amount of configuration required to start the application, and to ensure that your development environment is as close as possible to the production environment.
### Docker Method (Preferred)
1. Download the source code using `git` or the GitHub CLI.
2. Install Docker and Docker Compose. [Docker Desktop](https://www.docker.com/products/docker-desktop) is the recommended installer for non-Linux systems. Instructions are located [here](https://github.com/CS4850Group5A/VirtuDoc/wiki/Installing-Docker-and-Docker-Compose).
3. In your terminal from the project directory, run `docker-compose up --build`.
4. Visit `localhost:8080` in your browser.
5. To stop the entire application stack, press `Ctrl-C`.

Once your local stack is working, it is recommened that you set up SSL, especially if you will be doing front-end work. The instructions are in the [wiki](https://github.com/CS4850Group5A/VirtuDoc/wiki/Setting-up-SSL). Once that is set up you can access the web app from your web browser by visiting `https://localhost`.

Unit tests should run automatically when you re-run docker-compose, but you can run them manually with `./mvnw test`. If you make a change to the source code, restart the container from the Docker Desktop GUI, no need to restart the entire stack.

### Local Method (Not Recommended)
1. Download the source code using `git` or the GitHub CLI.
2. Install Java 11 LTS SE and Maven (Linux user should use the `adoptjdk` version).
3. Set the environment variables that can be found in docker-compose.yml.
4. Install any needed databases (instructions will be added here in the future).
5. In your terminal from the project directory, run `./mvnw spring-boot:run`. Windows users should use `./mvnw.cmd`.
6. Visit `localhost:8080` in your browser.
7. To stop the web server, press `Ctrl-C`.