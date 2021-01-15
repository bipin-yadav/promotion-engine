package com.demo.promotion.engine.repository;

import com.demo.promotion.engine.entities.Sku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.UUID;

/**
 * Spring Data DAO (repository) for {@link Sku}.
 */
@RepositoryRestResource(collectionResourceRel = "skus", path = "skus")
public interface SkuRepository extends JpaRepository<Sku, UUID> {
    List<Sku> findBySkuId(@Param("sku") String skuId);
}
