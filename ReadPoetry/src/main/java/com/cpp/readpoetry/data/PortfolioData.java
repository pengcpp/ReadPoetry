package com.cpp.readpoetry.data;


import com.cpp.readpoetry.third.json.JsonArray;
import com.cpp.readpoetry.third.json.JsonObject;

import java.util.ArrayList;

/**
 * 风险测评结束后展示结果 v2
 */
public class PortfolioData {

    public String name = "组合名称";     //组合名称
    public String comment = "基金简介...";  //基金简介
    public boolean isInvest = true; //是否投资过此组合
    public boolean isRisk = true;  //是否做过测试
    public long riskScore = 8;  //风险分值
    public boolean canRestart = true;  //是否可以重新测试
    public double incomeRateOfYear = 47f; //年收益率
    public double historyIncome = 78f;   //历史收益率

    public ArrayList<FundItemData> fundList = new ArrayList<FundItemData>(); //组合列表

    public double defaultMoney;//	显示投资金额
    public double minInvestAmount;//	最小投资金额

    public PortfolioData() {

        name = "组合名称";
        comment = "基金简介...";
        isInvest = true;
        isRisk = true;
        riskScore = 8;
        canRestart = true;
        minInvestAmount = 1000;
        incomeRateOfYear = 47f;
        historyIncome = 78f;

        for (int i = 0; i < TestData.MAIN_TAB_FUND_PROPORTION.length; i++) {
            FundItemData itemData = new FundItemData();
            itemData.proportion = TestData.MAIN_TAB_FUND_PROPORTION[i];
            itemData.color = TestData.MAIN_TAB_COMBINATION_COLORS[i];
            fundList.add(itemData);
        }
    }

    public PortfolioData(JsonObject riskScoreData) {
        name = riskScoreData.getString("name");
        comment = riskScoreData.getString("comment");
        isInvest = riskScoreData.getBool("isInvest");
        isRisk = riskScoreData.getBool("isRisk");
        canRestart = riskScoreData.getBool("canRestart");
        riskScore = riskScoreData.getNum("riskScore");
        minInvestAmount = riskScoreData.getNumDouble("minInvestAmount");
        incomeRateOfYear = riskScoreData.getNumDouble("incomeRateOfYear");
        historyIncome = riskScoreData.getNumDouble("historyIncome");

        JsonArray tmpList = riskScoreData.getJsonArray("fundList");
        if (tmpList != null && tmpList.size() > 0) {
            for (int i = 0; i < tmpList.size(); i++) {
                FundItemData itemData = new FundItemData((JsonObject) tmpList.get(i));
                itemData.color = TestData.MAIN_TAB_FUND_COLORS[i];
                fundList.add(itemData);
            }
        }
    }

    public class FundItemData {

        public String fundName;
        public String investType;
        public double proportion;
        public long fundId;
        public String code;
        public int color;

        public FundItemData() {
        }

        public FundItemData(JsonObject itemObject) {
            investType = itemObject.getString("investType");
            fundName = itemObject.getString("name");
            proportion = itemObject.getNumDouble("proportion");
            fundId = itemObject.getNum("id");
            code = itemObject.getString("code");
        }
    }
}
