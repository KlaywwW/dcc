module.exports = {
	baseUrl: './',
	assetsDir: 'static',
	productionSourceMap: false,
	// devServer: {
        
	// },
    // disableHostCheck: true,
	devServer: {
        port: 8083,// 端口
        disableHostCheck: true,
	    proxy: {
	        '/api':{
                // target:'http://192.168.123.51:8099',
                target:'http://192.168.123.86:8099',
	            changeOrigin:true,
	            pathRewrite:{
	                '^/api':''
	            }
	        }
        },
	}
}
