package com.restaurant.portal.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Client_features.
 */
@Entity
@Table(name = "client_features")
public class Client_features implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id")
    private Integer client_id;

    @Column(name = "facebook")
    private String facebook;

    @Column(name = "yelp")
    private String yelp;

    @Column(name = "foursquare")
    private String foursquare;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getClient_id() {
        return client_id;
    }

    public Client_features client_id(Integer client_id) {
        this.client_id = client_id;
        return this;
    }

    public void setClient_id(Integer client_id) {
        this.client_id = client_id;
    }

    public String getFacebook() {
        return facebook;
    }

    public Client_features facebook(String facebook) {
        this.facebook = facebook;
        return this;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getYelp() {
        return yelp;
    }

    public Client_features yelp(String yelp) {
        this.yelp = yelp;
        return this;
    }

    public void setYelp(String yelp) {
        this.yelp = yelp;
    }

    public String getFoursquare() {
        return foursquare;
    }

    public Client_features foursquare(String foursquare) {
        this.foursquare = foursquare;
        return this;
    }

    public void setFoursquare(String foursquare) {
        this.foursquare = foursquare;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Client_features client_features = (Client_features) o;
        if (client_features.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, client_features.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Client_features{" +
            "id=" + id +
            ", client_id='" + client_id + "'" +
            ", facebook='" + facebook + "'" +
            ", yelp='" + yelp + "'" +
            ", foursquare='" + foursquare + "'" +
            '}';
    }
}
