<template>
    <div class="container">
        <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="用户组管理" name="first">
                <div>
                    <div>
                        <el-row :gutter="10">
                            <el-col :xs="24" :sm="24" :md="24" :lg="12" :xl="12">
                                <div style="margin-bottom: 10px; margin-top: 10px">
                                    <label style="margin-right: 10px">用户组</label>
                                    <el-button type="primary" @click="operationGroup(null, 1)">添加用户组</el-button>
                                </div>
                                <el-table
                                    :data="groupData"
                                    style="width: 100%"
                                    border
                                    @current-change="handleCurrentChange"
                                    highlight-current-row
                                >
                                    <el-table-column type="index" label="No." width="60"> </el-table-column>
                                    <el-table-column prop="groupName" label="用户组"> </el-table-column>
                                    <el-table-column label="操作">
                                        <template slot-scope="scope">
                                            <el-button size="mini" @click="operationGroup(scope.row, 2)">编辑</el-button>
                                        </template>
                                    </el-table-column>
                                </el-table>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="24" :lg="12" :xl="12" v-if="show">
                                <div style="margin-bottom: 10px; margin-top: 10px">
                                    <label style="margin-right: 10px">权限</label>
                                    <el-button type="primary" @click="addRole()">添加权限</el-button>
                                </div>
                                <el-table :data="groupRoleData" style="width: 100%" border>
                                    <el-table-column type="index" label="No." width="60"></el-table-column>
                                    <el-table-column prop="roleName" label="权限"></el-table-column>
                                    <el-table-column prop="roleCode" label="roleCode"></el-table-column>
                                    <el-table-column label="操作">
                                        <template slot-scope="scope">
                                            <el-button size="mini" @click="delRole(scope.row)">删除</el-button>
                                        </template>
                                    </el-table-column>
                                </el-table>
                            </el-col>
                        </el-row>
                        <el-dialog :title="formGroupTitle" :visible.sync="operaGroupShow" width="400px">
                            <el-form :model="roleGroup" label-width="80px">
                                <el-form-item label="用户组">
                                    <el-input v-model="roleGroup.groupName"></el-input>
                                </el-form-item>
                            </el-form>
                            <div slot="footer" class="dialog-footer">
                                <el-button @click="groupCancel()">取 消</el-button>
                                <el-button type="primary" @click="groupSure()">确 定</el-button>
                            </div>
                        </el-dialog>

                        <el-dialog title="添加权限" :visible.sync="operaRoleShow" width="400px">
                            <el-form label-width="80px">
                                <el-form-item label="权限">
                                    <el-select v-model="roleSelectVal" filterable clearable placeholder="请选择">
                                        <el-option v-for="item in allRoles" :key="item.roleId" :label="item.roleName" :value="item.roleId">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                            </el-form>
                            <div slot="footer" class="dialog-footer">
                                <el-button
                                    @click="
                                        operaRoleShow = false;
                                        roleSelectVal = null;
                                    "
                                    >取 消</el-button
                                >
                                <el-button type="primary" @click="roleSure()">确 定</el-button>
                            </div>
                        </el-dialog>
                    </div>
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
    </div>
</template>


<script>
export default {
    data() {
        return {
            activeName: 'first',
            groupData: [],
            groupRoleData: [],
            show: false,
            operaGroupShow: false,
            formGroupTitle: '',
            roleGroup: {
                groupId: '',
                groupName: '',
                index: ''
            },
            operaRoleShow: false,
            allRoles: [],
            roleSelectVal: '',
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
        // 标签页切换
        handleClick(tab, event) {
            if (tab.name == 'first') {
                this.initRoleGroup();
            } else if (tab.name == 'second') {
                this.initUserData();
            }
        },
        // 用户组表格选择事件
        handleCurrentChange(val) {
            let that = this;
            console.log(val);
            this.show = true;
            if (val != null) {
                that.getRoleByGroup(val.groupId);
                that.roleGroup.groupId = val.groupId;
                that.roleGroup.groupName = val.groupName;
            }
        },
        // 添加或修改用户组
        operationGroup(row, index) {
            this.roleGroup.index = index;
            this.operaGroupShow = true;
            let that = this;
            if (index == 1) {
                that.formGroupTitle = '添加用户组';
            } else if (index == 2) {
                that.roleGroup.groupId = row.groupId;
                that.roleGroup.groupName = row.groupName;
                that.formGroupTitle = '修改用户组';
            }
        },
        groupCancel() {
            this.operaGroupShow = false;
            this.clear();
        },
        groupSure() {
            let index = this.roleGroup.index;
            let that = this;
            if (index == 1) {
                that.$axios.post('api/role/addRoleGroup', that.roleGroup).then((res) => {
                    that.$message(res.data.msg);
                    that.initRoleGroup();
                    that.operaGroupShow = false;
                });
            } else if (index == 2) {
                that.$axios.post('api/role/updateGroupName', that.roleGroup).then((res) => {
                    that.$message(res.data.msg);
                    that.initRoleGroup();
                    that.operaGroupShow = false;
                });
            }
        },
        //    添加权限
        addRole() {
            let roleGroup = this.roleGroup;
            if (roleGroup != null) {
                this.operaRoleShow = true;
            } else {
                this.$message({
                    message: '请先选择一个用户组',
                    type: 'warning'
                });
            }
        },
        roleSure() {
            let that = this;
            let form = {
                roleId: this.roleSelectVal,
                groupId: this.roleGroup.groupId
            };
            this.$axios.post('api/role/addRolesToGroup', form).then((res) => {
                this.$message({
                    message: res.data.msg,
                    type: 'success'
                });
                that.getRoleByGroup(form.groupId);
                that.operaRoleShow = false;
                that.roleSelectVal = null;
            });
        },
        delRole(row) {
            let that = this;
            console.log(row);
            this.$axios.get('api/role/delRole?groupId=' + row.userRoleGroup.groupId + '&roleId=' + row.roleId).then((res) => {
                this.$message({
                    message: res.data.msg,
                    type: 'success'
                });
                that.getRoleByGroup(row.userRoleGroup.groupId);
            });
        },
        // 初始化用户组数据
        initRoleGroup() {
            let that = this;
            // 查询权限组
            this.$axios
                .get('/api/role/getRoleGroup')
                .then((res) => {
                    that.groupData = res.data.obj;
                })
                .catch((res) => {
                    this.$message({
                        message: '连接错误',
                        type: 'warning'
                    });
                });
        },
        // 根据用户组获取权限
        getRoleByGroup(groupId) {
            let that = this;
            this.$axios.get('/api/role/getRoleByGroup?groupId=' + groupId).then((res) => {
                that.groupRoleData = res.data.obj;
            });
        },
        // 用户表格选择事件
        handleCurrentChangeUser(val) {
            let that = this;
            this.userShow = true;
            console.log(val);
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
            console.log(row);
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
        // 初始化用户数据
        initUserData() {
            this.$axios.get('api/user/show').then((res) => {
                this.userData = res.data.obj;
            });
        },
        clear() {
            this.roleGroup = {
                groupId: '',
                groupName: '',
                index: ''
            };
        }
    },
    created() {
        this.initRoleGroup();
        let that = this;
        this.$axios
            .get('/api/role/getAllRoles')
            .then((res) => {
                that.allRoles = res.data.obj;
            })
            .catch((res) => {
                this.$message({
                    message: '连接错误',
                    type: 'warning'
                });
            });
        this.$axios.get('/api/user/getAllMenu').then((res) => {
            that.allMenus = res.data.obj;
        });
    }
};
</script>

<style>
</style>
