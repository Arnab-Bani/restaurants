package com.restaurant.portal.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ClientMenu.
 */
@Entity
@Table(name = "client_menu")
public class ClientMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_id")
    private Integer item_id;

    @Column(name = "item_name")
    private String item_name;

    @Lob
    @Column(name = "item_image")
    private byte[] item_image;

    @Column(name = "item_image_content_type")
    private String item_imageContentType;

    @Column(name = "item_price")
    private Double item_price;

    @Column(name = "category_id")
    private Integer category_id;

    @Column(name = "item_desc")
    private String item_desc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getItem_id() {
        return item_id;
    }

    public ClientMenu item_id(Integer item_id) {
        this.item_id = item_id;
        return this;
    }

    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public ClientMenu item_name(String item_name) {
        this.item_name = item_name;
        return this;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public byte[] getItem_image() {
        return item_image;
    }

    public ClientMenu item_image(byte[] item_image) {
        this.item_image = item_image;
        return this;
    }

    public void setItem_image(byte[] item_image) {
        this.item_image = item_image;
    }

    public String getItem_imageContentType() {
        return item_imageContentType;
    }

    public ClientMenu item_imageContentType(String item_imageContentType) {
        this.item_imageContentType = item_imageContentType;
        return this;
    }

    public void setItem_imageContentType(String item_imageContentType) {
        this.item_imageContentType = item_imageContentType;
    }

    public Double getItem_price() {
        return item_price;
    }

    public ClientMenu item_price(Double item_price) {
        this.item_price = item_price;
        return this;
    }

    public void setItem_price(Double item_price) {
        this.item_price = item_price;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public ClientMenu category_id(Integer category_id) {
        this.category_id = category_id;
        return this;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public String getItem_desc() {
        return item_desc;
    }

    public ClientMenu item_desc(String item_desc) {
        this.item_desc = item_desc;
        return this;
    }

    public void setItem_desc(String item_desc) {
        this.item_desc = item_desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClientMenu clientMenu = (ClientMenu) o;
        if (clientMenu.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, clientMenu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ClientMenu{" +
            "id=" + id +
            ", item_id='" + item_id + "'" +
            ", item_name='" + item_name + "'" +
            ", item_image='" + item_image + "'" +
            ", item_imageContentType='" + item_imageContentType + "'" +
            ", item_price='" + item_price + "'" +
            ", category_id='" + category_id + "'" +
            ", item_desc='" + item_desc + "'" +
            '}';
    }
}
