package ru.sbt.course.threadsafecacheproxy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.lang.Math.pow;

public class ServiceImpl implements Service {
    /**
     * вычисляет куб числа
     * @param val - входное значени
     * @return - входное значенние, возведенное в 3ю степень
     */
    @Override
    public Double doWork1(Double val){
        return pow(val, 3.0d);
    }

    /**
     * Возвращает список строк, заполненный строкой + порядковый номер + дата
     * @param val1 - строка, которой нужно заполнить список
     * @param val2 - сколько раз повторить строку в списке
     * @param date - дата, которую надо прибавить
     * @return - список строк
     */
    @Override
    public List<String> doWork2(String val1, Double val2, Date date){
        List<String> result = new ArrayList<String>();
        for (int i=0; i < val2; i++)
            result.add(val1 + " " + i + " " + date);
        return result;
    }

    @Override
    public List<String> doWork3(String val){
        return Arrays.asList(val.split(" "));
    }

    @Override
    public Double doWork4(Double value){
        return pow(value, 4.0d);
    }
}
