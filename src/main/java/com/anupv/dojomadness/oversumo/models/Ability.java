package com.anupv.dojomadness.oversumo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "abilities")
public class Ability {

    @Id
    private int id;

    private String name;

    @Column(length = 10000)
    private String description;

    private boolean is_ultimate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hero_id")
    @JsonIgnore
    private Hero hero;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isUltimate() {
        return is_ultimate;
    }

    public void setUltimate(boolean is_ultimate) {
        this.is_ultimate = is_ultimate;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }
}
