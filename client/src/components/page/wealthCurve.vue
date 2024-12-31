<template>
    <!-- 财富变化曲线 -->
    <div class="hello">
        <stage :rowOption="chartOption" v-if="chartOption != {}"></stage>
        <stage :rowOption="chartOption2" v-if="chartOption2 != {}"></stage>
        <!-- <div :style="style.stage" :id="chartID"></div>
        <div :style="style.stage" :id="chartID2"></div> -->
    </div>
</template>

<script>
import stage from "../charts/stage";
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
            chartID: "wealthCurve",
            chartID2: "wealthCurveMonth",
            style: {
                stage: {
                    width: "100%"
                    // height: "500px"
                }
            },
            chartOption: {},
            chartOption2: {}
        };
    },
    mounted() {
        // this.$store.commit("updateUserAccount", "1");
        // this.userAccount = this.$store.state.userAccount;
        this.initStage();
        this.drawing();
    },
    methods: {
        initStage() {
            let windowHeight = window.innerHeight;
            this.style.stage.height = windowHeight - 90 + "px";
            this.style.stage.width = window.innerWidth;
        },
        drawing() {
            let params = {
                // userAccount: this.$store.state.userAccount,
                startDate: "2010-01-01",
                endDate: new Date()
            };
            this.http
                .getWealthCurve(params)
                .then(res => {
                    // let myChart = echarts.init(
                    //     document.getElementById(this.chartID)
                    // );
                    let option = this.getOption();
                    let chartMode = this.getChartMode();

                    let itemStyle1 = {
                        normal: {
                            color: new echarts.graphic.LinearGradient(
                                0,
                                0,
                                0,
                                1,
                                [
                                    {
                                        offset: 0,
                                        color: "#fccb05"
                                    },
                                    {
                                        offset: 1,
                                        color: "#f5804d"
                                    }
                                ]
                            ),
                            barBorderRadius: 12
                        }
                    };

                    let itemStyle2 = {
                        normal: {
                            color: new echarts.graphic.LinearGradient(
                                0,
                                0,
                                0,
                                1,
                                [
                                    {
                                        offset: 0,
                                        color: "#8bd46e"
                                    },
                                    {
                                        offset: 1,
                                        color: "#09bcb7"
                                    }
                                ]
                            ),
                            barBorderRadius: 11
                        }
                    };
                    // 历史总资产
                    let totalAssetsData = new Array();
                    // 历史总薪水
                    let totalPay = new Array();
                    let logDate = new Array();
                    res.sort((a, b) => {
                        return a.insertTime - b.insertTime;
                    });
                    console.log(res);
                    let latestPay = 0;
                    let latestAssets = 0;
                    for (let i in res) {
                        // if(i == 0) {
                        //     latestPay = res[i].pay;
                        //     latestAssets = latestPay + res[i].expend;
                        // }else {
                        //     latestPay += res[i].pay;
                        //     latestAssets =res[i].pay + totalAssetsData[i - 1] + res[i].expend; 
                        //     console.log("历史薪水累计：", latestPay);
                        // }
                        latestPay += res[i].pay;
                        console.log("历史薪水累计：", latestPay);
                        totalAssetsData.push(res[i].totalAsset);
                        totalPay.push(latestPay);
                        logDate.push(
                            this.dateFormat(
                                "YYYYmmdd",
                                new Date(res[i].date)
                            )
                        );
                    }

                    var chart1 = Object.assign({}, chartMode);
                    var chart2 = Object.assign({}, chartMode);
                    chart1["name"] = "总资产";
                    chart1.itemStyle = itemStyle1;
                    chart1.data = totalAssetsData;
                    chart2["name"] = "总收入";
                    chart2.itemStyle = itemStyle2;
                    chart2.data = totalPay;
                    option.series = [chart1, chart2];
                    option.xAxis.data = logDate;

                    console.log(option);
                    // myChart.setOption(option);
                    this.chartOption = option;
                    this.showMonthWealthCurve(res,option);

                })
                .catch(err => {
                    console.log(err);
                });
        },
        // 展示月消费额和收入
        showMonthWealthCurve(res,option) {
            
            let option2 = JSON.parse(JSON.stringify(option))
            // let option2 = Object.assign({}, option);
                    // let myChart2 = echarts.init(
                    //     document.getElementById(this.chartID2)
                    // );
                    let pays = new Array();
                    let expenditure = new Array();
                    let dateList = new Array();
                    for (let i in res) {
                        expenditure.push(Math.abs(res[i].expenditure));
                        pays.push(res[i].pay);
                    }
                    option2.title['text'] = '月消费走势'
                    option2.series[1]["name"] = "月收入";
                    option2.series[1]["data"] = pays;
                    option2.series[0]["data"] = expenditure;
                    option2.series[0]["name"] = "月消费";
                    // option2.xAxis.data = dateList;
                    // myChart2.setOption(option2);
                    this.chartOption2 = option2;
        },
        getOption() {
            return {
                title: {
                    text: "总资产走势",
                    textStyle: {
                        align: "center",
                        color: "#323a5e",
                        fontSize: 20
                    },
                    top: "5%",
                    left: "center"
                },
                backgroundColor: "#fff",
                tooltip: {
                    trigger: "axis",
                    axisPointer: {
                        // 坐标轴指示器，坐标轴触发有效
                        type: "shadow" // 默认为直线，可选为：'line' | 'shadow'
                    },
                    textStyle: {
                        align: "left"
                    }
                },
                grid: {
                    left: "2%",
                    right: "4%",
                    bottom: "14%",
                    top: "16%",
                    containLabel: true
                },
                legend: {
                    data: ["1", "2", "3"],
                    right: 10,
                    top: 12,
                    textStyle: {
                        color: "#323a5e"
                    },
                    itemWidth: 12,
                    itemHeight: 10
                    // itemGap: 35
                },
                xAxis: {
                    // type: "categray",h
                    axisLine: {
                        lineStyle: {
                            color: "#323a5e"
                        }
                    },
                    axisLabel: {
                        textStyle: {
                            fontFamily: "Microsoft YaHei"
                        }
                    }
                },
                yAxis: {
                    type: "value",
                    // 应该设置为比当前最大值大10%
                    // max: "1200",
                    axisLine: {
                        show: false,
                        lineStyle: {
                            color: "#323a5e"
                        }
                    },
                    splitLine: {
                        show: true,
                        lineStyle: {
                            color: "rgba(50,58,94,0.3)"
                        }
                    },
                    axisLabel: {}
                },
                dataZoom: [
                    {
                        show: true,
                        height: 12,
                        xAxisIndex: [0],
                        bottom: "8%",
                        start: 30,
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
                name: "1",
                type: "bar",
                barWidth: "15%"
            };
        },
        // date对象格式化传入格式 (yyyymmdd,new Date())
        dateFormat(fmt, date) {
            let ret;
            let opt = {
                "Y+": date.getFullYear().toString(), // 年
                "m+": (date.getMonth() + 1).toString(), // 月
                "d+": date.getDate().toString(), // 日
                "H+": date.getHours().toString(), // 时
                "M+": date.getMinutes().toString(), // 分
                "S+": date.getSeconds().toString() // 秒
                // 有其他格式化字符需求可以继续添加，必须转化成字符串
            };
            for (let k in opt) {
                ret = new RegExp("(" + k + ")").exec(fmt);
                if (ret) {
                    fmt = fmt.replace(
                        ret[1],
                        ret[1].length == 1
                            ? opt[k]
                            : opt[k].padStart(ret[1].length, "0")
                    );
                }
            }
            return fmt;
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