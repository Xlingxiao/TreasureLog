<template>
    <div id="app">
        <layout></layout>
    </div>
</template>

<script>
import layout from "./components/Layout";

export default {
    name: "App",
    components: {
        layout
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
