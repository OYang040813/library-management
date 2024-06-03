<template>
  <div id="Layout">
    <!--头部区域-->
    <div style="display: flex; align-items: center; height: 60px; background-color: white; margin-bottom: 2px">

      <div style="width: 100px">
        <img src="@/assets/wolf.png" alt="logo" style="width: 55px; position: relative; top: 2px; left: 20px">
      </div>

      <div style="width: 200px">
        <span style="font-size: 20px; margin-left: 20px;">后台管理</span>
      </div>

      <div style="flex: 1; text-align: right; padding-right: 30px">

        <el-dropdown size="medium" :split-button="true" trigger="click">

          <span class="el-dropdown-link" style="cursor: pointer">{{ admin.name }}</span>

          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item><i class="el-icon-s-tools"></i>修改信息</el-dropdown-item>
            <el-dropdown-item><div @click="out"><i class="el-icon-switch-button"></i>退出账号</div></el-dropdown-item>
          </el-dropdown-menu>

        </el-dropdown>

      </div>

    </div>
    <!--头部区域结束-->

    <!--侧边栏与主体部分-->
    <div style="display: flex">


      <!--侧边栏导航-->
      <div style="width: 200px; min-height: calc(100vh - 62px); overflow: hidden; background-color: white; margin-left: 2px">
        <el-menu :default-active="$router.path" router class="el-menu-demo" active-text-color="#ffd04b" background-color="white">

          <el-menu-item index="/">
            <i class="el-icon-coffee-cup"></i>
            <span style="cursor: pointer">首页</span>
          </el-menu-item>

          <el-submenu index="user">
            <template slot="title">
              <i class="el-icon-tickets"></i>
              <span>成员管理</span>
            </template>
            <el-menu-item index="myUser">成员列表</el-menu-item>
            <el-menu-item index="addUser">成员添加</el-menu-item>
          </el-submenu>

          <el-submenu index="admin">
            <template slot="title">
              <i class="el-icon-user"></i>
              <span>管理员管理</span>
            </template>
            <el-menu-item index="myAdmin">管理员列表</el-menu-item>
            <el-menu-item index="addAdmin">管理员添加</el-menu-item>
          </el-submenu>

          <el-submenu index="Category">
            <template slot="title">
              <i class="el-icon-s-operation"></i>
              <span>分类管理</span>
            </template>
            <el-menu-item index="myCategory">分类列表</el-menu-item>
            <el-menu-item index="addCategory">分类添加</el-menu-item>
          </el-submenu>

        </el-menu>
      </div>
      <!--侧边栏导航结束-->



      <!--主体部分-->
      <div style="flex: 1; background-color: white; padding: 10px; margin-left: 2px">
        <!--展示路由到的界面-->
        <router-view/>
      </div>
      <!--主体部分结束-->


    </div>
  <!--侧边栏与主体部分结束-->
  </div>
</template>

<script>
import Cookies from "js-cookie";

export default {
  name: "Layout",

  data() {
    return {
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {}
    }
  },

  methods: {
    out(){
      Cookies.remove('admin')
      this.$router.push('/login')
      this.$notify.success('当前账号已退出')
    }
  }
}
</script>

<style scoped>

</style>