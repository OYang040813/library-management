<template>

  <div style="width: 80%">

    <div style="height: 180px; width: 1000px; background-color: rgba(222,65,255,0.35); margin-bottom: 80px">
      <h2 style="color: white; line-height: 160px ;margin-left: 50px; cursor: pointer">新增一级分类</h2>
    </div>


    <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" label-width="130px" size="medium">

      <el-form-item label="名称" prop="name">
        <el-input v-model="form.name" placeholder="请输入分类名称"></el-input>
      </el-form-item>

      <el-form-item label="备注" style="margin-left: 95px" prop="remark">
        <el-input v-model="form.remark" placeholder="请输入分类备注"></el-input>
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
  name: "AddCategory",
  data() {
    return{

      rules: {
        name: [
          {required: true, message: '名称不能为空', trigger: 'blur'},
          {min: 2, max: 16, message: '长度在2到16个字符', trigger: 'blur'}
        ],
        remark: [
          {max: 20, message: '长度不能超过20个字符', trigger: 'blur'}
        ],
      },

      form:{
        id: '',
        name: '',
        remark: '',
      }
    }
  },
  methods: {

    save(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request.post('/category/save', this.form).then(res => {
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