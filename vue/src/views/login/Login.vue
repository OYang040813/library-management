<template>
  <div style="height: 100vh; overflow: hidden; position: relative">

<!--    <el-card v-if="loginAdmin.id" style="background-color: white; position: absolute; top: 50%; width: fit-content;-->
<!--    left: 50%; transform: translate(-50%, -50%); z-index: 1000;">-->
<!--      <slide-verify :l="42"-->
<!--                      :r="10"-->
<!--                      :w="310"-->
<!--                      :h="155"-->
<!--                       slider-text="向右滑动"-->
<!--                      @success="onSuccess"-->
<!--                      @fail="onFail"-->
<!--                      @refresh="onRefresh"-->
<!--      ></slide-verify>-->
<!--    </el-card>-->

    <div style="width: 500px; height: 400px;background-color: white; border-radius: 10px;
    margin: 95px auto; padding: 50px">
      <div style=" margin: 10px; text-align: center; font-size: 25px; font-weight: bold;
            color: dodgerblue">登录</div>
      <el-form :model="admin" :rules="rules" ref="loginForm">

        <div style="margin-top: 40px">
          <el-form-item prop="name">
          <el-input placeholder="请输入账号" prefix-icon="el-icon-user" size="medium" v-model="admin.name"></el-input>
          </el-form-item>
        </div>


        <div style="margin-top: 40px">
          <el-form-item prop="keynum">
            <el-input placeholder="请输入密码" show-password prefix-icon="el-icon-lock" size="medium" v-model="admin.keynum"></el-input>
          </el-form-item>
        </div>

        <div style="text-align: center; margin-top: 50px">
          <el-form-item>

                <el-button type="primary" @click="login" size="medium">登录<i class="el-icon-upload el-icon--right"></i>
            </el-button>

            <el-button type="danger" @click="reset" size="medium">重置<i class="el-icon-upload el-icon--right"></i>
            </el-button>

          </el-form-item>
        </div>

      </el-form>
    </div>

  </div>
</template>




<script>
import request from "@/utils/request";
import Cookies from "js-cookie";

export default {
  name: "Login",

  data(){
    return{

      loginAdmin:{},

      rules: {
        name: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
        ],
        keynum: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ],
      },

      admin: {
        name:'',
        keynum:''
      }
    }
  },

  methods: {
    login() {
      this.$refs["loginForm"].validate((valid) => {
        if (valid) {
          request.post('/admin/login', this.admin).then(res => {
            if (res.code === '200') {
              this.loginAdmin = res.data  //滑块组件出现
              Cookies.set('admin', JSON.stringify(this.loginAdmin))
              this.$notify.success('登陆成功')
              this.$router.push('/')
            } else {
              this.$notify.error(res.msg)
            }
          })
        } else {
          this.$message.error('请正确填入用户名与密码！')
        }
      });
    },

    reset() {
      this.$refs["loginForm"].resetFields();
    },

    onSuccess(){ //滑块验证成功后触发（回调函数）
      Cookies.set('admin', JSON.stringify(this.loginAdmin))
      this.$notify.success('登陆成功')
      this.$router.push('/')
    },

    onFail(){

    },

    onRefresh(){
      console.log('refresh')
    },

  }
}
</script>