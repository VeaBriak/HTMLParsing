import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/*Написать код, который будет получать список абсолютных путей изображений с главной страницы сайта lenta.ru
и скачивать эти изображения в заданную папку.*/


public class Main
{
    public static void main(String[] args) {
        String address = "https://lenta.ru/";
        List<String> list = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        Document document = null;
        try {
            document = Jsoup.connect(address).get();
        } catch (IOException e) {
            System.out.println("Указан неверный адрес сайта или нет соединения с интернетом!");
            e.printStackTrace();
        }
        Elements elements = document.select("img");
        for (Element element : elements) {
            String image = element.attr("src");
            if (image.startsWith("https")) {
                list.add(image);
            }
        }
        System.out.println("Введите путь к папке, где необходимо сохранить изображения:");
        String pathToDirectory = scanner.nextLine() + "\\";
        download(list, pathToDirectory);
    }

    private static void download(List<String> list, String pathToDirectory) {
        for (String path : list) {
            try {
                InputStream in = new URL(path).openStream();
                Files.createDirectories(Paths.get(pathToDirectory));
                Files.copy(in, Paths.get(pathToDirectory + new File(path).getName())
                        , StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Сохранение закончено");
            } catch (IOException ex) {
                System.out.println("Вы ввели некоректный путь к папке!");
                ex.printStackTrace();
            }
        }
    }
}
