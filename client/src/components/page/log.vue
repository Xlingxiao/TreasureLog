<template>
    <div :style="style.box">
        <completeLog v-show="selectLog == 'complete'"></completeLog>
        <logOnceExpend v-show="selectLog == 'expend'"></logOnceExpend>
        <logOnceInvest v-show="selectLog == 'invest'"></logOnceInvest>
    </div>
</template>

<script>
import completeLog from "./log/completeLog";
import logOnceExpend from "./log/logOnceExpend";
import logOnceInvest from "./log/logOnceInvest";

export default {
    name: "log",
    components: {
        completeLog,
        logOnceExpend,
        logOnceInvest
    },
    data() {
        return {
            style: {
                box: {
                    width: "200px",
                    margin: "0 auto"
                }
            },
            selectLog:"complete"
        };
    },
    watch: {
        style(lod, value) {
            let width = value.box.width;
            console.log(width);
        },
        $route(){
        let path = this.$route.path.split("/");
        if(path && path.length > 0)
            this.selectLog = path[path.length - 1]
        }
    },
    mounted() {
        
        let height = window.innerHeight;
        let width = window.innerWidth;
        if(width > height)
            this.style.box.width = "50%";
        else 
            this.style.box.height = "100%"
        let path = this.$route.path.split("/");
        if(path && path.length > 0)
            this.selectLog = path[path.length - 1]
    },
    methods: {}
};
</script>
