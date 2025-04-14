<template>
  <a-modal title="更新考试" :width="640" :visible="visible" :confirmLoading="confirmLoading" @cancel="handleCancel">
    <a-spin :spinning="confirmLoading">
      <a-steps :current="currentStep" :style="{ marginBottom: '28px' }" size="small">
        <a-step title="考试描述" />
        <a-step title="每题分数" />
        <a-step title="选择题目" />
      </a-steps>
      <a-form :form="form">
        <!-- step1 -->
        <div v-show="currentStep === 0">
          <a-form-item label="考试名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-model="name" />
          </a-form-item>
          <a-form-item label="考试限时" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number :min="1" :max="200" v-model="elapse" />
            分钟
          </a-form-item>
          <a-form-item label="考试简述" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-textarea :rows="2" v-model="desc"></a-textarea>
          </a-form-item>
          <a-form-item label="考试封面" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <p>直接点击列表图片修改，建议图片不要大于80*80</p>
          </a-form-item>
        </div>
        <div v-show="currentStep === 1">
          <a-form-item label="单选题" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number :min="1" :max="20" v-model="radioScore" />
            分
          </a-form-item>
          <a-form-item label="多选题" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number :min="1" :max="20" v-model="checkScore" />
            分
          </a-form-item>
          <a-form-item label="判断题" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number :min="1" :max="20" v-model="judgeScore" />
            分
          </a-form-item>
          <a-form-item label="写作题" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number :min="1" :max="20" v-model="partIScore" />
            分
          </a-form-item>
          <a-form-item label="听力A部分" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number :min="1" :max="20" v-model="partIIAScore" />
            分
          </a-form-item>
          <a-form-item label="听力B部分" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number :min="1" :max="20" v-model="partIIBScore" />
            分
          </a-form-item>
          <a-form-item label="听力C部分" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number :min="1" :max="20" v-model="partIICScore" />
            分
          </a-form-item>
          <a-form-item label="阅读A部分" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number :min="1" :max="20" v-model="partIIIAScore" />
            分
          </a-form-item>
          <a-form-item label="阅读B部分" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number :min="1" :max="20" v-model="partIIIBScore" />
            分
          </a-form-item>
          <a-form-item label="阅读C部分" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number :min="1" :max="20" v-model="partIIICScore" />
            分
          </a-form-item>
          <a-form-item label="翻译题" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number :min="1" :max="20" v-model="partIVScore" />
            分
          </a-form-item>
        </div>

        <div v-show="currentStep === 2">
          <a-form-item label="选择单选题" :labelCol="labelCol" :wrapperCol="wrapperCol" enterButton="Search">
            <!-- 单选 -->
            <a-select
              mode="multiple"
              :size="size"
              :default-value="defaultRadios"
              v-if="visible"
              placeholder="请选择单选题"
              style="width: 100%"
              @popupScroll="popupScroll"
              @change="handleRadioChange"
            >
              <a-select-option v-for="radio in radios" :value="radio.name" :key="radio.id">
                {{ radio.name }}
              </a-select-option>
            </a-select>
          </a-form-item>

          <a-form-item label="选择多选题" :labelCol="labelCol" :wrapperCol="wrapperCol" enterButton="Search">
            <!-- 多选 -->
            <a-select
              mode="multiple"
              :size="size"
              :default-value="defaultChecks"
              v-if="visible"
              placeholder="请选择多选题"
              style="width: 100%"
              @popupScroll="popupScroll"
              @change="handleCheckChange"
            >
              <a-select-option v-for="check in checks" :value="check.name" :key="check.id">
                {{ check.name }}
              </a-select-option>
            </a-select>
          </a-form-item>

          <a-form-item label="选择判断题" :labelCol="labelCol" :wrapperCol="wrapperCol" enterButton="Search">
            <!-- 判断 -->
            <a-select
              mode="multiple"
              :size="size"
              :default-value="defaultJudges"
              v-if="visible"
              placeholder="请选择判断题"
              style="width: 100%"
              @popupScroll="popupScroll"
              @change="handleJudgeChange">
              <a-select-option v-for="judge in judges" :value="judge.name" :key="judge.id">
                {{ judge.name }}
              </a-select-option>
            </a-select>
          </a-form-item>

          <a-form-item label="选择写作题" :labelCol="labelCol" :wrapperCol="wrapperCol" enterButton="Search">
            <a-select
              mode="multiple"
              :size="size"
              :default-value="defaultPartIs"
              v-if="visible"
              placeholder="请选择写作题"
              style="width: 100%"
              @popupScroll="popupScroll"
              @change="handlePartIChange"
            >
              <a-select-option v-for="partI in partIs" :value="partI.name" :key="partI.id">
                {{ partI.name }}
              </a-select-option>
            </a-select>
          </a-form-item>

          <a-form-item label="选择听力A部分题目" :labelCol="labelCol" :wrapperCol="wrapperCol" enterButton="Search">
            <a-select
              mode="multiple"
              :size="size"
              :default-value="defaultPartIIAs"
              v-if="visible"
              placeholder="请选择听力A部分题目"
              style="width: 100%"
              @popupScroll="popupScroll"
              @change="handlePartIIAChange"
            >
              <a-select-option v-for="partIIA in partIIAs" :value="partIIA.name" :key="partIIA.id">
                {{ partIIA.name }}
              </a-select-option>
            </a-select>
          </a-form-item>

          <a-form-item label="选择听力B部分题目" :labelCol="labelCol" :wrapperCol="wrapperCol" enterButton="Search">
            <a-select
              mode="multiple"
              :size="size"
              :default-value="defaultPartIIBs"
              v-if="visible"
              placeholder="请选择听力B部分题目"
              style="width: 100%"
              @popupScroll="popupScroll"
              @change="handlePartIIBChange"
            >
              <a-select-option v-for="partIIB in partIIBs" :value="partIIB.name" :key="partIIB.id">
                {{ partIIB.name }}
              </a-select-option>
            </a-select>
          </a-form-item>

          <a-form-item label="选择听力C部分题目" :labelCol="labelCol" :wrapperCol="wrapperCol" enterButton="Search">
            <a-select
              mode="multiple"
              :size="size"
              :default-value="defaultPartIICs"
              v-if="visible"
              placeholder="请选择听力C部分题目"
              style="width: 100%"
              @popupScroll="popupScroll"
              @change="handlePartIICChange"
            >
              <a-select-option v-for="partIIC in partIICs" :value="partIIC.name" :key="partIIC.id">
                {{ partIIC.name }}
              </a-select-option>
            </a-select>
          </a-form-item>

          <a-form-item label="选择阅读A部分题目" :labelCol="labelCol" :wrapperCol="wrapperCol" enterButton="Search">
            <a-select
              mode="multiple"
              :size="size"
              :default-value="defaultPartIIIAs"
              v-if="visible"
              placeholder="请选择阅读A部分题目"
              style="width: 100%"
              @popupScroll="popupScroll"
              @change="handlePartIIIAChange"
            >
              <a-select-option v-for="partIIIA in partIIIAs" :value="partIIIA.name" :key="partIIIA.id">
                {{ partIIIA.name }}
              </a-select-option>
            </a-select>
          </a-form-item>

          <a-form-item label="选择阅读B部分题目" :labelCol="labelCol" :wrapperCol="wrapperCol" enterButton="Search">
            <a-select
              mode="multiple"
              :size="size"
              :default-value="defaultPartIIIBs"
              v-if="visible"
              placeholder="请选择阅读B部分题目"
              style="width: 100%"
              @popupScroll="popupScroll"
              @change="handlePartIIIBChange"
            >
              <a-select-option v-for="partIIIB in partIIIBs" :value="partIIIB.name" :key="partIIIB.id">
                {{ partIIIB.name }}
              </a-select-option>
            </a-select>
          </a-form-item>

          <a-form-item label="选择阅读C部分题目" :labelCol="labelCol" :wrapperCol="wrapperCol" enterButton="Search">
            <a-select
              mode="multiple"
              :size="size"
              :default-value="defaultPartIIICs"
              v-if="visible"
              placeholder="请选择阅读C部分题目"
              style="width: 100%"
              @popupScroll="popupScroll"
              @change="handlePartIIICChange"
            >
              <a-select-option v-for="partIIIC in partIIICs" :value="partIIIC.name" :key="partIIIC.id">
                {{ partIIIC.name }}
              </a-select-option>
            </a-select>
          </a-form-item>

          <a-form-item label="选择翻译题" :labelCol="labelCol" :wrapperCol="wrapperCol" enterButton="Search">
            <a-select
              mode="multiple"
              :size="size"
              :default-value="defaultPartIVs"
              v-if="visible"
              placeholder="请选择翻译题"
              style="width: 100%"
              @popupScroll="popupScroll"
              @change="handlePartIVChange"
            >
              <a-select-option v-for="partIV in partIVs" :value="partIV.name" :key="partIV.id">
                {{ partIV.name }}
              </a-select-option>
            </a-select>
          </a-form-item>
        </div>
      </a-form>
    </a-spin>
    <template slot="footer">
      <a-button key="back" @click="backward" v-if="currentStep > 0" :style="{ float: 'left' }">上一步</a-button>
      <a-button key="cancel" @click="handleCancel">取消</a-button>
      <a-button key="forward" :loading="confirmLoading" type="primary" @click="handleNext(currentStep)">
        {{ currentStep === 2 && '完成' || '下一步' }}
      </a-button>
    </template>
  </a-modal>
</template>

<script>
import { getExamQuestionTypeList, examUpdate } from '../../../api/exam'

const stepForms = [
  ['name', 'elapse', 'desc', 'avatar'],
  ['radioScore', 'checkScore', 'judgeScore', 'partIScore', 'partIIAScore', 'partIIBScore', 'partIICScore', 'partIIIAScore', 'partIIIBScore', 'partIIICScore', 'partIVScore'],
  ['option']
]

export default {
  name: 'ExamEditModal',
  data () {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 }
      },
      size: 'default',
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 13 }
      },
      visible: false,
      confirmLoading: false,
      currentStep: 0,
      mdl: {},
      form: this.$form.createForm(this),
      // 考试的对象
      exam: {},
      name: '',
      elapse: 0,
      desc: '',
      avatar: '',
      radioScore: 0,
      checkScore: 0,
      judgeScore: 0,
      partIScore: 0,
      partIIAScore: 0,
      partIIBScore: 0,
      partIICScore: 0,
      partIIIAScore: 0,
      partIIIBScore: 0,
      partIIICScore: 0,
      partIVScore: 0,
      radios: [],
      checks: [],
      judges: [],
      defaultRadios: [],
      defaultChecks: [],
      defaultJudges: [],
      partIs: [],
      partIIAs: [],
      partIIBs: [],
      partIICs: [],
      partIIIAs: [],
      partIIIBs: [],
      partIIICs: [],
      partIVs: []
    }
  },
  methods: {
    edit (exam) {
      Object.assign(this.exam, exam) // 深度拷贝
      this.visible = true
      // 每次编辑需要先清空下之前的数据
      this.radios = []
      this.checks = []
      this.judges = []
      this.defaultRadios = []
      this.defaultChecks = []
      this.defaultJudges = []
      this.name = exam.name
      this.elapse = exam.elapse
      this.desc = exam.desc
      this.avatar = exam.avatar
      this.radioScore = exam.radioScore
      this.checkScore = exam.checkScore
      this.judgeScore = exam.judgeScore
      this.partIScore = exam.partIScore
      this.partIIAScore = exam.partIIAScore
      this.partIIBScore = exam.partIIBScore
      this.partIICScore = exam.partIICScore
      this.partIIIAScore = exam.partIIIAScore
      this.partIIIBScore = exam.partIIIBScore
      this.partIIICScore = exam.partIIICScore
      this.partIVScore = exam.partIVScore
      const that = this
      // 从后端数据获取单选题、多选题和判断题的列表.在编辑的时候需要在点击"编辑的时候传入进来"
      getExamQuestionTypeList().then(res => {
        console.log(res)
        if (res.code !== 0) {
          that.$notification.error({
            message: '获取问题列表失败',
            description: res.msg
          })
        }
        console.log(res.data)
        that.radios = res.data.radios
        that.checks = res.data.checks
        that.judges = res.data.judges
        that.partIs = res.data.partIs
        that.partIIAs = res.data.partIIAs
        that.partIIBs = res.data.partIIBs
        that.partIICs = res.data.partIICs
        that.partIIIAs = res.data.partIIIAs
        that.partIIIBs = res.data.partIIIBs
        that.partIIICs = res.data.partIIICs
        that.partIVs = res.data.partIVs
        // 从exam里面的radios、checks、judges设置下上面的this里面的三个属性，把checked属性设置为true
        for (let i = 0; i < exam.radios.length; i++) { // 遍历所有的题目的选项
          that.defaultRadios.push(exam.radios[i].name)
        }
        that.handleRadioChange(that.defaultRadios)
        for (let i = 0; i < exam.checks.length; i++) { // 遍历所有的题目的选项
          that.defaultChecks.push(exam.checks[i].name)
        }
        that.handleCheckChange(that.defaultChecks)
        for (let i = 0; i < exam.judges.length; i++) { // 遍历所有的题目的选项
          that.defaultJudges.push(exam.judges[i].name)
        }
        that.handleJudgeChange(that.defaultJudges)
      }).catch(err => {
        // 失败就弹出警告消息
        this.$notification.error({
          message: '获取问题列表失败',
          description: err.message
        })
      })
    },
    popupScroll () {
      console.log('popupScroll')
    },
    handleNext (step) {
      // 处理下一步或者完成事件
      const { form: { validateFields } } = this
      const currentStep = step + 1
      if (currentStep <= 2) {
        // stepForms
        validateFields(stepForms[this.currentStep], (errors, values) => {
          if (!errors) {
            this.currentStep = currentStep
          }
        })
        return
      }
      // last step，最后一步，代表完成考试编写，需要点击"完成"创建考试
      this.confirmLoading = true
      console.log('提交数据到后端')
      this.confirmLoading = false
      this.exam.name = this.name
      this.exam.elapse = this.elapse
      this.exam.desc = this.desc
      this.exam.avatar = this.avatar
      this.exam.radioScore = this.radioScore
      this.exam.checkScore = this.checkScore
      this.exam.judgeScore = this.judgeScore
      this.exam.partIScore = this.partIScore
      this.exam.partIIAScore = this.partIIAScore
      this.exam.partIIBScore = this.partIIBScore
      this.exam.partIICScore = this.partIICScore
      this.exam.partIIIAScore = this.partIIIAScore
      this.exam.partIIIBScore = this.partIIIBScore
      this.exam.partIIICScore = this.partIIICScore
      this.exam.partIVScore = this.partIVScore
      // 设置单选题、多选题和判断题的内容，但是提交前需要保证都已经被正确更新了
      this.exam.radios = this.radios
      this.exam.checks = this.checks
      this.exam.judges = this.judges
      const that = this
      examUpdate(that.exam).then(res => {
        // 成功就跳转到结果页面
        console.log(res)
        if (res.code === 0) {
          that.$notification.success({
            message: '更新成功',
            description: '考试更新成功'
          })
          // 关闭弹出框
          that.visible = false
          that.currentStep = 0
          that.$emit('ok')
        }
      }).catch(err => {
        // 失败就弹出警告消息
        that.$notification.error({
          message: '考试更新失败',
          description: err.message
        })
      })
    },
    backward () {
      this.currentStep--
    },
    handleCancel () {
      // clear form & currentStep
      this.visible = false
      this.currentStep = 0
    },
    // 改变选择的题目列表,这里需要分单选、多选和判断进行单独更新，下面的代码要针对radios、checks和judges分别适配
    handleRadioChange (values) {
      console.log(values)
      // 更新单选题的信息
      for (let i = 0; i < this.radios.length; i++) { // 遍历所有的题目的选项
        // 取出一个选项的id
        const name = this.radios[i].name
        // 当前问题是否被问题创建者选中
        let checked = false
        for (let j = 0; j < values.length; j++) { // 拿着
          const value = values[j]
          if (name === value) {
            // 说明这个问题被考试创建者选中
            checked = true
            this.radios[i].checked = true
            break // 跳出内部的for循环
          }
        }
        // 这个选项遍历到最后，发现还不是答案(不在答案数组中)，那么就把这个选项的answer属性设置为false
        if (checked === false) {
          this.radios[i].checked = false
        }
      }
    },

    // 更新多选题信息
    handleCheckChange (values) {
      console.log(values)
      // 更新多选题的信息
      for (let i = 0; i < this.checks.length; i++) { // 遍历所有的题目的选项
        // 取出一个选项的id
        const name = this.checks[i].name
        // 当前问题是否被问题创建者选中
        let checked = false
        for (let j = 0; j < values.length; j++) { // 拿着
          const value = values[j]
          if (name === value) {
            // 说明这个问题被考试创建者选中
            checked = true
            this.checks[i].checked = true
            break // 跳出内部的for循环
          }
        }
        // 这个选项遍历到最后，发现还不是答案(不在答案数组中)，那么就把这个选项的answer属性设置为false
        if (checked === false) {
          this.checks[i].checked = false
        }
      }
    },

    // 更新判断题信息
    handleJudgeChange (values) {
      console.log(values)
      // 更新判断题的信息
      for (let i = 0; i < this.judges.length; i++) { // 遍历所有的题目的选项
        // 取出一个选项的id
        const name = this.judges[i].name
        // 当前问题是否被问题创建者选中
        let checked = false
        for (let j = 0; j < values.length; j++) { // 拿着
          const value = values[j]
          if (name === value) {
            // 说明这个问题被考试创建者选中
            checked = true
            this.judges[i].checked = true
            break // 跳出内部的for循环
          }
        }
        // 这个选项遍历到最后，发现还不是答案(不在答案数组中)，那么就把这个选项的answer属性设置为false
        if (checked === false) {
          this.judges[i].checked = false
        }
      }
    },

    handlePartIChange (values) {
      console.log(values)
      // 更新写作题的信息
      for (let i = 0; i < this.partIs.length; i++) { // 遍历所有的题目的选项
        // 取出一个选项的id
        const name = this.partIs[i].name
        // 当前问题是否被问题创建者选中
        let checked = false
        for (let j = 0; j < values.length; j++) { // 拿着
          const value = values[j]
          if (name === value) {
            // 说明这个问题被考试创建者选中
            checked = true
            this.partIs[i].checked = true
            break // 跳出内部的for循环
          }
        }
        // 这个选项遍历到最后，发现还不是答案(不在答案数组中)，那么就把这个选项的answer属性设置为false
        if (checked === false) {
          this.partIs[i].checked = false
        }
      }
    },

    handlePartIIAChange (values) {
      console.log(values)
      // 更新听力A部分的信息
      for (let i = 0; i < this.partIIAs.length; i++) { // 遍历所有的题目的选项
        // 取出一个选项的id
        const name = this.partIIAs[i].name
        // 当前问题是否被问题创建者选中
        let checked = false
        for (let j = 0; j < values.length; j++) { // 拿着
          const value = values[j]
          if (name === value) {
            // 说明这个问题被考试创建者选中
            checked = true
            this.partIIAs[i].checked = true
            break // 跳出内部的for循环
          }
        }
        // 这个选项遍历到最后，发现还不是答案(不在答案数组中)，那么就把这个选项的answer属性设置为false
        if (checked === false) {
          this.partIIAs[i].checked = false
        }
      }
    },

    handlePartIIBChange (values) {
      console.log(values)
      // 更新听力B部分的信息
      for (let i = 0; i < this.partIIBs.length; i++) { // 遍历所有的题目的选项
        // 取出一个选项的id
        const name = this.partIIBs[i].name
        // 当前问题是否被问题创建者选中
        let checked = false
        for (let j = 0; j < values.length; j++) { // 拿着
          const value = values[j]
          if (name === value) {
            // 说明这个问题被考试创建者选中
            checked = true
            this.partIIBs[i].checked = true
            break // 跳出内部的for循环
          }
        }
        // 这个选项遍历到最后，发现还不是答案(不在答案数组中)，那么就把这个选项的answer属性设置为false
        if (checked === false) {
          this.partIIBs[i].checked = false
        }
      }
    },

    handlePartIICChange (values) {
      console.log(values)
      // 更新听力C部分的信息
      for (let i = 0; i < this.partIICs.length; i++) { // 遍历所有的题目的选项
        // 取出一个选项的id
        const name = this.partIICs[i].name
        // 当前问题是否被问题创建者选中
        let checked = false
        for (let j = 0; j < values.length; j++) { // 拿着
          const value = values[j]
          if (name === value) {
            // 说明这个问题被考试创建者选中
            checked = true
            this.partIICs[i].checked = true
            break // 跳出内部的for循环
          }
        }
        // 这个选项遍历到最后，发现还不是答案(不在答案数组中)，那么就把这个选项的answer属性设置为false
        if (checked === false) {
          this.partIICs[i].checked = false
        }
      }
    },

    handlePartIIIAChange (values) {
      console.log(values)
      // 更新阅读A部分的信息
      for (let i = 0; i < this.partIIIAs.length; i++) { // 遍历所有的题目的选项
        // 取出一个选项的id
        const name = this.partIIIAs[i].name
        // 当前问题是否被问题创建者选中
        let checked = false
        for (let j = 0; j < values.length; j++) { // 拿着
          const value = values[j]
          if (name === value) {
            // 说明这个问题被考试创建者选中
            checked = true
            this.partIIIAs[i].checked = true
            break // 跳出内部的for循环
          }
        }
        // 这个选项遍历到最后，发现还不是答案(不在答案数组中)，那么就把这个选项的answer属性设置为false
        if (checked === false) {
          this.partIIIAs[i].checked = false
        }
      }
    },

    handlePartIIIBChange (values) {
      console.log(values)
      // 更新阅读B部分的信息
      for (let i = 0; i < this.partIIIBs.length; i++) { // 遍历所有的题目的选项
        // 取出一个选项的id
        const name = this.partIIIBs[i].name
        // 当前问题是否被问题创建者选中
        let checked = false
        for (let j = 0; j < values.length; j++) { // 拿着
          const value = values[j]
          if (name === value) {
            // 说明这个问题被考试创建者选中
            checked = true
            this.partIIIBs[i].checked = true
            break // 跳出内部的for循环
          }
        }
        // 这个选项遍历到最后，发现还不是答案(不在答案数组中)，那么就把这个选项的answer属性设置为false
        if (checked === false) {
          this.partIIIBs[i].checked = false
        }
      }
    },

    handlePartIIICChange (values) {
      console.log(values)
      // 更新阅读C部分的信息
      for (let i = 0; i < this.partIIICs.length; i++) { // 遍历所有的题目的选项
        // 取出一个选项的id
        const name = this.partIIICs[i].name
        // 当前问题是否被问题创建者选中
        let checked = false
        for (let j = 0; j < values.length; j++) { // 拿着
          const value = values[j]
          if (name === value) {
            // 说明这个问题被考试创建者选中
            checked = true
            this.partIIICs[i].checked = true
            break // 跳出内部的for循环
          }
        }
        // 这个选项遍历到最后，发现还不是答案(不在答案数组中)，那么就把这个选项的answer属性设置为false
        if (checked === false) {
          this.partIIICs[i].checked = false
        }
      }
    },

    handlePartIVChange (values) {
      console.log(values)
      // 更新翻译题的信息
      for (let i = 0; i < this.partIVs.length; i++) { // 遍历所有的题目的选项
        // 取出一个选项的id
        const name = this.partIVs[i].name
        // 当前问题是否被问题创建者选中
        let checked = false
        for (let j = 0; j < values.length; j++) { // 拿着
          const value = values[j]
          if (name === value) {
            // 说明这个问题被考试创建者选中
            checked = true
            this.partIVs[i].checked = true
            break // 跳出内部的for循环
          }
        }
        // 这个选项遍历到最后，发现还不是答案(不在答案数组中)，那么就把这个选项的answer属性设置为false
        if (checked === false) {
          this.partIVs[i].checked = false
        }
      }
    }
  }
}
</script>
