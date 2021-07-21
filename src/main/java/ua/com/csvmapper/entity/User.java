package ua.com.csvmapper.entity;

import ua.com.csvmapper.annotation.Column;

public class User {
    @Column("password")
    private String password;
    @Column("orderCount")
    private int orderCount;
    @Column("email")
    private String email;
    @Column("cashback")
    private double cashback;
    @Column("newUser")
    private boolean newUser;
    @Column("role")
    private Role role;

    @Override
    public String toString() {
        return "User{" +
                "password='" + password + '\'' +
                ", orderCount=" + orderCount +
                ", email='" + email + '\'' +
                ", cashback=" + cashback +
                ", newUser=" + newUser +
                ", role=" + role +
                '}';
    }
}
