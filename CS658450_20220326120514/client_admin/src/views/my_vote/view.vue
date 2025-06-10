<template>
	<el-main class="bg">
		<el-form ref="form" :model="form" status-icon label-width="120px" v-if="is_view()">
			<el-col v-if="user_group === '管理员' || $check_field('get','user') || $check_field('add','user') || $check_field('set','user')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="用户" prop="user">
						<div v-if="user_group !== '管理员'">
							{{ get_user_session_user(form['user']) }}
							<!--<el-input id="business_name" v-model="form['user']" placeholder="请输入用户"-->
							<!--v-if="user_group === '管理员' || (form['my_vote_id'] && $check_field('set','user')) || (!form['my_vote_id'] && $check_field('add','user'))" :disabled="disabledObj['user_isDisabled']"></el-input>-->
							<!--<div v-else-if="$check_field('get','user')">{{form['user']}}</div>-->
							<el-select v-if="user_group === '管理员' || (form['my_vote_id'] && $check_field('set','user')) || (!form['my_vote_id'] && $check_field('add','user'))" id="user" v-model="form['user']" :disabled="disabledObj['user_isDisabled']">
								<el-option v-for="o in list_user_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
										   :value="o['user_id']">
								</el-option>
							</el-select>
							<el-select v-else-if="$check_field('get','user')" id="user" v-model="form['user']" :disabled="true">
								<el-option v-for="o in list_user_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
										   :value="o['user_id']">
								</el-option>
							</el-select>
						</div>
						<el-select v-else id="user" v-model="form['user']" :disabled="disabledObj['user_isDisabled']">
							<el-option v-for="o in list_user_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','voting_topic') || $check_field('add','voting_topic') || $check_field('set','voting_topic')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="投票话题" prop="voting_topic">
					<el-input id="voting_topic" v-model="form['voting_topic']" placeholder="请输入投票话题"
							  v-if="user_group === '管理员' || (form['my_vote_id'] && $check_field('set','voting_topic')) || (!form['my_vote_id'] && $check_field('add','voting_topic'))" :disabled="disabledObj['voting_topic_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','voting_topic')">{{form['voting_topic']}}</div>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','voting_type') || $check_field('add','voting_type') || $check_field('set','voting_type')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="投票类型" prop="voting_type">
					<el-input id="voting_type" v-model="form['voting_type']" placeholder="请输入投票类型"
							  v-if="user_group === '管理员' || (form['my_vote_id'] && $check_field('set','voting_type')) || (!form['my_vote_id'] && $check_field('add','voting_type'))" :disabled="disabledObj['voting_type_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','voting_type')">{{form['voting_type']}}</div>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','number_of_votes') || $check_field('add','number_of_votes') || $check_field('set','number_of_votes')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="投票数" prop="number_of_votes">
					<el-select id="number_of_votes" v-model="form['number_of_votes']"
						v-if="user_group === '管理员' || (form['my_vote_id'] && $check_field('set','number_of_votes')) || (!form['my_vote_id'] && $check_field('add','number_of_votes'))">
						<el-option v-for="o in list_number_of_votes" :key="o" :label="o" :value="o">
						</el-option>
					</el-select>
					<div v-else-if="$check_field('get','number_of_votes')">{{form['number_of_votes']}}</div>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','voting_instructions') || $check_field('add','voting_instructions') || $check_field('set','voting_instructions')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="投票说明" prop="voting_instructions">
					<el-input type="textarea" id="voting_instructions" v-model="form['voting_instructions']" placeholder="请输入投票说明"
						v-if="user_group === '管理员' || (form['my_vote_id'] && $check_field('set','voting_instructions')) || (!form['my_vote_id'] && $check_field('add','voting_instructions'))" :disabled="disabledObj['voting_instructions_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','voting_instructions')">{{form['voting_instructions']}}</div>
				</el-form-item>
			</el-col>
			<el-col :xs="24" :sm="12" :lg="8">
				<el-form-item>
					<el-button type="primary" @click="submit()">提交</el-button>
					<el-button @click="cancel()">取消</el-button>
				</el-form-item>
			</el-col>

		</el-form>
	</el-main>
</template>

<script>
	import mixin from "@/mixins/page.js";

	export default {
		mixins: [mixin],
		data() {
			return {
				field: "my_vote_id",
				url_add: "~/api/my_vote/add?",
				url_set: "~/api/my_vote/set?",
				url_get_obj: "~/api/my_vote/get_obj?",
				url_upload: "~/api/my_vote/upload?",

				query: {
					"my_vote_id": 0,
				},

				form: {
					"user": 0, // 用户
					"voting_topic":'', // 投票话题
					"voting_type":'', // 投票类型
					"number_of_votes":'', // 投票数
					"voting_instructions":'', // 投票说明
					"my_vote_id": 0, // ID

				},
				disabledObj:{
					"user_isDisabled": false,
					"voting_topic_isDisabled": false,
					"voting_type_isDisabled": false,
					"number_of_votes_isDisabled": false,
					"voting_instructions_isDisabled": false,
				},
				// 用户列表
				list_user_user: [],
				// 用户组
				group_user_user: "",
				//投票数选项列表
				list_number_of_votes: ['1'],

			}
		},
		methods: {
			/**
			 * 获取普通用户用户列表
			 */
			async get_list_user_user() {
                // if(this.user_group !== "管理员" && this.form["user"] === 0) {
                //     this.form["user"] = this.user.user_id;
                // }
                var json = await this.$get("~/api/user/get_list?user_group=普通用户");
                if(json.result && json.result.list){
                    this.list_user_user = json.result.list;
                }
                else if(json.error){
                    console.error(json.error);
                }
			},
			/**
			 * 获取普通用户用户组
			 */
			async get_group_user_user() {
				this.form["user"] = this.user.user_id;
				var json = await this.$get("~/api/user_group/get_obj?name=普通用户");
				if(json.result && json.result.obj){
					this.group_user_user = json.result.obj;
				}
				else if(json.error){
					console.error(json.error);
				}
			},
			get_user_session_user(id){
				var _this = this;
				var user_id = {"user_id":id}
				var url = "~/api/"+_this.group_user_user.source_table+"/get_obj?"
				this.$get(url, user_id, function(res) {
					if (res.result && res.result.obj) {
						var arr = []
						for (let key in res.result.obj) {
							arr.push(key)
						}
						var arrForm = []
						for (let key in _this.form) {
							arrForm.push(key)
						}
						for (var i=0;i<arr.length;i++){
							for (var j=0;j<arrForm.length;j++){
								if (arr[i]===arrForm[j]){
									if (arr[i]!=="user") {
										_this.form[arrForm[j]] = res.result.obj[arr[i]]
										_this.disabledObj[arrForm[j] + '_isDisabled'] = true
										break;
									}else {
										_this.disabledObj[arrForm[j] + '_isDisabled'] = true
									}
								}
							}
						}
					}
				});
			},
			get_user_user(id){
				var obj = this.list_user_user.getObj({"user_id":id});
				var ret = "";
				if(obj){
					if(obj.nickname){
						ret = obj.nickname;}
					else{
						ret = obj.username;
					}
				}
				return ret;
			},

			/**
			 * 获取对象之前
			 * @param {Object} param
			 */
			get_obj_before(param) {
				var form = "";
				// 获取缓存数据附加
				form = $.db.get("form");
				$.push(this.form ,form);
				/**
				* 请求列表前
				* @param {Object} param
				*/
				var user_group = this.user.user_group;
				if (user_group !== "管理员") {
					switch (user_group) {
						case "用户":
							if(param["user"] > 0){
								param["user"] = this.user.user_id;
							}
							break;
					}
				}
				if(this.form && form){
					Object.keys(this.form).forEach(key => {
						Object.keys(form).forEach(dbKey => {
							// if(dbKey === "charging_standard"){
							// 	this.form['charging_rules'] = form[dbKey];
							// 	this.disabledObj['charging_rules_isDisabled'] = true;
							// };
							if(key === dbKey){
								this.disabledObj[key+'_isDisabled'] = true;
							}
						})
					})
				}
				$.db.del("form");
				return param;
			},

			/**
			 * 获取对象之后
			 * @param {Object} json
			 * @param {Object} func
			 */
			get_obj_after(json, func){

			},

			is_view(){
				var bl = this.user_group == "管理员";

				if(!bl){
					bl = this.$check_action('/my_vote/table','add');
					console.log(bl ? "你有表格添加权限视作有添加权限" : "你没有表格添加权限");
				}
				if(!bl){
					bl = this.$check_action('/my_vote/table','set');
					console.log(bl ? "你有表格添加权限视作有修改权限" : "你没有表格修改权限");
				}
				if(!bl){
					bl = this.$check_action('/my_vote/view','add');
					console.log(bl ? "你有视图添加权限视作有添加权限" : "你没有视图添加权限");
				}
				if(!bl){
					bl = this.$check_action('/my_vote/view','set');
					console.log(bl ? "你有视图修改权限视作有修改权限" : "你没有视图修改权限");
				}
				if(!bl){
					bl = this.$check_action('/my_vote/view','get');
					console.log(bl ? "你有视图查询权限视作有查询权限" : "你没有视图查询权限");
				}

				console.log(bl ? "具有当前页面的查看权，请注意这不代表你有字段的查看权" : "无权查看当前页，请注意即便有字段查询权限没有页面查询权限也不行");

				return bl;
			},
			/**
			 * 上传文件
			 * @param {Object} param
			 */
			uploadimg(param) {
				this.uploadFile(param.file, "avatar");
			},

		},
		created() {
			this.get_list_user_user();
			this.get_group_user_user();
		}
	}
</script>

<style>
	.avatar-uploader .el-upload {
		border: 1px dashed #d9d9d9;
		border-radius: 6px;
		cursor: pointer;
		position: relative;
		overflow: hidden;
	}

	.avatar-uploader .el-upload:hover {
		border-color: #409EFF;
	}

	.avatar-uploader-icon {
		font-size: 28px;
		color: #8c939d;
		width: 178px;
		height: 178px;
		line-height: 178px;
		text-align: center;
	}

	.avatar {
		width: 178px;
		height: 178px;
		display: block;
	}
</style>
