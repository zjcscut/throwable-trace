package org.throwable.trace.orm.hibernate.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zhangjinci
 * @version 2016/11/14 11:09
 * @function
 */
@Entity
@Table(name = "tb_at_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
