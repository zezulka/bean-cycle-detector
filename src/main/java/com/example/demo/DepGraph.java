package com.example.demo;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DepGraph {
    private final Map<String, Set<String>> nodes;

    private DepGraph(Map<String, Set<String>> nodes) {
        this.nodes = nodes;
    }

    public Set<String> calculateCycles() {
        Set<String> visited = new HashSet<>();
        return nodes.keySet().stream()
                .map(node -> calculateCycles(node, visited, Collections.emptyList()))
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    private Set<String> calculateCycles(String node, Set<String> visited, List<String> path) {
        List<String> newPath = new LinkedList<>(path);
        newPath.add(node);

        if (path.contains(node)) {
            List<String> cycle = newPath.subList(path.indexOf(node), path.size());
            return Collections.singleton(String.join("->", cycle));
        }

        if (visited.contains(node)) {
            return Collections.emptySet();
        }
        visited.add(node);
        Set<String> deps = nodes.getOrDefault(node, Collections.emptySet());
        return deps.stream()
                .map(dep -> calculateCycles(dep, visited, newPath))
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    public static DepGraph buildDependencyGraph(ConfigurableApplicationContext applicationContext) {
        ConfigurableListableBeanFactory factory = applicationContext.getBeanFactory();
        Map<String, Set<String>> beanDeps = Arrays.stream(factory.getBeanDefinitionNames())
                .filter(beanName -> !factory.getBeanDefinition(beanName).isAbstract())
                .collect(Collectors.toMap(
                        Function.identity(),
                        beanName -> new HashSet<>(Arrays.asList(factory.getDependenciesForBean(beanName)))
                ));

        return new DepGraph(beanDeps);
    }
}
