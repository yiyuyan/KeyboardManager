package cn.ksmcbrigade.KM;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class KeysManager {
    private final Path path;
    private ArrayList<KeyInfo> keys = new ArrayList<>();
    private ArrayList<KeyMapping> keyMappings = new ArrayList<>();

    public KeysManager(String path) throws IOException {
        this.path = Paths.get(path);
        if (!new File(path).exists()) {
            Files.write(this.path,new JsonObject().toString().getBytes());
        }
        JsonObject json = JsonParser.parseString(Files.readString(this.path)).getAsJsonObject();
        for(String key:json.keySet()){
            KeyInfo keyInfo = new KeyInfo(json.get(key).getAsInt(),key);
            keys.add(keyInfo);
            keyMappings.add(new KeyMapping(keyInfo.getUse(), KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, keyInfo.getKey(),"key.km.cat"));
        }
    }

    public Path getPath() {
        return path;
    }

    public ArrayList<KeyInfo> getKeys() {
        return keys;
    }

    public ArrayList<KeyMapping> getKeyMappings() {
        return keyMappings;
    }

    @Override
    public String toString() {
        return "KeysManager{" +
                "path=" + path +
                ", keys=" + keys +
                '}';
    }
}
