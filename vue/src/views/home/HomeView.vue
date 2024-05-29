<template xmlns:el-table="http://www.w3.org/1999/html">
  <div>
    <div class="item">管理员账户: {{admin.name}}</div>
    <div class="item">虚拟id: {{admin.id}}</div>
    <div class="item">虚拟卡号: {{admin.cardnum}}</div>
  </div>
</template>

<script>

import Cookies from "js-cookie";
import request from "@/utils/request";

export default {
  name: "Home",

  data() {
    return {
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {}
    }
  },

}

</script>

<style>
.item {
  margin: 10px 0;
  color: #666;
  font-size: 13px;
}
</style>