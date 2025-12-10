package com.laolao.common.utils;

import java.util.Random;

public class NameUtil {

    private static final String[] NAMES = {
            "海绵宝宝", "纽曼", "帕奇柏金斯", "海霸王", "龙虾拉里", "海神",
            "飞行荷兰人", "企鹅男孩", "美人鱼战士", "泡芙老师", "小蜗",
            "珍珍", "凯伦", "痞老板", "珊迪", "蟹阿金", "章鱼哥",
            "派大星", "彼得", "路易斯", "梅根", "克里斯", "饺子", "布莱恩",
            "老乔", "Q哥", "黑哥", "休伊", "布彻尔", "法兰奇", "MM", "喜美子",
            "祖国人", "星光", "深海", "梅芙女王", "火车头", "玄色", "风暴前线",
            "士兵男孩"
    };

    private static final Random RANDOM = new Random();

    public static String getName() {
        int randomIndex = RANDOM.nextInt(NAMES.length);
        return NAMES[randomIndex];
    }
}