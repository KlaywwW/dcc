<template>
    <div class="sidebar">
        <el-menu
            class="sidebar-el-menu"
            :default-active="onRoutes"
            :collapse="collapse"
            background-color="#324157"
            text-color="#bfcbd9"
            active-text-color="#20a0ff"
            unique-opened
            @select="handleSelect"
        >
            <template v-for="item in items">
                <template v-if="item.subs">
                    <el-submenu :index="item.index" :key="item.index">
                        <template slot="title">
                            <i :class="item.icon"></i>
                            <span slot="title">{{ item.title }}</span>
                        </template>
                        <template v-for="subItem in item.subs">
                            <el-submenu v-if="subItem.subs" :index="subItem.index" :key="subItem.index">
                                <template slot="title">{{ subItem.title }}</template>
                                <el-menu-item v-for="(threeItem, i) in subItem.subs" :key="i" :index="threeItem.index">{{
                                    threeItem.title
                                }}</el-menu-item>
                            </el-submenu>
                            <el-menu-item v-else :index="subItem.index" :key="subItem.index">{{ subItem.title }}</el-menu-item>
                        </template>
                    </el-submenu>
                </template>
                <template v-else>
                    <el-menu-item :index="item.index" :key="item.index">
                        <i :class="item.icon"></i>
                        <span slot="title">{{ item.title }}</span>
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
    </div>
</template>

<script>
import bus from '../common/bus';
export default {
    methods: {
        handleSelect(index) {
            let user = sessionStorage.getItem('userData');
            if (user != null) {
                console.log(index);
                if (index == '1') {
                    console.log(this.$router.options);
                    this.$router.options.routes[1].children[2].meta.title = '品管作业指导书';
                    this.$router.push({
                        path: '/docList',
                        query: {
                            param: index
                        }
                    });
                } else if (index == '2') {
                    this.$router.options.routes[1].children[2].meta.title = '流程作业指导书';
                    this.$router.push({
                        path: '/docList',
                        query: {
                            param: index
                        }
                    });
                }
                // 财务判断
                else if (index == '3') {
                    // console.log(this.$store.state.doc);
                    this.$router.options.routes[1].children[2].meta.title = '工序标准作业票';
                    this.$router.push({
                        path: '/docList',
                        query: {
                            param: index
                        }
                    });
                } else if (index == '4') {
                    this.$router.options.routes[1].children[2].meta.title = '设备操作规范';
                    this.$router.push({
                        path: '/docList',
                        query: {
                            param: index
                        }
                    });
                } else {
                    this.$router.push({
                        path: '/' + index
                    });
                }
            }
        }
    },
    data() {
        return {
            collapse: false,
            items: [
                {
                    icon: 'el-icon-lx-home',
                    index: 'dashboard',
                    title: '系统首页'
                },
                {
                    icon: 'el-icon-date',
                    index: 'process',
                    title: '认证计划'
                },
                //新增
                {
                    icon: 'el-icon-s-custom',
                    index: 'adminpanel',
                    title: '管理面板',
                    subs: [
                        {
                            index: 'authority',
                            title: '权限查看'
                        },
                        {
                            index: 'userList',
                            title: '用户列表'
                        },
                        {
                            index: 'reviewList',
                            title: '审核列表'
                        },
                        {
                            index: 'logs',
                            title: '操作记录'
                        }
                    ]
                },

                {
                    icon: 'el-icon-lx-sort',
                    index: '1',
                    title: '品管作业指导书'
                },
                {
                    icon: 'el-icon-lx-sort',
                    index: '2',
                    title: '流程作业指导书'
                },
                {
                    icon: 'el-icon-lx-sort',
                    index: '3',
                    title: '工序标准作业票'
                },
                {
                    icon: 'el-icon-lx-sort',
                    index: '4',
                    title: '设备操作规范'
                }
            ]
        };
    },
    computed: {
        onRoutes() {
            return this.$route.path.replace('/', '');
        },
        col() {
            return this.$store.state.collapse;
        }
    },
    watch: {
        // 通过监听vuex中的数据变化来折叠侧边栏
        col(newCount, oldCount) {
            this.collapse = newCount;
            bus.$emit('collapse-content', newCount);
        }
    }
};
</script>

<style scoped>
.sidebar {
    display: block;
    position: absolute;
    left: 0;
    top: 70px;
    bottom: 0;
    overflow-y: scroll;
}

.sidebar::-webkit-scrollbar {
    width: 0;
}

.sidebar-el-menu:not(.el-menu--collapse) {
    width: 250px;
}

.sidebar > ul {
    height: 100%;
}
</style>
