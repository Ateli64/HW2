import java.util.*;

// Интерфейс
interface Printable {
    void printInfo();
}

// Абстрактный класс
abstract class MovieItem implements Printable {
    protected String title;
    protected double rating;
    protected int year;
    protected String genre;

    public MovieItem(String title, double rating, int year, String genre) {
        this.title = title;
        this.rating = rating;
        this.year = year;
        this.genre = genre;
    }

    public abstract boolean isRecommended();

    public String getTitle() {
        return title;
    }

    public double getRating() {
        return rating;
    }

    public String getGenre() {
        return genre;
    }
}

// Первый тип фильма - Боевик
class ActionMovie extends MovieItem {
    private String mainHero;

    public ActionMovie(String title, double rating, int year, String mainHero) {
        super(title, rating, year, "Боевик");
        this.mainHero = mainHero;
    }

    @Override
    public boolean isRecommended() {
        // Рекомендуем боевик, если рейтинг выше 7.5 и он вышел после 2010 года
        return rating >= 7.5 && year > 2010;
    }

    @Override
    public void printInfo() {
        System.out.println("[Боевик] Название: " + title + ", Год: " + year +
                ", Рейтинг: " + rating + ", Герой: " + mainHero);
    }
}

// Второй тип фильма - Комедия
class ComedyMovie extends MovieItem {
    private boolean isForKids;

    public ComedyMovie(String title, double rating, int year, boolean isForKids) {
        super(title, rating, year, "Комедия");
        this.isForKids = isForKids;
    }

    @Override
    public boolean isRecommended() {
        // Комедии рекомендуем просто по рейтингу
        return rating >= 6.5;
    }

    @Override
    public void printInfo() {
        String kidsStr = isForKids ? "Да" : "Нет";
        System.out.println("[Комедия] Название: " + title + ", Год: " + year +
                ", Рейтинг: " + rating + ", Для детей: " + kidsStr);
    }
}

// Главный класс
public class Main {
    public static void main(String[] args) {
        // 1. Создаем список
        List<MovieItem> catalog = new ArrayList<>();

        catalog.add(new ActionMovie("Тёмный рыцарь", 9.0, 2008, "Бэтмен"));
        catalog.add(new ActionMovie("Джон Уик", 8.2, 2014, "Джон Уик"));
        catalog.add(new ComedyMovie("Один дома", 8.3, 1990, true));
        catalog.add(new ComedyMovie("Мальчишник в Вегасе", 7.7, 2009, false));
        catalog.add(new ActionMovie("Безумный Макс", 8.1, 2015, "Макс"));

        // Вывод всей инфы
        System.out.println("--- Список всех фильмов ---");
        for (MovieItem m : catalog) {
            m.printInfo();
        }

        // Вывод рекомендованных
        System.out.println("\n--- Рекомендуем к просмотру ---");
        for (MovieItem m : catalog) {
            if (m.isRecommended()) {
                System.out.println("Советую глянуть: " + m.getTitle());
            }
        }

        // 2. Создаем Map для поиска
        Map<String, MovieItem> movieMap = new HashMap<>();
        for (MovieItem m : catalog) {
            movieMap.put(m.getTitle(), m);
        }

        // Поиск по названию
        System.out.println("\n--- Поиск фильма ---");
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите название для поиска: ");
        String search = sc.nextLine();

        if (movieMap.containsKey(search)) {
            System.out.println("Нашел!");
            movieMap.get(search).printInfo();
        } else {
            System.out.println("Такого фильма нет в базе.");
        }

        // 3. Доп. задание: Set (уникальные жанры)
        Set<String> genres = new HashSet<>();
        for (MovieItem m : catalog) {
            genres.add(m.getGenre());
        }
        System.out.println("\nЖанры в каталоге: " + genres);

        // 4. Доп. задание: Streams
        System.out.println("\n--- Топ рейтинга (через Streams) ---");
        catalog.stream()
                .filter(MovieItem::isRecommended)
                .sorted((m1, m2) -> Double.compare(m2.getRating(), m1.getRating()))
                .forEach(m -> System.out.println(m.getTitle() + " (" + m.getRating() + ")"));

        double avg = catalog.stream()
                .mapToDouble(MovieItem::getRating)
                .average()
                .orElse(0);
        System.out.println("\nСредний рейтинг всех фильмов: " + String.format("%.2f", avg));
    }
}