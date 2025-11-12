package com.laolao.common.utils;

import java.util.Random;

public class NameUtil {
    private static final String[] ADJECTIVES = {
            "愤怒的", "快乐的", "神秘的", "勇敢的", "安静的",
            "疯狂的", "优雅的", "敏捷的", "强大的", "智慧的",
            "狡猾的", "孤独的", "不朽的", "混沌的", "神圣的"
    };

    private static final String[] NOUNS = {
            "小鸟", "老虎", "巨龙", "狐狸", "恶龙",
            "骑士", "法师", "刺客", "巫师", "勇士",
            "凤凰", "巨人", "恶魔", "天使", "战神"
    };

    private static final Random RANDOM = new Random();

    public static String generateName() {
        String adjective = ADJECTIVES[RANDOM.nextInt(ADJECTIVES.length)];
        String noun = NOUNS[RANDOM.nextInt(NOUNS.length)];
        return adjective + noun;
    }
}
