package com.menesates.costmanagement.model;

import com.menesates.costmanagement.model.enums.CostType;
import com.menesates.costmanagement.model.enums.Repetition;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name = "RecurrentIncomeExpense")
public class RecurrentIncomeExpense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "username")
    private User user;
    @NotEmpty
    private Date startDate; //
    @NotEmpty
    @Enumerated(EnumType.STRING)
    @Column(length = 32)
    private Repetition repetition;
    @NotEmpty
    @Column(columnDefinition="Decimal(10,2) default '0.00'")
    private Double amount;
    private Integer currencyCode; // tr - 949
    private String explanation;
    @Enumerated(EnumType.STRING)
    @Column(length = 32)
    private CostType costType;

    public RecurrentIncomeExpense() {
    }

    public RecurrentIncomeExpense(User user,
                                  @NotEmpty Date startDate,
                                  @NotEmpty Repetition repetition,
                                  @NotEmpty Double amount,
                                  Integer currencyCode,
                                  String explanation,
                                  CostType costType) {
        this.user = user;
        this.startDate = startDate;
        this.repetition = repetition;
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.explanation = explanation;
        this.costType = costType;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Repetition getRepetition() {
        return repetition;
    }

    public void setRepetition(Repetition repetition) {
        this.repetition = repetition;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(Integer currencyCode) {
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

    @Override
    public String toString() {
        return "RecurrentIncomeExpense{" +
                "id=" + id +
                ", user=" + user +
                ", startDate=" + startDate +
                ", repetition=" + repetition +
                ", amount=" + amount +
                ", currencyCode=" + currencyCode +
                ", explanation='" + explanation + '\'' +
                ", costType=" + costType +
                '}';
    }
}
