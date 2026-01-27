package com.laolao.controller.user.user;

import com.laolao.common.result.Result;
import com.laolao.pojo.user.vo.NotificationVO;
import com.laolao.service.user.user.NotificationService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户端-消息相关
 */
@RestController
@RequestMapping("/user/notification")
public class NotificationController {
    @Resource
    private NotificationService notificationService;

    /**
     * 获取未读消息数量
     *
     * @return 数量
     */
    @GetMapping("/status")
    public Result<Integer> getReadStatus() {
        return notificationService.getReadStatus();
    }

    /**
     * 打开消息框
     * 同时全部设为已读
     *
     * @return 消息数据
     */
    @GetMapping
    public Result<List<NotificationVO>> list() {
        return notificationService.getList();
    }
}
