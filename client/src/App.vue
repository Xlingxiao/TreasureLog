<template>
    <login v-if="isLogin"></login>
    <div id="app" v-else>
        <layout></layout>
    </div>
</template>

<script>
import layout from "./components/Layout";
import login from "./components/auth/login"
export default {
    name: "App",
    components: {
        layout,
        login,
    },
    data() {
        return{
            isLogin:false
        }
    },
    mounted() {
        console.log(this.$route.path);
        if(this.$route.path == '/login'){
            this.isLogin = true;
        }
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
