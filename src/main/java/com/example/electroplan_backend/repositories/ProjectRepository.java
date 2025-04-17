package com.example.electroplan_backend.repositories;

import com.example.electroplan_backend.dto.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    // Простий варіант — всі проєкти юзера
    List<ProjectEntity> findAllByUserId(Long userId);

    // Розширений — з кастомним фільтром
    @Query("""
        SELECT p FROM ProjectEntity p WHERE
        (:userId IS NULL OR p.userId = :userId) AND
        (:titlePattern IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :titlePattern, '%'))) AND
        (:customerPattern IS NULL OR LOWER(p.customer) LIKE LOWER(CONCAT('%', :customerPattern, '%')))
        ORDER BY p.updatedAt DESC
    """)
    Page<ProjectEntity> findProjectsWithFilters(
            @Param("userId") Long userId,
            @Param("titlePattern") String titlePattern,
            @Param("customerPattern") String customerPattern,
            Pageable pageable
    );
}