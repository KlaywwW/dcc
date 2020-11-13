<template>
    <div>
        <el-table :data="tableData" :default-sort="{ prop: 'versionNo', order: 'descending' }" style="width: 100%">
            <el-table-column type="index" label="No"> </el-table-column>
            <el-table-column prop="isoNo" label="ISO编码"> </el-table-column>
            <el-table-column prop="filesName" label="文件名"> </el-table-column>
            <el-table-column prop="cdate" label="修改时间"> </el-table-column>
            <el-table-column prop="cuser" label="修改人"> </el-table-column>
            <el-table-column prop="versionNo" sortable label="版本号"> </el-table-column>
            <el-table-column label="浏览" id="show">
                <el-table-column label="浏览">
                    <template slot-scope="scope">
                        <el-button type="primary" @click="docShow(scope.row)"> 浏览 </el-button>
                    </template>
                </el-table-column>
                <el-table-column label="下载">
                    <template slot-scope="scope">
                        <el-button type="primary" @click="docDownload(scope.row)">下载</el-button>
                    </template>
                </el-table-column>
            </el-table-column>
        </el-table>
        <div>
            <el-dialog title="无权限操作请先申请" :visible.sync="dialogVisible" width="30%">
                <div>
                    <el-form ref="form" :model="form" label-width="80px">
                        <el-form-item label="申请人">
                            <el-input v-model="form.name"></el-input>
                        </el-form-item>
                        <el-form-item label="申请事项">
                            <el-input v-model="form.content" readonly></el-input>
                        </el-form-item>
                    </el-form>
                </div>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="cancel">取 消</el-button>
                    <el-button type="primary" @click="submit">提 交</el-button>
                </span>
            </el-dialog>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            tableData: [],
            dialogVisible: false,
            form: {
                name: '',
                content: '',
                fileId: ''
            }
        };
    },
    created() {
        let that = this;
        let original = JSON.parse(sessionStorage.getItem('original'));
        console.log(original);
        this.$axios.get('api/file/getFilesVersion?original=' + original.originalFileId).then((res) => {
            that.tableData = res.data.data;
        });

        this.userData = JSON.parse(sessionStorage.getItem('userData'));
        let user = this.userData.user;
        let userId = this.userData.user.id;
        let roles;
        this.$axios.get('api/role/getFileAuth?userId=' + userId).then((res) => {
            roles = res.data;
            this.userData = {
                user: user,
                role: roles
            };
            sessionStorage.removeItem('userData');
            sessionStorage.setItem('userData', JSON.stringify(this.userData));
        });
    },
    methods: {
        allot(roleId, row) {
            var that = this;
            console.log(row.filesId);
            console.log(row);
            console.log(that.userData);
            let auth = false;
            for (var i = 0; i < that.userData.role.length; i++) {
                if (that.userData.role[i].roleId == 1) {
                    auth = true;
                    break;
                } else {
                    if (that.userData.role[i].roleId == roleId && that.userData.role[i].userRoleFileList.length > 0) {
                        for (var j = 0; j < that.userData.role[i].userRoleFileList.length; j++) {
                            if (that.userData.role[i].userRoleFileList[j].fileId == row.filesId) {
                                auth = true;
                            }
                        }
                    }
                }
            }
            return auth;
        },
        docShow(row) {
            if (!this.allot(4, row)) {
                this.form.content = '浏览';
                this.dialogVisible = true;
                this.form.fileId = row.filesId;
            } else {
                let suffix = '.pdf';
                let fileSuffix = row.filesName.substring(row.filesName.lastIndexOf('.'));
                if (suffix == fileSuffix) {
                    sessionStorage.setItem('pdf', JSON.stringify(row));

                    this.$axios.get('api/file/showPdfRecord?fileId=' + row.filesId + '&userId=' + this.userData.user.id).then((res) => {
                        this.$router.push('/showPdf');
                    });
                } else {
                    this.$message({
                        message: '不是pdf文件无法预览',
                        type: 'warning'
                    });
                }
            }
        },
        docDownload(row) {
            console.log(row);
            if (!this.allot(3, row)) {
                this.form.content = '下载';
                this.dialogVisible = true;
                this.form.fileId = row.filesId;
                // this.$message({
                //     message: '无权限操作',
                //     type: 'warning'
                // });
            } else {
                this.$axios
                    .get('api/file/downloadFile?fileId=' + row.filesId + '&versionNo=' + row.versionNo, {
                        responseType: 'blob'
                    })
                    .then((res) => {
                        this.form.name = '';
                        let fileName = row.filesName;
                        console.log(fileName);
                        let blob = new Blob([res.data], {
                            type: 'application/octet-stream'
                        });
                        if (window.navigator.msSaveOrOpenBlob) {
                            navigator.msSaveBlob(blob, fileName);
                        } else {
                            let elink = document.createElement('a');
                            elink.download = fileName;
                            elink.style.display = 'none';
                            elink.href = URL.createObjectURL(blob);
                            document.body.appendChild(elink);
                            elink.click();
                            document.body.removeChild(elink);
                        }
                    })
                    .catch((res) => {});
            }
        },
        submit() {
            let form = {
                name: this.form.name,
                content: this.form.content,
                userId: this.userData.user.id,
                fileId: this.form.fileId
            };
            if (form.name == '' || form.name == null) {
                this.$message({
                    message: '请先填写申请人',
                    type: 'warning'
                });
                return true;
            }
            this.$axios
                .post('api/record/add', form)
                .then((res) => {
                    this.form.name = '';
                    this.$message({
                        message: res.data,
                        type: 'success'
                    });
                    this.dialogVisible = false;
                })
                .catch((err) => {});
        },
        cancel() {
            this.form.name = '';
            this.dialogVisible = false;
        }
    }
};
</script>

<style>
</style>