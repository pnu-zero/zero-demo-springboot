docker rm -f pnu-cloud-mariadb-dev pnu-cloud-redis-dev
docker run -p 3306:3306 --name zero-mariadb-dev -e MARIADB_ROOT_PASSWORD=rootroot -d mariadb
docker run -it -d -p 127.0.0.1:6379:6379 --name zero-redis-dev redis:4.0-alpine
