package com.transformer.transformer.util;
import com.squareup.javapoet.*;
import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

public class PojoGenerator {

    public static void generatePojo(String className, Map<String, Object> fields, String outputDir) throws IOException {
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC);

        for (Map.Entry<String, Object> field : fields.entrySet()) {
            String fieldName = field.getKey();
            Class<?> fieldType = inferFieldType(field.getValue());

            classBuilder.addField(FieldSpec.builder(fieldType, fieldName, Modifier.PRIVATE).build());

            classBuilder.addMethod(MethodSpec.methodBuilder("get" + capitalize(fieldName))
                    .addModifiers(Modifier.PUBLIC)
                    .returns(fieldType)
                    .addStatement("return this.$L", fieldName)
                    .build());

            classBuilder.addMethod(MethodSpec.methodBuilder("set" + capitalize(fieldName))
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(fieldType, fieldName)
                    .addStatement("this.$L = $L", fieldName, fieldName)
                    .build());
        }

        JavaFile javaFile = JavaFile.builder("com.example.generated", classBuilder.build()).build();
        javaFile.writeTo(Paths.get(outputDir));
    }

    private static Class<?> inferFieldType(Object value) {
        if (value instanceof Integer) return Integer.class;
        if (value instanceof Double) return Double.class;
        if (value instanceof Boolean) return Boolean.class;
        if (value instanceof String) return String.class;
        return Object.class;
    }

    private static String capitalize(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
