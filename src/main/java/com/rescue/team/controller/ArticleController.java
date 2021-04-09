package com.rescue.team.controller;

import com.rescue.team.bean.Article;
import com.rescue.team.bean.ResponseData;
import com.rescue.team.bean.state.ResponseState;
import com.rescue.team.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/article")
@Api(tags = "文章相关操作控制器")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @ApiOperation("获取最近15篇文章|无参")
    @PostMapping("/get15")
    public ResponseData getArticles() {
        List<Article> articles = articleService.getArticles();
        if(articles!=null) {
            Map<String,Object> data = new HashMap<>();
            for(int i=0; i<articles.size(); i++) {
                data.put("article"+i,articles.get(i));
            }
            return new ResponseData(ResponseState.SUCCESS.getValue(), ResponseState.SUCCESS.getMessage(), data);
        } else {
            return new ResponseData(ResponseState.RESULT_IS_NULL.getValue(), ResponseState.RESULT_IS_NULL.getMessage());
        }
    }
}
