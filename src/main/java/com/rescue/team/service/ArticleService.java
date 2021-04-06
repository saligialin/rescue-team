package com.rescue.team.service;

import com.rescue.team.bean.Article;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {

    List<Article> getArticles();
}
