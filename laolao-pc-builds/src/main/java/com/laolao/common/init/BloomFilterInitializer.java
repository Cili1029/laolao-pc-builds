package com.laolao.common.init;

import com.laolao.mapper.user.shop.BundleMapper;
import com.laolao.mapper.user.shop.ComponentMapper;
import jakarta.annotation.Resource;
import org.redisson.api.RBloomFilter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BloomFilterInitializer implements CommandLineRunner {
    @Resource
    private RBloomFilter<Integer> componentBloomFilter;
    @Resource
    private RBloomFilter<Integer> bundleBloomFilter;
    @Resource
    private ComponentMapper componentMapper;
    @Resource
    private BundleMapper bundleMapper;

    @Override
    public void run(String... args) {
        System.out.println("组件布隆过滤器运行");
        List<Integer> componentIds = componentMapper.selectId();
        componentIds.forEach(id -> componentBloomFilter.add(id));
        System.out.println("添加了"+ componentIds.size() +"条组件数据");

        System.out.println("整机布隆过滤器运行");
        List<Integer> bundleIds = bundleMapper.selectId();
        bundleIds.forEach(id -> bundleBloomFilter.add(id));
        System.out.println("添加了"+ bundleIds.size() +"条整机数据");
    }
}
