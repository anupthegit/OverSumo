package com.anupv.dojomadness.oversumo.repositories;

import com.anupv.dojomadness.oversumo.models.Ability;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbilityRepository extends CrudRepository<Ability, Integer> {
}
