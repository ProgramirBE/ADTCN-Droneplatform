<template>
  <div id="app">
    <!-- Page de Login -->
    <div v-if="!isLoggedIn" class="login-page">
      <div class="login-card">
        <h1>🚁 CityMesh</h1>
        <p class="subtitle">Droneplatform</p>

        <form @submit.prevent="login">
          <div class="form-group">
            <label>Gebruikersnaam</label>
            <input v-model="username" type="text" required placeholder="pilot1">
          </div>

          <div class="form-group">
            <label>Wachtwoord</label>
            <input v-model="password" type="password" required placeholder="pilot1pass">
          </div>

          <button type="submit" :disabled="loading">
            {{ loading ? 'Inloggen...' : 'Aanmelden' }}
          </button>

          <p v-if="error" class="error">{{ error }}</p>
        </form>

        <div class="hint">
          <strong>Test users:</strong> pilot1/pilot1pass, mech1/mech1pass, admin/adminpass
        </div>
      </div>
    </div>

    <!-- Dashboard -->
    <div v-else>
      <header class="header">
        <h1>🚁 CityMesh Droneplatform</h1>
        <div class="user-bar">
          <span>Welkom, <strong>{{ user.fullname }}</strong></span>
          <button @click="logout" class="btn-logout">Uitloggen</button>
        </div>
      </header>

      <div class="container">
        <div class="tabs">
          <button @click="tab = 'drones'" :class="{active: tab === 'drones'}">Drones</button>
          <button @click="tab = 'launchpads'" :class="{active: tab === 'launchpads'}">Launchpads</button>
          <button @click="tab = 'reservations'" :class="{active: tab === 'reservations'}">Reservaties</button>
          <button @click="tab = 'users'" :class="{active: tab === 'users'}">Users</button>
        </div>

        <!-- Drones -->
        <div v-if="tab === 'drones'" class="content">
          <h2>🚁 Drones ({{ drones.length }})</h2>
          <table v-if="drones.length">
            <thead>
              <tr>
                <th>ID</th>
                <th>Naam</th>
                <th>Model</th>
                <th>Status</th>
                <th>Batterij</th>
                <th v-if="isMechanic()">Actions</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="d in drones" :key="d.id">
                <td>{{ d.id }}</td>
                <td>{{ d.name }}</td>
                <td>{{ d.model }}</td>
                <td>
                  <span v-if="!editingDrone[d.id]" :class="'badge badge-' + d.status.toLowerCase().replace(/ /g, '-')">
                    {{ d.status }}
                  </span>
                  <select v-else v-model="droneEdits[d.id].status" class="edit-select">
                    <option value="Vliegklaar">Vliegklaar</option>
                    <option value="In Onderhoud">In Onderhoud</option>
                    <option value="In Gebruik">In Gebruik</option>
                    <option value="Wacht op Verzending">Wacht op Verzending</option>
                  </select>
                </td>
                <td>
                  <div v-if="!editingDrone[d.id]" class="battery">
                    <div :style="{width: d.batteryLevel + '%'}" :class="batteryClass(d.batteryLevel)"></div>
                    <span>{{ d.batteryLevel }}%</span>
                  </div>
                  <input v-else v-model.number="droneEdits[d.id].batteryLevel" type="number" min="0" max="100" class="edit-input">
                </td>
                <td v-if="isMechanic()">
                  <div class="action-buttons">
                    <button v-if="!editingDrone[d.id]" @click="startEditDrone(d)" class="btn-edit">✏️ Edit</button>
                    <template v-else>
                      <button @click="saveDrone(d.id)" class="btn-save">💾 Save</button>
                      <button @click="cancelEdit(d.id)" class="btn-cancel-edit">❌</button>
                    </template>
                    <button @click="resetDroneStatus(d.id)" class="btn-reset">🔄 Reset</button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        <!-- Launchpads -->
        <div v-if="tab === 'launchpads'" class="content">
          <h2>📍 Launchpads ({{ launchpads.length }})</h2>
          <table v-if="launchpads.length">
            <thead>
              <tr>
                <th>ID</th>
                <th>Naam</th>
                <th>Latitude</th>
                <th>Longitude</th>
                <th>Veilig</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="lp in launchpads" :key="lp.id">
                <td>{{ lp.id }}</td>
                <td>{{ lp.name }}</td>
                <td>{{ lp.latitude }}</td>
                <td>{{ lp.longitude }}</td>
                <td>{{ lp.isSafe ? '✅' : '⚠️' }}</td>
              </tr>
            </tbody>
          </table>
        </div>

        <!-- Réservations -->
        <div v-if="tab === 'reservations'" class="content">
          <h2>📅 Mijn Reservaties</h2>

          <button @click="showReservationModal = true" class="btn-primary" style="margin-bottom: 20px;">
            ➕ Niewe reservatie
          </button>

          <table v-if="reservations.length">
            <thead>
              <tr>
                <th>ID</th>
                <th>Launchpad</th>
                <th>Start</th>
                <th>Einde</th>
                <th>Statut</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="r in reservations" :key="r.id">
                <td>{{ r.id }}</td>
                <td>{{ r.launchpad.name }}</td>
                <td>{{ formatDate(r.startTime) }}</td>
                <td>{{ formatDate(r.endTime) }}</td>
                <td><span :class="'badge badge-' + r.status.toLowerCase()">{{ r.status }}</span></td>
                <td>
                  <button v-if="r.status === 'CONFIRMED'" @click="cancelRes(r.id)" class="btn-cancel">Cancen</button>
                </td>
              </tr>
            </tbody>
          </table>
          <p v-else>Geen reservatie</p>
        </div>

        <!-- Modal Nouvelle Réservation -->
        <div v-if="showReservationModal" class="modal" @click.self="showReservationModal = false">
          <div class="modal-content">
            <h3>📍 Niewe reservatie</h3>
            <form @submit.prevent="createRes">
              <div class="form-group">
                <label>Launchpad</label>
                <select v-model="newReservation.launchpadId" required>
                  <option value="">Select launchpad</option>
                  <option v-for="lp in availableLaunchpads" :key="lp.id" :value="lp.id">
                    {{ lp.name }} ({{ lp.latitude }}, {{ lp.longitude }})
                  </option>
                </select>
              </div>
              <div class="form-group">
                <label>Start</label>
                <input type="datetime-local" v-model="newReservation.startTime" required>
              </div>
              <div class="form-group">
                <label>Einde</label>
                <input type="datetime-local" v-model="newReservation.endTime" required>
              </div>
              <div style="display: flex; gap: 10px;">
                <button type="submit" class="btn-primary">Reserveer</button>
                <button type="button" @click="showReservationModal = false" class="btn-cancel">Cancel</button>
              </div>
            </form>
          </div>
        </div>

        <!-- Users -->
        <div v-if="tab === 'users'" class="content">
          <h2>👥 Users ({{ users.length }})</h2>
          <table v-if="users.length">
            <thead>
              <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Name</th>
                <th>Email</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="u in users" :key="u.id">
                <td>{{ u.id }}</td>
                <td><code>{{ u.username }}</code></td>
                <td>{{ u.fullname }}</td>
                <td>{{ u.email }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from './services/api.js'

export default {
  data() {
    return {
      isLoggedIn: false,
      username: '',
      password: '',
      user: null,
      loading: false,
      error: '',
      tab: 'drones',
      drones: [],
      launchpads: [],
      users: [],
      reservations: [],
      availableLaunchpads: [],
      showReservationModal: false,
      newReservation: {
        launchpadId: null,
        startTime: '',
        endTime: ''
      },
      editingDrone: {},
      droneEdits: {}
    }
  },
  mounted() {
    const saved = localStorage.getItem('user')
    if (saved) {
      this.user = JSON.parse(saved)
      this.isLoggedIn = true
      this.loadData()
    }
  },
  methods: {
    async login() {
      this.loading = true
      this.error = ''
      try {
        const res = await api.login({ username: this.username, password: this.password })
        if (res.data.success) {
          this.user = res.data.user
          this.isLoggedIn = true
          localStorage.setItem('user', JSON.stringify(this.user))
          this.loadData()
        }
      } catch (err) {
        this.error = err.response?.data?.error || 'Login failed'
      } finally {
        this.loading = false
      }
    },
    logout() {
      this.isLoggedIn = false
      this.user = null
      this.username = ''
      this.password = ''
      localStorage.removeItem('user')
    },
    async loadData() {
      try {
        const [d, lp, u] = await Promise.all([
          api.getDrones(),
          api.getLaunchpads(),
          api.getUsers()
        ])
        this.drones = d.data
        this.launchpads = lp.data
        this.users = u.data

        // Charger les réservations de l'utilisateur connecté
        if (this.user) {
          const res = await api.getReservationsByUser(this.user.id)
          this.reservations = res.data
        }

        // Charger les launchpads disponibles
        const avail = await api.getAvailableLaunchpads()
        this.availableLaunchpads = avail.data
      } catch (err) {
        console.error(err)
      }
    },
    async createRes() {
      try {
        await api.createReservation({
          userId: this.user.id,
          launchpadId: this.newReservation.launchpadId,
          startTime: this.newReservation.startTime,
          endTime: this.newReservation.endTime
        })
        this.showReservationModal = false
        this.newReservation = { launchpadId: null, startTime: '', endTime: '' }
        await this.loadData()
      } catch (err) {
        alert(err.response?.data?.error || 'Erreur lors de la réservation')
      }
    },
    async cancelRes(id) {
      if (confirm('Cancel reservation ?')) {
        try {
          await api.cancelReservation(id)
          await this.loadData()
        } catch (err) {
          alert('Error While canceling')
        }
      }
    },
    formatDate(dateStr) {
      if (!dateStr) return ''
      return new Date(dateStr).toLocaleString('fr-BE')
    },
    isMechanic() {
      if (!this.user || !this.user.roles) return false
      return this.user.roles.some(role => role.name === 'Mechanieker')
    },
    startEditDrone(drone) {
      this.editingDrone[drone.id] = true
      this.droneEdits[drone.id] = {
        status: drone.status,
        batteryLevel: drone.batteryLevel
      }
    },
    cancelEdit(droneId) {
      delete this.editingDrone[droneId]
      delete this.droneEdits[droneId]
    },
    async saveDrone(droneId) {
      try {
        const edits = this.droneEdits[droneId]
        const drone = this.drones.find(d => d.id === droneId)

        // Modifier le statut si changé
        if (edits.status !== drone.status) {
          await api.updateDroneStatus(droneId, this.user.id, edits.status)
        }

        // Modifier la batterie si changée
        if (edits.batteryLevel !== drone.batteryLevel) {
          await api.updateDroneBattery(droneId, this.user.id, edits.batteryLevel)
        }

        this.cancelEdit(droneId)
        await this.loadData()
      } catch (err) {
        alert(err.response?.data?.error || 'Erreur lors de la modification')
      }
    },
    async resetDroneStatus(droneId) {
      if (confirm('Réinitialiser ce drone (Status: Vliegklaar, Batterie: 100%) ?')) {
        try {
          await api.resetDrone(droneId, this.user.id)
          await this.loadData()
        } catch (err) {
          alert(err.response?.data?.error || 'Erreur lors de la réinitialisation')
        }
      }
    },
    batteryClass(level) {
      return level >= 70 ? 'high' : level >= 30 ? 'med' : 'low'
    }
  }
}
</script>

<style>
* { margin: 0; padding: 0; box-sizing: border-box; }
body { font-family: Arial, sans-serif; background: linear-gradient(135deg, #667eea, #764ba2); min-height: 100vh; }

.login-page { display: flex; align-items: center; justify-content: center; min-height: 100vh; padding: 20px; }
.login-card { background: white; padding: 40px; border-radius: 15px; max-width: 400px; width: 100%; box-shadow: 0 20px 60px rgba(0,0,0,0.3); }
.login-card h1 { font-size: 2.5em; color: #667eea; text-align: center; margin-bottom: 10px; }
.subtitle { text-align: center; color: #666; margin-bottom: 30px; }
.form-group { margin-bottom: 20px; }
.form-group label { display: block; margin-bottom: 8px; font-weight: bold; color: #333; }
.form-group input { width: 100%; padding: 12px; border: 2px solid #ddd; border-radius: 8px; font-size: 16px; }
.form-group input:focus { outline: none; border-color: #667eea; }
button[type="submit"] { width: 100%; padding: 14px; background: linear-gradient(135deg, #667eea, #764ba2); color: white; border: none; border-radius: 8px; font-size: 16px; font-weight: bold; cursor: pointer; }
button[type="submit"]:disabled { opacity: 0.6; cursor: not-allowed; }
.error { margin-top: 15px; color: #dc3545; text-align: center; font-weight: bold; }
.hint { margin-top: 20px; padding: 15px; background: #f8f9fa; border-radius: 8px; font-size: 0.9em; color: #666; }

.header { background: white; padding: 20px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); display: flex; justify-content: space-between; align-items: center; }
.header h1 { color: #667eea; font-size: 1.8em; }
.user-bar { display: flex; align-items: center; gap: 20px; }
.btn-logout { padding: 10px 20px; background: #dc3545; color: white; border: none; border-radius: 8px; cursor: pointer; font-weight: bold; }
.btn-logout:hover { background: #c82333; }

.container { max-width: 1200px; margin: 30px auto; padding: 0 20px; }
.tabs { display: flex; gap: 10px; margin-bottom: 30px; }
.tabs button { padding: 12px 24px; background: white; border: none; border-radius: 8px; cursor: pointer; font-weight: bold; color: #666; }
.tabs button.active { background: linear-gradient(135deg, #667eea, #764ba2); color: white; }

.content { background: white; padding: 30px; border-radius: 15px; box-shadow: 0 10px 30px rgba(0,0,0,0.2); }
.content h2 { margin-bottom: 20px; color: #333; }

table { width: 100%; border-collapse: collapse; }
thead { background: linear-gradient(135deg, #667eea, #764ba2); color: white; }
th, td { padding: 12px; text-align: left; }
tbody tr { border-bottom: 1px solid #eee; }
tbody tr:hover { background: #f8f9fa; }

.badge { padding: 5px 10px; border-radius: 12px; font-size: 0.85em; font-weight: bold; display: inline-block; }
.badge-vliegklaar { background: #d4edda; color: #155724; }
.badge-in-onderhoud { background: #fff3cd; color: #856404; }
.badge-in-gebruik { background: #d1ecf1; color: #0c5460; }

.battery { position: relative; width: 100px; height: 20px; background: #eee; border-radius: 10px; overflow: hidden; }
.battery div { height: 100%; transition: width 0.3s; }
.battery .high { background: #28a745; }
.battery .med { background: #ffc107; }
.battery .low { background: #dc3545; }
.battery span { position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); font-size: 0.8em; font-weight: bold; color: #333; }

code { background: #f4f4f4; padding: 3px 6px; border-radius: 4px; font-family: monospace; }

.btn-primary { padding: 10px 20px; background: #667eea; color: white; border: none; border-radius: 8px; cursor: pointer; font-weight: bold; }
.btn-primary:hover { background: #5568d3; }
.btn-cancel { padding: 8px 16px; background: #dc3545; color: white; border: none; border-radius: 6px; cursor: pointer; font-size: 0.9em; }
.btn-cancel:hover { background: #c82333; }

.modal { position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.5); display: flex; align-items: center; justify-content: center; z-index: 1000; }
.modal-content { background: white; padding: 30px; border-radius: 15px; max-width: 500px; width: 90%; }
.modal-content h3 { margin-bottom: 20px; color: #333; }
.modal-content .form-group { margin-bottom: 15px; }
.modal-content label { display: block; margin-bottom: 5px; font-weight: bold; color: #555; }
.modal-content input, .modal-content select { width: 100%; padding: 10px; border: 2px solid #ddd; border-radius: 6px; font-size: 14px; }
.modal-content input:focus, .modal-content select:focus { outline: none; border-color: #667eea; }

.badge-confirmed { background: #d4edda; color: #155724; }
.badge-cancelled { background: #f8d7da; color: #721c24; }

.action-buttons { display: flex; gap: 5px; flex-wrap: wrap; }
.btn-edit { padding: 6px 12px; background: #ffc107; color: #000; border: none; border-radius: 4px; cursor: pointer; font-size: 0.85em; }
.btn-save { padding: 6px 12px; background: #28a745; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 0.85em; }
.btn-cancel-edit { padding: 6px 12px; background: #6c757d; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 0.85em; }
.btn-reset { padding: 6px 12px; background: #17a2b8; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 0.85em; }
.edit-select, .edit-input { padding: 6px; border: 2px solid #667eea; border-radius: 4px; font-size: 0.9em; }
.edit-input { width: 80px; }
</style>

