<template>
    <div class="hello">
        <div :style="style.stage" :id="chartID"></div>
    </div>
</template>

<script>
import echarts from "echarts";
import macarons from "echarts/theme/macarons2";
export default {
    name: "PieChart",
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
        },
        optionData: {
            type: Object,
            default() {
                return {}
            }
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
            min: 0,
            count: 0,
            // 饼图下钻状态
            pieState: { level: 1, level1Data: [], drillMap: {}, totalText: "", currentName: "" },
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
        },
        optionData: {
            handler(value, old) {
                console.log("update", value);
                this.updateChartData();
            }
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
            this.top = windowHeight < windowWidth ? this.min:this.min * 1.5
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
        // 更新数据：切换日期后重建饼图并重置到一级视图
        updateChartData() {
            this.myChart.hideLoading();
            let root = this.optionData.channelStatus;
            this.buildPieData(root);
            this.bindPieEvents();
            let sub = "收入: " + this.optionData.pay + " 消费: " + this.optionData.expenditure
                + " 被动收入: " + this.optionData.passiveIncome;
            this.renderPie(this.pieState.level1Data, this.pieState.totalText, sub, false);
        },
        // 默认展示一级分类环形饼图
        defaultDrawing() {
            let params = {
                userAccount: this.$store.state.userAccount
            };
            this.http
                .getLatestChannleData(params)
                .then(res => {
                    this.myChart = echarts.init(
                        document.getElementById(this.chartID),
                        this.theme
                    );
                    let root = res;
                    root.children.reverse();
                    this.buildPieData(root);
                    this.bindPieEvents();
                    this.renderPie(this.pieState.level1Data, this.pieState.totalText, "", false);
                })
                .catch(err => {
                    console.log(err);
                });
        },
        // 递归累加叶子节点绝对值（用于饼图占比切片大小）
        sumLeafValue(node) {
            if (!node.children || node.children.length === 0) {
                return Math.abs(node.value || 0);
            }
            return node.children.reduce((s, c) => s + this.sumLeafValue(c), 0);
        },
        // 递归累加叶子节点原始值（保留正负号，用于总资产计算）
        sumLeafSigned(node) {
            if (!node.children || node.children.length === 0) {
                return node.value || 0;
            }
            return node.children.reduce((s, c) => s + this.sumLeafSigned(c), 0);
        },
        // 由根节点构建一级分类数据，并缓存每个一级分类的二级下钻数据
        buildPieData(root) {
            let level1 = [];
            let drillMap = {};
            let total = 0;
            ((root && root.children) || []).forEach(cat => {
                let catTotal = this.sumLeafValue(cat);
                let catSigned = this.sumLeafSigned(cat);
                // 总资产按原始正负号累加，信用卡等负债做减项
                total += catSigned;
                // value 用绝对值决定切片大小，signed 保留正负号用于标签展示
                level1.push({ name: cat.name, value: catTotal, signed: catSigned });
                // 二级分类：若无 children 则用自身值
                let children = cat.children && cat.children.length
                    ? cat.children.map(sub => ({
                        name: sub.name,
                        value: this.sumLeafValue(sub),
                        signed: this.sumLeafSigned(sub)
                    }))
                    : [{ name: cat.name, value: catTotal, signed: catSigned }];
                drillMap[cat.name] = children;
            });
            this.pieState = {
                level: 1,
                level1Data: level1,
                drillMap: drillMap,
                totalText: "总资产：" + total,
                currentName: ""
            };
        },
        // 渲染环形饼图（含中心返回提示）
        renderPie(dataList, mainTitle, subTitle, showBack) {
            this.option = {
                title: [
                    { text: mainTitle, left: "center", top: (this.min * 10) / 600 },
                    {
                        text: subTitle || "", left: "center", top: (this.top * 30) / 600,
                        textStyle: { color: "#666", fontWeight: "lighter", fontSize: 15 }
                    },
                    {
                        text: showBack ? "点击中心返回" : "", left: "center",
                        // 对齐到饼图圆心 y 坐标，保证横竖屏下文字都在圆孔中心
                        top: (this.top * 300) / 700, textVerticalAlign: "middle",
                        textStyle: { color: "#999", fontSize: 13, fontWeight: "lighter" }
                    }
                ],
                textStyle: { fontSize: 15 },
                tooltip: {
                    trigger: "item",
                    formatter: params => {
                        let val = params.data.signed !== undefined ? params.data.signed : params.value;
                        return params.name + ": " + val + " (" + params.percent + "%)";
                    }
                },
                series: [{
                    type: "pie",
                    radius: ["20%", "62%"],
                    center: ["50%", (this.top * 300) / 700],
                    avoidLabelOverlap: true,
                    label: {
                        formatter: params => {
                            let val = params.data.signed !== undefined ? params.data.signed : params.value;
                            return params.name + "\n" + val;
                        }
                    },
                    data: dataList
                }]
            };
            // 第二个参数 true：全量刷新，彻底清除旧的 sunburst 配置
            this.myChart.setOption(this.option, true);
        },
        // 绑定点击事件：一级下钻 + 中心空白返回
        bindPieEvents() {
            if (this._pieEventBound) return;
            this._pieEventBound = true;
            // 点击一级扇区下钻到二级
            this.myChart.on("click", params => {
                if (this.pieState.level === 1) {
                    let children = this.pieState.drillMap[params.name];
                    if (children && children.length) {
                        this.pieState.level = 2;
                        this.pieState.currentName = params.name;
                        this.renderPie(children, params.name, "", true);
                    }
                }
            });
            // 点击中心空白（无 target）返回一级总览
            this.myChart.getZr().on("click", e => {
                if (this.pieState.level === 2 && !e.target) {
                    this.pieState.level = 1;
                    this.pieState.currentName = "";
                    this.renderPie(this.pieState.level1Data, this.pieState.totalText, "", false);
                }
            });
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
