<template>
  <div class="showBox">
    <el-row class="el-row-wrap">
      <el-col class="el-col-wrap">
          <el-select v-model="currentDataSource" @change="updateSource()" placeholder="数据源">
            <el-option v-for="item in dataSourceList" :key="item" :label="item" :value="item">
                <span>{{ item }}</span>
            </el-option>
          </el-select>
        <el-button @click="query" type="primary" icon="el-icon-upload" style="margin-left: 15px">刷新</el-button>
        <el-button @click="showEdit('insert',tableData[0])" type="primary" icon="el-icon-upload" style="margin-left: 15px">新增一条</el-button>
      </el-col>
    </el-row>
    <el-table :data="tableData" border height="700" lazy v-loading="loading" stripe style="width: 100%">
      <el-table-column type="index" width="50" label='序号'></el-table-column>
      <el-table-column
        v-for="(column, index) in currentColumn"
        :key="index"
        :prop="column.prop"
        :label="column.label"
        :sortable="column.sortable"
        :align="column.align"
      >
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="100">
        <template slot-scope="scope">
          <el-button @click="showEdit('update',scope.row)" type="text" size="small">编辑</el-button>
          <el-popconfirm title="确定删除吗？" @cancel="deleteRow()" @onConfirm="deleteRow(scope.row)"> 
              <el-button type="text" slot="reference" size="small">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="编辑内容" :visible.sync="dialogVisible" width="350px" top="0" style="margin-top: 50px" >
      <div v-for="(column, index) in editData" :key="index" class = "inputRow">
        <el-col :span="5" class="el-col-wrap">
			<div class="colorItem">{{column.label}}</div>
		</el-col>
		<el-col :span="19" class="el-col-wrap">
			<div class>
				<el-input v-model="column.value" class="inputItem"></el-input>
			</div>
		</el-col>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false" size="small">取 消</el-button>
        <el-button type="primary" @click="updateRow(editData)" size="small">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "edit",
  data() {
    return {
        dataSourceList: ['channel','info','expend','invest'],
        currentDataSource: 'channel',
        columns: {
            channel: [
                { prop: "channel1", label: "渠道", sortable: true, align: "left" },
                { prop: "channel2", label: "细分渠道", sortable: true, align: "left"},
                { prop: "money", label: "余额", sortable: true, align: "right"},
                { prop: "insertTime", label: "记录日期", sortable: true, align: "left"},
            ],
            info: [
                { prop: "pay", label: "收入", sortable: true, align: "left" },
                { prop: "expenditure", label: "花费", sortable: true, align: "right" },
                { prop: "date", label: "记录日期", sortable: true, align: "left" },
            ],
            expend: [
                { prop: "info", label: "花费名称", sortable: true, align: "left" },
                { prop: "amount", label: "金额", sortable: true, align: "right" },
                { prop: "essential", label: "是否必要", sortable: true, align: "left" },
                { prop: "detail", label: "详情", sortable: true, align: "left" },
                { prop: "insertTime", label: "记录日期", sortable: true, align: "left" },
            ],
            invest: [
                { prop: "channel", label: "渠道", sortable: true, align: "left" },
                { prop: "invest", label: "投资金额", sortable: true, align: "left" },
                { prop: "gross", label: "总金额", sortable: true, align: "left" },
                { prop: "insertTime", label: "记录日期", sortable: true, align: "left" },
            ]
      },
      currentColumn: [],
	  loading: false,
      tableData: [],
      currentRow: {},
      opt: '',
      dialogVisible: false,
      visible: false,
	  // 当前编辑的数据
	  editData: {},
    };
  },
  watch: {},
  mounted() {
    this.currentColumn = this.columns.channel;
    this.query();
  },
  methods: {
    // 添加一个主要花销
    query() {
        console.log("query " + this.currentDataSource);
		this.loading = true;
        this.http.detailOpt(this.currentDataSource,'query',{}).then((res)=>{
            this.tableData = res;
			this.loading = false;
        }).catch(err=>{this.loading = false;})
    },
    // 更新数据源
    updateSource() {
		this.loading = true;
        console.log("dataSource:" + this.currentDataSource);
        this.http.detailOpt(this.currentDataSource,'query',{}).then((res)=>{
            this.currentColumn = this.columns[this.currentDataSource];
            this.tableData = res;
			this.loading = false;
        }).catch(err=>{this.loading = false;})
    },
    showEdit(opt,row) {
        this.opt = opt;
        this.currentRow = row;
        this.dialogVisible = true;
		console.log("opt:" + opt + " currentRow:" + JSON.stringify(row));
		var editData = JSON.parse(JSON.stringify(this.currentColumn));
		if(opt == 'update') {
			for(let i in editData) {
				let item = editData[i];
				console.log('item:' + item)
				item.value = row[item.prop];
			}	
		}
		this.editData = editData;
    },
    // 新增或更新
    updateRow(row) {
        console.log(this.opt + " row:" + JSON.stringify(row));
        this.dialogVisible = false;
        var params = this.currentRow;
		for(let i in row) {
			let item = row[i];
			console.log('item:' + JSON.stringify(item));
			params[item.prop] = item.value;
		}
		if(this.opt == 'insert') {
			params.id = null;
		}
		console.log(this.opt + " params:" + JSON.stringify(params));
        this.http.detailOpt(this.currentDataSource,this.opt,params).then((res)=>{
            this.currentColumn = this.columns[this.currentDataSource];
            this.tableData = res;
			this.query();
        })
        
    },
    // 删除数据
    deleteRow(row) {
        console.log("delete row:" + JSON.stringify(row));
        var params = {"id": row.id}
        this.http.detailOpt(this.currentDataSource,'delete',params).then((res)=>{
            this.currentColumn = this.columns[this.currentDataSource];
            this.tableData = res;
			this.query();
        })
    },
  },
};
</script>


<style scoped>
.el-row-wrap {
  line-height: 60px;
}

.showBox {
  width: 90%;
  text-align: center;
  margin: 30px auto;
}

.inputRow {
	margin-top:10px; 
	height:50px;
}

.colorItem {
	height: 40px;
	line-height: 40px;
}
</style>