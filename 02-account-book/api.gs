const ss = SpreadsheetApp.getActive()
const authToken = PropertiesService.getScriptProperties().getProperty('authToken') || ''

/**
 * return response
 * @param {*} content
 * @returns {TextOutput}
 */
function response (content) {
  const res = ContentService.createTextOutput()
  res.setMimeType(ContentService.MimeType.JSON)
  res.setContent(JSON.stringify(content))
  return res
}

/**
 * execute when the app receive the post request
 * @param {Event} e
 * @returns {TextOutput}
 */
function doPost (e) {
  let contents
  try {
    contents = JSON.parse(e.postData.contents)
  } catch (e) {
    return response({ error: 'Wrong JSON format' })
  }

  if (contents.authToken !== authToken) {
    return response({ error: 'Fail to authorize' })
  }

  const { method = '', params = {} } = contents

  let result
  try {
    switch (method) {
      case 'POST':
        result = onPost(params)
        break
      case 'GET':
        result = onGet(params)
        break
      case 'PUT':
        result = onPut(params)
        break
      case 'DELETE':
        result = onDelete(params)
        break
      default:
        result = { error: 'Please select the method' }
    }
  } catch (e) {
    result = { error: e }
  }

  return response(result)
}

/** --- API --- */

/**
 * add the data
 * @param {Object} params
 * @param {Object} params.item account book data
 * @returns {Object} added account book data
 */
function onPost ({ item }) {
  if (!isValid(item)) {
    return {
      error: 'Please input with right format.'
    }
  }
  const { date, title, category, tags, income, outgo, memo } = item
  
  const yearMonth = date.slice(0, 7)
  const sheet = ss.getSheetByName(yearMonth) || insertTemplate(yearMonth)

  const id = Utilities.getUuid().slice(0, 8)
  const row = ["'" + id, "'" + date, "'" + title, "'" + category, "'" + tags, income, outgo, "'" + memo]
  sheet.appendRow(row)

  return { id, date, title, category, tags, income, outgo, memo }
}

/**
 * Get the selected month data
 * @param {Object} params
 * @param {String} params.yearMonth yyyy/MM
 * @returns {Object[]} account book data
 */
function onGet ({ yearMonth }) {
  const ymReg = /^[0-9]{4}-(0[1-9]|1[0-2])$/
  
  if (!ymReg.test(yearMonth)) {
    return {
      error: 'Please input with right format.'
    }
  }
  
  const sheet = ss.getSheetByName(yearMonth)
  const lastRow = sheet ? sheet.getLastRow() : 0

  if (lastRow < 7) {
    return []
  }

  const list = sheet.getRange('A7:H' + lastRow).getValues().map(row => {
    const [id, date, title, category, tags, income, outgo, memo] = row
    return {
      id,
      date,
      title,
      category,
      tags,
      income: (income === '') ? null : income,
      outgo: (outgo === '') ? null : outgo,
      memo
    }
  })

  return list
}

/**
 * Delte the selected month and id data
 * @param {Object} params
 * @param {String} params.yearMonth yyyy/MM
 * @param {String} params.id id
 * @returns {Object} message
 */
function onDelete ({ yearMonth, id }) {
  const ymReg = /^[0-9]{4}-(0[1-9]|1[0-2])$/
  const sheet = ss.getSheetByName(yearMonth)

  if (!ymReg.test(yearMonth) || sheet === null) {
    return {
      error: 'Selected sheet does not exist.'
    }
  }

  const lastRow = sheet.getLastRow()
  const index = sheet.getRange('A7:A' + lastRow).getValues().flat().findIndex(v => v === id)

  if (index === -1) {
    return {
      error: 'Selected data does not exist.'
    }
  }

  sheet.deleteRow(index + 7)
  return {
    message: 'Deleted'
  }
}

/**
 * update the selected data
 * @param {Object} params
 * @param {String} params.beforeYM Not updated data's year and month
 * @param {Object} params.item account book data
 * @returns {Object} updated account book data
 */
function onPut ({ beforeYM, item }) {
  const ymReg = /^[0-9]{4}-(0[1-9]|1[0-2])$/
  if (!ymReg.test(beforeYM) || !isValid(item)) {
    return {
      error: 'Please input with right format.'
    }
  }

  // If the year and month are different from the not updated data, delete and add the new data
  const yearMonth = item.date.slice(0, 7)
  if (beforeYM !== yearMonth) {
    onDelete({ yearMonth: beforeYM, id: item.id })
    return onPost({ item })
  }

  const sheet = ss.getSheetByName(yearMonth)
  if (sheet === null) {
    return {
      error: 'Selected sheet does not exist.'
    }
  }

  const id = item.id
  const lastRow = sheet.getLastRow()
  const index = sheet.getRange('A7:A' + lastRow).getValues().flat().findIndex(v => v === id)

  if (index === -1) {
    return {
      error: 'Selected data does not exist.'
    }
  }

  const row = index + 7
  const { date, title, category, tags, income, outgo, memo } = item

  const values = [["'" + date, "'" + title, "'" + category, "'" + tags, income, outgo, "'" + memo]]
  sheet.getRange(`B${row}:H${row}`).setValues(values)

  return { id, date, title, category, tags, income, outgo, memo }
}

/** --- common --- */

/**
 * Create the month selection template sheet
 * @param {String} yearMonth
 * @returns {Sheet} sheet
 */
function insertTemplate (yearMonth) {
  const { SOLID_MEDIUM, DOUBLE } = SpreadsheetApp.BorderStyle

  const sheet = ss.insertSheet(yearMonth, 0)
  const [year, month] = yearMonth.split('-')

  // balance check area
  sheet.getRange('A1:B1')
    .merge()
    .setValue(`year: ${year} month: ${parseInt(month)}`)
    .setFontWeight('bold')
    .setHorizontalAlignment('center')
    .setBorder(null, null, true, null, null, null, 'black', SOLID_MEDIUM)

  sheet.getRange('A2:A4')
    .setValues([['Income：'], ['Expense：'], ['Balance：']])
    .setFontWeight('bold')
    .setHorizontalAlignment('right')

  sheet.getRange('B2:B4')
    .setFormulas([['=SUM(F7:F)'], ['=SUM(G7:G)'], ['=B2-B3']])
    .setNumberFormat('#,##0')

  sheet.getRange('A4:B4')
    .setBorder(true, null, null, null, null, null, 'black', DOUBLE)

  // table header
  sheet.getRange('A6:H6')
    .setValues([['id', 'Date', 'Title', 'Category', 'Tag', 'Income', 'Expense', 'Memo']])
    .setFontWeight('bold')
    .setBorder(null, null, true, null, null, null, 'black', SOLID_MEDIUM)

  sheet.getRange('F7:G')
    .setNumberFormat('#,##0')

  // Expense group by the category
  sheet.getRange('J1')
    .setFormula('=QUERY(B7:H, "select D, sum(G), sum(G) / "&B3&"  where G > 0 group by D order by sum(G) desc label D \'category\', sum(G) \'expense\'")')

  sheet.getRange('J1:L1')
    .setFontWeight('bold')
    .setBorder(null, null, true, null, null, null, 'black', SOLID_MEDIUM)

  sheet.getRange('L1')
    .setFontColor('white')

  sheet.getRange('K2:K')
    .setNumberFormat('#,##0')

  sheet.getRange('L2:L')
    .setNumberFormat('0.0%')

  sheet.setColumnWidth(9, 21)

  return sheet
}

/**
 * Check the data format is fine
 * @param {Object} item
 * @returns {Boolean} isValid
 */
function isValid (item = {}) {
  const strKeys = ['date', 'title', 'category', 'tags', 'memo']
  const keys = [...strKeys, 'income', 'outgo']

  // ARE all of keies existing??
  for (const key of keys) {
    if (item[key] === undefined) return false
  }

  // Is string without the balance number??
  for (const key of strKeys) {
    if (typeof item[key] !== 'string') return false
  }

  // Is date format fine??
  const dateReg = /^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$/
  if (!dateReg.test(item.date)) return false

  // Is income or expense input??
  const { income: i, outgo: o } = item
  if ((i === null && o === null) || (i !== null && o !== null)) return false

  // Is input income or expense integer??
  if (i !== null && typeof i !== 'number') return false
  if (o !== null && typeof o !== 'number') return false

  return true
}