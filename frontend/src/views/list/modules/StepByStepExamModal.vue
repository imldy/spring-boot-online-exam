<template>
  <a-modal
    title="创建考试"
    :width="640"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <a-steps :current="currentStep" :style="{ marginBottom: '28px' }" size="small">
        <a-step title="考试描述"/>
        <a-step title="每题分数"/>
        <a-step title="选择题目"/>
      </a-steps>
      <a-form :form="form">
        <!-- step1 -->
        <div v-show="currentStep === 0">
          <a-form-item
            label="考试名称"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
          >
            <a-input v-decorator="['name', {rules: [{required: true}]}]"/>
          </a-form-item>
          <a-form-item
            label="考试限时"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
          >
            <a-input-number
              :min="1"
              :max="200"
              v-decorator="['elapse', {initialValue: '130',rules: [{required: true}]}]"
            />
            分钟
          </a-form-item>
          <a-form-item
            label="考试简述"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
          >
            <a-textarea :rows="2" v-decorator="['desc', {rules: [{required: true}]}]"></a-textarea>
          </a-form-item>
          <a-form-item
            label="考试封面"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
          >
            <!-- 创建考试的时候传入的图片 -->
            <div id="summernote-exam-avatar-create"></div>
          </a-form-item>
        </div>
        <div v-show="currentStep === 1">
          <a-form-item
            label="写作题"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
          >
            <a-input-number
              :min="1"
              :max="20"
              v-decorator="['examScorePartI', {initialValue: '5',rules: [{required: true}]}]"
            />
            分
          </a-form-item>

          <a-form-item
            label="听力A部分"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
          >
            <a-input-number
              :min="1"
              :max="20"
              v-decorator="['examScorePartIIA', {initialValue: '5',rules: [{required: true}]}]"
            />
            分
          </a-form-item>

          <a-form-item
            label="听力B部分"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
          >
            <a-input-number
              :min="1"
              :max="20"
              v-decorator="['examScorePartIIB', {initialValue: '5',rules: [{required: true}]}]"
            />
            分
          </a-form-item>

          <a-form-item
            label="听力C部分"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
          >
            <a-input-number
              :min="1"
              :max="20"
              v-decorator="['examScorePartIIC', {initialValue: '5',rules: [{required: true}]}]"
            />
            分
          </a-form-item>

          <a-form-item
            label="阅读A部分"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
          >
            <a-input-number
              :min="1"
              :max="20"
              v-decorator="['examScorePartIIIA', {initialValue: '5',rules: [{required: true}]}]"
            />
            分
          </a-form-item>

          <a-form-item
            label="阅读B部分"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
          >
            <a-input-number
              :min="1"
              :max="20"
              v-decorator="['examScorePartIIIB', {initialValue: '5',rules: [{required: true}]}]"
            />
            分
          </a-form-item>

          <a-form-item
            label="阅读C部分"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
          >
            <a-input-number
              :min="1"
              :max="20"
              v-decorator="['examScorePartIIIC', {initialValue: '5',rules: [{required: true}]}]"
            />
            分
          </a-form-item>

          <a-form-item
            label="翻译题"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
          >
            <a-input-number
              :min="1"
              :max="20"
              v-decorator="['examScorePartIV', {initialValue: '5',rules: [{required: true}]}]"
            />
            分
          </a-form-item>
        </div>

        <div v-show="currentStep === 2">
          <a-form-item
            label="选择写作题"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            enterButton="Search"
          >
            <a-select
              mode="multiple"
              :size="size"
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

          <a-form-item
            label="选择听力A部分题目"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            enterButton="Search"
          >
            <a-select
              mode="multiple"
              :size="size"
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

          <a-form-item
            label="选择听力B部分题目"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            enterButton="Search"
          >
            <a-select
              mode="multiple"
              :size="size"
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

          <a-form-item
            label="选择听力C部分题目"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            enterButton="Search"
          >
            <a-select
              mode="multiple"
              :size="size"
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

          <a-form-item
            label="选择阅读A部分题目"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            enterButton="Search"
          >
            <a-select
              mode="multiple"
              :size="size"
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

          <a-form-item
            label="选择阅读B部分题目"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            enterButton="Search"
          >
            <a-select
              mode="multiple"
              :size="size"
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

          <a-form-item
            label="选择阅读C部分题目"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            enterButton="Search"
          >
            <a-select
              mode="multiple"
              :size="size"
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

          <a-form-item
            label="选择翻译题"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            enterButton="Search"
          >
            <a-select
              mode="multiple"
              :size="size"
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
import '../../../plugins/summernote'
import $ from 'jquery'
import { getExamQuestionTypeList, examCreate } from '../../../api/exam'

const stepForms = [
  ['name', 'elapse', 'desc'],
  ['examScorePartI', 'examScorePartIIA', 'examScorePartIIB', 'examScorePartIIC', 'examScorePartIIIA', 'examScorePartIIIB', 'examScorePartIIIC', 'examScorePartIV'],
  ['option']
]

export default {
  name: 'StepByStepExamModal',
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
      // 写作题对象列表
      partIs: [],
      // 听力A部分对象列表
      partIIAs: [],
      // 听力B部分对象列表
      partIIBs: [],
      // 听力C部分对象列表
      partIICs: [],
      // 阅读A部分对象列表
      partIIIAs: [],
      // 阅读B部分对象列表
      partIIIBs: [],
      // 阅读C部分对象列表
      partIIICs: [],
      // 翻译题对象列表
      partIVs: []
    }
  },
  updated () {
    this.initSummernote()
  },
  methods: {
    initSummernote () {
      console.log('初始化富文本插件')
      $('#summernote-exam-avatar-create').summernote({
        lang: 'zh-CN',
        placeholder: '粘贴截图到这即可，图片最好不要大于80*80',
        height: 200,
        width: 320,
        htmlMode: true,
        toolbar: [],
        fontSizes: ['8', '9', '10', '11', '12', '14', '18', '24', '36'],
        fontNames: [
          'Arial', 'Arial Black', 'Comic Sans MS', 'Courier New',
          'Helvetica Neue', 'Helvetica', 'Impact', 'Lucida Grande',
          'Tahoma', 'Times New Roman', 'Verdana'
        ]
      })
    },
    create () {
      this.visible = true
      // 从后端数据获取题目列表
      getExamQuestionTypeList().then(res => {
        console.log(res)
        if (res.code === 0) {
          console.log(res.data)
          this.partIs = res.data.partIs
          this.partIIAs = res.data.partIIAs
          this.partIIBs = res.data.partIIBs
          this.partIICs = res.data.partIICs
          this.partIIIAs = res.data.partIIIAs
          this.partIIIBs = res.data.partIIIBs
          this.partIIICs = res.data.partIIICs
          this.partIVs = res.data.partIVs
        } else {
          this.$notification.error({
            message: '获取问题列表失败',
            description: res.msg
          })
        }
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
      validateFields((errors, values) => { // values就是表单中校验的值，后面提交到后端接口时主要就是用这个values
        values.avatar = $('#summernote-exam-avatar-create').summernote('code')
        // 设置题目内容，但是提交前需要保证都已经被正确更新了
        values.partIs = this.partIs
        values.partIIAs = this.partIIAs
        values.partIIBs = this.partIIBs
        values.partIICs = this.partIICs
        values.partIIIAs = this.partIIIAs
        values.partIIIBs = this.partIIIBs
        values.partIIICs = this.partIIICs
        values.partIVs = this.partIVs
        console.log('提交数据到后端')
        this.confirmLoading = false
        if (!errors) {
          // 在这里把创建的考试的内容(存放在values中)提交给后端接口，需要的参数都已经封装成values这个json啦
          console.log('values:', values)
          // 把data中的question属性提交到后端，待写完后端接口.把前端的创建的题型提交到后端
          examCreate(values).then(res => {
            // 成功就跳转到结果页面
            console.log(res)
            if (res.code === 0) {
              this.$notification.success({
                message: '创建成功',
                description: '考试创建成功'
              })
              // 关闭弹出框
              this.visible = false
              this.$emit('ok')
            }
          }).catch(err => {
            // 失败就弹出警告消息
            this.$notification.error({
              message: '考试创建失败',
              description: err.message
            })
          })
        } else {
          this.confirmLoading = false
        }
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
    // 更新写作题信息
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

    // 更新听力A部分信息
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

    // 更新听力B部分信息
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

    // 更新听力C部分信息
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

    // 更新阅读A部分信息
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

    // 更新阅读B部分信息
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

    // 更新阅读C部分信息
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

    // 更新翻译题信息
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
