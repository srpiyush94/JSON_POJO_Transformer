package com.transformer.transformer.service;

import com.transformer.transformer.model.JsonDocument;
import com.transformer.transformer.repository.JsonDocumentRepository;
import com.transformer.transformer.util.PojoGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class PojoService {

    private final JsonDocumentRepository repository;

    @Value("${generated.pojo.output.dir:./generated-sources}")
    private String outputDir;

    public PojoService(JsonDocumentRepository repository) {
        this.repository = repository;
    }

    public void saveAndGeneratePojo(JsonDocument document)  {
        // Save JSON document to MongoDB
        repository.save(document);

        // Generate POJO dynamically from JSON data
        PojoGenerator.generatePojo(document.getClassName(), document.getJsonData(), outputDir);
    }
}
