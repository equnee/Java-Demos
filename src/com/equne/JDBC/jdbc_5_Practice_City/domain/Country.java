/* 国家表 */
package com.equne.JDBC.jdbc_5_Practice_City.domain;

public class Country {
    private Integer cid;
    private String cname;

    public Country() {
    }

    public Country(Integer cid, String cname) {
        this.cid = cid;
        this.cname = cname;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append(this.cid);
        builder.append(",");
        builder.append(this.cname);
        builder.append("}");

        return new String(builder);
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
