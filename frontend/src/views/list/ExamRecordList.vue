<template>
  <div>
    <a-card style="margin-top: 24px" :bordered="false" title="参加过的考试">
      <div slot="extra">
        <a-input-search style="margin-left: 16px; width: 272px;"/>
      </div>
      <a-list size="large" v-if="Array.isArray(data) && data.length > 0">
        <a-list-item :key="index" v-for="(item, index) in data" v-if="item">
          <a-list-item-meta :description="item.exam ? item.exam.examDescription : '考试信息已删除'">
            <a-avatar slot="avatar" size="large" shape="square" :src="getAvatarSrc(item)"/>
            <a slot="title">{{ item.exam ? item.exam.examName : '考试信息已删除' }}</a>
          </a-list-item-meta>
          <div slot="actions" v-if="item.examRecord">
            <a @click="viewExamRecordDetail(item.examRecord)">查看考试详情</a>
          </div>
          <div class="list-content">
            <div class="list-content-item" v-if="item.user">
              <span>Owner</span>
              <p>{{ item.user.userUsername || '未知用户' }}</p>
            </div>
            <div class="list-content-item" v-if="item.examRecord">
              <span>开始时间</span>
              <p>{{ item.examRecord.examJoinDate || '未知时间' }}</p>
            </div>
            <div class="list-content-item" v-if="item.examRecord">
              <span>分数</span>
              <p>{{ item.examRecord.examJoinScore !== undefined && item.examRecord.examJoinScore !== null ? String(item.examRecord.examJoinScore) : '未评分' }}</p>
            </div>
          </div>
        </a-list-item>
      </a-list>
      <div v-else>
        <a-empty description="暂无考试记录" />
      </div>
    </a-card>
  </div>
</template>

<script>
import HeadInfo from '../../components/tools/HeadInfo'
import { getExamRecordList } from '../../api/exam'
import $ from 'jquery'

export default {
  // 考试记录列表，记录考生参加过地所有考试和考试成绩
  name: 'ExamRecordList',
  components: {
    HeadInfo
  },
  data () {
    return {
      data: []
    }
  },
  methods: {
    /**
     * 提取头像图片地址
     */
    getAvatarSrc (item) {
      if (!item || !item.exam || !item.exam.examAvatar) {
        return 'https://i.loli.net/2019/11/02/rCHKVJd4jTovzW9.jpg'
      }
      const avatar = item.exam.examAvatar
      const $content = $(avatar)
      const imgSrc = $content.children('img').attr('src')
      return imgSrc || 'https://i.loli.net/2019/11/02/rCHKVJd4jTovzW9.jpg'
    },
    /**
     * 根据考试记录的id拿到本次考试的详情并查看
     * @param record 考试详情的记录
     */
    viewExamRecordDetail (record) {
      if (!record || !record.examId || !record.examRecordId) {
        this.$notification.error({
          message: '考试记录不完整',
          description: '无法查看详情，考试记录数据不完整'
        })
        return
      }
      // 直接跳到参加考试的页面，查看所有题目的详细情况
      const routeUrl = this.$router.resolve({
        path: `/exam/record/${record.examId}/${record.examRecordId}`
      })
      // 和点击考试卡片效果一样，跳转到考试页面，里面有所有题目的情况，相当于就是详情了
      window.open(routeUrl.href, '_blank')
    }
  },
  mounted () {
    // 从后端数据获取考试列表，适配前端卡片
    getExamRecordList().then(res => {
      if (res.code === 0) {
        this.data = res.data || []
        console.log('获取考试记录数据:', this.data)
      } else {
        this.$notification.error({
          message: '获取考试记录失败',
          description: res.msg
        })
      }
    }).catch(err => {
      // 失败就弹出警告消息
      this.$notification.error({
        message: '获取考试记录失败',
        description: err.message
      })
    })
  }
}
</script>

<style lang="less" scoped>
  .ant-avatar-lg {
    width: 48px;
    height: 48px;
    line-height: 48px;
  }

  .list-content-item {
    color: rgba(0, 0, 0, .45);
    display: inline-block;
    vertical-align: middle;
    font-size: 14px;
    margin-left: 40px;

    span {
      line-height: 20px;
    }

    p {
      margin-top: 4px;
      margin-bottom: 0;
      line-height: 22px;
    }
  }
</style>
