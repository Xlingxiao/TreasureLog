<template>
    <div class="hello">
        <h1>{{ msg }}</h1>
        <div class="main" :id="chartID"></div>
    </div>
</template>

<script>
import echarts from "echarts";
export default {
    name: "DemoChartDataFromServer",
    props: {
        msg: String
    },
    data() {
        return {
            chartID: "DemoID"
        };
    },
    mounted() {
        this.drawing();
    },
    methods: {
        drawing() {
            let params = {
                userAccount: this.$store.state.userAccount
            }
            this.http.getLatestChannleData(params).then(res => {
                let myChart = echarts.init(document.getElementById(this.chartID));
                let option = {
                    series: {
                        type: "sunburst",
                        data: res.children
                    }
                }
                myChart.setOption(option)
            }).catch(err => {
                console.log(err)
            })
            
        },
    }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.hello *{
    text-align: center;
    margin: 0 auto;
}
.main {
    width: 80%;
    height: 500px;
}
</style>
