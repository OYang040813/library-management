<template>

  <div style="width: 80%">

    <div style="height: 180px; width: 1000px; background-color: darkslategray; margin-bottom: 80px">
      <h2 style="color: white; line-height: 160px ;margin-left: 50px">编辑管理员</h2>
    </div>


    <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" label-width="180px" size="medium">

      <el-form-item label="虚拟号">
        <el-input v-model="form.cardnum" disabled></el-input>
      </el-form-item>

      <el-form-item label="虚拟名" prop="name">
        <el-input v-model="form.name" placeholder="请输入新虚拟名"></el-input>
      </el-form-item>

      <el-form-item label="密码" prop="keynum">
        <el-input v-model="form.keynum" disabled></el-input>
      </el-form-item>

    </el-form>

    <div style="text-align: center; margin-left: 150px; margin-top: 30px">

      <el-button type="primary" @click="update('ruleForm')" size="medium">更新<i class="el-icon-upload el-icon--right"></i>
      </el-button>

      <el-button type="danger" @click="reset('ruleForm')" size="medium">重置<i class="el-icon-upload el-icon--right"></i>
      </el-button>

    </div>

  </div>

</template>


<script>
import request from "@/utils/request";

export default {
  name: "EditCategory",
  data() {
    return{

      rules: {
        name: [
          {required: true, message: '虚拟名不能为空', trigger: 'blur'},
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

  created() {
    this.init();
  },

  methods: {

    init(){
      const id = this.$route.query.id
      request.get("/admin/" + id).then(res =>{
        this.form = res.data;
      })
    },

    update(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request.put('/admin/update', this.form).then(res =>{
            if (res.code === '200'){
              this.$notify.success('更新成功')
              this.$router.push("/myadmin")
            } else{
              this.$notify.error(res.msg)
            }
          });
        } else {
          this.$message.error('更新失败,请检查!!!')
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