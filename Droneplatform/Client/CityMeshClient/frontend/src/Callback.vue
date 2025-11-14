<template>
  <div class="login-page">
    <div class="login-card">
      <h1>🚁 CityMesh</h1>
      <p class="subtitle">Verifying authentication...</p>
      <p v-if="error" class="error">{{ error }}</p>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      error: '',
    }
  },
  async mounted() {
    // This is the OIDC callback logic from PDF slide 32
    const params = new URLSearchParams(window.location.search);
    const code = params.get('code');

    if (!code) {
      this.error = 'No authorization code found.';
      return;
    }

    const verifier = sessionStorage.getItem('pkce_verifier');
    if (!verifier) {
      this.error = 'No PKCE verifier found.';
      return;
    }

    // Exchange code for token
    try {
      const tokenParams = new URLSearchParams({
        grant_type: 'authorization_code',
        code: code,
        redirect_uri: 'http://localhost/callback',
        client_id: 'my-client',
        code_verifier: verifier
      });

      // Nginx will proxy /oauth2/token to http://app:8081/oauth2/token
      const response = await fetch('/oauth2/token', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: tokenParams.toString()
      });

      if (!response.ok) {
        throw new Error('Failed to exchange token');
      }

      const tokens = await response.json();

      // Store the token and redirect
      localStorage.setItem('access_token', tokens.access_token);
      sessionStorage.removeItem('pkce_verifier');

      // Redirect to the main app
      this.$router.push('/');

    } catch (err) {
      this.error = 'Failed to fetch token. Please try logging in again.';
      console.error(err);
      localStorage.removeItem('access_token');
      sessionStorage.removeItem('pkce_verifier');
    }
  }
}
</script>

<style scoped>
/* Scoped styles from app.vue */
.login-page { display: flex; align-items: center; justify-content: center; min-height: 100vh; padding: 20px; }
.login-card { background: white; padding: 40px; border-radius: 15px; max-width: 400px; width: 100%; box-shadow: 0 20px 60px rgba(0,0,0,0.3); }
.login-card h1 { font-size: 2.5em; color: #667eea; text-align: center; margin-bottom: 10px; }
.subtitle { text-align: center; color: #666; margin-bottom: 30px; }
.error { margin-top: 15px; color: #dc3545; text-align: center; font-weight: bold; }
</style>