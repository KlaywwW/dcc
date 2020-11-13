<template>
	<div>

		<!-- <el-table :data="tableData" style="width: 100%" row-key="id" border :tree-props="{children: 'children'}"
		 :header-cell-style="tableHeaderColor">
			<el-table-column prop="" label="编号">
			</el-table-column>
			<el-table-column prop="" label="文件名">
			</el-table-column>
			<el-table-column prop="" label="版本">
			</el-table-column>
			<el-table-column prop="" label="创建时间">
			</el-table-column>
			<el-table-column prop="" label="创建人">
			</el-table-column>
			<el-table-column prop="" label="机密等级">
			</el-table-column>
			<el-table-column label="浏览" id="show">
				<el-table-column prop="" label="浏览文档">
				</el-table-column>
				<el-table-column prop="" label="历史记录"></el-table-column>
			</el-table-column>
			<el-table-column label="异动" id="update">
				<el-table-column prop="" label="变更版本">
				</el-table-column>
				<el-table-column prop="" label="上传附件">
				</el-table-column>
				<el-table-column prop="" label="编辑编号">
				</el-table-column>
			</el-table-column>
			</el-table-column>
		</el-table> -->

	</div>
</template>

<script>
	export default {
		name: '',

		data() {
			return {
				tableData: [{
					id: 1,
					date: '2016-05-02',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					id: 2,
					date: '2016-05-04',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1517 弄'
				}, {
					id: 3,
					date: '2016-05-01',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1519 弄',
					children: [{
						id: 31,
						date: '2016-05-01',
						name: '王小虎',
						address: '上海市普陀区金沙江路 1519 弄'
					}, {
						id: 32,
						date: '2016-05-01',
						name: '王小虎',
						address: '上海市普陀区金沙江路 1519 弄'
					}]
				}, {
					id: 4,
					date: '2016-05-03',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1516 弄'
				}]
			}
		},
		created() {
			this.change();
		},
		// 监听vuex中数据变化
		computed: {
			doc() {
				return this.$route.query.param;
			}
		},
		watch: {
			doc() {
				this.change();
			}
		},

		methods: {
			tableHeaderColor({
				row,
				rowIndex,
				columnIndex
			}) {
				// console.log(row) //表头行标号为0
				// console.log(row);
				// console.log(rowIndex);
				// console.log(columnIndex);
				if (rowIndex === 0 && columnIndex === 6) {
					return 'background-color: #409EFF;text-align:center;font-size:16;color:gray;';
				} else if (rowIndex === 0 && columnIndex === 7) {
					return 'background-color: #ffff66;text-align:center;font-size:16;color:gray;';
				}

			},
			change() {
				let index = this.$route.query.param;
				var _this = this;
				// 人资判断
				if (index == "1_2") {
					this.title = "人资表格样本";
				}
				// 财务判断
				if (index == "2_2") {
					this.title = "财务表格样本";
				}
				// 采购判断
				if (index == "3_2") {
					this.title = "采购表格样本";
				}
				// 仓库判断
				if (index == "4_2") {
					this.title = "仓库表格样本";
				}
				// 品保判断
				if (index == "5_2") {
					this.title = "品保表格样本";
				}
				// 业务判断
				if (index == "6_2") {
					this.title = "业务表格样本";
				}
				// 生产办判断
				if (index == "7_2") {
					this.title = "生产办表格样本";
				}
				// TPM判断
				if (index == "8_2") {
					this.title = "TPM表格样本";
				}
				// 研发判断
				if (index == "9_2") {
					this.title = "研发表格样本";
				}
			}
		}
	}
</script>

<style scoped>


</style>
