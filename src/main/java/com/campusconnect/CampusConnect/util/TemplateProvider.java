package com.campusconnect.CampusConnect.util;

import com.campusconnect.CampusConnect.enums.EmailType;

public class TemplateProvider {

    public static String getSubject(EmailType type) {
        return switch (type) {
            case WELCOME -> "🎉 Welcome to CampusConnect - Your Journey Starts Here!";
            case POST_NOTIFIER -> "🚀 New Post Alert: Stay Updated!";
            case WARNING -> "⚠️ Important Notice: Inappropriate Content Alert";
        };
    }

    public static String getBody(EmailType type, String... placeholders) {
        return switch (type) {
            case WELCOME -> String.format(
                    "Hey %s! 👋\n\n" +
                            "Welcome to **CampusConnect** – your gateway to exciting opportunities, insightful discussions, and a growing network of students and professionals! 🌍✨\n\n" +
                            "We're thrilled to have you onboard. Start exploring, connect with peers, and make the most of your journey!\n\n" +
                            "**Happy Networking!** 🎯\n\n" +
                            "Best,\n" +
                            "CampusConnect Team 🚀",
                    placeholders[0]
            );

            case POST_NOTIFIER -> String.format(
                    "Hey %s! 🎉\n\n" +
                            "A new post just went live: **\"%s\"** 🔥\n\n" +
                            "Don't miss out! Engage with the latest updates, drop your thoughts, and stay connected with your community. 💬\n\n" +
                            "**Click here to check it out!**\n\n" +
                            "Best,\n" +
                            "CampusConnect Team 🚀",
                    placeholders[0], placeholders[1]
            );

            case WARNING -> String.format(
                    "Dear %s,\n\n" +
                            "🚨 **We Need to Talk About Your Recent Post** 🚨\n\n" +
                            "We've noticed that your recent post contains content that may be inappropriate or against our community guidelines. We encourage open discussions, but we also need to maintain a respectful and positive space for everyone. 🤝\n\n" +
                            "**If this was a misunderstanding**, we’d love to hear from you. Otherwise, please ensure that your posts follow our guidelines to keep CampusConnect a safe and engaging platform for all users.\n\n" +
                            "**Repeated violations may lead to further action, including content removal or account suspension.**\n\n" +
                            "If you believe this is an error, feel free to reach out to our support team.\n\n" +
                            "Best,\n" +
                            "CampusConnect Team 🚀",
                    placeholders[0]
            );
        };
    }
}
