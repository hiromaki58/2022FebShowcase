<template>
  <div>
  <v-card>
      <v-card-title>
        <!-- Select the month -->
        <v-col cols="8">
          <v-menu
            ref="menu"
            v-model="menu"
            :close-on-content-click="false"
            :return-value.sync="yearMonth"
            transition="scale-transition"
            offset-y
            max-width="290px"
            min-width="290px"
          >
            <template v-slot:activator="{ on }">
              <v-text-field
                v-model="yearMonth"
                prepend-icon="mdi-calendar"
                readonly
                v-on="on"
                hide-details
              />
            </template>
            <v-date-picker
              v-model="yearMonth"
              type="month"
              color="green"
              locale="en-CA"
              no-title
              scrollable
            >
              <v-spacer/>
                <v-btn text color="grey" @click="menu = false">Cancel</v-btn>
                <v-btn text color="primary" @click="onSelectMonth">Select</v-btn>
            </v-date-picker>
          </v-menu>
        </v-col>
        <v-spacer/>
        <!-- button to add -->
        <v-col class="text-right" cols="4">
          <v-btn dark color="green" @click="onClickAdd">
            <v-icon>mdi-plus</v-icon>
          </v-btn>
        </v-col>
        <!-- search form -->
        <v-col cols="12">
          <v-text-field
            v-model="search"
            append-icon="mdi-magnify"
            label="Search"
            single-line
            hide-details
          />
        </v-col>
      </v-card-title>
      <!-- table -->
      <v-data-table
        class="text-no-wrap"
        :headers="tableHeaders"
        :items="tableData"
        :search="search"
        :footer-props="footerProps"
        :loading="loading"
        :sort-by="'date'"
        :sort-desc="true"
        :items-per-page="30"
        mobile-breakpoint="0"
      >
        <!-- date -->
        <template v-slot:item.date="{ item }">
          {{ 'Day ' + parseInt(item.date.slice(-2))  }}
        </template>
        <!-- tag -->
        <template v-slot:item.tags="{ item }">
          <div v-if="item.tags">
            <v-chip
              class="mr-2"
              v-for="(tag, i) in item.tags.split(',')"
              :key="i"
            >
              {{ tag }}
            </v-chip>
          </div>
        </template>
        <!-- income -->
        <template v-slot:item.income="{ item }">
          {{ separate(item.income) }}
        </template>
        <!-- tag -->
        <template v-slot:item.outgo="{ item }">
          {{ separate(item.outgo) }}
        </template>
        <!-- edit -->
        <template v-slot:item.actions="{ item }">
          <v-icon class="mr-2" @click="onClickEdit(item)">mdi-pencil</v-icon>
          <v-icon @click="onClickDelete(item)">mdi-delete</v-icon>
        </template>
      </v-data-table>
    </v-card>
    <!-- editing dialog -->
    <ItemDialog ref="itemDialog"/>
    <!-- delete dialog -->
    <DeleteDialog ref="deleteDialog"/>
  </div>
</template>
<script>
  import { mapState, mapActions } from 'vuex'
  import ItemDialog from '../components/ItemDialog.vue'
  import DeleteDialog from '../components/DeleteDialog.vue'
  export default {
    name: 'Home',
    components: {
      ItemDialog,
      DeleteDialog
    },
    data () {
    const today = new Date()
    const year = today.getFullYear()
    const month = ('0' + (today.getMonth() + 1)).slice(-2)

    return {
      /** month selection menu */
      menu: false,
      /** search key word */
      search: '',
      /** selected year and month */
      yearMonth: `${year}-${month}`,
      /** shown data at the table */
      tableData: []
    }
  },
  computed: {
    ...mapState({
      /** account book data */
      abData: state => state.abData,
      /** set loading */
      loading: state => state.loading.fetch,
    }),
    /** table header */
    tableHeaders () {
      return [
        { text: 'Day', value: 'date', align: 'end' },
        { text: 'title', value: 'title', sortable: false },
        { text: 'catagory', value: 'category', sortable: false },
        { text: 'tag', value: 'tags', sortable: false },
        { text: 'income', value: 'income', align: 'end' },
        { text: 'expense', value: 'outgo', align: 'end' },
        { text: 'memo', value: 'memo', sortable: false },
        { text: 'edit', value: 'actions', sortable: false }
      ]
    },
    /** table footer */
    footerProps () {
      return { itemsPerPageText: '', itemsPerPageOptions: [] }
    }
  },
  methods: {
    ...mapActions([
      /** get the account book data */
      'fetchAbData'
    ]),
    /** update the data in the browser */
    async updateTable () {
      const yearMonth = this.yearMonth
      const list = this.abData[yearMonth]
      if (list) {
        console.log('fetchAbData at updateTable of if is called.')
        this.tableData = list
      } else {
        console.log('fetchAbData at updateTable of else is called.')
        await this.fetchAbData({ yearMonth })
        this.tableData = this.abData[yearMonth]
      }
    },
    /**
     * 3 digits comma separated
     * if the number is null, return null
     */
    separate (num) {
      return num !== null ? num.toString().replace(/(\d)(?=(\d{3})+$)/g, '$1,') : null
    },
    /** When month selection button is clicked */
    onSelectMonth () {
      this.$refs.menu.save(this.yearMonth)
      this.updateTable()
    },
    /** if add button is clicked */
    onClickAdd () {
      this.$refs.itemDialog.open('add')
    },
    /** if edit button is clicked */
    onClickEdit (item) {
      this.$refs.itemDialog.open('edit', item)
    },
    /** if delete button is clicked */
    onClickDelete (item) {
      this.$refs.deleteDialog.open(item)
    }
  },
  created () {
    this.updateTable()
  }
}
</script>
