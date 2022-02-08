<template>
  <v-app>
    <!-- tool bar -->
    <v-app-bar app color="green" dark>
      <!-- title -->
      <v-toolbar-title>{{ appName }}</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn icon to="/">
        <v-icon>mdi-file-table-outline</v-icon>
      </v-btn>
      <v-btn icon to="/settings">
        <v-icon>mdi-cog</v-icon>
      </v-btn>
    </v-app-bar>
    <!-- main content -->
    <v-main>
      <v-container fluid>
        <!-- switched the router-view content up to the path -->
        <router-view></router-view>
      </v-container>
    </v-main>
    <!-- snack bar -->
    <v-snackbar v-model="snackbar" color="error">{{ errorMessage }}</v-snackbar>
  </v-app>
</template>
<script>
  import { mapState } from 'vuex'
  export default {
    name: 'App',
    data () {
    return {
      snackbar: false
    }
  },
    computed: mapState({
      appName: state => state.settings.appName,
      errorMessage: state => state.errorMessage
    }),
    // when errorMessage changed
    watch: {
    errorMessage () {
      // show the snack bar
      console.log('error message is called.')
      this.snackbar = true
    }
  },
    // exceuted once when App instance created
    beforeCreate () {
      this.$store.dispatch('loadSettings')
    }
  }
</script>