package fr.arinonia.bot.utils;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

public class FormatMarkdown {

    public static String formatToMarkdown(final Element element) {
        final StringBuilder markdown = new StringBuilder();

        for (final Node node : element.childNodes()) {
            if (node instanceof final Element elem) {
                markdown.append(formatElement(elem));
            } else if (node instanceof TextNode) {
                markdown.append(((TextNode) node).text()).append("\n");
            }
        }

        return markdown.toString();
    }

    private static String formatElement(final Element elem) {
        return switch (elem.tagName()) {
            case "h2" -> formatHeader(elem, 2);
            case "h3" -> formatHeader(elem, 3);
            case "p" -> formatParagraph(elem);
            case "code" -> formatInlineCode(elem);
            case "pre" -> formatCodeBlock(elem);
            default -> elem.text() + "\n";
        };
    }

    private static String formatHeader(final Element elem, int level) {
        return "#".repeat(level) + " " + elem.text() + "\n\n";
    }

    private static String formatParagraph(final Element elem) {
        return elem.text() + "\n\n";
    }

    private static String formatInlineCode(final Element elem) {
        return "`" + elem.text() + "`";
    }

    private static String formatCodeBlock(final Element elem) {
        final Elements codeBlocks = elem.select("code");

        if (!codeBlocks.isEmpty()) {
            final String languageClass = codeBlocks.attr("class");
            final String language = getCodeLanguage(languageClass);
            return "```" + language + "\n" + codeBlocks.text() + "\n```\n\n";
        }

        return "";
    }

    private static String getCodeLanguage(final String languageClass) {
        if (languageClass.contains("go")) {
            return "go";
        } else if (languageClass.contains("console")) {
            return "console";
        } else if (languageClass.contains("javascript")) {
            return "javascript";
        } else if (languageClass.contains("java")) {
            return "java";
        } else if (languageClass.contains("rust")) {
            return "rust";
        } else {
            return "";
        }
    }

}
