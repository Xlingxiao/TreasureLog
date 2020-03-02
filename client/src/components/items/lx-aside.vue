/**
* 生成aside
*/
<template>
        <el-aside :width="asideWidth">
            <lx-menu
                :menus="menus"
                :defaultActive="defaultActive"
                :backgroundColor="asideColor"
                :textColor="asideFontColor"
            ></lx-menu>
        </el-aside>
</template>

<script type="text/javascript">
import lxMenu from "./lx-menu";
export default {
    components: {
        lxMenu
    },
    //参数
    props: {
        asideWidth: {
            type: String,
            default: "30%"
        }
    },
    data() {
        return {
            asideColor: "#409EFF",
            defaultActive: ["1"],
            asideFontColor: "#fff",
            asideStyle: {
                width: ""
            },
            menus: [
                {
                    title: "导航栏",
                    index: "1",
                    children: [
                        {
                            index: "1-1",
                            name: "财富分布情况"
                        },
                        {
                            index: "1-2",
                            name: "基金收益"
                        }
                    ]
                },
            ]
        };
    },
    mounted() {
        this.asideStyle.width = this.asideWidth;
    },
    //监听
    watch: {},
    //计算
    computed: {},
    //方法
    methods: {}
};
</script>

<style scoped>
.lxMenu {
    text-align: left;
    background: #409eff;
}
</style>
