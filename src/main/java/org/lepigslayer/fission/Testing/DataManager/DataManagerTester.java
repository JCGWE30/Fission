package org.lepigslayer.fission.Testing.DataManager;

import org.lepigslayer.fission.DataManager.DataManager;

public class DataManagerTester {
    public static void runTest(){
        DataManager.registerStatic(StaticTestData.class,"","data");
        DataManager.registerInstanced(InstancedTestData.class,"instanced");


        var staticData = DataManager.getStatic(StaticTestData.class);
        var instanced1 = DataManager.getInstanced(InstancedTestData.class,"instancedClass1");
        var instanced2 = DataManager.getInstanced(InstancedTestData.class,"instancedClass2");
        var instanced3 = DataManager.getInstanced(InstancedTestData.class,"instancedClass3");
        var instanced4 = DataManager.getInstanced(InstancedTestData.class,"instancedClass4");
        var instanced5 = DataManager.getInstanced(InstancedTestData.class,"instancedClass5");
        var instanced6 = DataManager.getInstanced(InstancedTestData.class,"instancedClass6");
        var instanced7 = DataManager.getInstanced(InstancedTestData.class,"instancedClass7");

        DataManager.saveAll();
    }
}
