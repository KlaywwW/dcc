import Vue from 'vue';
import App from './App.vue';
import router from './router';
import axios from 'axios';
import store from './vuex/store'
import ElementUI from 'element-ui';

import 'xe-utils';
import VXETable from 'vxe-table';
import 'vxe-table/lib/style.css';

import 'element-ui/lib/theme-chalk/index.css'; // 默认主题
// import './assets/css/theme-green/index.css'; // 浅绿色主题
import './assets/css/icon.css';
import './components/common/directives';
import 'babel-polyfill';

Vue.use(VXETable);
Vue.prototype.$XModal = VXETable.modal;
Vue.prototype.$XPrint = VXETable.print;
Vue.prototype.$XReadFile = VXETable.readFile;

Vue.prototype.$axios = axios;
//允许携带cookie
axios.default.withCredentials = true;

Vue.config.productionTip = false;

Vue.use(ElementUI, {
	size: 'small'
});


//使用钩子函数对路由进行权限跳转
router.beforeEach((to, from, next) => {
	document.title = `${to.meta.title} | dcc`;
	let userData = sessionStorage.getItem("userData");
	let role = false;
	if (userData != null) {
		role = JSON.parse(sessionStorage.getItem('userData')).role[0].roles.roleCode;
    }
    
    // next();
	console.log("----star----")
	console.log(role);
	console.log(to)
	console.log(to.meta.permission);
    console.log("----end----");
    console.log(!role && to.path !== '/login')
	// 在没有登录时无法去到任何组件
	if (!role && to.path !== '/login') {
		console.log("next:/login")
		next('/login');
	}else {
		// 简单的判断IE10及以下不进入富文本编辑器，该组件不兼容
		if (navigator.userAgent.indexOf('MSIE') > -1 && to.path === '/editor') {
			Vue.prototype.$alert('vue-quill-editor组件不兼容IE10及以下浏览器，请使用更高版本的浏览器查看', '浏览器不兼容通知', {
				confirmButtonText: '确定'
			});
		} else {
            // if(){

            // }
			next();
		}
	}
});

new Vue({
	router,
	render: h => h(App),
	store
}).$mount('#app');
