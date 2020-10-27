package cn.mvc.tools;

import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * @program: Vent
 * @description: RedisTool
 * @author: ShengBi
 * @create: 2020-06-23 14:24
 **/
public class RedisTool {
    public List<Map<String, Object>> getinfo() {
        List<Map<String, Object>> relist = new ArrayList<>();

        Jedis jedis = new Jedis("192.168.61.236", 6379);  //指定Redis服务Host和port
        jedis.auth("redis");
        jedis.select(6);
        Set<String> keyname = jedis.keys("*");
        Iterator iterator = keyname.iterator();
        if (keyname.size() > 0) {
            while (iterator.hasNext()) {
                Map<String, Object> dataMap1 = new HashMap<>();
                dataMap1.put("serverip", iterator.next());
                relist.add(dataMap1);
            }
        }
        return relist;
    }

    /*
     * @Description: 插入string
     * @Param: [key, values, db]
     * @return: boolean
     * @Author: ShengBi
     * @Date: 2020/6/24
     */
    public boolean insertstring(String key, String values, int db) {
        Jedis jedis = new Jedis("192.168.61.236", 6379);  //指定Redis服务Host和port
        jedis.auth("redis");
        jedis.select(db);
        jedis.set(key, values);
        if (jedis.exists(key)) {
            return true;
        } else {
            return false;
        }
    }

}
