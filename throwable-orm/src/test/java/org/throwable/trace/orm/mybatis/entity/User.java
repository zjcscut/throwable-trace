package org.throwable.trace.orm.mybatis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zhangjinci
 * @version 2016/11/15 9:43
 * @function
 */
@Entity
@Table(name = "TB_AT_USER")
public class User implements Serializable {


    private static final long serialVersionUID = -7684496227693743739L;

    @Id
    private Integer id;
    @Column(name = "USER_ID")
    private Integer userId;
    @Column(name = "NAME")
    private String name;
    @Column(name = "BIRTH")
    private Date birth;
    @Column(name = "EMAIL")
    private String email;

    public User() {
    }

    public User(Integer id, Integer userId, String name, Date birth, String email) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.birth = birth;
        this.email = email;
    }

    public User(Integer userId, String name, Date birth, String email) {
        this.userId = userId;
        this.name = name;
        this.birth = birth;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", birth=" + birth +
                ", email='" + email + '\'' +
                '}';
    }
}
