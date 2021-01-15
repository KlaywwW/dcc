<template>
    <div>
        <el-row :gutter="20">
            <el-col :span="12">
                <el-card shadow="hover" class="mgb20" style="height: 514px">
                    <div class="user-info">
                        <img src="../../assets/img/img.jpg" class="user-avator" alt />
                        <div class="user-info-cont">
                            <div class="user-info-name">{{ account }}</div>
                            <div>{{ role }}</div>
                            <div>{{ name }}</div>
                        </div>
                    </div>
                    <div style="margin-top: 60px">
                        <div slot="header" class="clearfix">
                            <span>逾期待办事项</span>《{{ todoTodayCount }}》
                            <!-- <el-button style="float: right; padding: 3px 0" type="text">添加</el-button> -->
                        </div>
                        <el-table :data="todoTodayList" style="width: 100%" height="250">
                            <el-table-column width="40" type="index" label="No"></el-table-column>
                            <el-table-column prop="directory.dirName" label="四大项目"></el-table-column>
                            <el-table-column prop="content" label="认证内容"></el-table-column>
                            <el-table-column label="待处理事项">
                                <template slot-scope="scope">
                                    <div v-if="scope.row.planGather.planTime != null && scope.row.planGather.actualTime == null">
                                        收集资料中
                                    </div>
                                    <!-- <div v-else-if="scope.row.planGather.planTime != null && scope.row.planGather.actualTime != null && scope.row.planGather.gatherPlanTime == null">
                                        待计划送审
                                    </div> -->
                                    <div v-else-if="scope.row.planAuth.authPlanTime != null && scope.row.planAuth.authActualTime == null">
                                        待认证
                                    </div>
                                    <div v-else-if="scope.row.planAuth.authPlanTime != null && scope.row.planAuth.authActualTime != null">
                                        待稽核
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column label="计划时间">
                                <template slot-scope="scope">
                                    <div v-if="scope.row.planGather.planTime != null && scope.row.planGather.actualTime == null">
                                        {{ scope.row.planGather.planTime }}
                                    </div>
                                    <!-- <div v-else-if="scope.row.planGather.planTime != null && scope.row.planGather.actualTime != null && scope.row.planGather.gatherPlanTime == null">
                                        待计划送审
                                    </div> -->
                                    <div v-else-if="scope.row.planAuth.authPlanTime != null && scope.row.planAuth.authActualTime == null">
                                        {{ scope.row.planAuth.authPlanTime }}
                                    </div>
                                    <div v-else-if="scope.row.planAuth.authPlanTime != null && scope.row.planAuth.authActualTime != null">
                                        {{ scope.row.planCheck.checkPlanTime }}
                                    </div>
                                </template>
                            </el-table-column>
                        </el-table>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="12">
                <el-card shadow="hover" style="height: 514px">
                    <div slot="header" class="clearfix">
                        <span>所有待办事项</span>《{{ todoCount }}》
                        <!-- <el-button style="float: right; padding: 3px 0" type="text">添加</el-button> -->
                    </div>
                    <el-table :data="todoList" style="width: 100%" height="400">
                        <el-table-column width="40" type="index" label="No"></el-table-column>
                        <el-table-column prop="directory.dirName" label="四大项目"></el-table-column>
                        <el-table-column prop="content" label="认证内容"></el-table-column>
                        <el-table-column label="待处理事项">
                            <template slot-scope="scope">
                                <div v-if="scope.row.planGather.planTime != null && scope.row.planGather.actualTime == null">
                                    收集资料中
                                </div>
                                <div
                                    v-else-if="
                                        scope.row.planGather.planTime != null &&
                                        scope.row.planGather.actualTime != null &&
                                        scope.row.planGather.gatherPlanTime == null
                                    "
                                >
                                    待计划送审
                                </div>
                                <div v-else-if="scope.row.planAuth.authPlanTime != null && scope.row.planAuth.authActualTime == null">
                                    待认证
                                </div>
                                <div v-else-if="scope.row.planAuth.authPlanTime != null && scope.row.planAuth.authActualTime != null">
                                    待稽核
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column label="计划时间">
                            <template slot-scope="scope">
                                <div v-if="scope.row.planGather.planTime != null && scope.row.planGather.actualTime == null">
                                    {{ scope.row.planGather.planTime }}
                                </div>
                                <div
                                    v-else-if="
                                        scope.row.planGather.planTime != null &&
                                        scope.row.planGather.actualTime != null &&
                                        scope.row.planGather.gatherPlanTime == null
                                    "
                                >
                                    未计划
                                </div>
                                <div v-else-if="scope.row.planAuth.authPlanTime != null && scope.row.planAuth.authActualTime == null">
                                    {{ scope.row.planAuth.authPlanTime }}
                                </div>
                                <div v-else-if="scope.row.planAuth.authPlanTime != null && scope.row.planAuth.authActualTime != null">
                                    {{ scope.row.planCheck.checkPlanTime }}
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-card>
            </el-col>
        </el-row>
        <!-- <el-row :gutter="20">
            <el-col :span="12">
                <el-card shadow="hover">
                    <schart ref="bar" class="schart" canvasId="bar" :options="options"></schart>
                </el-card>
            </el-col>
            <el-col :span="12">
                <el-card shadow="hover">
                    <schart ref="line" class="schart" canvasId="line" :options="options2"></schart>
                </el-card>
            </el-col>
        </el-row> -->
    </div>
</template>

<script>
import Schart from 'vue-schart';
import bus from '../common/bus';
export default {
    name: 'dashboard',
    data() {
        return {
            // name: "",
            todoList: [],
            todoCount: 0,
            todoTodayList: [],
            todoTodayCount: 0,
            userData: [],
            data: [
                {
                    name: '2018/09/04',
                    value: 1083
                },
                {
                    name: '2018/09/05',
                    value: 941
                },
                {
                    name: '2018/09/06',
                    value: 1139
                },
                {
                    name: '2018/09/07',
                    value: 816
                },
                {
                    name: '2018/09/08',
                    value: 327
                },
                {
                    name: '2018/09/09',
                    value: 228
                },
                {
                    name: '2018/09/10',
                    value: 1065
                }
            ],
            options: {
                type: 'bar',
                title: {
                    text: '最近一周各品类销售图'
                },
                xRorate: 25,
                labels: ['周一', '周二', '周三', '周四', '周五'],
                datasets: [
                    {
                        label: '家电',
                        data: [234, 278, 270, 190, 230]
                    },
                    {
                        label: '百货',
                        data: [164, 178, 190, 135, 160]
                    },
                    {
                        label: '食品',
                        data: [144, 198, 150, 235, 120]
                    }
                ]
            },
            options2: {
                type: 'line',
                title: {
                    text: '最近几个月各品类销售趋势图'
                },
                labels: ['6月', '7月', '8月', '9月', '10月'],
                datasets: [
                    {
                        label: '家电',
                        data: [234, 278, 270, 190, 230]
                    },
                    {
                        label: '百货',
                        data: [164, 178, 150, 135, 160]
                    },
                    {
                        label: '食品',
                        data: [74, 118, 200, 235, 90]
                    }
                ]
            }
        };
    },
    components: {
        Schart
    },
    computed: {
        role() {
            let roleName = JSON.parse(sessionStorage.getItem('userData')).user.roleGroup.groupName;
            if (roleName == null) {
                roleName = '请登录';
            } 
            return roleName;
        },
        account() {
            let userData = sessionStorage.getItem('userData');
            if (userData == null) {
                return '请登录';
            } else {
                return JSON.parse(userData).user.account;
            }
        },
        name() {
            let userData = sessionStorage.getItem('userData');
            if (userData == null) {
                return '请登录';
            } else {
                return JSON.parse(userData).user.username;
            }
        }
    },

    activated() {
        // this.handleListener();
    },
    deactivated() {
        window.removeEventListener('resize', this.renderChart);
        bus.$off('collapse', this.handleBus);
    },
    created() {
        let that = this;
        const h = this.$createElement;
        this.userData = JSON.parse(sessionStorage.getItem('userData'));
        this.$axios.get('api/plan/getPlanByUser?userId=' + this.userData.user.id).then((res) => {
            if (res.data.code == 777) {
                this.$router.push('/login');
            }
            that.todoList = res.data.data;
            that.todoCount = res.data.count;
            console.log(res.data);
        });
        this.$axios.get('api/plan/nowPlan?userId=' + this.userData.user.id).then((res) => {
            that.todoTodayList = res.data.data;
            that.todoTodayCount = res.data.count;
            if (res.data.count > 0) {
                this.$notify({
                    // title: '今日待办事项',
                    dangerouslyUseHTMLString: true,
                    message: '逾期待办事项数量    ' + '<strong style="color:red;font-size:20px">' + res.data.count + '</strong>',
                    duration: 50000,
                    type: 'warning'
                });
            }
            console.log(res.data);
        });
    },
    methods: {
        changeDate() {
            const now = new Date().getTime();
            this.data.forEach((item, index) => {
                const date = new Date(now - (6 - index) * 86400000);
                item.name = `${date.getFullYear()}/${date.getMonth() + 1}/${date.getDate()}`;
            });
        }
    }
};
</script>


<style scoped>
.el-row {
    margin-bottom: 20px;
}

.grid-content {
    display: flex;
    align-items: center;
    height: 100px;
}

.grid-cont-right {
    flex: 1;
    text-align: center;
    font-size: 14px;
    color: #999;
}

.grid-num {
    font-size: 30px;
    font-weight: bold;
}

.grid-con-icon {
    font-size: 50px;
    width: 100px;
    height: 100px;
    text-align: center;
    line-height: 100px;
    color: #fff;
}

.grid-con-1 .grid-con-icon {
    background: rgb(45, 140, 240);
}

.grid-con-1 .grid-num {
    color: rgb(45, 140, 240);
}

.grid-con-2 .grid-con-icon {
    background: rgb(100, 213, 114);
}

.grid-con-2 .grid-num {
    color: rgb(45, 140, 240);
}

.grid-con-3 .grid-con-icon {
    background: rgb(242, 94, 67);
}

.grid-con-3 .grid-num {
    color: rgb(242, 94, 67);
}

.user-info {
    display: flex;
    align-items: center;
    padding-bottom: 20px;
    border-bottom: 2px solid #ccc;
    margin-bottom: 20px;
}

.user-avator {
    width: 120px;
    height: 120px;
    border-radius: 50%;
}

.user-info-cont {
    padding-left: 50px;
    flex: 1;
    font-size: 14px;
    color: #999;
}

.user-info-cont div:first-child {
    font-size: 30px;
    color: #222;
}

.user-info-list {
    font-size: 14px;
    color: #999;
    line-height: 25px;
}

.user-info-list span {
    margin-left: 70px;
}

.mgb20 {
    margin-bottom: 20px;
}

.todo-item {
    font-size: 14px;
}

.todo-item-del {
    text-decoration: line-through;
    color: #999;
}

.schart {
    width: 100%;
    height: 300px;
}
</style>
