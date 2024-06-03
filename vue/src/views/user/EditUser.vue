<template>

  <div style="width: 80%">

    <div style="height: 110px; width: 1000px; background-color: crimson; margin-bottom: 40px">
      <h2 style="color: white; line-height: 90px ;margin-left: 50px">编辑用户</h2>
    </div>


    <el-form :inline="true" :model="form"  label-width="180px" size="medium">

      <el-form-item label="卡号">
        <el-input v-model="form.cardnum" disabled></el-input>
      </el-form-item>

      <el-form-item label="用户名">
        <el-input v-model="form.name" placeholder="请输入用户名"></el-input>
      </el-form-item>

      <el-form-item label="手机号码">
        <el-input v-model="form.phone" placeholder="请输入手机号码"></el-input>
      </el-form-item>

      <el-form-item label="邮箱">
        <el-input v-model="form.email" placeholder="请输入邮箱"></el-input>
      </el-form-item>

      <el-form-item label="性别">
        <el-input v-model="form.sex" placeholder="请输入性别"></el-input>
      </el-form-item>

      <el-form-item label="密码">
        <el-input v-model="form.keynum" placeholder="请输入密码"></el-input>
      </el-form-item>

      <el-form-item label="年龄">
        <el-input v-model="form.age" placeholder="请输入年龄"></el-input>
      </el-form-item>

      <el-form-item label="地址">
        <el-input v-model="form.address" placeholder="请输入地址"></el-input>
      </el-form-item>

    </el-form>

    <div style="text-align: center; margin-left: 150px; margin-top: 30px">

      <el-button type="primary" @click="update" size="medium">更新<i class="el-icon-upload el-icon--right"></i>
      </el-button>

      <el-button type="danger" @click="reset" size="medium">重置<i class="el-icon-upload el-icon--right"></i>
      </el-button>

    </div>

  </div>

</template>


<script>
import request from "@/utils/request";

export default {
  name: "EditUser",
  data() {
    return{
     form:{}
    }
  },

  created() {
    this.init();
  },

  methods: {

    init(){
      const id = this.$route.query.id
      request.get("/user/" + id).then(res =>{
        this.form = res.data;
      })
    },

    update() {
      request.put('/user/update', this.form).then(res =>{
        if (res.code === '200'){
          this.$notify.success('更新成功')
          this.$router.push("/myUser")
        } else{
          this.$notify.error(res.msg)
        }
      })
    },

    reset(){
      this.init();
    }

  }

}
</script>

<style scoped>

</style>