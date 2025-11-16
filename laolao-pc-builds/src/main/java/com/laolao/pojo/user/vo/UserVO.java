package com.laolao.pojo.user.vo;

import com.laolao.pojo.forum.vo.PostSimpleVO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserVO {
    /**
     * 用户信息
     */
    private UserSimpleVO user;

    /**
     * 用户的帖子
     */
    private List<PostSimpleVO> userPostList;

    /**
     * 喜欢的帖子(基本信息不传)
     */
    private List<PostSimpleVO> likePostList = null;
}
