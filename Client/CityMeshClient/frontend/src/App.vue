<template>
  <div style="font-family: Arial, Helvetica, sans-serif; margin: 2rem;">
    <h1>🚁 CityMesh - Plateforme de Drones</h1>
    <Drones />
  </div>
</template>

<script>
import { defineComponent } from 'vue'
import Drones from './components/Drones.vue'

export default defineComponent({
  components: { Drones }
})
</script>

