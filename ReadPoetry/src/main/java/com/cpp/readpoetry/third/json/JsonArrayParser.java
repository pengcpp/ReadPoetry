package com.cpp.readpoetry.third.json;

import java.util.ArrayList;

/**
 * JsonArray的解析器
 * 
 * @author wencheng.song
 * 
 */
public class JsonArrayParser {
    /**
     * 解析JsonArray中的每一项
     * 
     * @author wencheng.song
     * 
     * @param <T>
     *            给定类型
     */
    public interface IItemParser<T> {
        /**
         * 把某一项解析成给定类型T
         * 
         * @param jValue
         *            某项的原始jsonValue
         * @return 解析后的给定类型
         */
        T parseItem(JsonValue jValue);
    }

    /**
     * 把一个JsonArray解析成一个ArrayList。组装的过程由此函数完成，调用者只需提供每一项的解析方法。
     * 
     * @param jsonArray
     *            原始的JsonArray
     * @param itemParser
     *            用于解析每一项
     * @return 组装后的ArrayList
     */
    public static <T> ArrayList<T> parse2ArrayList(JsonArray jsonArray, IItemParser<T> itemParser) {
        ArrayList<T> list = new ArrayList<T>();
        if (jsonArray != null) {
            int size = jsonArray.size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    JsonValue jValue = jsonArray.get(i);
                    T item = itemParser.parseItem(jValue);
                    if (item != null) {
                        list.add(item);
                    }
                }
            }
        }
        return list;
    }
}
