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
                    text: "主要支出",
                    left: "center",
                    top: 20
                },
                calculable: true,
                legend: {
                    icon: "circle",
                    x: "center",
                    y: "5%",
                    data: []
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
            theme: "macarons",
            min: 0,
        };
    },
    created() {
        },
    beforeMount() {
        },
    mounted() {
        let windowHeight = window.innerHeight;
        let windowWidth = window.innerWidth;
        this.min = Math.min(windowHeight,windowWidth);
        this.initExpends();
    },
    methods: {
        initExpends() {
            let params = {
                userAccount: this.$store.state.userAccount
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
                        legendData.push(item.info)
                        chartData.push({ name: item.info, value: item.amount });
                    });
                    console.log(chartData);
                    this.option.title.text = this.title;
                    this.option.legend.data = legendData;
                    this.option.legend.y = this.min * 65/500;
                    this.option.series[0].radius = [this.min * 35/500,this.min * 135/500]
                    this.option.series[0].center = ["50%", (this.min * 350) / 600],
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
