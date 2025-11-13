package com.laolao.converter;

import com.laolao.pojo.forum.entity.Comment;
import com.laolao.pojo.forum.entity.ForumCategory;
import com.laolao.pojo.forum.entity.Post;
import com.laolao.pojo.forum.vo.*;
import com.laolao.pojo.shop.dto.AddressDTO;
import com.laolao.pojo.shop.dto.CartProductDTO;
import com.laolao.pojo.shop.entity.*;
import com.laolao.pojo.shop.vo.*;
import com.laolao.pojo.user.entity.User;
import com.laolao.pojo.user.vo.UserSimpleVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * MapStruct的映射器
 * 用于VO entity VO的相互转换
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MapStruct {
    UserSimpleVO userToUserSimpleVO(User user);

    // 商品的
    ShopCategoryVO ShopCategoryToCategoryVO(ShopCategory shopCategory);

    ProductVO bundleToComponentVO(Bundle bundle);

    CartItem cartProductDTOToCartItem(CartProductDTO cartProductDTO);

    AddressVO addressToAddressVO(Address address);

    @Mapping(target = "id", ignore = true)
    OrderDetail cartProductVOToOrderDetail(CartProductVO cartProductVO);

    OrderDetailVO orderToOrderDetailVO(Order order);

    OrderProductVO orderDeatilToOrderProductVO(OrderDetail orderDetail);

    @Mapping(target = "id", ignore = true)
    Order addressDTOToOrder(AddressDTO addressDTO);

    // 论坛的
    ForumCategoryVO ForumCategoryToCategoryVO(ForumCategory forumCategory);

    PostSimpleVO PostToSimpleVO(Post post);

    PostVO PostToPostVO(Post post);

    CommentVO commentToCommentVO(Comment comment);

    CommentReplyVO commentToCommentReplyVO(Comment comment);
}
