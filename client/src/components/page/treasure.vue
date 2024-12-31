<template>
  <div>
    <el-select
      v-model="infoValue"
      placeholder="请选择"
      filterable
      @change="getTreasureStatus()"
    >
      <el-option
        v-for="item in infoList"
        :key="item.id"
        :label="item.insertTime"
        :value="item.id"
      >
      </el-option>
    </el-select>
    <charts :optionData="optionData"></charts>

    <div>
      <el-table v-if="showTable" :data="tableData" height="350" stripe style="width: 100%; ">
        <el-table-column prop="date" sortable label="日期">
        </el-table-column>
        <!-- <el-table-column prop="userAccount" label="账号" width="180"></el-table-column> -->
        <el-table-column prop="pay" sortable label="薪水" style="text-align: right"> </el-table-column>
        <el-table-column prop="expenditure" sortable label="支出"> </el-table-column>
        <el-table-column prop="money" sortable label="现金"> </el-table-column>
        <el-table-column prop="totalInvest" sortable label="理财"> </el-table-column>
        <el-table-column prop="debt" sortable label="负债"> </el-table-column>
        <el-table-column prop="totalAsset" sortable label="汇总"> </el-table-column>
        <el-table-column label="操作">
      <template slot-scope="scope">
        <el-button
          size="mini"
          @click="getDetails(scope.row)">明细</el-button>
      </template>
    </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import charts from "../charts/Charts";
export default {
  name: "treasure",
  components: {
    charts,
  },
  props: {
    msg: String,
  },
  data() {
    return {
      // 历史收支情况
      infoList: [],
      // 当前选中的收支情况id
      infoValue: "",
      // 画图用的数据
      optionData: null,
      title: "test",
      expenditure: "",
      pay: "",
      tableData: [],
      showTable: false
    };
  },
  mounted() {
    this.getTreasureClassInfo();
    // 获取历时收支情况
    this.http.getIncomeInfoList({}).then((res) => {
      if (res) {
        let firstValue = res[0].id;
        this.infoValue = firstValue;
      }
      this.infoList = res;
    });
  },
  methods: {
    getTreasureStatus() {
      let param = {
        infoId: this.infoValue,
      };
      this.http.getTreasureStatus(param).then((res) => {
        console.log(res);
        if (!res) {
          return;
        }
        this.optionData = res;
        this.pay = res.pay;
        this.expenditure = res.expenditure;
      });
    },
    getDetails(row) {
      console.log(row);
      this.infoValue = row.infoId;
      this.getTreasureStatus();
    },
    getTreasureClassInfo() {
      this.http.getTreasureClassInfo({}).then(res => {
        console.log(res);
        if (!res) {
          return;
        }
        this.tableData = res;
        this.showTable = true;
      }).catch(err=>{
        this.showTable = false;
      })
    }
  },
};
</script>
