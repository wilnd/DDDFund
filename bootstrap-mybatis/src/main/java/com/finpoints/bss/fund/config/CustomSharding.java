package com.finpoints.bss.fund.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.Collection;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;

public class CustomSharding implements StandardShardingAlgorithm<Comparable<?>> {

    private final static SortedMap<Long, String> sortedMap = new TreeMap<>();

    private static long getFNVHash(String str) {
        final long p = 16777619;
        long hash = 2166136261L;
        long fnvSize = 4294967296L;
        for (int i = 0; i < str.length(); i++) {
            hash = ((hash ^ str.charAt(i)) * p) % fnvSize;
        }
        return hash;
    }

    static {
        // 构建一致性hash环
        String tableName = "api_flow_";
        String shardingNumber = System.getenv("SHARDING-NUMBER");
        int shardingnum = 80;
        if (StringUtils.isNotEmpty(shardingNumber)) {
            shardingnum = Integer.parseInt(shardingNumber);
        }
        for (int i = 0; i < shardingnum; i++) {
            String each = tableName + i;
            sortedMap.put(getFNVHash(each), each);
            for (int j = 0; j < 100; j++) {
                sortedMap.put(getFNVHash(each + "_VN_" + j), each);
            }
        }
    }

    @Override
    public String getType() {
        return "custom-sharding";
    }

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Comparable<?>> shardingValue) {
        long abs = getFNVHash(shardingValue.getValue().toString());
        // 一致性hash
        SortedMap<Long, String> subMap = sortedMap.tailMap(abs);
        if (subMap.isEmpty()) {
            //如果没有比该key的hash值大的，则从第一个node开始
            long i = sortedMap.firstKey();
            //返回对应的服务器
            return sortedMap.get(i);
        } else {
            //第一个Key就是顺时针过去离node最近的那个结点
            long i = subMap.firstKey();
            //返回对应的服务器
            return subMap.get(i);
        }
    }

    @Override
    public Collection<String> doSharding(Collection collection, RangeShardingValue rangeShardingValue) {
        return null;
    }

    @Override
    public Properties getProps() {
        return null;
    }

    @Override
    public void init(Properties properties) {

    }
}
