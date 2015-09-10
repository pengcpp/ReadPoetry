package com.cpp.readpoetry.data;

import android.graphics.Color;

import java.util.ArrayList;

/**
 * 测试数据
 */
public class TestData {

    // PIE COLOR DEFAULT
    public static final int[] LIBERTY_COLORS = new int[]{
            Color.rgb(44, 186, 134), Color.rgb(252, 73, 30), Color.rgb(255, 62, 208),
            Color.rgb(255, 161, 104), Color.rgb(236, 230, 140), Color.rgb(1, 147, 250),
            Color.rgb(149, 116, 37), Color.rgb(179, 83, 144), Color.rgb(43, 130, 38),
            Color.rgb(255, 210, 0), Color.rgb(199, 219, 87), Color.rgb(140, 84, 217),
            Color.rgb(122, 117, 119), Color.rgb(255, 145, 159), Color.rgb(140, 25, 255),
            Color.rgb(50, 77, 119), Color.rgb(98, 55, 159), Color.rgb(140, 100, 125),
            Color.rgb(10, 130, 238)
    };

    public static final int[] LINE_COLORS = new int[]{
            Color.rgb(252, 73, 30), Color.rgb(62, 216, 255), Color.rgb(0, 169, 24),
            Color.rgb(140, 84, 217), Color.rgb(255, 72, 0),

            Color.rgb(252, 73, 30), Color.rgb(62, 216, 255), Color.rgb(0, 169, 24),
            Color.rgb(140, 84, 217), Color.rgb(255, 72, 0)
    };

    public static final int[] MAIN_TAB_FUND_COLORS = new int[]{
            Color.parseColor("#5fd4ce"), Color.parseColor("#be82f3"), Color.parseColor("#f2c047"),
            Color.parseColor("#f27c60"), Color.parseColor("#5faef0"), Color.parseColor("#00a3f8"),
            Color.parseColor("#7569e2"), Color.parseColor("#854ea8"),
    };

    public static final float[] MAIN_TAB_FUND_PROPORTION = new float[]{
            30, 28, 22, 15, 5
    };

    public static final int[] MAIN_TAB_COMBINATION_COLORS = new int[]{
            Color.parseColor("#df335b"), Color.parseColor("#fb8541"), Color.parseColor("#f8a64f"),
            Color.parseColor("#ffd200"),
            Color.parseColor("#c7db57"), Color.parseColor("#3ed8ff"), Color.parseColor("#00a3f8"),
            Color.parseColor("#7569e2"), Color.parseColor("#854ea8"),
    };


    public static ArrayList<QuoraData> initQuoraList() {

        ArrayList<QuoraData> quoraDataArrayList = new ArrayList<>();

        QuoraData quoraData = new QuoraData();
        quoraData.quoraTitle = "您目前的个人及家庭财务状况属于以下哪一种:";
        ArrayList<String> quoraList = new ArrayList<String>();
        quoraList.add("有较大数额未到期负债");
        quoraList.add("收支相抵");
        quoraList.add("有一定积蓄");
        quoraList.add("有较为丰厚的积蓄并有一定的投资");
        quoraList.add("比较富裕且有相当的投资");
        quoraData.quoraList = quoraList;
        quoraData.position = 0;
        quoraDataArrayList.add(quoraData);

        quoraData = new QuoraData();
        quoraData.quoraTitle = "您个人目前已经或者准备投资的基金金额占您或者家庭所拥有总资产的比重是多少:" +
                "(备注:总资产包括存款、证券投资、房地产及实业等)";
        quoraList = new ArrayList<>();
        quoraList.add("80-100%");
        quoraList.add("50-80%");
        quoraList.add("20-50%");
        quoraList.add("0-20%");
        quoraList.add("零");
        quoraData.quoraList = quoraList;
        quoraData.position = 1;
        quoraDataArrayList.add(quoraData);

        quoraData = new QuoraData();
        quoraData.quoraTitle = "您的年收入是多少:";
        quoraList = new ArrayList<>();
        quoraList.add("2万元以下");
        quoraList.add("2万元至5万元");
        quoraList.add("5万元至15万元");
        quoraList.add("15万元至50万元");
        quoraList.add("50万元以上");
        quoraData.quoraList = quoraList;
        quoraData.position = 2;
        quoraDataArrayList.add(quoraData);

        quoraData = new QuoraData();
        quoraData.quoraTitle = "您计划中的基金投资期限是多长:";
        quoraList = new ArrayList<>();
        quoraList.add("少于1年");
        quoraList.add("1-3年");
        quoraList.add("3-5年 ");
        quoraList.add("5-8年");
        quoraList.add("8年以上");
        quoraData.quoraList = quoraList;
        quoraData.position = 3;
        quoraDataArrayList.add(quoraData);

        quoraData = new QuoraData();
        quoraData.quoraTitle = "您是否有过股票与基金的投资经历,如有投资时间是多长:";
        quoraList = new ArrayList<String>();
        quoraList.add("没有");
        quoraList.add("有,但是少于3年");
        quoraList.add("有,在3-5年之间");
        quoraList.add("有,在5-10年之间");
        quoraList.add("有,长于10年");
        quoraData.quoraList = quoraList;
        quoraData.position = 4;
        quoraDataArrayList.add(quoraData);

        quoraData = new QuoraData();
        quoraData.quoraTitle = "您投资基金主要用于什么目的:";
        quoraList = new ArrayList<>();
        quoraList.add("平时生活保障");
        quoraList.add("养老");
        quoraList.add("子女教育");
        quoraList.add("资产增值");
        quoraList.add("置业");
        quoraData.quoraList = quoraList;
        quoraData.position = 5;
        quoraDataArrayList.add(quoraData);

        quoraData = new QuoraData();
        quoraData.quoraTitle = "您希望获得的年收益率是多少:";
        quoraList = new ArrayList<>();
        quoraList.add("相当于储蓄存款收益率");
        quoraList.add("相当于通货膨胀率");
        quoraList.add("高于通货膨胀率,只要保值并略有增值即可");
        quoraList.add("接近或相当于股票市场平均收益率");
        quoraList.add("大大超过股票市场平均收益率");
        quoraData.quoraList = quoraList;
        quoraData.position = 6;
        quoraDataArrayList.add(quoraData);

        quoraData = new QuoraData();
        quoraData.quoraTitle = "以下几种投资模式,您更偏好哪种模式:";
        quoraList = new ArrayList<>();
        quoraList.add("最高收益5%,不亏损");
        quoraList.add("可能的最高收益是15%,可能的最高亏损是5%");
        quoraList.add("可能的最高收益是30%,可能的最高亏损是15%");
        quoraList.add("可能最高收益100%,可能最高亏损60%");
        quoraList.add("可能最高收益100%,可能最高亏损90%");
        quoraData.quoraList = quoraList;
        quoraData.position = 7;
        quoraDataArrayList.add(quoraData);

        quoraData = new QuoraData();
        quoraData.quoraTitle = "如果您的股票型基金投资暂时亏损了30%,你怎么办:";
        quoraList = new ArrayList<>();
        quoraList.add("无法承受风险,准备赎回或卖掉");
        quoraList.add("3个月到6个月内如果还是亏损30%,就赎回或卖掉");
        quoraList.add("1年之内还是亏损30%,就准备赎回或卖掉");
        quoraList.add("2-3年之内都可以持有,等待机会");
        quoraList.add("没关系,可以长期持有,等待机会");
        quoraData.quoraList = quoraList;
        quoraData.position = 8;
        quoraDataArrayList.add(quoraData);

        quoraData = new QuoraData();
        quoraData.quoraTitle = "您的家人或朋友认为您在基金投资中是什么样的风险承担者:";
        quoraList = new ArrayList<>();
        quoraList.add("无法承受风险");
        quoraList.add("虽然厌恶风险但愿意承担一定的风险");
        quoraList.add("在深思熟虑后愿意承担相当的风险");
        quoraList.add("敢冒风险,比较激进");
        quoraList.add("爱好风险,相当激进");
        quoraData.quoraList = quoraList;
        quoraData.position = 9;
        quoraDataArrayList.add(quoraData);

        return quoraDataArrayList;
    }


}
