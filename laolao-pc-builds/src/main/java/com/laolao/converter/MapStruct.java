package com.laolao.converter;

import com.laolao.pojo.forum.dto.AdminForumCategoryDTO;
import com.laolao.pojo.forum.entity.Comment;
import com.laolao.pojo.forum.entity.ForumCategory;
import com.laolao.pojo.forum.entity.Post;
import com.laolao.pojo.forum.vo.*;
import com.laolao.pojo.shop.dto.*;
import com.laolao.pojo.shop.entity.*;
import com.laolao.pojo.shop.vo.*;
import com.laolao.pojo.user.dto.AdminUserUpdateDTO;
import com.laolao.pojo.user.entity.User;
import com.laolao.pojo.user.vo.AdminUserVO;
import com.laolao.pojo.user.dto.UpdateUserDTO;
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

    ComponentDetailsVO componentToProductDetailsVO(Component component);

    BundleDetailsVO bundleToProductDetailsVO(Bundle bundle);

    VariantVO variantToVariantVO(Variant variant);

    ProductVO bundleToProductVO(Bundle bundle);

    CartItem cartProductDTOToCartItem(BuyProductDTO buyProductDTO);

    AddressVO addressToAddressVO(Address address);

    @Mapping(target = "id", ignore = true)
    OrderDetail cartProductVOToOrderDetail(CartProductVO cartProductVO);

    OrderDetail orderProductVOToOrderDetail(OrderProductVO orderProductVO);

    OrderDetailVO orderToOrderDetailVO(Order order);

    OrderProductVO orderDeatilToOrderProductVO(OrderDetail orderDetail);

    @Mapping(target = "id", ignore = true)
    Order addressDTOToOrder(AddressDTO addressDTO);

    ShopCouponVO shopCouponToShopCouponVO(ShopCoupon shopCoupon);

    User updateUserVOToUser(UpdateUserDTO updateUserDTO);

    // 论坛的
    ForumCategoryVO ForumCategoryToCategoryVO(ForumCategory forumCategory);

    PostSimpleVO PostToSimpleVO(Post post);

    PostVO PostToPostVO(Post post);

    CommentVO commentToCommentVO(Comment comment);

    CommentReplyVO commentToCommentReplyVO(Comment comment);

    // 管理端
    AdminUserVO userToAdminUserVO(User user);

    AdminComponentVO componentToAdminComponentVO(Component component);

    AdminBundleVO bundleToAdminBundleVO(Bundle bundle);

    ForumCategory adminForumCategoryDTOToForumCategory(AdminForumCategoryDTO adminForumCategoryDTO);

    Bundle AdminAddBundleDTOToBundle(AdminAddBundleDTO adminAddBundleDTO);

    Bundle AdminUpdateBundleDTOToBundle(AdminUpdateBundleDTO adminUpdateBundleDTO);

    Component adminUpdateComponentDTOToComponent(AdminUpdateComponentDTO adminUpdateComponentDTO);

    ShopCoupon adminShopCouponDTOToShopCoupon(AdminShopCouponDTO adminShopCouponDTO);

    Variant adminUpdateVariantDTOToVariant(AdminUpdateVariantDTO adminUpdateVariantDTO);

    User adminUserUpdateDTOToUser(AdminUserUpdateDTO adminUserUpdateDTO);

    ShopCategory adminShopCategoryDTOToShopCategory(AdminShopCategoryDTO adminShopCategoryDTO);

    Component adminAddComponentDTOToComponent(AdminAddComponentDTO adminAddComponentDTO);

    AdminOrderVO orderToAdminOrderVO(Order order);

    AdminOrderDetailVO orderDetailToorderDetailVO(OrderDetail orderDetail);
}
