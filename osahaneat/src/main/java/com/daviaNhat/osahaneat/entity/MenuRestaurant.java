package com.daviaNhat.osahaneat.entity;

import com.daviaNhat.osahaneat.entity.keys.KeyMenuRestaurant;
import jakarta.persistence.*;

import java.util.Date;

@Entity(name = "MenuRestaurant")
public class MenuRestaurant {

    @EmbeddedId
    KeyMenuRestaurant keys;

    @ManyToOne
    @JoinColumn(name = "cate_id" ,insertable = false, updatable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "res_id" ,insertable = false, updatable = false)
    private Restaurant restaurant;

    @Column(name = "create__date")
    private Date createDate;

    public KeyMenuRestaurant getKeys() {
        return keys;
    }

    public void setKeys(KeyMenuRestaurant keys) {
        this.keys = keys;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
