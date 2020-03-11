<template>
    <el-container :style="style.farmeStyle">
        <el-aside v-show="isOpen" :width="asideWidth" style="background-color: rgb(238, 241, 246)">
            <lx-menu
                :mode="menuMode"
                :title="title"
                :router="false"
                :defaultOpeneds="defaultOpeneds"
                :backgroundColor="asideColor"
                :textColor="asideFontColor"
                @changeOpen="changeOpenStatus"
            ></lx-menu>
        </el-aside>
        <el-container>
            <el-header>
                <span>{{title}}</span>
            </el-header>
        <i @click="changeOpenStatus" :class="style.switchIcon" :style="style.switch"></i>
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

            style: {
                farmeStyle: {
                    height: "100%"
                },
                switch: {
                    height: "20px",
                    width: "20px",
                    "font-size": "20px",
                    "background": "rgba(128,128,128,0.15)"
                },
                switchIcon: "el-icon-arrow-left"
            },
            asideWidth: "20%",
            asideColor: "#409EFF",
            defaultOpeneds: ["/treasure"],
            asideFontColor: "#fff",
            menuMode: "horizontal",
            isVertical: true,
            currentMenuName: "",
            isOpen: true
        };
    },
    created() {
        this.init();
        },
    mounted() {
        if(this.windWidth < this.windHeight){
            this.isOpen = false
            this.asideWidth = "100%"
        }
    },
    methods: {
        init() {
            this.windHeight = window.innerHeight;
            this.windWidth = window.innerWidth;
            this.isVertical = true;
            this.menuMode = "vertical";
            this.style.farmeStyle.height = this.windHeight + "px";
            if (this.windHeight < this.windWidth) {
            } else {
                // this.asideWidth = "100%"
                // this.menuMode = "horizontal"
                // this.isVertical = false;
            }
            this.currentMenuName = "财富分布情况";
            console.log(this.windWidth, "---", this.windHeight);
        },
        changeOpenStatus() {
            this.isOpen = !this.isOpen;
            if(this.isOpen)
                this.style.switchIcon = "el-icon-arrow-left"
            else
                this.style.switchIcon = "el-icon-arrow-right"
            console.log("Switch Status",this.isOpen)
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

.el-main {
    padding: 0;
}

.lxMenu {
    text-align: left;
    background: #409eff;
}
</style>
