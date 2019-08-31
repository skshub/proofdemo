package com.sk.demo.web;

import java.util.Objects;

public class NameKeyDto {

    private String objKey1;
    private String objValue1;
    private String objKey2;
    private String objValue2;

    public NameKeyDto() {
    }

    public NameKeyDto(String objKey1, String objValue1, String objKey2, String objValue2) {
        this.objKey1 = objKey1;
        this.objValue1 = objValue1;
        this.objKey2 = objKey2;
        this.objValue2 = objValue2;
    }

    public String getObjKey1() {
        return objKey1;
    }

    public String getObjValue1() {
        return objValue1;
    }

    public String getObjKey2() {
        return objKey2;
    }

    public String getObjValue2() {
        return objValue2;
    }

    public void setObjKey1(String objKey1) {
        this.objKey1 = objKey1;
    }

    public void setObjValue1(String objValue1) {
        this.objValue1 = objValue1;
    }

    public void setObjKey2(String objKey2) {
        this.objKey2 = objKey2;
    }

    public void setObjValue2(String objValue2) {
        this.objValue2 = objValue2;
    }

    public String toJsonString() {
        return "{\" " + objKey1 + "\" : \"" + objValue1 + "\" , \"" + objKey2 + "\" : \"" + objValue2 + "\"}";
    }

    @Override
    public String toString() {
        return objKey1 + '=' + objValue1 + '\n' + objKey2 + '=' + objValue2 ;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NameKeyDto)) return false;
        NameKeyDto that = (NameKeyDto) o;
        return Objects.equals(objKey1, that.objKey1) &&
                Objects.equals(objValue1, that.objValue1) &&
                Objects.equals(objKey2, that.objKey2) &&
                Objects.equals(objValue2, that.objValue2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objKey1, objValue1, objKey2, objValue2);
    }
}
