#!/bin/bash

# Function to run a docker-compose file and wait for 10 seconds
run_compose() {
  local file=$1
  echo "Running docker-compose file: $file"
  docker-compose -f $file up -d
  echo "Waiting for 5 seconds..."
  sleep 5
}

# Paths to the docker-compose files
compose_files=(
  "./Docker/mono/docker-compose.postgres.yml"
  "./Docker/mono/docker-compose.pgadmin.yml"
  "./Docker/mono/docker-compose.mongo.yml"
)

# Run each docker-compose file
for file in "${compose_files[@]}"; do
  run_compose $file
done

# Ask if the user wants to start the main docker-compose file
read -p "Do you want to start the main docker-compose.yml? (y/n) " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]; then
  echo "Running main docker-compose file: ./docker-compose.yml"
  docker-compose -f ./docker-compose.yml up -d
else
  echo "Main docker-compose.yml not started."
fi
