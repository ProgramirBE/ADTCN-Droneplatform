# Frontend Vue - CityMesh Drone Platform

Application Vue 3 + Vite pour la gestion de drones.

## Pré-requis
- Node.js 18+ et npm
- Backend Spring Boot sur http://localhost:8081
- Base MySQL avec données importées (voir instructions ci-dessous)

## Installation

```bash
cd Client/CityMeshClient/frontend
npm install
```

## Lancement (développement)

```bash
npm run dev
```

Le frontend sera disponible sur **http://localhost:5173** (ou 5174 si le port est occupé)

## Import de la base de données

Avant de lancer l'application, importez le dump SQL :

### Méthode 1 : Via phpMyAdmin (http://localhost:8082)
1. Accéder à http://localhost:8082
2. Connectez-vous (user: `citymeshuser`, pass: `citymeshpwd`)
3. Importez le fichier `ADTCN/src/main/resources/db.sql`

### Méthode 2 : Via Docker CLI
```bash
docker exec -i mysql-db mysql -ucitymeshuser -pcitymeshpwd citymesh < ADTCN/src/main/resources/db.sql
```

## Lancement complet avec Docker Compose

Dans le dossier `ADTCN` :

```bash
# Démarrer les containers (MySQL + phpMyAdmin + App Java)
docker compose up -d

# Vérifier les logs
docker compose logs -f app

# Arrêter
docker compose down
```

## API REST disponibles

- `GET /api/drones` - Liste tous les drones
- `GET /api/drones/{id}` - Détails d'un drone
- `POST /api/drones` - Créer un drone
- `PUT /api/drones/{id}` - Modifier un drone
- `DELETE /api/drones/{id}` - Supprimer un drone

- `GET /api/launchpads` - Liste des launchpads
- `GET /api/launchpads/safe` - Launchpads sûrs uniquement

- `GET /api/users` - Liste des utilisateurs

## Structure

- **Entities** : `User`, `Drone`, `Launchpad`, `Flight`, `Reservation`
- **Repositories** : JpaRepository pour chaque entité
- **Controllers** : REST API CRUD complètes

## Notes

- CSRF désactivé pour simplifier le développement
- CORS configuré pour accepter localhost:*
- Authentification désactivée temporairement (à activer en production)

