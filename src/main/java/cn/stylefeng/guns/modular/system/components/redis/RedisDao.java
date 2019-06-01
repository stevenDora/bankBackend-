package cn.stylefeng.guns.modular.system.components.redis;

import cn.stylefeng.guns.modular.system.utils.StringUtils;
import org.springframework.data.redis.connection.RedisZSetCommands;

import java.util.List;
import java.util.Set;

/**
 * @desc: redis API 操作
 * @author： kidy
 * @createtime： 5/23/20181:55 PM
 * @modify by： ${user}
 * @modify time： 5/23/20181:55 PM
 * @desc of modify：
 * @throws:
 */
public interface RedisDao {
    void setString(String prefix, String key, String value, Long expire);
    Object getObject(String prefix, String key);
    Set keys(String parten);
    boolean set(final String key, final String value);
    String get(final String key);
    boolean expire(final String key, long expire);
    boolean expire(String prefix, String key, long expire);
    <T> boolean setList(String key, List<T> list);
    <T> List<T> getList(String key, Class<T> clz);
    long lpush(final String key, Object obj);
    long rpush(final String key, Object obj);
    String lpop(final String key);
    String rpop(final String key);
    Object getSerObject(String prefix, String key);
    Long incr(String prefix, String key);
    Long del(String prefix, String key);
    boolean set(String key, Object value);
    Object getObject(String key);
    void zadd(String prefix, String key, double score, String member);
    Set<RedisZSetCommands.Tuple> zrange(String prefix, String key, int startIndex, int endIndex);
    default String getKey(String prefix, String key) {
        StringBuffer sb = new StringBuffer();
        if (StringUtils.isNotEmpty(prefix)) {
            sb.append(prefix).append(StringUtils.SPLIT);
        }
        sb.append(key);
        return sb.toString();
    }
}
