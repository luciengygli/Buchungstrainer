/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.buchungstrainer.set.rw;

/**
 *
 * @author Adrian Wilhelm
 */
public enum Type {

    TYPE, ACTIVES, PASSIVES, EFFORT, EARNING;

    @Override
    public String toString() {
        switch (this) {
            case TYPE:
                return "Typ";
            case ACTIVES:
                return "Aktiven";
            case PASSIVES:
                return "Passiven";
            case EFFORT:
                return "Aufwand";
            case EARNING:
                return "Ertrag";
            default:
                return "";
        }
    }

}
