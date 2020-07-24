package org.sid.lightecomv1.repositories;

import org.sid.lightecomv1.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin(origins = "*")
public interface CategoryRepository extends JpaRepository<Category,Long> {
}
