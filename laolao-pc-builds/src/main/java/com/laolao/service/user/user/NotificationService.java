package com.laolao.service.user.user;

import com.laolao.common.result.Result;
import com.laolao.pojo.user.Listener.CommentNotification;
import com.laolao.pojo.user.Listener.LikeNotification;
import com.laolao.pojo.user.Listener.OrderNotification;
import com.laolao.pojo.user.Listener.ReplyNotification;
import com.laolao.pojo.user.vo.NotificationVO;

import java.util.List;

public interface NotificationService {
    Result<Integer> getReadStatus();

    Result<List<NotificationVO>> getList();

    void commentNotification(CommentNotification commentNotification);

    public void replyNotification(ReplyNotification replyNotification);

    public void likeNotification(LikeNotification likeNotification);

    public void ShippedNotification(OrderNotification orderNotification);
}
