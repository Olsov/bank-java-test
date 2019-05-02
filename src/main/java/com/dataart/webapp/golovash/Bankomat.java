package com.dataart.webapp.golovash; /**
 * Created by Icomp on 18/06/2016.
 */
/**
 * @author Vladislav Golovash
 * @version 0.0.1a
 */

import java.io.*;

import java.util.Properties;

/**
 * Класс банкомат отвечает за работу с деньгами, снятие и пополнения счета
 *
 */
public class Bankomat {
    /**
     * Путь к файлу конфигурации
     */
    public static final String CONFIG_PATH = "config.properties";

    /**
     *Вызывается при создании обекта класса Банкомат
     * @throws IOException
     *
     */
    public Bankomat() throws IOException {
        /**
         * Уничтожает файл конфига при создании обьекта на класс
         */
        destroyFile(CONFIG_PATH);
        createConfigFileIfNotExists(CONFIG_PATH);
        allMoney();
    }

    /**
     *Ложит деньги на счет
     * @param money деньги которые нужно положить
     * @return Возвращает  0 если  введеное число денег не совпадает с (20-500) и 1 если совпало и количество купюр меньше 20
     * @throws IOException
     */
    public int putMoney(int money,int amount) throws IOException {
        /**
         * Если деньги заранее меньше 20  идет возвращение 0 - не правильной операции
         */
        if(money<20)
        {
            return 0;
        }

    Properties prop =new Properties();
        prop.load(new FileInputStream(CONFIG_PATH));
        /**
         * Массив из количества купюр
         */
        int[] array_money=new int[5];
        array_money[0] = Integer.parseInt(prop.getProperty("20$"));
        array_money[1] = Integer.parseInt(prop.getProperty("50$"));
        array_money[2] = Integer.parseInt(prop.getProperty("100$"));
        array_money[3]= Integer.parseInt(prop.getProperty("200$"));
        array_money[4] = Integer.parseInt(prop.getProperty("500$"));
        /**
         * ограничение на 20 купюр одной масти
         */
        int amount_config = Integer.parseInt(prop.getProperty("amount"));
        /**
         * проверка  принадлежности  денег к одному из  номиналов и не превышение количества  положенных денег
         * 20
         */
        if((money==500) &&(array_money[4]<amount_config)&&(array_money[4]+amount<=amount_config))
        {
            money-=500;
            array_money[4]+=amount;
        }
            else  if((money-200)==0 &&(array_money[3]<amount_config) &&(array_money[3]+amount<=amount_config))
        {
            money-=200;
            array_money[3]+=amount;
        }     else  if((money-100)==0&&(array_money[2]<amount_config)&&(array_money[2]+amount<=amount_config))
        {
            money-=100;
            array_money[2]+=amount;
        }     else  if((money-50)==0&&(array_money[1]<amount_config)&&(array_money[1]+amount<=amount_config))
        {
            money-=50;
            array_money[1]+=1;
        }     else  if((money-20)==0&&(array_money[0]<amount_config)&&(array_money[0]+amount<=amount_config))
        {
            money-=20;
            array_money[4]+=amount;
        }
            else{return 0;}
        /**
         * изменяем полученые деньги и их количество в конфиг файле
         */
        propChange("20$",array_money[0]);
        propChange("50$",array_money[1]);
        propChange("100$",array_money[2]);
        propChange("200$",array_money[3]);
        propChange("500$",array_money[4]);
        return 1;
    }

    /**
     * Снимает деньги со счета
     * @param money сумма денег для снятия
     * @return Возвращает массив данных , из которых элементы под номерами(0-4) это количетво выданых  купюр, а
     * 5 элемент это выданная сумма
     * @throws IOException
     */
    public int[] getMoney(int money) throws IOException {
        /**
         * если сумма заведомо меньше 0 возвращает null
         */
        if (money <= 0) {
            return null;
        }
        /**
         * переменная которая считает  сумму которую можно снять
         */
        int k = 0;
        /**
         * переменная отвечающая за количество купурь которые максимально можно снять
         */
        int div = 0;
        /**
         * бекап денег, если сумма  денег которая пытается снять человек   при отнимании от  получившейся при расчетах суммы
         * получает отрицательно значение(при снятии большой суммы денег)
         */
        int money_backup=money;
        /**
         * считает количество снятых купюр для не превышения 10 счета
         */
        int[] chet = new int[5];
        /**
         * сохрнаяет количествокупюрь
         */
        int[] vuvod=new int[6];
        Properties prop = new Properties();

        prop.load(new FileInputStream(CONFIG_PATH));
        int twenty = Integer.parseInt(prop.getProperty("20$"));
        int fifty = Integer.parseInt(prop.getProperty("50$"));
        int one_hundred = Integer.parseInt(prop.getProperty("100$"));
        int two_hundred = Integer.parseInt(prop.getProperty("200$"));
        int five_hundred = Integer.parseInt(prop.getProperty("500$"));
        int  limit= Integer.parseInt(prop.getProperty("limit"));
/**
 * пока  сумма денег больше 20( минимум на которой можно делить ) выполняем  действия
 */
        while (money >= 20) {

/**
 * если сделение больше 0 ( значит можно снять 1 купюру) счет не превышает 10. количество купюры в конфиг файле больше нуля
 * и  целочисленный результат от деления меньше или равен количествам купюр
 */
            if (((money / 500) > 0) && (chet[4]<=limit) &&(five_hundred>0) &&((money/500)<=(five_hundred))) {
                /**
                 * отнимает количество снятый денег
                 */
                five_hundred = five_hundred - (money / 500);
                /**
                 * заносим целочисленное количество снятый купюр
                 */
                div = money / 500;
                /**
                 * добовляем это количество к счету
                 *
                 */
                chet[4]+=div;
                /**
                 * считаем сумму снятых денег
                 */
                k = k + div * 500;
                /**
                 * проверяем не привышает посчитанная сумма полученую( при: 700-300=400 , и потом 400 - 600 меньше 0)
                 */
                if(money-k>=0){
                money = money - k;}
                else{
                    money=money_backup-k;
                }

            } else if (((money / 200) > 0) && (chet[3]<= limit)&&(two_hundred>0)&& ((money/200)<=(two_hundred))){
                two_hundred -= (money / 200);
                div = money / 200;
                chet[3]+=div;
                k = k + div * 200;
                if(money-k>=0){
                    money = money - k;}
                else{
                    money=money_backup-k;
                }


            } else if (((money / 100) > 0) && (chet[2]<= limit)&&(one_hundred>0)&& ((money/100)<=(one_hundred))) {
                one_hundred -= (money / 100);
                div = money / 100;
                chet[2]+=div;
                k = k + div * 100;

                if(money-k>=0){
                    money = money - k;}
                else{
                    money=money_backup-k;
                }

            } else if (((money / 50) > 0) && (chet[1]<= limit)&&(fifty>0)&& ((money/50)<=(fifty))) {
                fifty -= (money / 50);
                div = money / 50;
                chet[1]+=div;
                k = k + div * 50;
                if(money-k>=0){
                    money = money - k;}
                else{
                    money=money_backup-k;
                }
            } else if (((money / 20) > 0) && (chet[0]<= limit)&&(twenty>0)&& ((money/20)<=(twenty))) {
                twenty -= (money / 20);
                div = money / 20;
                chet[0]+=div;
                k = k + div * 20;
                if(money-k>=0){
                    money = money - k;}
                else{
                    money=money_backup-k;
                }
            }
            /**
             * если  количество денег  мешье 20 возвращаем сумму снятия
             */
            else if (money < 20) {
                for (int i=0;i<=4;i++)
                {
                    vuvod[i]=chet[i];
                }
                vuvod[5]=k;
                return  vuvod;
            }
            /**
             * если количество снятых денег равняется количеству запрошенных( не изменились данные)
             */
            else if(k==money){
                return null;
            }
            /**
             * любая другая ошибка
             */
            else
            {
                return null;
            }

        }
        prop.clear();
        /**
         * присваеваем виводу(0-4) количуству снятых купюр
         */
        for (int i=0;i<=4;i++)
        {
            vuvod[i]=chet[i];
        }
        /**
         * присваемваем виводу 5 сумму снятия , возвращаем сумму
         */
        vuvod[5]=k;
        return vuvod;
    }


    /**
     * Метод меняет  значения элемента в конфиг файле
     * @param string Имя  элемента к конфиг файле
     * @param money Количество купюр(значение элемента в конфиг файле)
     * @throws IOException
     */
    private void propChange(String string, int money) throws IOException {

        FileInputStream in = new FileInputStream(CONFIG_PATH);
        Properties props = new Properties();
        props.load(in);
        in.close();

        FileOutputStream out = new FileOutputStream(CONFIG_PATH);
        props.setProperty(string, Integer.toString(money));
        props.store(out, null);
        out.close();

    }

    /**
     * Используется для сумирования  всех купюр и их количества при создании класса Bankomat
     *  и сохраняет значение в конфиг файле  в элементе Sum
     * @throws IOException
     */
    private void allMoney() throws IOException {
        Properties prop = new Properties();

        prop.load(new FileInputStream(CONFIG_PATH));
        System.out.println(prop);
        int twenty = Integer.parseInt(prop.getProperty("20$"));
        int fifty = Integer.parseInt(prop.getProperty("50$"));
        int one_hundred = Integer.parseInt(prop.getProperty("100$"));
        int two_hundred = Integer.parseInt(prop.getProperty("200$"));
        int five_hundred = Integer.parseInt(prop.getProperty("500$"));
        twenty = twenty *20+ one_hundred *100+ fifty *50+ five_hundred *500+ two_hundred*200;
        propChange("Sum", twenty);
    }

    /**
     * Создает конфиг файл если тот не существует, вызывается при создании класса Банкомат
     * @param string Путь к конфиг файлу
     * @throws IOException
     */
    private void  createConfigFileIfNotExists(String string) throws IOException {
        File file=new File(string);
     if (!file.createNewFile())
     {
         System.out.println("File already exists");
     }
        else{
         System.out.println("File Created");
            propChange("20$",10);
            propChange("50$",16);
            propChange("100$",2);
            propChange("200$",13);
            propChange("500$",15);
            propChange("limit",10);
            propChange("amount",20);
            propChange("Sum",0);

     }
    }

    /**
     * Удаляет файл , вызывается(или не вызывается)  при создании класса банкомант, для того что бы создать новый конфиг файл
     * @param string Путь к файлу
     */
    private void destroyFile(String string)
    {
        File file= new File(string);
        file.delete();
    }

    /**
     * Записываем в логи  наши действия с банкоматом  или ошибки
     * @param name Имя лога
     * @param text текст лога
     * @throws IOException
     */
    public void logFeed(String name,String text) throws IOException {
       File catalog=new File("C://logs");
       catalog.mkdir();

        File filelog=new File("C://logs/"+name+"_log.txt");
        filelog.getParentFile().mkdirs();
        filelog.createNewFile();


        FileWriter writer = new FileWriter("C://logs/"+name+"_log.txt", true);
        BufferedWriter bufferWriter = new BufferedWriter(writer);
        bufferWriter.write(text+"\r\n");
        bufferWriter.close();


    }

    /**
     * Проверяет существует ли лог
     * @param name  имя лога
     * @return возвращает true если существует  и false если не существует
     */
    public boolean isExists(String name)
    {
        File filelog=new File("C://logs/"+name+"_log.txt");
        return filelog.exists();
    }

    /**
     * Изменяет сразу несколько значение в конфиг файле, используется при подтверждении снятия денег
     * @param array - массив с количество выданных банктон где элементы от (0-4) отвечают  купюрам (20-500)
     * @throws IOException
     */
    public void changeMoneyEquivalent(int[] array) throws IOException {
        Properties prop = new Properties();

        prop.load(new FileInputStream(CONFIG_PATH));
        int twenty = Integer.parseInt(prop.getProperty("20$"));
        int fifty = Integer.parseInt(prop.getProperty("50$"));
        int one_hundred = Integer.parseInt(prop.getProperty("100$"));
        int two_hundred = Integer.parseInt(prop.getProperty("200$"));
        int five_hundred = Integer.parseInt(prop.getProperty("500$"));

        propChange("20$",(twenty-array[0]));
        propChange("50$",(fifty-array[1]));
        propChange("100$",(one_hundred-array[2]));
        propChange("200$",(two_hundred-array[3]));
        propChange("500$",(five_hundred-array[4]));
    }

    /**
     * Не используется в этой версии. Ложит  любую сумму денег если это возможно
     * @param money деньги  которые надо положить
     * @return 1 если деньги были положены и 0 если  нет
     * @throws IOException
     */
    public int putAnyMoney(int money) throws IOException {
        if(money<20)
        {
            return 0;
        }
        Properties prop =new Properties();
        prop.load(new FileInputStream(CONFIG_PATH));
        int[] array_money=new int[5];
        array_money[0] = Integer.parseInt(prop.getProperty("20$"));
        array_money[1] = Integer.parseInt(prop.getProperty("50$"));
        array_money[2] = Integer.parseInt(prop.getProperty("100$"));
        array_money[3]= Integer.parseInt(prop.getProperty("200$"));
        array_money[4] = Integer.parseInt(prop.getProperty("500$"));
        int k=0;
        while (money>0) {
            if((money-500)>=0&&(array_money[4]<20))
            {
                money-=500;
                array_money[4]+=1;
            }
            else  if((money-200)>=0 &&(array_money[3]<20))
            {
                money-=200;
                array_money[3]+=1;
            }     else  if((money-100)>=0&&(array_money[2]<20))
            {
                money-=100;
                array_money[2]+=1;
            }     else  if((money-50)>=0&&(array_money[1]<20))
            {
                money-=50;
                array_money[1]+=1;
            }     else  if((money-20)>=0&&(array_money[0]<20))
            {
                money-=20;
                array_money[4]+=1;
            }
            else{return 0;}
        }
        propChange("20$",array_money[0]);
        propChange("50$",array_money[1]);
        propChange("100$",array_money[2]);
        propChange("200$",array_money[3]);
        propChange("500$",array_money[4]);
        return 1;
    }
}
