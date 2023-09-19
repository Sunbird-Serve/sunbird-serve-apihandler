package com.evidyaloka.vneed.dao;

import com.evidyaloka.vneed.dao.model.Vneed;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface VneedRepository extends CrudRepository<Vneed, String> {}
