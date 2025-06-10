<template>
  <div class="page_search">
	<div class="warp">
	  <div class="container">
		<div class="row">
		  <div class="col-12">
			<div class="card_result_search">
			  <div class="title">搜索结果</div>
				<!-- 文章搜索结果 -->
			  <list_result_search
				:list="result_article"
				title="新闻资讯"
				source_table="article"
			  ></list_result_search>
			  <list_result_search
				v-if="$check_action('/ordinary_users/list', 'get')"
				:list="result_ordinary_users_full_name"
				title="普通用户姓名"
				source_table="ordinary_users"
			  ></list_result_search>
			  <list_result_search
				v-if="$check_action('/vote/list', 'get')"
				:list="result_vote_voting_topic"
				title="参与投票投票话题"
				source_table="vote"
			  ></list_result_search>
			  <list_result_search
				v-if="$check_action('/vote/list', 'get')"
				:list="result_vote_voting_type"
				title="参与投票投票类型"
				source_table="vote"
			  ></list_result_search>
			  <list_result_search
				v-if="$check_action('/my_vote/list', 'get')"
				:list="result_my_vote_voting_topic"
				title="我的投票投票话题"
				source_table="my_vote"
			  ></list_result_search>
			  <list_result_search
				v-if="$check_action('/my_vote/list', 'get')"
				:list="result_my_vote_voting_type"
				title="我的投票投票类型"
				source_table="my_vote"
			  ></list_result_search>
			  <list_result_search
				v-if="$check_action('/cancel_voting/list', 'get')"
				:list="result_cancel_voting_voting_topic"
				title="取消投票投票话题"
				source_table="cancel_voting"
			  ></list_result_search>
			  <list_result_search
				v-if="$check_action('/cancel_voting/list', 'get')"
				:list="result_cancel_voting_voting_type"
				title="取消投票投票类型"
				source_table="cancel_voting"
			  ></list_result_search>
			</div>
		  </div>
		</div>
	  </div>
	</div>
  </div>
</template>

<script>
import mixin from "../../mixins/page.js";
import list_result_search from "../../components/diy/list_result_search.vue";

export default {
  mixins: [mixin],
  data() {
	return {
	  "query": {
		word: "",
	  },
	  "result_article": [],
			"result_ordinary_users_full_name":[],
			"result_vote_voting_topic":[],
			"result_vote_voting_type":[],
			"result_my_vote_voting_topic":[],
			"result_my_vote_voting_type":[],
			"result_cancel_voting_voting_topic":[],
			"result_cancel_voting_voting_type":[],
	};
  },
  methods: {
	/**
	 * 获取文章
	 */
	get_article() {
	  this.$get("~/api/article/get_list?like=0", { page: 1, size: 10, title: this.query.word }, (json) => {
		if (json.result) {
		  this.result_article = json.result.list;
		}
	  });
	},
	/**
	 * 获取full_name
	 */
	get_ordinary_users_full_name(){
		this.$get("~/api/ordinary_users/get_list?like=0", { page: 1, size: 10, "full_name": this.query.word }, (json) => {
		  if (json.result) {
			var result_ordinary_users_full_name = json.result.list;
			result_ordinary_users_full_name.map(o => o.title = o['full_name'])
	  			this.result_ordinary_users_full_name = result_ordinary_users_full_name
		 	}
		});
	},
	/**
	 * 获取voting_topic
	 */
	get_vote_voting_topic(){
		this.$get("~/api/vote/get_list?like=0", { page: 1, size: 10, "voting_topic": this.query.word }, (json) => {
		  if (json.result) {
			var result_vote_voting_topic = json.result.list;
			result_vote_voting_topic.map(o => o.title = o['voting_topic'])
	  			this.result_vote_voting_topic = result_vote_voting_topic
		 	}
		});
	},
	/**
	 * 获取voting_type
	 */
	get_vote_voting_type(){
		this.$get("~/api/vote/get_list?like=0", { page: 1, size: 10, "voting_type": this.query.word }, (json) => {
		  if (json.result) {
			var result_vote_voting_type = json.result.list;
			result_vote_voting_type.map(o => o.title = o['voting_type'])
	  			this.result_vote_voting_type = result_vote_voting_type
		 	}
		});
	},
	/**
	 * 获取voting_topic
	 */
	get_my_vote_voting_topic(){
		this.$get("~/api/my_vote/get_list?like=0", { page: 1, size: 10, "voting_topic": this.query.word }, (json) => {
		  if (json.result) {
			var result_my_vote_voting_topic = json.result.list;
			result_my_vote_voting_topic.map(o => o.title = o['voting_topic'])
	  			this.result_my_vote_voting_topic = result_my_vote_voting_topic
		 	}
		});
	},
	/**
	 * 获取voting_type
	 */
	get_my_vote_voting_type(){
		this.$get("~/api/my_vote/get_list?like=0", { page: 1, size: 10, "voting_type": this.query.word }, (json) => {
		  if (json.result) {
			var result_my_vote_voting_type = json.result.list;
			result_my_vote_voting_type.map(o => o.title = o['voting_type'])
	  			this.result_my_vote_voting_type = result_my_vote_voting_type
		 	}
		});
	},
	/**
	 * 获取voting_topic
	 */
	get_cancel_voting_voting_topic(){
		this.$get("~/api/cancel_voting/get_list?like=0", { page: 1, size: 10, "voting_topic": this.query.word }, (json) => {
		  if (json.result) {
			var result_cancel_voting_voting_topic = json.result.list;
			result_cancel_voting_voting_topic.map(o => o.title = o['voting_topic'])
	  			this.result_cancel_voting_voting_topic = result_cancel_voting_voting_topic
		 	}
		});
	},
	/**
	 * 获取voting_type
	 */
	get_cancel_voting_voting_type(){
		this.$get("~/api/cancel_voting/get_list?like=0", { page: 1, size: 10, "voting_type": this.query.word }, (json) => {
		  if (json.result) {
			var result_cancel_voting_voting_type = json.result.list;
			result_cancel_voting_voting_type.map(o => o.title = o['voting_type'])
	  			this.result_cancel_voting_voting_type = result_cancel_voting_voting_type
		 	}
		});
	},

  },
  components: { list_result_search },
	created(){
    this.query.word = this.$route.query.word || "";
  },
  mounted() {
	this.get_article();
		this.get_ordinary_users_full_name();
		this.get_vote_voting_topic();
		this.get_vote_voting_type();
		this.get_my_vote_voting_topic();
		this.get_my_vote_voting_type();
		this.get_cancel_voting_voting_topic();
		this.get_cancel_voting_voting_type();
  },
  watch: {
	$route() {
	  $.push(this.query, this.$route.query);
	  this.get_article();
	  this.get_ordinary_users_full_name();
	  this.get_vote_voting_topic();
	  this.get_vote_voting_type();
	  this.get_my_vote_voting_topic();
	  this.get_my_vote_voting_type();
	  this.get_cancel_voting_voting_topic();
	  this.get_cancel_voting_voting_type();
	},
  },
};
</script>

<style scoped>
.card_search {
  text-align: center;
}
.card_result_search>.title {
  text-align: center;
  padding: 10px 0;
}
</style>
