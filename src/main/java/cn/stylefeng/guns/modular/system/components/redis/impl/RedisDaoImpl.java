package cn.stylefeng.guns.modular.system.components.redis.impl;

import cn.stylefeng.guns.modular.system.components.redis.RedisDao;
import cn.stylefeng.guns.modular.system.constant.Constant;
import cn.stylefeng.guns.modular.system.utils.DateUtil;
import cn.stylefeng.guns.modular.system.utils.SerializeUtils;
import cn.stylefeng.guns.modular.system.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @desc: redis API 操作實現
 * @author： kidy
 * @createtime： 5/23/20182:01 PM
 * @modify by： ${user}
 * @modify time： 5/23/20182:01 PM
 * @desc of modify：
 * @throws:
 */
@Component
public class RedisDaoImpl implements RedisDao {
    private static Logger log = LoggerFactory.getLogger(RedisDaoImpl.class);
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void setString(String prefix, String key, String value, Long expire) {
        try {
            if (StringUtils.isEmpty(expire)){
                redisTemplate.opsForValue().set(getKey(prefix,key),value);
            }else{
                redisTemplate.opsForValue().set(getKey(prefix,key),value,expire, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            log.info("redis setString 异常：{}",e.getMessage());
        }
    }

    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Object getObject(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    @Override
    public Object getObject(String prefix, String key) {
        try {
            return redisTemplate.opsForValue().get(getKey(prefix, key));
        } catch (Exception e) {
            log.info("redis getObject 异常：{}",e.getMessage());
        }
        return null;
    }

    @Override
    public Set keys(String parten) {
        return stringRedisTemplate.keys(parten);
    }

    @Override
    public boolean set(String key, String value) {
        boolean result = (boolean) redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            connection.set(serializer.serialize(key), serializer.serialize(value));
            return true;
        });
        return result;
    }

    @Override
    public String get(String key) {
        String result = (String) redisTemplate.execute((RedisCallback<String>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            byte[] value = connection.get(serializer.serialize(key));
            return serializer.deserialize(value);
        });
        return result;
    }

    @Override
    public boolean expire(String key, long expire) {
        return expire(null,key,expire);
    }

    @Override
    public boolean expire(String prefix, String key, long expire) {
        boolean result = (boolean) redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            connection.expire(serializer.serialize(getKey(prefix,key)), expire);
            return true;
        });
        return result;
    }

    @Override
    public <T> boolean setList(String key, List<T> list) {
        String value = JSONObject.toJSONString(list);
        return set(key, value);
    }

    @Override
    public <T> List<T> getList(String key, Class<T> clz) {
        String json = get(key);
        if (json != null) {
            List<T> list = JSONObject.parseArray(json, clz);
            return list;
        }
        return null;
    }

    @Override
    public long lpush(String key, Object obj) {
        final String value = JSONObject.toJSONString(obj);
        long result = (long) redisTemplate.execute((RedisCallback<Long>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            long count = connection.lPush(serializer.serialize(key), serializer.serialize(value));
            return count;
        });
        return result;
    }

    @Override
    public long rpush(String key, Object obj) {
        final String value = JSONObject.toJSONString(obj);
        long result = (long) redisTemplate.execute((RedisCallback<Long>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            long count = connection.rPush(serializer.serialize(key), serializer.serialize(value));
            return count;
        });
        return result;
    }

    @Override
    public String lpop(String key) {
        String result = (String) redisTemplate.execute((RedisCallback<String>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            byte[] res = connection.lPop(serializer.serialize(key));
            return serializer.deserialize(res);
        });
        return result;
    }

    @Override
    public String rpop(String key) {
        String result = (String) redisTemplate.execute((RedisCallback<String>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            byte[] res = connection.rPop(serializer.serialize(key));
            return serializer.deserialize(res);
        });
        return result;
    }


    @Override
    public Object getSerObject(String prefix, String key) {
        return redisTemplate.execute((RedisCallback<Object>) connection -> SerializeUtils.deserialize(connection.get(getKey(prefix, key).getBytes())));
    }

    @Override
    public Long incr(String prefix, String key) {
        return (Long) redisTemplate.execute((RedisCallback<Long>) connection ->connection.incr(getKey(prefix,key).getBytes()));
    }

    @Override
    public Long del(String prefix, String key) {
        return (Long) redisTemplate.execute((RedisCallback) connection -> connection.del(getKey(prefix,key).getBytes()));
    }

    @Override
    public void zadd(String prefix, String key, double score, String member) {
        redisTemplate.execute((RedisCallback) connection -> connection.zAdd(getKey(prefix, key).getBytes(),score,member.getBytes()));
    }

    @Override
    public Set<RedisZSetCommands.Tuple> zrange(String prefix, String key, int startIndex, int endIndex) {
        return (Set<RedisZSetCommands.Tuple>) redisTemplate.execute((RedisCallback<Set<RedisZSetCommands.Tuple>>) connection -> connection.zRangeWithScores(getKey(prefix, key).getBytes(),startIndex,endIndex));
    }

    @Override
    public String getOrderNo() {
        return DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmss")
                + "00"
                + this.incr(Constant.RedisOrderPrefix.PLATFORM_SYSTEM_ORDER_NO,"");
    }

}
