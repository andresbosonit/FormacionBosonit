#!/bin/sh
# wait-for-eureka.sh

echo "Waiting for Eureka to become available..."

while ! nc -z netflix-eureka-naming-server 8761; do
  sleep 1
done

echo "Eureka is available. Proceeding..."