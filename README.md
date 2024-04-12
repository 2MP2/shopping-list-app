### Setting Up Local Environment

Follow these steps to set up the local environment for the Shopping List App:

1. Navigate to the environment configuration directory:
    ```
    cd shopping-list-app/environment-configuration
    ```

2. Run the following command to build and run the Docker containers in detached mode:
    ```
    docker-compose up --build -d
    ```

3. Wait until the process of building and running containers is completed. You can check the status of containers using the following command:
    ```
    docker ps
    ```

4. Once the containers are up and running, open a web browser and go to the following address:
    ```
    http://localhost:80
    ```

You should now be able to access the Shopping List App.
