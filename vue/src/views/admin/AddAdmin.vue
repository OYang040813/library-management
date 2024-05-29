<template>

  <div style="width: 80%">

    <div style="height: 180px; width: 1000px; background-color: gray; margin-bottom: 80px">
      <h2 style="color: white; line-height: 160px ;margin-left: 50px; cursor: pointer">新增管理员</h2>
    </div>


    <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" label-width="130px" size="medium">

      <el-form-item label="虚拟名" prop="name">
        <el-input v-model="form.name" placeholder="请输入管理员虚拟名"></el-input>
      </el-form-item>

      <el-form-item label="密码" style="margin-left: 95px" prop="keynum">
        <el-input v-model="form.keynum" placeholder="请输入管理员验证密码" show-password minlength="1"></el-input>
      </el-form-item>

    </el-form>

    <div style="text-align: center; margin-left: 80px; margin-top: 50px">

      <el-button type="primary" @click="save('ruleForm')" size="medium">提交<i class="el-icon-upload el-icon--right"></i>
      </el-button>

      <el-button type="danger" @click="reset('ruleForm')" size="medium">重置<i class="el-icon-upload el-icon--right"></i>
      </el-button>

    </div>

  </div>

</template>


<script>
import request from "@/utils/request";

export default {
  name: "AddAdmin",
  data() {

    return{

      rules: {
        name: [
          {required: true, message: '请输入虚拟名', trigger: 'blur'},
          {min: 2, max: 16, message: '长度在2到16个字符', trigger: 'blur'}
        ],
        keynum: [
          {required: true, message: '密码不能为空', trigger: 'blur'},
          {min: 6, max: 12, message: '长度在6到12个字符', trigger: 'blur'}
        ],
      },

      form:{
        id: '',
        name: '',
        keynum: '',
        cardnum:''
      }
    }
  },
  methods: {

    save(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request.post('/admin/save', this.form).then(res => {
            if (res.code === '200') {
              this.$notify.success('新增成功')
              this.$refs[formName].resetFields()
            } else {
              this.$notify.error(res.msg)
            }
          })
        } else {
          this.$message.error('新增失败,请检查!!!')
        }
      });
    },

    reset(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>

<style scoped>

</style>