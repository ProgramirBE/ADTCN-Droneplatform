# CityMesh Drone Platform - Guide Complet

## 🚀 Démarrage Rapide

### Option 1 : Script automatique (Windows)

Dans le dossier `ADTCN`, double-cliquez sur `start.bat` ou lancez :

```bash
cd ADTCN
start.bat
```

Ce script va :
1. Builder l'application Spring Boot
2. Démarrer MySQL + phpMyAdmin + App Java (Docker Compose)
3. Importer automatiquement la base de données

### Option 2 : Manuel

```bash
# 1. Build
cd ADTCN
mvn clean package -DskipTests

# 2. Démarrer les containers
docker compose up -d

# 3. Attendre 20s puis importer la DB
docker exec -i mysql-db mysql -ucitymeshuser -pcitymeshpwd citymesh < src/main/resources/db.sql

# 4. Vérifier les logs
docker compose logs -f app
```

## 🌐 URLs

- **API Backend** : http://localhost:8081/api/drones
- **phpMyAdmin** : http://localhost:8082
  - User: `citymeshuser`
  - Pass: `citymeshpwd`
- **MySQL** : `localhost:3306` / `citymesh`

## 📱 Frontend Vue.js

```bash
cd Client/CityMeshClient/frontend
npm install
npm run dev
```

Le frontend sera sur http://localhost:5173

## 🔌 API REST Endpoints

### Drones
- `GET    /api/drones` - Liste tous les drones
- `GET    /api/drones/{id}` - Détails d'un drone
- `POST   /api/drones` - Créer un drone
- `PUT    /api/drones/{id}` - Modifier
- `DELETE /api/drones/{id}` - Supprimer

### Launchpads
- `GET    /api/launchpads` - Liste
- `GET    /api/launchpads/safe` - Launchpads sûrs uniquement
- `POST   /api/launchpads` - Créer
- `PUT    /api/launchpads/{id}` - Modifier
- `DELETE /api/launchpads/{id}` - Supprimer

### Users
- `GET    /api/users` - Liste
- `GET    /api/users/{id}` - Détails
- `POST   /api/users` - Créer
- `PUT    /api/users/{id}` - Modifier
- `DELETE /api/users/{id}` - Supprimer

## 📊 Base de Données

### Tables
- `users` - Utilisateurs
- `drones` - Drones avec status/batterie
- `launchpads` - Plateformes de lancement
- `flights` - Historique des vols
- `reservations` - Réservations de launchpads
- `roles` - Rôles utilisateur
- `user_roles` - Association users<->roles
- `checkpoints` - Checkpoints de vol
- `maintenance_logs` - Logs de maintenance
- `no_fly_zones` - Zones interdites

### Données de test incluses
- 3 drones (Drone A, B, C)
- 2 launchpads (LP Noord, LP Zuid)
- 3 utilisateurs (pilot1, mech1, admin)

## 🛠️ Commandes Utiles

```bash
# Logs en temps réel
docker compose logs -f

# Redémarrer un service
docker compose restart app

# Arrêter tout
docker compose down

# Rebuild complet
mvn clean package -DskipTests && docker compose up -d --build

# Accéder au shell MySQL
docker exec -it mysql-db mysql -ucitymeshuser -pcitymeshpwd citymesh
```

## 🔧 Configuration

### application.properties
```properties
server.port=8081
spring.datasource.url=jdbc:mysql://db:3306/citymesh
spring.datasource.username=citymeshuser
spring.datasource.password=citymeshpwd
spring.jpa.hibernate.ddl-auto=none
```

### Docker Compose
- MySQL : port 3306
- phpMyAdmin : port 8082
- App Java : port 8081

## ✅ Vérifications

1. **MySQL fonctionne ?**
```bash
docker compose ps
# mysql-db doit être "healthy"
```

2. **App démarre ?**
```bash
docker compose logs app
# Cherchez "Started CityMeshApplication"
```

3. **API répond ?**
```bash
curl http://localhost:8081/api/drones
# Devrait retourner un JSON avec les drones
```

4. **Frontend connecte ?**
- Ouvrir http://localhost:5173
- La liste des drones doit s'afficher

## 🐛 Troubleshooting

### Port 3306 déjà utilisé
```bash
# Trouver et arrêter le processus
netstat -ano | findstr :3306
taskkill /PID <PID> /F
```

### DB non importée
```bash
# Importer manuellement
docker exec -i mysql-db mysql -ucitymeshuser -pcitymeshpwd citymesh < src/main/resources/db.sql
```

### App ne démarre pas
```bash
# Vérifier les logs
docker compose logs app

# Rebuild
mvn clean package -DskipTests
docker compose restart app
```

## 📝 Structure Projet

```
ADTCN/
├── src/main/java/be/odisee/citymesh/
│   ├── entity/          # Entités JPA (User, Drone, etc.)
│   ├── repository/      # Repositories Spring Data
│   ├── controller/      # REST Controllers
│   └── SecurityConfig.java
├── src/main/resources/
│   ├── application.properties
│   └── db.sql          # Dump SQL
├── docker-compose.yml
└── start.bat           # Script de démarrage

Client/CityMeshClient/frontend/
├── src/
│   ├── components/     # Composants Vue
│   └── App.vue
├── vite.config.js
└── package.json
```

## 🎯 Objectifs Atteints

✅ REST API backend avec MySQL  
✅ Frontend Vue.js qui consomme l'API  
✅ Application dans Docker containers  
✅ MySQL + phpMyAdmin + App Java  
✅ Persistance MySQL (pas H2)  
✅ CRUD complet sur Drones/Launchpads/Users  

## 📞 Support

Si problème, vérifier :
1. Docker Desktop est démarré
2. Ports 3306, 8081, 8082 sont libres
3. `mvn clean package` sans erreur
4. `docker compose up -d` sans erreur
5. DB importée avec succès

