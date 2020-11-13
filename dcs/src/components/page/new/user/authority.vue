<template>
    <div>
        <div>
            <span>选择用户</span>
            <el-select v-model="user" placeholder="请选择用户" clearable>
                <el-option v-for="userItem in userOptions" :key="userItem.id" :label="userItem.account" :value="userItem.id"> </el-option>
            </el-select>
            <el-button type="primary" @click="select"> 查询 </el-button>
            <!-- <span>选择权限</span>
            <el-select v-model="auth" placeholder="请选择权限" clearable>
                <el-option v-for="authItem in authOptions" :key="authItem.roleId" :label="authItem.roleName" :value="authItem.roleId">
                </el-option>
            </el-select>
            <span>选择文件</span>
            <el-select v-model="file" placeholder="请选择文件" clearable>
                <el-option v-for="fileItem in fileOptions" :key="fileItem.filesId" :label="fileItem.filesName" :value="fileItem.filesId">
                </el-option>
            </el-select>
            <el-button type="primary" @click="add"> 添加 </el-button> -->
        </div>
        <div>
            <el-table :data="tableData" border style="width: 100%">
                <el-table-column type="index" label="序号"></el-table-column>

                <el-table-column label="权限" prop="roles.roleName"></el-table-column>
                <el-table-column label="文件">
                    <template slot-scope="props">
                        <el-row v-for="(item, i) in props.row.userRoleFileList" :key="i" style="margin-bottom: 5px">
                            <el-col :span="12">
                                {{ item.files.filesName }}
                            </el-col>
                            <!-- <el-col :span="12">
                                <el-button type="primary">修改</el-button>
                                <el-button type="warning">删除</el-button>
                            </el-col> -->
                        </el-row>
                    </template>
                </el-table-column>
                <!-- <el-table-column type="expand">
                    <template slot-scope="props">
                        <el-form
                            label-position="left"
                            inline
                            class="demo-table-expand"
                            v-for="(item, i) in props.row.userRoleFileList"
                            :key="i"
                        >
                            <el-form-item label="文件名称">
                                <span>{{ item.files.filesName }}</span>
                            </el-form-item>
                        </el-form>
                    </template>
                </el-table-column> -->
            </el-table>
        </div>
    </div>
</template>


<script>
export default {
    data() {
        return {
            tableData: [],
            userOptions: [],
            authOptions: [],
            fileOptions: [],
            user: '',
            file: '',
            auth: '',
            dayNum: 0
        };
    },
    methods: {
        add() {},
        select() {
            var that = this;
            // 获取用户信息
            this.$axios
                .get('api/role/getFileAuth?userId=' + that.user)
                .then((result) => {
                    console.log(result.data);
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
    created() {
        var that = this;
        // 获取用户信息
        this.$axios
            .get('api/user/show')
            .then((result) => {
                console.log(result.data.obj);
                that.userOptions = result.data.obj;
            })
            .catch((error) => {
                if (error.response.status == '403') {
                    this.$router.push('/login');
                } else {
                    this.$message.error(error.response.data.message);
                }
            });
        // 文件列表
        this.$axios
            .get('api/file/showFileList')
            .then((result) => {
                that.fileOptions = result.data.data;
                console.log(that.fileOptions);
            })
            .catch((error) => {
                if (error.response.status == '403') {
                    this.$router.push('/login');
                } else {
                    this.$message.error(error.response.data.message);
                }
                // console.log(error.response.data.message);
            });
        this.$axios
            .get('api/user/roles')
            .then((result) => {
                that.authOptions = result.data.obj;
            })
            .catch((error) => {
                if (error.response.status == '403') {
                    this.$router.push('/login');
                } else {
                    this.$message.error(error.response.data.message);
                }
            });
    }
};
</script>

<style>
</style>
