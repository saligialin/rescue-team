package com.rescue.team.service.impl;

import com.rescue.team.bean.Article;
import com.rescue.team.dao.ArticleDao;
import com.rescue.team.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Override
    public List<Article> getArticles() {
        log.info("开始获取最近15篇文章");
        try {
            List<Article> articles = articleDao.getArticles();
            log.info("获取最近15篇文章结束");
            return articles;
        } catch (Exception e) {
            log.info("获取最近15篇文章异常：");
            log.info(e.toString());
            return null;
        }
    }
}
