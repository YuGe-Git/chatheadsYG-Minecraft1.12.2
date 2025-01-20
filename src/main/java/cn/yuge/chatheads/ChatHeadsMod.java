package cn.yuge.chatheads;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;
import net.minecraft.util.text.TextComponentString;

@Mod(modid = ChatHeadsMod.MODID, name = ChatHeadsMod.NAME, version = ChatHeadsMod.VERSION)
public class ChatHeadsMod
{
    public static final String MODID = "chatheads";
    public static final String NAME = "Chat Heads";
    public static final String VERSION = "1.0";

    private static Logger logger;
    private boolean hasInitialized = false;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        logger.info("[Chat Heads]加载成功！");
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        logger.info("Chat Heads Mod post initialization");
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (!hasInitialized && event.phase == TickEvent.Phase.END && Minecraft.getMinecraft().player != null) {
            try {
                // 只输出一次初始化日志
                GuiNewChat oldChat = ReflectionHelper.getPrivateValue(
                    GuiIngame.class,
                    Minecraft.getMinecraft().ingameGUI,
                    "persistentChatGUI", "field_73840_e"
                );
                
                if (oldChat != null) {
                    ReflectionHelper.setPrivateValue(
                        GuiIngame.class,
                        Minecraft.getMinecraft().ingameGUI,
                        new GuiChatHeads(Minecraft.getMinecraft()),
                        "persistentChatGUI", "field_73840_e"
                    );
                    logger.info("聊天GUI替换成功");
                    hasInitialized = true;
                }
            } catch (Exception e) {
                logger.error("替换聊天GUI时发生错误: {}", e.getMessage());
                hasInitialized = true; // 防止持续尝试
            }
        }
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
//        if (!event.isCanceled()) {
//            String message = event.getMessage().getUnformattedText();
//            if (message.matches("<.*> .*")) {
//                // 在玩家名字前添加空格
//                String formattedText = event.getMessage().getFormattedText();
//                formattedText = "█" + formattedText;  // 减少为2个空格，与头像大小更匹配
//                event.setMessage(new TextComponentString(formattedText));
//            }
//        }
    }
}
