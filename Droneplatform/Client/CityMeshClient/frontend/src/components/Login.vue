<template>
  <section>
    <h2>Login</h2>
    <form @submit.prevent="doLogin">
      <div>
        <input v-model="username" placeholder="Username" />
      </div>
      <div>
        <input v-model="password" type="password" placeholder="password" />
      </div>
      <div style="margin-top:8px">
        <button type="submit">Login</button>
      </div>
    </form>
    <div v-if="error" style="color:crimson; margin-top:6px">{{ error }}</div>
  </section>
</template>

<script>
import { ref } from 'vue'

export default {
  props: [],
  emits: ['login-success'],
  setup(_, { emit }) {
    const username = ref('citymeshuser')
    const password = ref('citymeshpwd')
    const error = ref(null)

    async function doLogin() {
      error.value = null
      const form = new URLSearchParams()
      form.append('username', username.value)
      form.append('password', password.value)

      try {
        const res = await fetch('/login', {
          method: 'POST',
          headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
          body: form,
          credentials: 'include'
        })

        // Spring Security renvoie une redirection vers /drones en cas de succès.
        if (res.ok || res.redirected || res.status === 302) {
          emit('login-success')
        } else if (res.status === 401 || res.status === 403) {
          error.value = 'Login Invalid'
        } else {
          error.value = 'Server Error: ' + res.status
        }
      } catch (e) {
        error.value = 'Network Error: ' + e.message
      }
    }

    return { username, password, error, doLogin }
  }
}
</script>

