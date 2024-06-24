<template>

  <div style="width: 80%">

    <div style="height: 110px; width: 1000px; background-color: lemonchiffon; margin-bottom: 40px">
      <h2 style="color: white; line-height: 90px ;margin-left: 50px; cursor: pointer">新增图书</h2>
    </div>


    <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" label-width="170px" size="medium">

      <el-form-item label="名称" prop="name">
        <el-input v-model="form.name" placeholder="请输入书名"></el-input>
      </el-form-item>

      <el-form-item label="书号" prop="bookNo">
        <el-input style="width: 220px" v-model="form.bookNo" placeholder="请输入书号"></el-input>
      </el-form-item>

      <el-form-item label="出版日期" prop="publishDate">
        <el-date-picker
            style="width: 210px"
            v-model="form.publishDate"
            type="datetime"
            value-format="yyyy-MM-dd"
            placeholder="请选择出版日期">
        </el-date-picker>
      </el-form-item>

      <el-form-item label="出版商" prop="publisher">
        <el-input style="width: 220px" v-model="form.publisher" placeholder="请输入出版商"></el-input>
      </el-form-item>

      <el-form-item label="作者" prop="author">
        <el-input style="width: 210px" v-model="form.author" placeholder="请输入作者"></el-input>
      </el-form-item>

      <el-form-item label="分类" prop="category">
        <el-cascader
            v-model="form.category"
            :options="categories"
            :props="{ expandTrigger: 'hover' }"
            @change="handleChange">
        </el-cascader>
      </el-form-item>

      <el-form-item label="封面" prop="cover">
        <el-input style="width: 610px" v-model="form.cover" placeholder="请以链接输入图书封面"></el-input>
      </el-form-item>

      <el-form-item label="图书描述" prop="description">
        <el-input style="width: 610px" type="textarea" v-model="form.description" placeholder="请输入图书描述"></el-input>
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
import category from "@/views/category/Category.vue";

export default {
  name: "AddBook",
  computed: {
    category() {
      return category
    }
  },
  data() {
    return {

      rules: {
        name: [
          {required: true, message: '书名不能为空', trigger: 'blur'},
          {min: 2, max: 16, message: '长度在2到20个字符', trigger: 'blur'}
        ],
        publishDate: [
          {required: true, message: '请输入出版日期', trigger: 'blur'}
        ],
        author: [
          {required: true, message: '请输入作者', trigger: 'blur'}
        ],
        publisher: [
          {required: true, message: '请输入出版商', trigger: 'blur'}
        ],
        category: [
          {required: true, message: '请选择所属分类', trigger: 'blur'}
        ],
        bookNo: [
          {required: true, message: '书号不能为空', trigger: 'blur'},
          {min: 6, max: 12, message: '长度在2到20个字符', trigger: 'blur'}
        ],
      },

      form: {
        id: '',
        name: '',
        description: '',
        publishDate: '',
        author: '',
        publisher: '',
        category: '',
        bookNo: '',
        cover: '',
      },

      categories: [],
    };
  },
  methods: {

    save(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request.post('/book/save', this.form).then(res => {
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