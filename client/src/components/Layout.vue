<template>
    <el-container :style="farmeStyle">
        <el-aside :width="asideWidth" style="background-color: rgb(238, 241, 246)">
            <lx-menu
                :menus="menus"
                :title="title"
                :router="false"
                :defaultActive="defaultActive"
                :defaultOpeneds="defaultOpeneds"
                :backgroundColor="asideColor"
                :textColor="asideFontColor"
            ></lx-menu>
        </el-aside>
        <el-container>
            <el-header>
                <span>{{title}}</span>
            </el-header>
            <el-main class="el-main">
                <!-- <img alt="Vue logo" src="../assets/logo.png" />
                <HelloWorld msg="Welcome to Your Vue.js App" />-->
                <!-- <DemoChart msg='示例图表'/> -->
                <!-- <DemoChartDataFromServer msg="示例图表：数据来自服务器" /> -->
                <!-- <Charts :msg="currentMenuName" /> -->
                <router-view></router-view>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
import HelloWorld from "./Demo/HelloWorld.vue";
import DemoChart from "./Demo/DemoChart";
import DemoChartDataFromServer from "./Demo/DemoChartDataFromServer";
import Charts from "./charts/Charts";

import lxMenu from "./items/lx-menu";

export default {
    components: {
        lxMenu,
        DemoChartDataFromServer,
        DemoChart,
        HelloWorld,
        Charts
    },
    data() {
        return {
            title: "Treasure Life",
            farmeStyle: {
                height: "600px"
            },
            asideWidth: "20%",
            asideColor: "#409EFF",
            defaultActive: "/treasure",
            defaultOpeneds: ["/treasure"],
            asideFontColor: "#fff",
            menus: [
                {
                    index: "/treasure",
                    name: "财富分布情况"
                },
                {
                    index: "/fund",
                    name: "基金收益"
                },
                {
                    index: "/extend",
                    name: "支出明细"
                }
            ],
            currentMenuName: ""
        };
    },
    mounted() {
        this.init();
    },
    methods: {
        init() {
            let windHeight = window.innerHeight;
            this.farmeStyle.height = windHeight + "px";
            this.currentMenuName = "财富分布情况";
        }
    }
};
</script>

<style>
.el-header {
    background-color: #409eff;
    color: #fff;
    line-height: 60px;
    font-size: 25px;
}

.el-aside {
    color: #333;
}

.el-main * {
    padding: 0;
}

.lxMenu {
    text-align: left;
    background: #409eff;
}
</style>
