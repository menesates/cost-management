package com.menesates.costmanagement.model;

import com.menesates.costmanagement.model.enums.Status;

import javax.persistence.*;

@Entity
@Table(name = "Budget")
@NamedQueries({
        @NamedQuery(name = "Budget.findAll", query = "SELECT b FROM Budget b"),
        @NamedQuery(name = "Budget.findAllForUser", query = "SELECT b FROM Budget b WHERE b.user = :username"),
        @NamedQuery(name = "Budget.deleteByUsername", query = "DELETE FROM Budget WHERE username = :username")
})
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "username", nullable = false)
    private User user;
    @Enumerated(EnumType.STRING)
    @Column(length = 32)
    private Status status;
    @Column(columnDefinition="Decimal(10,2) default '0.00'")
    private Double lowerLimit;
    @Column(columnDefinition="Decimal(10,2) default '0.00'")
    private Double upperLimit;
    @Column(columnDefinition="Decimal(10,2) default '0.00'")
    private Double balance;
    private Integer currencyCode; // tr-949

    public Budget() {
    }

    public Budget(User user,
                  Status status,
                  Double lowerLimit,
                  Double upperLimit,
                  Double balance,
                  Integer currencyCode) {
        this.user = user;
        this.status = status;
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.balance = balance;
        this.currencyCode = currencyCode;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Double getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(Double lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public Double getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(Double upperLimit) {
        this.upperLimit = upperLimit;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Integer getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(Integer currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public String toString() {
        return "Budget{" +
                "id=" + id +
                ", user=" + user +
                ", status=" + status +
                ", lowerLimit=" + lowerLimit +
                ", upperLimit=" + upperLimit +
                ", balance=" + balance +
                ", currencyCode=" + currencyCode +
                '}';
    }
}
