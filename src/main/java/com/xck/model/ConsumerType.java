package com.xck.model;

/**
 * 消费类型
 *
 * @author xuchengkun
 * @date 2022/01/08 23:17
 **/
public class ConsumerType {

    private int typeId;

    private String typeName;

    private int parentTypeId;

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getParentTypeId() {
        return parentTypeId;
    }

    public void setParentTypeId(int parentTypeId) {
        this.parentTypeId = parentTypeId;
    }
}
