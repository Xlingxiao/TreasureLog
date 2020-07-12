<template>
    <charts :rowOption="option" :title="title"></charts>
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
            let nowDate = new Date();
            let startDate = this.getDateString(-1,'month',nowDate);
            let params = {
                userAccount: this.$store.state.userAccount,
                startDate: startDate,
                endDate: nowDate
            };
            this.http
                .getMainExpend(params)
                .then(res => {
                    res.sort((a,b)=>{
                        return a.amount - b.amount;
                    })
                    console.log("res-->",res);
                    this.title = "主要支出";
                    let sumExpend = 0;
                    let chartData = new Array();
                    let legendData = new Array();
                    res.forEach(item => {
                        sumExpend += item.amount; 
                        legendData.push(item.info);
                        chartData.push({ name: item.info, value: item.amount });
                    });
                    console.log(chartData);
                    this.option.title.text = this.title;
                    this.option.title.subtext = '共计：' + sumExpend;
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
        },
        // 获取时间:yyyyMMdd
        // 接收4个参数：
        // 0. now
        // 1. date x
        // 2. month x
        // 3. year x
        // x 表示次数，例如五天前：(date,-5),一个月后：（month，1）
        getDateString(count, unit, nowDate) {
            // 获取当前时间毫秒数
            unit = unit.toLowerCase();
            nowDate = nowDate instanceof Date ? nowDate : new Date();
            let date = nowDate.getDate();
            let month = nowDate.getMonth();
            let year = nowDate.getFullYear();
            if (unit == 'date') {
                let value = nowDate.valueOf();
                let dayValue = 3600 * 24 * 1000; // 一天的毫秒数
                let addMill = dayValue * count;
                value += addMill;
                let newDate = new Date(value);
                return this.getDateString(0, '', newDate);
            } else if (unit == 'month') {
                month += count;
                // 获取溢出的年数
                let y = Math.floor(month / 12);
                year += y;
                month = month % 12;
                month = month < 0 ? 12 + month : month;
            } else if (unit == 'year') {
                year += count < year ? count : 0;
            }
            date = date < 10 ? '0' + date : date;
            month = month < 10 ? '0' + month : month;
            return new Date(year,month,date);
        }
    }
};
</script>
