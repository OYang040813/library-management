<template>
  <div>

    <!--    搜索组件-->
    <div>
      <el-input style="width: 240px" placeholder="请输入人名" v-model="params.name"></el-input>
      <el-input style="width: 240px; margin-left: 5px" placeholder="请输入电话" v-model="params.phone"></el-input>

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
    <el-table :data="tableData" stripe>
      <el-table-column prop="id" label="id编号" width="60"></el-table-column>
      <el-table-column prop="name" label="用户名" width="90"></el-table-column>
      <el-table-column prop="cardnum" label="卡号"></el-table-column>
      <el-table-column prop="email" label="邮箱"></el-table-column>
      <el-table-column prop="phone" label="电话号码" width="100"></el-table-column>
      <el-table-column prop="sex" label="性别" width="50"></el-table-column>
      <el-table-column prop="age" label="年龄" width="50"></el-table-column>

      <el-table-column label="操作">
        <template v-slot="scope">
<!--          scope.row为当前行数据-->
          <el-button type="primary" @click="$router.push('/editUser?id=' + scope.row.id)">编辑</el-button>

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


  </div>
</template>






<script>
import request from "@/utils/request";
import EditUser from "@/views/user/EditUser.vue";

export default {
  name: 'MyUser',
  computed: {

  },
  data(){
    return{
      tableData: [],
      total:0,
      params:{
        pageNum:1,
        pageSize:7,
        name:'',
        phone:''
      }
    }
  },

  created() {
    this.load()
  },

  methods:{

    // 函数1：根据已有参数加载页面
    load(){
      request.get('/user/page',{params : this.params}).then(res => {
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
        phone:''
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
      request.delete("/user/delete/" + id).then(res =>{
        if(res.code === '200'){
          this.$notify.success("删除成功")
          this.load()//调用刷新函数
        }else{
          this.$notify.error(res.msg)
        }
      })
    },

  }
}
</script>

<style scoped>

</style>