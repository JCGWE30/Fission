package org.lepigslayer.fission.DataManager;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.function.Function;

public class InstancedData<T> {
    File fileRoot;
    Class<T> dataClass;

    private Map<String, T> data;

    public InstancedData(Class<T> clazz, File fileRoot){
        this.fileRoot = fileRoot;
        this.dataClass = clazz;
        this.data = new HashMap<>();
    }

    <C> C getData(Class<C> clazz, String name, Gson gson) {
        Object dataObject = data.computeIfAbsent(name, (k) -> this.loadData(k, gson));

        if (!clazz.isInstance(dataObject)) {
            throw new IllegalArgumentException("Data under key " + name + " is not of type " + clazz.getName());
        }

        return clazz.cast(dataObject);
    }

    <C> List<C> getAllData(Class<C> clazz, Gson gson) {
        List<C> dataList = new ArrayList<>();
        for (String fileName : getFileNames()) {
            dataList.add(getData(clazz, fileName, gson));
        }
        return dataList;
    }

    boolean hasData(String name) {
        if(this.data.containsKey(name))
            return true;

        return Arrays.asList(getFileNames()).contains(name);
    }

    private String[] getFileNames(){
        if (!fileRoot.exists()) {
            fileRoot.mkdirs();
        }

        return fileRoot.list();
    }

    private T loadData(String name, Gson gson){
        if (!fileRoot.exists()) {
            fileRoot.mkdirs();
        }

        File file = new File(fileRoot, name + ".json");

        if(!file.exists())
            return newClass();

        try {
            String rawJson = new String(Files.readAllBytes(file.toPath()));
            T data = gson.fromJson(rawJson, dataClass);

            if(data instanceof DataHolder holder)
                holder.onDataLoaded();

            return data;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    void saveData(Gson gson) throws IOException{
        for (Map.Entry<String, T> entry : data.entrySet()) {
            String name = entry.getKey();
            T dataObject = entry.getValue();

            if (!fileRoot.exists()) {
                fileRoot.mkdirs();
            }

            File file = new File(fileRoot, name + ".json");
            file.createNewFile();

            Files.write(file.toPath(), gson.toJson(dataObject).getBytes());
        }
    }

    private T newClass(){
        try{
            return dataClass.getDeclaredConstructor().newInstance();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}
