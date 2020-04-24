package ru.sbt.course.threadsafecacheproxy;
/**
 * CachArray - циклический массив размера size для хранения наборов входных аргументов и результатов,
 * возвращаемых кэшируемой функцией. При переполнении, замещает ранее сохраненные данные новыми.
 */

import java.io.Serializable;

public class CachArray implements Serializable{
    final int size;
    int current;
    CachAtom[] data;

    public CachArray(int size){
        this.size = size;
        current = 0;
        data = new CachAtom[size];
        for (int i = 0; i<size; i++)
            data[i] = new CachAtom();
    }

    /**
     * ищет во внутреннем массиве равный набор сохраненных входных параметров
     * @param Args - набор входных параметров, кэшируемой функции
     * @return - при успешном нахождении равного набора входных параметров, возвращает соответствующий им результат.
     * Иначе возвращает null.
     */
    public Object getResult(Object[] Args){
        for (CachAtom d : data)
            if(d.equals(Args))
                return d.getResult();
        return null;
    }

    /**
     * Добавляет во внутренний массив набор входных параметров и соответствующий им результат,
     * возвращаемый кэшируемой функцией
     * @param Args - набор входных параметров
     * @param res - результат, соответствующий набору входных параметров
     */
    public void add(Object[] Args, Object res){
        data[current++].set(Args, res);
        if(current>=size)
            current = 0;
    }
}
