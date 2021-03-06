package ru.skillbench.tasks.text.regex;

import java.util.List;
import java.util.NoSuchElementException;

public interface CurriculumVitae {
    /**
     * Это регулярное выражения для использования в методе {@link #getPhones()}. Оно описывает несколько форматов
     * номеров телефонов - такие как американский, российский и украинский форматы (для больших городов);
     * оно НЕ соответствует британскому и французскому форматам.<br/>
     * Выражение не включает код страны. Необязательными являются код региона (3 цифры в начале)
     *   и доп. номер в конце (extension: одна или несколько цифр после строки "ext" или "ext.").<br/>
     * Число цифр в номере, удовлетворяющем выражению, не меньше 7.<br/>
     * Примеры номеров, удовлетворяющих выражению: "(916)125-4171", "495 926-93-47 ext.1846", "800 250 0890"
     */
    public static final String PHONE_PATTERN =
            "(\\(?([1-9][0-9]{2})\\)?[-. ]*)?([1-9][0-9]{2})[-. ]*(\\d{2})[-. ]*(\\d{2})(\\s*ext\\.?\\s*([0-9]+))?";

    /**
     * Задает текст резюме.<br/>
     * О реализации: текст НЕ должен анализировать в этом методе.
     * @param text Текст резюме
     */
    void setText(String text);
    /**
     * Рекомендуется вызывать этот метод во всех остальных методах вашего класса.
     * @return Текущий текст резюме (который мог измениться не только методом setText, но и методами update*).
     * @throws IllegalStateException Если текст резюме не был задан путем вызова {@link #setText(String)}.
     */
    String getText();

    /**
     * Возвращает список телефонов в том же порядке, в котором они расположены в самом резюме.<br/>
     * О реализации: используйте {@link #PHONE_PATTERN} для поиска телефонов;
     * используйте группы этого регулярного выражения, чтобы извлечь код региона и extension из найденных номеров;
     * если код региона или extension не присутствует в номере, объект {@link Phone} должен хранить отрицательное значение.
     * @see Phone
     * @return Список, который не может быть <code>null</code>, но может быть пустым (если ни одного телефона не найдено).
     * @throws IllegalStateException Если текст резюме не был задан путем вызова {@link #setText(String)}.
     */
    List<Phone> getPhones();

    /**
     * Возвращает полное имя, т.е. ПЕРВУЮ часть текста резюме, которая удовлетворяет такие критериям:
     * <ol>
     * <li>полное имя содержит 2 или 3 слова, разделенных пробелом (' ');</li>
     * <li>каждое слово содержит не меньше двух символов;</li>
     * <li>первый символ слова - это заглавная латинская буква (буква английского алфавита в upper case);</li>
     * <li>последний символ слова - это либо точка ('.'), либо строчная(lower case) латинская буква;</li>
     * <li>не первые и не последние символы слова - это только строчные (lower case) латинские буквы.</li>
     * </ol>
     * @return Полное имя (в точности равно значению в тексте резюме)
     * @throws NoSuchElementException Если резюме не содержит полного имени, которое удовлетворяет критериям.
     * @throws IllegalStateException Если текст резюме не был задан путем вызова {@link #setText(String)}.
     */
    String getFullName();
    /**
     * Возвращает имя (первое слово из полного имени {@link #getFullName()}).
     * @throws NoSuchElementException Если резюме не содержит полного имени.
     * @throws IllegalStateException Если текст резюме не был задан путем вызова {@link #setText(String)}.
     */
    String getFirstName();
    /**
     * Возвращает отчество (второе слово из полного имени {@link #getFullName()})
     *  или <code>null</null>, если полное имя состоит только из двух слов.
     * @throws NoSuchElementException Если резюме не содержит полного имени.
     * @throws IllegalStateException Если текст резюме не был задан путем вызова {@link #setText(String)}.
     */
    String getMiddleName();
    /**
     * Возвращает фамилию (последнее слово из полного имени {@link #getFullName()}).
     * @throws NoSuchElementException Если резюме не содержит полного имени.
     * @throws IllegalStateException Если текст резюме не был задан путем вызова {@link #setText(String)}.
     */
    String getLastName();

    /**
     * Заменяет фамилию на <code>newLastName</code> в тексте резюме.
     * @see #getLastName()
     * @param newLastName Не может быть null
     * @throws NoSuchElementException Если резюме не содержит полного имени.
     * @throws IllegalStateException Если текст резюме не был задан путем вызова {@link #setText(String)}.
     */
    void updateLastName(String newLastName);

    /**
     * Заменяет <code>oldPhone.getNumber()</code> на <code>newPhone.getNumber()</code> в тексте резюме.<br/>
     * О реализации: использование regex здесь ведет к большему объему кода, чем вызов не связанных с
     *  регулярными выражениями методов {@link String} (или метода {@link String} и метода {@link StringBuilder}).
     * @param oldPhone Не может быть null
     * @param newPhone Не может быть null
     * @throws IllegalArgumentException Если резюме не содержит текста, равного <code>oldPhone.getNumber()</code>.
     * @throws IllegalStateException Если текст резюме не был задан путем вызова {@link #setText(String)}.
     */
    void updatePhone(Phone oldPhone, Phone newPhone);

    /**
     * Ищет строку <code>piece</code> в тексте резюме и скрывает ее, то есть заменяет каждый символ из
     *  <code>piece</code> на символ 'X', за исключениеми следующих разделительных символов: ' ', '.' и '@'.
     * Число символов 'X' равно числу замененных символов.<br/>
     * Например: "John A. Smith" заменяется на "XXXX X. XXXXX", "john@hp.com" - на "XXXX@XX.XXX".<br/>
     * Эта замена может быть отменена путем вызова {@link #unhideAll()}.
     * @param piece Не может быть null
     * @throws IllegalArgumentException Если резюме не содержит текста, равного <code>piece</code>.
     * @throws IllegalStateException Если текст резюме не был задан путем вызова {@link #setText(String)}.
     */
    void hide(String piece);
    /**
     * Ищет строку <code>phone</code> в тексте резюме и скрывает ее, то есть, заменяет все ЦИФРЫ из
     *  <code>phone</code> на символ 'X'.<br/>
     * Например: "(123)456 7890" заменяется на "(XXX)XXX XXXX".<br/>
     * Эта замена может быть отменена путем вызова {@link #unhideAll()}.
     * @param phone Не может быть null
     * @throws IllegalArgumentException Если резюме не содержит текста, равного <code>phone</code>.
     * @throws IllegalStateException Если текст резюме не был задан путем вызова {@link #setText(String)}.
     */
    void hidePhone(String phone);
    /**
     * Отменяет все изменения, сделанные методами {@link #hide(String)} и {@link #hidePhone(String)},
     *  т.е. заменяет куски текста с символами 'X' в текущем тексте резюме (скрытые куски, вставленные ранее)
     *  на соответствующие куски из исходного текста резюме.<br/>
     * Примечание: в резюме не может быть двух (или более) одинаковых скрытых кусков (одинаковых куско с 'X').<br/>
     * О реализации: исходные и скрытые куски следует хранить в некой коллекции.
     *  Кроме того, эта коллекция должна очищаться при вызове {@link #setText(String)}.
     * @return Число кусков, замененных в тексте резюме при выполнении метода
     * @throws IllegalStateException Если текст резюме не был задан путем вызова {@link #setText(String)}.
     */
    int unhideAll();
}
