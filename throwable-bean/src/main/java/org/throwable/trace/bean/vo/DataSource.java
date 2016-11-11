package org.throwable.trace.bean.vo;

/**
 * @author zhangjinci
 * @version 2016/11/11 17:22
 * @function 数据库Vo
 */
public class DataSource {

    private Boolean is_master;
    private String name;
    private String url;
    private String username;
    private String password;

    public Boolean getIs_master() {
        return is_master;
    }

    public void setIs_master(Boolean is_master) {
        this.is_master = is_master;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
