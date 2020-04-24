package ru.sbt.course.threadsafecacheproxy;
/**
 * MethodCach - класс для хранения кэшируемой информации для метода
 */

import java.io.Serializable;

public class MethodCach implements Serializable{
    private String MethodName;
    private boolean isFile;
    private String fileName;
    private CachArray cachArray;
    private Class[] identityMask;

    /**
     * MethodCach конструктор
     * @param cachSize - размер массива для кэширования входных параметров и возвращаемых результатов
     * @param mName - имя метода, для которого кэшируются данные
     * @param isInFile - признак того, что кэшируемые данные будут сохраняться в файл
     * @param fName - имя файла для записи кэшируемых данных
     * @param idM - маска входных параметров, которые надо учитывать при кэшировании. Если параметра нет в маске,
     *            при кэшировании его не учитываем.
     */
    public MethodCach(int cachSize, String mName, boolean isInFile, String fName, Class[] idM) {
        cachArray = new CachArray(cachSize);
        MethodName = mName;
        isFile = isInFile;
        fileName = fName;
        identityMask = idM;
    }

    /**
     * Прореживает набор входных параметров в соответствии с маской.
     * @param args - набор входных параметров метода
     * @return - набор учитываемых входных параметров в соответствии с маской
     */
    private Object[] byIdentity(Object[] args){
        if(identityMask.length == 0) return args;
        Object[] idArgs = new Object[identityMask.length];
        int i = 0, j = 0;
        for (Class cl : identityMask)
            for ( ; i<args.length; i++)
                if(cl.isInstance(args[i])){
                    idArgs[j++] = args[i];
                    break;
                }
        return idArgs;
    }

    /**
     * Возвращает значение из кэша, соответствующее набору входных параметров.
     * @param Args - набор входных парметров
     * @return - возвращаемое значение из кэша, соответствующее набору входных параметров. Если нет соответствия,
     * возвращает null.
     */
    public Object getCacheResult(Object[] Args) {
        return cachArray.getResult(byIdentity(Args));
    }

    /**
     * Добавляет в кэш набор входных параметров и возвращаемый результат.
     * @param args - набор входных параметров
     * @param result - возвращаемый результат
     */
    public void add(Object[] args, Object result) {
        cachArray.add(byIdentity(args), result);
    }

    public boolean isFile() {
        return isFile;
    }

    public String getFileName() {
        return fileName;
    }
}
