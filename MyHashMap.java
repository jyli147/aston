import javax.lang.model.element.Element;

public class MyHashMap<K, V> {
    // Массив для хранения элементов хэш-карты
    private MyAr<K, V>[] array;
    // Текущий размер хэш-карты
    private int size;

    // Конструктор по умолчанию, инициализирует массив с размером 16
    public MyHashMap() {
        this.size = 16; // Инициализируем размер в 0
        array = new MyAr[size]; // Создаем массив фиксированного размера
    }

    // Внутренний класс для представления узла в связном списке
    static class MyAr<K, V> {
        K key; // Ключ
        V value; // Значение
        MyAr<K, V> next; // Ссылка на следующий узел

        // Конструктор для создания узла
        MyAr(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    // Метод для добавления или обновления значения по ключу
    public void put(K key, V value) {
        int hash = getHash(key); // Получаем индекс для ключа
        MyAr<K, V> newMyAr = new MyAr<>(key, value); // Создаем новый узел

        // Если в данной ячейке массива нет узла, добавляем его
        if (array[hash] == null) {
            array[hash] = newMyAr;
        } else {
            MyAr<K, V> previous = null;
            MyAr<K, V> current = array[hash];

            // Проходим по связанному списку для поиска ключа
            while (current != null) {
                if (current.key.equals(key)) {
                    // Если ключ уже существует, обновляем значение
                    current.value = value; // Обновляем значение
                    return;
                }
                previous = current;
                current = current.next;
            }
            // Если ключ не найден, добавляем новый узел в конец списка
            previous.next = newMyAr;
        }
    }

    // Метод для получения значения по ключу
    public V get(K key) {
        int hash = getHash(key); // Находим индекс в массиве
        MyAr<K, V> temp = array[hash]; // Получаем узел по индексу

        // Ищем узел с нужным ключом
        while (temp != null) {
            if (temp.key.equals(key)) {
                return temp.value; // Возвращаем значение, если ключ найден
            }
            temp = temp.next; // Переходим к следующему узлу
        }
        return null; // Если ключ не найден, возвращаем null
    }

    // Метод для удаления элемента по ключу
    public void remove(K key) {
        int hash = getHash(key); // Находим индекс в массиве
        MyAr<K, V> current = array[hash]; // Получаем узел по индексу
        MyAr<K, V> prev = null; // Предыдущий узел для удаления

        // Ищем узел с нужным ключом
        while (current != null) {
            if (current.key.equals(key)) {
                // Если узел найден, удаляем его
                if (prev == null) {
                    // Если это первый узел в списке
                    array[hash] = current.next; // Сдвигаем голову списка
                } else {
                    prev.next = current.next; // Пропускаем текущий узел
                }
                 return; // Выходим из метода
            }
            prev = current; // Запоминаем предыдущий узел
            current = current.next; // Переходим к следующему узлу
        }
    }

    // Метод для получения хэш-индекса для ключа
    private int getHash(K key) {
        return (key.hashCode() & 0x7FFFFFFF) % array.length; // Обеспечиваем положительный индекс
    }

    // Метод для получения текущего размера хэш-карты
    public int getSize() {
        return size; // Возвращаем размер
    }
}



