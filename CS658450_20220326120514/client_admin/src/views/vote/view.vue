<template>
	<el-main class="bg">
		<el-form ref="form" :model="form" status-icon label-width="120px" v-if="is_view()">
			<el-col v-if="user_group === '管理员' || $check_field('get','voting_topic') || $check_field('add','voting_topic') || $check_field('set','voting_topic')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="投票话题" prop="voting_topic">
					<el-input id="voting_topic" v-model="form['voting_topic']" placeholder="请输入投票话题"
							  v-if="user_group === '管理员' || (form['vote_id'] && $check_field('set','voting_topic')) || (!form['vote_id'] && $check_field('add','voting_topic'))" :disabled="disabledObj['voting_topic_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','voting_topic')">{{form['voting_topic']}}</div>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','voting_type') || $check_field('add','voting_type') || $check_field('set','voting_type')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="投票类型" prop="voting_type">
					<el-input id="voting_type" v-model="form['voting_type']" placeholder="请输入投票类型"
							  v-if="user_group === '管理员' || (form['vote_id'] && $check_field('set','voting_type')) || (!form['vote_id'] && $check_field('add','voting_type'))" :disabled="disabledObj['voting_type_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','voting_type')">{{form['voting_type']}}</div>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','remarks') || $check_field('add','remarks') || $check_field('set','remarks')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="备注" prop="remarks">
					<el-input id="remarks" v-model="form['remarks']" placeholder="请输入备注"
							  v-if="user_group === '管理员' || (form['vote_id'] && $check_field('set','remarks')) || (!form['vote_id'] && $check_field('add','remarks'))" :disabled="disabledObj['remarks_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','remarks')">{{form['remarks']}}</div>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','voting_cover') || $check_field('add','voting_cover') || $check_field('set','voting_cover')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="投票封面" prop="voting_cover">
					<el-upload :disabled="disabledObj['voting_cover_isDisabled']" id="voting_cover" class="avatar-uploader" drag
						accept="image/gif, image/jpeg, image/png, image/jpg" action="" :http-request="upload_voting_cover"
						:show-file-list="false" v-if="user_group === '管理员' || (form['vote_id'] && $check_field('set','voting_cover')) || (!form['vote_id'] && $check_field('add','voting_cover'))">
						<img v-if="form['voting_cover']" :src="$fullUrl(form['voting_cover'])" class="avatar">
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
					<el-image v-else-if="$check_field('get','voting_cover')" style="width: 100px; height: 100px"
						:src="$fullUrl(form['voting_cover'])" :preview-src-list="[$fullUrl(form['voting_cover'])]">
						<div slot="error" class="image-slot">
							<img src="../../../public/img/error.png" style="width: 90px; height: 90px" />
						</div>
					</el-image>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','votes_') || $check_field('add','votes_') || $check_field('set','votes_')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="总票数" prop="votes_">
					<el-input-number id="votes_" v-model.number="form['votes_']"
						v-if="user_group === '管理员' || (form['vote_id'] && $check_field('set','votes_')) || (!form['vote_id'] && $check_field('add','votes_'))"></el-input-number>
					<div v-else-if="$check_field('get','votes_')">{{form['votes_']}}</div>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','topic_introduction') || $check_field('add','topic_introduction') || $check_field('set','topic_introduction')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="话题介绍" prop="topic_introduction">
					<el-input type="textarea" id="topic_introduction" v-model="form['topic_introduction']" placeholder="请输入话题介绍"
						v-if="user_group === '管理员' || (form['vote_id'] && $check_field('set','topic_introduction')) || (!form['vote_id'] && $check_field('add','topic_introduction'))" :disabled="disabledObj['topic_introduction_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','topic_introduction')">{{form['topic_introduction']}}</div>
				</el-form-item>
			</el-col>
			<el-col v-if="user_group === '管理员' || $check_field('get','voting_instructions') || $check_field('add','voting_instructions') || $check_field('set','voting_instructions')" :xs="24" :sm="12" :lg="8">
				<el-form-item label="投票说明" prop="voting_instructions">
					<el-input type="textarea" id="voting_instructions" v-model="form['voting_instructions']" placeholder="请输入投票说明"
						v-if="user_group === '管理员' || (form['vote_id'] && $check_field('set','voting_instructions')) || (!form['vote_id'] && $check_field('add','voting_instructions'))" :disabled="disabledObj['voting_instructions_isDisabled']"></el-input>
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
				field: "vote_id",
				url_add: "~/api/vote/add?",
				url_set: "~/api/vote/set?",
				url_get_obj: "~/api/vote/get_obj?",
				url_upload: "~/api/vote/upload?",

				query: {
					"vote_id": 0,
				},

				form: {
					"voting_topic":'', // 投票话题
					"voting_type":'', // 投票类型
					"remarks":'', // 备注
					"voting_cover":'', // 投票封面
					"votes_":0, // 总票数
					"topic_introduction":'', // 话题介绍
					"voting_instructions":'', // 投票说明
					"vote_id": 0, // ID

				},
				disabledObj:{
					"voting_topic_isDisabled": false,
					"voting_type_isDisabled": false,
					"remarks_isDisabled": false,
					"voting_cover_isDisabled": false,
					"topic_introduction_isDisabled": false,
					"voting_instructions_isDisabled": false,
				},

			}
		},
		methods: {
			/**
			 * 上传投票封面
			 * @param {Object} param图片参数
			 */
			upload_voting_cover(param){
				this.uploadFile(param.file, "voting_cover");
			},

			/**
			 * 获取对象之前
			 * @param {Object} param
			 */
			get_obj_before(param) {
				var form = "";
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
					bl = this.$check_action('/vote/table','add');
					console.log(bl ? "你有表格添加权限视作有添加权限" : "你没有表格添加权限");
				}
				if(!bl){
					bl = this.$check_action('/vote/table','set');
					console.log(bl ? "你有表格添加权限视作有修改权限" : "你没有表格修改权限");
				}
				if(!bl){
					bl = this.$check_action('/vote/view','add');
					console.log(bl ? "你有视图添加权限视作有添加权限" : "你没有视图添加权限");
				}
				if(!bl){
					bl = this.$check_action('/vote/view','set');
					console.log(bl ? "你有视图修改权限视作有修改权限" : "你没有视图修改权限");
				}
				if(!bl){
					bl = this.$check_action('/vote/view','get');
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
