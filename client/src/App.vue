<template>
    <router-view id="app"></router-view>
    <!-- <login v-if="isLogin"></login>
    <div id="app" v-else>
        <layout></layout>
    </div> -->
</template>

<script>
import layout from "./components/Layout";
export default {
    name: "App",
    components: {
        layout,
    },
    data() {
        return {
            
        };
    },
    created() {
        this.http.initContext(this);
        console.log(this.$route.path);
        let token = localStorage.getItem('token');
        if(!token) {
            this.isLogin = true;
        }
    },
    mounted() {
        var _this = this;
        window.onresize = function() {
            // 定义窗口大小变更通知事件
            _this.$store.commit("setWinSize");
        };
    }
};
</script>

<style>
body {
    margin: 0;
    padding: 0;
}
li {
    list-style: none;
}
#app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
}
</style>
