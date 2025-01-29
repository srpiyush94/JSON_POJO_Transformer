package com.transformer.transformer.repository;

import com.transformer.transformer.model.JsonDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JsonDocumentRepository extends MongoRepository<JsonDocument, String> {
}