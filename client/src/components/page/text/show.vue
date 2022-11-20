<template>
  <div class="showBox">
    <el-row class="el-row-wrap">
      <el-col class="el-col-wrap">
        <el-button @click="refresh" type="primary" icon="el-icon-upload"
          >刷新</el-button
        >
      </el-col>
    </el-row>
    <el-table :data="contentList" border style="width: 100%">
      <el-table-column fixed prop="userAccount" label="用户"></el-table-column>
      <el-table-column fixed prop="showContent" label="内容"></el-table-column>
      <el-table-column fixed prop="insertTime" label="时间"></el-table-column>
      <el-table-column fixed="right" label="操作" width="100">
        <template slot-scope="scope">
          <el-button @click="showDetail(scope.row)" type="text" size="small"
            >查看</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      title="内容"
      :visible.sync="dialogVisible"
      width="80%"
      :before-close="handleClose"
      top="0"
      style="margin-top: 50px"
    >
      <!-- <span>{{ details }}</span> -->
      <el-input
      type="textarea"
      :autosize="{ minRows: 3, maxRows: 30}"
      v-model="details">
      </el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false"
          >确 定</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "text show",
  props: {
    msg: String,
    dialogWidth: "50%"
  },
  data() {
    return {
      contentList: [
        {
          content: "test2",
          id: 3,
          insertTime: "2022-07-12 23:28:16",
          userAccount: "8928",
        },
      ],
      showContent: [],
      dialogVisible: false,
      details: "",
    };
  },
  mounted() {
    let windowHeight = window.innerHeight;
    let windowWidth = window.innerWidth;
    if(windowHeight > windowWidth) {
      dialogWidth = "100%"
    }
    this.refresh();
  },
  methods: {
    refresh() {
      let data = {
        userAccount: this.$store.state.userAccount,
      };
      this.http.getTextInfo(data).then((res) => {
        let data = res;
        for (let i = 0; i < data.length; i++) {
          if (data[i]["content"].length > 100) {
            data[i]["showContent"] = data[i]["content"].substring(0, 100);
          } else {
            data[i]["showContent"] = data[i]["content"];
          }
        }
        this.contentList = data;
      });
    },
    showDetail(row) {
      this.dialogVisible = true;
      this.details = row.content;
    },
    handleClose() {

    }
  },
};
</script>

<style scoped>
.el-row-wrap {
  line-height: 60px;
}

.showBox {
  width: 90%;
  text-align: center;
  margin: 30px auto;
}
</style>