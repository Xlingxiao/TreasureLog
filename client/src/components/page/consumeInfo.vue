<template>
    <!-- 消费情况 日月年 -->
    <div class="hello">
        <!-- <stage :rowOption="chartOption" v-if="chartOption != {}"></stage> -->
        <!-- 遍历n个图表 -->
        <div v-if="charts.length > 0" v-for="chartInfo in charts" :style='style.stage'>
            <stage :chartHeight = "chartHeight" :rowOption="chartInfo.chartOption" v-if="chartInfo.chartOption != {}"></stage>
        </div>
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
            chartID: "consumeInfo",
            style: {
                stage: {
                    width: "50%",
                    display: 'inline-block'
                }
            },
            chartHeight: 500,
            charts: [
                {
                    chartID: "",
                    chartName: "日均支出",
                    chartOption: {}
                },
                {
                    chartID: "",
                    chartName: "月支出",
                    chartOption: {}
                },
                {
                    chartID: "",
                    chartName: "本年总支出",
                    chartOption: {}
                },
                {
                    chartID: "",
                    chartName: "年支出收入比",
                    chartOption: {}
                }
            ],
            chartOption: {}
        };
    },
    mounted() {
        // this.$store.commit("updateUserAccount", "1");
        // this.userAccount = this.$store.state.userAccount;
        this.initStage();
        this.drawing();
    },
    methods: {
        // 初始化空间
        initStage() {
            let windowHeight = window.innerHeight;
            this.chartHeight = (windowHeight - 90) / 2
            this.style.stage.height = this.chartHeight + "px";
            this.style.stage.width = window.innerWidth / 2;
        },
        // 绘画图表
        drawing() {
            this.chartOption = this.getOption();
            this.http.getSpendInfo({}).then(res => {
                let values = [res.dailySpend, res.monthSpend, res.yearSpend, (res.yearSpend / res.annualIncome) * 100 ];
                for (let i in this.charts) {
                    let chartInfo = this.charts[i];
                    let chartName = chartInfo.chartName;
                    let value = Math.abs(values[i]).toFixed(2)
                    let data = [
                        {
                            value: value,
                            name: chartInfo.chartName
                        }
                    ];
                    let chartOption = this.getOption();
                    if(chartName != '年支出收入比') {
                        chartOption.series[0].detail.formatter = '{value}￥';
                        // let value = Math.abs(values[i]).toFixed(0) + '';
                        // let maxValue = 1;
                        // for(let i = 0; i < value.length; i++) {
                        //     maxValue += '0'
                        // }
                        let radio = 0.5;
                        if(chartName != '日均支出') {
                             maxValue = (res.monthlyIncome * radio / 30).toFixed(0)
                        } else if(chartName != '月支出') {
                             maxValue = (res.monthlyIncome * radio).toFixed(0)
                        } else if(chartName != '本年总支出') {
                             maxValue = (res.annualIncome * radio).toFixed(0)
                        }
                        chartOption.series[0].max = maxValue;
                    }
                    chartOption.series[0].name = chartName;
                    chartOption.series[0].data = data;
                    chartInfo.chartOption = chartOption;
                }
            })
            
        },
        // 获取模板
        getOption() {
            return {
                backgroundColor: "#ffffff",
                color: ["#37A2DA", "#32C5E9", "#67E0E3"],
                series: [
                    {
                        name: "业务指标",
                        type: "gauge",
                        detail: {
                            formatter: "{value}%",
                            offsetCenter: ["0", "70%"],
                            fontSize: 30
                        },
                        axisLine: {
                            show: true,
                            lineStyle: {
                                width: 30,
                                shadowBlur: 0,
                                color: [
                                    [0.3, "#67e0e3"],
                                    [0.7, "#37a2da"],
                                    [1, "#fd666d"]
                                ]
                            }
                        },
                        title: {
                            offsetCenter: ["0", "40%"],
                            fontSize: 25
                        },
                        data: [
                            {
                                value: 50,
                                name: "完成率"
                            }
                        ]
                    }
                ]
            };
        }
    }
};
</script>
<style scoped>
.hello * {
    text-align: center;
    /* margin: 0 auto; */
}
</style>