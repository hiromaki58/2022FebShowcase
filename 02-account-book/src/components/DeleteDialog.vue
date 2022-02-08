<template>
  <v-dialog
    v-model="show"
    persistent
    max-width="290"
  >
    <v-card>
      <v-card-title/>
      <v-card-text class="black--text">
        Would you like to delete「{{ item.title }}」??
      </v-card-text>
      <v-card-actions>
        <v-spacer/>
        <v-btn color="grey" text :disabled="loading" @click="onClickClose">Cancel</v-btn>
        <v-btn color="red" text :loading="loading" @click="onClickDelete">Delete</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
<script>
  import { mapActions, mapState } from 'vuex'
  export default {
    name: 'DeleteDialog',
    data () {
      return {
        /** Is dialog shown?? */
        show: false,
        /** loading flag */
        loading: false,
        /** received data */
        item: {}
      }
    },
    computed: mapState({
      /** set loading */
      loading: state => state.loading.delete
    }),
    methods: {
      ...mapActions([
        /** remove the data */
        'deleteAbData'
      ]),
      /**
      * show the dialog
      * this method is called from the parent.
      */
      open (item) {
        this.show = true
        this.item = item
      },
      /** when cancell button is clicked */
      onClickClose () {
        this.show = false
      },
      /** when remove button is clicked. */
      async onClickDelete () {
        await this.deleteAbData({ item: this.item })
        this.show = false
      }
    }
  }
</script>
