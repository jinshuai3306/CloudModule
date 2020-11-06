package com.qfedu.provider.third;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.protocol.ScoredEntry;
import org.redisson.config.Config;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @program: CloudLike
 * @description:  基于Redisson实现Redis数据库的操作
 * @author: Feri：靳帅
 * @create: 2020-11-02 11:13
 */
public class RedissonUtil {
    private static RedissonClient client;
    static {
        //配置
        Config config=new Config();
        config.setThreads(1000);//设置线程的数量
        config.useSingleServer().setAddress("redis://39.105.189.141:6380").setPassword("qfjava");
//        config.useSingleServer();//单机
//        config.useSentinelServers();//哨兵
//        config.useClusterServers();//集群
        client=Redisson.create(config);
    }
    //------------------------------------String-------------------------------------
    //新增，未设置有效期----string,string
    public static void setStr(String key,String val){
        client.getBucket(key).set(val);
    }
    //新增，未设置有效期----string,object
    public static void setStr(String key,Object val){
        client.getBucket(key).set(val);
    }
    //新增，设置有效期----string,string,秒
    public static void setStr(String key,String val,long seconds){
        client.getBucket(key).set(val,seconds, TimeUnit.SECONDS);
    }
    //查询
    public static String getStr(String key){
        return client.getBucket(key).get().toString();
    }
    public static Set<Object> getSet(String key){
        return client.getSet(key).readAll();
    }
    public static Object getStrObj(String key){
        return client.getBucket(key).get();
    }

    //-------------------------------Set---------------------------------------------
    //set添加--string,string
    public static void setSet(String key,String val){
        client.getSet(key).add(val);
    }
    //set添加--string,set<object>
    public static void setSet(String key, Set<Object> vals){
        client.getSet(key).addAll(vals);
    }
    //-------------------------------ZSet----------------------------------------------
    //添加
    public static void setZset(String key,double score,String val){
        client.getScoredSortedSet(key).add(score,val);
    }
    public static void setZset(String key, Map<Object,Double> vals){
        client.getScoredSortedSet(key).addAll(vals);
    }
    //查询
    //读取全部
    public static Collection<Object> getZSet(String key){
        return client.getScoredSortedSet(key).readAll();
    }
    //得到 score时间戳到当前时间的zset集合
    public static Collection<ScoredEntry<Object>> getZSet(String key, double score){
        return client.getScoredSortedSet(key).entryRange(score,true,System.currentTimeMillis(),true);
        //return client.getScoredSortedSet(key).readSort(SortOrder.ASC,score,);
    }
    //Set校验
    public static boolean checkSet(String key,Object val){
        return client.getSet(key).contains(val);
    }
    //ZSet校验
    public static boolean checkZSet(String key,Object val){
        return client.getScoredSortedSet(key).contains(val);
    }
    //String校验
    public static boolean checkKey(String... key){
        return client.getKeys().countExists(key)>0;
    }

    /**
     * 模糊查询key
     * @param key
     * @return
     */
    public static boolean checkKeyPat(String key){
        return client.getKeys().getKeysByPattern(key)!=null;
    }


    //设置有效期  key/时间/时间单位
    public static void setTime(String key,long time,TimeUnit timeUnit){
        client.getKeys().expire(key, time, timeUnit);
    }
    //删除key
    public static void delKeys(String... keys){
        client.getKeys().delete(keys);
    }
    //移除元素
    public static boolean delZSet(String key,String val){
        return client.getScoredSortedSet(key).remove(val);
    }
    //分布式锁 =Redis(setnx)+Java(Lock)
    //加锁
    public static void lock(String key){
        client.getLock(key).lock();
    }
    //加锁 设置默认的超时时间
    public static void lock(String key,long seconds){
        client.getLock(key).lock(seconds,TimeUnit.SECONDS);
    }
    //释放锁
    public static void unlock(String key){
        client.getLock(key).unlock();
    }
    //得到
    //得到所有点赞的数据信息集合--------------------- RedisKeyConfig.LIKE_CID + "*"： 所有前缀的key集合
    public static Iterable<String> getKeys(String pat){
        return client.getKeys().getKeysByPattern(pat);
    }


    //------------------------------------hash------------------------------------------
    public static Object getHash(String key,String field){
        return client.getMap(key).get(field);
    }
    public static Collection<Object> getHash(String key){
        return client.getMap(key).values();
    }

    public static void setHash(String key,String field,Object val){
        client.getMap(key).put(field,val);

    }
    public static void setHashAll(String key,Map<String,Object> map) {
        client.getMap(key).putAll(map);
    }

    public static boolean checkField(String key,String field){
        return client.getMap(key).containsKey(field);
    }
}
