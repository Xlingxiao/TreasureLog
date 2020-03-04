<template>
    <div>
        <h1>hello log</h1>
        <el-row class="el-row-wrap">
            <el-col :span="5" class="el-col-wrap">
                <div class="colorItem">薪水</div>
            </el-col>
            <el-col :span="19" class="el-col-wrap">
                <div class>
                    <el-input type="number" v-model="pay" class="inputItem"></el-input>
                </div>
            </el-col>
        </el-row>
        <el-row class="el-row-wrap">
            <el-col class="el-col-wrap">
                <div class="colorItem">主要消费</div>
            </el-col>
        </el-row>
        <el-row v-for="mainExpend in mainExpends" class="el-row-wrap">
            <el-col :span="11" class="el-col-wrap">
                <!-- <div class="colorItem">{{mainExpend.name}}</div> -->
                <el-input
                    @blur="fillterEmpty(1)"
                    placeholder="物品名称"
                    v-model="mainExpend.name"
                    class="inputItem"
                ></el-input>
            </el-col>
            <el-col :span="2">
                <p></p>
            </el-col>
            <el-col :span="11" class="el-col-wrap">
                <div class>
                    <el-input
                        v-model="mainExpend.value"
                        placeholder="花费金钱"
                        class="inputItem"
                        type="number"
                    ></el-input>
                </div>
            </el-col>
        </el-row>
        <el-row class="el-row-wrap">
            <el-col class="el-col-wrap">
                <el-button
                    @click="addOneExpend"
                    type="primary"
                    icon="el-icon-circle-plus-outline"
                >新增</el-button>
            </el-col>
        </el-row>

        <el-row class="el-row-wrap">
            <el-col class="el-col-wrap">
                <div class="colorItem">资产分布情况</div>
            </el-col>
        </el-row>

        <el-row v-for="channel in channels" class="el-row-wrap">
            <el-col :span="8" class="el-col-wrap">
                <el-select v-model="channel.channel1" placeholder="财富渠道">
                    <el-option v-for="item in channles1" :key="item" :label="item" :value="item">
                        <span>{{ item }}</span>
                    </el-option>
                    <el-input
                        @blur="addChannel"
                        v-model="newChannel"
                        placeholder="新建财富渠道"
                        class="inputItem"
                        clearable
                    ></el-input>
                </el-select>
            </el-col>
            <el-col :span="1">
                <p></p>
            </el-col>
            <el-col :span="8" class="el-col-wrap">
                <el-input @blur="fillterEmpty(2)" placeholder="细分位置" v-model="channel.name" class="inputItem"></el-input>
            </el-col>
            <el-col :span="1">
                <p></p>
            </el-col>
            <el-col :span="6" class="el-col-wrap">
                <el-input  type="number" v-model="channel.value" placeholder="财富值"  ></el-input>
            </el-col>
        </el-row>
        <el-row class="el-row-wrap">
            <el-col class="el-col-wrap">
                <el-button
                    @click="addOneChannel"
                    type="primary"
                    icon="el-icon-circle-plus-outline"
                >新增</el-button>
            </el-col>
        </el-row>

         <el-row class="el-row-wrap">
            <el-col class="el-col-wrap">
                <el-button @click="submit" type="primary" icon="el-icon-upload">记录一次</el-button>
            </el-col>
        </el-row>
    </div>
</template>

<script>
export default {
    name: "completeLog",
    data() {
        return {
            pay: "",
            mainExpends: new Array(),
            channels: new Array(),
            channles1: new Array(),
            channle1: "",
            newChannel: ""
        };
    },
    watch: {
        
    },
    mounted() {
        this.channles1.push("微信", "支付宝");
        this.channels.push({ channel1:"", name: "", value: 0 });
        this.mainExpends.push({ name: "", value: 0 });
    },
    methods: {
        // 添加一个主要花销
        addOneExpend() {
            this.mainExpends.push({ name: "", value: "" });
        },
        // 过滤为空的
        fillterEmpty(num) {
            // console.log(this.mainExpends);
            if(num == 1)
                this.mainExpends = this.mainExpends.filter(
                    (item, key) => item.value || item.name
                );
            else 
                this.channels = this.channels.filter(
                    (item, key) => item.value || item.name
                );
            
        },
        // 新增一个一级渠道
        addChannel() {
            let channel = this.newChannel;
            this.channles1.push(channel);
            this.newChannel = "";
        },
        // 添加一个财富记录
        addOneChannel() {
            this.fillterEmpty(2);
            this.channels.push({channel1:"", name: "", value: 0 });
        },
        // 整理请求参数
        submit() {
            let info = "";
            this.mainExpends.forEach(element => {
                if(element.name && element.value)
                    info += element.name + ':' + element.value + '_,_';
            });
            let channelList = new Array;
            for(let i in this.channels) {
                if(this.channels[i].channel1 && this.channels[i].name && this.channels[i].value) {
                    let channel2 = {
                        "channel1": this.channels[i].channel1,
                        "channel2": this.channels[i].name,
                        "value": this.channels[i].value
                    };  
                    channelList.push(channel2);
                }
            }
            let params = {
                "userAccount":'2',
                "pay": this.pay,
                "info": info,
                "channel": channelList
            }
            console.log(params)
            this.http.addCompleteLog(params).then(res => {
                this.$message("记录成功！")
            })
        }
    }
};
</script>

<style scoped>
.el-col-wrap {
    line-height: 40px;
    margin: 10px auto;
}
</style>