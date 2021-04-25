<template>
    <div>
        <div>
            <el-select v-model="user" placeholder="请选择用户" clearable>
                <el-option v-for="userItem in userOptions" :key="userItem.id" :label="userItem.account" :value="userItem.id"> </el-option>
            </el-select>
            <el-button type="primary" @click="select"> 查询 </el-button>
            <el-table :data="tableData" style="width: 100%">
                <el-table-column type="index" label="NO."> </el-table-column>
                <el-table-column prop="operationContent" label="操作内容" > </el-table-column>
                <el-table-column prop="operationDate" label="操作时间"> </el-table-column>
                <el-table-column prop="users.account" label="操作用户"> </el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            tableData: [],
            userOptions:[],
            user:''
        };
    },
    methods: {
        select(){
            let that=this;
            this.$axios.get('api/file/showLog?userId='+this.user).then(
                res=>{
                    that.tableData=res.data;
                }
            )
        }
    },
    created(){
        let that=this;
         this.$axios
            .get('api/user/show')
            .then((result) => {
                that.userOptions = result.data.obj;
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