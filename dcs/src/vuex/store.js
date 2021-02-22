import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

let state={
	doc:"",
	userData:window.sessionStorage.getItem("userData"),
	collapse:false
}
let plan=null;

export default new Vuex.Store({
	state,plan
})