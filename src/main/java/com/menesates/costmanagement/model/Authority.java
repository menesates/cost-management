package com.menesates.costmanagement.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "Authorities")
@NamedQueries({
        @NamedQuery(name = "Authority.findAll", query = "SELECT a FROM Authority a"),
        @NamedQuery(name = "Authority.findByUserAuthorities", query = "SELECT a FROM Authority a WHERE a.user = :username"),
        @NamedQuery(name = "Authority.findByAuthorityName", query = "SELECT a FROM Authority a WHERE a.authority = :authority"),
        @NamedQuery(name = "Authority.deleteByUsername", query = "DELETE FROM Authority WHERE username = :username")
})
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user;
    @NotEmpty
    @Column(nullable = false)
    private String authority;

    public Authority() {
    }

    public Authority(User user, @NotEmpty String authority) {
        this.user = user;
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "Authority{" +
                "id=" + id +
                ", user=" + user +
                ", authority='" + authority + '\'' +
                '}';
    }
}
