<template>

  <div style="width: 80%">

    <div style="height: 110px; width: 1000px; background-color: slateblue; margin-bottom: 40px">
      <h2 style="color: white; line-height: 90px ;margin-left: 50px; cursor: pointer">新增用户</h2>
    </div>


    <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" label-width="170px" size="medium">

      <el-form-item label="姓名" prop="name">
        <el-input v-model="form.name" placeholder="请输入用户名"></el-input>
      </el-form-item>

      <el-form-item label="手机号码" prop="phone">
        <el-input v-model="form.phone" placeholder="请输入手机号码"></el-input>
      </el-form-item>

      <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.email" placeholder="请输入邮箱"></el-input>
      </el-form-item>

      <el-form-item label="性别">
        <div>
          <el-radio-group v-model="form.sex">
            <el-radio-button label="男"></el-radio-button>
            <el-radio-button label="女"></el-radio-button>
          </el-radio-group>
        </div>
      </el-form-item>

      <el-form-item label="密码" prop="keynum">
        <el-input v-model="form.keynum" placeholder="请输入密码" show-password minlength="1"></el-input>
      </el-form-item>

      <el-form-item label="确认密码" prop="keynum_repeat">
        <el-input v-model="form.keynum_repeat" placeholder="请再次确认密码" show-password minlength="1"></el-input>
      </el-form-item>

      <el-form-item label="年龄" prop="age">
        <el-input v-model="form.age" placeholder="请输入年龄"></el-input>
      </el-form-item>

      <el-form-item label="地址" prop="address">
        <el-input v-model="form.address" placeholder="请输入地址"></el-input>
      </el-form-item>

    </el-form>

    <div style="text-align: center; margin-left: 150px; margin-top: 30px">

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
  name: "AddUser",
  data() {

    const checkAge = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('年龄不能为空'));
      }
      if (!/^[0-9]+$/.test(value)) {
        callback(new Error('请输入数字值'));
      } else {
        if (parseInt(value) >100 || parseInt(value) <=0) {
          callback(new Error('请输入合理年龄'));
        }
      }
      callback();
    };
    const checkPhone = (rule, value, callback) => {
      if (!/^[1][3,4,5,6,7,8,9][0-9]{9}$/.test(value)) {
        callback(new Error('请输入合法手机号'));
      }
      callback();
    };
    const checkkeynum = (rule, value, callback) => {
      if (value===this.form.keynum) {
        callback();
      }
      callback(new Error('密码不一致'));
    };

    return {

      rules: {
        name: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 2, max: 16, message: '长度在2到16个字符', trigger: 'blur'}
        ],
        phone: [
          {validator: checkPhone, trigger: 'blur'}
        ],
        email: [
          {required: true, message: '请输入邮箱', trigger: 'blur'}
        ],
        keynum: [
          {required: true, message: '密码不能为空', trigger: 'blur'},
          {min: 6, max: 12, message: '长度在6到12个字符', trigger: 'blur'}
        ],
        keynum_repeat: [
          {validator: checkkeynum, trigger: 'blur'}
        ],
        age: [
          {validator: checkAge, trigger: 'blur'}
        ],
      },

      form: {
        id: '',
        name: '',
        cardnum: '',
        email: '',
        phone: '',
        age: '',
        keynum: '',
        keynum_repeat: '',
        sex: '男'
      }
    };
  },
  methods: {

    save(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request.post('/user/save', this.form).then(res => {
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