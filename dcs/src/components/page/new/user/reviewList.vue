<template>
    <div>
        <div v-show="selectShow">
            <el-select v-model="status" placeholder="请选择审核状态" clearable>
                <el-option label="待审核" value="1"></el-option>
                <el-option label="已通过" value="2"></el-option>
                <el-option label="未通过" value="3"></el-option>
            </el-select>
            <el-button type="primary" @click="select"> 查询 </el-button>
        </div>
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
        <div v-show="statusShow">
            <el-table :data="tableData" border style="width: 100%">
                <el-table-column type="index" label="序号"></el-table-column>
                <el-table-column label="申请人" prop="applyName"></el-table-column>
                <el-table-column label="申请日期" prop="applyDate"></el-table-column>
                <el-table-column label="申请状态">
                    <template slot-scope="scope">
                        <div v-if="scope.row.applyStatus == 1"><el-tag size="medium" type="warning">待审核</el-tag></div>
                        <div v-if="scope.row.applyStatus == 2"><el-tag size="medium" type="success">通过</el-tag></div>
                        <div v-if="scope.row.applyStatus == 3"><el-tag size="medium" type="danger">未通过</el-tag></div>
                    </template>
                </el-table-column>
                <el-table-column label="申请内容" prop="applyContent"></el-table-column>
                <el-table-column label="申请文件名" prop="files.filesName"></el-table-column>
                <el-table-column label="审核时间" prop="applyPassDate"></el-table-column>
                <el-table-column label="操作人" prop="operator"></el-table-column>
            </el-table>
        </div>
    </div>
</template>


<script>
export default {
    data() {
        return {
            userData: [],
            tableData: [],
            status: '',
            selectShow: true,
            statusShow: false,
            adminShow: false
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
                    this.$message.success(res.data);
                    this.tableData.splice(index, 1);
                })
                .catch((res) => {});
        },
        noApprove(row,index) {
            row['status'] = 3;

             this.$axios
                .post('api/record/updateRecord', row)
                .then((res) => {
                    this.$message.success(res.data);
                    this.tableData.splice(index, 1);
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
        }
    },
    created() {
        let that = this;
        this.userData = JSON.parse(sessionStorage.getItem('userData'));
        if (this.userData.role[0].roles.roleCode == 'admin') {
            that.adminShow = true;
            that.selectShow = false;
            that.statusOneShow = false;
            that.statusTwoShow = false;
            this.$axios
                .get('api/record/getRecordStatusOne')
                .then((result) => {
                    console.log(result.data);
                    that.tableData = result.data;
                    // for (let i = 0; i < result.data.length; i++) {
                    //     let applyStatus='';
                    //     if(result.data[i].applyStatus==1){
                    //         applyStatus="待审核"
                    //     }else if(result.data[i].applyStatus==2){
                    //         applyStatus="审核通过"
                    //     }else if(result.data[i].applyStatus==3){
                    //         applyStatus="审核未通过"
                    //     }
                    //     let tableData={
                    //         applyContent:result.data[i].applyContent,
                    //         applyDate:result.data[i].applyDate,
                    //         applyName:result.data[i].applyName,
                    //         applyPassDate:result.data[i].applyPassDate,
                    //         applyStatus:applyStatus,
                    //         fileId:result.data[i].fileId,
                    //         files:result.data[i].files,
                    //         id:result.data[i].id,
                    //         inForce:result.data[i].inForce,
                    //         operator:result.data[i].operator,
                    //         userId:result.data[i].userId
                    //     }
                    //     that.tableData.push(tableData);
                    // }
                })
                .catch((error) => {
                    console.log(error);
                });
        }
    }
};
</script>

<style>
</style>
