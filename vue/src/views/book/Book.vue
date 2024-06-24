<template>
  <div>

<!--    搜索组件-->
    <div>
      <el-input style="width: 240px" placeholder="请输入书籍名称" v-model="params.name"></el-input>
      <el-input style="width: 240px; margin-left: 5px" placeholder="请输入书号" v-model="params.bookNo"></el-input>

      <el-button style="margin-left: 5px" type="primary" @click="load">
        <i class="el-icon-search"></i>
        <span>搜索</span>
      </el-button>

      <el-button style="margin-left: 5px" type="warning" @click="reset">
        <i class="el-icon-refresh"></i>
        <span>重置</span>
      </el-button>

    </div>
<!--    搜索组件结束-->

<!--    表格表头-->
    <el-table :data="tableData" stripe row-key="id" default-expand-all>
      <el-table-column prop="id" label="id" width="40"></el-table-column>
      <el-table-column prop="name" label="图书名"></el-table-column>
<!--      <el-table-column prop="description" label="描述"></el-table-column>-->
      <el-table-column prop="publishDate" label="出版日期" width="90"></el-table-column>
      <el-table-column prop="author" label="作者"></el-table-column>
      <el-table-column prop="publisher" label="出版商"></el-table-column>
      <el-table-column prop="category" label="分类" width="80"></el-table-column>
      <el-table-column prop="bookNo" label="书号" width="80"></el-table-column>
      <el-table-column prop="cover" label="封面">
        <template v-slot="scope">
          <el-image :src="scope.row.cover" :preview-src-list="[scope.row.cover]"></el-image>
        </template>
      </el-table-column>
      <el-table-column prop="createtime" label="创建时间" width="90"></el-table-column>
      <el-table-column prop="updatetime" label="更新时间" width="90"></el-table-column>

      <el-table-column label="操作" width="160px">
        <template v-slot="scope" ><!--scope.row为当前行数据-->
          <el-button type="primary" @click="$router.push('/editBook?id=' + scope.row.id)">编辑</el-button>
          <el-popconfirm
              style="margin-left: 7px"
              confirm-button-text='确认'
              cancel-button-text='取消'
              cancel-button-type="Primary"
              icon="el-icon-delete-solid"
              title="您确定删除这行数据吗？"
              @confirm="del(scope.row.id)"
          >
            <el-button type="danger" slot="reference">删除</el-button>
          </el-popconfirm>

        </template>
      </el-table-column>
    </el-table>
<!--    表格表头结束-->

<!--    分页组件-->
    <div style="margin-top: 20px;">
      <el-pagination
          background
          :current-page = "params.pageNum"
          :page-size="params.pageSize"
          @current-change="handleCurrentChange"
          layout="prev, pager, next"
          :total= "total">
      </el-pagination>
    </div>
<!--    分页组件结束-->

<!--&lt;!&ndash;    对话框组件开始&ndash;&gt;-->
<!--    <el-dialog title="添加二级分类" :visible.sync="dialogFormVisible" width="30%">-->
<!--      <el-form :inline="true" :model="form" label-width="120px" ref="Ruleform" :rules="rules">-->
<!--        <el-form-item label="二级分类名称" prop="name">-->
<!--          <el-input v-model="form.name" placeholder="请输入二级分类名称"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="二级分类备注" prop="remark">-->
<!--          <el-input v-model="form.remark" placeholder="请输入二级分类备注"></el-input>-->
<!--        </el-form-item>-->
<!--      </el-form>-->
<!--      <div slot="footer" class="dialog-footer">-->
<!--        <el-button @click="dialogFormVisible = false">取 消</el-button>-->
<!--        <el-button type="primary" @click="save('Ruleform')">确 定</el-button>-->
<!--      </div>-->
<!--    </el-dialog>-->
<!--&lt;!&ndash;对话框组件结束&ndash;&gt;-->

  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: 'MyBook',
  data(){
    return{

      tableData: [],
      total:0,

      params:{
        pageNum:1,
        pageSize:7,
        name:'',
        bookNo:'',
      },

    }
  },

  created() {
    this.load()
  },

  methods:{

    // 函数1：根据已有参数加载页面
    load(){
      request.get('/book/page',{params : this.params}).then(res => {
        if(res.code === '200'){
          this.tableData = res.data.list
          this.total = res.data.total
        }
      })
    },

    // 函数2：重回初始化阶段
    reset(){
      this.params = {
        pageNum: 1,
        pageSize: 7,
        name: '',
        bookNo: '',
      }
      this.load();
    },

    // 函数3：触发分页，切换页面
    handleCurrentChange(pageNum){
      this.params.pageNum = pageNum
      this.load()
    },

    // 函数4：删除数据
    del(id){
      request.delete("/book/delete/" + id).then(res =>{
        if(res.code === '200'){
          this.$notify.success("删除成功")
          this.load()//调用刷新函数
        }else{
          this.$notify.error(res.msg)
        }
      })
    },

    //函数5：添加二级分类弹窗出现
    HandleAdd(row){
      // //将当前行的id作为二级分类的pid
      // this.pid = row.id
      this.dialogFormVisible = true
    },

    // //函数6：保存二级分类
    // save(formName) {
    //   this.$refs[formName].validate((valid) => {
    //     if (valid) {
    //       this.form.pid = this.pid
    //       request.post('/book/save', this.form).then(res => {
    //         if (res.code === '200') {
    //           this.$notify.success('新增二级分类成功')
    //           this.$refs[formName].resetFields()
    //           this.dialogFormVisible = false
    //           this.load()
    //         } else {
    //           this.$notify.error(res.msg)
    //         }
    //       })
    //     } else {
    //       this.$message.error('新增二级分类失败,请检查!!!')
    //     }
    //   });
    // },

  }
}
</script>

<style scoped>

</style>