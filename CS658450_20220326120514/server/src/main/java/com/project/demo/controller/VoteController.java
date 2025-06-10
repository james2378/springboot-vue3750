package com.project.demo.controller;

import com.project.demo.entity.Vote;
import com.project.demo.service.VoteService;
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
 *参与投票：(Vote)表控制层
 *
 */
@RestController
@RequestMapping("/vote")
public class VoteController extends BaseController<Vote,VoteService> {

    /**
     *参与投票对象
     */
    @Autowired
    public VoteController(VoteService service) {
        setService(service);
    }

    @PostMapping("/add")
    @Transactional
    public Map<String, Object> add(HttpServletRequest request) throws IOException {
        Map<String,Object> paramMap = service.readBody(request.getReader());
        Map<String, String> mapvoting_topic = new HashMap<>();
        mapvoting_topic.put("voting_topic",String.valueOf(paramMap.get("voting_topic")));
        List listvoting_topic = service.select(mapvoting_topic, new HashMap<>()).getResultList();
        if (listvoting_topic.size()>0){
            return error(30000, "字段投票话题内容不能重复");
        }
        this.addMap(paramMap);
        return success(1);
    }

}
