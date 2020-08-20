<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="${column.comments}" prop="skuId">
      <el-input v-model="dataForm.skuId" placeholder="${column.comments}"></el-input>
    </el-form-item>
    <el-form-item label="${column.comments}" prop="spuId">
      <el-input v-model="dataForm.spuId" placeholder="${column.comments}"></el-input>
    </el-form-item>
    <el-form-item label="${column.comments}" prop="spuName">
      <el-input v-model="dataForm.spuName" placeholder="${column.comments}"></el-input>
    </el-form-item>
    <el-form-item label="${column.comments}" prop="memberNickName">
      <el-input v-model="dataForm.memberNickName" placeholder="${column.comments}"></el-input>
    </el-form-item>
    <el-form-item label="${column.comments}" prop="star">
      <el-input v-model="dataForm.star" placeholder="${column.comments}"></el-input>
    </el-form-item>
    <el-form-item label="${column.comments}" prop="memberIp">
      <el-input v-model="dataForm.memberIp" placeholder="${column.comments}"></el-input>
    </el-form-item>
    <el-form-item label="${column.comments}" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="${column.comments}"></el-input>
    </el-form-item>
    <el-form-item label="${column.comments}" prop="showStatus">
      <el-input v-model="dataForm.showStatus" placeholder="${column.comments}"></el-input>
    </el-form-item>
    <el-form-item label="${column.comments}" prop="spuAttributes">
      <el-input v-model="dataForm.spuAttributes" placeholder="${column.comments}"></el-input>
    </el-form-item>
    <el-form-item label="${column.comments}" prop="likesCount">
      <el-input v-model="dataForm.likesCount" placeholder="${column.comments}"></el-input>
    </el-form-item>
    <el-form-item label="${column.comments}" prop="replyCount">
      <el-input v-model="dataForm.replyCount" placeholder="${column.comments}"></el-input>
    </el-form-item>
    <el-form-item label="${column.comments}" prop="resources">
      <el-input v-model="dataForm.resources" placeholder="${column.comments}"></el-input>
    </el-form-item>
    <el-form-item label="${column.comments}" prop="content">
      <el-input v-model="dataForm.content" placeholder="${column.comments}"></el-input>
    </el-form-item>
    <el-form-item label="${column.comments}" prop="memberIcon">
      <el-input v-model="dataForm.memberIcon" placeholder="${column.comments}"></el-input>
    </el-form-item>
    <el-form-item label="${column.comments}" prop="commentType">
      <el-input v-model="dataForm.commentType" placeholder="${column.comments}"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          skuId: '',
          spuId: '',
          spuName: '',
          memberNickName: '',
          star: '',
          memberIp: '',
          createTime: '',
          showStatus: '',
          spuAttributes: '',
          likesCount: '',
          replyCount: '',
          resources: '',
          content: '',
          memberIcon: '',
          commentType: ''
        },
        dataRule: {
          skuId: [
            { required: true, message: '${column.comments}不能为空', trigger: 'blur' }
          ],
          spuId: [
            { required: true, message: '${column.comments}不能为空', trigger: 'blur' }
          ],
          spuName: [
            { required: true, message: '${column.comments}不能为空', trigger: 'blur' }
          ],
          memberNickName: [
            { required: true, message: '${column.comments}不能为空', trigger: 'blur' }
          ],
          star: [
            { required: true, message: '${column.comments}不能为空', trigger: 'blur' }
          ],
          memberIp: [
            { required: true, message: '${column.comments}不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '${column.comments}不能为空', trigger: 'blur' }
          ],
          showStatus: [
            { required: true, message: '${column.comments}不能为空', trigger: 'blur' }
          ],
          spuAttributes: [
            { required: true, message: '${column.comments}不能为空', trigger: 'blur' }
          ],
          likesCount: [
            { required: true, message: '${column.comments}不能为空', trigger: 'blur' }
          ],
          replyCount: [
            { required: true, message: '${column.comments}不能为空', trigger: 'blur' }
          ],
          resources: [
            { required: true, message: '${column.comments}不能为空', trigger: 'blur' }
          ],
          content: [
            { required: true, message: '${column.comments}不能为空', trigger: 'blur' }
          ],
          memberIcon: [
            { required: true, message: '${column.comments}不能为空', trigger: 'blur' }
          ],
          commentType: [
            { required: true, message: '${column.comments}不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/product/pmsspucomment/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.skuId = data.pmsSpuComment.skuId
                this.dataForm.spuId = data.pmsSpuComment.spuId
                this.dataForm.spuName = data.pmsSpuComment.spuName
                this.dataForm.memberNickName = data.pmsSpuComment.memberNickName
                this.dataForm.star = data.pmsSpuComment.star
                this.dataForm.memberIp = data.pmsSpuComment.memberIp
                this.dataForm.createTime = data.pmsSpuComment.createTime
                this.dataForm.showStatus = data.pmsSpuComment.showStatus
                this.dataForm.spuAttributes = data.pmsSpuComment.spuAttributes
                this.dataForm.likesCount = data.pmsSpuComment.likesCount
                this.dataForm.replyCount = data.pmsSpuComment.replyCount
                this.dataForm.resources = data.pmsSpuComment.resources
                this.dataForm.content = data.pmsSpuComment.content
                this.dataForm.memberIcon = data.pmsSpuComment.memberIcon
                this.dataForm.commentType = data.pmsSpuComment.commentType
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/product/pmsspucomment/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'skuId': this.dataForm.skuId,
                'spuId': this.dataForm.spuId,
                'spuName': this.dataForm.spuName,
                'memberNickName': this.dataForm.memberNickName,
                'star': this.dataForm.star,
                'memberIp': this.dataForm.memberIp,
                'createTime': this.dataForm.createTime,
                'showStatus': this.dataForm.showStatus,
                'spuAttributes': this.dataForm.spuAttributes,
                'likesCount': this.dataForm.likesCount,
                'replyCount': this.dataForm.replyCount,
                'resources': this.dataForm.resources,
                'content': this.dataForm.content,
                'memberIcon': this.dataForm.memberIcon,
                'commentType': this.dataForm.commentType
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
