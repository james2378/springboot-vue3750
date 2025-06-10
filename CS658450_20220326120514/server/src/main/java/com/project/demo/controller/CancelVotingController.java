package com.project.demo.controller;

import com.project.demo.entity.CancelVoting;
import com.project.demo.service.CancelVotingService;
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
 *取消投票：(CancelVoting)表控制层
 *
 */
@RestController
@RequestMapping("/cancel_voting")
public class CancelVotingController extends BaseController<CancelVoting,CancelVotingService> {

    /**
     *取消投票对象
     */
    @Autowired
    public CancelVotingController(CancelVotingService service) {
        setService(service);
    }

    @PostMapping("/add")
    @Transactional
    public Map<String, Object> add(HttpServletRequest request) throws IOException {
        Map<String,Object> paramMap = service.readBody(request.getReader());
        this.addMap(paramMap);
        String sql = "SELECT MAX(cancel_voting_id) AS max FROM "+"cancel_voting";
        Query select = service.runCountSql(sql);
        Integer max = (Integer) select.getSingleResult();
        sql = "UPDATE `vote` INNER JOIN `cancel_voting` ON vote.voting_topic=cancel_voting.voting_topic SET vote.votes_ = vote.votes_ - cancel_voting.number_of_cancelled_votes WHERE cancel_voting.cancel_voting_id="+max;
        select = service.runCountSql(sql);
        select.executeUpdate();
        return success(1);
    }

}
