import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
    // 将地址栏中的#去掉
    mode:'history',
	routes: [{
			path: '/',
			redirect: '/dashboard'
		},
		{
			path: '/',
			component: () => import( /* webpackChunkName: "home" */ '../components/common/Home.vue'),
			meta: {
				title: '自述文件'
			},
			children: [
                {
					path: '/authority',
					component: () => import('../components/page/new/user/authority.vue'),
					meta: {
						title: '权限管理'
					}
				},
				{
					path: '/userList',
					component: () => import('../components/page/new/user/userList.vue'),
					meta: {
						title: '用户管理'
					}
				},
				{
					path: '/docList',
					component: () => import('../components/page/new/document/docList.vue'),
					meta: {
						title: '文件列表'
					}
				},
				{
					path: '/userCenter',
					component: () => import('../components/page/new/user/userCenter.vue'),
					meta: {
						title: '个人中心'
					}
				},

				{
					path: '/dashboard',
					component: () => import( /* webpackChunkName: "dashboard" */ '../components/page/Dashboard.vue'),
					meta: {
						title: '系统首页'
                    }
                },
				{
					path: '/404',
					component: () => import( /* webpackChunkName: "404" */ '../components/page/404.vue'),
					meta: {
						title: '404'
					}
				},
				{
					path: '/403',
					component: () => import( /* webpackChunkName: "403" */ '../components/page/403.vue'),
					meta: {
						title: '403'
					}
				},{
					path: '/showPdf',
					component: () => import('../components/page/new/document/showPdf.vue'),
					meta: {
						title: 'PDF预览'
					}
                },
                {
                    path: '/reviewList',
					component: () => import('../components/page/new/user/reviewList.vue'),
					meta: {
						title: '申请管理'
					}
                    
                },
                {
                    path: '/docHistory',
					component: () => import('../components/page/new/document/docHistory.vue'),
					meta: {
						title: '历史版本文件'
					}
                    
                },
                {
                    path: '/logs',
					component: () => import('../components/page/new/document/logs.vue'),
					meta: {
						title: '操作记录'
					}
                    
                },
                {
                    path: '/process',
					component: () => import('../components/page/new/document/process.vue'),
					meta: {
						title: '认证计划'
					}
                    
                },
                {
                    path: '/demo',
					component: () => import('../components/page/demo.vue'),
					meta: {
						title: '测试'
					}
                    
                }
			]
		},
		{
			path: '/login',
			component: () => import( /* webpackChunkName: "login" */ '../components/page/Login.vue'),
			meta: {
				title: '登录'
			}
		},
		{
			path: '*',
			redirect: '/404'
		}
	]
});
