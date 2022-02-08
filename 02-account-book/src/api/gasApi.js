import axios from 'axios'

// create the axios instance with common header info
const gasApi = axios.create({
  headers: { 'content-type': 'application/x-www-form-urlencoded' }
})

// common response process
// if error is included , reject it
gasApi.interceptors.response.use(res => {
  if (res.data.error) {
    return Promise.reject(res.data.error)
  }
  return Promise.resolve(res)
}, err => {
  return Promise.reject(err)
})

/**
 * set the api URL
 * @param {String} url
 */
const setUrl = url => {
  gasApi.defaults.baseURL = url
  console.log('setUrl is called.')
  console.log('gasApi.defaults.baseURL is : ' + gasApi.defaults.baseURL)
}

/**
 * set the authToken
 * @param {String} token
 */
let authToken = ''
const setAuthToken = token => {
  authToken = token
  console.log('token is : ' + token)
}

/**
 * fetch the selected month data
 * @param {String} yearMonth
 * @returns {Promise}
 */
const fetch = yearMonth => {
  console.log('fetch at gasApi is called.')
  return gasApi.post(gasApi.defaults.baseURL, {
    method: 'GET',
    authToken,
    params: {
      yearMonth
    }
  })
}

/**
 * add the data
 * @param {Object} item
 * @returns {Promise}
 */
const add = item => {
  return gasApi.post(gasApi.defaults.baseURL, {
    method: 'POST',
    authToken,
    params: {
      item
    }
  })
}

/**
 * delete the selected month data
 * @param {String} yearMonth
 * @param {String} id
 * @returns {Promise}
 */
const $delete = (yearMonth, id) => {
  return gasApi.post(gasApi.defaults.baseURL, {
    method: 'DELETE',
    authToken,
    params: {
      yearMonth,
      id
    }
  })
}

/**
 * update the data
 * @param {String} beforeYM
 * @param {Object} item
 * @returns {Promise}
 */
const update = (beforeYM, item) => {
  return gasApi.post(gasApi.defaults.baseURL, {
    method: 'PUT',
    authToken,
    params: {
      beforeYM,
      item
    }
  })
}

export default {
  setUrl,
  setAuthToken,
  fetch,
  add,
  delete: $delete,
  update
}