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
                    width: "60%",
                    margin: "0 auto",
                    'min-width': '400px'
                }
            },
            selectLog:"complete"
        };
    },
    watch: {

        $route(){
            let path = this.$route.path.split("/");
            if(path && path.length > 0)
                this.selectLog = path[path.length - 1]
        },
        windoWidth() {
            this.setWindow();
        }
    },
    computed: {
        windoWidth(){
            return this.$store.state.winWidth;
        }
    },
    mounted() {
        
        this.setWindow();
        let path = this.$route.path.split("/");
        if(path && path.length > 0)
            this.selectLog = path[path.length - 1]
    },
    methods: {
        // 屏幕自适应
        setWindow(){
            let height = window.innerHeight;
            let width = window.innerWidth * 0.8;
            let minWidth = width * 0.6;
            let maxWidth = width * 0.9;
            let maxSpan = maxWidth - minWidth;
            if(width > height) {
                let span = (maxSpan - (width - height)) * 100 / width;
                let boxWidth = 60 + span; 
                boxWidth = boxWidth < 60 ? 60 : boxWidth;
                this.style.box.width = boxWidth + "%";
            } else {
                let span = (maxSpan - (maxWidth - minWidth)) * 100 / width;
                let boxWidth = 90 - span; 
                boxWidth = boxWidth > 90 ? 90 : boxWidth;
                this.style.box.width = boxWidth + "%";
            }
            this.style.box.height = height;
        },
    }
};
</script>
