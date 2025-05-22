package com.example.electroplan_backend.repositories;

import com.example.electroplan_backend.dto.entities.ProjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    @Query(nativeQuery = true, value = """
        SELECT id, created_at, customer, data, name, updated_at, user_email 
        FROM projects p 
        WHERE p.user_email = :userEmail 
        AND (:titlePattern IS NULL OR LOWER(p.name::text) LIKE LOWER(CONCAT('%', :titlePattern, '%'))) 
        AND (:customerPattern IS NULL OR LOWER(p.customer::text) LIKE LOWER(CONCAT('%', :customerPattern, '%')))
    """,
    countQuery = """
        SELECT count(*) 
        FROM projects p 
        WHERE p.user_email = :userEmail 
        AND (:titlePattern IS NULL OR LOWER(p.name::text) LIKE LOWER(CONCAT('%', :titlePattern, '%'))) 
        AND (:customerPattern IS NULL OR LOWER(p.customer::text) LIKE LOWER(CONCAT('%', :customerPattern, '%')))
    """)
    Page<ProjectEntity> findProjectsWithFilters(
            @Param("userEmail") String userEmail,
            @Param("titlePattern") String titlePattern,
            @Param("customerPattern") String customerPattern,
            Pageable pageable
    );
}