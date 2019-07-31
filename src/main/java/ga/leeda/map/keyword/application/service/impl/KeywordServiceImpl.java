package ga.leeda.map.keyword.application.service.impl;

import ga.leeda.map.keyword.application.service.KeywordService;
import ga.leeda.map.keyword.domain.Keyword;
import ga.leeda.map.keyword.domain.KeywordRepository;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeywordServiceImpl implements KeywordService {

    private final KeywordRepository repository;
    private final RedissonClient redissonClient;

    public KeywordServiceImpl(final KeywordRepository repository, final RedissonClient redissonClient) {
        this.repository = repository;
        this.redissonClient = redissonClient;
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
        // 왜 lock 해지 안했는데 세마포어가 풀리지
        RLock lock = redissonClient.getLock("KEYWORD-INSERT0-SEMAPHORE");
        if (lock.tryLock()) {
            try {
                return repository.findOrCreate(keywordString);
            } finally {
                if (lock.isLocked()) {
                    lock.unlock();
                }
            }
        } else {
            throw new IllegalStateException();
        }

    }
}
