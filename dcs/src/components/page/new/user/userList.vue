<template>
    <div class="container">
        <div>
            <el-button type="primary" @click="addUser">添加用户</el-button>
        </div>
        <el-table :data="tableData" border style="width: 100%">
            <el-table-column type="index" :index="indexMethod" label="序号"> </el-table-column>
            <el-table-column prop="account" label="用户名"> </el-table-column>
            <el-table-column label="权限">
                <template slot-scope="scope">
                    <div v-for="(item, i) in scope.row.userRoleList" :key="i">
                        <span>{{ item.roles.roleName }}</span>
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="username" label="姓名"> </el-table-column>
            <el-table-column prop="department.departmentName" label="部门"> </el-table-column>

            <el-table-column label="账号状态">
                <template slot-scope="scope">
                    <!-- 这里取到当前单元格 -->
                    <span v-if="scope.row.isStatus == 1">已禁用</span>
                    <span v-if="scope.row.isStatus == 0">可用</span>
                    <!-- scope.row 直接取到该单元格对象，就是数组里的元素对象，即是tableData[scope.$index] -->
                    <!-- .title 是对象里面的title属性的值 -->
                </template>
                <!-- <span v-if="this.tableData.isStatus==1">禁用</span> -->
            </el-table-column>
            <el-table-column fixed="right" label="操作">
                <template slot-scope="scope">
                    <el-button @click="handleClick(scope.row)" type="text" size="small">编辑</el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog :title="title" :visible.sync="dialogFormVisible" width="40%">
            <el-form ref="form" :model="form" label-width="80px">
                <el-form-item label="账号">
                    <el-input v-model="form.account" class="inputclass"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="form.password" class="inputclass" type="password"></el-input>
                </el-form-item>
                <el-form-item label="姓名">
                    <el-input v-model="form.username" class="inputclass"></el-input>
                </el-form-item>
                <!-- <el-form-item label="年龄">
                    <el-input v-model="form.age" class="inputclass"></el-input>
                </el-form-item> -->
                <el-form-item label="部门">
                    <el-select placeholder="请选择部门" v-model="form.dep">
                        <el-option v-for="dep in department" :key="dep.id" :label="dep.departmentName" :value="dep.id"></el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="账号状态:">
                    <el-switch v-model="form.delivery" active-text="禁用"></el-switch>
                </el-form-item>
                <!-- <el-form-item label="wxUid">
                    <el-input v-model="form.wxUid" class="inputclass"></el-input>
                </el-form-item> -->
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancel">取 消</el-button>
                <el-button type="primary" @click="operationUser()">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
export default {
    data() {
        return {
            tableData: [],
            title: '',
            index: 0,
            dialogFormVisible: false,
            form: {
                id: '',
                password: '',
                account: '',
                role: '',
                // roleId:'',
                username: '',
                age: '',
                delivery: false,
                dep: '',
                wxUid: ''
            },
            department: {},
            roles: {},
            files: []
        };
    },
    methods: {
        addUser() {
            this.title = '添加用户';
            this.index = 1;
            this.dialogFormVisible = true;
        },
        cancel() {
            this.dialogFormVisible = false;
            this.clearForm();
        },
        operationUser() {
            let index = this.index;
            console.log(this.form);
            if (index == 1) {
                this.$axios
                    .post('api/user/addUser', this.form)
                    .then((res) => {
                        this.$message.success(res.data);
                        this.clearForm();
                        this.initData();
                    })
                    .catch((res) => {});
            } else if (index == 2)
                this.$axios
                    .put('api/user/updateUser/' + this.form.id, this.form)
                    .then((success) => {
                        console.log(success);
                        if (success.statusText == 'OK') {
                            this.$message.success('修改成功');
                            this.clearForm();
                            this.initData();
                        }
                    })
                    .catch((error) => {
                        this.$message.error('修改失败!' + error.response.statusText);
                    });
            this.dialogFormVisible = false;
        },
        handleClick(row) {
            this.title = '修改用户';
            this.index = 2;
            const header = {
                headers: {
                    'Access-Control-Allow-Origin': '*',
                    'X-Requested-With': 'XMLHttpRequest',
                    'Content-Type': 'application/json; charset=UTF-8'
                }
            };

            console.log(row);
            var _this = this;
            this.form.id = row.id;
            this.form.account = row.account;
            this.form.password = row.password;
            this.form.username = row.username;
            this.form.age = row.age;
            this.form.dep = row.departmentId;
            this.form.wxUid = row.wxUid;
            if (row.isStatus == 1) {
                _this.form.delivery = true;
            } else {
                _this.form.delivery = false;
            }
            // for (var i = 0; i < row.userRoleList.length; i++) {
            //     this.form.roleGroup[i] = row.userRoleList[i].roleId;
            //     this.form.file[i]=row.userRoleList[i].fileId;
            // }
            console.log(this.form.roleGroup);
            // 获取角色信息
            this.$axios
                .get('api/user/roles', header)
                .then((result) => {
                    this.roles = result.data.obj;
                    console.log(this.roles);
                })
                .catch((error) => {
                    this.$message.error(error.response.data.message);
                });
            // 获取部门信息
            this.$axios
                .get('api/user/department', header)
                .then((result) => {
                    this.department = result.data.obj;
                })
                .catch((error) => {
                    this.$message.error(error.response.data.message);
                });
            // 获取所有文件
            // this.$axios
            //     .get('api/file/showFileList', header)
            //     .then((result) => {
            //         this.files = result.data.data;
            //     })
            //     .catch((error) => {
            //         this.$message.error(error.response.data.message);
            //     });
            this.dialogFormVisible = true;
        },
        indexMethod(index) {
            return index + 1;
        },
        clearForm() {
            this.form = {
                id: '',
                password: '',
                account: '',
                role: '',
                // roleId:'',
                username: '',
                age: '',
                delivery: false,
                dep: '',
                wxUid: ''
            };
            this.title = '';
            this.index = 0;
        },
        initData() {
            const header = {
                headers: {
                    'Access-Control-Allow-Origin': '*',
                    'X-Requested-With': 'XMLHttpRequest',
                    'Content-Type': 'application/json; charset=UTF-8'
                }
            };
            this.$axios
                .get('api/user/show', header)
                .then((result) => {
                    this.tableData = result.data.obj;
                    console.log(this.tableData);
                })
                .catch((error) => {
                    if (error.response.status == '403') {
                        this.$router.push('/login');
                    } else {
                        this.$message.error(error.response.data.message);
                    }
                });
            this.$axios
                .get('api/user/roles', header)
                .then((result) => {
                    this.roles = result.data.obj;
                    console.log(this.roles);
                })
                .catch((error) => {
                    this.$message.error(error.response.data.message);
                });
            // 获取部门信息
            this.$axios
                .get('api/user/department', header)
                .then((result) => {
                    this.department = result.data.obj;
                })
                .catch((error) => {
                    this.$message.error(error.response.data.message);
                });
        }
    },

    created() {
        this.initData();
    }
};
</script>

<style>
/* .inputclass{
		width: 30%;
	} */
</style>
