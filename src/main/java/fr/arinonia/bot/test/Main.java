package fr.arinonia.bot.test;

import fr.arinonia.bot.utils.Constants;
import fr.arinonia.bot.utils.ProjectType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.IOException;
/**
 * Just my trash Class for testing dumb thing
 */
public class Main {

    public static void main(String[] args) {

        if (args.length == 0) {
            //change my mind type is useless all the project are in the same directory except Java :c
            System.out.println("need the language of the project, write go if it's a free one");
            return;
        }

        final String arg = args[0].toUpperCase();
        final ProjectType type = ProjectType.valueOf(arg);

        final String url = Constants.ROOT_SUBJECTS_URL + type.getType() + "/abort/README.md";

        try {
            final Document doc = Jsoup.connect(url).get();
            final Element element = doc.getElementsByClass("file-view markup markdown").first();
            System.out.println(formatToMarkdown(element));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }


    public static String formatToMarkdown(final Element element) {
        final StringBuilder markdown = new StringBuilder();

        for (final Node node : element.childNodes()) {
            if (node instanceof Element) {
                final Element elem = (Element) node;

                switch (elem.tagName()) {
                    case "h2":
                        markdown.append("## ").append(elem.text()).append("\n\n");
                        break;
                    case "h3":
                        markdown.append("### ").append(elem.text()).append("\n\n");
                        break;
                    case "p":
                        markdown.append(elem.text()).append("\n\n");
                        break;
                    case "code":
                        markdown.append("`").append(elem.text()).append("`");
                        break;
                    case "pre":  // Préformater les blocs de code
                        final Elements codeBlocks = elem.select("code");
                        if (!codeBlocks.isEmpty()) {
                            final String languageClass = codeBlocks.attr("class");
                            final String language = languageClass.contains("go") ? "go" :
                                    languageClass.contains("console") ? "console" : "";
                            markdown.append("```").append(language).append("\n")
                                    .append(codeBlocks.text()).append("\n```\n\n");
                        }
                        break;
                    default:
                        markdown.append(elem.text()).append("\n");
                        break;
                }
            } else if (node instanceof TextNode) {
                markdown.append(((TextNode) node).text()).append("\n");
            }
        }
        return markdown.toString();
    }
}
