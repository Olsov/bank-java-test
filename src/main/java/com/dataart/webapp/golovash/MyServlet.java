package com.dataart.webapp.golovash;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Icomp on 16/06/2016.
 * Веб сервлет который работает с сайтом
 */
@WebServlet("/s")
public class MyServlet extends HttpServlet {
    DateFormat df = new SimpleDateFormat("MM_dd_yyyy");
  Date today ;
    String value_final="1";

   private  String reportDate;



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
    }

    @SuppressWarnings("Since15")
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       request.setAttribute("adress", "http://localhost:8080/s");
        String view = request.getParameter("view");
        LocalTime time;
        today = Calendar.getInstance().getTime();
        reportDate = df.format(today);
        final Bankomat bank=new Bankomat();
        final Zip zip=new Zip();

        time=LocalTime.now();
        if(!bank.isExists(reportDate)) bank.logFeed(reportDate, time+ "[LOG CREATED]: " + reportDate);

        request.setAttribute("viewname","");
        if (view == null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        } else {
            if (view.equals("get")) {
                time= LocalTime.now();
                String value=request.getParameter("money");
                if(!isInteger(value)){
                    request.setAttribute("name","Невозможно выполнить операцию");
                    System.out.println(reportDate);
                    bank.logFeed(reportDate,time+"[Ошибка снятия денег:"+value+"$]");
                }
                else {

                    time=LocalTime.now();
                    int[] username = bank.getMoney(Integer.parseInt(value));
                    if (username == null) {
                        request.setAttribute("name","Невозможно выполнить операцию");
                    } else {
                            if(username[5]==(Integer.parseInt(value))){
                        String value2 = " Выдано 20$:" + username[0]
                                + "  50$:" + username[1]
                                + "  100$:" + username[2]
                                + "  200$:" + username[3]
                                + "  500$:" + username[4]
                                + " Сумма:" + username[5];
                        request.setAttribute("name", value2);
                        time=LocalTime.now();
                        bank.logFeed(reportDate,time+"["+value2+"]");
                            }
                        else
                            {
                                value_final=value;
                                request.setAttribute("name","Невозможно выдать полную сумму. Возможно выдать:"+username[5]);
                                request.setAttribute("viewname","add_for_shure");
                            }
                    }
                }

                RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
                dispatcher.forward(request, response);
            } else if (view.equals("add")) {
                String value=request.getParameter("money");
                String value_amount=request.getParameter("moneycalc");
                if(!isInteger(value)|| (!isInteger(value_amount))){
                    time=LocalTime.now();
                    request.setAttribute("name","Невозможно выполнить операцию");
                    bank.logFeed(reportDate,time+"[Ошибка пополнения счета на:"+value+"$]");
                }
                else {
                    int username = bank.putMoney(Integer.parseInt(value),Integer.parseInt(value_amount));
                    if (username == 1) {

                        request.setAttribute("name", "Счет пополнен");
                        time=LocalTime.now();
                        bank.logFeed(reportDate,time+"[Пополнение счета на: "+value+"$ в количестве: "+value_amount+"]");
                    } else {
                        request.setAttribute("name", "Ошибка выполнения");
                        time=LocalTime.now();
                        bank.logFeed(reportDate,time+"[Ошибка пополнение счета на: "+value+"$]");
                    }
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
                    dispatcher.forward(request, response);
                }

            }

            else if(view.equals("download"))
            {

                File directoryToZip = new File("C://logs");

                List<File> fileList = new ArrayList<File>();
                System.out.println("---Getting references to all files in: " + directoryToZip.getCanonicalPath());
                zip.getAllFiles(directoryToZip, fileList);
                System.out.println("---Creating zip file");
                zip.writeZipFile(directoryToZip, fileList);
                OutputStream out = response.getOutputStream();
                FileInputStream in = new FileInputStream("logs.zip");
                byte[] buffer = new byte[4096];
                int length;
                while ((length = in.read(buffer)) > 0){
                    out.write(buffer, 0, length);
                }
                in.close();
                out.flush();
                RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
                dispatcher.forward(request, response);
            }
            else if(view.equals("add_for_shure"))
            {

                time=LocalTime.now();
                int[] username = bank.getMoney(Integer.parseInt(value_final));
                if (username == null) {
                    request.setAttribute("name","Невозможно выполнить операцию");
                } else {
                    String value2 = " Выдано 20$:" + username[0]
                            + "  50$:" + username[1]
                            + "  100$:" + username[2]
                            + "  200$:" + username[3]
                            + "  500$:" + username[4]
                            + " Сумма:" + username[5];
                    request.setAttribute("name", value2);
                    bank.changeMoneyEquivalent(username);
                    time = LocalTime.now();
                    bank.logFeed(reportDate, time + "[" + value2 + "]");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
                    dispatcher.forward(request, response);
                }
            }
            else {

                RequestDispatcher dispatcher = request.getRequestDispatcher("/error404.jsp");
                dispatcher.forward(request, response);
            }
        }
    }

    /**
     * Проверяет является ли значения Integer
     * @param s - строка для проверки
     * @return - возвращает true если число и false если не число
     */
    public static boolean isInteger(String s) {
        boolean isValidInteger = false;
        try
        {
            Integer.parseInt(s);

            // s is a valid integer

            isValidInteger = true;
        }
        catch (NumberFormatException ex)
        {
            // s is not an integer
        }

        return isValidInteger;
    }


}
