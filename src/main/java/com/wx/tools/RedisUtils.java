package com.wx.tools;

import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.*;

public class RedisUtils {
    private final static Logger LOG = Logger.getLogger(RedisUtils.class);


    private static JedisPool shardedJedisPool = null;

    static {
        if (shardedJedisPool == null) {
            shardedJedisPool = initSharedJedisPool();
        }
    }

    private RedisUtils() {

    }

    public static JedisPool getShardedJedisPool() {
        if (shardedJedisPool == null) {
            initSharedJedisPool();
        }
        return shardedJedisPool;
    }

    /**
     * Init the sharedJedisPool
     *
     * @return Success: return the shardedJedisPool Otherwise null
     */
    private static JedisPool initSharedJedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(200);
        config.setMaxTotal(300);
        config.setTestOnBorrow(false);
        config.setTestOnReturn(false);
        // shards infos
        String host = ConfigService.RedisHost;
        int port = ConfigService.RedisPort;
        String passwd = ConfigService.RedisAuth;
        int db = ConfigService.RedisDB;
        JedisPool pool;
        if (StringUtil.isEmpty(passwd)) {
            pool = new JedisPool(config, host, port);
        } else {
            pool = new JedisPool(config, host, port, 3000, passwd, db);
        }
        shardedJedisPool = pool;
        return pool;
    }

    public static byte[] getKey(String key) {
        if (shardedJedisPool == null) {
            LOG.error("reids pool is null,please check the configuration");
            return null;
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            return jedis.get(key.getBytes());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static String getKeyAsString(String key) {
        if (shardedJedisPool == null) {
            LOG.error("reids pool is null,please check the configuration");
            return null;
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            byte[] data = jedis.get(key.getBytes());
            if (data != null) {
                return new String(data, "UTF-8");
            } else {
                return null;
            }
        } catch (Exception ex) {
            LOG.error(String.format("Get key:%s from redis failed", key), ex);
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static String getKeyAsString(String key, int expiredSeconds) {
        if (shardedJedisPool == null) {
            LOG.error("reids pool is null,please check the configuration");
            return null;
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            byte[] data = jedis.get(key.getBytes());
            if (data != null) {
                jedis.expire(key, expiredSeconds);
                return new String(data, "UTF-8");
            } else {
                return null;
            }
        } catch (Exception ex) {
            LOG.error(String.format("Get key:%s from redis failed", key), ex);
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static void setex(String key, String value, int seconds) {
        if (shardedJedisPool == null) {
            LOG.error("reids pool is null,please check the configuration");
            return;
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            jedis.setex(key, seconds, value);
        } catch (Exception ex) {
            LOG.error(String.format("Set key:%s with value:%s to redis failed", key, value), ex);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static void setex(String key, byte[] value, int seconds) {
        if (shardedJedisPool == null) {
            LOG.error("reids pool is null,please check the configuration");
            return;
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            jedis.setex(key.getBytes("utf-8"), seconds, value);
        } catch (Exception ex) {
            LOG.error(String.format("Set key:%s with value:%s to redis failed", key, value), ex);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static void set(String key, String value) {
        if (shardedJedisPool == null) {
            LOG.error("reids pool is null,please check the configuration");
            return;
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            jedis.set(key, value);
        } catch (Exception ex) {
            LOG.error(String.format("Set key:%s with value:%s to redis failed", key, value), ex);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    public static Long del(String key) {
        if (shardedJedisPool == null) {
            LOG.error("reids pool is null,please check the configuration");
            return 0L;
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            return jedis.del(key);
        } catch (Exception ex) {
            LOG.error(String.format("Delete key:%s from redis failed", key), ex);
            return 0L;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    public static long llen(String key) {
        if (shardedJedisPool == null) {
            LOG.error("redis pool is null,please check the configuration");
            return 0;
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            return jedis.llen(key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    public static List<String> lrange(String key) {
        if (shardedJedisPool == null) {
            LOG.error("redis pool is null,please check the configuration");
            return Collections.emptyList();
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            return jedis.lrange(key, 0, -1);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    public static List<byte[]> lrange(byte[] key) {
        if (shardedJedisPool == null) {
            LOG.error("redis pool is null,please check the configuration");
            return Collections.emptyList();
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            return jedis.lrange(key, 0, -1);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    public static void lpush(String key, String... values) {
        if (shardedJedisPool == null) {
            LOG.error("redis pool is null,please check the configuration");
            return;
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            jedis.lpush(key, values);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    public static void hrem(byte[] key, byte[] field) {
        if (shardedJedisPool == null) {
            LOG.error("redis pool is null,please check the configuration");
            return;
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            jedis.hdel(key, field);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    public static void hset(byte[] key, byte[] field, byte[] value) {
        if (shardedJedisPool == null) {
            LOG.error("redis pool is null,please check the configuration");
            return;
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            jedis.hset(key, field, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    public static Map<byte[], byte[]> hGetAll(byte[] key) {
        if (shardedJedisPool == null) {
            LOG.error("redis pool is null,please check the configuration");
            return null;
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            return jedis.hgetAll(key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    public static long lrem(byte[] key, int count, byte[] value) {
        if (shardedJedisPool == null) {
            LOG.error("redis pool is null,please check the configuration");
            return 0;
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            return jedis.lrem(key, 0, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    public static String rpop(String key) {
        if (shardedJedisPool == null) {
            LOG.error("redis pool is null,please check the configuration");
            return null;
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            return jedis.rpop(key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }
    public static boolean exists(String key) {
        if (shardedJedisPool == null) {
            LOG.error("redis pool is null,please check the configuration");
            return false;
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            return jedis.exists(key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    public static long incr(String key) {
        if (shardedJedisPool == null) {
            LOG.error("redis pool is null,please check the configuration");
            return 0;
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            return (Long) (jedis.incr(key));
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    public static long incrBy(String key, long count) {
        if (shardedJedisPool == null) {
            LOG.error("redis pool is null,please check the configuration");
            return 0;
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            return jedis.incrBy(key, count);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    public static Double zincrBy(String key, int score, String member) {
        if (shardedJedisPool == null) {
            LOG.error("redis pool is null,please check the configuration");
            return 0.0;
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            return jedis.zincrby(key, (double) score, member);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    public static Set<String> zrevrange(String key, int topn) {
        if (shardedJedisPool == null) {
            LOG.error("redis pool is null,please check the configuration");
            return Collections.emptySet();
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            return jedis.zrevrange(key, 0, topn - 1);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    public static double zscore(String key, String member) {
        if (shardedJedisPool == null) {
            LOG.error("redis pool is null,please check the configuration");
            return 0;
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            return jedis.zscore(key, member);
        } catch (Exception e) {
            return 0;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static long zrevrank(String key, String member) {
        if (shardedJedisPool == null) {
            LOG.error("redis pool is null,please check the configuration");
            return 0;
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            return jedis.zrevrank(key, member);
        } catch (Exception e) {
            return -1;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static Long zunionstore(String dstKey, String[] keys) {
        if (shardedJedisPool == null) {
            LOG.error("redis pool is null,please check the configuration");
            return 0L;
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            return jedis.zunionstore(dstKey, keys);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static Long zcount(String dstKey, int min, int max) {
        if (shardedJedisPool == null) {
            LOG.error("redis pool is null,please check the configuration");
            return 0L;
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            return jedis.zcount(dstKey, (double) min, (double) max);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static void sadd(String key, String[] members) {
        if (shardedJedisPool == null) {
            LOG.error("redis pool is null,please check the configuration");
            return;
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            jedis.sadd(key, members);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    public static Set<String> smembers(String key) {
        if (shardedJedisPool == null) {
            LOG.error("redis pool is null,please check the configuration");
            return Collections.emptySet();
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            return jedis.smembers(key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    public static void sremove(String key, String[] members) {
        if (shardedJedisPool == null) {
            LOG.error("redis pool is null,please check the configuration");
            return;
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            jedis.srem(key, members);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static void incrby(String key, int count) {
        if (shardedJedisPool == null) {
            LOG.error("redis pool is null,please check the configuration");
            return;
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            jedis.incrBy(key, (long) count);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static void expire(String key, int seconds) {
        if (shardedJedisPool == null) {
            LOG.error("redis pool is null,please check the configuration");
            return;
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            jedis.expire(key, seconds);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    public static String hget(String key, String field) {
        if (shardedJedisPool == null) {
            LOG.error("redis pool is null,please check the configuration");
            return null;
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            return jedis.hget(key, field);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static void hset(String key, String field, String value) {
        if (shardedJedisPool == null) {
            LOG.error("redis pool is null,please check the configuration");
            return;
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            jedis.hset(key, field, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    public static void hsetnx(String key, String field, String value) {
        if (shardedJedisPool == null) {
            LOG.error("redis pool is null,please check the configuration");
            return;
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            jedis.hsetnx(key, field, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    public static void hmset(String key, Map<String, String> value) {
        if (shardedJedisPool == null) {
            LOG.error("redis pool is null,please check the configuration");
            return;
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            jedis.hmset(key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static List<String> hmget(String key, String... fields) {
        if (shardedJedisPool == null) {
            LOG.error("redis pool is null,please check the configuration");
            return Collections.emptyList();
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            return jedis.hmget(key, fields);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static Map<String, String> hgetall(String key) {
        if (shardedJedisPool == null) {
            LOG.error("redis pool is null,please check the configuration");
            return Collections.emptyMap();
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            return jedis.hgetAll(key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static boolean hExists(String key, String field) {
        if (shardedJedisPool == null) {
            LOG.error("redis pool is null,please check the configuration");
            return false;
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            return jedis.hexists(key, field);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static long hincrBy(String key, String field, int increment) {
        if (shardedJedisPool == null) {
            LOG.error("redis pool is null,please check the configuration");
            return 0L;
        }
        Jedis jedis = shardedJedisPool.getResource();
        try {
            return jedis.hincrBy(key, field, (long) increment);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

}
