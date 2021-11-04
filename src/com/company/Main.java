package com.company;
import java.util.Scanner;

public class Main {

    public static String delDoubleSpaces (String str){
        while (str.contains("  ")){
            str = str.replaceAll("  ", " ");
        }
        return str;
    }

    public static boolean isNumeric(String strNum) {
        try {
            double d = Integer.parseInt(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    // для перевода входных римских чисел в десятичные. Также годится для проверки является ли входное римским от 1 до 10
    public static int romanToDec(String x){
        final String[] ROMANNUMS = {"I","II","III","IV","V","VI","VII","VIII","IX","X"};
        int result = 0;
        for (int i = 1; i<=10; i++){
            if (x.equals(ROMANNUMS[i-1])){
                result = i;
            }
        }
        return result;
    }

    public static String getNumberType(String num1, String num2) {
        if ((isNumeric(num1)) && (isNumeric(num2))){
            return "decimal";
        }
        else if ((romanToDec(num1) != 0) && (romanToDec(num2) != 0)){
            return "roman";
        }
        else return "";
    }

    public static String getActionType(String act){
        final String ACTIONS = "+-*/";
        var idx = ACTIONS.indexOf(act);
        if (idx == -1){
            return null;
        }
        else{
            return act;
        }
    }

    public static Integer doCalculate(int x, String act, int y){
        switch (act){
            case "+" : return x+y;
            case "-" : return x-y;
            case "*" : return x*y;
            case "/" : return x/y;
            default  : return null;
        }
    }

    public static String decToRoman(int x){
        final String[] ROMANNUMS = {"I","II","III","IV","V","VI","VII","VIII","IX","X"};
        final String[] ROMANTENS = {"X","XX","XXX","XL","L","LX","LXX","LXXX","XC"};
        if ((x >= 1) && (x <= 10)){
            return ROMANNUMS[x-1];
        }
        else if ((x >= 11) && (x <= 99)){
            var str = Integer.toString(x);
            if (str.charAt(1) == '0'){
                return ROMANTENS[Integer.parseInt(Character.toString(str.charAt(0)))-1];
            }
            else return ROMANTENS[Integer.parseInt(Character.toString(str.charAt(0)))-1]+
                    ROMANNUMS[Integer.parseInt(Character.toString(str.charAt(1)))-1];
        }
        else if (x == 100) {
            return "C";
        }
        else return "";
    }

    public static void main(String[] args) {
        try{
            // вводим строку, чистим от лишних пробелов (начальных, конечных, дублированных)
            System.out.print("Input a string: ");
            Scanner input = new Scanner(System.in);
            String inputData = input.nextLine().strip();
            inputData = delDoubleSpaces(inputData);
            if (inputData.isEmpty()){
                throw new Exception("throws Exception: ошибка, пустая входная строка");
            }

            // выделяем три входных параметра из введённых данных
            String[] argsData = {"","",""};
            int i = 0;
            int index = -1;
            while (i < 3 && !inputData.isEmpty()){
                index = inputData.indexOf(' ');
                if (index > -1){
                    argsData[i] = inputData.substring(0,index).strip();
                    inputData = inputData.substring(index+1);
                }
                else {
                    argsData[i] = inputData.strip();
                    inputData = "";
                }
                // если число содержит точку - делаем пустым т.е. выдаст ошибку недосточности аргументов
                if (argsData[i].indexOf('.') >= 0) {
                    argsData[i]="";
                }
                i++;
            }

            if (!inputData.isEmpty()){
                throw new Exception("throws Exception: ошибка, введено более 3 параметров");
            }


            // Вычисляем
            int calcResult;
            int x;
            int y;
            if ((argsData[0].equals("")) || (argsData[1].equals("")) || (argsData[2].equals(""))){
                throw new Exception("throws Exception: не хватает аргументов");
            }
            else{
                var actionType = getActionType(argsData[1]);
                var numbersType = getNumberType(argsData[0].strip(),argsData[2].strip());
                //System.out.println("Тип введённых чисел: \""+numbersType+"\"");
                if (actionType != null){
                    switch (numbersType){
                        case "decimal":
                            x = Integer.parseInt(argsData[0]);
                            y = Integer.parseInt(argsData[2]);
                            if ((x>=1) && (x<=10) && (y>=1) && (y<=10)){
                                calcResult = doCalculate (x,actionType,y);
                                System.out.println(calcResult);
                            }
                            else throw new Exception("throws Exception: десятичные аргументы вне диапазона 1..10");
                            break;
                        case "roman":
                            x = romanToDec(argsData[0]);
                            y = romanToDec(argsData[2]);
                            calcResult = doCalculate (x,actionType,y);
                            if (calcResult > 0){
                                System.out.println(decToRoman(calcResult));
                            }
                            else throw new Exception("throws Exception: римский результат не может быть меньше нуля");
                            break;
                        default: throw new Exception("throws Exception: форматы входных чисел не совпадают или вне диапазона 1..10");
                    }
                }
                else throw new Exception("throws Exception: неправильно указано математическое действие");

            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            System.exit(1);
        }
    }
}
