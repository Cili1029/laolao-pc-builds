package com.laolao.controller.admin.shop;

import com.github.pagehelper.PageInfo;
import com.laolao.common.annotation.LogSql;
import com.laolao.common.result.Result;
import com.laolao.pojo.shop.dto.AdminOrderRejectDTO;
import com.laolao.pojo.shop.dto.AdminOrderShipDTO;
import com.laolao.pojo.shop.vo.AdminOrderDetailVO;
import com.laolao.pojo.shop.vo.AdminOrderVO;
import com.laolao.service.admin.shop.AdminOrderService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 管理端-订单相关
 */
@RestController
@RequestMapping("/admin/shop/order")
public class AdminOrderController {
    @Resource
    private AdminOrderService adminOrderService;

    /**
     * 获取订单
     *
     * @return 订单数据
     */
    @GetMapping
    public Result<PageInfo<AdminOrderVO>> getOrder(Integer status, Integer pageNum, Integer pageSize) {
        return adminOrderService.getCoupon(status, pageNum, pageSize);
    }

    /**
     * 获取订单商品详细
     *
     * @return 产品数据
     */
    @GetMapping("/detail")
    public Result<List<AdminOrderDetailVO>> getProduct(@RequestParam Integer orderId) {
        return adminOrderService.getProduct(orderId);
    }

    /**
     * 拒绝订单
     *
     * @param adminOrderRejectDTO 主键和原因
     * @return 结果信息
     */
    @LogSql(description = "拒单")
    @PatchMapping("/reject")
    public Result<String> rejectOrder(@RequestBody AdminOrderRejectDTO adminOrderRejectDTO) {
        return adminOrderService.rejectOrder(adminOrderRejectDTO);
    }

    /**
     * 订单发货
     *
     * @param adminOrderShipDTO 主键和原因
     * @return 结果信息
     */
    @LogSql(description = "发货")
    @PatchMapping("/ship")
    public Result<String> rejectOrder(@RequestBody AdminOrderShipDTO adminOrderShipDTO) {
        return adminOrderService.ship(adminOrderShipDTO);
    }


}
