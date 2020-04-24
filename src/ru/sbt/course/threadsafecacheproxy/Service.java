package ru.sbt.course.threadsafecacheproxy;

import java.util.Date;
import java.util.List;


public interface Service {
    @Cache(cachType = Cache.IN_MEMORY)
    public Double doWork1(Double value);
    @Cache(cachType = Cache.FILE, fileName = "doWork2.cch", zip = true, identityBy = {String.class, Double.class})
    public List<String> doWork2(String val1, Double val2, Date date);
    @Cache(cachType = Cache.IN_MEMORY, listLength = 100)
    public List<String> doWork3(String val);

    public Double doWork4(Double value);
}
