<template>
    <div class="container">
        <el-tabs v-model="activeName" @tab-click="handleTabClick">
            <el-tab-pane label="用户管理" name="first">
                <div>
                    <div>
                        <div>
                            <el-button type="primary" @click="addUser">添加用户</el-button>
                        </div>
                        <el-table :data="tableData" border style="width: 100%">
                            <el-table-column type="index" :index="indexMethod" label="序号"> </el-table-column>
                            <el-table-column prop="account" label="用户名"> </el-table-column>
                            <el-table-column prop="roleGroup.groupName" label="用户组"> </el-table-column>
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
                    </div>
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
                            <el-form-item label="部门">
                                <el-select placeholder="请选择部门" v-model="form.dep">
                                    <el-option
                                        v-for="dep in department"
                                        :key="dep.id"
                                        :label="dep.departmentName"
                                        :value="dep.id"
                                    ></el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="用户组">
                                <el-select placeholder="请选择用户组" v-model="form.roleGroupId">
                                    <el-option
                                        v-for="group in roleGroup"
                                        :key="group.groupId"
                                        :label="group.groupName"
                                        :value="group.groupId"
                                    ></el-option>
                                </el-select>
                            </el-form-item>

                            <el-form-item label="账号状态:">
                                <el-switch v-model="form.delivery" active-text="禁用"></el-switch>
                            </el-form-item>
                        </el-form>
                        <div slot="footer" class="dialog-footer">
                            <el-button @click="cancel">取 消</el-button>
                            <el-button type="primary" @click="operationUser()">确 定</el-button>
                        </div>
                    </el-dialog>
                </div>
            </el-tab-pane>
            <el-tab-pane label="菜单管理" name="second">
                <div>
                    <div>
                        <el-row :gutter="10">
                            <el-col :xs="24" :sm="24" :md="24" :lg="12" :xl="12">
                                <div style="margin-bottom: 10px; margin-top: 10px">
                                    <label style="margin-right: 10px">用户列表</label>
                                </div>
                                <el-table
                                    :data="userData"
                                    style="width: 100%"
                                    border
                                    @current-change="handleCurrentChangeUser"
                                    highlight-current-row
                                >
                                    <el-table-column type="index" label="No." width="60"> </el-table-column>
                                    <el-table-column prop="account" label="账号"> </el-table-column>
                                    <el-table-column prop="roleGroup.groupName" label="用户组"></el-table-column>
                                    <el-table-column prop="username" label="用户名"></el-table-column>
                                </el-table>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="24" :lg="12" :xl="12" v-if="userShow">
                                <div style="margin-bottom: 10px; margin-top: 10px">
                                    <label style="margin-right: 10px">菜单</label>
                                    <el-button type="primary" @click="addMenu()">添加菜单</el-button>
                                </div>
                                <el-table :data="menuData" style="width: 100%" border>
                                    <el-table-column type="index" label="No." width="60"></el-table-column>
                                    <el-table-column prop="menu.menuName" label="菜单名字"></el-table-column>
                                    <el-table-column prop="menu.menuPath" label="菜单路径"></el-table-column>
                                    <el-table-column label="操作">
                                        <template slot-scope="scope">
                                            <el-button size="mini" @click="delMenu(scope.row)">删除</el-button>
                                        </template>
                                    </el-table-column>
                                </el-table>
                            </el-col>
                        </el-row>
                        <el-dialog title="为用户添加菜单" :visible.sync="operaMenuShow" width="400px">
                            <el-form label-width="80px">
                                <el-form-item label="菜单">
                                    <el-select v-model="menuSelectVal" filterable clearable placeholder="请选择">
                                        <el-option v-for="item in allMenus" :key="item.id" :label="item.menuName" :value="item.id">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                            </el-form>
                            <div slot="footer" class="dialog-footer">
                                <el-button
                                    @click="
                                        operaMenuShow = false;
                                        menuSelectVal = null;
                                    "
                                    >取 消</el-button
                                >
                                <el-button type="primary" @click="menuSure()">确 定</el-button>
                            </div>
                        </el-dialog>
                    </div>
                </div>
            </el-tab-pane>
        </el-tabs>
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
                roleGroupId: ''
            },
            activeName: 'first',
            department: [],
            directory: [],
            roles: {},
            files: [],
            roleGroup: [],
            userData: [],
            userShow: false,
            menuData: [],
            operaMenuShow: false,
            menuSelectVal: '',
            allMenus: [],
            userMenu: null
        };
    },
    methods: {
        handleTabClick(tab) {
            if (tab.name == 'first') {
                this.initData();
            } else if (tab.name == 'second') {
                this.initUserData();
            }
        },
        // 用户表格选择事件
        handleCurrentChangeUser(val) {
            let that = this;
            this.userShow = true;
            if (val != null) {
                this.getMenuById(val.id);
                that.userMenu = val;
            }
        },
        addMenu() {
            let userMenu = this.userMenu;
            if (userMenu != null) {
                this.operaMenuShow = true;
            } else {
                this.$message({
                    message: '请先选择一个用户',
                    type: 'warning'
                });
            }
        },
        delMenu(row) {
            let that = this;
            this.$axios.get('api/user/delUserMenu?mid=' + row.mid + '&uid=' + row.uid).then((res) => {
                this.$message({
                    message: res.data.msg,
                    type: 'success'
                });
                that.getMenuById(row.uid);
            });
        },
        getMenuById(uid) {
            let that = this;
            this.$axios.get('/api/user/getUserMenuByUid?uid=' + uid).then((res) => {
                that.menuData = res.data.obj;
            });
        },
        menuSure() {
            let that = this;
            let form = {
                mid: this.menuSelectVal,
                uid: this.userMenu.id
            };
            this.$axios.post('api/user/addUserMenu', form).then((res) => {
                this.$message({
                    message: res.data.msg,
                    type: 'success'
                });
                that.getMenuById(that.userMenu.id);
                that.menuSelectVal = '';
                that.operaMenuShow = false;
            });
        },

        addUser() {
            this.title = '添加用户';
            this.index = 1;
            this.dialogFormVisible = true;
        },
        cancel() {
            this.dialogFormVisible = false;
            this.clearForm();
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
            var _this = this;
            this.form.id = row.id;
            this.form.account = row.account;
            this.form.password = row.password;
            this.form.username = row.username;
            this.form.age = row.age;
            this.form.dep = row.departmentId;
            this.form.roleGroupId = row.roleGroupId;
            if (row.isStatus == 1) {
                _this.form.delivery = true;
            } else {
                _this.form.delivery = false;
            }
            this.dialogFormVisible = true;
        },
        operationUser() {
            let index = this.index; //index == 1 添加用户 index == 2 修改用户
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
                    .post('api/user/updateUser/' + this.form.id, this.form)
                    .then((success) => {
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
                roleGroupId: ''
            };
            this.title = '';
            this.index = 0;
        },
        initUserData() {
            this.$axios.get('api/user/show').then((res) => {
                this.userData = res.data.obj;
            });
        },
        // iAcrYC0
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
                })
                .catch((error) => {
                    if (error.response.status == '403') {
                        this.$router.push('/login');
                    } else {
                        this.$message.error(error.response.data.message);
                    }
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
            this.$axios
                .get('api/plan/getDir', header)
                .then((res) => {
                    this.directory = res.data;
                })
                .catch((error) => {
                    this.$message.error(error);
                });
            let that = this;
            // 查询权限组
            this.$axios
                .get('/api/role/getRoleGroup')
                .then((res) => {
                    this.roleGroup = res.data.obj;
                })
                .catch((res) => {
                    this.$message({
                        message: '连接错误',
                        type: 'warning'
                    });
                });
        }
    },

    created() {
        this.initData();
        this.$axios.get('/api/user/getAllMenu').then((res) => {
            this.allMenus = res.data.obj;
        });
    }
};
</script>

<style>
/* .inputclass{
		width: 30%;
	} */
</style>
