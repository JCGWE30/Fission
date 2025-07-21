package org.lepigslayer.fission.DataManager;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class StaticData<T> {
    File fileRoot;
    String name;
    Class<T> dataClass;

    T data;

    public StaticData(Class<T> clazz, File fileRoot, String name){
        this.fileRoot = fileRoot;
        this.name = name;
        this.dataClass = clazz;
        try {
            data = clazz.getDeclaredConstructor().newInstance();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    <C> C getData(Class<C> clazz){
        if(!data.getClass().isAssignableFrom(clazz))
            throw new IllegalArgumentException("Class " + clazz.getName() + " is not the correct type");
        return clazz.cast(data);
    }

    void saveData(Gson gson) throws IOException {
        if (!fileRoot.exists()) {
            fileRoot.mkdirs();
        }

        File file = new File(fileRoot, name + ".json");
        file.createNewFile();

        Files.write(file.toPath(), gson.toJson(data).getBytes());
    }

    void loadData(Gson gson) throws IOException {
        if (!fileRoot.exists()) {
            fileRoot.mkdirs();
        }

        File file = new File(fileRoot, name + ".json");

        if(!file.exists())
            return;

        String rawJson = new String(Files.readAllBytes(file.toPath()));

        data = gson.fromJson(rawJson, dataClass);

        if(data instanceof DataHolder holder)
            holder.onDataLoaded();
    }
}
