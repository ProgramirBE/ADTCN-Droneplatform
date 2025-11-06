Frontend Vue CityMesh Drone Platform.

Application Vue 3 + Vite voor dronebeheer.

Vereisten zijn Node.js 18+ en npm, backend Spring Boot op http://localhost:8081 en MySQL database met geïmporteerde data.

Installatie: ga naar Client/CityMeshClient/frontend en voer npm install uit.

Start in ontwikkelmodus met npm run dev.

Frontend is bereikbaar op http://localhost:5173 of op 5174 als de poort bezet is.

Voor database importeer dump bestand.

Methode 1: via phpMyAdmin op http://localhost:8082, log in met user citymeshuser en pass citymeshpwd en importeer ADTCN/src/main/resources/db.sql.

Methode 2: via Docker CLI met docker exec -i mysql-db mysql -ucitymeshuser -pcitymeshpwd citymesh < ADTCN/src/main/resources/db.sql.

Volledige start met Docker Compose: ga naar ADTCN map, voer docker compose up -d uit voor MySQL phpMyAdmin en Java app, check logs met docker compose logs -f app en stop alles met docker compose down.

Beschikbare API endpoints zijn GET/POST/PUT/DELETE voor /api/drones en varianten, GET /api/launchpads en /api/launchpads/safe, en GET /api/users.

Structuur bevat Entities zoals User Drone Launchpad Flight Reservation, Repositories met JpaRepository, en Controllers met volledige CRUD REST API.

Notities: CSRF is uitgeschakeld voor eenvoud, CORS staat localhost:* toe, en authenticatie is tijdelijk uitgeschakeld maar moet in productie weer aan.