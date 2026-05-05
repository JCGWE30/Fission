package org.lepigslayer.fission.Profile;

import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ProfileLoader {

    public static ProfileLoader fromString(String apiKey, File skinFolder){
        return new ProfileLoader(apiKey, skinFolder);
    }

    public static ProfileLoader fromFile(File apiKey, File skinFolder){
        try{
            String key = Files.readString(apiKey.toPath());
            return new ProfileLoader(key, skinFolder);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    private String apiKey;
    private File skinFolder;
    private OkHttpClient client = new OkHttpClient();

    private ProfileLoader(String apiKey, File skinFolder){
        this.apiKey = apiKey;
        this.skinFolder = skinFolder;
    }
}
