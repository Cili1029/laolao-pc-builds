package com.laolao.service.user.shop.impl;

import com.laolao.common.constant.RedisConstant;
import com.laolao.common.context.UserContext;
import com.laolao.common.result.Result;
import com.laolao.converter.MapStruct;
import com.laolao.mapper.user.shop.ShopCouponMapper;
import com.laolao.pojo.shop.dto.GetCouponDTO;
import com.laolao.pojo.shop.entity.ShopCoupon;
import com.laolao.pojo.shop.vo.ShopCouponVO;
import com.laolao.pojo.shop.entity.UserCoupon;
import com.laolao.pojo.shop.vo.UserCouponVO;
import com.laolao.service.user.shop.ShopCouponService;
import jakarta.annotation.Resource;
import org.redisson.api.RLock;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopShopCouponServiceImpl implements ShopCouponService {
    @Resource
    private ShopCouponMapper shopCouponMapper;
    @Resource
    private MapStruct mapStruct;
    @Resource
    private RedissonClient redissonClient;

    // 注入自己以解决内部调用事务失效问题，将getToUser写在另一个Service里好像也行
    @Resource
    // 防止 Spring 在启动时因为“自己依赖自己”而陷入循环依赖的死循环
    @Lazy
    private ShopShopCouponServiceImpl self;

    @Override
    public Result<List<ShopCouponVO>> ShowShopCoupon() {
        List<ShopCoupon> couponList = shopCouponMapper.selectShopCoupon(UserContext.getCurrentId());
        List<ShopCouponVO> shopCouponVOS = new ArrayList<>();
        for (ShopCoupon coupon : couponList) {
            ShopCouponVO shopCouponVO = mapStruct.shopCouponToShopCouponVO(coupon);
            shopCouponVOS.add(shopCouponVO);
        }
        return Result.success(shopCouponVOS);
    }

    @Override
    public Result<List<UserCouponVO>> showUserCoupon() {
        List<UserCouponVO> couponList = shopCouponMapper.selectUserCoupon(UserContext.getCurrentId(), 0);
        return Result.success(couponList);
    }

    @Override
    public Result<String> getCoupon(GetCouponDTO getCouponDTO) {
        int couponId = getCouponDTO.getCouponId();
        int userId = UserContext.getCurrentId();

        // RLock解决一人一单
        // 定义锁
        String lockKey = RedisConstant.Shop.COUPON_LOCK + userId + ":" + couponId;
        RLock lock = redissonClient.getLock(lockKey);

        // 尝试获取锁
        boolean isLock = lock.tryLock();
        if (!isLock) {
            // 用户并发请求
            return Result.error("请求过于频繁，请稍后再试！");
        }

        // 上锁
        try {
            // RSemaphore解决超卖
            // 定义锁
            String semaphoreKey = RedisConstant.Shop.COUPON_STOCK + couponId;
            RSemaphore semaphore = redissonClient.getSemaphore(semaphoreKey);

            // 检查库存
            boolean stockRes = semaphore.tryAcquire();
            if (!stockRes) {
                return Result.error("优惠券已被领完！");
            }

            try {
                // 有库存，更新数据库（查一人一单记录 + 插入）
                // error不算异常，但是还是得还库存
                Result<String> res = self.getToUser(userId, couponId);
                if (res.getCode() == 0) {
                    semaphore.release();
                }
                return res;
            } catch (Exception e) {
                // 如果数据库操作失败，要把抢走的信号量还回去
                semaphore.release();
                throw e;
            }
        } finally {
            // 只有持有锁的线程才能释放 防止超时自动释放抛出异常
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    @Transactional
    public Result<String> getToUser(int userId, int couponId) {
        // 是否领过？
        int count = shopCouponMapper.isGotTheCoupon(userId, couponId);
        if (count > 0) {
            return Result.error("您已经领取过该优惠券了！");
        }

        // 领取
        UserCoupon userCoupon = UserCoupon.builder()
                .userId(userId)
                .couponId(couponId)
                .build();
        shopCouponMapper.insertUserCoupon(userCoupon);

        // 扣mysql数据，后期改为队列吧
        shopCouponMapper.updateStock(couponId);
        return Result.success("领取成功！");
    }

    @Override
    public Result<List<UserCouponVO>> getAvailableCoupon(int userCouponId) {
        List<UserCouponVO> couponList = shopCouponMapper.selectAvailableCoupon(UserContext.getCurrentId(), userCouponId);
        return Result.success(couponList);
    }
}
