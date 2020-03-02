<template>
    <charts :rowOption="option" :theme="theme" :title="title"></charts>
</template>

<script>
import charts from "../charts/Charts";
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
            option: {
                toolbox: {
                    show: true,
                    feature: {
                        mark: {
                            show: true
                        },
                        dataView: {
                            show: true,
                            readOnly: false
                        },
                        magicType: {
                            show: true,
                            type: ["pie", "funnel"]
                        },
                        restore: {
                            show: true
                        },
                        saveAsImage: {
                            show: true
                        }
                    }
                },
                calculable: true,
                tooltip: {
                    trigger: "item",
                    formatter: "{a}<br/>{b}: {c}"
                },
                title: {
                    text: "南丁格尔玫瑰图",
                    left: "center",
                    top: 20
                },
                calculable: true,
                legend: {
                    icon: "circle",
                    x: "center",
                    y: "15%",
                    data: [
                        "义乌市1",
                        "义乌市2",
                        "义乌市3",
                        "义乌市4",
                        "义乌市5",
                        "义乌市6",
                        "义乌市7",
                        "义乌市8",
                        "义乌市9"
                    ]
                },
                series: [
                    {
                        name: "",
                        type: "pie",
                        radius: [37, 155],
                        avoidLabelOverlap: false,
                        startAngle: 0,
                        center: ["50.1%", "65%"],
                        roseType: "area",
                        selectedMode: "single",
                        label: {
                            normal: {
                                show: true,
                                formatter: "{c}"
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        labelLine: {
                            normal: {
                                show: true,
                                smooth: false,
                                length: 20,
                                length2: 10
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        data: []
                    }
                ]
            },
            title: "主要支出",
            theme: "macarons"
        };
    },
    created() {
        },
    beforeMount() {
        },
    mounted() {
        this.initExpends();
        
    },
    methods: {
        initExpends() {
            let params = {
                userAccount: 1
            };
            this.http
                .getMainExpend(params)
                .then(res => {
                    console.log(res);
                    this.title = "主要支出";
                    this.theme = "macarons";
                    let chartData = new Array();
                    let legendData = new Array();
                    res.forEach(item => {
                        // console.log(item.key, item.value);
                        legendData.push(item.key)
                        chartData.push({ name: item.key, value: item.value });
                    });
                    console.log(chartData);
                    this.option.title.text = this.title;
                    this.option.legend.data = legendData;
                    this.option.series[0].name = this.title;
                    this.option.series[0].data = chartData;
                    console.log(this.option)
                })
                .catch(err => {
                    console.log(err);
                });
        }
    }
};
</script>
