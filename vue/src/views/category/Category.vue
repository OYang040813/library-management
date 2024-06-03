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
    <el-table :data="tableData" stripe>
      <el-table-column prop="id" label="编号" width="60px"></el-table-column>
      <el-table-column prop="name" label="虚拟名" width="80px"></el-table-column>
      <el-table-column prop="remark" label="备注"></el-table-column>
      <el-table-column prop="createtime" label="创建时间"></el-table-column>
      <el-table-column prop="updatetime" label="更新时间"></el-table-column>

      <el-table-column label="状态" width="170px">
        <template v-slot="scope" >
          <el-switch
              style="display: block"
              v-model="scope.row.status"
              @change="changeStatus(scope.row)"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-text="启用"
              inactive-text="禁用">
          </el-switch>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="230px">
        <template v-slot="scope" >
<!--            scope.row为当前行数据-->
          <el-button type="primary" @click="$router.push('/editCategory?id=' + scope.row.id)">编辑</el-button>

<!--          删除-->
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

export default {
  name: 'MyCategory',
  data(){
    return{

      tableData: [],
      total:0,

      params:{
        pageNum:1,
        pageSize:7,
        name:'',
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
  }
}
</script>

<style scoped>

</style>