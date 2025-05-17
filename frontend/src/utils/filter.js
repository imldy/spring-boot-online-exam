import Vue from 'vue'
import moment from 'moment'
import 'moment/locale/zh-cn'
import $ from 'jquery'
moment.locale('zh-cn')

Vue.filter('NumberFormat', function (value) {
  if (!value) {
    return '0'
  }
  const intPartFormat = value.toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,') // 将整数部分逢三一断
  return intPartFormat
})

Vue.filter('dayjs', function (dataStr, pattern = 'YYYY-MM-DD HH:mm:ss') {
  return moment(dataStr).format(pattern)
})

Vue.filter('moment', function (dataStr, pattern = 'YYYY-MM-DD HH:mm:ss') {
  return moment(dataStr).format(pattern)
})

Vue.filter('imgSrcFilter', function (content) {
  if (!content || !$(content).children('img').length) {
    return 'https://i.loli.net/2019/11/02/rCHKVJd4jTovzW9.jpg' // 默认图片路径
  }
  return $(content).children('img').attr('src')
})
