package com.laolao.task;

import com.laolao.common.utils.AliOSSUtil;
import com.laolao.mapper.common.SysFileMapper;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class FileTask {
    @Resource
    private SysFileMapper fileMapper;
    @Resource
    private AliOSSUtil ossUtil;

    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanupTempFile() {
        LocalDateTime expire = LocalDateTime.now().minusDays(1);
        // 查找失效文件
        List<String> urls =  fileMapper.selectExpireFiles(expire);
        if (!CollectionUtils.isEmpty(urls)) {
            ossUtil.delete(urls);
            fileMapper.delete(expire);
        }
    }
}
