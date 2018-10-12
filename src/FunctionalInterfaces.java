import org.apache.commons.lang3.time.StopWatch;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FunctionalInterfaces {

    public static void main(String[] args) {
//        m1();
//        m2();
//        m3();
//        m4();
//        m5();
//        m6();
//        optional();
//        m7();
//        m5();
//        sorting();
//        streams();
//        m10();
//        pagination();
//        mathces();
//        minAndMax();
//        parallelStreams();
        collect();
    }
    /*
    Функциональный интерфейс Predicate<T> проверяет соблюдение некоторого условия.
    Если оно соблюдается, то возвращается значение true.
    В качестве параметра лямбда-выражение принимает объект типа T:

    public interface Predicate<T> {
        boolean test(T t);
    }
     */
    private static void m1() {
        Predicate<Integer> isPositive = x -> x > 0;


        System.out.println(isPositive.test(5)); // true
        System.out.println(isPositive.test(-7)); // false

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        numbers.stream()
                .filter(number -> isNumbersEqual(number, 10))
                .count();

        System.out.println(sumAll(numbers, n -> true));
        System.out.println(sumAll(numbers, n -> n % 2 == 0));
        System.out.println(sumAll(numbers, n -> n > 3));

    }

    private static boolean isNumbersEqual(int a, int b) {
        return a == b;
    }

    private static int sumAll(List<Integer> numbers, Predicate<Integer> p) {
        int total = 0;
        for (int number : numbers) {
            if (p.test(number)) {
                total += number;
            }
        }
        return total;
    }

    /*
    BinaryOperator<T> принимает в качестве параметра два объекта типа T,
    выполняет над ними бинарную операцию и возвращает ее результат также в виде объекта типа T:

    public interface BinaryOperator<T> {
        T apply(T t1, T t2);
    }
     */
    private static void m2() {
        //  которых входные и выходные обобщенные параметры должны совпадать
        BinaryOperator<Integer> multiply = (x, y) -> x * y;

        System.out.println(multiply.apply(3, 5)); // 15
        System.out.println(multiply.apply(10, -2)); // -20


        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, null);
        numbers.stream()
                .filter(Objects::nonNull)
                .reduce((a, b) -> a + b)
                .ifPresent(System.out::println);

        BinaryOperator<StringBuilder> op = (sb1, sb2) -> sb1.append(sb2);
//        BinaryOperator<StringBuilder> op = StringBuilder::append;
        System.out.println(op.apply(new StringBuilder("Functional Interfaces in"),
                new StringBuilder("Java 8"))); // Функциональные интерфейсы в Java 8
    }

    /*
    UnaryOperator<T>
    UnaryOperator<T> принимает в качестве параметра объект типа T,
    выполняет над ними операции и возвращает результат операций в виде объекта типа T:

    public interface UnaryOperator<T> {
        T apply(T t);
    }
     */
    private static void m3() {
        UnaryOperator<Integer> square = x -> x * x;
        System.out.println(square.apply(5)); // 25

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, null);

        UnaryOperator<StringBuilder> op = sb -> reverseAndToUpperCase(sb);
//        UnaryOperator<StringBuilder> op = FunctionalInterfaces::reverseAndToUpperCase;
        System.out.println(op.apply(new StringBuilder("Java 8")));
    }

    private static StringBuilder reverseAndToUpperCase(StringBuilder sb) {
        return new StringBuilder(sb.insert(0, "http://www.it-courses.by/")
                .append(".html")
                .toString()
                .replace(" ", "_")
                .toLowerCase());
    }

    /*
    Функциональный интерфейс Function<T,R> представляет функцию перехода от объекта типа T к объекту типа R:

    public interface Function<T, R> {
        R apply(T t);
    }
     */
    private static void m4() {
        Function<Integer, String> convert = x -> String.valueOf(x) + " долларов";
        Function<String, Integer> stringToNumber = x -> Integer.valueOf(x) * 100;
        System.out.println(convert.apply(5)); // 5 долларов
        System.out.println(stringToNumber.apply("120")); // 5 долларов

        List<String> strings = Arrays.asList(1, 2, 3, 4, 5, 13)
                .stream()
                .map(convert)
                .collect(Collectors.toList());
        strings.stream()
                .forEach(System.out::println);
    }

    /*
    Consumer<T> выполняет некоторое действие над объектом типа T, при этом ничего не возвращая:

    public interface Consumer<T> {
        void accept(T t);
    }
     */
    private static void m5() {
        Consumer<Integer> printer = x -> System.out.printf("%d долларов \n", x);
        printer.accept(600); // 600 долларов

        Arrays.asList(
                new StringBuilder("string 1"),
                new StringBuilder("string 2"),
                new StringBuilder("string 3")
        ).stream()
                .peek(stringBuilder -> stringBuilder.append("1111")) // для изменения внутрннего состояния объектов
                .map(stringBuilder -> stringBuilder.toString())
                .forEach(string -> System.out.println(string));

        BiConsumer<String, String> biConsumer = (x, y) -> {
            System.out.println(x);
            System.out.println(y);
        };

        biConsumer.accept("java2s.com", " tutorials");
    }

    /*
    Supplier<T> не принимает никаких аргументов, но должен возвращать объект типа T:
    public interface Supplier<T> {
        T get();
    }
     */
    static class User {

        private String name;

        String getName() {
            return name;
        }

        User(String n) {
            this.name = n;
        }
    }

    private static void m6() {
        Supplier<User> userFactory = () -> {

            Scanner in = new Scanner(System.in);
            System.out.println("Введите имя: ");
            String name = in.nextLine();
            return new User(name);
        };


        User user1 = userFactory.get();
        User user2 = userFactory.get();

        System.out.println("Имя user1: " + user1.getName());
        System.out.println("Имя user2: " + user2.getName());
    }

    private static void m7() {
        List<Integer> list = Arrays.asList(1, 3, 5, 11, 14, 1, 2, 4, 6, 7, 5, 9, 10);

        Supplier<AtomicInteger> supplier = AtomicInteger::new;

        BiConsumer<AtomicInteger, Integer> accumulator =
                (AtomicInteger a, Integer i) -> {
                    a.set(a.get() + i);
                };

        BiConsumer<AtomicInteger, AtomicInteger> combiner =
                (a1, a2) -> a1.set(a1.get() + a2.get());

        AtomicInteger result = list.stream()
                .parallel()
                .collect(supplier, accumulator, combiner);
        System.out.println(result);

    }

    private static void sorting() {
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Collections.sort(names);

        stopWatch.stop();
        System.out.println("обычная сортировка " + stopWatch.getTime());
        System.out.println();
        stopWatch.reset();

        Comparator<String> stringComparator = new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return b.compareTo(a);
            }
        };

        stopWatch.start();
        Collections.sort(names, stringComparator);
        stopWatch.stop();
        System.out.println("сортировка компаратором " + stopWatch.getTime());
        System.out.println();
        stopWatch.reset();

        Collections.sort(names, (String a, String b) -> b.compareTo(a));
        Collections.sort(names, (a, b) -> b.compareTo(a));
        Collections.sort(names, Comparator.reverseOrder());
        names.sort(Comparator.reverseOrder());

        stopWatch.start();
        names.stream()
                .sorted(stringComparator)
                .collect(Collectors.toList());
        stopWatch.stop();
        System.out.println("сортировка стримом " + stopWatch.getTime());
        System.out.println();
        stopWatch.reset();

        System.out.println(names);

        stopWatch.start();
        new TreeSet<>(names);
        stopWatch.stop();
        System.out.println("создание трисет " + stopWatch.getTime());
    }

    /*
    Опциональные значения (optionals) не являются функциональными интерфейсами, однако являются удобным средством предотвращения
    NullPointerException. Это важная концепция, которая понадобится нам в следующем разделе, поэтому давайте взглянем,
    как работают опциональные значения.

    Опциональные значение — это по сути контейнер для значения, которое может быть равно null. Например, вам нужен метод,
    который возвращает какое-то значение, но иногда он должен возвращать пустое значение. Вместо того, чтобы возвращать null,
    в Java 8 вы можете вернуть опциональное значение.
     */
    private static void optional() {
//        List<String> stringNumbers = Arrays.asList(null, "two", "2", "5");
//
//        List<Integer> numbers = stringNumbers.stream()
//                .map(string -> stringToNumber(string))
//                .filter(optional -> optional.isPresent())
//                .map(optional -> optional.get())
//                .collect(Collectors.toList());

//        List<Integer> numbers = stringNumbers.stream()
//                .map(FunctionalInterfaces::stringToNumber)
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .collect(Collectors.toList());

//        List<Integer> numbers = Arrays.asList(1, 2, null, 4, 5 ,6);
//        numbers.stream()
//                .map(FunctionalInterfaces::numberToString)
//                .filter(Optional::isPresent)
//                .forEach(System.out::println);

        stringToNumberAndStringBuilder(null);
        stringToNumberAndStringBuilder("non null");
        stringToNumberAndStringBuilder("3");

        System.out.println();
    }

    public static Optional<String> numberToString(Integer integer) {
        Optional<String> result = Optional.ofNullable(integer.toString());
        return result;
    }

    public static StringBuilder stringToNumberAndStringBuilder(String string) {
        StringBuilder result = Optional.ofNullable(Integer.valueOf(string))
                .map(integer -> new StringBuilder(integer.toString()))
                .orElse(new StringBuilder("empty"));
        return result;
    }

    public static void streams() {
        List<String> stringCollection = new ArrayList<>();
        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");

       /*
       Тип java.util.Stream представляет собой последовательность элементов, над которой можно производить различные операции.
       Операции над потоками бывают или промежуточными (intermediate) или конечными (terminal).
       Конечные операции возвращают результат определенного типа, а промежуточные операции возвращают тот же поток.
       Таким образом вы можете строить цепочки из несколько операций над одним и тем же потоком.
       Поток создаются на основе источников, например типов, реализующих java.util.Collection, такие как списки или множества
       (ассоциативные массивы не поддерживаются).
        */

       /*
       Операция Filter принимает предикат, который фильтрует все элементы потока.
       Эта операция является промежуточной, т.е. позволяет нам вызвать другую операцию (например, forEach) над результатом.
       ForEach принимает функцию, которая вызывается для каждого элемента в (уже отфильтрованном) поток.
       ForEach является конечной операцией. Она не возращает никакого значения, поэтому дальнейший вызов потоковых операций невозможен.
        */
        stringCollection
                .stream()
                .sorted() // промежуточная
                .sorted((a, b) -> b.compareTo(a)) //
                .filter((s) -> s.startsWith("a")) // промежуточная
                .map(String::toUpperCase)
//                .map(User::new)
                .forEach(System.out::println); // конечная

        long startsWithB =
                stringCollection
                        .stream()
                        .filter((s) -> s.startsWith("b"))
                        .count(); // Операция Count является конечной операцией и возвращает количество элементов в потоке.
        // Типом возвращаемого значения является long

        Optional<String> reduced =
                stringCollection
                        .stream()
                        .sorted()
                        .reduce((s1, s2) -> s1 + "#" + s2); //Эта конечная операция производит свертку элементов потока по заданной функции.
        // Результатом является опциональное значение.

        BinaryOperator<String> binaryOperator =  (s1, s2) -> s1 + "#" + s2;
        binaryOperator.apply("1", "2");

        reduced.ifPresent(System.out::println);
        // "aaa1#aaa2#bbb1#bbb2#bbb3#ccc#ddd1#ddd2"

        System.out.println(startsWithB);
    }

//    Ряд методов Stream API возвращают подпотоки или объединенные потоки на основе уже имеющихся потоков.
// Рассмотрим эти методы.
//
//            takeWhile
//    Метод takeWhile() выбирает из потока элементы, пока они соответствуют условию. Если попадается элемент,
// который не соответствует условию, то метод завершает свою работу. Выбранные элементы возвращаются в виде потока.\

//    public static void takeWhile() {
//        Stream<Integer> numbers = Stream.of(-3, -2, -1, 0, 1, 2, 3, -4, -5);
//        numbers.takeWhile(n -> n < 0)
//                .forEach(n -> System.out.println(n));
//    }

    private static void m10() {
        Stream<String> phoneStream = Stream.of("iPhone 6 S", "Lumia 950", "Samsung Galaxy S 6", "LG G 4", "Nexus 7");

        phoneStream.skip(1)
                .limit(2)
                .forEach(System.out::println);
    }

    private static void pagination() {
        List<String> phones = Arrays.asList("iPhone 6 S", "Lumia 950", "Huawei Nexus 6P",
                "Samsung Galaxy S 6", "LG G 4", "Xiaomi MI 5",
                "ASUS Zenfone 2", "Sony Xperia Z5", "Meizu Pro 5",
                "Lenovo S 850");


        int pageSize = 3; // количество элементов на страницу

        int kolvoStranic = 0;
        int ostatok = phones.size() % 3;
        if (ostatok == 0) {
            kolvoStranic = phones.size() / 3;
        } else {
            kolvoStranic = phones.size() / 3 + 1;
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {

            System.out.println("Введите номер страницы: ");
            System.out.println("всего страниц " + kolvoStranic);
            int page = scanner.nextInt();

            if (page < 1 || page > kolvoStranic) {
                System.out.println("не валидный ввод");
                continue;
                // если число меньше 1, выходим из цикла
            }

            phones.stream().skip((page - 1) * pageSize)
                    .limit(pageSize)
                    .forEach(s -> System.out.println(s));
        }
    }

    private static void mathces() {
        List<String> names = Arrays.asList("Tom", "Sam", "Bob", "Alice", "Ali" , "Bill");

        // есть ли в потоке строка, длина которой больше 3
        boolean any = names.stream().anyMatch(s -> s.length() > 3);
        System.out.println(any);    // true

        // все ли строки имеют длину в 3 символа
        boolean all = names.stream().allMatch(s -> s.length() == 3);
        System.out.println(all);    // false

        // НЕТ ЛИ в потоке строки "Bill". Если нет, то true, если есть, то false
        boolean none = names.stream().noneMatch(s -> s == "Bill");
        System.out.println(none);   // true
    }

    private static void minAndMax() {
        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

        Optional<Integer> min = numbers.stream().min(Integer::compare);
        Optional<Integer> max = numbers.stream().max(Integer::compare);
        min.ifPresent(System.out::println);
        max.ifPresent(System.out::println);
//        System.out.println(min.get());  // 1
//        System.out.println(max.get());  // 9

        ArrayList<Phone> phones = new ArrayList<>(Arrays.asList(
                new Phone("iPhone 8", 52000),
                new Phone("Nokia 9", 35000),
                new Phone("Samsung Galaxy S9", 48000),
                new Phone("HTC U12", 36000)));

        Phone minPhone = phones.stream().min(Phone::compare).get();
        Phone maxPhone = phones.stream().max(Phone::compare).get();
        System.out.printf("MIN Name: %s Price: %d \n", minPhone.getName(), minPhone.getPrice());
        System.out.printf("MAX Name: %s Price: %d \n", maxPhone.getName(), maxPhone.getPrice());

    }

    static class Phone {

        private String name;
        private int price;

        public Phone(String name, int price) {
            this.name = name;
            this.price = price;
        }

        public static int compare(Phone p1, Phone p2) {
            if (p1.getPrice() > p2.getPrice())
                return 1;
            return -1;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }
    }


//    Кроме последовательных потоков Stream API поддерживает параллельные потоки.
// Распараллеливание потоков позволяет задействовать несколько ядер процессора (если целевая машина многоядерная)
// и тем самым может повысить производительность и ускорить вычисления. В то же время говорить,
// что применение параллельных потоков на многоядерных машинах однозначно повысит производительность - не совсем корректно.
// В каждом конкретном случае надо проверять и тестировать.
//
//    Чтобы сделать обычный последовательный поток параллельным, надо вызвать у объекта Stream метод parallel.
// Кроме того, можно также использовать метод parallelStream() интерфейса Collection для создания параллельного потока
// из коллекции.
//
//   В то же время если рабочая машина не является многоядерной, то поток будет выполняться как последовательный.
//
//   Применение параллельных потоков во многих случаях будет аналогично. Например:

    private static void parallelStreams() {
        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

        Stream<Integer> numbersStream = Stream.of(1, 2, 3, 4, 5, 6);
        Optional<Integer> result = numbersStream.parallel().reduce((x, y) -> x * y);
//        System.out.println(result.get()); // 720

        // Однако не все функции можно без ущерба для точности вычисления перенести с последовательных потоков на параллельные.
        // Прежде всего такие функции должны быть без сохранения состояния и ассоциативными, то есть при выполнении слева
        // направо давать тот же результат, что и при выполнении справа налево, как в случае с произведением чисел. Например:
//        Stream<String> wordsStream = Stream.of("мама", "мыла", "раму");
//        String sentence = wordsStream
//                .parallel()
//                .reduce("Результат:", (x, y) -> x + " " + y);
//        System.out.println(sentence);

        // Данный вывод не является правильным. Если же мы не уверены, что на каком-то этапе работы с
        // параллельным потоком он адекватно сможет выполнить какую-нибудь операцию, то мы можем преобразовать
        // этот поток в последовательный посредством вызова метода sequential():
        Stream<String> wordsStreamSequental = Stream.of("мама", "мыла", "раму", "hello world");
        Calendar start = Calendar.getInstance();
        String sentenceSequental = wordsStreamSequental
//                .parallel()
                .filter(s -> s.length() < 10) // фильтрация над параллельным потоком
//                .sequential()
                .reduce("Результат:", (x, y) -> x + " " + y); // операция над последовательным потоком
        Calendar stop = Calendar.getInstance();
        System.out.println(stop.getTimeInMillis() - start.getTimeInMillis());
//        System.out.println(sentenceSequental);

        System.out.println();
        Stream<String> wordsStreamSequental1 = Stream.of("мама", "мыла", "раму", "hello world");
        Calendar start1 = Calendar.getInstance();
        String sentenceSequental1 = wordsStreamSequental1
                .filter(s -> s.length() < 10) // фильтрация над параллельным потоком
                .reduce("Результат:", (x, y) -> x + " " + y); // операция над последовательным потоком
        Calendar stop1 = Calendar.getInstance();
        System.out.println(stop1.getTimeInMillis() - start1.getTimeInMillis());

        //а в примере с числами не важно
    }

    private static void collect() {
        List<String> phones = new ArrayList<String>();
        Collections.addAll(phones, "iPhone 8", "HTC U12", "Huawei Nexus 6P",
                "Samsung Galaxy S9", "LG G6", "Xiaomi MI6", "ASUS Zenfone 2",
                "Sony Xperia Z5", "Meizu Pro 6", "Lenovo S850");

        List<String> filteredPhones = phones.stream()
                .filter(s -> s.length() < 10)
                .collect(Collectors.toList());

        for (String s : filteredPhones) {
            System.out.println(s);
        }

        Set<String> filteredSet = phones.stream()
                .filter(s -> s.length() < 10)
                .collect(Collectors.toSet());

        Stream<Phone> phoneStream = Stream.of(new Phone("iPhone 8", 54000),
                new Phone("Nokia 9", 45000),
                new Phone("Samsung Galaxy S9", 40000),
                new Phone("LG G6", 32000));


        Map<String, Integer> phonesMap = phoneStream
                .collect(Collectors.toMap(p -> p.getName(), t -> t.getPrice()));

        phonesMap.forEach((k, v) -> System.out.println(k + " " + v));

        List<Phone> listFromMap = phonesMap.entrySet().stream()
                .map(entry -> new Phone(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());


        TreeSet<String> filteredTreeSet = phones
                .stream()
                .filter(s -> s.length() < 12)
                .collect(Collectors.toCollection(TreeSet::new));

        // Вторая форма метода collect имеет три параметра:
        //
        //1
        //<R> R collect(Supplier<R> supplier, BiConsumer<R,? super T> accumulator, BiConsumer<R,R> combiner)
        //supplier: создает объект коллекции
        //
        //accumulator: добавляет элемент в коллекцию
        //
        //combiner: бинарная функция, которая объединяет два объекта
        //
        //Применим эту версию метода collect:

        ArrayList<String> filteredPhonesArrayList = phones.parallelStream().
                filter(s -> s.length() < 12)
                .collect(
                        () -> new ArrayList<>(), // создаем ArrayList
                        (list, item) -> list.add(item), // добавляем в список элемент
                        (list1, list2) -> list1.addAll(list2)); // добавляем в список другой список

        filteredPhones.forEach(s -> System.out.println(s));
    }

}
