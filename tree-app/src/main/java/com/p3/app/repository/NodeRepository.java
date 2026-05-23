package com.p3.app.repository;

import com.p3.app.entity.NodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NodeRepository extends JpaRepository<NodeEntity, Long> {

    List<NodeEntity> findByParentId(Long parentId);
}
