<template>
    <div>
        <div v-show="adminShow">
            <el-table :data="tableData" border style="width: 100%">
                <el-table-column type="index" label="序号"></el-table-column>
                <el-table-column label="申请人" prop="applyName"></el-table-column>
                <el-table-column label="申请日期" prop="applyDate"></el-table-column>
                <el-table-column label="申请状态">
                    <template slot-scope="scope">
                        <div v-if="scope.row.applyStatus == 1"><el-tag size="medium" type="warning">待审核</el-tag></div>
                    </template>
                </el-table-column>
                <el-table-column label="申请内容" prop="applyContent"></el-table-column>
                <el-table-column label="申请文件名" prop="files.filesName"></el-table-column>
                <el-table-column label="文件路径" prop="files.filesPath"></el-table-column>
                <el-table-column label="申请账号" prop="users.account"></el-table-column>
                <el-table-column fixed="right" label="操作" width="100">
                    <template slot-scope="scope">
                        <el-button @click="approve(scope.row, scope.index)" type="text" size="small">通过</el-button>
                        <el-button @click="noApprove(scope.row, scope.index)" type="text" size="small">不通过</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>


<script>
export default {
    data() {
        return {
            userData: [],
            adminShow: false,
            tableData:[]
        };
    },
    methods: {
        approve(row, index) {
            // row.concat({"status":2})
            row['status'] = 2;
            console.log(index);
            this.$axios
                .post('api/record/updateRecord', row)
                .then((res) => {
                    if(res.data.msg=="ok"){
                        this.$message.success(res.data.obj);
                    }else{
                        this.$message.error(res.data.obj)
                    }
                    this.initRecord();
                })
                .catch((res) => {});
        },
        noApprove(row, index) {
            row['status'] = 3;

            this.$axios
                .post('api/record/updateRecord', row)
                .then((res) => {
                    if(res.data.msg=="ok"){
                        this.$message.success(res.data.obj);
                    }else{
                        this.$message.error(res.data.obj)
                    }
                    this.initRecord();
                })
                .catch((res) => {});
        },
        select() {
            var that = this;
            console.log(that.status);
            if (that.status != '' && that.status != null) {
                this.$axios
                    .get('api/record/getRecordByStatus?status=' + that.status + '&userId=' + that.userData.user.id)
                    .then((result) => {
                        console.log(result.data);
                        that.statusShow = true;

                        that.tableData = result.data;
                    })
                    .catch((error) => {
                        if (error.response.status == '403') {
                            this.$router.push('/login');
                        } else {
                            this.$message.error(error.response.data.message);
                        }
                    });
            }
        },
        initRecord() {
            let that = this;
            this.$axios
                .get('api/record/getRecordStatusOne')
                .then((result) => {
                    console.log(result.data);
                    that.tableData = result.data;
                })
                .catch((error) => {
                    console.log(error);
                });
        }
    },

    created() {
        let that = this;
        this.userData = JSON.parse(sessionStorage.getItem('userData'));
        let user = this.userData.user;
        if (user.roleGroup.groupName == '管理员' || user.roleGroup.groupName == '文件管理员') {
            that.adminShow = true;
            that.selectShow = false;
            that.statusOneShow = false;
            that.statusTwoShow = false;
            that.initRecord();
        }
    }
};
</script>

<style>
</style>
