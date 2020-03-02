<template>
    <div class="hello">
        <div :style="style.stage" :id="chartID"></div>
    </div>
</template>

<script>
import echarts from "echarts";
import macarons from "echarts/theme/macarons2"
export default {
    name: "Charts",
    props: {
        msg: String,
        chartKey: String,
        chartName: String,
        rowOption: {
            type: Object,
            default() {
                return undefined;
            }
        },
        theme: {
            type: String,
            default: "macarons"
        },
        title: {
            type: String,
            default: "ChartID"
        }
    },
    data() {
        return {
            chartID: "",
            style: {
                stage: {
                    width: "80%",
                    height: "500px"
                }
            },
            option: {},
            myChart: {}
        };
    },
    watch:{
        // 复杂数据类型的监听需要使用deep属性
        rowOption: {
            handler(value, old){
            console.log("update",value)
            this.updateMyChart();
            },
            deep: true
        }
    },
    created() {
        let num = this.$store.state.chartNum;
        this.$store.commit("updateChartNum", num++);
        this.chartID = "chart" + num;
    },
    mounted() {
        this.initStage();
        // 没有传东西过来默认展示资金状态
        if (!this.rowOption) this.defaultDrawing();
        else this.initEcharts();
    },

    methods: {
        initStage() {
            let windowHeight = window.innerHeight;
            this.style.stage.height = windowHeight * 0.7 + "px";
            this.style.stage.width = this.style.stage.height;
        },
        // 初始化echarts的框架
        initEcharts() {
            let canvens = document.getElementById(this.chartID);
            this.myChart = echarts.init(canvens, macarons);
            this.option = this.rowOption;
            this.myChart.setOption(this.option);
            this.myChart.showLoading();
        },
        // 绘图
        updateMyChart() {
            this.myChart.hideLoading();
            let changedData = this.rowOption;
            this.myChart.setOption(changedData);
            console.log(this.option)
        },
        // 默认画个旭日图
        defaultDrawing() {
            let params = {
                userAccount: "1"
            };
            this.http
                .getLatestChannleData(params)
                .then(res => {
                    let myChart = echarts.init(
                        document.getElementById(this.chartID),
                        this.theme
                    );
                    this.option = {
                        series: {
                            type: "sunburst",
                            data: res.children
                        }
                    };
                    myChart.setOption(this.option);
                })
                .catch(err => {
                    console.log(err);
                });
        },
    }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.hello * {
    text-align: center;
    margin: 0 auto;
}
</style>
