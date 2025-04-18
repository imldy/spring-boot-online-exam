<template>
  <div class="home-page page1">
    <div class="home-page-wrapper" id="page1-wrapper">
      <h2><span>在线CET模拟系统</span>能为您做什么</h2>
      <div class="title-line-wrapper page1-line"></div>
      <list :data-source="features" />
    </div>
  </div>
</template>

<script>
import List from './List'
const featuresCN = [
  {
    title: '全真模拟',
    content: '完全模拟真实CET考试环境，让您提前适应考试节奏',
    src: 'https://gw.alipayobjects.com/zos/rmsportal/VriUmzNjDnjoFoFFZvuh.svg',
    color: '#13C2C2',
    shadowColor: 'rgba(19,194,194,.12)'
  },
  {
    title: '智能评分',
    content: '采用先进评分算法，提供客观准确的成绩评估',
    src: 'https://gw.alipayobjects.com/zos/rmsportal/smwQOoxCjXVbNAKMqvWk.svg',
    color: '#2F54EB',
    shadowColor: 'rgba(47,84,235,.12)'
  },
  {
    title: '真题题库',
    content: '收录历年真题，提供最权威的备考资料',
    src: 'https://gw.alipayobjects.com/zos/rmsportal/hBbIHzUsSbSxrhoRFYzi.svg',
    color: '#F5222D',
    shadowColor: 'rgba(245,34,45,.12)'
  },
  {
    title: '错题分析',
    content: '智能分析错题，针对性提供学习建议',
    src: 'https://gw.alipayobjects.com/zos/rmsportal/BISfzKcCNCYFmTYcUygW.svg',
    color: '#1AC44D',
    shadowColor: 'rgba(26,196,77,.12)'
  },
  {
    title: '学习计划',
    content: '根据个人水平定制专属学习计划',
    src: 'https://gw.alipayobjects.com/zos/rmsportal/pbmKMSFpLurLALLNliUQ.svg',
    color: '#FA8C16',
    shadowColor: 'rgba(250,140,22,.12)'
  },
  {
    title: '进度追踪',
    content: '实时记录学习进度，清晰掌握备考情况',
    src: 'https://gw.alipayobjects.com/zos/rmsportal/RpJIQitGbSCHwLMimybX.svg',
    color: '#1890FF',
    shadowColor: 'rgba(24,144,255,.12)'
  }
]

export default {
  name: 'Page1',
  components: {
    List
  },
  data () {
    return {
      features: featuresCN
    }
  },
  created () {
    this.updateFeatures()
  },
  methods: {
    updateFeatures () {
      const arr = featuresCN
      const newArr = [[], [], []]
      arr.forEach((item, index) => {
        newArr[Math.floor(index / 3)].push(item)
      })
      this.features = newArr
    }
  }
}
</script>
