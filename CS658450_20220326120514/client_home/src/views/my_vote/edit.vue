<template>
	<div class="diy_edit page_my_vote" id="my_vote_edit">
		<div class='warp'>
			<div class='container'>
				<div class='row diy_edit_content_box'>
					<div v-if="$check_field('set','user') || $check_field('add','user') || $check_field('get','user')" class="form-item col-12 col-md-6">
						<div class="diy_title">
							<span>用户:
							</span>
						</div>
						<div class="diy_field diy_down">
							<select id="form_user" :disabled="disabledObj['user_isDisabled']" v-model="form['user']" v-if="(form['user'] && $check_field('set','user')) || (!form['user'] && $check_field('add','user'))" >
								<option v-for="o in list_user_user" :value="o['user_id']">
									{{o['nickname'] + '-' + o['username']}}
								</option>
							</select>
							<span v-else-if="$check_field('get','user')">{{ form['user'] }}</span>
						</div>
					</div>
					<div v-if="$check_field('set','voting_topic') || $check_field('add','voting_topic') || $check_field('get','voting_topic')" class="form-item col-12 col-md-6">
						<div class="diy_title">
							<span>投票话题:
							</span>
						</div>
						<!-- 文本 -->
						<div class="diy_field diy_text">
							<input type="text" id="form_voting_topic" v-model="form['voting_topic']" placeholder="请输入投票话题" v-if="(form['voting_topic'] && $check_field('set','voting_topic')) || (!form['voting_topic'] && $check_field('add','voting_topic'))"  :disabled="disabledObj['voting_topic_isDisabled']"/>
							<span v-else-if="$check_field('get','voting_topic')">{{ form[''] }}</span>
						</div>
					</div>
					<div v-if="$check_field('set','voting_type') || $check_field('add','voting_type') || $check_field('get','voting_type')" class="form-item col-12 col-md-6">
						<div class="diy_title">
							<span>投票类型:
							</span>
						</div>
						<!-- 文本 -->
						<div class="diy_field diy_text">
							<input type="text" id="form_voting_type" v-model="form['voting_type']" placeholder="请输入投票类型" v-if="(form['voting_type'] && $check_field('set','voting_type')) || (!form['voting_type'] && $check_field('add','voting_type'))"  :disabled="disabledObj['voting_type_isDisabled']"/>
							<span v-else-if="$check_field('get','voting_type')">{{ form[''] }}</span>
						</div>
					</div>
					<div v-if="$check_field('set','number_of_votes') || $check_field('add','number_of_votes') || $check_field('get','number_of_votes')" class="form-item col-12 col-md-6">
						<div class="diy_title">
							<span>投票数:
							</span>
						</div>
						<!-- 选项 -->
						<div class="diy_field diy_down">
							<select id="form_number_of_votes" v-model="form['number_of_votes']" v-if="(form['number_of_votes'] && $check_field('set','number_of_votes')) || (!form['number_of_votes'] && $check_field('add','number_of_votes'))" >
								<option v-for="o in list_number_of_votes" :value="o">
									{{o}}
								</option>
							</select>
							<span v-else-if="$check_field('get','number_of_votes')">{{ form['number_of_votes'] }}</span>
						</div>
					</div>
					<div v-if="$check_field('set','voting_instructions') || $check_field('add','voting_instructions') || $check_field('get','voting_instructions')" class="form-item col-12 col-md-6">
						<div class="diy_title">
							<span>投票说明:
							</span>
						</div>
						<!-- 多文本 -->
						<div class="diy_field diy_desc">
							<textarea id="form_voting_instructions" v-model="form['voting_instructions']" v-if="(form['voting_instructions'] && $check_field('set','voting_instructions')) || (!form['voting_instructions'] && $check_field('add','voting_instructions'))" :disabled="disabledObj['voting_instructions_isDisabled']" />
							<span v-else-if="$check_field('get','voting_instructions')">{{ form[''] }}</span>
						</div>
					</div>
				</div>
				<div class="diy_edit_submit_box row">
					<div class="col-12">
						<div class="btn_box">
							<button class="btn_submit" @click="submit()">提交</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	import mixin from "@/mixins/page.js";
	export default {
		mixins: [mixin],
		components: {},
		data() {
			return {
				url_get_obj: "~/api/my_vote/get_obj?",
				url_add: "~/api/my_vote/add?",
				url_set: "~/api/my_vote/set?",

				// 登录权限
				oauth: {
					"signIn": true,
					"user_group": []
				},

				// 查询条件
				query: {
					"user": 0,
					"voting_topic": "",
					"voting_type": "",
					"number_of_votes": "",
					"voting_instructions": "",
					"my_vote_id": 0,
				},

				obj: {
					"user": 0, // 用户
					"voting_topic":'', // 投票话题
					"voting_type":'', // 投票类型
					"number_of_votes":'', // 投票数
					"voting_instructions":'', // 投票说明
					"my_vote_id": 0,
				},

				// 表单字段
				form: {
					"user": 0, // 用户
					"voting_topic":'', // 投票话题
					"voting_type":'', // 投票类型
					"number_of_votes":'', // 投票数
					"voting_instructions":'', // 投票说明
					"my_vote_id": 0,

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
				//投票数选项列表
				list_number_of_votes: ['1'],

				// ID字段
				field: "my_vote_id",
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
			async get_user_session_user(){
				var _this = this;
				var json = await this.$get("~/api/user_group/get_obj?name=普通用户");
				if(json.result && json.result.obj){
					var source_table = json.result.obj.source_table;
					var user_id = _this.$store.state.user.user_id;
					if (user_id){
						var url = "~/api/"+source_table+"/get_obj?"
						this.$get(url, {"user_id":_this.$store.state.user.user_id}, function(res) {
							if (res.result && res.result.obj) {
								var arr = []
								for (let key in res.result.obj) {
									arr.push(key)
								}
								var arrForm = []
								for (let key in _this.form) {
									arrForm.push(key)
								}
								_this.form["user"] = user_id
								_this.disabledObj['user' + '_isDisabled'] = true
								for (var i=0;i<arr.length;i++){
									for (var j=0;j<arrForm.length;j++){
										if (arr[i]===arrForm[j]){
											if (arr[i]!=="user") {
												_this.form[arrForm[j]] = res.result.obj[arr[i]]
												_this.disabledObj[arrForm[j] + '_isDisabled'] = true
												break;
											}
										}
									}
								}
							}
						});
					}
				}
				else if(json.error){
					console.error(json.error);
				}
			},

			/**
			 * 修改文件
			 * @param { Object } files 上传文件对象
			 * @param { String } str 表单的属性名
			 */
			change_file(files, str) {
				var form = new FormData();
				form.append("file", files[0]);
				this.$post("~/api/my_vote/upload?", form, (res) => {
					if (res.result) {
						this.form[str] = res.result.url;
					} else if (res.error) {
						this.$toast(res.error.message);
					}
				});
			},

			/**
			 * 获取对象后获取缓存表单
			 * @param {Object} json
			 * @param {Object} func
			 */
			get_obj_before(param){
				var form = $.db.get("form");
				if (form) {
					this.obj = $.push(this.obj ,form);
					this.form = $.push(this.form ,form);
				}
				var arr = []
				for (let key in form) {
					arr.push(key)
				}
				for (var i=0;i<arr.length;i++){
					this.disabledObj[arr[i] + '_isDisabled'] = true
				}
				return param;
			},

			/**
			 * 获取对象后获取缓存表单
			 * @param {Object} json
			 * @param {Object} func
			 */
			get_obj_after(json ,func){
				var form = $.db.get("form");
				var obj = Object.assign({} ,form ,this.obj);
				if (form) {
					this.obj = $.push(this.obj ,obj);
				}
				if (form) {
					this.form = $.push(this.form ,form);
				}
				if(func){
					func(json);
				}
			},

		},
		created() {
			this.get_user_session_user();
			this.get_list_user_user();
		}
	}
</script>

<style>
</style>
