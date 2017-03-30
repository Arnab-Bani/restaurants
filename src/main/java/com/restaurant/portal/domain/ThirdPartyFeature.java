package com.restaurant.portal.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ThirdPartyFeature.
 */
@Entity
@Table(name = "third_party_feature")
public class ThirdPartyFeature implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "feature_code")
    private String featureCode;

    @Column(name = "feature_name")
    private String featureName;

    @Column(name = "billable")
    private Boolean billable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFeatureCode() {
        return featureCode;
    }

    public ThirdPartyFeature featureCode(String featureCode) {
        this.featureCode = featureCode;
        return this;
    }

    public void setFeatureCode(String featureCode) {
        this.featureCode = featureCode;
    }

    public String getFeatureName() {
        return featureName;
    }

    public ThirdPartyFeature featureName(String featureName) {
        this.featureName = featureName;
        return this;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public Boolean isBillable() {
        return billable;
    }

    public ThirdPartyFeature billable(Boolean billable) {
        this.billable = billable;
        return this;
    }

    public void setBillable(Boolean billable) {
        this.billable = billable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ThirdPartyFeature thirdPartyFeature = (ThirdPartyFeature) o;
        if (thirdPartyFeature.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, thirdPartyFeature.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ThirdPartyFeature{" +
            "id=" + id +
            ", featureCode='" + featureCode + "'" +
            ", featureName='" + featureName + "'" +
            ", billable='" + billable + "'" +
            '}';
    }
}
