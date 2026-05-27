package com.p3.app.repository;

import com.p3.app.entity.NodeEntity;
import java.util.List;

public interface TreeDataRepository {
    NodeEntity saveRoot(String value);
    NodeEntity saveChild(Long parentId, String value);
    List<NodeEntity> findAll();
}