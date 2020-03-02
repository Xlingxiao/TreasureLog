import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

const store =  new Vuex.Store({
    state: {
        userAccount:"",
        chartNum:1,
    },
    mutations: {
        // 通过外界的新值来修改仓库中共享数据的值
        updateUserAccount(state, newUserAccount) {
            state.userAccount = newUserAccount;
        },
        // 更新chartID
        updateChartNum(state, num){
            state.chartID = num;

        }
    },
    actions: {}
})

export default store;