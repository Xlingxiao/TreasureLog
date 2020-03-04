<template>
<div>

    <h1>hello once log</h1>
    <el-row >
        <el-col >
            <el-input v-model="pay" placeholder="薪水"></el-input>
        </el-col>
    </el-row>
   <el-input v-model="pay" placeholder="薪水"></el-input>
   <el-input v-model="pay" placeholder="薪水"></el-input>
</div>

</template>

<script>
export default {
    name: "logOnceExpend",
    data() {
        return {
            pay: "",
        };
    },
    mounted() {
        
    },
    methods: {
        
    }
};
</script>
