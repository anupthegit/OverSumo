package com.anupv.dojomadness.oversumo.repositories;

import com.anupv.dojomadness.oversumo.models.Hero;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroRepository extends CrudRepository<Hero, Integer> {
}
