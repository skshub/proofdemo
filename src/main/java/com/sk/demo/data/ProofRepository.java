package com.sk.demo.data;

import com.sk.demo.data.entity.NameKeyValueEntity;
import com.sk.demo.data.entity.NameKeyValueKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProofRepository extends CrudRepository<NameKeyValueEntity, NameKeyValueKey>{
}
