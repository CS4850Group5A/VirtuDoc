package com.virtudoc.web.runner;

import com.virtudoc.web.annotation.Preseed;
import org.reflections.Reflections;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.Yaml;

import javax.persistence.Entity;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@Profile("dev-managed")
@Order(value=1)
public class DatabaseSeedRunner implements CommandLineRunner {
    @Autowired
    private BeanFactory beanFactory;

    @Override
    public void run(String... args) throws Exception {
        // Get the YAML configuration file.
        Map<String, Object> seedConfig = getSeedConfig();
        Set<Class<?>> entityClasses = getRepositoryClasses();
        for (Class<?> repositoryClass  : entityClasses) {
            // Do stuff
        }
    }
    // TODO: Test all this stuff in sandboxes before building on top of it.
    private Set<Class<?>> getRepositoryClasses() {
        Reflections reflections = new Reflections("com.virtudoc.web.repository");
        return reflections.getTypesAnnotatedWith(Preseed.class);
    }

    private Map<String, Object> getSeedConfig() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream("seeds.yaml");
        Yaml yaml = new Yaml();
        return yaml.load(is);
    }
}
