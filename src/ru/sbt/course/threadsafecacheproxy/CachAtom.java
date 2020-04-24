package ru.sbt.course.threadsafecacheproxy;
/**
 * CachAtom - структура для хранения кэшируемой информации
 * набора входных аргументов и результата
 *
 * @author - Hin7
 * @version - 1.1
 */

import java.io.Serializable;

public class CachAtom implements Serializable{
    private Object[] inArgs;
    private Object result;

    public void set(Object[] Args, Object res){
        inArgs = Args.clone();
        result = res;
    }

    /**
     * сравнивает массив входных параметров с внетренним сохраненным массивом в inArgs
     * @param Args - массив входных параметров
     * @return - true, если Args равен inArgs
     */
    public boolean equals(Object[] Args){
        if(inArgs == null) return false;
        int i = 0;
        for(Object o : Args){
            if(inArgs[i] == null || !o.equals(inArgs[i]))
                return false;
            i++;
        }

        return true;
    }

    public Object getResult() {
        return result;
    }
}
