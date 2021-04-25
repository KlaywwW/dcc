<style>
.el-divider__text,
.el-link {
    font-size: 1.35rem;
}

.show {
    color: red;
}

/* .el-up */
</style>

<template>
<div class="container">
    <div>
        <el-select v-model="depValue" filterable clearable  placeholder="请选择">
    <el-option
      v-for="item in options"
      :key="item.id"
      :label="item.departmentName"
      :value="item.id">
    </el-option>
  </el-select>
        <el-button type="primary" @click="selectByDep">查询</el-button>
    </div>
    <div id="upload">
        <el-upload class="upload-demo" ref="upload" :action="uploadPath" :on-preview="handlePreview" :on-remove="handleRemove" :auto-upload="false" :file-list="fileList" :limit="1" name="file" :http-request="fileUpload" :headers="headers" :disabled="uploadStatus==1?false:true">
            <el-button slot="trigger" size="small" type="primary" @click="choiceFile">选取文件</el-button>
            <el-button style="margin-left: 0.5rem;" size="small" type="success" @click="submitUpload">{{upload}}<i class="el-icon-upload el-icon--right"></i></el-button>
        </el-upload>
        <!-- <a href="sdwx:// ">启动</a> -->
    </div>
    <el-divider content-position="left">{{ title }}文件列表</el-divider>
    <div>
        <el-table :data="tableData" style="width: 100%" :header-cell-style="tableHeaderColor">
            <el-table-column type="index" :index="indexMethod" label="No">
            </el-table-column>
            <el-table-column prop="isoNo" label="ISO编码">
            </el-table-column>
            <el-table-column prop="filesName" label="文件名">
            </el-table-column>
            <el-table-column prop="cdate" label="修改时间">
            </el-table-column>
            <el-table-column prop="cuser" label="修改人">
            </el-table-column>
            <el-table-column prop="versionNo" label="版本号">
            </el-table-column>
            <el-table-column prop="department.departmentName" label="部门">
            </el-table-column>
            <!-- <el-table-column prop="filesLevel" label="机密等级"></el-table-column> -->

            <el-table-column label="浏览" id="show">
                <el-table-column label="浏览">
                    <template slot-scope="scope">
                        <el-button type="primary" @click="docShow(scope.row)">
                            
                            {{look}}
                        </el-button>
                        <!-- <el-button type="primary" @click="docDownload(scope.row)">下载</el-button> -->
                    </template>
                </el-table-column>
                <el-table-column label="下载">
                    <template slot-scope="scope">
                    <el-button type="primary" @click="docDownload(scope.row)">{{download}}</el-button>
                    </template>
                </el-table-column>
                <el-table-column prop="" label="历史版本">
                    <template slot-scope="scope">
                    <el-button type="primary" @click="docHistory(scope.row)">历史</el-button>
                    </template>
                </el-table-column>
            </el-table-column>
            <el-table-column label="异动" id="update">
                <!-- <el-table-column label="认证状态">
                     <el-button type="primary"> -->认证成功
                    <!-- </el-button> 
                </el-table-column> -->
                <el-table-column label="更新版本">
                    <template slot-scope="scope" >
                        <el-upload class="upload-demo"  :file-list="fileUpdateList" :action="updatePath" :headers="headers" name="file" :http-request="updateUpload" :data="{fileId:scope.row.filesId,departmentId:scope.row.departmentId}" :disabled="updateStatus==1?false:true">
                            <el-button type="primary"  @click="docUpdate(scope.row)">{{update}}</el-button>
                        </el-upload>
                    </template>
                </el-table-column>
            </el-table-column>
            </el-table-column>
        </el-table>
    </div>

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
<el-dialog title="填写资料" :visible.sync="uploadDialog" width="30%">
        <div>
            <el-form ref="form" :model="uploadForm" label-width="80px">
                <el-form-item label="上传人">
                    <el-input v-model="uploadForm.uploadName"></el-input>
                </el-form-item>
                <el-form-item label="选择部门">
                    <el-select v-model="uploadForm.depUploadValue" filterable placeholder="请选择">
                        <el-option v-for="item in options" :key="item.id" :label="item.departmentName" :value="item.id"></el-option>
                     </el-select>
                </el-form-item>
            </el-form>
        </div>
    <span slot="footer" class="dialog-footer">
    <el-button @click="uploadCancel">取 消</el-button>
    <el-button type="primary" @click="uploadSubmit">提 交</el-button>
  </span>
</el-dialog>
</div>
</div>
</template>

<script>
import userListVue from '../user/userList.vue';
export default {
    name: '',

    data() {
        return {
            uploadForm: {
                uploadName: '',
                depUploadValue: ''
            },
            depValue: '', //选中部门的值
            dirId: '', //菜单id
            options: [],
            updateStatus: 0,
            uploadStatus: 0,
            dialogVisible: false,
            uploadDialog: false,
            userData: {},
            isoNo: '',
            title: '',
            docs2: '',
            headers: {
                'Access-Control-Allow-Origin': '*',
                'X-Requested-With': 'XMLHttpRequest'
                // 'Content-Type': 'application/json; charset=UTF-8'
            },
            tableData: [],
            fileList: [],
            fileUpdateList: [],
            path: '',
            uploadPath: '',
            updatePath: '',
            look: '预览',
            upload: '上传文件到服务器',
            download: '下载',
            update: '更新',
            form: {
                name: '',
                content: '',
                fileId: ''
            }
        };
    },
    created() {
        var that = this;
        this.look = '预览';
        this.upload = '上传文件到服务器';
        this.download = '下载';
        this.update = '更新';
        let userData = JSON.parse(sessionStorage.getItem('userData'));
        this.$axios.get('/api/user/refreshUser?userId=' + userData.user.id).then((res) => {
            sessionStorage.removeItem('userData');
            sessionStorage.setItem('userData', JSON.stringify(res.data.obj));
            this.userData = JSON.parse(sessionStorage.getItem('userData'));
        });
        let user = this.userData.user;
        this.$axios.get('api/file/getAllDep').then((res) => {
            that.options = res.data.data;
        });
    },
    mounted() {
        this.change();
        this.getdocList();
    },
    // 监听路由参数中数据变化
    computed: {
        doc() {
            // return this.$store.state.doc;
            return this.$route.query.param;
        }
    },
    watch: {
        doc() {
            this.change();
            this.getdocList();
        }
    },

    methods: {
        // 按部门查询文件
        selectByDep() {
            var that = this;
            this.$axios.get('api/file/show?departmentId=' + that.depValue + '&dirId=' + that.dirId).then((res) => {
                for (var i = 0; i < res.data.data.length; i++) {
                    res.data.data[i].cdate = that.dateFormat(res.data.data[i].cdate);
                }
                that.tableData = res.data.data;
            });
        },
        uploadSubmit() {
            let that = this;

            if (that.uploadForm.uploadName == '' || that.uploadForm.uploadName == null) {
                this.$message({
                    message: '请先填写上传人',
                    type: 'warning'
                });
                return true;
            }
            if (that.uploadForm.depUploadValue == '' || that.uploadForm.depUploadValue == null) {
                this.$message({
                    message: '请选择部门后上传',
                    type: 'warning'
                });
                return true;
            }
            that.$refs.upload.submit();
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
                    if (res.data.msg == 'ok') {
                        this.$message.success(res.data.obj);
                    } else {
                        this.$message.error(res.data.obj);
                    }
                    this.form.name = '';
                    this.dialogVisible = false;
                })
                .catch((err) => {});
        },
        cancel() {
            this.form.name = '';
            this.dialogVisible = false;
        },
        uploadCancel() {
            this.uploadDialog = false;
        },
        updateUpload(param, scope) {
            let that = this;
            let formData = new FormData();
            formData.append('file', param.file);
            formData.append('fileId', param.data.fileId);
            formData.append('userName', this.userData.user.account);
            formData.append('dirId', this.dirId);
            formData.append('departmentId', param.data.departmentId);
            formData.append('userId', this.userData.user.id);
            this.$axios.post('api/file/update', formData).then((res) => {
                this.form.name = '';
                this.$message({
                    message: res.data.msg,
                    type: 'success'
                });
                // console.log(res.data);
                that.getdocList();
                this.fileUpdateList = [];
            });
        },
        // 权限判断
        allot(operation, row) {
            let that = this;
            let auth = false;

            let role = this.userData.role;
            for (let j = 0; j < role.length; j++) {
                if (role[j].roleCode == operation) {
                    auth = true;
                    return auth;
                }
            }
            let roleFile = this.userData.roleFile;
            for (let i = 0; i < roleFile.length; i++) {
                if (roleFile[i].fileId == row.filesId && roleFile[i].operation == operation) {
                    auth = true;
                }
            }

            return auth;
        },
        //数据库中2.上传 3.下载 4.查看 5.更新
        docShow(row) {
            if (!this.allot('look', row)) {
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
            if (!this.allot('download', row)) {
                this.form.content = '下载';
                this.dialogVisible = true;
                this.form.fileId = row.filesId;
                // this.$message({
                //     message: '无权限操作',
                //     type: 'warning'
                // });
            } else {
                this.$axios
                    .get('api/file/downloadFile?fileId=' + row.filesId + '&userId=' + this.userData.user.id, {
                        responseType: 'blob'
                    })
                    .then((res) => {
                        this.form.name = '';
                        let fileName = row.filesName;
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
        docUpdate(row) {
            if (!this.allot('update', row)) {
                this.form.content = '更新';
                this.dialogVisible = true;
                this.form.fileId = row.filesId;
            } else {
                // console.log(true);
                this.updateStatus = 1;
            }
        },
        docHistory(row) {
            let original = row.originalFileId;
            if (original == null || original == '') {
                this.$message({
                    message: '该文件无历史版本',
                    type: 'warning'
                });
            } else {
                sessionStorage.setItem('original', JSON.stringify(row));
                this.$router.push('/docHistory');
            }
        },
        choiceFile() {
            let that = this;
            let count = 0;
            for (var i = 0; i < that.userData.role.length; i++) {
                if (that.userData.role[i].roleCode == 'upload') {
                    that.uploadStatus = 1;
                    count++;
                }
            }
            if (count <= 0) {
                this.$message({
                    message: '无权限操作',
                    type: 'warning'
                });
            }
        },
        // 下标增加
        indexMethod(index) {
            return index + 1;
        },
        // 自定义上传
        fileUpload(param) {
            let that = this;
            let formData = new FormData();
            formData.append('files', param.file);
            formData.append('userName', this.uploadForm.uploadName);
            formData.append('dirId', this.dirId);
            formData.append('departmentId', this.uploadForm.depUploadValue);
            formData.append('userId', this.userData.user.id);
            this.$axios.post('api/file/upload', formData).then((res) => {
                this.$message({
                    message: res.data.msg,
                    type: 'success'
                });
                that.uploadForm.depUploadValue = '';
                that.uploadForm.uploadName = '';
                that.uploadDialog = false;
                that.getdocList();
                that.uploadDialog = false;
                that.fileList = [];
            });
        },
        // 提交上传
        submitUpload() {
            let that = this;
            // this.isoNo = value;
            let count = 0;
            if (that.$refs.upload._data.uploadFiles.length === 0) {
                that.$message({
                    message: '选择文件后上传',
                    type: 'warning'
                });
                return true;
            }
            this.uploadDialog = true;
        },
        //  移除待上传的文件
        handleRemove(file, fileList) {
        },
        // 查询待上传的文件
        handlePreview(file) {
        },
        // 给表头设置颜色
        tableHeaderColor({ row, rowIndex, columnIndex }) {
            if (rowIndex === 0 && columnIndex === 7) {
                return 'background-color: #409EFF;text-align:center;font-size:16;color:black;';
            } else if (rowIndex === 0 && columnIndex === 8) {
                return 'background-color: #ffff66;text-align:center;font-size:16;color:black;';
            }
        },
        change() {
            // console.log(this.$route);
            let index = this.$route.query.param;
            // 品管作业指导书
            if (index == '4') {
                this.title = '品管作业指导书';
                // 请求路径
                this.dirId = 4;
            }
            // 流程作业指导书
            if (index == '1') {
                this.title = '流程作业指导书';
                this.dirId = 1;
            }
            // 工序标准作业票
            if (index == '2') {
                this.title = '工序标准作业票';
                this.dirId = 2;
            }
            // 设备操作规范
            if (index == '3') {
                this.title = '设备操作规范';
                this.dirId = 3;
            }
        },
        // 请求后台获取数据
        getdocList() {
            var that = this;
            this.$axios.get('api/file/showAllByDir?dirId=' + that.dirId).then((res) => {
                for (var i = 0; i < res.data.data.length; i++) {
                    res.data.data[i].cdate = that.dateFormat(res.data.data[i].cdate);
                }
                that.tableData = res.data.data;
            });
        },
        // 转换时间
        dateFormat: function (time) {
            var date = new Date(time);
            var year = date.getFullYear();
            var month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1;
            var day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
            var hours = date.getHours() < 10 ? '0' + date.getHours() : date.getHours();
            var minutes = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes();
            var seconds = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds();
            // 拼接
            return year + '-' + month + '-' + day;
            // + " " + hours + ":" + minutes + ":" + seconds
        }
    }
};
</script>
