<template>
  <div id="app">
    <!-- OIDC Login Button -->
    <div v-if="!isLoggedIn" class="login-page">
      <div class="login-card">
        <h1>🚁 CityMesh</h1>
        <p class="subtitle">Droneplatform</p>
        <p style="text-align: center; margin-bottom: 20px;">Please log in to continue.</p>
        <button type="button" @click="handleLogin" :disabled="loading" class="btn-login">
          {{ loading ? 'Redirecting...' : 'Aanmelden' }}
        </button>
      </div>
    </div>

    <!-- Dashboard (Unchanged from your original) -->
    <div v-else>
      <header class="header">
        <h1>🚁 CityMesh Droneplatform</h1>
        <div class="user-bar">
          <!-- Decode user info from token -->
          <span>Welkom, <strong>{{ user ? (user.sub || user.fullname) : 'Gebruiker' }}</strong></span>
          <button @click="logout" class="btn-logout">Uitloggen</button>
        </div>
      </header>

      <div class="container">
        <!-- The rest of your dashboard UI (tabs, tables, etc.) is unchanged -->
        <div class="tabs">
          <button @click="tab = 'drones'" :class="{active: tab === 'drones'}">Drones</button>
          <button @click="tab = 'launchpads'" :class="{active: tab === 'launchpads'}">Launchpads</button>
          <button @click="tab = 'reservations'" :class="{active: tab === 'reservations'}">Reservaties</button>
          <button @click="tab = 'users'" :class="{active: tab === 'users'}">Users</button>
        </div>

        <!-- Drones -->
        <div v-if="tab === 'drones'" class="content">
          <h2>🚁 Drones ({{ drones.length }})</h2>
          <!-- Your drone table... -->
        </div>

        <!-- Launchpads -->
        <div v-if="tab === 'launchpads'" class="content">
          <h2>📍 Launchpads ({{ launchpads.length }})</h2>
          <!-- Your launchpad table... -->
        </div>

        <!-- Réservations -->
        <div v-if="tab === 'reservations'" class="content">
          <h2>📅 Mijn Reservaties</h2>
          <!-- Your reservations table and modal... -->
        </div>

        <!-- Users -->
        <div v-if="tab === 'users'" class="content">
          <h2>👥 Users ({{ users.length }})</h2>
          <!-- Your users table... -->
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from './services/api.js'

// --- PKCE Helper Functions (from PDF slide 33) ---
function base64url(bytes) {
  return btoa(String.fromCharCode(...bytes))
      .replace(/\+/g, '-')
      .replace(/\//g, '_')
      .replace(/=+$/, '');
}

async function createPkceChallenge(verifier) {
  const data = new TextEncoder().encode(verifier);
  const digest = await crypto.subtle.digest('SHA-256', data);
  return base64url(new Uint8Array(digest));
}

function createPkceVerifier() {
  const bytes = new Uint8Array(64);
  crypto.getRandomValues(bytes);
  return base64url(bytes);
}
// --- End PKCE ---

function decodeJwt(token) {
  try {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
      return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
    return JSON.parse(jsonPayload);
  } catch (e) {
    console.error("Failed to decode JWT", e);
    return null;
  }
}

export default {
  data() {
    return {
      isLoggedIn: false,
      user: null,
      loading: false,
      // The rest of your data properties are unchanged
      tab: 'drones',
      drones: [],
      // ...
    }
  },
  mounted() {
    // On page load, check if we have a token
    const token = localStorage.getItem('access_token');
    if (token) {
      this.isLoggedIn = true;
      this.user = decodeJwt(token); // Decode user info from token
      this.loadData();
    }
  },
  methods: {
    // NEW OIDC LOGIN METHOD
    async handleLogin() {
      this.loading = true;

      // 1. Create and store PKCE verifier
      const verifier = createPkceVerifier();
      sessionStorage.setItem('pkce_verifier', verifier);

      // 2. Create challenge
      const challenge = await createPkceChallenge(verifier);

      // 3. Build authorization URL (as in PDF slide 28)
      const params = new URLSearchParams({
        response_type: 'code',
        client_id: 'my-client',
        scope: 'openid profile api_access',
        redirect_uri: 'http://localhost/callback',
        code_challenge: challenge,
        code_challenge_method: 'S256',
      });

      // 4. Redirect browser to Spring Authorization Server
      // Nginx will proxy /oauth2/authorize to http://app:8081/oauth2/authorize
      window.location.href = `/oauth2/authorize?${params.toString()}`;
    },

    // NEW LOGOUT METHOD
    logout() {
      this.isLoggedIn = false
      this.user = null
      localStorage.removeItem('access_token')
      // Optional: Redirect to OIDC logout endpoint
      // window.location.href = '/logout';
    },

    // Your other methods (loadData, createRes, etc.) are unchanged
    async loadData() {
      // ...
    },
    // ...
  }
}
</script>

<style>
/* Add this for the new login button */
.btn-login {
  width: 100%;
  padding: 14px;
  background: linear-gradient(135deg, #667eea, #764ba2);
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
}
.btn-login:disabled {
  opacity: 0.6;
}

/* Your other styles are unchanged */
* { margin: 0; padding: 0; box-sizing: border-box; }
/* ... */
</style>