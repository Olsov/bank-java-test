package com.dataart.webapp.golovash;

import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Icomp on 24/06/2016.
 */

/**
 * Тест функций банкомата
 */
public class MoneyClassTest {
    @Test
    public void getMoney() throws IOException {
        int money=535;
        if (money < 20) {
            System.out.println("Невозможно выдать деньги");

        }
        int k = 0;
        int div = 0;
        int money_backup=money;
        int[] chet = new int[5];
        int[] vuvod=new int[6];
        int twenty = 20;
        int fifty = 20;
        int one_hundred = 20;
        int two_hundred = 20;
        int five_hundred = 20;

        while (money >= 20) {


            if (((money / 500) > 0) && (chet[4]<=10) &&(five_hundred>0) &&((money/500)<=(five_hundred))) {
                five_hundred = five_hundred - (money / 500);
                div = money / 500;
                chet[4]+=div;
                k = k + div * 500;
                if(money-k>=0){
                    money = money - k;}
                else{
                    money=money_backup-k;
                }

            } else if (((money / 200) > 0) && (chet[3]<= 10)&&(two_hundred>0)&& ((money/200)<=(two_hundred))){
                two_hundred -= (money / 200);
                div = money / 200;
                chet[3]+=div;
                k = k + div * 200;
                if(money-k>=0){
                    money = money - k;}
                else{
                    money=money_backup-k;
                }


            } else if (((money / 100) > 0) && (chet[2]<= 10)&&(one_hundred>0)&& ((money/100)<=(one_hundred))) {
                one_hundred -= (money / 100);
                div = money / 100;
                chet[2]+=div;
                k = k + div * 100;

                if(money-k>=0){
                    money = money - k;}
                else{
                    money=money_backup-k;
                }

            } else if (((money / 50) > 0) && (chet[1]<= 10)&&(fifty>0)&& ((money/50)<=(fifty))) {
                fifty -= (money / 50);
                div = money / 50;
                chet[1]+=div;
                k = k + div * 50;
                if(money-k>=0){
                    money = money - k;}
                else{
                    money=money_backup-k;
                }
            } else if (((money / 20) > 0) && (chet[0]<= 10)&&(twenty>0)&& ((money/20)<=(twenty))) {
                twenty -= (money / 20);
                div = money / 20;
                chet[0]+=div;
                k = k + div * 20;
                if(money-k>=0){
                    money = money - k;}
                else{
                    money=money_backup-k;
                }
            } else if (money < 20) {
                for (int i=0;i<=4;i++)
                {
                    vuvod[i]=chet[i];
                }
                vuvod[5]=k;
                System.out.println(vuvod);
                assertEquals(520,k);
            }
            else
            {
                System.out.println("Нету возможности выдать деньги");
            }

        }
        for (int i=0;i<=4;i++)
        {
            vuvod[i]=chet[i];
        }

        vuvod[5]=k;
        System.out.println(vuvod[5]);
      assertEquals(520,k);

    }
    @Test
    public void  putMoney()
    {
        int money=20;
        if(money<20)
        {
            System.out.println("No");
        }
        int[] array_money=new int[5];

        array_money[4]=19;
        array_money[3]=19;
        array_money[2]=16;
        array_money[1]=1;
        array_money[0]=3;
        if((money==500) &&(array_money[4]<20))
        {
            money-=500;
            array_money[4]+=1;
        }
        else  if((money-200)==0 &&(array_money[3]<20))
        {
            money-=200;
            array_money[3]+=1;
        }     else  if((money-100)==0&&(array_money[2]<20))
        {
            money-=100;
            array_money[2]+=1;
        }     else  if((money-50)==0&&(array_money[1]<20))
        {
            money-=50;
            array_money[1]+=1;
        }     else  if((money-20)==0&&(array_money[0]<20))
        {
            money-=20;
            array_money[4]+=1;
        }
        else{assertEquals(money,money);}
        assertEquals(0,money);
    }
}
