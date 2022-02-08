<template>
  <!-- add／edit dialog -->
  <v-dialog
    v-model="show"
    scrollable
    persistent
    max-width="500px"
    eager
  >
    <v-card>
      <v-card-title>{{ titleText }}</v-card-title>
      <v-divider/>
      <v-card-text>
        <v-form ref="form" v-model="valid">
          <!-- select date -->
          <v-menu
            ref="menu"
            v-model="menu"
            :close-on-content-click="false"
            :return-value.sync="date"
            transition="scale-transition"
            offset-y
            max-width="290px"
            min-width="290px"
          >
            <template v-slot:activator="{ on }">
              <v-text-field
                v-model="date"
                prepend-icon="mdi-calendar"
                readonly
                v-on="on"
                hide-details
              />
            </template>
            <v-date-picker
              v-model="date"
              color="green"
              locale="ja-jp"
              :day-format="date => new Date(date).getDate()"
              no-title
              scrollable
            >
              <v-spacer/>
              <v-btn text color="grey" @click="menu = false">Cancel</v-btn>
              <v-btn text color="primary" @click="$refs.menu.save(date)">Select</v-btn>
            </v-date-picker>
          </v-menu>
          <!-- title -->
          <v-text-field
            label="title"
            v-model.trim="title"
            :counter="20"
            :rules="titleRules"
          />
          <!-- balance -->
          <v-radio-group
            row
            v-model="inout"
            hide-details
            @change="onChangeInout"
          >
            <v-radio label="income" value="income"/>
            <v-radio label="expense" value="outgo"/>
          </v-radio-group>
          <!-- category -->
          <v-select
            label="category"
            v-model="category"
            :items="categoryItems"
            hide-details
          />
          <!-- tag -->
          <v-select
            label="tag"
            v-model="tags"
            :items="tagItems"
            multiple
            chips
            :rules="[tagRule]"
          />
          <!-- amount -->
          <v-text-field
            label="amount"
            v-model.number="amount"
            prefix="￥"
            pattern="[0-9]*"
            :rules="amountRules"
          />
          <!-- memo -->
          <v-text-field
            label="memo"
            v-model="memo"
            :counter="50"
            :rules="[memoRule]"
          />
        </v-form>
      </v-card-text>
      <v-divider/>
      <v-card-actions>
        <v-spacer/>
        <v-btn
          color="grey darken-1"
          text
          :disabled="loading"
          @click="onClickClose"
        >
          cancel
        </v-btn>
        <v-btn
          color="blue darken-1"
          text
          :disabled="!valid"
          :loading="loading"
          @click="onClickAction"
        >
          {{ actionText }}
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
<script>
import { mapActions, mapGetters, mapState } from 'vuex'
export default {
  name: 'ItemDialog',
  data () {
    return {
      /** Is dialog shown?? */
      show: false,
      /** check the input data is valid */
      valid: false,
      /** date selection menu situation */
      menu: false,
      /** loading flag */
      loading: false,
      /** manipulate situation 'add' or 'edit' */
      actionType: 'add',
      /** id */
      id: '',
      /** date */
      date: '',
      /** title */
      title: '',
      /** balance 'income' or 'outgo' */
      inout: '',
      /** catagory */
      category: '',
      /** tag */
      tags: [],
      /** amount */
      amount: 0,
      /** memo */
      memo: '',
      /** category list */
      incomeItems: ['category1', 'category2'],
      outgoItems: ['category3', 'category4'],
      /** category list */
      categoryItems: [],
      /** tag list */
      tagItems: ['tag1', 'tag2'],
      /** original date（using at editing mode） */
      beforeYM: '',
      /** validation rule */
      titleRules: [
        v => v.trim().length > 0 || 'Title is required.',
        v => v.length <= 20 || 'Less than 20 characters'
      ],
      tagRule: v => v.length <= 5 || '5 tags are maximum.',
      amountRules: [
        v => v >= 0 || 'Need to be more than 0',
        v => Number.isInteger(v) || 'Need to be integer'
      ],
      memoRule: v => v.length <= 50 || 'Less than 50 characters'
    }
  },
  computed: {
    ...mapGetters([
      /** income category */
      'incomeItems',
      'outgoItems',
      /** tag */
      'tagItems'
    ]),
    ...mapState({
      /** set loading */
      loading: state => state.loading.add || state.loading.update
    }),
    /** dialog title */
    titleText () {
      return this.actionType === 'add' ? 'Add' : 'Edit'
    },
    /** dialog action */
    actionText () {
      return this.actionType === 'add' ? 'Add' : 'edit'
    }
  },
  methods: {
    ...mapActions([
      /** add data */
      'addAbData',
      /** update data */
      'updateAbData'
    ]),
    /**
     * Show the dialog
     * This method is called by the parent
     */
    open (actionType, item) {
      this.show = true
      this.actionType = actionType
      this.resetForm(item)
      if (actionType === 'edit') {
        this.beforeYM = item.date.slice(0, 7)
      }
    },
    /** To cancel */
    onClickClose () {
      this.show = false
    },
    /** To add or edit */
    async onClickAction () {
      const item = {
        date: this.date,
        title: this.title,
        category: this.category,
        tags: this.tags.join(','),
        memo: this.memo,
        income: null,
        outgo: null
      }
      item[this.inout] = this.amount || 0

      if (this.actionType === 'add') {
        await this.addAbData({ item })
      } else {
        item.id = this.id
        await this.updateAbData({ beforeYM: this.beforeYM, item })
      }

      this.show = false
    },
    /** if change the balance */
    onChangeInout () {
      if (this.inout === 'income') {
        this.categoryItems = this.incomeItems
      } else {
        this.categoryItems = this.outgoItems
      }
      this.category = this.categoryItems[0]
    },
    /** initialize the form */
    resetForm (item = {}) {
      const today = new Date()
      const year = today.getFullYear()
      const month = ('0' + (today.getMonth() + 1)).slice(-2)
      const date = ('0' + today.getDate()).slice(-2)
      this.id = item.id || ''
      this.date = item.date || `${year}-${month}-${date}`
      this.title = item.title || ''
      this.inout = item.income != null ? 'income' : 'outgo'
      if (this.inout === 'income') {
        this.categoryItems = this.incomeItems
        this.amount = item.income || 0
      } else {
        this.categoryItems = this.outgoItems
        this.amount = item.outgo || 0
      }
      this.category = item.category || this.categoryItems[0]
      this.tags = item.tags ? item.tags.split(',') : []
      this.memo = item.memo || ''
      this.$refs.form.resetValidation()
    }
  }
}
</script>