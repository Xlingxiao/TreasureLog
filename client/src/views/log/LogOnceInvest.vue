<template>
    <div>
        <h1>记录一次投资</h1>
        <div :style="style.box" v-for="investItem in investList">
            <el-row class="el-row-wrap">
                <el-col :span="5">
                    <p style="margin:0">投资渠道</p>
                </el-col>
                <el-col :span="19">
                    <!-- <el-input @blur="fillterEmpty" placeholder="渠道" v-model="investItem.channel"></el-input> -->
                    <el-select style="width:100%" v-model="investItem.channel" placeholder="渠道">
                        <el-option
                            v-for="item in channles"
                            :key="item"
                            :label="item"
                            :value="item"
                        >
                            <span>{{ item }}</span>
                        </el-option>
                        <el-input
                            @blur="addChannel(investItem)"
                            v-model="newChannel"
                            placeholder="新建财富渠道"
                            class="inputItem"
                            clearable
                        ></el-input>
                    </el-select>
                </el-col>
            </el-row>
            <el-row class="el-row-wrap">
                <el-col :span="5">
                    <p style="margin:0">投入额</p>
                </el-col>
                <el-col :span="19">
                    <el-input
                        @blur="fillterEmpty"
                        type="number"
                        placeholder="投资额"
                        v-model="investItem.invest"
                    ></el-input>
                </el-col>
            </el-row>
            <el-row class="el-row-wrap">
                <el-col :span="5">
                    <p style="margin:0">当前总额</p>
                </el-col>
                <el-col :span="19">
                    <el-input
                        @blur="fillterEmpty"
                        type="number"
                        placeholder="当前总额"
                        v-model="investItem.gross"
                    ></el-input>
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
                    padding: "5%",
                    "margin-bottom": "25px"
                }
            },
            pay: "",
            investList: [],
            channles: new Array(),
            newChannel: ""
        };
    },
    mounted() {
        this.channles.push("基金", "股票");
        this.investList = new Array();
        this.investList.push({
            channel: "",
            invest: 0,
            gross: 0
        });
    },
    methods: {
        // 新增一个一级渠道
        addChannel(investItem) {
            let channel = this.newChannel;
            this.channles.push(channel);
            this.newChannel = "";
            investItem.channel = channel;
        },
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
            this.http
                .addInvestLog(this.investList)
                .then(res => {
                    this.$alert("成功记录一次理财情况！");
                })
                .catch(e => {
                    this.$alert("记录失败请稍后重试！");
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
