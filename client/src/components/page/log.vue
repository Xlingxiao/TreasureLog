<template>
    <div :style="style.box">
        <completeLog></completeLog>
    </div>
</template>

<script>
import completeLog from "./log/completeLog";
import logOnceExpend from "./log/logOnceExpend";

export default {
    name: "log",
    components: {
        completeLog,
        logOnceExpend
    },
    data() {
        return {
            style: {
                box: {
                    width: "200px",
                    margin: "0 auto"
                }
            }
        };
    },
    watch: {
        style(lod, value) {
            let width = value.box.width;
            console.log(width);
        }
    },
    mounted() {
        let height = window.innerHeight;
        let width = window.innerWidth;
        if(width > height)
            this.style.box.width = "50%";
        else 
            this.style.box.height = "100%"
    },
    methods: {}
};
</script>
