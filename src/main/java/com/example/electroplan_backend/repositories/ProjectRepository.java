package com.example.electroplan_backend.repositories;

import com.example.electroplan_backend.dto.entities.ProjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;


@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    @Query("""
                    SELECT c FROM ProjectEntity c WHERE 
                    (:manufacturerPattern IS NULL OR c.manufacturer ILIKE :manufacturerPattern) AND 
                    (:bodyType IS NULL OR c.bodyType = :bodyType) AND 
                    (:mileageFrom IS NULL OR c.mileage >= :mileageFrom) AND 
            (:priceFrom IS NULL OR c.price >= :priceFrom) AND 
                (:priceTo IS NULL OR c.price <= :priceTo) AND 
                    (:mileageTo IS NULL OR c.mileage <= :mileageTo) AND 
                    (:displayNamePattern IS NULL OR c.displayName ILIKE :displayNamePattern) AND 
                    c.isAvailable = true
            """)
    Page<ProjectEntity> findProjectsWithFilters(@Param("manufacturerPattern") String manufacturerPattern,
                                        @Param("bodyType") String bodyType,
                                        @Param("priceFrom") Integer priceFrom,
                                        @Param("priceTo") Integer priceTo,
                                        @Param("mileageFrom") Integer mileageFrom,
                                        @Param("mileageTo") Integer mileageTo,
                                        @Param("displayNamePattern") String displayNamePattern,
                                        Pageable pageable);

    Collection<ProjectEntity> findAllBySellerId(Long id);
}
