# Chat Heads Mod (1.12.2)

一个为Minecraft聊天框添加玩家头像的模组。

## 🌟 功能介绍

- 在聊天消息前显示发言者的头像
- 头像使用玩家的皮肤（包括头盔层）
- 支持聊天框滚动
- 平滑的淡出动画效果
- 完全兼容原版聊天功能

## 📥 安装说明

1. 确保已安装Minecraft Forge 1.12.2 (推荐版本: 14.23.5.2847)
2. 下载最新版本的Chat Heads模组
3. 将模组文件放入Minecraft的mods文件夹
4. 启动游戏即可

## 🛠️ 开发环境

- Minecraft 1.12.2
- Forge 14.23.5.2847
- Java 8
- Gradle

## 🔧 构建说明

1. 克隆仓库
```bash
git clone https://github.com/YuGe-Git/chatheadsYG-Minecraft1.12.2.git
```

2. 设置开发环境
```bash
./gradlew setupDecompWorkspace
```

3. 构建模组
```bash
./gradlew build
```

编译后的模组文件将位于 `build/libs` 目录。

## 💡 特性说明

- 头像大小：9x9像素
- 位置：聊天消息左侧
- 透明度：与聊天文本同步
- 淡出效果：在消息消失前20tick开始淡出

## ⚠️ 已知问题

- 聊天文本会向右偏移约10像素以为头像留出空间
- 可能与其他修改聊天GUI的模组产生冲突

## 🔄 更新日志

### 版本 1.0
- 初始发布
- 实现基本的头像显示功能
- 添加平滑淡出效果
- 支持聊天框滚动

## 📝 许可证

本项目采用 MIT 许可证。详见 [LICENSE](LICENSE) 文件。

## 🤝 贡献

欢迎提交Issue和Pull Request！如果你发现了bug或有新的想法，请在[Issues](https://github.com/YuGe-Git/chatheadsYG-Minecraft1.12.2/issues)页面提出。

## 📞 联系方式

- 作者：YuGe
- GitHub: [@YuGe-Git](https://github.com/YuGe-Git)

## 🙏 鸣谢

- Minecraft Forge 团队
- 原版 [Chat Heads](https://github.com/dzwdz/chat_heads) 模组的创意启发

---

如果你觉得这个模组有用，欢迎给个⭐支持！

