# Bot01 🤖

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)](https://github.com/Arinonia/Bot01)
[![Gradle](https://img.shields.io/badge/gradle-v7.3-blue)](https://gradle.org/)
[![License](https://img.shields.io/badge/license-MIT-green)](LICENSE)

Welcome to **Bot01**, a Discord bot built by **Arinonia**. This bot is designed to help you easily access various Zone01 projects subjects and audits directly from Gitea server, scraping and sending them as markdown to Discord channels.

## 🌟 Features
- Slash commands to fetch subjects and audits 📄

## 🌈 Future Features
 - Plugin API: In future versions, I plan to implement an API for Bot01, similar to Spigot for Minecraft. This will allow developers to create plugins for the bot, enhancing its functionality and versatility.


## 🛠️ Technologies
- **Language**: Java 17 ☕
- **Build Tool**: Gradle ⚙️
- **Discord API**: JDA (Java Discord API) 📡
- **Web Scraping**: Jsoup 🌐

## 🚀 Setup Instructions
1. Clone this repository:
    ```bash
    git clone https://github.com/Arinonia/Bot01.git
    cd Bot01
    ```
2. Build the project with Gradle:
    ```bash
    ./gradlew build
    ```
3. Create a `config.json` file in the root directory and add your Discord bot token:
    ```json
    {
      "token": "YOUR_DISCORD_BOT_TOKEN"
    }
    ```
4. Run the bot:
    ```bash
    ./gradlew run
    ```

## 📋 Commands
- `/subject name [type]`: Fetches a project subject and sends it to the Discord channel.
- `/audit name [type]`: Fetches an audit and sends it to the Discord channel.

Type is temporary and is only useful for the java branch, which is in a subfolder on the repo. 

## 📚 Libraries
- [JDA](https://github.com/DV8FromTheWorld/JDA) - Discord API wrapper
- [Jsoup](https://jsoup.org/) - Web scraping
- [Gson](https://github.com/google/gson) - JSON

## 📝 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

