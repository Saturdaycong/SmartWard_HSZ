package com.ekz.ctt.eckctt.mvp.model.entity;
import java.util.Objects;

public class WaringClientListBean {
    /**
     * color : DE8200
     * id : 54
     * warningName : 要素膳食
     */

    public String color;
    public String id;
    public String warningName;
    public String type;
    public int bedwarningId;
    public boolean isChecked = false;
    public boolean isEnableDelete = true;
    public String warningPinYin;

    public WaringClientListBean(String color, String warningName) {
        this.color=color;
        this.warningName=warningName;
    }

    public WaringClientListBean() { }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WaringClientListBean that = (WaringClientListBean) o;
        return Objects.equals(warningName, that.warningName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(warningName);
    }
}