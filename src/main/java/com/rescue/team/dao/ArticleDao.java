package com.rescue.team.dao;

import com.rescue.team.bean.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleDao {

    List<Article> getArticles() ;

}
