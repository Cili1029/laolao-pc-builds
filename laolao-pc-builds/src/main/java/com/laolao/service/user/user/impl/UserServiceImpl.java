package com.laolao.service.user.user.impl;

import com.laolao.converter.MapStruct;
import com.laolao.mapper.common.SysFileMapper;
import com.laolao.mapper.user.forum.LikeMapper;
import com.laolao.mapper.user.forum.PostMapper;
import com.laolao.mapper.common.UserCommonMapper;
import com.laolao.pojo.forum.entity.Post;
import com.laolao.pojo.forum.vo.PostSimpleVO;
import com.laolao.pojo.user.entity.User;
import com.laolao.pojo.user.dto.UpdateUserDTO;
import com.laolao.pojo.user.vo.UserSimpleVO;
import com.laolao.common.result.Result;
import com.laolao.pojo.user.vo.UserVO;
import com.laolao.service.user.user.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserCommonMapper userCommonMapper;
    @Resource
    private MapStruct mapStruct;
    @Resource
    private PostMapper postMapper;
    @Resource
    private LikeMapper likeMapper;
    @Resource
    private SysFileMapper sysFileMapper;

    @Override
    public Result<UserVO> getUser(Integer id) {
        // 获取用户的信息
        User user = userCommonMapper.getUser(id);
        UserSimpleVO userSimpleVO = mapStruct.userToUserSimpleVO(user);
        // 获取用户的帖子
        List<Post> postList = postMapper.selectPostByUserid(id);
        ArrayList<PostSimpleVO> userPostList = new ArrayList<>();
        for (Post post : postList) {
            PostSimpleVO postSimpleVO = mapStruct.PostToSimpleVO(post);
            userPostList.add(postSimpleVO);
        }
        // 组装
        UserVO userVO = UserVO.builder()
                .user(userSimpleVO)
                .userPostList(userPostList)
                .build();
        return Result.success(userVO);
    }

    @Override
    public Result<List<PostSimpleVO>> getLike(Integer id) {
        // 获取喜欢的帖子
        List<Post> postList;
        List<Integer> postIdList = likeMapper.getLikePost(id);
        ArrayList<PostSimpleVO> likePostList = new ArrayList<>();
        if (!postIdList.isEmpty()) {
            postList = postMapper.getPostBatch(postIdList);
            for (Post post : postList) {
                PostSimpleVO postSimpleVO = mapStruct.PostToSimpleVO(post);
                likePostList.add(postSimpleVO);
            }
        }
        return Result.success(likePostList);
    }

    @Override
    public Result<String> update(UpdateUserDTO updateUserDTO) {
        User user = mapStruct.updateUserVOToUser(updateUserDTO);
        userCommonMapper.updateUser(user);
        if (org.apache.commons.lang3.StringUtils.isNoneBlank(updateUserDTO.getAvatar())) {
            List<String> images = new ArrayList<>();
            images.add(updateUserDTO.getAvatar());
            sysFileMapper.update(images);
        }
        return Result.success("修改成功！");

    }
}
