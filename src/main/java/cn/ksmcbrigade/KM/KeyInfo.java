package cn.ksmcbrigade.KM;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundChatPacket;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class KeyInfo {

    private int key;
    private String use;

    public KeyInfo(int key,String use){
        this.key = key;
        this.use = use;
    }

    public int getKey() {
        return key;
    }

    public String getUse() {
        return use;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setUse(String use) {
        this.use = use;
    }

    @Override
    public String toString() {
        return "KeyInfo{" +
                "key=" + key +
                ", use='" + use + '\'' +
                '}';
    }

    public void use() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if(this.use.contains(".") | this.use.contains("/")){
            Minecraft.getInstance().getConnection().getConnection().send(new ServerboundChatPacket(this.use));
        }
        else if(this.use.contains("*")){
            Minecraft.getInstance().getConnection().getConnection().send(new ServerboundChatPacket(this.use.replace("*","")));
        }
        else{
            if(has(this.use)){
                set(this.use,!get(this.use));
                Minecraft.getInstance().player.sendMessage(Component.nullToEmpty("§9"+I18n.get(this.use)+"§r:§6"+get(this.use)),Minecraft.getInstance().player.getUUID());
            }
            else{
                Minecraft.getInstance().player.sendMessage(Component.nullToEmpty(I18n.get("chat.km.warring").replace("{f}",this.use)),Minecraft.getInstance().player.getUUID());
            }
        }
    }

    public static boolean has(String name) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName("cn.ksmcbrigade.VM.Utils");
        Class<?>[] parameterTypes = new Class[]{String.class};
        Method method = clazz.getDeclaredMethod("hasMod", parameterTypes);
        method.setAccessible(true);
        Object instance = clazz.getDeclaredConstructor().newInstance();
        return (boolean) method.invoke(instance, name);
    }

    public static boolean get(String name) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName("cn.ksmcbrigade.VM.Utils");
        Class<?>[] parameterTypes = new Class[]{String.class};
        Method method = clazz.getDeclaredMethod("isEnabledMod", parameterTypes);
        method.setAccessible(true);
        Object instance = clazz.getDeclaredConstructor().newInstance();
        return (boolean) method.invoke(instance, name);
    }

    public static void set(String name,boolean enabled) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName("cn.ksmcbrigade.VM.Utils");
        Class<?>[] parameterTypes = new Class[]{String.class,boolean.class};
        Method method = clazz.getDeclaredMethod("SetMod", parameterTypes);
        method.setAccessible(true);
        Object instance = clazz.getDeclaredConstructor().newInstance();
        method.invoke(instance, name,enabled);
    }
}
