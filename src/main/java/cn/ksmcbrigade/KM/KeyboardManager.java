package cn.ksmcbrigade.KM;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forgespi.language.IModInfo;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@Mod("km")
@Mod.EventBusSubscriber
public class KeyboardManager {

    public static KeysManager keys;

    static {
        try {
            keys = new KeysManager("config/km-keys.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public KeyboardManager() {
        MinecraftForge.EVENT_BUS.register(this);
        if(!hasAPI()){
            throw new RuntimeException("KeyboardManager requires Vape Manager mod.");
        }
        for(KeyMapping key:keys.getKeyMappings()){
            ClientRegistry.registerKeyBinding(key);
        }
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        for(int i=0;i<keys.getKeyMappings().size();i++){
            if(keys.getKeyMappings().get(i).isDown()){
                keys.getKeys().get(i).use();
            }
        }
    }

    public static boolean hasAPI(){
        for(IModInfo modInfo: ModList.get().getMods()){
            if(modInfo.getModId().equals("vm")) {
                return true;
            }
        }
        return false;
    }
}
