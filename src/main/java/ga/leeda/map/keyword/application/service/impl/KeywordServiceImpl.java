package ga.leeda.map.keyword.application.service.impl;

import ga.leeda.map.keyword.application.service.KeywordService;
import ga.leeda.map.keyword.domain.Keyword;
import ga.leeda.map.keyword.domain.KeywordRepository;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeywordServiceImpl implements KeywordService {

    private final KeywordRepository repository;

    public KeywordServiceImpl(final KeywordRepository repository) {
        this.repository = repository;
    }


    @Override
    @Cacheable(value = "top_rank_keyword")
    public List<Keyword> getTopRankKeywordList(int resultCount) {
        PageRequest pageRequest = PageRequest.of(0, resultCount);
        return repository.findByOrderByHitCountDescIdDesc(pageRequest);
    }

    @Override
    @CachePut(value = "keyword", key = "#keywordString")
    public Keyword findOrCreate(final String keywordString) {

        return repository.findOrCreate(keywordString);
    }
}
