<template>
    <div class="hello">
        <stage :rowOption="chartOption" v-if="chartOption != {}"></stage>
    </div>
</template>

<script>
import stage from "../../charts/stage";
import echarts from "echarts";
export default {
    name: "treasure",
    components: {
        stage
    },
    props: {
        msg: String
    },
    data() {
        return {
            data: "",
            chartID: "DemoID",
            style: {
                stage: {
                    width: "100%"
                    // height: "500px"
                }
            },
            chartOption:{},
        };
    },
    mounted() {
        // this.$store.commit("updateUserAccount", "1");
        this.userAccount = this.$store.state.userAccount;
        this.initStage();
        this.drawing();
        // let _this = this;
        // window.onresize = () => {
        //     //调用methods中的事件
        //     setTimeout(() => {
        //         _this.updateCharts();
        //     }, 400);
        // };
    },
    methods: {
        initStage() {
            let windowHeight = window.innerHeight;
            this.style.stage.height = windowHeight - 90 + "px";
            this.style.stage.width = window.innerWidth;
        },
        drawing() {
            let params = {
                userAccount: this.$store.state.userAccount,
                channel: "基金"
            };
            this.http
                .getInvestInfo(params)
                .then(res => {
                    // let myChart = echarts.init(
                    //     document.getElementById(this.chartID)
                    // );
                    let option = this.getOption();
                    let profit = res.fundGain[res.fundGain.length - 1] * 1;
                    if (profit > 0) {
                        option.title.subtext = "当前投资盈利：" + profit;
                    } else {
                        option.title.subtext =
                            "当前投资亏损：" + Math.abs(profit);
                    }
                    let chartMode = this.getChartMode();
                    let itemStyle1 = {
                        color: "#eecc66",
                        borderColor: "#fff",
                        borderWidth: 5,
                        shadowColor: "rgba(0, 0, 0, .3)",
                        shadowBlur: 0,
                        shadowOffsetY: 2,
                        shadowOffsetX: 2
                    };
                    let areaStyle1 = {
                        normal: {
                            color: new echarts.graphic.LinearGradient(
                                0,
                                0,
                                0,
                                1,
                                [
                                    {
                                        offset: 0,
                                        color: "rgba(108,80,243,0.3)"
                                    },
                                    {
                                        offset: 1,
                                        color: "rgba(108,80,243,0)"
                                    }
                                ],
                                false
                            ),
                            shadowColor: "rgba(108,80,243, 0.9)",
                            shadowBlur: 20
                        }
                    };
                    let itemStyle2 = {
                        // color: "#6c50f3",
                        borderColor: "#fff",
                        borderWidth: 5,
                        shadowColor: "rgba(0, 0, 0, .3)",
                        shadowBlur: 0,
                        shadowOffsetY: 2,
                        shadowOffsetX: 2
                    };
                    let areaStyle2 = {
                        normal: {
                            color: new echarts.graphic.LinearGradient(
                                0,
                                0,
                                0,
                                1,
                                [
                                    {
                                        offset: 0,
                                        color: "rgba(0,202,149,0.3)"
                                    },
                                    {
                                        offset: 1,
                                        color: "rgba(0,202,149,0)"
                                    }
                                ],
                                false
                            ),
                            shadowColor: "rgba(0,202,149, 0.9)",
                            shadowBlur: 20
                        }
                    };
                    let itemStyle3 = {
                        color: "#6c50f3",
                        borderColor: "#fff",
                        borderWidth: 5,
                        shadowColor: "rgba(0, 0, 0, .3)",
                        shadowBlur: 0,
                        shadowOffsetY: 2,
                        shadowOffsetX: 2
                    };
                    let dateData = res.dates;

                    let grossData = new Array();
                    let investData = new Array();
                    let gainData = new Array();
                    let totalGain = new Array();
                    let grain = 0;
                    for (let i in dateData) {
                        grossData.push([dateData[i], res.fundGross[i]]);
                        investData.push([dateData[i], res.fundInvest[i]]);
                        gainData.push([dateData[i], res.fundGain[i]]);
                        grain += res.fundGain[i];
                        totalGain.push([dateData[i], grain]);
                    }

                    var chart1 = Object.assign({}, chartMode);
                    var chart2 = Object.assign({}, chartMode);
                    var chart3 = Object.assign({}, chartMode);
                    // var yAxis2 = Object.assign({},option.yAxis[0])
                    option.yAxis.push({
                        type: "value",
                        splitLine: false,
                        axisLine: false
                    });

                    chart1["name"] = "总盈利";
                    // chart1.areaStyle = areaStyle1;
                    chart1.itemStyle = itemStyle1;
                    chart1.data = totalGain;
                    chart2["name"] = "投入";
                    chart2.itemStyle = itemStyle2;
                    chart2.areaStyle = areaStyle2;
                    chart2.data = investData;
                    chart3.name = "盈利";
                    chart3.itemStyle = itemStyle3;
                    chart3.data = gainData;
                    chart3.yAxisIndex = 1;
                    option.series = [chart2, chart3, chart1];
                    option.yAxis[0].max =
                        Math.max.apply(null, res.fundGross) * 1.3;
                    option.yAxis[1].max =
                        Math.max.apply(null, res.fundGain) * 3;
                    this.chartOption = option;
                    // console.log(option);
                    // myChart.setOption(option, true);
                })
                .catch(err => {
                    console.log(err);
                });
        },
        getOption() {
            return {
                backgroundColor: "#fff",
                // 标题
                title: {
                    text: "基金情况",
                    textStyle: {
                        align: "left",
                        color: "#080b30",
                        fontSize: 20
                    },
                    top: "5%",
                    left: "center"
                },
                // 获取焦点后的展示
                tooltip: {
                    trigger: "axis",
                    textStyle: {
                        align: "left"
                    }
                },
                // 顶部工具栏
                toolbox: {
                    left: "right",
                    feature: {
                        dataZoom: {
                            yAxisIndex: "none"
                        },
                        restore: {},
                        saveAsImage: {}
                    }
                },
                // 图表所占的面积
                grid: {
                    top: "15%",
                    left: "5%",
                    right: "5%",
                    bottom: "15%"
                    // containLabel: true
                },
                // x轴
                xAxis: [
                    {
                        type: "time",
                        axisLine: {
                            show: true
                        },
                        splitArea: {
                            // show: true,
                            color: "#f00",
                            lineStyle: {
                                color: "#f00"
                            }
                        },
                        axisLabel: {
                            color: "#6c50f3"
                        },
                        splitLine: {
                            show: false
                        },
                        boundaryGap: false
                        // data: ["A", "B", "C", "D", "E", "F"]
                    }
                ],
                // y轴
                yAxis: [
                    {
                        type: "value",
                        // min: 0,
                        // max: 140,
                        splitNumber: 4,
                        splitLine: {
                            show: true,
                            lineStyle: {
                                color: "rgba(255,255,255,0.1)"
                            }
                        },
                        axisLine: {
                            show: false
                        },
                        axisLabel: {
                            show: false,
                            margin: 20,
                            textStyle: {
                                color: "#d1e6eb"
                            }
                        },
                        axisTick: {
                            show: false
                        }
                    }
                ],
                dataZoom: [
                    {
                        show: true,
                        height: 12,
                        xAxisIndex: [0],
                        bottom: "8%",
                        start: 0,
                        end: 100,
                        handleIcon:
                            "path://M306.1,413c0,2.2-1.8,4-4,4h-59.8c-2.2,0-4-1.8-4-4V200.8c0-2.2,1.8-4,4-4h59.8c2.2,0,4,1.8,4,4V413z",
                        handleSize: "110%",
                        handleStyle: {
                            color: "#d3dee5"
                        },
                        textStyle: {
                            color: "#323a5e"
                        },
                        borderColor: "#90979c"
                    },
                    {
                        type: "inside",
                        show: true,
                        height: 15,
                        start: 1,
                        end: 35
                    }
                ],
                series: []
            };
        },
        getChartMode() {
            return {
                name: "qwwqwq",
                type: "line",
                smooth: true,
                showAllSymbol: true,
                symbol: "circle",
                symbolSize: 10,
                label: {
                    show: true,
                    position: "top",
                    textStyle: {
                        color: "#00ca95"
                    }
                }
            };
        },
        updateCharts() {
            let myChart = echarts.init(document.getElementById(this.chartID));
            myChart.resize();
        }
    }
};
</script>
<style scoped>
.hello * {
   
    margin: 0 auto;
}
</style>