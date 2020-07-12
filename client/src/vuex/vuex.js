import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

const store =  new Vuex.Store({
    state: {
        userAccount:"8928",
        chartNum:1,
        winWidth:document.documentElement.clientWidth, //屏幕宽度
        winHeight:document.documentElement.clientHeight, //屏幕高度
        currentUri:'',
    },
    mutations: {
        // 通过外界的新值来修改仓库中共享数据的值
        updateUserAccount(state, newUserAccount) {
            state.userAccount = newUserAccount;
        },
        // 更新chartID
        updateChartNum(state, num){
            state.chartNum = num;

        },
        // 更新窗口大小
        setWinSize(state){
            state.winWidth =
            document.documentElement.clientWidth;
            state.winHeight = document.documentElement.clientHeight;
            //sconsole.log("窗口大小变为：",state.winHeight,"*",state.winWidth)
        },
        setCurrentUri(uri) {
            state.currentUri = uri;
        }
    },
    actions: {}
})

export default store;