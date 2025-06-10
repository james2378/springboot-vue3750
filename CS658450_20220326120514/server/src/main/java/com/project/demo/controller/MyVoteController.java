package com.project.demo.controller;

import com.project.demo.entity.MyVote;
import com.project.demo.service.MyVoteService;
import com.project.demo.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *我的投票：(MyVote)表控制层
 *
 */
@RestController
@RequestMapping("/my_vote")
public class MyVoteController extends BaseController<MyVote,MyVoteService> {

    /**
     *我的投票对象
     */
    @Autowired
    public MyVoteController(MyVoteService service) {
        setService(service);
    }

    @PostMapping("/add")
    @Transactional
    public Map<String, Object> add(HttpServletRequest request) throws IOException {
        Map<String,Object> paramMap = service.readBody(request.getReader());
        this.addMap(paramMap);
        String sql = "SELECT MAX(my_vote_id) AS max FROM "+"my_vote";
        Query select = service.runCountSql(sql);
        Integer max = (Integer) select.getSingleResult();
        sql = "UPDATE `vote` INNER JOIN `my_vote` ON vote.voting_topic=my_vote.voting_topic SET vote.votes_ = vote.votes_ + my_vote.number_of_votes WHERE my_vote.my_vote_id="+max;
        select = service.runCountSql(sql);
        select.executeUpdate();
        return success(1);
    }

}
