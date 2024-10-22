package com.laberit.sina.bootcamp.extra.awesomefinalproject.repository;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.model.UnauthorizedAccess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnauthorizedAccessRepository extends JpaRepository<UnauthorizedAccess, Long> {
}