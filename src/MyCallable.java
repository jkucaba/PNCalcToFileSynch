import java.util.ArrayList;
import java.util.concurrent.Callable;

import static java.lang.Integer.parseInt;

public class MyCallable implements Callable<Double> {
    String rownanie; // pobrane rownanie
    Double wynik;       // wynik rownaniea

    public MyCallable(String line) {
        this.rownanie = line;
    }
    public ArrayList<String> obliczONP(String[] args, int j) {
        int stackIndex = -2;
        String [] Stack = new String [30];
        ArrayList<String> wynik= new ArrayList<>();   // lista wynikowa do której będziemy kolejno dodawać wartości

        for(int i=0; i<args[j].length(); i++){     // po raz kolejny iterujemy przez wszystkie znaki w wyrażeniu
            //w warunku sprawdzmy czy mamy do czynienia z liczba
            if(args[j].charAt(i) != '+' && args[j].charAt(i) != '-' && args[j].charAt(i) != '*' && args[j].charAt(i) != '/' &&args[j].charAt(i) != '(' && args[j].charAt(i) != ')' && args[j].charAt(i) != '^'){
                if(i<args[j].length()-1){
                    int u = i+1;
                    // w pętli iterujemy przez wszytskie liczby i zestawiamy je ze sobą (chodzi o przypadek gdy mamy np. 1234)
                    while(args[j].charAt(u) != '+' && args[j].charAt(u) != '-' && args[j].charAt(u) != '*' && args[j].charAt(u) != '/' &&args[j].charAt(u) != '(' && args[j].charAt(u) != ')' && args[j].charAt(u) != '^' && u < args[j].length()-1){
                        u++;
                    }
                    if(args[j].charAt(u) != '+' && args[j].charAt(u) != '-' && args[j].charAt(u) != '*' && args[j].charAt(u) != '/' &&args[j].charAt(u) != '(' && args[j].charAt(u) != ')' && args[j].charAt(u) != '^'){
                        u++;
                    }
                    StringBuilder str = new StringBuilder();
                    for(int c=i; c<u; c++){
                        str.append(args[j].charAt(c));
                    }
                    wynik.add(str.toString());
                    i=u-1;
                }
                else{
                    wynik.add(String.valueOf(args[j].charAt(i)));
                }

            }
            else{
                // przypadek pierwszy (czytamy pierwszy operator) - wykonuje się raz
                if (stackIndex == -2 && (args[j].charAt(0) != '(')) {
                    stackIndex += 2;
                    Stack[stackIndex] = String.valueOf(args[j].charAt(i));
                }
                // kolejno ustalamy czy pojawienie się operatora zdejmuje znajdujące się na stosie operatory
                else {
                    if(args[j].charAt(i)=='('){
                        if(stackIndex == -2){
                            stackIndex += 2;
                        }
                        else {
                            stackIndex++;
                        }
                        Stack[stackIndex] = String.valueOf(args[j].charAt(i));
                    }
                    // operator '+' zdejmuje operatory o takim samym lub wyższym priorytecie itd.
                    if(args[j].charAt(i)=='+'){
                        if(Stack[stackIndex].equals("-") ||Stack[stackIndex].equals("+") || Stack[stackIndex].equals("*")||Stack[stackIndex].equals("/")||Stack[stackIndex].equals("^")){
                            while((Stack[stackIndex].equals("-") ||Stack[stackIndex].equals("+") || Stack[stackIndex].equals("*")||Stack[stackIndex].equals("/")||Stack[stackIndex].equals("^"))&&stackIndex>0) {
                                wynik.add(Stack[stackIndex]);
                                stackIndex--;
                            }
                            if((stackIndex) == 0 && (Stack[stackIndex].equals("-") ||Stack[stackIndex].equals("+") || Stack[stackIndex].equals("*")||Stack[stackIndex].equals("/")||Stack[stackIndex].equals("^"))){
                                wynik.add(Stack[stackIndex]);
                                Stack[stackIndex] = String.valueOf(args[j].charAt(i));
                                continue;
                            }
                        }
                        stackIndex++;
                        Stack[stackIndex] = String.valueOf(args[j].charAt(i));

                    }
                    if(args[j].charAt(i)=='-'){
                        if(Stack[stackIndex].equals("-") || Stack[stackIndex].equals("+") || Stack[stackIndex].equals("*")||Stack[stackIndex].equals("/") || Stack[stackIndex].equals("^")){
                            while((Stack[stackIndex].equals("-") || Stack[stackIndex].equals("+") || Stack[stackIndex].equals("*")||Stack[stackIndex].equals("/")||Stack[stackIndex].equals("^"))&& stackIndex > 0) {
                                wynik.add(Stack[stackIndex]);
                                stackIndex--;
                            }
                            if((stackIndex) == 0 && (Stack[stackIndex].equals("-") ||Stack[stackIndex].equals("+") || Stack[stackIndex].equals("*")||Stack[stackIndex].equals("/")||Stack[stackIndex].equals("^"))){
                                wynik.add(Stack[stackIndex]);
                                Stack[stackIndex] = String.valueOf(args[j].charAt(i));
                                continue;
                            }
                        }
                        stackIndex++;
                        Stack[stackIndex] = String.valueOf(args[j].charAt(i));
                    }
                    if(args[j].charAt(i)=='*'){
                        if(Stack[stackIndex].equals("*") || Stack[stackIndex].equals("/") || Stack[stackIndex].equals("^") ){
                            while((Stack[stackIndex].equals("*")||Stack[stackIndex].equals("/")||Stack[stackIndex].equals("^")) && stackIndex > 0) {
                                wynik.add(Stack[stackIndex]);
                                stackIndex--;
                            }
                            if((stackIndex) == 0 && (Stack[stackIndex].equals("*") || Stack[stackIndex].equals("/") || Stack[stackIndex].equals("^") )){
                                wynik.add(Stack[stackIndex]);
                                Stack[stackIndex] = String.valueOf(args[j].charAt(i));
                                continue;
                            }
                        }
                        stackIndex++;
                        Stack[stackIndex] = String.valueOf(args[j].charAt(i));
                    }
                    if(args[j].charAt(i)=='/'){
                        if(Stack[stackIndex].equals("*")|| Stack[stackIndex].equals("/") ||Stack[stackIndex].equals("^")){
                            while((Stack[stackIndex].equals("*")||Stack[stackIndex].equals("/")||Stack[stackIndex].equals("^")) && stackIndex > 0) {
                                wynik.add(Stack[stackIndex]);
                                stackIndex--;
                            }
                            if((stackIndex) == 0 && (Stack[stackIndex].equals("*") || Stack[stackIndex].equals("/") || Stack[stackIndex].equals("^") )){
                                wynik.add(Stack[stackIndex]);
                                Stack[stackIndex] = String.valueOf(args[j].charAt(i));
                                continue;
                            }
                        }
                        stackIndex++;
                        Stack[stackIndex] = String.valueOf(args[j].charAt(i));
                    }
                    if(args[j].charAt(i)=='^'){
                        if(Stack[stackIndex].equals("^")){
                            wynik.add(Stack[stackIndex]);
                        }
                        else {
                            stackIndex++;
                        }
                        Stack[stackIndex] = String.valueOf(args[j].charAt(i));
                    }
                    if(args[j].charAt(i)==')'){
                        while(!Stack[stackIndex].equals("(") && stackIndex > 0) {
                            wynik.add(Stack[stackIndex]);
                            stackIndex--;
                        }
                        if(stackIndex != 0) {
                            stackIndex--;
                        }
                    }
                }
            }
        }
        //w tej pętli dodajemy operatory które zostały na stosie
        for(int i=stackIndex; i>=0; i--){
            if(!Stack[i].equals("(")) {
                wynik.add(Stack[i]);
            }

        }
        return wynik;
    }
    public double kalkulatorZOnp(ArrayList<String> x) {
        double [] Stos = new double [20];
        int stosIndex = -1;
        for(int i=0; i<x.toArray().length; i++){//iterujemy pętlą przez całe wyrażenie
            //w warunku sprawdzamy czy mamy doczynienia z liczbą czy może z operatorem, jeśli z liczbą to dodajemy ją na stos
            if(!x.get(i).equals("+") && !x.get(i).equals("-") && !x.get(i).equals("*") && !x.get(i).equals("/") && !x.get(i).equals("(") && !x.get(i).equals(")") && !x.get(i).equals("^")){
                stosIndex++;
                Stos[stosIndex] = parseInt(x.get(i));
            }
            //w przypadku operatora bierzemy dwie wartości ze stosu (z góry i jedną przed górną) i wykonujemy odpowiednią operację
            else {
                double s1 = (Stos[stosIndex]);  //liczba z góry stosu
                int sI = stosIndex-1;
                double z = Stos[sI];            //2 liczba licząc od góry stosu

                switch (x.get(i)) {
                    case "+" -> {
                        stosIndex--;                //zmniejszamy wierzchołek stosu o 1 i tam zapisujemy wynik operacji
                        Stos[stosIndex] = z + s1;
                    }
                    case "-" -> {
                        stosIndex--;
                        Stos[stosIndex] = z - s1;
                    }
                    case "*" -> {
                        stosIndex--;
                        Stos[stosIndex] = z * s1;
                    }
                    case "/" -> {
                        stosIndex--;
                        Stos[stosIndex] = z / s1;
                    }
                    case "^" -> {
                        stosIndex--;
                        Stos[stosIndex] = Math.pow(z, s1);
                    }
                }
            }
        }
        return Stos[0];
    }

    @Override
    public Double call(){
        System.out.println("Thread " + Thread.currentThread().getName() + ": " + "Obliczam równanie: " + this.rownanie);

        // Obliczenie rownania
        String[] equation = new String[1];
        equation[0] = this.rownanie;
        ArrayList<String> ONP;

        // Najpierw obliczamy ONP naszego rownania
        ONP = obliczONP(equation, 0);

        // Potem obliczamy wynik
        this.wynik = kalkulatorZOnp(ONP);

        return kalkulatorZOnp(ONP);
    }

    public Double getWynik(){
        return this.wynik;
    }

    public String getRownanie(){
        return this.rownanie;
    }
}