<template>
    <div>
        <h1>记录一次投资</h1>
        <div :style="style.box" v-for="investItem in investList">
            <el-row class="el-row-wrap">
                <el-col :span="5">
                    <p style="margin:0">投资渠道</p>
                </el-col>
                <el-col :span="19">
                    <el-input @blur="fillterEmpty" placeholder="渠道" v-model="investItem.channel"></el-input>
                </el-col>
            </el-row>
            <el-row class="el-row-wrap">
                <el-col :span="5">
                    <p style="margin:0">投入额</p>
                </el-col>
                <el-col :span="19">
                <el-input @blur="fillterEmpty" type="number" placeholder="投资额" v-model="investItem.invest"></el-input>
                </el-col>
            </el-row>
            <el-row class="el-row-wrap">
                <el-col :span="5">
                    <p style="margin:0">当前总额</p>
                </el-col>
                <el-col :span="19">
                <el-input @blur="fillterEmpty" type="number" placeholder="当前总额" v-model="investItem.gross"></el-input>
                </el-col>
            </el-row>
        </div>

        <el-row class="el-row-wrap">
            <el-col class="el-col-wrap">
                <el-button @click="addInvest" type="primary" icon="el-icon-circle-plus-outline">新增</el-button>
                <el-button @click="submit" type="primary" icon="el-icon-upload">记录一次</el-button>
            </el-col>
        </el-row>
    </div>
</template>

<script>
export default {
    name: "logOnceInvest",
    data() {
        return {
            style: {
                box: {
                    border: "0.5px solid #bbb",
                    "border-radius": "1%",
                    padding: "5px",
                    "margin-bottom": "25px"
                }
            },
            pay: "",
            investList: []
        };
    },
    mounted() {
        this.investList = new Array();
        this.investList.push({
            channel: "",
            invest: 0,
            gross: 0
        });
    },
    methods: {
        // 过滤为空的
        fillterEmpty() {
            // console.log(this.mainExpends);
            if (this.investList.length > 1)
                this.investList = this.investList.filter(
                    (item, key) => item.channel || item.invest || item.gross
                );
        },
        // 增加一个投资记录
        addInvest() {
            if (this.investList.length > 1) this.fillterEmpty(this.investList);
            this.investList.push({
                channel: "",
                invest: 0,
                gross: 0
            });
        },
        // 上传一次记录
        submit() {
            this.investList.forEach(invest => {
                invest.userAccount = this.$store.state.userAccount;
            });
            this.http.addInvestLog(this.investList).then(res => {
                console.log(res);
            });
        }
    }
};
</script>

<style scoped>
.el-row-wrap {
    line-height: 60px;
}
</style>
