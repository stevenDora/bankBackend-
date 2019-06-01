package cn.stylefeng.guns.modular.system.components.redis.impl;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import cn.stylefeng.guns.modular.system.utils.StringUtils;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static cn.stylefeng.guns.modular.system.constant.BankManageEnum.BANK_CARD_LOCK_FAILED;
import static cn.stylefeng.guns.modular.system.constant.Constant.Lock.CASH_FLOW_LOCK_PREFIX;


/**
 * @title : redis分布式锁
 * @describle :
 * <p>
 * Create By kidy
 * @date 2017/7/5 15:26 星期三
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedisDistributedLock {
    private static Logger log = LoggerFactory.getLogger(RedisDistributedLock.class);
    @Resource
    private RedisTemplate redisTemplate;
    /* 锁 */
    private String lock = CASH_FLOW_LOCK_PREFIX;
    /* 锁超时时间(单位/ms) 默认1分钟 */
    private long lockTimeOut = 60*1000;
    /* 锁等待重试次数 默认6次 */
    private int lockWaitReTryTimes = 2;

    /**
     * 获取锁资源
     * 用于多重锁
     * @param lock
     * @return
     * @throws InterruptedException
     */
    public synchronized boolean lock(String lock) throws ServiceException {
        this.lock = lock;
        return lock();
    }

    /**
     * 获取锁资源
     * @return
     * @throws InterruptedException
     */
    public synchronized boolean lock() throws ServiceException {
        try {
            int reTryTimes = 1;
            while(reTryTimes<=lockWaitReTryTimes){
                long expires = System.currentTimeMillis()+lockTimeOut+1;
                //设置锁超时时间
                String expireLockTime = String.valueOf(expires);
                log.info("锁：{}",lock);
                log.info("判断锁是否存在:{}",redisTemplate.opsForValue().get(lock));
                boolean ret = this.setNX(lock,expireLockTime);
                log.info("是否获取锁:{}",ret);
                if (ret){
                    //获取锁
                    return true;
                }
                /**
                 * 锁超时
                 *
                 * 判断锁超时时间，已防止死锁
                 */
                Long currentLockTimeVal = StringUtils.isEmpty(this.get(lock))?0:this.get(lock);
                long systemCurrentTimeVal = System.currentTimeMillis();
//                log.info("当前锁超时时间-系统当前时间:ms"+(currentLockTimeVal-systemCurrentTimeVal));
                //判断当前锁值是否为空，否则判断锁是否超时(currentLockTimeVal<System.currentTimeMillis())
                if (StringUtils.isNotEmpty(currentLockTimeVal)&&(currentLockTimeVal-systemCurrentTimeVal)<0){
                    //获取上一个锁超时时间，并设置现在锁的超时时间
                    String oldLockTime = this.getSet(lock,expireLockTime);
                    /**
                     * 由于jedis.getSet是同步的，所以只有一个线程才能获取到上一个锁的超时时间
                     * 当多个线程同时执行到此处，由于只有一个线程的设置值和当前值相同，他才有权利获取锁
                     */
                    if (StringUtils.isNotEmpty(oldLockTime)&&oldLockTime.equals(String.valueOf(currentLockTimeVal))){
                        //获取锁
                        return true;
                    }
                }
                log.info("获取锁重试第"+reTryTimes+"次");
                reTryTimes++;
                /**
                 * 使用随机数保证每个线程等待时间公平
                 */
//                BigDecimal time = BigDecimalUtils.op(0, BigDecimalUtils.BigDecimalType.MULTIPLY,String.valueOf(Math.random()),"100");
//                log.info("重试耗时:ms"+10);
                Thread.sleep(5);
            }
        } catch (InterruptedException e) {
            log.info("redis获取锁异常:{}",e.getMessage());
            throw new ServiceException(BANK_CARD_LOCK_FAILED);
        }
        return false;
    }

    /**
     * 释放锁资源
     * 用于多重锁
     */
    public synchronized void unlock(String lockey,boolean locked){
        if (locked){
            //只有拥有锁资源线程方可释放锁资源
            redisTemplate.execute((RedisCallback<String>) connection -> {
                connection.del(lockey.getBytes());
                log.info("释放锁：{}",lockey);
                return null;
            });
            log.info("查看锁是否已被释放:{}",redisTemplate.opsForValue().get(lockey));
        } else {
            log.info("非占有锁[{}]资源者不可释放锁资源，重试机制内未获取到锁，该干啥干啥去",lockey);
        }
    }

    /**
     * 释放锁资源
     */
    public synchronized void unlock(boolean locked){
        if (locked){
            //只有拥有锁资源线程方可释放锁资源
            redisTemplate.execute((RedisCallback<String>) connection -> {
                connection.del(lock.getBytes());
                log.info("释放锁：{}",lock);
                return null;
            });
            log.info("查看锁是否已被释放:{}",redisTemplate.opsForValue().get(lock));
        } else {
            log.info("非占有锁[{}]资源者不可释放锁资源，重试机制内未获取到锁，该干啥干啥去",lock);
        }
    }

    public Long get(String key){
        String result = (String) redisTemplate.execute((RedisCallback<String>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            byte[] value = connection.get(serializer.serialize(key));
            return serializer.deserialize(value);
        });
        return Long.parseLong(result);
    }

    public boolean setNX(final String key,String value){
        boolean result = (boolean) redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            return connection.setNX(serializer.serialize(key), serializer.serialize(value));
        });
        return result;
    }

    public String getSet(String key,String value){
        String result = (String) redisTemplate.execute((RedisCallback<String>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            byte[] data = connection.getSet(serializer.serialize(key),serializer.serialize(value));
            return serializer.deserialize(data);
        });
        return result;
    }
}
