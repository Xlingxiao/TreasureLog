<template>
    <div class="hello">
        <div :style="style.stage" :id="chartID"></div>
    </div>
</template>

<script>
import echarts from "echarts";
import macarons from "echarts/theme/macarons2";
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
                    width: "100%",
                    height: "500px"
                }
            },
            option: {},
            myChart: {},
            min: 0
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
    created() {
        this.initStage();
        let num = this.$store.state.chartNum;
        this.$store.commit("updateChartNum", num++);
        this.chartID = "chart" + num;
    },
    mounted() {
        // 没有传东西过来默认展示资金状态
        if (!this.rowOption) this.defaultDrawing();
        else this.initEcharts();
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
            this.myChart.hideLoading();
            let changedData = this.rowOption;
            this.myChart.setOption(changedData);
            console.log(this.option);
        },
        // 默认画个旭日图
        defaultDrawing() {
            let params = {
                userAccount: this.$store.state.userAccount
            };
            this.http
                .getLatestChannleData(params)
                .then(res => {
                    let myChart = echarts.init(
                        document.getElementById(this.chartID),
                        this.theme
                    );
                    let root = res;
                    root.children.reverse();
                    console.log(root);
                    this.traverse(root);
                    this.option = {
                        title: { text: "资金分布情况", left: "center", top: (this.min * 10) / 600 },
                        series: {
                            type: "sunburst",
                            center: ["50%", (this.min * 320) / 600],
                            levels: [
                                {},
                                {
                                    r0: 0,
                                    r: (this.min * 8) / 60
                                },
                                {
                                    r0: (this.min * 8) / 60,
                                    r: (this.min * 18) / 60
                                },
                                {
                                    r0: (this.min * 2) / 6,
                                    r: (this.min * 205) / 600,
                                    itemStyle: {
                                        shadowBlur: 80
                                    },
                                    label: {
                                        position: "outside",
                                        color: "#666",
                                        size: "15px"
                                    },
                                    downplay: {
                                        label: {
                                            opacity: 0.5
                                        }
                                    }
                                }
                            ],
                            data: res.children
                        }
                    };
                    console.log(root);
                    console.log(this.option);
                    myChart.setOption(this.option);
                })
                .catch(err => {
                    console.log(err);
                });
        },
        // 遍历树 深度优先遍历
        traverse(root) {
            let children = root.children;
            let _this = this;
            if (children && children.length > 0) {
                for (let i in children) {
                    _this.traverse(children[i]);
                }
            } else {
                if (root.value > 0)
                    root.children = [
                        {
                            name: root.value,
                            value: root.value,
                            itemStyle: { color: "#CC3F57" }
                        }
                    ];
                else
                    root.children = [
                        {
                            name: root.value,
                            value: Math.abs(root.value),
                            itemStyle: { color: "#CC3F57" }
                        }
                    ];
            }
            if (root.value < 0) root.value = Math.abs(root.value);
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
