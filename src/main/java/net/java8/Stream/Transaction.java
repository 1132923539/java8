package net.java8.Stream;

import java.util.Objects;

/**
 * @author eltons,  Date on 2018-09-06.
 */
public class Transaction {
    private String name;
    private Integer year;
    private Long trading;

    @Override
    public String toString() {
        return "Transaction{" +
                "name='" + name + '\'' +
                ", year=" + year +
                ", trading=" + trading +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(year, that.year) &&
                Objects.equals(trading, that.trading);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, year, trading);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getTrading() {
        return trading;
    }

    public void setTrading(Long trading) {
        this.trading = trading;
    }

    public Transaction() {
    }

    public Transaction(String name, Integer year, Long trading) {
        this.name = name;
        this.year = year;
        this.trading = trading;
    }
}
