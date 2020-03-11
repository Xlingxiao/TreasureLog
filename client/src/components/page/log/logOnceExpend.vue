<template>
    <div>
        <h1>记录一次消费</h1>
        <div :style="style.box" v-for="expendItem in expendList">
            <el-row class="el-row-wrap">
                <el-col :span="5">
                    <p style="margin:0">消费信息</p>
                </el-col>
                <el-col :span="19">
                    <el-input @blur="fillterEmpty" v-model="expendItem.info"></el-input>
                </el-col>
            </el-row>
            <el-row class="el-row-wrap">
                <el-col :span="5">
                    <p style="margin:0">所花费用</p>
                </el-col>
                <el-col :span="14">
                    <el-input @blur="fillterEmpty" type="number" v-model="expendItem.amount"></el-input>
                </el-col>
                <el-col :span="5">
                    <el-select v-model="expendItem.essential" placeholder="是否必要">
                        <el-option
                            v-for="item in essentialOptions"
                            :key="item.value"
                            :label="item.name"
                            :value="item.value"
                        ></el-option>
                    </el-select>
                </el-col>
            </el-row>
            <el-row class="el-row-wrap">
                <el-col :span="5">
                    <p style="margin:0">备注</p>
                </el-col>
                <el-col :span="19">
                    <el-input @blur="fillterEmpty" v-model="expendItem.detail"></el-input>
                </el-col>
            </el-row>
        </div>

        <el-row class="el-row-wrap">
            <el-col class="el-col-wrap">
                <el-button @click="addExpend" type="primary" icon="el-icon-circle-plus-outline">新增</el-button>
                <el-button @click="submit" type="primary" icon="el-icon-upload">记录一次</el-button>
            </el-col>
        </el-row>
    </div>
</template>

<script>
export default {
    name: "logOnceExpend",
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
            essentialOptions: [
                {
                    name: "是",
                    value: 0
                },
                {
                    name: "否",
                    value: 1
                }
            ],
            expendList: []
        };
    },
    mounted() {
        this.expendList = new Array();
        this.expendList.push({ info: "", amount: 0, essential: 0, detail: "" });
    },
    methods: {
        // 过滤为空的
        fillterEmpty() {
            // console.log(this.mainExpends);
            if (this.expendList.length > 0)
                this.expendList = this.expendList.filter(
                    (item, key) => item.info || item.amount || item.detail
                );
        },
        // 增加一个投资记录
        addExpend() {
            if (this.expendList.length > 0) this.fillterEmpty(this.expendList);
            this.expendList.push({
                info: "",
                amount: 0,
                essential: 0,
                detail: ""
            });
        },
        // 上传一次记录
        submit() {
            this.fillterEmpty();
            let list = this.expendList;
            if (list.length < 1) {
                this.addExpend();
                return;
            }
            list.forEach(expend => {
                expend.userAccount = this.$store.state.userAccount;
            });
            this.http.addExpendLog(list).then(res => {
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