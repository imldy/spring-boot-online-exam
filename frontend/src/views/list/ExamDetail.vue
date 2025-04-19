<template>
  <a-layout>
    <a-layout-header class="header" style="color: #fff">
      <!--   v-if="examDetail.exam" 是为了防止 异步请求时页面渲染的时候还没有拿到这个值而报错， 下面多处这个判断都是这个道理 -->
      <span style="font-size:25px;margin-left: 0px;" v-if="examDetail.exam">
        <a-avatar slot="avatar" size="large" shape="circle" :src="examDetail.exam.examAvatar | imgSrcFilter"/>
        {{ examDetail.exam.examName }}
        <span style="font-size:15px;">{{ examDetail.exam.examDescription }} </span>
      </span>
      <span style="float: right;">
        <span style="margin-right: 60px; font-size: 20px" v-if="examDetail.exam">考试限时：{{ examDetail.exam.examTimeLimit }}分钟</span>
        <a-button type="danger" ghost style="margin-right: 60px;" @click="finishExam()">交卷</a-button>
        <a-avatar class="avatar" size="small" :src="avatar()"/>
        <span style="margin-left: 12px">{{ nickname() }}</span>
      </span>
    </a-layout-header>
    <a-layout>
      <a-layout-sider width="190" :style="{background: '#444',overflow: 'auto', height: '100vh', position: 'fixed', left: 0 }">
        <a-menu
          mode="inline"
          :defaultSelectedKeys="['1']"
          :defaultOpenKeys="['partI', 'partII', 'partIII', 'partIV']"
          :style="{ height: '100%', borderRight: 0 }"
        >
          <a-sub-menu key="partI">
            <span slot="title" v-if="examDetail.exam"><a-icon type="edit" theme="twoTone"/>写作部分</span>
            <a-menu-item v-for="(item, index) in examDetail.partIIds" :key="item" @click="getQuestionDetail(item)">
              <a-icon type="eye" theme="twoTone" twoToneColor="#52c41a" v-if="answersMap.get(item)"/>
              题目{{ index + 1 }}
            </a-menu-item>
          </a-sub-menu>
          <a-sub-menu key="partII">
            <span slot="title" v-if="examDetail.exam"><a-icon type="sound" theme="twoTone"/>听力部分</span>
            <a-sub-menu key="partIIA">
              <span slot="title">Section A</span>
              <a-menu-item v-for="(item, index) in examDetail.partIIAIds" :key="item" @click="getQuestionDetail(item)">
                <a-icon type="eye" theme="twoTone" twoToneColor="#52c41a" v-if="answersMap.get(item)"/>
                题目{{ index + 1 }}
              </a-menu-item>
            </a-sub-menu>
            <a-sub-menu key="partIIB">
              <span slot="title">Section B</span>
              <a-menu-item v-for="(item, index) in examDetail.partIIBIds" :key="item" @click="getQuestionDetail(item)">
                <a-icon type="eye" theme="twoTone" twoToneColor="#52c41a" v-if="answersMap.get(item)"/>
                题目{{ index + 1 }}
              </a-menu-item>
            </a-sub-menu>
            <a-sub-menu key="partIIC">
              <span slot="title">Section C</span>
              <a-menu-item v-for="(item, index) in examDetail.partIICIds" :key="item" @click="getQuestionDetail(item)">
                <a-icon type="eye" theme="twoTone" twoToneColor="#52c41a" v-if="answersMap.get(item)"/>
                题目{{ index + 1 }}
              </a-menu-item>
            </a-sub-menu>
          </a-sub-menu>
          <a-sub-menu key="partIII">
            <span slot="title" v-if="examDetail.exam"><a-icon type="read" theme="twoTone"/>阅读部分</span>
            <a-sub-menu key="partIIIA">
              <span slot="title">Section A</span>
              <a-menu-item v-for="(item, index) in examDetail.partIIIAIds" :key="item" @click="getQuestionDetail(item)">
                <a-icon type="eye" theme="twoTone" twoToneColor="#52c41a" v-if="answersMap.get(item)"/>
                题目{{ index + 1 }}
              </a-menu-item>
            </a-sub-menu>
            <a-sub-menu key="partIIIB">
              <span slot="title">Section B</span>
              <a-menu-item v-for="(item, index) in examDetail.partIIIBIds" :key="item" @click="getQuestionDetail(item)">
                <a-icon type="eye" theme="twoTone" twoToneColor="#52c41a" v-if="answersMap.get(item)"/>
                题目{{ index + 1 }}
              </a-menu-item>
            </a-sub-menu>
            <a-sub-menu key="partIIIC">
              <span slot="title">Section C</span>
              <a-menu-item v-for="(item, index) in examDetail.partIIICIds" :key="item" @click="getQuestionDetail(item)">
                <a-icon type="eye" theme="twoTone" twoToneColor="#52c41a" v-if="answersMap.get(item)"/>
                题目{{ index + 1 }}
              </a-menu-item>
            </a-sub-menu>
          </a-sub-menu>
          <a-sub-menu key="partIV">
            <span slot="title" v-if="examDetail.exam"><a-icon type="translation" theme="twoTone"/>翻译部分</span>
            <a-menu-item v-for="(item, index) in examDetail.partIVIds" :key="item" @click="getQuestionDetail(item)">
              <a-icon type="eye" theme="twoTone" twoToneColor="#52c41a" v-if="answersMap.get(item)"/>
              题目{{ index + 1 }}
            </a-menu-item>
          </a-sub-menu>
        </a-menu>
      </a-layout-sider>
      <a-layout :style="{ marginLeft: '200px' }">
        <a-layout-content :style="{ margin: '24px 16px 0',height: '84vh', overflow: 'initial' }">
          <div :style="{ padding: '24px', background: '#fff',height: '84vh'}">
            <span v-show="currentQuestion === ''" style="font-size: 30px;font-family: Consolas">欢迎参加考试，请点击左侧题目编号开始答题</span>
            <strong>{{ currentQuestion.type }} </strong> <p v-html="currentQuestion.name"></p>
            <!-- 显示音频文件（如果是听力题） -->
            <div v-if="isListeningType && currentQuestion.audioUrl" class="audio-player">
              <audio controls>
                <source :src="currentQuestion.audioUrl" type="audio/mpeg">
                您的浏览器不支持音频播放。
              </audio>
            </div>

            <!-- 听力A、B、C部分和阅读B、C部分 -->
            <a-checkbox-group @change="onCheckChange" v-model="checkValues" v-if="isListeningType || currentQuestion.typeId === 621 || currentQuestion.typeId === 631">
              <a-checkbox v-for="option in currentQuestion.options" :key="option.questionOptionId" :style="optionStyle" :value="option.questionOptionId">
                {{ option.questionOptionContent }}
              </a-checkbox>
            </a-checkbox-group>

            <!-- 阅读A部分 -->
            <a-select
              mode="multiple"
              :size="size"
              placeholder="请选择答案（每个选项只能选择一次）"
              :value="checkValues"
              style="width: 100%"
              @change="onReadingAChange"
              v-if="currentQuestion.typeId === 611"
            >
              <a-select-option v-for="option in currentQuestion.options" :key="option.questionOptionId">
                {{ option.questionOptionContent }}
              </a-select-option>
            </a-select>
          </div>
        </a-layout-content>
        <a-layout-footer :style="{ textAlign: 'center' }">
          Spting Boot Online CET ©2025 Crated by 黄海学院
        </a-layout-footer>
      </a-layout>
    </a-layout>
  </a-layout>
</template>

<script>
import { getExamDetail, getQuestionDetail, finishExam } from '../../api/exam'
import UserMenu from '../../components/tools/UserMenu'
import { mapGetters } from 'vuex'

export default {
  name: 'ExamDetail',
  components: {
    UserMenu
  },
  data () {
    return {
      // 考试详情对象
      examDetail: {},
      // 用户做过的问题都放到这个数组中，键为问题id, 值为currentQuestion(其属性answers属性用于存放答案选项地id或ids),，用于存放用户勾选的答案
      answersMap: {},
      // 当前用户的问题
      currentQuestion: '',
      // 多选题的绑定值，用于从answersMap中初始化做题状态
      checkValues: [],
      optionStyle: {
        display: 'block',
        height: '30px',
        lineHeight: '30px',
        marginLeft: '0px'
      },
      isListeningType: false,
      size: 'default'
    }
  },
  mounted () {
    this.answersMap = new Map()
    const that = this
    // 从后端获取考试的详细信息，渲染到考试详情里
    getExamDetail(this.$route.params.id)
      .then(res => {
        if (res.code === 0) {
          // 赋值考试对象
          that.examDetail = res.data
          return res.data
        } else {
          this.$notification.error({
            message: '获取考试详情失败',
            description: res.msg
          })
        }
      })
  },
  methods: {
    // 从全局变量中获取用户昵称和头像,
    ...mapGetters(['nickname', 'avatar']),
    getQuestionDetail (questionId) {
      // 问题切换时从后端拿到问题详情，渲染到前端content中
      const that = this
      // 清空问题绑定的值
      this.checkValues = []
      getQuestionDetail(questionId)
        .then(res => {
          if (res.code === 0) {
            // 赋值当前考试对象
            that.currentQuestion = res.data
            // 判断是否是听力题
            that.isListeningType = [511, 521, 531].includes(that.currentQuestion.typeId)
            // 查看用户是不是已经做过这道题又切换回来的，answersMap中查找，能找到这个题目id对应的值数组不为空说明用户做过这道题
            if (that.answersMap.get(that.currentQuestion.id)) {
              // 说明之前做过这道题了
              if (that.currentQuestion.type === '多选题') {
                // 数组是引用类型，因此需要进行拷贝，千万不要直接赋值
                Object.assign(that.checkValues, that.answersMap.get(that.currentQuestion.id))
              }
            }
            return res.data
          } else {
            this.$notification.error({
              message: '获取问题详情失败',
              description: res.msg
            })
          }
        })
    },
    /**
     * 多选题触发的变化事件
     * @param checkedValues
     */
    onCheckChange (checkedValues) {
      // 更新做题者选择的答案
      this.answersMap.set(this.currentQuestion.id, checkedValues)
    },
    onReadingAChange (values) {
      // 确保每个选项只被选择一次
      const uniqueValues = [...new Set(values)]
      this.checkValues = uniqueValues
      // 更新做题者选择的答案
      this.answersMap.set(this.currentQuestion.id, uniqueValues)
    },
    _strMapToObj (strMap) {
      const obj = Object.create(null)
      for (const [k, v] of strMap) {
        obj[k] = v
      }
      return obj
    },
    /**
     *map转换为json
     */
    _mapToJson (map) {
      return JSON.stringify(this._strMapToObj(map))
    },
    /**
     * 结束考试并交卷
     */
    finishExam () {
      // Todo:向后端提交作答信息数组answersMap
      finishExam(this.$route.params.id, this._mapToJson(this.answersMap))
        .then(res => {
          if (res.code === 0) {
            // 考试交卷，后端判分完成，然后跳转到我的考试界面
            this.$notification.success({
              message: '考卷提交成功！'
            })
            this.$router.push('/list/exam-record-list')
            return res.data
          } else {
            this.$notification.error({
              message: '交卷失败！',
              description: res.msg
            })
          }
        })
    }
  }
}
</script>

<style scoped>

</style>
