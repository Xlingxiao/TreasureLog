<template>
    <div class="mainBox" :style="logBoxStyle">
        <el-form
            :model="user"
            ref="user"
            label-width="100px"
            class="demo-ruleForm formStyle"
            @keyup.enter.native="submitCheck('user')"
        >
            <span></span>
            <!-- <el-form-item
                label="账号"
                prop="age"
                :rules="[
                    { required: true, message: '年龄不能为空'},
                    { type: 'number', message: '年龄必须为数字值'}
                    ]"
            >-->
            <el-form-item
                label="UserAccount"
                prop="account"
                :rules="[{ required: true, message: '账号不能为空'}]"
            >
                <el-input type="account" v-model.number="user.account" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item
                label="Password"
                prop="password"
                :rules="[{ required: true, message: '密码不能为空'}]"
            >
                <el-input
                    type="password"
                    v-model.number="user.password"
                    autocomplete="off"
                    @keyup.enter.native="submitCheck('user')"
                ></el-input>
            </el-form-item>
            <el-form-item label-width="0">
                <div class="submit">
                    <el-button type="primary" @click="submitCheck('user')">登录</el-button>
                    <!-- <el-button @click="resetForm('user')">重置</el-button> -->
                </div>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
export default {
    data() {
        return {
            logBoxStyle: {},
            user: {
                account: "",
                password: ""
            }
        };
    },
    mounted() {
        let windowHeight = window.innerHeight;
        let windowWidth = window.innerWidth;
        if (windowHeight > windowWidth) {
            this.logBoxStyle = {
                width: "90%",
                top: "30%",
                left: "5%"
            };
        }
    },
    methods: {
        // 登录检查
        submitCheck(formName) {
            this.$refs[formName].validate(valid => {
                if (valid) {
                    console.log(this.user);
                    this.submit();
                } else {
                    console.log("error submit!!");
                    return false;
                }
            });
        },
        // 登录
        submit(params) {
            this.http.login(this.user);
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        }
    }
};
</script>

<style scoped>
.mainBox {
    width: 50%;
    height: 300px;
    margin-top: -25%;
    position: absolute;
    top: 50%;
    left: 25%;
    border: 0.5px solid #ccc;
    border-radius: 2%;
}

.formStyle {
    margin: 15% auto;
    width: 80%;
}

.submit {
    width: 30%;
    margin: 0 auto;
}
</style>