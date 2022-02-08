<template>
  <div class="form-wrapper">
    <p>※This setting is unique for this device.</p>
    <v-form v-model="valid">
      <h3>app setting</h3>
      <!-- app name -->
      <v-text-field
        label="app name"
        v-model="settings.appName"
        :counter="30"
        :rules="[appNameRule]"
      />
      <!-- API URL -->
      <v-text-field
        label="API URL"
        v-model="settings.apiUrl"
        :counter="150"
        :rules="[stringRule]"
      />
      <!-- Auth Token -->
      <v-text-field
        label="Auth Token"
        v-model="settings.authToken"
        :counter="150"
        :rules="[stringRule]"
      />
      <h3>category or tag setting</h3>
      <p>Please separate by commma.</p>
      <!-- income category -->
      <v-text-field
        label="income category"
        v-model="settings.strIncomeItems"
        :counter="150"
        :rules="[stringRule]"
      />
      <!-- expense category -->
      <v-text-field
        label="expense category"
        v-model="settings.strOutgoItems"
        :counter="150"
        :rules="[stringRule]"
      />
      <!-- tag -->
      <v-text-field
        label="tag"
        v-model="settings.strTagItems"
        :counter="150"
        :rules="[stringRule, tagRule]"
      />
      <v-row class="mt-4">
        <v-spacer/>
        <v-btn color="primary" :disabled="!valid" @click="onClickSave">save</v-btn>
      </v-row>
    </v-form>
  </div>
</template>
<script>
  export default {
    name: 'Settings',
  data () {
    const createItems = v => v.split(',').map(v => v.trim()).filter(v => v.length !== 0)
    const itemMaxLength = v => createItems(v).reduce((a, c) => Math.max(a, c.length), 0)
    return {
      /** Is the input data validated?? */
      valid: false,
      /** setting */
      settings: { ...this.$store.state.settings },
      /** validation rule */
      appNameRule: v => v.length <= 30 || 'Less than 30 characters',
      stringRule: v => v.length <= 150 || 'Less than 150 characters',
      categoryRules: [
        v => createItems(v).length !== 0 || 'More than one category is required',
        v => itemMaxLength(v) <= 4 || 'Each category should be less than 5 letters'
      ],
      tagRule: v => itemMaxLength(v) <= 40 || 'Each category should be less than 5 letters'
    }
  },
  methods: {
    /** When save button is clicked. */
    onClickSave () {
      this.$store.dispatch('saveSettings', { settings: this.settings })
    }
  }
}
</script>

<style>
.form-wrapper {
  max-width: 500px;
  margin: auto;
}
</style>