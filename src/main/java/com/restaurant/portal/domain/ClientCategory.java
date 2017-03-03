package com.restaurant.portal.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ClientCategory.
 */
@Entity
@Table(name = "client_category")
public class ClientCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Client client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public ClientCategory client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClientCategory clientCategory = (ClientCategory) o;
        if (clientCategory.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, clientCategory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ClientCategory{" +
            "id=" + id +
            '}';
    }
}
