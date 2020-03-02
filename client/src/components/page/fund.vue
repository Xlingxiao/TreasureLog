<template>
    <div class="hello">
        <div :style="style.stage" :id="chartID"></div>
    </div>
</template>

<script>
import charts from "../charts/Charts";
import echarts from "echarts";
export default {
    name: "treasure",
    components: {
        charts
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
                    width: "90%",
                    height: "500px"
                }
            },
        };
    },
    mounted() {
        this.$store.commit("updateUserAccount", "1");
        this.userAccount = this.$store.state.userAccount;
        this.initStage();
        this.drawing();
    },
    methods: {
        initStage() {
            let windowHeight = window.innerHeight;
            this.style.stage.height = windowHeight - 60 + "px";
            // this.style.stage.width = this.style.stage.height;
        },
        drawing() {
            let params = {
                userAccount: this.$store.state.userAccount,
                startDate: "2010-01-01",
                endDate: new Date()
            };
            this.http
                .getInvestInfo(params)
                .then(res => {
                    
                    let myChart = echarts.init(
                        document.getElementById(this.chartID)
                    );
                    let option = this.getOption();
                    let chartMode = this.getChartMode();
                    let itemStyle1 = {
                        color: "#6c50f3",
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
                        color: "#eee",
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
                    for(let i in dateData){
                        grossData.push([dateData[i],res.fundGross[i]]);
                        investData.push([dateData[i],res.fundInvest[i]])
                        gainData.push([dateData[i],res.fundGain[i]])
                    }

                    var chart1 = Object.assign({}, chartMode);
                    var chart2 = Object.assign({}, chartMode);
                    var chart3 = Object.assign({}, chartMode);
                    // var yAxis2 = Object.assign({},option.yAxis[0])
                    option.yAxis.push({type:"value",splitLine:false,axisLine:false});
                    chart3.yAxisIndex = 1;

                    chart1.itemStyle = itemStyle1;
                    chart1.areaStyle = areaStyle1;
                    chart1.data = grossData;
                    console.log(chart1);
                    console.log("chart1 name", chart1.name);

                    chart1["name"] = "总额";
                    chart2.itemStyle = itemStyle2;
                    chart2.areaStyle = areaStyle2;
                    chart2.data = investData;
                    chart2["name"] = "投入";
                    chart3.itemStyle = itemStyle3;
                    chart3.data = gainData;
                    chart3.name = "盈利";
                    option.series = [chart1, chart2, chart3];
                    option.yAxis[0].max = Math.max.apply(null,res.fundGross) * 1.3
                    option.yAxis[1].max = Math.max.apply(null,res.fundGain) * 3
                    
                    console.log(option);
                    myChart.setOption(option);
                })
                .catch(err => {
                    console.log(err);
                });
        },
        getOption() {
            return {
                backgroundColor: "#080b30",
                // 标题
                title: {
                    text: "基金情况",
                    textStyle: {
                        align: "center",
                        color: "#fff",
                        fontSize: 20
                    },
                    top: "5%",
                    left: "center"
                },
                // 获取焦点后的展示
                tooltip: {
                    trigger: "axis"
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
                            color: "#fff"
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
                        min: 0,
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
                },
            }
        }
    }
};
</script>
<style scoped>
.hello * {
    text-align: center;
    margin: 0 auto;
}
</style>