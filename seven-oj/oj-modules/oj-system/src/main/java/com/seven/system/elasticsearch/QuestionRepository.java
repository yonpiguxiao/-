package com.seven.system.elasticsearch;


import com.seven.system.domain.question.es.QuestionES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends ElasticsearchRepository<QuestionES, Long> {

}
