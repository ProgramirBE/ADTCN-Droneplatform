import axios from 'axios'

const api = axios.create({
  baseURL: '/api',
  timeout: 10000
})

export default {
  // Auth
  login: (credentials) => api.post('/auth/login', credentials),
  logout: () => api.post('/auth/logout'),

  // Drones
  getDrones: () => api.get('/drones'),
  getDroneById: (id) => api.get(`/drones/${id}`),
  createDrone: (drone) => api.post('/drones', drone),
  updateDrone: (id, drone) => api.put(`/drones/${id}`, drone),
  deleteDrone: (id) => api.delete(`/drones/${id}`),

  // Users
  getUsers: () => api.get('/users'),
  getUserById: (id) => api.get(`/users/${id}`),

  // Launchpads
  getLaunchpads: () => api.get('/launchpads'),
  getAvailableLaunchpads: () => api.get('/reservations/available'),
  getSafeLaunchpads: () => api.get('/launchpads/safe'),

  // Reservations
  getReservations: () => api.get('/reservations'),
  getReservationsByUser: (userId) => api.get(`/reservations/user/${userId}`),
  getReservationsByLaunchpad: (launchpadId) => api.get(`/reservations/launchpad/${launchpadId}`),
  createReservation: (reservation) => api.post('/reservations', reservation),
  cancelReservation: (id) => api.put(`/reservations/${id}/cancel`),
  deleteReservation: (id) => api.delete(`/reservations/${id}`),

  // Mechanic operations
  updateDroneStatus: (droneId, userId, status) => api.put(`/mechanic/drone/${droneId}/status`, { userId, status }),
  updateDroneBattery: (droneId, userId, batteryLevel) => api.put(`/mechanic/drone/${droneId}/battery`, { userId, batteryLevel }),
  resetDrone: (droneId, userId) => api.put(`/mechanic/drone/${droneId}/reset`, { userId })
}

