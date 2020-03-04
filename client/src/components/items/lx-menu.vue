/**
* 生成menu
*/
<template>
    <div>
        <div v-if="title && mode=='vertical'" :style="style.title">
            <img :style="style.title.img" src="../../assets/logo.png" />
            <p :style="style.title.text">{{title}}</p>
        </div>
        <el-menu
            :mode="mode"
            :default-openeds="defaultOpeneds"
            :default-active="defaultActive"
            :background-color="backgroundColor"
            :text-color="textColor"
            :unique-opened="uniqueOpened"
            :active-text-color="activeTextColor"
            :router="router"
            v-on:select="handleSelect"
            class="el-menu-vertical-demo lxMenu"
        >
            <!-- <el-submenu index="1">
            <template slot="title">
                <i class="el-icon-location"></i>
                <span>导航一</span>
            </template>
                <el-menu-item index="1-1">选项1</el-menu-item>
                <el-menu-item index="1-2">选项2</el-menu-item>
                <el-menu-item index="1-3">选项3</el-menu-item>
            <el-submenu index="1-4">
                <template slot="title">选项4</template>
                <el-menu-item index="1-4-1">选项1</el-menu-item>
            </el-submenu>
            </el-submenu>-->
            <!-- <el-submenu v-for="(item,i) in menus" :index="item.index">
                <template slot="title" v-if="item.title">
                    <i class="el-icon-location"></i>
                    <span>{{item.title}}</span>
                </template>
                <el-menu-item
                    v-for="(secondMenu,j) in item.children"
                    :index="secondMenu.index"
                ><i class="el-icon-document"></i>{{secondMenu.name}}</el-menu-item>
            </el-submenu>-->
            <el-menu-item v-for="(item,j) in menus" :index="item.index">
                <i class="el-icon-document"></i>
                {{item.name}}
            </el-menu-item>
        </el-menu>
    </div>
</template>

<script type="text/javascript">
export default {
    //参数
    props: {
        defaultOpeneds: {
            type: Array,
            default() {
                return [];
            }
        },
        defaultActive: {
            type: String
        },
        backgroundColor: {
            type: String,
            default: "#fff"
        },
        textColor: {
            type: String,
            default: "#303133"
        },
        uniqueOpened: {
            type: Boolean,
            default: true
        },
        activeTextColor: {
            type: String,
            default: "#ecec66"
        },
        title: {
            type: String
        },
        router: {
            type: Boolean,
            default: false
        },
        menus: {
            Array,
            default() {
                return [];
            }
        },
        mode: {
            type: String,
            default: "vertical"
        }
    },
    data() {
        return {
            style: {
                title: {
                    height: "40px",
                    // border: "5px solid #409eff",
                    padding: "10px 0",
                    background: "#409eff",
                    img: {
                        height: "80%",
                        margin: "5px"
                    },
                    text: {
                        "line-height": "40px",
                        "vertical-align": "top",
                        margin: "0",
                        display: "inline-block",
                        "font-size": "22px",
                        color: "#fff"
                    }
                }
            }
        };
    },
    //监听
    watch: {},
    //计算
    computed: {},
    //方法
    mounted() {
        this.windHeight = window.innerHeight;
        this.windWidth = window.innerWidth;
    },
    methods: {
        handleSelect(key,keyPath) {
            console.log(key, keyPath)
            if(this.windHeight > this.windWidth)
                this.$emit('changeOpen')
            let currPage = this.$route.path;
            var to = keyPath[0]
            if (keyPath.length > 1) {
                to = keyPath[1]
            }
            if (to != currPage) {
                this.$router.push({
                    path: to
                })
            }
        }
    }
};
</script>

<style scoped>
.el-menu-item {
    border-top: 1px solid rgb(187, 181, 181);
}
.lxMenu {
    text-align: left;
    background: #409eff;
}
</style>
