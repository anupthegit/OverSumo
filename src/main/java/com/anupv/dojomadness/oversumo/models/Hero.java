package com.anupv.dojomadness.oversumo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "heroes")
public class Hero {

    @Id
    private int id;

    private String name;

    private String real_name;

    private int health;

    private int armour;

    private int shield;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hero", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Ability> abilities;

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

    public String getRealName() {
        return real_name;
    }

    public void setRealName(String real_name) {
        this.real_name = real_name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getArmour() {
        return armour;
    }

    public void setArmour(int armour) {
        this.armour = armour;
    }

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public Set<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(Set<Ability> abilities) {
        this.abilities = abilities;
    }
}
