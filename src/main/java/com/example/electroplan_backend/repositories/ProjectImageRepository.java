package com.example.electroplan_backend.repositories;

import com.example.electroplan_backend.dto.documents.ProjectImage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectImageRepository extends MongoRepository<ProjectImage, String> {

    ProjectImage findByProjectId(Long carId);

    void deleteAll();
}
