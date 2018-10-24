package com.menesates.costmanagement.model;

import com.menesates.costmanagement.model.enums.CostType;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name = "Income_Expense")
@NamedQueries({
        @NamedQuery(name = "IncomeExpense.findAll", query = "SELECT ie FROM IncomeExpense ie"),
        @NamedQuery(name = "IncomeExpense.findIncome", query = "SELECT ie FROM IncomeExpense ie WHERE ie.amount > 0"),
        @NamedQuery(name = "IncomeExpense.findIncomeForUser", query = "SELECT ie FROM IncomeExpense ie WHERE ie.amount > 0 AND ie.user = :username"),
        @NamedQuery(name = "IncomeExpense.findExpence", query = "SELECT ie FROM IncomeExpense ie WHERE ie.amount < 0"),
        @NamedQuery(name = "IncomeExpense.findExpenceForUser", query = "SELECT ie FROM IncomeExpense ie WHERE ie.amount < 0 AND ie.user = :username"),
        @NamedQuery(name = "IncomeExpense.findAllForUser", query = "SELECT ie FROM IncomeExpense ie WHERE ie.user = :username"),
})
public class IncomeExpense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user;
    //@NotEmpty
    @Column(columnDefinition="Decimal(10,2) default '0.00'")
    private Double amount;
    private Integer currencyCode; // tr - 949
    private String explanation;
    @Enumerated(EnumType.STRING)
    @Column(length = 32, nullable = false)
    private CostType costType;
    @Column(nullable = false)
    private Date date;

    public IncomeExpense() {
    }

    public IncomeExpense(User user,
                         @NotEmpty Double amount,
                         int currencyCode,
                         String explanation,
                         @NotEmpty CostType costType,
                         @NotEmpty Date date) {
        this.user = user;
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.explanation = explanation;
        this.costType = costType;
        this.date = date;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(int currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public CostType getCostType() {
        return costType;
    }

    public void setCostType(CostType costType) {
        this.costType = costType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "IncomeExpense{" +
                "id=" + id +
                ", user=" + user +
                ", amount=" + amount +
                ", currencyCode=" + currencyCode +
                ", explanation='" + explanation + '\'' +
                ", costType=" + costType +
                ", date=" + date +
                '}';
    }
}
