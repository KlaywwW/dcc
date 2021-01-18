l<template>
    <div>
        <div class="container">
            <div style="margin-bottom: 10px">
                <el-row :gutter="12">
                    <el-col :span="4">
                        <el-card shadow="hover">
                            <div>
                                <div class="num">{{ numFirst }}</div>
                                <div class="title">进行中</div>
                            </div>
                        </el-card>
                    </el-col>

                    <el-col :span="4">
                        <el-card shadow="hover">
                            <div>
                                <div class="num">{{ numSecond }}</div>
                                <div class="title">流程作业指导书</div>
                            </div>
                        </el-card>
                    </el-col>
                    <el-col :span="4">
                        <el-card shadow="hover">
                            <div>
                                <div class="num">{{ numThird }}</div>
                                <div class="title">工序标准作业票</div>
                            </div>
                        </el-card>
                    </el-col>
                    <el-col :span="4">
                        <el-card shadow="hover">
                            <div>
                                <div class="num">{{ numFourth }}</div>
                                <div class="title">设备操作规范</div>
                            </div>
                        </el-card>
                    </el-col>
                    <el-col :span="4">
                        <el-card shadow="hover">
                            <div>
                                <div class="num">{{ numFifth }}</div>
                                <div class="title">品管作业指导书</div>
                            </div>
                        </el-card>
                    </el-col>
                    <el-col :span="4">
                        <el-card shadow="hover">
                            <div>
                                <div class="num">{{ numSixth }}</div>
                                <div class="title">已完成</div>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>
            </div>
            <el-tabs v-model="activeName" type="border-card" @tab-click="handleClick">
                <el-tab-pane label="首页" name="first">
                    <div>
                        <el-button type="primary" @click="addPlan">添加计划</el-button>
                    </div>
                    <div>
                        <el-divider content-position="left"></el-divider>
                        <div id="main">
                            <el-table
                                :data="tableData"
                                style="width: 100%"
                                border
                                :cell-style="cellStyle"
                                :header-cell-style="tableHeaderColor"
                            >
                                <!-- 合并单元格 :span-method="objectSpanMethod" -->
                                <el-table-column prop="dirName" label="四大标准" width="120" fixed></el-table-column>
                                <el-table-column type="index" label="No" width="40"></el-table-column>
                                <el-table-column prop="content" label="认证项目" fixed></el-table-column>
                                <el-table-column label="收集资料">
                                    <el-table-column prop="depPrincipal" label="被收集单位"></el-table-column>
                                    <el-table-column prop="planGather.planTime" label="计划完成时间"></el-table-column>
                                    <el-table-column label="实际完成时间" width="100">
                                        <template slot-scope="scope">
                                            <div v-if="scope.row.planGather != null">
                                                {{ scope.row.planGather.actualTime }}
                                                <div
                                                    v-if="scope.row.planGather.actualTime == null && scope.row.planGather.planTime != null"
                                                >
                                                    <el-button type="primary" plain size="mini" @click="verify(scope.row, 1)"
                                                        >确认完成</el-button
                                                    >
                                                </div>
                                            </div>
                                        </template>
                                    </el-table-column>

                                    <el-table-column prop="users.username" label="收集资料组长"></el-table-column>
                                    <el-table-column prop="planGather.gatherPlanTime" label="计划送审时间"></el-table-column>
                                </el-table-column>
                                <el-table-column label="审核认证">
                                    <el-table-column prop="planAuth.authPlanTime" label="计划时间"></el-table-column>
                                    <el-table-column label="认证人员" :filter-method="filterPlanAuth" :filters="filterData">
                                        <template slot-scope="scope">
                                            <div v-if="scope.row.planAuth != null">
                                                <div v-for="(item, i) in scope.row.planAuth.users" :key="i">
                                                    {{ item.username }}
                                                </div>
                                            </div>
                                        </template>
                                    </el-table-column>
                                    <el-table-column label="完成时间" width="100">
                                        <template slot-scope="scope">
                                            <div v-if="scope.row.planAuth != null">
                                                {{ scope.row.planAuth.authActualTime }}
                                                <div
                                                    v-if="
                                                        scope.row.planAuth.authActualTime == null && scope.row.planAuth.authPlanTime != null
                                                    "
                                                >
                                                    <el-button type="primary" plain size="mini" @click="verify(scope.row, 2)"
                                                        >确认完成</el-button
                                                    >
                                                </div>
                                            </div>
                                        </template>
                                    </el-table-column>
                                </el-table-column>
                                <el-table-column label="稽核">
                                    <el-table-column prop="planCheck.checkPlanTime" label="计划时间"></el-table-column>
                                    <el-table-column label="稽核人员" :filter-method="filterPlanCheck" :filters="filterData">
                                        <template slot-scope="scope">
                                            <div v-if="scope.row.planCheck != null">
                                                <div v-for="(item, i) in scope.row.planCheck.users" :key="i">
                                                    {{ item.username }}
                                                </div>
                                            </div>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="planCheck.checkActualTime" label="完成时间"></el-table-column>
                                </el-table-column>
                                <el-table-column label="操作" fixed="right" width="150">
                                    <template slot-scope="scope">
                                        <div v-if="scope.row.planAuth == null || scope.row.planAuth.authPlanTime == null">
                                            <el-row>
                                                <el-col :span="24">
                                                    <el-button type="primary" plain size="mini" @click="settingPlan(scope.row)"
                                                        >设定计划送审时间</el-button
                                                    >
                                                </el-col>
                                            </el-row>
                                            <el-row v-if="scope.row.planGather.actualTime == null">
                                                <el-col :span="24">
                                                    <el-button type="warning" plain size="mini" @click="updatePlan(scope.row, 1)"
                                                        >修改计划</el-button
                                                    >
                                                </el-col>
                                                <el-col :span="24">
                                                    <el-button type="warning" plain size="mini" @click="delPlan(scope.row)"
                                                        >删除计划</el-button
                                                    >
                                                </el-col>
                                            </el-row>
                                        </div>
                                        <div v-else-if="scope.row.planCheck == null || scope.row.planCheck.checkPlanTime == null">
                                            <el-row>
                                                <el-col :span="24">
                                                    <el-button type="primary" plain size="mini" @click="addAuthPlan(scope.row)"
                                                        >设定稽核计划时间</el-button
                                                    >
                                                </el-col>
                                            </el-row>
                                            <el-row v-if="scope.row.planAuth.authActualTime == null">
                                                <el-col :span="24">
                                                    <el-button type="warning" plain size="mini" @click="updatePlan(scope.row, 2)"
                                                        >修改认证计划时间</el-button
                                                    >
                                                </el-col>
                                            </el-row>
                                        </div>
                                        <div v-else-if="scope.row.planCheck.checkActualTime == null">
                                            <el-row>
                                                <el-col :span="24">
                                                    <el-button type="primary" plain size="mini" @click="pass(scope.row)"
                                                        >稽核通过</el-button
                                                    >
                                                </el-col>
                                            </el-row>
                                            <el-row>
                                                <el-col :span="24">
                                                    <el-button type="warning" plain size="mini" @click="updatePlan(scope.row, 3)"
                                                        >修改稽核计划时间</el-button
                                                    >
                                                </el-col>
                                            </el-row>
                                        </div>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </div>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="流程作业指导书" name="second">
                    <div>
                        <el-table
                            :data="tabTableData"
                            style="width: 100%"
                            border
                            :cell-style="cellStyleTab"
                            :header-cell-style="tabTableHeaderColor"
                        >
                            <el-table-column type="index" label="No" width="40"></el-table-column>
                            <el-table-column prop="content" label="认证项目"></el-table-column>
                            <el-table-column label="收集资料">
                                <el-table-column prop="depPrincipal" label="被收集单位"></el-table-column>
                                <el-table-column prop="planGather.planTime" sortable label="计划完成时间"></el-table-column>
                                <el-table-column width="100" label="实际完成时间">
                                    <template slot-scope="scope">
                                        <div v-if="scope.row.planGather != null">
                                            {{ scope.row.planGather.actualTime }}
                                            <div v-if="scope.row.planGather.actualTime == null && scope.row.planGather.planTime != null">
                                                <el-button type="primary" plain size="mini" @click="verify(scope.row, 1)"
                                                    >确认完成</el-button
                                                >
                                            </div>
                                        </div>
                                    </template>
                                </el-table-column>

                                <el-table-column prop="users.username" label="收集资料组长"></el-table-column>
                                <el-table-column prop="planGather.gatherPlanTime" label="计划送审时间" sortable></el-table-column>
                            </el-table-column>
                            <el-table-column label="审核认证">
                                <el-table-column prop="planAuth.authPlanTime" label="计划时间" sortable></el-table-column>
                                <el-table-column label="认证人员" :filter-method="filterPlanAuth" :filters="filterData">
                                    <template slot-scope="scope">
                                        <div v-if="scope.row.planAuth != null">
                                            <div v-for="(item, i) in scope.row.planAuth.users" :key="i">
                                                {{ item.username }}
                                            </div>
                                        </div>
                                    </template>
                                </el-table-column>
                                <el-table-column label="完成时间" width="100">
                                    <template slot-scope="scope">
                                        <div v-if="scope.row.planAuth != null">
                                            {{ scope.row.planAuth.authActualTime }}
                                            <div
                                                v-if="scope.row.planAuth.authActualTime == null && scope.row.planAuth.authPlanTime != null"
                                            >
                                                <el-button type="primary" plain size="mini" @click="verify(scope.row, 2)"
                                                    >确认完成</el-button
                                                >
                                            </div>
                                        </div>
                                    </template>
                                </el-table-column>
                            </el-table-column>
                            <el-table-column label="稽核">
                                <el-table-column prop="planCheck.checkPlanTime" label="计划时间" sortable></el-table-column>
                                <el-table-column label="稽核人员" :filter-method="filterPlanCheck" :filters="filterData">
                                    <template slot-scope="scope">
                                        <div v-if="scope.row.planCheck != null">
                                            <div v-for="(item, i) in scope.row.planCheck.users" :key="i">
                                                {{ item.username }}
                                            </div>
                                        </div>
                                    </template>
                                </el-table-column>
                                <el-table-column prop="planCheck.checkActualTime" label="完成时间"></el-table-column>
                            </el-table-column>
                            <el-table-column label="操作" fixed="right" width="150">
                                <template slot-scope="scope">
                                    <div v-if="scope.row.planAuth == null || scope.row.planAuth.authPlanTime == null">
                                        <el-row>
                                            <el-col :span="24">
                                                <el-button type="primary" plain size="mini" @click="settingPlan(scope.row)"
                                                    >设定计划送审时间</el-button
                                                >
                                            </el-col>
                                        </el-row>
                                        <el-row v-if="scope.row.planGather.actualTime == null">
                                            <el-col :span="24">
                                                <el-button type="warning" plain size="mini" @click="updatePlan(scope.row, 1)"
                                                    >修改计划</el-button
                                                >
                                            </el-col>
                                            <el-col :span="24">
                                                <el-button type="warning" plain size="mini" @click="delPlan(scope.row)">删除计划</el-button>
                                            </el-col>
                                        </el-row>
                                    </div>
                                    <div v-else-if="scope.row.planCheck == null || scope.row.planCheck.checkPlanTime == null">
                                        <el-row>
                                            <el-col :span="24">
                                                <el-button type="primary" plain size="mini" @click="addAuthPlan(scope.row)"
                                                    >设定稽核计划时间</el-button
                                                >
                                            </el-col>
                                        </el-row>
                                        <el-row v-if="scope.row.planAuth.authActualTime == null">
                                            <el-col :span="24">
                                                <el-button type="warning" plain size="mini" @click="updatePlan(scope.row, 2)"
                                                    >修改认证计划时间</el-button
                                                >
                                            </el-col>
                                        </el-row>
                                    </div>
                                    <div v-else-if="scope.row.planCheck.checkActualTime == null">
                                        <el-row>
                                            <el-col :span="24">
                                                <el-button type="primary" plain size="mini" @click="pass(scope.row)">稽核通过</el-button>
                                            </el-col>
                                        </el-row>
                                        <el-row>
                                            <el-col :span="24">
                                                <el-button type="warning" plain size="mini" @click="updatePlan(scope.row, 3)"
                                                    >修改稽核计划时间</el-button
                                                >
                                            </el-col>
                                        </el-row>
                                    </div>
                                </template>
                            </el-table-column>
                        </el-table>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="工序标准作业票" name="third">
                    <div>
                        <el-table
                            :data="tabTableData"
                            style="width: 100%"
                            border
                            :cell-style="cellStyleTab"
                            :header-cell-style="tabTableHeaderColor"
                        >
                            <el-table-column type="index" label="No" width="40"></el-table-column>
                            <el-table-column prop="content" label="认证项目"></el-table-column>
                            <el-table-column label="收集资料">
                                <el-table-column prop="depPrincipal" label="被收集单位"></el-table-column>
                                <el-table-column prop="planGather.planTime" label="计划完成时间" sortable></el-table-column>
                                <el-table-column width="100" label="实际完成时间">
                                    <template slot-scope="scope">
                                        <div v-if="scope.row.planGather != null">
                                            {{ scope.row.planGather.actualTime }}
                                            <div v-if="scope.row.planGather.actualTime == null && scope.row.planGather.planTime != null">
                                                <el-button type="primary" plain size="mini" @click="verify(scope.row, 1)"
                                                    >确认完成</el-button
                                                >
                                            </div>
                                        </div>
                                    </template>
                                </el-table-column>

                                <el-table-column prop="users.username" label="收集资料组长"></el-table-column>
                                <el-table-column prop="planGather.gatherPlanTime" label="计划送审时间" sortable></el-table-column>
                            </el-table-column>
                            <el-table-column label="审核认证">
                                <el-table-column prop="planAuth.authPlanTime" label="计划时间" sortable></el-table-column>
                                <el-table-column label="认证人员" :filter-method="filterPlanAuth" :filters="filterData">
                                    <template slot-scope="scope">
                                        <div v-if="scope.row.planAuth != null">
                                            <div v-for="(item, i) in scope.row.planAuth.users" :key="i">
                                                {{ item.username }}
                                            </div>
                                        </div>
                                    </template>
                                </el-table-column>
                                <el-table-column label="完成时间" width="100">
                                    <template slot-scope="scope">
                                        <div v-if="scope.row.planAuth != null">
                                            {{ scope.row.planAuth.authActualTime }}
                                            <div
                                                v-if="scope.row.planAuth.authActualTime == null && scope.row.planAuth.authPlanTime != null"
                                            >
                                                <el-button type="primary" plain size="mini" @click="verify(scope.row, 2)"
                                                    >确认完成</el-button
                                                >
                                            </div>
                                        </div>
                                    </template>
                                </el-table-column>
                            </el-table-column>
                            <el-table-column label="稽核">
                                <el-table-column prop="planCheck.checkPlanTime" label="计划时间" sortable></el-table-column>
                                <el-table-column label="稽核人员" :filter-method="filterPlanCheck" :filters="filterData">
                                    <template slot-scope="scope">
                                        <div v-if="scope.row.planCheck != null">
                                            <div v-for="(item, i) in scope.row.planCheck.users" :key="i">
                                                {{ item.username }}
                                            </div>
                                        </div>
                                    </template>
                                </el-table-column>
                                <el-table-column prop="planCheck.checkActualTime" label="完成时间"></el-table-column>
                            </el-table-column>
                            <el-table-column label="操作" fixed="right" width="150">
                                <template slot-scope="scope">
                                    <div v-if="scope.row.planAuth == null || scope.row.planAuth.authPlanTime == null">
                                        <el-row>
                                            <el-col :span="24">
                                                <el-button type="primary" plain size="mini" @click="settingPlan(scope.row)"
                                                    >设定计划送审时间</el-button
                                                >
                                            </el-col>
                                        </el-row>
                                        <el-row v-if="scope.row.planGather.actualTime == null">
                                            <el-col :span="24">
                                                <el-button type="warning" plain size="mini" @click="updatePlan(scope.row, 1)"
                                                    >修改计划</el-button
                                                >
                                            </el-col>
                                            <el-col :span="24">
                                                <el-button type="warning" plain size="mini" @click="delPlan(scope.row)">删除计划</el-button>
                                            </el-col>
                                        </el-row>
                                    </div>
                                    <div v-else-if="scope.row.planCheck == null || scope.row.planCheck.checkPlanTime == null">
                                        <el-row>
                                            <el-col :span="24">
                                                <el-button type="primary" plain size="mini" @click="addAuthPlan(scope.row)"
                                                    >设定稽核计划时间</el-button
                                                >
                                            </el-col>
                                        </el-row>
                                        <el-row v-if="scope.row.planAuth.authActualTime == null">
                                            <el-col :span="24">
                                                <el-button type="warning" plain size="mini" @click="updatePlan(scope.row, 2)"
                                                    >修改认证计划时间</el-button
                                                >
                                            </el-col>
                                        </el-row>
                                    </div>
                                    <div v-else-if="scope.row.planCheck.checkActualTime == null">
                                        <el-row>
                                            <el-col :span="24">
                                                <el-button type="primary" plain size="mini" @click="pass(scope.row)">稽核通过</el-button>
                                            </el-col>
                                        </el-row>
                                        <el-row>
                                            <el-col :span="24">
                                                <el-button type="warning" plain size="mini" @click="updatePlan(scope.row, 3)"
                                                    >修改稽核计划时间</el-button
                                                >
                                            </el-col>
                                        </el-row>
                                    </div>
                                </template>
                            </el-table-column>
                        </el-table>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="设备操作规范" name="fourth">
                    <div>
                        <el-table
                            :data="tabTableData"
                            style="width: 100%"
                            border
                            :cell-style="cellStyleTab"
                            :header-cell-style="tabTableHeaderColor"
                        >
                            <el-table-column type="index" label="No" width="40"></el-table-column>
                            <el-table-column prop="content" label="认证项目"></el-table-column>
                            <el-table-column label="收集资料">
                                <el-table-column prop="depPrincipal" label="被收集单位"></el-table-column>
                                <el-table-column prop="planGather.planTime" label="计划完成时间" sortable></el-table-column>
                                <el-table-column width="100" label="实际完成时间">
                                    <template slot-scope="scope">
                                        <div v-if="scope.row.planGather != null">
                                            {{ scope.row.planGather.actualTime }}
                                            <div v-if="scope.row.planGather.actualTime == null && scope.row.planGather.planTime != null">
                                                <el-button type="primary" plain size="mini" @click="verify(scope.row, 1)"
                                                    >确认完成</el-button
                                                >
                                            </div>
                                        </div>
                                    </template>
                                </el-table-column>

                                <el-table-column prop="users.username" label="收集资料组长"></el-table-column>
                                <el-table-column prop="planGather.gatherPlanTime" label="计划送审时间" sortable></el-table-column>
                            </el-table-column>
                            <el-table-column label="审核认证">
                                <el-table-column prop="planAuth.authPlanTime" label="计划时间" sortable></el-table-column>
                                <el-table-column label="认证人员" :filter-method="filterPlanAuth" :filters="filterData">
                                    <template slot-scope="scope">
                                        <div v-if="scope.row.planAuth != null">
                                            <div v-for="(item, i) in scope.row.planAuth.users" :key="i">
                                                {{ item.username }}
                                            </div>
                                        </div>
                                    </template>
                                </el-table-column>
                                <el-table-column label="完成时间" width="100">
                                    <template slot-scope="scope">
                                        <div v-if="scope.row.planAuth != null">
                                            {{ scope.row.planAuth.authActualTime }}
                                            <div
                                                v-if="scope.row.planAuth.authActualTime == null && scope.row.planAuth.authPlanTime != null"
                                            >
                                                <el-button type="primary" plain size="mini" @click="verify(scope.row, 2)"
                                                    >确认完成</el-button
                                                >
                                            </div>
                                        </div>
                                    </template>
                                </el-table-column>
                            </el-table-column>
                            <el-table-column label="稽核">
                                <el-table-column prop="planCheck.checkPlanTime" label="计划时间" sortable></el-table-column>
                                <el-table-column label="稽核人员" :filter-method="filterPlanCheck" :filters="filterData">
                                    <template slot-scope="scope">
                                        <div v-if="scope.row.planCheck != null">
                                            <div v-for="(item, i) in scope.row.planCheck.users" :key="i">
                                                {{ item.username }}
                                            </div>
                                        </div>
                                    </template>
                                </el-table-column>
                                <el-table-column prop="planCheck.checkActualTime" label="完成时间"></el-table-column>
                            </el-table-column>
                            <el-table-column label="操作" fixed="right" width="150">
                                <template slot-scope="scope">
                                    <div v-if="scope.row.planAuth == null || scope.row.planAuth.authPlanTime == null">
                                        <el-row>
                                            <el-col :span="24">
                                                <el-button type="primary" plain size="mini" @click="settingPlan(scope.row)"
                                                    >设定计划送审时间</el-button
                                                >
                                            </el-col>
                                        </el-row>
                                        <el-row v-if="scope.row.planGather.actualTime == null">
                                            <el-col :span="24">
                                                <el-button type="warning" plain size="mini" @click="updatePlan(scope.row, 1)"
                                                    >修改计划</el-button
                                                >
                                            </el-col>
                                            <el-col :span="24">
                                                <el-button type="warning" plain size="mini" @click="delPlan(scope.row)">删除计划</el-button>
                                            </el-col>
                                        </el-row>
                                    </div>
                                    <div v-else-if="scope.row.planCheck == null || scope.row.planCheck.checkPlanTime == null">
                                        <el-row>
                                            <el-col :span="24">
                                                <el-button type="primary" plain size="mini" @click="addAuthPlan(scope.row)"
                                                    >设定稽核计划时间</el-button
                                                >
                                            </el-col>
                                        </el-row>
                                        <el-row v-if="scope.row.planAuth.authActualTime == null">
                                            <el-col :span="24">
                                                <el-button type="warning" plain size="mini" @click="updatePlan(scope.row, 2)"
                                                    >修改认证计划时间</el-button
                                                >
                                            </el-col>
                                        </el-row>
                                    </div>
                                    <div v-else-if="scope.row.planCheck.checkActualTime == null">
                                        <el-row>
                                            <el-col :span="24">
                                                <el-button type="primary" plain size="mini" @click="pass(scope.row)">稽核通过</el-button>
                                            </el-col>
                                        </el-row>
                                        <el-row>
                                            <el-col :span="24">
                                                <el-button type="warning" plain size="mini" @click="updatePlan(scope.row, 3)"
                                                    >修改稽核计划时间</el-button
                                                >
                                            </el-col>
                                        </el-row>
                                    </div>
                                </template>
                            </el-table-column>
                        </el-table>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="品管作业指导书" name="fifth">
                    <div>
                        <el-table
                            :data="tabTableData"
                            style="width: 100%"
                            border
                            :cell-style="cellStyleTab"
                            :header-cell-style="tabTableHeaderColor"
                        >
                            <el-table-column type="index" label="No" width="40"></el-table-column>
                            <el-table-column prop="content" label="认证项目"></el-table-column>
                            <el-table-column label="收集资料">
                                <el-table-column prop="depPrincipal" label="被收集单位"></el-table-column>
                                <el-table-column prop="planGather.planTime" label="计划完成时间" sortable></el-table-column>
                                <el-table-column width="100" label="实际完成时间">
                                    <template slot-scope="scope">
                                        <div v-if="scope.row.planGather != null">
                                            {{ scope.row.planGather.actualTime }}
                                            <div v-if="scope.row.planGather.actualTime == null && scope.row.planGather.planTime != null">
                                                <el-button type="primary" plain size="mini" @click="verify(scope.row, 1)"
                                                    >确认完成</el-button
                                                >
                                            </div>
                                        </div>
                                    </template>
                                </el-table-column>

                                <el-table-column prop="users.username" label="收集资料组长"></el-table-column>
                                <el-table-column prop="planGather.gatherPlanTime" label="计划送审时间" sortable></el-table-column>
                            </el-table-column>
                            <el-table-column label="审核认证">
                                <el-table-column prop="planAuth.authPlanTime" label="计划时间" sortable></el-table-column>
                                <el-table-column label="认证人员" :filter-method="filterPlanAuth" :filters="filterData">
                                    <template slot-scope="scope">
                                        <div v-if="scope.row.planAuth != null">
                                            <div v-for="(item, i) in scope.row.planAuth.users" :key="i">
                                                {{ item.username }}
                                            </div>
                                        </div>
                                    </template>
                                </el-table-column>
                                <el-table-column label="完成时间" width="100">
                                    <template slot-scope="scope">
                                        <div v-if="scope.row.planAuth != null">
                                            {{ scope.row.planAuth.authActualTime }}
                                            <div
                                                v-if="scope.row.planAuth.authActualTime == null && scope.row.planAuth.authPlanTime != null"
                                            >
                                                <el-button type="primary" plain size="mini" @click="verify(scope.row, 2)"
                                                    >确认完成</el-button
                                                >
                                            </div>
                                        </div>
                                    </template>
                                </el-table-column>
                            </el-table-column>
                            <el-table-column label="稽核">
                                <el-table-column prop="planCheck.checkPlanTime" label="计划时间" sortable></el-table-column>
                                <el-table-column label="稽核人员" :filter-method="filterPlanCheck" :filters="filterData">
                                    <template slot-scope="scope">
                                        <div v-if="scope.row.planCheck != null">
                                            <div v-for="(item, i) in scope.row.planCheck.users" :key="i">
                                                {{ item.username }}
                                            </div>
                                        </div>
                                    </template>
                                </el-table-column>
                                <el-table-column prop="planCheck.checkActualTime" label="完成时间"></el-table-column>
                            </el-table-column>
                            <el-table-column label="操作" fixed="right" width="150">
                                <template slot-scope="scope">
                                    <div v-if="scope.row.planAuth == null || scope.row.planAuth.authPlanTime == null">
                                        <el-row>
                                            <el-col :span="24">
                                                <el-button type="primary" plain size="mini" @click="settingPlan(scope.row)"
                                                    >设定计划送审时间</el-button
                                                >
                                            </el-col>
                                        </el-row>
                                        <el-row v-if="scope.row.planGather.actualTime == null">
                                            <el-col :span="24">
                                                <el-button type="warning" plain size="mini" @click="updatePlan(scope.row, 1)"
                                                    >修改计划</el-button
                                                >
                                            </el-col>
                                            <el-col :span="24">
                                                <el-button type="warning" plain size="mini" @click="delPlan(scope.row)">删除计划</el-button>
                                            </el-col>
                                        </el-row>
                                    </div>
                                    <div v-else-if="scope.row.planCheck == null || scope.row.planCheck.checkPlanTime == null">
                                        <el-row>
                                            <el-col :span="24">
                                                <el-button type="primary" plain size="mini" @click="addAuthPlan(scope.row)"
                                                    >设定稽核计划时间</el-button
                                                >
                                            </el-col>
                                        </el-row>
                                        <el-row v-if="scope.row.planAuth.authActualTime == null">
                                            <el-col :span="24">
                                                <el-button type="warning" plain size="mini" @click="updatePlan(scope.row, 2)"
                                                    >修改认证计划时间</el-button
                                                >
                                            </el-col>
                                        </el-row>
                                    </div>
                                    <div v-else-if="scope.row.planCheck.checkActualTime == null">
                                        <el-row>
                                            <el-col :span="24">
                                                <el-button type="primary" plain size="mini" @click="pass(scope.row)">稽核通过</el-button>
                                            </el-col>
                                        </el-row>
                                        <el-row>
                                            <el-col :span="24">
                                                <el-button type="warning" plain size="mini" @click="updatePlan(scope.row, 3)"
                                                    >修改稽核计划时间</el-button
                                                >
                                            </el-col>
                                        </el-row>
                                    </div>
                                </template>
                            </el-table-column>
                        </el-table>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="已完成" name="sixth">
                    <div>
                        <el-table :data="finishTableData" style="width: 100%" border :header-cell-style="tabTableHeaderColor">
                            <el-table-column type="index" label="No" width="40"></el-table-column>
                            <el-table-column prop="content" label="认证内容"></el-table-column>
                            <el-table-column label="收集资料">
                                <el-table-column prop="depPrincipal" label="被收集单位"></el-table-column>
                                <el-table-column prop="planGather.planTime" sortable label="计划完成时间"></el-table-column>
                                <el-table-column prop="planGather.actualTime" sortable label="实际完成时间"> </el-table-column>
                                <el-table-column
                                    prop="users.username"
                                    label="收集资料组长"
                                    :filters="filterData"
                                    :filter-method="filterPlanGather"
                                ></el-table-column>
                                <el-table-column
                                    prop="planGather.gatherPlanTime"
                                    sortable
                                    label="计划送审时间"
                                    width="136"
                                ></el-table-column>
                            </el-table-column>
                            <el-table-column label="审核认证">
                                <el-table-column prop="planAuth.authPlanTime" sortable label="计划时间" width="136"></el-table-column>
                                <el-table-column label="认证人员" :filters="filterData" :filter-method="filterPlanAuth">
                                    <template slot-scope="scope">
                                        <div v-if="scope.row.planAuth != null">
                                            <div v-for="(item, i) in scope.row.planAuth.users" :key="i">
                                                {{ item.username }}
                                            </div>
                                        </div>
                                    </template>
                                </el-table-column>
                                <el-table-column prop="planAuth.authActualTime" sortable label="完成时间" width="100"> </el-table-column>
                            </el-table-column>
                            <el-table-column label="稽核">
                                <el-table-column prop="planCheck.checkPlanTime" sortable label="计划时间" width="136"></el-table-column>
                                <el-table-column label="稽核人员" :filters="filterData" :filter-method="filterPlanCheck">
                                    <template slot-scope="scope">
                                        <div v-if="scope.row.planCheck != null">
                                            <div v-for="(item, i) in scope.row.planCheck.users" :key="i">
                                                {{ item.username }}
                                            </div>
                                        </div>
                                    </template>
                                </el-table-column>
                                <el-table-column prop="planCheck.checkActualTime" sortable label="完成时间"></el-table-column>
                            </el-table-column>
                        </el-table>
                    </div>
                </el-tab-pane>
            </el-tabs>
        </div>
        <div>
            <!-- 修改时间 -->
            <el-dialog :title="title" :visible.sync="timeUpdateShow" width="40%" :show-close="false">
                <div>
                    <el-form ref="form" :model="formUpdate">
                        <el-form-item label="选择日期时间">
                            <el-date-picker
                                v-model="formUpdate.time"
                                type="datetime"
                                placeholder="选择日期时间"
                                value-format="yyyy-MM-dd HH:mm:ss"
                                :picker-options="expireTimeOption"
                            >
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item label="人员">
                            <el-select v-model="formUpdate.checkUsers" multiple filterable placeholder="请选择">
                                <el-option v-for="item in userList" :key="item.id" :label="item.username" :value="item.id"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-form>
                </div>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="cancelUpdateChose">取 消</el-button>
                    <el-button type="primary" @click="submitUpdateChose">提 交</el-button>
                </span>
            </el-dialog>
            <!-- 设定稽核计划 -->
            <el-dialog :title="title" :visible.sync="timeChoseShow" width="40%" :show-close="false">
                <div>
                    <el-form ref="form" :model="formChoose">
                        <el-form-item label="选择稽核人员">
                            <el-select v-model="formChoose.checkUsers" multiple filterable placeholder="请选择">
                                <el-option v-for="item in userList" :key="item.id" :label="item.username" :value="item.id"></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="选择日期时间">
                            <el-date-picker
                                v-model="formChoose.time"
                                type="datetime"
                                placeholder="选择日期时间"
                                value-format="yyyy-MM-dd HH:mm:ss"
                                :picker-options="expireTimeOption"
                            >
                            </el-date-picker>
                        </el-form-item>
                    </el-form>
                </div>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="cancelChose">取 消</el-button>
                    <el-button type="primary" @click="submitChose">提 交</el-button>
                </span>
            </el-dialog>
            <!-- 添加认证计划 -->
            <el-dialog title="填写资料" :visible.sync="planGatherShow" width="40%">
                <div>
                    <el-form ref="form" :model="gatherForm" label-width="100px">
                        <el-form-item label="选择审核人员">
                            <el-select v-model="gatherForm.authUsers" multiple filterable placeholder="请选择">
                                <el-option v-for="item in userList" :key="item.id" :label="item.username" :value="item.id"></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="计划资料送审时间">
                            <el-date-picker
                                v-model="gatherForm.gatherTime"
                                type="datetime"
                                placeholder="选择日期时间"
                                value-format="yyyy-MM-dd HH:mm:ss"
                                :picker-options="expireTimeOption"
                            >
                            </el-date-picker>
                        </el-form-item>
                    </el-form>
                </div>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="gatherCancel">取 消</el-button>
                    <el-button type="primary" @click="gatherSubmit">提 交</el-button>
                </span>
            </el-dialog>
            <!-- 添加计划 -->
            <el-dialog title="添加计划" :visible.sync="planShow" width="40%">
                <div>
                    <el-form ref="form" :model="form" label-width="140px">
                        <el-form-item label="认证项目">
                            <el-input v-model="form.content"></el-input>
                        </el-form-item>
                        <el-form-item label="四大标准">
                            <el-select v-model="form.dirId" filterable placeholder="请选择">
                                <el-option v-for="item in directory" :key="item.id" :label="item.dirName" :value="item.id"></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="被收集单位负责人">
                            <el-input v-model="form.depPrincipal"></el-input>
                        </el-form-item>
                        <el-form-item label="计划完成时间">
                            <el-date-picker
                                v-model="form.gatherTime"
                                type="datetime"
                                placeholder="选择日期时间"
                                value-format="yyyy-MM-dd HH:mm:ss"
                                :picker-options="expireTimeOption"
                            >
                            </el-date-picker>
                        </el-form-item>
                    </el-form>
                </div>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="cancel">取 消</el-button>
                    <el-button type="primary" @click="submit">提 交</el-button>
                </span>
            </el-dialog>
            <el-dialog title="修改计划" :visible.sync="updatePlanShow" width="40%">
                <div>
                    <el-form ref="form" :model="updatePlanForm" label-width="140px">
                        <el-form-item label="认证项目">
                            <el-input v-model="updatePlanForm.content"></el-input>
                        </el-form-item>
                        <el-form-item label="四大标准">
                            <el-select v-model="updatePlanForm.dirId" filterable placeholder="请选择">
                                <el-option v-for="item in directory" :key="item.id" :label="item.dirName" :value="item.id"></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="被收集单位负责人">
                            <el-input v-model="updatePlanForm.depPrincipal"></el-input>
                        </el-form-item>
                        <el-form-item label="计划完成时间">
                            <el-date-picker
                                v-model="updatePlanForm.planTime"
                                type="datetime"
                                placeholder="选择日期时间"
                                value-format="yyyy-MM-dd HH:mm:ss"
                                :picker-options="expireTimeOption"
                            >
                            </el-date-picker>
                        </el-form-item>
                    </el-form>
                </div>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="updatePlanCancel">取 消</el-button>
                    <el-button type="primary" @click="updatePlanSubmit">提 交</el-button>
                </span>
            </el-dialog>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            updatePlanShow: false,
            updatePlanForm: {
                id: '',
                content: '',
                dirId: '',
                depPrincipal: '',
                planTime: ''
            },
            numFirst: 0,
            numSecond: 0,
            numThird: 0,
            numFourth: 0,
            numFifth: 0,
            numSixth: 0,
            activeName: 'first',
            title: '',
            visible: false,
            timeUpdateShow: false,
            timeChoseShow: false,
            planShow: false,
            planGatherShow: false,
            formUpdate: {
                time: '',
                planId: '',
                userId: '',
                operaId: '',
                checkUsers: []
            },
            formChoose: {
                time: '',
                planId: '',
                userId: '',
                operaId: '',
                checkUsers: []
            },
            form: {
                content: '',
                dirId: '',
                gatherTime: '',
                depPrincipal: ''
            },
            gatherForm: {
                // content: '',
                planId: '',
                gatherTime: '',
                authUsers: []
            },
            directory: [],
            userData: [],
            tableData: [],
            userList: [],
            expireTimeOption: {
                disabledDate(date) {
                    //disabledDate 文档上：设置禁用状态，参数为当前日期，要求返回 Boolean
                    return date.getTime() < Date.now() - 24 * 60 * 60 * 1000;
                }
            },
            tabIndex: '',
            tabTableData: [],
            filterData: [],
            finishTableData: [],
            sort: ['descending']
        };
    },
    created() {
        let that = this;
        this.userData = JSON.parse(sessionStorage.getItem('userData'));
        this.initData();
        this.initNum(1);
        this.initNum(2);
        this.initNum(3);
        this.initNum(4);
        this.$axios
            .get('api/plan/getDir')
            .then((res) => {
                that.directory = res.data;
            })
            .catch((err) => {});
        this.$axios
            .get('api/user/show')
            .then((result) => {
                that.userList = result.data.obj;
                that.userList.forEach((item) => {
                    that.filterData.push({
                        text: item.username,
                        value: item.id
                    });
                });
            })
            .catch((error) => {
                console.log(error);
            });

        this.$axios.get('api/plan/getPlanFinish').then((res) => {
            that.finishTableData = res.data.data;
            that.numSixth = res.data.num;
        });
    },
    methods: {
        filterPlanGather(value, row) {
            if (row.planGather != null) {
                if (row.planGather.userId == value) {
                    return true;
                }
            }
        },
        // 认证人过滤
        filterPlanAuth(value, row) {
            if (row.planAuth != null) {
                if (row.planAuth.users.length != 0) {
                    for (var i = 0; i < row.planAuth.users.length; i++) {
                        if (row.planAuth.users[i].id == value) {
                            console.log(row.planAuth.users[i].username);
                            return true;
                        }
                    }
                }
            }
        },
        // 稽查人过滤
        filterPlanCheck(value, row) {
            console.log(value);
            if (row.planCheck != null) {
                if (row.planCheck.users.length != 0) {
                    for (var i = 0; i < row.planCheck.users.length; i++) {
                        if (row.planCheck.users[i].id == value) {
                            console.log(row.planCheck.users[i].username);
                            return true;
                        }
                    }
                }
            }
        },
        // 标签页点击事件
        handleClick(tab) {
            let that = this;
            this.tabIndex = tab.index;
            if (this.tabIndex == 0) {
                this.initData();
            } else if (this.tabIndex == 5) {
                that.$axios.get('api/plan/getPlanFinish').then((res) => {
                    that.finishTableData = res.data.data;
                    that.numSixth = res.data.num;
                });
            } else {
                this.initDataDir(this.tabIndex);
            }
        },
        // 确认完成的三个方法 index == 1 代表 收集资料 2 代表认证
        verify(row, index) {
            let that = this;
            this.$confirm('是否确认完成?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            })
                .then(() => {
                    let role = that.userData.role;
                    if (index == 1) {
                        if (role.length <= 0) {
                            that.$message('无权限操作');
                            return true;
                        }
                        let userCount = 0;
                        if (this.userData.user.roleGroupId == 1) {
                            userCount = 1;
                        }
                        if (row.planGather.userId == that.userData.user.id) {
                            userCount++;
                        }
                        if (userCount <= 0) {
                            that.$message('无权限操作');
                        } else {
                            that.sure(row.id, that.userData.user.id, index);
                        }
                    } else if (index == 2) {
                        let userCount = 0;
                        if (this.userData.user.roleGroupId == 1) {
                            userCount = 1;
                        }
                        for (let i = 0; i < row.planAuth.users.length; i++) {
                            if (row.planAuth.users[i].id == that.userData.user.id) {
                                userCount++;
                            }
                        }
                        if (userCount > 0) {
                            that.sure(row.id, that.userData.user.id, index);
                        } else {
                            that.$message('无权限操作');
                        }
                    }
                })
                .catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消'
                    });
                });
        },
        // 确认完成 请求后台方法
        sure(id, userId, index) {
            let that = this;
            that.$axios
                .get('api/plan/verify?planId=' + id + '&userId=' + userId + '&index=' + index)
                .then((res) => {
                    if (that.tabIndex != 0) {
                        that.initDataDir(that.tabIndex);
                    }
                })
                .catch((res) => {});
        },
        // 稽核通过的方法
        pass(row) {
            let that = this;
            let form = {
                planId: row.id,
                userId: this.userData.user.id
            };

            let role = that.userData.role;
            if (role.length <= 0) {
                that.$message('无权限操作');
                return true;
            }
            let count = 0;
            if (this.userData.user.roleGroupId == 1) {
                count = 1;
            }
            for (let i = 0; i < role.length; i++) {
                let roleCode = role[i].roleCode;
                if (roleCode == 'checkDir1' || roleCode == 'checkDir2' || roleCode == 'checkDir3' || roleCode == 'checkDir4') {
                    count++;
                }
            }
            if (count <= 0) {
                that.$message('无权限操作');
            } else {
                that.operaPassCheck(form);
            }
        },
        operaPassCheck(form) {
            let that = this;
            this.$axios
                .post('api/plan/passCheck', form)
                .then((res) => {
                    this.$message({
                        message: res.data,
                        type: 'success'
                    });
                    if (that.tabIndex != 0) {
                        that.initDataDir(that.tabIndex);
                        return true;
                    }
                    that.initData();
                })
                .catch((error) => {});
        },
        // 删除计划
        delPlan(row) {
            let that = this;
            console.log(row);
            let userCount = 0;
            if (this.userData.user.roleGroupId == 1) {
                userCount = 1;
            }
            if (row.planGather.userId == that.userData.user.id) {
                userCount++;
            }
            if (userCount <= 0) {
                that.$message('无权限操作');
            } else {
                this.$confirm('是否确认完成?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                })
                    .then(() => {
                        that.$axios.get('/api/plan/delPlan?id=' + row.id).then((res) => {
                            if (res.data.msg == 'ok') {
                                that.$message.success(res.data.obj);
                                that.initData();
                                if (that.tabIndex != 0) {
                                    that.initDataDir(that.tabIndex);
                                    return true;
                                }
                            } else {
                                that.$message.error(res.data.obj);
                            }
                        });
                    })
                    .catch(() => {
                        that.$message({
                            type: 'info',
                            message: '已取消'
                        });
                    });
            }
        },
        updatePlanCancel() {
            this.updatePlanForm = {
                id: '',
                content: '',
                dirId: '',
                depPrincipal: '',
                planTime: ''
            };
            this.updatePlanShow = false;
        },
        updatePlanSubmit() {
            let that = this;
            this.$axios.post('/api/plan/updatePlan', this.updatePlanForm).then((res) => {
                if (res.data.msg == 'ok') {
                    this.$message.success(res.data.obj);
                } else {
                    this.$message.error(res.data.obj);
                }
                if (that.tabIndex != 0) {
                    that.initDataDir(that.tabIndex);
                }
                that.updatePlanShow = false;
                that.updatePlanForm = {
                    id: '',
                    content: '',
                    dirId: '',
                    depPrincipal: '',
                    planTime: ''
                };
            });
        },
        // 修改计划时间弹框的所需三个方法
        updatePlan(row, index) {
            let that = this;
            let count = 0;
            this.formUpdate.planId = row.id;
            this.formUpdate.userId = this.userData.user.id;
            this.formUpdate.operaId = index;
            let planAuthUsers = [];
            let planCheckUsers = [];
            if (row.planAuth != null) {
                for (const key in row.planAuth.users) {
                    planAuthUsers.push(row.planAuth.users[key].id);
                }
            }
            if (row.planCheck != null) {
                for (const key in row.planCheck.users) {
                    planCheckUsers.push(row.planCheck.users[key].id);
                }
            }
            if (index == 1) {
                let userCount = 0;
                if (this.userData.user.roleGroupId == 1) {
                    userCount = 1;
                }
                if (row.planGather.userId == that.userData.user.id) {
                    userCount++;
                }
                if (userCount <= 0) {
                    that.$message('无权限操作');
                } else {
                    that.updatePlanForm.id = row.id;
                    that.updatePlanForm.content = row.content;
                    that.updatePlanForm.dirId = row.dirId;
                    that.updatePlanForm.depPrincipal = row.depPrincipal;
                    that.updatePlanForm.planTime = row.planGather.planTime;

                    that.updatePlanShow = true;
                }
            } else if (index == 2) {
                if (this.userData.user.roleGroupId != 1) {
                    for (let i = 0; i < row.planAuth.users.length; i++) {
                        if (row.planAuth.users[i].id == this.userData.user.id) {
                            count++;
                        }
                    }
                    if (count > 0) {
                        that.formUpdate.checkUsers = planAuthUsers;
                        this.title = '修改计划审核认证时间';
                        this.timeUpdateShow = true;
                    } else {
                        this.$message({
                            message: '无法操作'
                        });
                        return true;
                    }
                } else {
                    that.formUpdate.checkUsers = planAuthUsers;
                    this.title = '修改计划审核认证时间';
                    this.timeUpdateShow = true;
                }
            } else if (index == 3) {
                if (this.userData.user.roleGroupId != 1) {
                    let role = that.userData.role;
                    if (role.length <= 0) {
                        that.$message('无权限操作');
                        return true;
                    }
                    let count = 0;
                    for (let i = 0; i < role.length; i++) {
                        let roleCode = role[i].roleCode;
                        if (roleCode == 'checkDir1' || roleCode == 'checkDir2' || roleCode == 'checkDir3' || roleCode == 'checkDir4') {
                            count++;
                        }
                    }

                    if (count > 0) {
                        that.formUpdate.checkUsers = planCheckUsers;
                        this.title = '修改计划稽核时间';
                        this.timeUpdateShow = true;
                    } else {
                        this.$message({
                            message: '无法操作'
                        });
                        return true;
                    }
                } else {
                    console.log(row);
                    that.formUpdate.checkUsers = planCheckUsers;
                    this.title = '修改计划稽核时间';
                    this.timeUpdateShow = true;
                }
            }
        },
        submitUpdateChose() {
            let that = this;
            let index = this.formUpdate.operaId;
            let form = {
                time: this.formUpdate.time,
                planId: this.formUpdate.planId,
                userId: this.formUpdate.userId,
                checkUsers: this.formUpdate.checkUsers
            };
            if (form.time == null || form.time == '') {
                this.$message({
                    message: '请选择计划时间',
                    type: 'warning'
                });
                return true;
            }
            console.log(this.formUpdate.checkUsers);
            console.log(form);
            if (form.checkUsers.length <= 0) {
                this.$message({
                    message: '请选择人员',
                    type: 'warning'
                });
                return true;
            }
            if (index == 2) {
                console.log('修改认证计划时间');
                this.$axios
                    .post('api/plan/updatePlanAuth', form)
                    .then((res) => {
                        this.$message({
                            message: res.data,
                            type: 'success'
                        });
                        that.timeUpdateShow = false;
                        that.initForm();
                        if (that.tabIndex != 0) {
                            that.initDataDir(that.tabIndex);
                            return true;
                        }
                        that.initData();
                    })
                    .catch((err) => {});
            } else if (index == 3) {
                console.log('修改稽核计划时间');
                this.$axios
                    .post('api/plan/updatePlanCheck', form)
                    .then((res) => {
                        this.$message({
                            message: res.data,
                            type: 'success'
                        });
                        that.timeUpdateShow = false;
                        that.initForm();
                        if (that.tabIndex != 0) {
                            that.initDataDir(that.tabIndex);
                            return true;
                        }
                        that.initData();
                    })
                    .catch((err) => {});
            }
        },
        cancelUpdateChose() {
            this.initForm();
            this.timeUpdateShow = false;
        },
        // index 代表点击的什么按钮 1.审核认证 2.稽核
        // 添加稽核计划弹框的所需三个方法
        addAuthPlan(row) {
            this.formChoose.planId = row.id;
            this.formChoose.userId = this.userData.user.id;
            let that = this;

            let role = that.userData.role;
            if (role.length <= 0) {
                that.$message('无权限操作');
                return true;
            }
            let count = 0;
            if (this.userData.user.roleGroupId == 1) {
                count = 1;
            }
            for (let i = 0; i < role.length; i++) {
                let roleCode = role[i].roleCode;
                if (roleCode == 'checkDir1' || roleCode == 'checkDir2' || roleCode == 'checkDir3' || roleCode == 'checkDir4') {
                    count++;
                }
            }
            if (count <= 0) {
                that.$message('无权限操作');
            } else {
                this.title = '设定计划稽核时间';
                if (row.planAuth.authActualTime == null) {
                    this.$message({
                        message: '请确认完成审核认证',
                        type: 'warning'
                    });
                    return true;
                }
                this.timeChoseShow = true;
            }
        },
        cancelChose() {
            this.initForm();
            this.timeChoseShow = false;
        },
        submitChose() {
            let that = this;
            let form = {
                time: this.formChoose.time,
                planId: this.formChoose.planId,
                userId: this.formChoose.userId,
                checkUsers: this.formChoose.checkUsers
            };
            if (form.time == null || form.time == '') {
                this.$message({
                    message: '请选择计划时间',
                    type: 'warning'
                });
                return true;
            }
            if (form.checkUsers.length == 0) {
                this.$message({
                    message: '请选择稽核人员',
                    type: 'warning'
                });
                return true;
            }
            console.log('添加稽核计划');
            this.$axios
                .post('api/plan/addPlanCheck', form)
                .then((res) => {
                    this.$message({
                        message: res.data,
                        type: 'success'
                    });
                    that.timeChoseShow = false;
                    if (that.tabIndex != 0) {
                        that.initDataDir(that.tabIndex);
                    }
                    that.initForm();
                    if (that.tabIndex != 0) {
                        that.initDataDir(that.tabIndex);
                        return true;
                    }
                    that.initData();
                })
                .catch((err) => {});
        },
        // 添加计划弹框的所需三个方法
        addPlan() {
            let role = this.userData.role;
            if (role.length > 0) {
                let count = 0;
                for (let i = 0; i < role.length; i++) {
                    let roleCode = role[i].roleCode;
                    if (roleCode == 'dir1' || roleCode == 'dir2' || roleCode == 'dir3' || roleCode == 'dir4') {
                        count++;
                    }
                }
                if (count <= 0) {
                    this.$message('无权限操作');
                } else {
                    this.planShow = true;
                    return true;
                }
            }
            this.$message('无权限操作');
        },
        cancel() {
            this.initForm();
            this.planShow = false;
        },
        submit() {
            let that = this;
            let form = {
                dirId: this.form.dirId,
                content: this.form.content.trim(),
                userId: this.userData.user.id,
                time: this.form.gatherTime,
                depPrincipal: this.form.depPrincipal.trim()
            };

            if (form.content == '' || form.content == null) {
                this.$message({
                    message: '请填写认证内容',
                    type: 'warning'
                });
                return true;
            }
            if (form.dirId == 0 || form.dirId == '') {
                this.$message({
                    message: '请选择认证项目',
                    type: 'warning'
                });
                return true;
            }
            if (form.depPrincipal == null || form.depPrincipal == '') {
                this.$message({
                    message: '请选择部门负责人',
                    type: 'warning'
                });
                return true;
            }
            if (form.time == null || form.time == '') {
                this.$message({
                    message: '请选择计划时间',
                    type: 'warning'
                });
                return true;
            }

            console.log(form);
            this.$axios
                .post('api/plan/addPlan', form)
                .then((res) => {
                    this.$message({
                        message: res.data,
                        type: 'success'
                    });
                    that.planShow = false;
                    that.initData();
                    that.initForm();
                })
                .catch((err) => {});
        },
        // 添加收集计划并设定认证计划时间弹框所需方法
        settingPlan(row) {
            console.log(this.userData);
            console.log(row);
            if (row.planGather.actualTime == null) {
                this.$message({
                    message: '请确认完成收集资料',
                    type: 'warning'
                });
                return true;
            }
            if (this.userData.user.roleGroupId != 1) {
                if (row.planGather.userId != this.userData.user.id) {
                    this.$message({
                        message: '无法操作'
                    });
                    return true;
                }
            }

            this.planGatherShow = true;
            this.gatherForm.planId = row.id;
        },
        gatherSubmit() {
            let that = this;
            let form = {
                userId: this.userData.user.id,
                time: this.gatherForm.gatherTime,
                authUsers: this.gatherForm.authUsers,
                planId: this.gatherForm.planId
            };

            if (form.authUsers.length == 0) {
                this.$message({
                    message: '请选择审核人员',
                    type: 'warning'
                });
                return true;
            }
            if (form.time == null || form.time == '') {
                this.$message({
                    message: '请选择计划送审时间',
                    type: 'warning'
                });
                return true;
            }
            console.log(form);
            this.$axios
                .post('api/plan/addPlanAuth', form)
                .then((res) => {
                    this.$message({
                        message: res.data,
                        type: 'success'
                    });
                    that.planGatherShow = false;
                    that.initData();
                    if (that.tabIndex != '' || that.tabIndex != 0) {
                        that.initDataDir(that.tabIndex);
                        return true;
                    }
                    that.initForm();
                })
                .catch((err) => {});
        },
        gatherCancel() {
            this.initForm();
            this.planGatherShow = false;
        },

        // 初始化数据
        initData() {
            let that = this;
            this.$axios
                .get('api/plan/getPlan')
                .then((res) => {
                    that.tableData = res.data.data;
                    that.tableData.forEach((item) => {
                        item['dirName'] = item.directory.dirName;
                    });
                    // 合并单元格
                    that.tableData = that.mergeTableRow(that.tableData, ['dirName']);
                    that.numFirst = res.data.count;
                    console.log(that.tableData);
                })
                .catch((err) => {});
        },
        initDataDir(index) {
            this.$axios.get('/api/plan/getPlanByDir?dirId=' + index).then((res) => {
                console.log(res.data.data);
                this.tabTableData = res.data.data;
            });
        },
        initNum(index) {
            let that = this;
            this.$axios.get('/api/plan/getPlanNum?dirId=' + index).then((res) => {
                if (index == 1) {
                    that.numSecond = res.data.num;
                } else if (index == 2) {
                    that.numThird = res.data.num;
                } else if (index == 3) {
                    that.numFourth = res.data.num;
                } else if (index == 4) {
                    that.numFifth = res.data.num;
                }
            });
        },
        // 将表单的值重置
        initForm() {
            (this.formUpdate = {
                time: '',
                planId: '',
                userId: '',
                operaId: '',
                checkUsers: []
            }),
                (this.formChoose = {
                    time: '',
                    planId: '',
                    userId: '',
                    operaId: '',
                    checkUsers: []
                }),
                (this.form = {
                    content: '',
                    dirId: '',
                    gatherTime: '',
                    depPrincipal: ''
                }),
                (this.gatherForm = {
                    // content: '',
                    planId: '',
                    gatherTime: '',
                    authUsers: []
                });
        },
        // 合并单元格
        objectSpanMethod({ row, column, rowIndex, columnIndex }) {
            const span = column['property'] + '-span';
            if (row[span]) {
                return row[span];
            }
        },
        mergeTableRow(data, merge) {
            if (!merge || merge.length === 0) {
                return data;
            }
            merge.forEach((m) => {
                const mList = {};
                data = data.map((v, index) => {
                    const rowVal = v[m];
                    if (mList[rowVal] && mList[rowVal].newIndex === index) {
                        mList[rowVal]['num']++;
                        mList[rowVal]['newIndex']++;
                        data[mList[rowVal]['index']][m + '-span'].rowspan++;
                        v[m + '-span'] = {
                            rowspan: 0,
                            colspan: 0
                        };
                    } else {
                        mList[rowVal] = { num: 1, index: index, newIndex: index + 1 };
                        v[m + '-span'] = {
                            rowspan: 1,
                            colspan: 1
                        };
                    }
                    return v;
                });
            });
            return data;
        },
        // 设置单元格样式
        cellStyle({ row, columnIndex }) {
            if (columnIndex == 4) {
                if (row.planGather != null) {
                    if (row.planGather.planTime != null && row.planGather.actualTime == null) {
                        if (this.calendarLt(row.planGather.planTime)) {
                            return 'background-color: #F56C6C';
                        } else if (this.calendarSame(row.planGather.planTime)) {
                            return 'background-color: #67C23A';
                        }
                        return null;
                    }
                    //  else if (row.planGather.actualTime != null && row.planGather.gatherPlanTime == null) {
                    //     if (this.calendarLt(row.planGather.planTime)) {
                    //         return 'background-color: #F56C6C';
                    //     }
                    //     return 'background-color: #67C23A';
                    // }
                }
            }
            if (columnIndex == 8) {
                if (row.planAuth != null) {
                    if (row.planAuth.authPlanTime != null && row.planAuth.authActualTime == null) {
                        if (this.calendarLt(row.planAuth.authPlanTime)) {
                            return 'background-color: #F56C6C';
                        } else if (this.calendarSame(row.planAuth.authPlanTime)) {
                            return 'background-color: #67C23A';
                        }
                        return null;
                    }
                    //  else if (row.planAuth.authActualTime != null && row.planCheck == null) {
                    //     return 'background-color: #67C23A';
                    // }
                }
            }
            if (columnIndex == 11) {
                if (row.planCheck != null && row.planCheck.checkPlanTime != null) {
                    if (this.calendarLt(row.planCheck.checkPlanTime)) {
                        return 'background-color: #F56C6C';
                    } else if (this.calendarSame(row.planCheck.checkPlanTime)) {
                        return 'background-color: #67C23A';
                    }
                    return null;
                }
            }
        },
        // 其他tab中的数据操作
        cellStyleTab({ row, columnIndex }) {
            if (columnIndex == 3) {
                if (row.planGather != null) {
                    if (row.planGather.planTime != null && row.planGather.actualTime == null) {
                        if (this.calendarLt(row.planGather.planTime)) {
                            return 'background-color: #F56C6C';
                        } else if (this.calendarSame(row.planGather.planTime)) {
                            return 'background-color: #67C23A';
                        }
                        return null;
                    }
                    //  else if (row.planGather.actualTime != null && row.planGather.gatherPlanTime == null) {
                    //     if (this.calendarLt(row.planGather.planTime)) {
                    //         return 'background-color: #F56C6C';
                    //     }
                    //     return 'background-color: #67C23A';
                    // }
                }
            }
            if (columnIndex == 7) {
                if (row.planAuth != null) {
                    if (row.planAuth.authPlanTime != null && row.planAuth.authActualTime == null) {
                        if (this.calendarLt(row.planAuth.authPlanTime)) {
                            return 'background-color: #F56C6C';
                        } else if (this.calendarSame(row.planAuth.authPlanTime)) {
                            return 'background-color: #67C23A';
                        }
                        return null;
                    }
                    //  else if (row.planAuth.authActualTime != null && row.planCheck == null) {
                    //     return 'background-color: #67C23A';
                    // }
                }
            }
            if (columnIndex == 10) {
                if (row.planCheck != null && row.planCheck.checkPlanTime != null) {
                    if (this.calendarLt(row.planCheck.checkPlanTime)) {
                        return 'background-color: #F56C6C';
                    } else if (this.calendarSame(row.planCheck.checkPlanTime)) {
                        return 'background-color: #67C23A';
                    }
                    return null;
                }
            }
        },
        // 表头颜色
        tableHeaderColor({ row, rowIndex, columnIndex }) {
            if (rowIndex === 0 && columnIndex === 3) {
                return 'background-color: #20D7A0;text-align:center;font-size:16;color:black;';
            } else if (rowIndex === 0 && columnIndex === 4) {
                return 'background-color: #ffff66;text-align:center;font-size:16;color:black;';
            } else if (rowIndex === 0 && columnIndex === 5) {
                return 'background-color: #AD89A7;text-align:center;font-size:16;color:black;';
            }
            if (rowIndex === 1 && columnIndex === 0) {
                return 'background-color: #20D7A0;text-align:center;font-size:16;color:black;';
            } else if (rowIndex === 1 && columnIndex === 1) {
                return 'background-color: #20D7A0;text-align:center;font-size:16;color:black;';
            } else if (rowIndex === 1 && columnIndex === 2) {
                return 'background-color: #20D7A0;text-align:center;font-size:16;color:black;';
            } else if (rowIndex === 1 && columnIndex === 3) {
                return 'background-color: #20D7A0;text-align:center;font-size:16;color:black;';
            } else if (rowIndex === 1 && columnIndex === 4) {
                return 'background-color: #20D7A0;text-align:center;font-size:16;color:black;';
            } else if (rowIndex === 1 && columnIndex === 5) {
                return 'background-color: #ffff66;text-align:center;font-size:16;color:black;';
            } else if (rowIndex === 1 && columnIndex === 6) {
                return 'background-color: #ffff66;text-align:center;font-size:16;color:black;';
            } else if (rowIndex === 1 && columnIndex === 7) {
                return 'background-color: #ffff66;text-align:center;font-size:16;color:black;';
            } else if (rowIndex === 1 && columnIndex === 8) {
                return 'background-color: #AD89A7;text-align:center;font-size:16;color:black;';
            } else if (rowIndex === 1 && columnIndex === 9) {
                return 'background-color: #AD89A7;text-align:center;font-size:16;color:black;';
            } else if (rowIndex === 1 && columnIndex === 10) {
                return 'background-color: #AD89A7;text-align:center;font-size:16;color:black;';
            }
        },
        tabTableHeaderColor({ row, rowIndex, columnIndex }) {
            if (rowIndex === 0 && columnIndex === 2) {
                return 'background-color: #20D7A0;text-align:center;font-size:16;color:black;';
            } else if (rowIndex === 0 && columnIndex === 3) {
                return 'background-color: #ffff66;text-align:center;font-size:16;color:black;';
            } else if (rowIndex === 0 && columnIndex === 4) {
                return 'background-color: #AD89A7;text-align:center;font-size:16;color:black;';
            }
            if (rowIndex === 1 && columnIndex === 0) {
                return 'background-color: #20D7A0;text-align:center;font-size:16;color:black;';
            } else if (rowIndex === 1 && columnIndex === 1) {
                return 'background-color: #20D7A0;text-align:center;font-size:16;color:black;';
            } else if (rowIndex === 1 && columnIndex === 2) {
                return 'background-color: #20D7A0;text-align:center;font-size:16;color:black;';
            } else if (rowIndex === 1 && columnIndex === 3) {
                return 'background-color: #20D7A0;text-align:center;font-size:16;color:black;';
            } else if (rowIndex === 1 && columnIndex === 4) {
                return 'background-color: #20D7A0;text-align:center;font-size:16;color:black;';
            } else if (rowIndex === 1 && columnIndex === 5) {
                return 'background-color: #ffff66;text-align:center;font-size:16;color:black;';
            } else if (rowIndex === 1 && columnIndex === 6) {
                return 'background-color: #ffff66;text-align:center;font-size:16;color:black;';
            } else if (rowIndex === 1 && columnIndex === 7) {
                return 'background-color: #ffff66;text-align:center;font-size:16;color:black;';
            } else if (rowIndex === 1 && columnIndex === 8) {
                return 'background-color: #AD89A7;text-align:center;font-size:16;color:black;';
            } else if (rowIndex === 1 && columnIndex === 9) {
                return 'background-color: #AD89A7;text-align:center;font-size:16;color:black;';
            } else if (rowIndex === 1 && columnIndex === 10) {
                return 'background-color: #AD89A7;text-align:center;font-size:16;color:black;';
            }
        },
        // 日期判断 超过红色，当天绿色，其他无色
        calendarLt(str) {
            var date = new Date(str);
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            var day = date.getDate();

            var calendar = new Date();
            var calYear = calendar.getFullYear();
            var calMonth = calendar.getMonth() + 1;
            var calDay = calendar.getDate();

            if (year < calYear) {
                return true;
            } else if (year == calYear && month < calMonth) {
                return true;
            } else if (year == calYear && month == calMonth && day < calDay) {
                return true;
            }

            // console.log(year + '年' + month + '月' + day + '日 ');
            return false;
        },
        calendarSame(str) {
            var date = new Date(str);
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            var day = date.getDate();

            var calendar = new Date();
            var calYear = calendar.getFullYear();
            var calMonth = calendar.getMonth() + 1;
            var calDay = calendar.getDate();

            // if (year < calYear) {
            //     return true;
            // } else if (year == calYear && month < calMonth) {
            //     return true;
            // } else if (year == calYear && month == calMonth && day < calDay) {
            //     return true;
            // }
            return year == calYear && month == calMonth && day == calDay;
        }
    }
};
</script>

<style>
.num {
    text-align: center;
    font-weight: bolder;
    color: #67c23a;
}
.title {
    text-align: center;
    font-size: 14px;
    font-weight: bolder;
    /* color: #E6A23C; */
}
.el-scrollbar__view {
    max-height: 200px;
}
.el-card__body {
    padding: 0px;
}
.el-table .cell {
    color: black;
}
</style>
