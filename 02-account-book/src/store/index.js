import Vue from 'vue'
import Vuex from 'vuex'
import gasApi from '../api/gasApi'

Vue.use(Vuex)

/**
 * State
 * Vuex situation
 */
const state = {
  /** account book data */
  abData: {},
  /** check loaidng */
  loading: {
    fetch: false,
    add: false,
    update: false,
    delete: false
  },
  /** error message */
  errorMessage: '',
  /** setting */
  settings: {
    appName: 'Accounting book',
    apiUrl: '',
    authToken: '',
    strIncomeItems: 'Salary, Bonus, Carry forward',
    strOutgoItems: 'Food expense, Hobby, Fare, Entertainment expense, rent, Communoication cost, Car, Tax',
    strTagItems: 'Fixed cost, Credit card'
  }
}
/**
 * Mutations
 * Called from Actions when State is updated.
 */
const mutations = {
  /** set the selected month account book data */
  setAbData (state, { yearMonth, list }) {
    state.abData[yearMonth] = list
  },

  /** Add the data */
  addAbData (state, { item }) {
    const yearMonth = item.date.slice(0, 7)
    const list = state.abData[yearMonth]
    if (list) {
      list.push(item)
    }
  },

  /** update the selected month data */
  updateAbData (state, { yearMonth, item }) {
    const list = state.abData[yearMonth]
    if (list) {
      const index = list.findIndex(v => v.id === item.id)
      list.splice(index, 1, item)
    }
  },

  /** remove the selected month and id data */
  deleteAbData (state, { yearMonth, id }) {
    const list = state.abData[yearMonth]
    if (list) {
      const index = list.findIndex(v => v.id === id)
      list.splice(index, 1)
    }
  },
  /** set the loading */
  setLoading (state, { type, v }) {
    state.loading[type] = v
  },
  /** set the error message */
  setErrorMessage (state, { message }) {
    state.errorMessage = message
  },
  /** save the setting */
  saveSettings (state, { settings }) {
    state.settings = { ...settings }
    const { appName, apiUrl, authToken } = state.settings
    document.title = appName
    gasApi.setUrl(apiUrl)
    gasApi.setAuthToken(authToken)
    // initialize the account book
    state.abData = {}

    localStorage.setItem('settings', JSON.stringify(settings))
  },

  /** load the setting */
  loadSettings (state) {
    const settings = JSON.parse(localStorage.getItem('settings'))
    if (settings) {
      state.settings = Object.assign(state.settings, settings)
    }
    const { appName, apiUrl, authToken } = state.settings
    document.title = appName
    gasApi.setUrl(apiUrl)
    gasApi.setAuthToken(authToken)
  }
}

/**
 * Actions
 * Called from the interface and commit the Mutation
 */
const actions = {
  /** get the selected month account book data */
  async fetchAbData ({ commit }, { yearMonth }) {
    const type = 'fetch'
    console.log('fethc is called.')
    commit('setLoading', { type, v: true })
    try {
      const res = await gasApi.fetch(yearMonth)
      commit('setAbData', { yearMonth, list: res.data })
    } catch (e) {
      commit('setErrorMessage', { message: e })
      commit('setAbData', { yearMonth, list: [] })
    } finally {
      commit('setLoading', { type, v: false })
    }
  },

  /** add the data */
  async addAbData ({ commit }, { item }) {
    const type = 'add'
    commit('setLoading', { type, v: true })
    try {
      const res = await gasApi.add(item)
      commit('addAbData', { item: res.data })
    } catch (e) {
      commit('setErrorMessage', { message: e })
    } finally {
      commit('setLoading', { type, v: false })
    }
  },

  /** update the data */
  async updateAbData ({ commit }, { beforeYM, item }) {
    const type = 'update'
    const yearMonth = item.date.slice(0, 7)
    commit('setLoading', { type, v: true })
    try {
      const res = await gasApi.update(beforeYM, item)
      if (yearMonth === beforeYM) {
        commit('updateAbData', { yearMonth, item })
        return
      }
      const id = item.id
      commit('deleteAbData', { yearMonth: beforeYM, id })
      commit('addAbData', { item: res.data })
    } catch (e) {
      commit('setErrorMessage', { message: e })
    } finally {
      commit('setLoading', { type, v: false })
    }
  },

  /** remove the data */
  async deleteAbData ({ commit }, { item }) {
    const type = 'delete'
    const yearMonth = item.date.slice(0, 7)
    const id = item.id
    commit('setLoading', { type, v: true })
    try {
      await gasApi.delete(yearMonth, id)
      commit('deleteAbData', { yearMonth, id })
    } catch (e) {
      commit('setErrorMessage', { message: e })
    } finally {
      commit('setLoading', { type, v: false })
    }
  },

  /** save the setting */
  saveSettings ({ commit }, { settings }) {
    commit('saveSettings', { settings })
  },

  /** load the setting */
  loadSettings ({ commit }) {
    commit('loadSettings')
  }
}

/** set the comma sepalated letters into the array */
const createItems = v => v.split(',').map(v => v.trim()).filter(v => v.length !== 0)

/**
 * Getters
 * get if from the interface and pass the  modified State info
 */
const getters = {
  /** income category（array） */
  incomeItems (state) {
    return createItems(state.settings.strIncomeItems)
  },
  /** expense category（array */
  outgoItems (state) {
    return createItems(state.settings.strOutgoItems)
  },
  /** tag（array） */
  tagItems (state) {
    return createItems(state.settings.strTagItems)
  }
}

const store = new Vuex.Store({
  state,
  mutations,
  actions,
  getters
})

export default store