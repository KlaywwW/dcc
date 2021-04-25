<template>
    <div class="container">
        <el-row type="flex" class="row-bg" justify="center">
            <el-col :span="18" style="width: 40%">
                <div>
                    <h4 style="text-align: center; margin-bottom: 10px">个人资料</h4>
                    <el-form ref="form" :model="user" label-width="80px">
                        <el-form-item label="账户名称">
                            <el-input v-model="user.account"></el-input>
                        </el-form-item>
                        <el-form-item label="密码">
                            <el-input v-model="user.password" type="password"></el-input>
                        </el-form-item>
                        <el-form-item label="部门">
                            <el-select placeholder="请选择部门" v-model="user.departmentId">
                                <el-option v-for="dep in department" :key="dep.id" :label="dep.departmentName" :value="dep.id"></el-option>
                            </el-select>
                        </el-form-item>

                        <el-form-item>
                            <el-button type="primary" @click="onSubmit">立即更新</el-button>
                        </el-form-item>
                    </el-form>
                </div>
            </el-col>
        </el-row>
        <div>
            <div>
                <el-select v-model="status" placeholder="请选择审核状态" clearable>
                    <el-option label="待审核" value="1"></el-option>
                    <el-option label="已通过" value="2"></el-option>
                    <el-option label="未通过" value="3"></el-option>
                </el-select>
                <el-button type="primary" @click="select"> 查询 </el-button>
            </div>
            <div>
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
    </div>
</template>

<script>
export default {
    name: '',

    data() {
        return {
            user: null,
            userData: [],
            department: [],
            status: '',
            tableData: [],
        };
    },
    created() {
        this.userData = JSON.parse(sessionStorage.getItem('userData'));
        this.user = this.userData.user;
        this.$axios
            .get('api/user/department')
            .then((result) => {
                this.department = result.data.obj;
            })
            .catch((error) => {
                this.$message.error(error.response.data.message);
            });
    },

    methods: {
        select() {
            var that = this;
            if (that.status != '' && that.status != null) {
                this.$axios
                    .get('api/record/getRecordByStatus?status=' + that.status + '&userId=' + that.userData.user.id)
                    .then((result) => {
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
        onSubmit() {
            let that = this;
            this.$axios.post('/api/user/userCenterUpdate', that.user).then((res) => {
                if (res.data.msg == 'ok') {
                    this.$message.success(res.data.obj);
                    that.initUser(that.user.id);
                } else {
                    this.$message.error(res.data.obj);
                }
            });
        },
        initUser(uid) {
            this.$axios.get('/api/user/refreshUser?userId=' + uid).then((res) => {
                this.user = res.data.obj.user;
                sessionStorage.removeItem('userData');
                sessionStorage.setItem('userData', JSON.stringify(res.data.obj));
            });
        }
    }
};
</script>

<style scoped>
</style>
