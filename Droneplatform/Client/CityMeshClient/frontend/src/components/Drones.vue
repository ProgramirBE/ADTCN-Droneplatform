<template>
  <section>
    <h2>Lijst van drones</h2>
    <button @click="reload" style="margin-bottom: 1rem;">🔄 Actualiser</button>
    <div v-if="loading">Loading...</div>
    <div v-else-if="error" style="color:crimson">{{ error }}</div>
    <table v-else style="width:100%; border-collapse: collapse;">
      <thead>
        <tr style="background: #f0f0f0;">
          <th style="border:1px solid #ccc; padding:8px;">ID</th>
          <th style="border:1px solid #ccc; padding:8px;">Naam</th>
          <th style="border:1px solid #ccc; padding:8px;">Model</th>
          <th style="border:1px solid #ccc; padding:8px;">Statut</th>
          <th style="border:1px solid #ccc; padding:8px;">Batterij (%)</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="d in drones" :key="d.id">
          <td style="border:1px solid #ccc; padding:8px;">{{ d.id }}</td>
          <td style="border:1px solid #ccc; padding:8px;">{{ d.name }}</td>
          <td style="border:1px solid #ccc; padding:8px;">{{ d.model }}</td>
          <td style="border:1px solid #ccc; padding:8px;">{{ d.status }}</td>
          <td style="border:1px solid #ccc; padding:8px;">{{ d.batteryLevel }}</td>
        </tr>
      </tbody>
    </table>
    <div v-if="!loading && drones.length === 0" style="margin-top:1rem;">Geen gevonden drones.</div>
  </section>
</template>

<script>
import { ref, onMounted } from 'vue'

export default {
  setup() {
    const drones = ref([])
    const error = ref(null)
    const loading = ref(false)

    async function load() {
      error.value = null
      loading.value = true
      try {
        const res = await fetch('/api/drones')
        if (res.ok) {
          drones.value = await res.json()
        } else {
          error.value = 'Error: ' + res.status
        }
      } catch (e) {
        error.value = 'Error Network: ' + e.message
      } finally {
        loading.value = false
      }
    }

    function reload() {
      load()
    }

    onMounted(load)

    return { drones, error, loading, reload }
  }
}
</script>

