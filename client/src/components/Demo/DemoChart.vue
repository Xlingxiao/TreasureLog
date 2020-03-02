<template>
    <div class="hello">
        <h1>{{ msg }}</h1>
        <div class="main" :id="chartID"></div>
    </div>
</template>

<script>
import echarts from "echarts";
export default {
    name: "DemoChart",
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
            console.log("------------------------", this.chartID);
            let myChart = echarts.init(document.getElementById(this.chartID));
            var option = {
                series: {
                    type: "sunburst",
                    data: [
                        {
                            name: "alipay",
                            value: 28739,
                            children: [
                                {
                                    name: "balanceOfAccount",
                                    value: 6529
                                },
                                {
                                    name: "huabei",
                                    value: 113,
                                    itemStyle: {
                                        color: "red",
                                        emphasis: {
                                            color: "#ffa9a9"
                                        }
                                    }
                                },
                                {
                                    name: "fund",
                                    value: 22189
                                }
                            ]
                        },
                        {
                            name: "wechat",
                            value: 470,
                            children: [
                                {
                                    name: "balanceOfAccount",
                                    value: 470
                                }
                            ]
                        },
                        {
                            name: "bankCard",
                            value: 35289,
                            children: [
                                {
                                    name: "CCB",
                                    value: 33289
                                },
                                {
                                    name: "ICBC",
                                    value: 2000
                                }
                            ]
                        },
                        {
                            name: "creditCard",
                            value: 3662,
                            children: [
                                {
                                    name: "CCB",
                                    value: 3662,
                                    itemStyle: {
                                        color: "red",
                                        emphasis: {
                                            color: "#ffa9a9"
                                        }
                                    }
                                }
                            ],
                            itemStyle: {
                                color: "red",
                                emphasis: {
                                    color: "#ffa9a9"
                                }
                            }
                        }
                    ],
                    itemStyle: {
                        color: "#8989dd",
                        borderWidth: 2,
                        emphasis: {
                            color: "#a9a9ff"
                        }
                    }
                }
            };
            myChart.setOption(option)
        }
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
