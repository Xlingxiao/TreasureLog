<template>
    <div>
        <el-row class="el-row-wrap">
            <el-col :span="5">
                <p style="margin:0">账号</p>
            </el-col>
            <el-col :span="19">
                <el-input v-model="userAccount"></el-input>
            </el-col>
        </el-row>
        <el-row class="el-row-wrap">
            <el-col :span="5">
                <p style="margin:0">密码</p>
            </el-col>
            <el-col :span="19">
                <el-input v-model="password"></el-input>
            </el-col>
        </el-row>
    </div>
</template>

<script>
export default {
    name: "login",
    data() {
        return {
            userAccount: "",
            password: ""
        };
    },
    created() {},
    beforeMount() {},
    mounted() {},
    methods: {
        login() {}
    }
};
</script>
