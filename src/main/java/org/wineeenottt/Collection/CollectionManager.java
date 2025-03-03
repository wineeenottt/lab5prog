package org.wineeenottt.Collection;

import org.wineeenottt.WorkWithFile.FileManager;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Класс CollectionManager управляет коллекцией маршрутов (Route), предоставляя методы для работы с ней.
 * Коллекция хранится в виде HashSet, что обеспечивает уникальность элементов.
 */
public class CollectionManager {

    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    private int maxId;
    /**
     * Коллекция маршрутов, над которой осуществляется работа.
     */
    private final HashSet<Route> hashSetRouteCollection;
    /**
     * Время создания коллекции.
     */
    private final ZonedDateTime collectionCreation;

    /**
     * Конструктор класса CollectionManager.
     *
     * @param routes Набор маршрутов, который будет использоваться для инициализации коллекции.
     */
    public CollectionManager(Set<Route> routes) {
        this.hashSetRouteCollection = new HashSet<>(routes);
        this.collectionCreation = ZonedDateTime.now();
        this.maxId = new FileManager().findMaxId(routes);
    }

    /**
     * Выводит основную информацию о коллекции, включая тип коллекции, тип элементов,
     * время создания и количество элементов.
     */
    public void infoAboutCollection() {
        System.out.println("Коллекция: " + hashSetRouteCollection.getClass().getSimpleName());
        System.out.println("Тип элементов: " + Route.class.getSimpleName());
        System.out.println("Время создания коллекции: " + collectionCreation.format(DateTimeFormatter.ofPattern(PATTERN)));
        System.out.println("Количество элементов: " + hashSetRouteCollection.size());
    }

    /**
     * Выводит информацию по всем элементам коллекции, отсортированным по ID.
     * Если коллекция пуста, выводится соответствующее сообщение.
     */
    public void showElementsCollection() {
        if (hashSetRouteCollection.isEmpty()) {
            System.out.println("Коллекция пуста");
        } else {
            List<Route> routeList = new ArrayList<>(hashSetRouteCollection);

            routeList.sort((r1, r2) -> Integer.compare(r1.getId(), r2.getId()));

            for (Route route : routeList) {
                System.out.println(route);
            }
        }
    }

    /**
     * Удаляет все элементы из коллекции.
     */
    public void clearAllCollection() {
        hashSetRouteCollection.clear();
    }

    /**
     * Проверяет, существует ли в коллекции элемент с указанным ID.
     *
     * @param id ID маршрута, который необходимо проверить.
     * @return true, если элемент с таким ID существует, иначе false.
     */
    public boolean containsIdRoute(Integer id) {
        for (Route route : hashSetRouteCollection) {
            if (route.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Удаляет из коллекции все элементы, ID которых превышает указанный.
     *
     * @param id ID, по которому происходит удаление элементов.
     */
    public void removeGreater(Integer id) {
        if (hashSetRouteCollection.isEmpty()) {
            System.out.println("Коллекция пуста");
        } else {
            HashSet<Route> toRemove = new HashSet<>();

            for (Route route : hashSetRouteCollection) {
                if (route.getId() > id) {
                    toRemove.add(route);
                }
            }
            hashSetRouteCollection.removeAll(toRemove);
        }
    }

    /**
     * Выводит расстояния всех маршрутов в коллекции, отсортированные по возрастанию.
     * Если коллекция пуста, выводится соответствующее сообщение.
     */
    public void showRouteSortedDistance() {
        if (hashSetRouteCollection.isEmpty()) {
            System.out.println("Коллекция пуста");
        } else {
            List<Long> distances = new ArrayList<>();
            for (Route route : hashSetRouteCollection) {
                if (route.getDistance() != null) {
                    distances.add(route.getDistance());
                }
            }
            Collections.sort(distances);
            for (Long distance : distances) {
                System.out.println(distance);
            }
        }
    }

    /**
     * Выводит ID и имена всех маршрутов в коллекции, отсортированные по ID.
     * Если коллекция пуста, выводится соответствующее сообщение.
     */
    public void showIdSortedCollection() {
        if (hashSetRouteCollection.isEmpty()) {
            System.out.println("Коллекция пуста");
        } else {
            List<Route> routeList = new ArrayList<>(hashSetRouteCollection);

            routeList.sort(Comparator.comparing(Route::getId));

            for (Route route : routeList) {
                System.out.println("ID: " + route.getId() + ", Name: " + route.getName());
            }
        }
    }

    /**
     * Удаляет маршрут с указанным ID из коллекции.
     *
     * @param id ID маршрута, который необходимо удалить.
     */
    public void removeById(Integer id) {
        if (hashSetRouteCollection.isEmpty()) {
            System.out.println("Коллекция пуста");
        } else {
            Route toRemove = null;
            for (Route route : hashSetRouteCollection) {
                if (route.getId().equals(id)) {
                    toRemove = route;
                    break;
                }
            }
            if (toRemove != null) {
                hashSetRouteCollection.remove(toRemove);
            }
        }
    }

    /**
     * Возвращает сумму расстояний всех маршрутов в коллекции.
     *
     * @return Сумма расстояний всех маршрутов. Если коллекция пуста, возвращает 0.
     */
    public Long sumOfDistance() {
        if (hashSetRouteCollection.isEmpty()) {
            System.out.println("Коллекция пуста");
            return 0L;
        } else {
            Long sum = 0L;
            for (Route route : hashSetRouteCollection) {
                if (route.getDistance() != null) {
                    sum += route.getDistance();
                }
            }
            return sum;
        }
    }

    /**
     * Возвращает максимальный ID среди всех маршрутов в коллекции.
     *
     * @return Максимальный ID.
     */
    public int getMaxId() {
        return maxId;
    }

    /**
     * Добавляет новый маршрут в коллекцию, автоматически генерируя ID (maxId + 1).
     *
     * @param name         Название маршрута.
     * @param coordinates  Координаты маршрута.
     * @param creationDate Дата создания маршрута.
     * @param from         Начальная точка маршрута.
     * @param to           Конечная точка маршрута.
     * @param distance     Расстояние маршрута.
     */
    public void addRoute(String name, Coordinates coordinates, ZonedDateTime creationDate, Location from, Location to, Long distance) {
        int newId = maxId + 1;
        Route route = new Route(newId, name, coordinates, creationDate, from, to, distance);
        hashSetRouteCollection.add(route);
        maxId = newId;
    }

    /**
     * Добавляет маршрут в коллекцию, если переданный ID больше текущего максимального ID.
     * Если ID не больше текущего максимального, маршрут не добавляется.
     *
     * @param id           ID маршрута.
     * @param name         Название маршрута.
     * @param coordinates  Координаты маршрута.
     * @param creationDate Дата создания маршрута.
     * @param from         Начальная точка маршрута.
     * @param to           Конечная точка маршрута.
     * @param distance     Расстояние маршрута.
     */
    public void addIfMaxIdRoute(int id, String name, Coordinates coordinates, ZonedDateTime creationDate, Location from, Location to, Long distance) {
        if (id > maxId) {
            Route route = new Route(id, name, coordinates, creationDate, from, to, distance);
            hashSetRouteCollection.add(route);
            maxId = id;
        }
    }

    /**
     * Сохраняет коллекцию маршрутов в файл по указанному пути.
     *
     * @param filePath Путь к файлу, в который будет сохранена коллекция.
     */
    public void save(String filePath) {
        FileManager csvParser = new FileManager();
        csvParser.parseToCsv(filePath, hashSetRouteCollection);
    }

    /**
     * Возвращает строку с описанием всех полей маршрута.
     *
     * @return Строка с описанием полей маршрута.
     */
    public String getFieldNames() {
        return "Список всех полей: \n" + "Name (String)\n" + "CoordinateX (Double)\n" + "CoordinateY (Float)\n" +
                "LocationFromX (Float)\n" + "LocationFromY (Integer)\n" + "LocationFromZ (Double)\n" + "LocationFromName (String)\n" +
                "LocationToX (Float)\n" + "LocationToY (Integer)\n" + "LocationToZ (Double)\n" + "LocationToName (String)\n" +
                "Distance (Long)\n";
    }

    /**
     * Обновляет значение указанного поля маршрута с заданным ID.
     *
     * @param id    ID маршрута, который необходимо обновить.
     * @param field Название поля, которое необходимо обновить.
     * @param value Новое значение поля.
     */
    public void update(Integer id, String field, String value) {
        try {
            if (field.equals("stop")) {
                return;
            }
            for (Route route : hashSetRouteCollection) {
                if (route.getId().equals(id)) {
                    switch (field) {
                        case "Name":
                            route.setName(validateString(value));
                            break;
                        case "CoordinateX":
                            route.setCoordinateX(parseDoubleWithMax(value));
                            break;
                        case "CoordinateY":
                            route.setCoordinateY(parseFloat(value));
                            break;
                        case "LocationFromX":
                            route.getFrom().setX(parseFloat(value));
                            break;
                        case "LocationFromY":
                            route.getFrom().setY(parseInteger(value));
                            break;
                        case "LocationFromZ":
                            route.getFrom().setZ(parseDouble(value));
                            break;
                        case "LocationFromName":
                            route.getFrom().setName(validateString(value));
                            break;
                        case "LocationToX":
                            route.getTo().setX(parseFloat(value));
                            break;
                        case "LocationToY":
                            route.getTo().setY(parseInteger(value));
                            break;
                        case "LocationToZ":
                            route.getTo().setZ(parseDouble(value));
                            break;
                        case "LocationToName":
                            route.getTo().setName(validateString(value));
                            break;
                        case "Distance":
                            route.setDistance(parseLongWithMin(value));
                            break;
                        case "Stop":
                            return;
                        default:
                            System.out.println("Поле не распознано");
                            return;
                    }
                    System.out.println("Значение поля было изменено");
                    return;
                }
            }
            System.out.println("Маршрут с ID " + id + " не найден.");
        } catch (NumberFormatException ex) {
            System.err.println("Ошибка: Неверный формат числа (" + ex.getMessage() + ")");
        } catch (NullPointerException ex) {
            System.err.println("Ошибка: Значение не может быть пустым");
        }
    }

    /**
     * Преобразует строку в Double, проверяя, что значение не превышает 750.
     *
     * @param value Строка, которую необходимо преобразовать.
     * @return Преобразованное значение типа Double.
     * @throws NullPointerException     Если строка пуста или равна null.
     * @throws NumberFormatException    Если строка не может быть преобразована в Double.
     * @throws IllegalArgumentException Если значение превышает 750.
     */
    private Double parseDoubleWithMax(String value) {
        try {
            if (value == null || value.isEmpty()) throw new NullPointerException("Значение не может быть пустым");
            double parsedValue = Double.parseDouble(value);
            if (parsedValue > 750) {
                throw new IllegalArgumentException("Значение CoordinateX не может быть больше " + 750);
            }
            return parsedValue;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Неверный формат для Double: " + value);
        }
    }

    /**
     * Преобразует строку в Long, проверяя, что значение больше 1.
     *
     * @param value Строка, которую необходимо преобразовать.
     * @return Преобразованное значение типа Long.
     * @throws NullPointerException     Если строка пуста или равна null.
     * @throws NumberFormatException    Если строка не может быть преобразована в Long.
     * @throws IllegalArgumentException Если значение меньше или равно 1.
     */
    private Long parseLongWithMin(String value) {
        if (value == null || value.isEmpty()) throw new NullPointerException("Значение не может быть пустым");
        try {
            long parsedValue = Long.parseLong(value);
            if (parsedValue <= 1) {
                throw new IllegalArgumentException("Значение Distance должно быть больше " + 1);
            }
            return parsedValue;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Неверный формат для Long: " + value);
        }
    }

    /**
     * Проверяет, что строка не пуста и не равна null.
     *
     * @param value Строка, которую необходимо проверить.
     * @return Проверенная строка.
     * @throws NullPointerException Если строка пуста или равна null.
     */
    private String validateString(String value) {
        if (value == null || value.isEmpty()) throw new NullPointerException("Значение не может быть пустым");
        return value;
    }

    /**
     * Преобразует строку в Double.
     *
     * @param value Строка, которую необходимо преобразовать.
     * @return Преобразованное значение типа Double.
     * @throws NullPointerException  Если строка пуста или равна null.
     * @throws NumberFormatException Если строка не может быть преобразована в Double.
     */
    private Double parseDouble(String value) {
        if (value == null || value.isEmpty()) {
            throw new NullPointerException("Значение не может быть пустым");
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("Неверный формат для Double: " + value);
        }
    }

    /**
     * Преобразует строку в Float.
     *
     * @param value Строка, которую необходимо преобразовать.
     * @return Преобразованное значение типа Float.
     * @throws NullPointerException  Если строка пуста или равна null.
     * @throws NumberFormatException Если строка не может быть преобразована в Float.
     */
    private Float parseFloat(String value) {
        if (value == null || value.isEmpty()) {
            throw new NullPointerException("Значение не может быть пустым");
        }
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("Неверный формат для Float: " + value);
        }
    }

    /**
     * Преобразует строку в Integer.
     *
     * @param value Строка, которую необходимо преобразовать.
     * @return Преобразованное значение типа Integer.
     * @throws NullPointerException  Если строка пуста или равна null.
     * @throws NumberFormatException Если строка не может быть преобразована в Integer.
     */
    private Integer parseInteger(String value) {
        if (value == null || value.isEmpty()) {
            throw new NullPointerException("Значение не может быть пустым");
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("Неверный формат для Integer: " + value);
        }
    }
}