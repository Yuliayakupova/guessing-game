#!/bin/bash

if lsof -i :8080 -t >/dev/null ; then
  echo "Port 8080 is in use, killing process..."
  lsof -i :8080 -t | xargs kill -9
  sleep 2
fi

echo "Starting frontend..."
cd dist/frontend || exit 1
npx serve -s . &
FRONT_PID=$!
cd ../..

echo "Starting backend..."
cd dist || exit 1
java -jar app.jar &
BACK_PID=$!
cd ..

function wait_for_server() {
  local url=$1
  local max_attempts=20
  local attempt=0
  echo "⏳ Waiting for $url..."
  until curl -s "$url" > /dev/null; do
    if (( attempt >= max_attempts )); then
      echo "❌ Server $url did not start in time"
      kill $FRONT_PID $BACK_PID
      exit 1
    fi
    attempt=$((attempt+1))
    sleep 2
  done
  echo "✅ $url is up"
}

wait_for_server http://localhost:3000
wait_for_server http://localhost:8080/api/health

echo "Servers are running. Press Ctrl+C to stop."

trap "echo 'Stopping servers...'; kill $FRONT_PID $BACK_PID; exit 0" SIGINT SIGTERM

wait
