CityMesh Drone Platform handleiding.

Start met start.bat in ADTCN map of ga naar map en run mvn clean package -DskipTests dan docker compose up -d wacht 20 seconden en run docker exec -i mysql-db mysql -ucitymeshuser -pcitymeshpwd citymesh < src/main/resources/db.sql.

Check logs met docker compose logs -f app.

URLs zijn localhost:8081/api/drones voor API, localhost:8082 voor phpMyAdmin met user citymeshuser pass citymeshpwd, MySQL op localhost:3306.

Voor frontend ga naar Client/CityMeshClient/frontend run npm install dan npm run dev op localhost:5173.

API endpoints zijn GET/POST/PUT/DELETE voor /api/drones /api/launchpads /api/users.

Database heeft users drones launchpads flights reservations roles user_roles checkpoints maintenance_logs no_fly_zones tabellen met 3 test drones 2 launchpads 3 users.

Handige commandes zijn docker compose logs -f voor logs docker compose restart app voor herstart docker compose down om te stoppen.

Config gebruikt server.port 8081 MySQL op db:3306.

Troubleshoot door port 3306 te checken met netstat -ano findstr :3306 en eventueel taskkill /PID /F.

Als database niet geimporteerd run import commando handmatig.

Project structuur is ADTCN/src/main/java/be/odisee/citymesh met entity repository controller mappen en SecurityConfig plus resources met application.properties en db.sql.

Client map heeft Vue frontend.

Alles draait in Docker containers met MySQL phpMyAdmin en Java app.