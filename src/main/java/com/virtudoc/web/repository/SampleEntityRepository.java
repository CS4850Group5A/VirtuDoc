package com.virtudoc.web.repository;

import com.virtudoc.web.entity.SampleEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Automatically creates all necessary SQL generators for CRUD actions.
 */
public interface SampleEntityRepository extends CrudRepository<SampleEntity, Integer> {
}
