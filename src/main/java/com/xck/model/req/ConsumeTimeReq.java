package com.xck.model.req;

/**
 * 时间消费请求
 *
 * @author xuchengkun
 * @date 2022/01/06 11:05
 **/
public class ConsumeTimeReq {

    /* userId */
    private String userId;

    private String consumeId;

    /* 名称 */
    private String productName;

    /* 开始时间 */
    private String startTime;

    /* 结束时间 */
    private String endTime;

    /* 总耗时 */
    private int useTime;

    /* 说明 */
    private String comment;

    /* 消费类型 */
    private int consumeType;

    private String insertTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getConsumeId() {
        return consumeId;
    }

    public void setConsumeId(String consumeId) {
        this.consumeId = consumeId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getUseTime() {
        return useTime;
    }

    public void setUseTime(int useTime) {
        this.useTime = useTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getConsumeType() {
        return consumeType;
    }

    public void setConsumeType(int consumeType) {
        this.consumeType = consumeType;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }
}
