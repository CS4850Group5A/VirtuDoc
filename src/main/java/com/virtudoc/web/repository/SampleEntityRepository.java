package com.virtudoc.web.repository;

import com.virtudoc.web.entity.SampleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * Automatically creates all necessary SQL generators for CRUD actions.
 */
@Component
public interface SampleEntityRepository extends CrudRepository<SampleEntity, Integer> {
}
