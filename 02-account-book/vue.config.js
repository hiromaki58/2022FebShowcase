const isProd = process.env.NODE_ENV === 'production'

module.exports = {
  transpileDependencies: [
    'vuetify'
  ],
  publicPath: isProd ? '/02-account-book/2022Showcase' : '/',
  outputDir: 'docs',
  filenameHashing: false,
  productionSourceMap: false
}