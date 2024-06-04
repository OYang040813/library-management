<template>
  <div>

<!--    搜索组件-->
    <div>
      <el-input style="width: 240px" placeholder="请输入分类名称" v-model="params.name"></el-input>

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
      <el-table-column prop="id" label="编号" width="100"></el-table-column>
      <el-table-column prop="name" label="虚拟名"></el-table-column>
      <el-table-column prop="remark" label="备注"></el-table-column>
      <el-table-column prop="createtime" label="创建时间"></el-table-column>
      <el-table-column prop="updatetime" label="更新时间"></el-table-column>

      <el-table-column label="操作" width="260px">
        <template v-slot="scope" ><!--scope.row为当前行数据-->
          <el-button type="success" v-if="!scope.row.pid" @click="HandleAdd(scope.row)">添加二级分类</el-button>
          <el-button type="primary" @click="$router.push('/editCategory?id=' + scope.row.id)">编辑</el-button>
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

<!--    对话框组件开始-->
    <el-dialog title="添加二级分类" :visible.sync="dialogFormVisible" width="30%">
      <el-form :inline="true" :model="form" label-width="120px" ref="Ruleform" :rules="rules">
        <el-form-item label="二级分类名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入二级分类名称"></el-input>
        </el-form-item>
        <el-form-item label="二级分类备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入二级分类备注"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save('Ruleform')">确 定</el-button>
      </div>
    </el-dialog>
<!--对话框组件结束-->

  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: 'MyCategory',
  data(){
    return{

      tableData: [],
      total:0,

      dialogFormVisible: false,

      params:{
        pageNum:1,
        pageSize:7,
        name:'',
      },

      rules: {
        name: [
          {required: true, message: '二级分类名称不能为空', trigger: 'blur'},
        ],
        remark: [
          {max: 50, message: '长度不能超过50个字符', trigger: 'blur'}
        ],
      },

      pid:'',

      form:{
        id:'',
        pid:'',
        name:'',
        remark:'',
      },

    }
  },

  created() {
    this.load()
  },

  methods:{

    // 函数1：根据已有参数加载页面
    load(){
      request.get('/category/page',{params : this.params}).then(res => {
        if(res.code === '200'){
          this.tableData = res.data.list
          this.total = res.data.total
        }
      })
    },

    // 函数2：重回初始化阶段
    reset(){
      this.params = {
        pageNum:1,
        pageSize:7,
        name:'',
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
      request.delete("/category/delete/" + id).then(res =>{
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
      //将当前行的id作为二级分类的pid
      this.pid = row.id
      this.dialogFormVisible = true
    },

    //函数6：保存二级分类
    save(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.form.pid = this.pid
          request.post('/category/save', this.form).then(res => {
            if (res.code === '200') {
              this.$notify.success('新增二级分类成功')
              this.$refs[formName].resetFields()
              this.dialogFormVisible = false
              this.load()
            } else {
              this.$notify.error(res.msg)
            }
          })
        } else {
          this.$message.error('新增二级分类失败,请检查!!!')
        }
      });
    },

  }
}
</script>

<style scoped>

</style>