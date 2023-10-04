#!/bin/bash
echo "Waiting for Eureka to become available..."
while ! nc -z rabbitmq 5672; do sleep 3; done
echo "Eureka is available. Proceeding..."