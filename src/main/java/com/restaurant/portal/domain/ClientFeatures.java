package com.restaurant.portal.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ClientFeatures.
 */
@Entity
@Table(name = "client_features")
public class ClientFeatures implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "feature_url")
    private String featureUrl;

    @Column(name = "price")
    private Double price;

    @ManyToOne
    @JoinColumn
    private Client clientId;

    @ManyToOne
    @JoinColumn
    private ThirdPartyFeature thirdPartyFeatureId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFeatureUrl() {
        return featureUrl;
    }

    public ClientFeatures featureUrl(String featureUrl) {
        this.featureUrl = featureUrl;
        return this;
    }

    public void setFeatureUrl(String featureUrl) {
        this.featureUrl = featureUrl;
    }

    public Double getPrice() {
        return price;
    }

    public ClientFeatures price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Client getClientId() {
        return clientId;
    }

    public ClientFeatures clientId(Client client) {
        this.clientId = client;
        return this;
    }

    public void setClientId(Client client) {
        this.clientId = client;
    }

    public ThirdPartyFeature getThirdPartyFeatureId() {
        return thirdPartyFeatureId;
    }

    public ClientFeatures thirdPartyFeatureId(ThirdPartyFeature thirdPartyFeature) {
        this.thirdPartyFeatureId = thirdPartyFeature;
        return this;
    }

    public void setThirdPartyFeatureId(ThirdPartyFeature thirdPartyFeature) {
        this.thirdPartyFeatureId = thirdPartyFeature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClientFeatures clientFeatures = (ClientFeatures) o;
        if (clientFeatures.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, clientFeatures.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ClientFeatures{" +
            "id=" + id +
            ", featureUrl='" + featureUrl + "'" +
            ", price='" + price + "'" +
            '}';
    }
}
