package utils;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;

public class Functions {
    public static ScanResult getScanResultByPackageName(String packageName){
        ClassGraph graph = getClassGraph(packageName);
        return graph.scan();
    }

    public static ClassGraph getClassGraph(String packageName){
        return new ClassGraph().enableAllInfo().whitelistPackages(packageName);
    }
}
