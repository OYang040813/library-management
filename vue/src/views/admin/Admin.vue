<template>
  <div>

<!--    搜索组件-->
    <div>
      <el-input style="width: 240px" placeholder="请输入虚拟名" v-model="params.name"></el-input>
      <el-input style="width: 240px; margin-left: 5px" placeholder="请输入管理员卡号" v-model="params.cardnum"></el-input>

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
      <el-table-column prop="id" label="id卡号" width="60px"></el-table-column>
      <el-table-column prop="cardnum" label="管理号"></el-table-column>
      <el-table-column prop="name" label="虚拟名" width="80px"></el-table-column>
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
          <el-button type="primary" @click="$router.push('/editAdmin?id=' + scope.row.id)">编辑</el-button>

<!--          删除用户-->
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

<!--          修改密码-->
          <el-popconfirm
              style="margin-left: 7px"
              confirm-button-text='确认'
              cancel-button-text='取消'
              cancel-button-type="Primary"
              icon="el-icon-delete-solid"
              title="您确定修改该用户密码吗？"
              @confirm="HandleChangeKeynum(scope.row.id, scope.row.name)"
          >
            <el-button type="warning" slot="reference">修改密码</el-button>
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
    <el-dialog title="修改密码" :visible.sync="dialogFormVisible" width="30%">
      <el-form :inline="true" :model="form" label-width="100px" ref="keynumRef" :rules="rules">
        <el-form-item label="旧密码" prop="keynum">
          <el-input v-model="form.keynum" placeholder="请输入旧密码" show-password></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newkeynum">
          <el-input v-model="form.newkeynum" placeholder="请输入新密码" show-password></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveKeynum">确 定</el-button>
      </div>
    </el-dialog>
<!--对话框组件结束-->

  </div>
</template>






<script>
import request from "@/utils/request";
import EditAdmin from "@/views/admin/EditAdmin.vue";
import Cookies from "js-cookie";

export default {
  name: 'MyAdmin',
  computed: {
    EditAdmin() {
      return EditAdmin
    }
  },
  data(){
    return{

      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {},
      tableData: [],
      total:0,

      dialogFormVisible: false,
      params:{
        pageNum:1,
        pageSize:7,
        name:'',
        cardnum:''
      },
      rules: {
        keynum: [
          {required: true, message: '请正确输入旧密码', trigger: 'blur'},
          {min: 6, max: 12, message: '请正确输入旧密码', trigger: 'blur'}
        ],
        newkeynum: [
          {required: true, message: '新密码不能为空', trigger: 'blur'},
          {min: 6, max: 12, message: '长度在6到12个字符', trigger: 'blur'}
        ],
      },
      form:{
        name:'',
        keynum:'',
        newkeynum:'',
        id:'',
      },
    }
  },

  created() {
    this.load()
  },

  methods:{

    // 函数1：根据已有参数加载页面
    load(){

      request.get('/admin/page',{params : this.params}).then(res => {
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
        cardnum:''
      }
      this.form = {
        name:'',
        keynum:'',
        newkeynum:'',
        id:'',
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
      request.delete("/admin/delete/" + id).then(res =>{
        if(res.code === '200'){
          this.$notify.success("删除成功")
          this.load()//调用刷新函数
        }else{
          this.$notify.error(res.msg)
        }
      })
    },

    //函数5：修改密码
    HandleChangeKeynum(id,name){
      this.form.id = JSON.parse(JSON.stringify(id))
      this.form.name = JSON.parse(JSON.stringify(name))
      this.dialogFormVisible = true;
    },

    //函数6：保存新密码
    saveKeynum(){
      this.$refs["keynumRef"].validate((valid) => {
        if (valid) {
          request.put('/admin/keynum', this.form).then(res => {
            if (res.code === '200') {
              this.$notify.success("修改成功")
              if (this.form.id === this.admin.id){ //当前修改的账号为用户本身账号
                Cookies.remove('admin')
                this.$router.push('/login')
              }else{
                this.reset();
                this.dialogFormVisible = false
              }
            } else {
              this.$notify.error("修改失败")
            }
          })
        } else {
          this.$message.error('请正确填入用户名与密码！')
        }
      });
    },

    //函数7：更改状态
    changeStatus(row){
      let admin = Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {}
      if(row.id === 1){
        this.$notify.warning('权限不足')
        this.reset()
      }else if (admin.id === 1){
        request.put('/admin/update', row).then(res =>{
          if (res.code === '200'){
            if (row.status){
              this.$notify.success('启用成功')
            }else{
              this.$notify.success('禁用成功')
            }
            this.reset()
          } else{
            this.$notify.error(res.msg)
          }
        });
      }else{
        this.$notify.warning('权限不足')
        this.reset()
      }
    },
  }
}
</script>

<style scoped>

</style>