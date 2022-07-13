<template>
  <div class="mainBox">
    <el-input
      type="textarea"
      placeholder="请输入内容"
      v-model="content"
      rows="25"
      maxlength="102400"
      show-word-limit
    >
    </el-input>
    <el-row class="el-row-wrap">
      <el-col class="el-col-wrap">
        <el-button @click="insertLog" type="primary" icon="el-icon-upload"
          >记录</el-button
        >
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "text main",
  props: {
    msg: String,
  },
  data() {
    return {
      content: "",
    };
  },
  mounted() {},
  methods: {
    insertLog() {
      if (!this.content) {
        return;
      }
      let data = {
        userAccount: this.$store.state.userAccount,
        text: this.content,
      };
      this.http
        .addOneText(data)
        .then((res) => {
          this.$alert("成功！");
        })
        .catch((err) => {
          this.$alert("失败！");
        });
    },
  },
};
</script>

<style scoped>
.el-row-wrap {
  line-height: 60px;
}

.mainBox {
  width: 90%;
  text-align: center;
  margin: 30px auto;
}
</style>