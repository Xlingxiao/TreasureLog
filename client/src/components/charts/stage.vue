<template>
    <!-- 只负责监听屏幕并重新绘画Echarts -->
    <div class="hello">
        <div :style="style.stage" :id="chartID"></div>
    </div>
</template>

<script>
import echarts from "echarts";
import macarons from "echarts/theme/macarons2";
export default {
    name: "Stage",
    props: {
        rowOption: {
            type: Object,
            default() {
                return undefined;
            }
        },
        // chartID: {
        //     type:String,
        //     default: "chart"
        // }
    },
    data() {
        return {
            chartID: "",
            style: {
                stage: {
                    width: "100%",
                    height: "500px"
                }
            },
            option: {},
            myChart: {},
            min: 0,
            count: 0
        };
    },
    created() {
        this.initStage();
        let num = this.$store.state.chartNum;
        this.chartID = "chart" + num;
        num = num + 1;
        this.$store.commit("updateChartNum", num);
    },
    mounted() {
        // 没有传东西过来默认展示资金状态
        if (!this.rowOption) return;
        let _this = this;
        window.onresize = () => {
            //调用methods中的事件
            setTimeout(() => {
                _this.resize();
            }, 400);
        };
    },
    watch: {
        // 复杂数据类型的监听需要使用deep属性
        rowOption: {
            handler(value, old) {
                console.log("update", value);
                this.updateMyChart();
            },
            deep: true
        }
    },
    methods: {
        initStage() {
            let windowHeight = window.innerHeight;
            let windowWidth = window.innerWidth;
            this.min = Math.min(windowHeight, windowWidth);
            this.style.stage.height = windowHeight * 0.9 + "px";
            // this.style.stage.width = this.style.stage.height;
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
            let myChart = echarts.init(document.getElementById(this.chartID));
            myChart.hideLoading();
            let changedData = this.rowOption;
            myChart.setOption(changedData, true);
        },
        resize() {
            let myChart = echarts.init(document.getElementById(this.chartID));
            myChart.resize();
        }
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
