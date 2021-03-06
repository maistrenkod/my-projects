package ru.skillbench.tasks.text.regex;
/**
 * Этот класс хранит информацию о номере телефона.<br/>
 * В дополнении к полному номеру (String) он может хранить два необязательных поля, ивлеченных из полного
 *  номера и преобразованных в тип int: код региона и extension (доп. номер).
 * @see CurriculumVitae#PHONE_PATTERN
 */

public class Phone {
    private String number;
    private int areaCode;
    private int extension;
    public Phone(String number) {
        this.number = number;
        this.areaCode = -1;
        this.extension = -1;
    }
    public Phone(String number, int areaCode, int extension) {
        this.number = number;
        this.areaCode = areaCode;
        this.extension = extension;
    }
    /**
     * @return Полный номер в виде String
     */
    public final String getNumber() {
        return number;
    }
    /**
     * @return код региона (или отрицательное число, если код региона не задан)
     */
    public final int getAreaCode() {
        return areaCode;
    }
    /**
     * @return extension (или отрицательное число, если extension не задан)
     */
    public final int getExtension() {
        return extension;
    }
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Phone))
            return false;
        Phone p = (Phone) obj;
        if(getNumber() == null)//null is considered to be equal to null
            return p.getNumber() == null;
        if(!getNumber().equals(p.getNumber()))
            return false;
        if(!equalsOptional(getAreaCode(), p.getAreaCode()))
            return false;
        if(!equalsOptional(getExtension(), p.getExtension()))
            return false;
        return true;
    }
    private boolean equalsOptional(int v1, int v2){
        if(v1 < 0) return v2 < 0;
        else return v1 == v2;
    }
    private void addMarker(StringBuilder sb, int value, boolean fromStart){
        String s = "";
        if(value < 0) {
            s = fromStart ? "{NO CODE}" : "{NO EXT}";
        } else {
            String sValue = Integer.toString(value);
            int j = fromStart ? sb.indexOf(sValue) : sb.lastIndexOf(sValue);
            if(j < 0 || (fromStart && j > 1) || (!fromStart && j < sb.length()-sValue.length()) ){
                s = "{WRONG:"+sValue+"}";
            }
        }
        if(fromStart) sb.insert(0, s);
        else sb.append(s);
    }
    /**
     * Строковое представление телефона.
     * Добавляет "{NO CODE}" перед {@link #number}, если значение {@link #areaCode} отрицательно, и
     *  добавляет "{NO EXT}" после {@link #number}, если значение {@link #extension} отрицательно.
     * Если areaCode / extension не соответствует самому номеру, строка "WRONG" добавляется
     *  перед / после {@link #number}.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(number);
        addMarker(sb, areaCode, true);
        addMarker(sb, extension, false);
        return sb.toString();
    }
}
