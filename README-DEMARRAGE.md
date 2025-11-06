CityMesh Droneplatform handleiding.

Vereisten: Docker Desktop geïnstalleerd en actief, Node.js 18+ en npm.

Stap 1: start backend (Spring Boot + MySQL).  
Ga naar C:\droneplatform\ADTCN-Droneplatform en voer docker-compose up -d uit om MySQL, phpMyAdmin en de Java app te starten.  
Controleer met docker ps of drie containers draaien: epbva-cucumber-app op poort 8081, mysql-db op 3306 en phpmyadmin op 8082.

Stap 2: start frontend (Vue.js).  
Open een nieuwe terminal, ga naar C:\droneplatform\ADTCN-Droneplatform\Client\CityMeshClient\frontend, voer npm install uit en daarna npm run dev.

Toegangs-URLs:  
Frontend op http://localhost:5173  
API backend op http://localhost:8081/api/drones  
phpMyAdmin op http://localhost:8082 met login citymeshuser / citymeshpwd en server db.

Controle:  
Test API met curl http://localhost:8081/api/drones, /api/users of /api/launchpads.  
Frontend opent op http://localhost:5173 met drie tabs (Drones, Launchpads, Gebruikers) en data uit MySQL.

Problemen oplossen:  
Als containers niet starten, gebruik docker-compose down -v en daarna docker-compose up -d en controleer logs met docker-compose logs -f.  
Als API 404 geeft, ga naar ADTCN map, run mvn clean package -DskipTests en herstart met docker-compose restart app.  
Als frontend geen data laadt, controleer API op http://localhost:8081/api/drones, bekijk consolefouten (F12) en controleer proxy-instellingen in vite.config.js.

Data-structuur bevat Drones, Launchpads en Users met id, naam, status en tijdstempels.

Om de app te stoppen, druk Ctrl+C in frontend-terminal en voer in projectmap docker-compose down uit.

Belangrijk: H2 is volledig verwijderd, enkel MySQL wordt gebruikt, data wordt persistent opgeslagen, CORS is geconfigureerd en frontend gebruikt Vite proxy.

Bereikte doelen: Spring Boot monoliet met MySQL persistentie in Docker, phpMyAdmin voor beheer, volledige REST API, Vue.js frontend, alles draait via Docker op poort 8081.

Voor demonstratie: start docker-compose up -d, wacht 30 seconden, open http://localhost:5173, toon drie tabs, open phpMyAdmin op http://localhost:8082 en laat zien dat data uit MySQL komt.

Snelle probleemoplossing: bij poort 3306 gebruik netstat -ano | findstr :3306 en stop het proces, bij poort 8081 check of geen andere Spring Boot app draait, bij lege reactie even wachten tot app start, bij frontend die niets laadt controleer docker-compose logs app.