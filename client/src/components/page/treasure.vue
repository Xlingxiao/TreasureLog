<template>
  <div>
    <el-select v-model="infoValue" placeholder="请选择" filterable  @change="getTreasureStatus()" >
      <el-option
        v-for="item in infoList"
        :key="item.id"
        :label="item.insertTime"
        :value="item.id"
      >
      </el-option>
    </el-select>
    <charts :optionData='optionData'></charts>
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
        infoValue: '',
        // 画图用的数据
        optionData: null,
        title: "test",
        expenditure: '',
        pay: ''
    };
  },
  mounted() {
    // 获取历时收支情况
    this.http.getIncomeInfoList({}).then((res) => {
        if(res)  {
            let firstValue = res[0].id;
            this.infoValue = firstValue;
        }
        console.log()
        this.infoList = res
    });
  },
  methods: {
    getTreasureStatus() {
      let param = {
        infoId: this.infoValue,
      };
      this.http.getTreasureStatus(param).then((res) => {
        console.log(res);
        if(!res) {
            return;
        }
        this.optionData = res;
        this.pay = res.pay;
        this.expenditure = res.expenditure;
      });
    }
  }
};
</script>
