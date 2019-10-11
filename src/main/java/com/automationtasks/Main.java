package com.automationtasks;

import java.time.LocalDate;

public class Main {

        public static void main(String[] args){
            LocalDate startDate = LocalDate.of(2018, 10, 1);
            LocalDate endDate = LocalDate.of(2018, 11, 4);
            String valuteCharCode = "EUR";

            //Task #4
            System.out.println(BnmWebService.getValCurs(startDate));
            //Task #5
            System.out.println(BnmWebService.getValute(startDate, valuteCharCode));
            //Task #6
            System.out.println(BnmWebService.getMaxValute(startDate, endDate, valuteCharCode));
        }
}
