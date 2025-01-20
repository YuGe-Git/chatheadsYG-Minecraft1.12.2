package cn.yuge.chatheads;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;
import java.lang.reflect.Field;

public class GuiChatHeads extends GuiNewChat {
    private static final String[] DRAWN_CHAT_LINES = new String[] { "drawnChatLines", "field_146253_i" };
    private static final String[] SCROLL_POS = new String[] { "scrollPos", "field_146250_j" };
    private static final Logger LOGGER = LogManager.getLogger("ChatHeads");
    
    public GuiChatHeads(Minecraft mcIn) {
        super(mcIn);
        LOGGER.info("聊天头像GUI已初始化");
    }

    @Override
    public void drawChat(int updateCounter) {
        Minecraft mc = Minecraft.getMinecraft();
        try {


            if (mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
                List<ChatLine> drawnChatLines = ReflectionHelper.getPrivateValue(
                    GuiNewChat.class,
                    this,
                    DRAWN_CHAT_LINES
                );
                
                // 获取滚动位置
                int scrollPos = ReflectionHelper.getPrivateValue(
                    GuiNewChat.class,
                    this,
                    SCROLL_POS
                );
                
                if (drawnChatLines != null && !drawnChatLines.isEmpty()) {

                    float opacity = mc.gameSettings.chatOpacity * 0.9F + 0.1F;
                    float chatScale = this.getChatScale();
                    int maxLines = this.getLineCount();
                    int lineHeight = mc.fontRenderer.FONT_HEIGHT;
                    
                    GlStateManager.pushMatrix();
                    //GlStateManager.translate(10.0F, -8.0F, 0.0F);  // 将文本向右移动，为头像留出空间
                    GlStateManager.translate(10.0F, -8.0F, 0.0F);  // 将文本向右移动，为头像留出空间
                    GlStateManager.scale(chatScale, chatScale, 1.0F);
                    GlStateManager.enableBlend();
                    
                    // 渲染文本
                    super.drawChat(updateCounter);
                    
                    GlStateManager.popMatrix();
                    
                    // 渲染头像
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(0.0F, -8.0F, 0.0F);
                    GlStateManager.scale(chatScale, chatScale, 1.0F);
//                    GlStateManager.scale(chatScale, chatScale, 2.0F);
                    GlStateManager.enableBlend();
                    GlStateManager.enableAlpha();
                    GlStateManager.tryBlendFuncSeparate(
                        GlStateManager.SourceFactor.SRC_ALPHA,
                        GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                        GlStateManager.SourceFactor.ONE,
                        GlStateManager.DestFactor.ZERO
                    );

                    // 从下往上渲染头像
                    for (int i = 0; i + scrollPos < drawnChatLines.size() && i < maxLines; i++) {
                        ChatLine chatline = drawnChatLines.get(i + scrollPos);
                        if (chatline != null) {
                            int j = updateCounter - chatline.getUpdatedCounter();
                            if (j < 200 || this.getChatOpen()) {
                                String message = chatline.getChatComponent().getUnformattedText();
                                
                                if (message.matches("<.*> .*")) {
                                    String playerName = message.substring(message.indexOf("<") + 1, message.indexOf(">"));
                                    
                                    if (mc.getConnection() != null && mc.getConnection().getPlayerInfo(playerName) != null) {
                                        ResourceLocation skin = mc.getConnection().getPlayerInfo(playerName).getLocationSkin();
                                        
                                        double d0 = 1.0D;
                                        if (!this.getChatOpen()) {
                                            if (j >= 180) {  // 只在最后20tick淡出
                                                d0 = 1.0D - (double)(j - 180) / 20.0D;
                                                d0 = MathHelper.clamp(d0, 0.0D, 1.0D);
                                            }
                                        }
                                        
                                        float chatOpacity = mc.gameSettings.chatOpacity;
                                        int alpha = (int)(255.0D * d0 * chatOpacity);  // 移除d0的平方项，使淡出更线性
                                        
                                        if (alpha > 3) {
                                            mc.getTextureManager().bindTexture(skin);
                                            GlStateManager.color(1.0F, 1.0F, 1.0F, alpha / 255.0F);
                                            
                                            int y = -1 - (i * lineHeight);
                                            
                                            // 渲染头部
                                            Gui.drawScaledCustomSizeModalRect(
                                                1,          // X坐标
                                                y,          // Y坐标
                                                8, 8,       // 从皮肤的位置
                                                8, 8,       // 截取大小
                                                9, 9,       // 渲染大小
                                                64, 64      // 皮肤纹理大小
                                            );
                                            
                                            // 渲染头盔层
                                            Gui.drawScaledCustomSizeModalRect(
                                                1,          // X坐标
                                                y,          // Y坐标
                                                40, 8,      // 从皮肤的位置
                                                8, 8,       // 截取大小
                                                9, 9,       // 渲染大小
                                                64, 64      // 皮肤纹理大小
                                            );
                                        }
                                    }
                                }
                            }
                        }
                    }
                    
                    GlStateManager.disableAlpha();
                    GlStateManager.disableBlend();
                    GlStateManager.popMatrix();
                }
            }
        } catch (Exception e) {
            LOGGER.error("渲染聊天时发生错误", e);
        }
    }
 
} 