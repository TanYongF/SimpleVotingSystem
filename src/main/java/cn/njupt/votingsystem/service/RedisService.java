package cn.njupt.votingsystem.service;

import cn.njupt.votingsystem.pojo.Vote;
import cn.njupt.votingsystem.pojo.VoteOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/10/25
 **/
@Service
@Slf4j
public class RedisService {

    public static final String REDIS_CHANNEL_PREFIX = "channel_";

    public static final String REDIS_OPTION_PREFIX = "option_";

    public static final String REDIS_HOT_LIST = "hot_lists";

    @Resource
    protected RedisTemplate redisTemplate;

    public void add(final String key, int num){
        redisTemplate.opsForValue().increment(key);
    }

    public void zAdd(final String key, final String value, final Integer score){
        redisTemplate.opsForZSet().add(key , value, score);
    }

    public void zRemove(final String key, final Integer value){
        redisTemplate.opsForZSet().remove(key, value);
    }

    public void alterHotList(final String key, final String value){
        if(exists(key)) redisTemplate.opsForZSet().incrementScore(key, value, 1);
//        redisTemplate.opsForZSet().removeRange(key, 0, -1 * (TopN + 1));
    }

    public Integer zGetScore(final String key, final String value){
        return redisTemplate.opsForZSet().score(key, value).intValue();
    }

    /**
    * @param TopN: 获取前TopN高个
    **/
    public Set zGetTopN(final String key, int TopN){
        if(TopN < 0) TopN = 0; //如果传入负数，那么查询全部
        Set hotLists = redisTemplate.opsForZSet().reverseRange(key, 0, TopN - 1);
        return hotLists;
    }

    public void setString(final String key, String value,final long time){
        try {
            redisTemplate.opsForValue().set(key, value,time, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("写入redis缓存失败！错误信息为：" + e.getMessage());
        }
    }

    /**
     * 写入redis缓存（不设置expire存活时间）
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Integer value) {
        redisTemplate.opsForValue().set(key, value);
        return true;
    }

    /**
     * 写入redis缓存（设置expire存活时间）
     *
     * @param key
     * @param value
     * @param expire
     * @return
     */
    public void setEx(final String key, Integer value, Long expire) {
        redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
    }


    /**
     * 读取redis缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        try {
            result = redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("读取redis缓存失败！错误信息为：" + e.getMessage());
        }
        return result;
    }

    /**
     * 判断redis缓存中是否有对应的key
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        boolean result = false;
        try {
            result = redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("判断redis缓存中是否有对应的key失败！错误信息为：" + e.getMessage());
        }
        return result;
    }

    /**
     * redis根据key删除对应的value
     *
     * @param key
     * @return
     */
    public boolean remove(final String key) {
        boolean result = false;
        try {
            if (exists(key)) {
                redisTemplate.delete(key);
            }
            result = true;
        } catch (Exception e) {
            log.error("redis根据key删除对应的value失败！错误信息为：" + e.getMessage());
        }
        return result;
    }

    /**
     * redis根据keys批量删除对应的value
     * @param keys
     * @return
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    public void add(final String... keys) {
        for (String key : keys) {
            set(key, 0);
        }
    }

    public void plus(final String... keys) {
        for (String key : keys) {
            if (exists(key)) {
                redisTemplate.opsForValue().increment(key);
            }
        }
    }

    public void removeVote(Vote vote) {
        for (VoteOptions vp : vote.getVoteOptionsList()) {
            remove("option_" + vp.getId());
        }
    }
}