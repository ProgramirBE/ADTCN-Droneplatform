import axios from 'axios'

const api = axios.create({
  // The baseURL is now / because Nginx is the entry point
  // We will call /api/drones, /api/users, etc.
  baseURL: '/',
  timeout: 10000
})

// --- ADD THIS INTERCEPTOR ---
// This code runs before every single request
api.interceptors.request.use(
    (config) => {
      const token = localStorage.getItem('access_token');
      if (token) {
        // As shown in PDF slide 24
        config.headers['Authorization'] = `Bearer ${token}`;
      }
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
);
// --- END INTERCEPTOR ---

export default {
  // Auth functions are GONE. OIDC handles this.

  // Drones
  getDrones: () => api.get('/api/drones'),
  getDroneById: (id) => api.get(`/api/drones/${id}`),
  createDrone: (drone) => api.post('/api/drones', drone),
  updateDrone: (id, drone) => api.put(`/api/drones/${id}`, drone),
  deleteDrone: (id) => api.delete(`/api/drones/${id}`),

  // Users
  getUsers: () => api.get('/api/users'),
  getUserById: (id) => api.get(`/api/users/${id}`),

  // Launchpads
  getLaunchpads: () => api.get('/api/launchpads'),
  getAvailableLaunchpads: () => api.get('/api/reservations/available'),
  getSafeLaunchpads: () => api.get('/api/launchpads/safe'),

  // Reservations
  getReservations: () => api.get('/api/reservations'),
  getReservationsByUser: (userId) => api.get(`/api/reservations/user/${userId}`),
  getReservationsByLaunchpad: (launchpadId) => api.get(`/api/reservations/launchpad/${launchpadId}`),
  createReservation: (reservation) => api.post('/api/reservations', reservation),
  cancelReservation: (id) => api.put(`/api/reservations/${id}/cancel`),
  deleteReservation: (id) => api.delete(`/api/reservations/${id}`),

  // Mechanic operations
  updateDroneStatus: (droneId, userId, status) => api.put(`/api/mechanic/drone/${droneId}/status`, { userId, status }),
  updateDroneBattery: (droneId, userId, batteryLevel) => api.put(`/api/mechanic/drone/${droneId}/battery`, { userId, batteryLevel }),
  resetDrone: (droneId, userId) => api.put(`/api/mechanic/drone/${droneId}/reset`, { userId })
}