module.exports = {
	baseUrl: './',
	assetsDir: 'static',
	productionSourceMap: false,
	// devServer: {
	// 	port: 3000// 端口
	// }
	devServer: {
	    proxy: {
	        '/api':{
				target:'http://192.168.123.86:8099',
	            changeOrigin:true,
	            pathRewrite:{
	                '^/api':''
	            }
	        }
	    }
	}
}
