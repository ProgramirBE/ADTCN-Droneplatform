# 🚁 CityMesh Droneplatform - Guide de démarrage

## 📋 Prérequis

- Docker Desktop installé et démarré
- Node.js 18+ et npm

## 🚀 Lancement de l'application complète

### 1️⃣ Démarrer le backend (Spring Boot + MySQL)

```powershell
# Aller dans le dossier du projet
cd C:\droneplatform\ADTCN-Droneplatform

# Démarrer Docker Compose (MySQL + phpMyAdmin + App Java)
docker-compose up -d

# Vérifier que les containers tournent
docker ps
```

Vous devriez voir 3 containers :
- `epbva-cucumber-app` (port 8081) - Application Spring Boot
- `mysql-db` (port 3306) - Base de données MySQL
- `phpmyadmin` (port 8082) - Interface phpMyAdmin

### 2️⃣ Démarrer le frontend (Vue.js)

```powershell
# Ouvrir un nouveau terminal
cd C:\droneplatform\ADTCN-Droneplatform\Client\CityMeshClient\frontend

# Installer les dépendances (première fois seulement)
npm install

# Lancer le serveur de développement
npm run dev
```

## 🌐 URLs d'accès

| Service | URL | Description |
|---------|-----|-------------|
| **Frontend Vue.js** | http://localhost:5173 | Interface utilisateur |
| **API REST** | http://localhost:8081/api/drones | Backend Spring Boot |
| **phpMyAdmin** | http://localhost:8082 | Administration MySQL |

### Credentials phpMyAdmin
- **Serveur** : `db`
- **Username** : `citymeshuser`
- **Password** : `citymeshpwd`

## ✅ Vérifications

### Test API
```powershell
# Tester l'API drones
curl http://localhost:8081/api/drones

# Tester l'API users
curl http://localhost:8081/api/users

# Tester l'API launchpads
curl http://localhost:8081/api/launchpads
```

### Test Frontend
Ouvrir http://localhost:5173 dans le navigateur - vous devriez voir :
- ✅ L'interface CityMesh
- ✅ 3 onglets : Drones, Launchpads, Utilisateurs
- ✅ Données chargées depuis MySQL

## 🔧 Si ça ne marche pas

### Problème : Les containers ne démarrent pas

```powershell
# Arrêter et supprimer tous les containers
docker-compose down -v

# Redémarrer
docker-compose up -d

# Voir les logs
docker-compose logs -f
```

### Problème : L'API retourne 404

```powershell
# Rebuild l'application
cd C:\droneplatform\ADTCN-Droneplatform\ADTCN
mvn clean package -DskipTests

# Redémarrer le container
docker-compose restart app
```

### Problème : Le frontend ne charge pas les données

1. Vérifier que l'API fonctionne : http://localhost:8081/api/drones
2. Vérifier la console du navigateur (F12) pour voir les erreurs
3. Vérifier que le proxy Vite est configuré dans `vite.config.js`

## 📊 Structure des données

### Drones
```json
{
  "id": 1,
  "name": "Drone A",
  "model": "DJI X1",
  "status": "Vliegklaar",
  "batteryLevel": 100,
  "createdAt": "2025-11-05T14:14:26"
}
```

### Launchpads
```json
{
  "id": 1,
  "name": "LP Noord",
  "latitude": 50.855,
  "longitude": 4.35,
  "isSafe": true,
  "createdAt": "2025-11-05T14:14:26"
}
```

### Users
```json
{
  "id": 1,
  "username": "pilot1",
  "fullname": "Piloot Een",
  "email": "pilot1@example.com",
  "createdAt": "2025-11-05T14:14:26"
}
```

## 🛑 Arrêter l'application

```powershell
# Arrêter le frontend (Ctrl+C dans le terminal)

# Arrêter les containers Docker
cd C:\droneplatform\ADTCN-Droneplatform
docker-compose down
```

## 📝 Notes importantes

- ✅ H2 a été **complètement supprimé** du pom.xml
- ✅ L'application utilise **uniquement MySQL**
- ✅ Les données sont **persistées** dans MySQL
- ✅ CORS est configuré pour permettre les appels depuis le frontend
- ✅ Le frontend utilise un **proxy Vite** pour éviter les problèmes CORS

## 🎯 Objectifs atteints

- [x] Application Spring Boot monolithe
- [x] Persistance MySQL dans Docker
- [x] phpMyAdmin pour administrer la DB
- [x] API REST complète (GET, POST, PUT, DELETE)
- [x] Frontend Vue.js qui consomme l'API
- [x] Application fonctionne sur port 8081 dans Docker

## 🚀 Pour montrer au prof

1. Lancer `docker-compose up -d`
2. Attendre 30 secondes
3. Ouvrir http://localhost:5173
4. Montrer les 3 onglets (Drones, Launchpads, Users)
5. Ouvrir http://localhost:8082 (phpMyAdmin) pour montrer la DB
6. Montrer que les données viennent bien de MySQL

## 📞 Troubleshooting rapide

| Problème | Solution |
|----------|----------|
| Port 3306 occupé | `netstat -ano \| findstr :3306` puis tuer le processus |
| Port 8081 occupé | Vérifier qu'aucune autre app Spring Boot ne tourne |
| "Empty reply from server" | Attendre que Spring Boot démarre (30-40s) |
| Frontend ne charge rien | Vérifier `docker-compose logs app` |

