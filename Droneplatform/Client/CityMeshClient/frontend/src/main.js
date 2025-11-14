import { createApp } from 'vue'
import { createRouter, createWebHistory } from 'vue-router'

// 1. Import your components
import App from './App.vue'
import Callback from './Callback.vue'

// 2. Define your routes
const routes = [
    {
        path: '/',
        name: 'Home',
        component: App // App.vue is your main dashboard component
    },
    {
        path: '/callback', // This is the OIDC redirect URL
        name: 'Callback',
        component: Callback // The component that handles the token exchange
    }
]

// 3. Create the router instance
const router = createRouter({
    history: createWebHistory(), // Use browser history mode
    routes,
})

// 4. Create the app, tell it to use the router, and mount it
const app = createApp(App) // Use App as the root, router-view will swap components
app.use(router)
app.mount('#app')