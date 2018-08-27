/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.buchungstrainer.set.rw;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author Adrian Wilhelm
 */
public class Account {

    private int id = 0;
    private String name = "";
    private BigDecimal ab = new BigDecimal(0);
    private BigDecimal value = new BigDecimal(0);
    private Type type;
    private boolean minusAccount;

    public Account() {
    }

    public Account(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Account(int id, String name, int initialValue, int value) {
        this.id = id;
        this.name = name;
        this.value = new BigDecimal(value);
    }

    public Account(int id, String name, int initialValue, int value, Type type) {
        this.id = id;
        this.name = name;
        this.value = new BigDecimal(value);
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAb() {
        return ab;
    }

    public void setAb(BigDecimal ab) {
        this.ab = ab;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public void addValue(BigDecimal value) {
        this.value = this.value.add(value);
    }

    public void subValue(BigDecimal value) {
        this.value = this.value.add(value);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isMinusAccount() {
        return minusAccount;
    }

    public void setIsMinusAccount(boolean itIs) {
        this.minusAccount = itIs;
    }

    @Override
    public String toString() {
        if (id == 0 && name.equals("")) {
            return "-";
        }
        return id + " " + name;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Account other = (Account) obj;
        if (this.id != other.id) {
            return false;
        }
        return Objects.equals(this.name, other.name);
    }
}
