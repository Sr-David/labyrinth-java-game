//Fer un joc del laberint, on el jugador ha de trovar la sortida del laberint sense coneixel.
import java.util.Scanner;

public class laberint {
    public static void main(String[] args) {



        char[][] laberint = crearLaberint();
        char[][] laberint2 = crearLaberint2();
        boolean sortir = false;
        boolean piqueta = false;
        laberint2[1][1] = 'J'; // Posar el jugador a la posició inicial
        String moviment = "";
        int vidas = 5;

        inici();     
        mostrarLaberint(laberint2, vidas);
        
        while (!moviment.equals("sortir") && !sortir && vidas > 0) {
            moviment = accio();    
            if (moviment.equals("ajuda")) {mostrarAjuda(comprovarPosicion(laberint2)[0], comprovarPosicion(laberint2)[1], laberint);} //Mostrar ajuda

            char vei = comprovarVei(moviment, laberint, comprovarPosicion(laberint2)[0], comprovarPosicion(laberint2)[1]);
            piqueta = piqueta(vei, piqueta);
            laberint2 = moviment(moviment, laberint, laberint2, piqueta);
            
            if (vei == '@') {vidas--;} //Restar vida si troba una bomba

            mostrarLaberint(laberint2, vidas);
            sortir = sortida(vei);
            
        }
        System.out.println("El Laberint sencer és:");
        mostrarLaberint(laberint, vidas);

        System.out.println("Gràcies per jugar!");
    }


    //Metodo para mostrar menu de inicio
    public static void inici(){


        System.out.println("Benvingut al joc del llaberint!");
        System.out.println("Aquest joc consisteix en intentar trobar la sortida dins de un llaberint invisible!");
        System.out.println("El jugador es troba a la posició 'J'. Si es troba amb una bomba, perdrà una vida i tornarà a la posició inicial.");
        System.out.println("Si es troba amb una piqueta, podrà travesar les parets del llaberint.");
        System.out.println("Per indicar on vols anar, has de introduir el text (escriure 'sortir' per sortir): ");
        System.out.println("- amunt");
        System.out.println("- avall");
        System.out.println("- esquerra");
        System.out.println("- dreta");
        System.out.println("- ajuda");
        System.out.println("");


    }

    //Metodo para crear el laberinto
    public static char[][] crearLaberint(){

        
        char[][] laberint = {
            {'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'},
            {'#','J',' ',' ',' ','@',' ',' ',' ','#','#','#','#','#','@',' ',' ','#','#','#','#','#','#','#','#','#','#','#','#','#'},
            {'#','#','#','#',' ','#',' ','@',' ','#','#','#','#','#',' ','#',' ','#','@',' ',' ',' ',' ',' ','#',' ','#','#','#','#'},
            {'#',' ',' ',' ',' ',' ',' ',' ',' ','#','#',' ',' ',' ',' ',' ',' ',' ','#',' ','#','#','#',' ',' ',' ',' ',' ',' ','#'},
            {'#',' ','@','#','#',' ','#','#',' ',' ',' ',' ','#','#','#','#',' ',' ',' ',' ','@','#',' ',' ','#',' ','#','#','@','#'},
            {'#',' ','@','#',' ','@','#',' ',' ','#','#','@',' ',' ','#',' ',' ','@','#','#','#','#',' ',' ','@',' ',' ','@','#','#'},
            {'#',' ',' ','@',' ','#',' ','@','#','#',' ',' ',' ','#','@','#','@','#','#',' ',' ','#','#',' ',' ','@','#','#',' ','#'},
            {'#','@',' ','#','@','#','#','#',' ','@',' ','#','#',' ','#',' ','#',' ','#','#',' ',' ','#','@',' ',' ','@',' ','#','#'},
            {'#','@',' ',' ','#','#','#',' ','#','#',' ','#','#',' ','#',' ','#',' ','#','#',' ','#','#','#','@',' ','@','#','#','#'},
            {'#','#','@','?','#',' ',' ',' ',' ',' ','@',' ','#',' ',' ','#',' ',' ','@',' ','#',' ',' ',' ',' ','F','#','#',' ','#'},
            {'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'}
        };

        return laberint;

    }
    
    //Metodo para crear el segundo laberinto (con el que se juega)
    public static char[][] crearLaberint2(){

        char[][] laberint2 = new char[11][30];

        for (int i = 0; i < laberint2.length; i++) {
            for (int j = 0; j < laberint2[i].length; j++) {
                laberint2[i][j] = ' ';
            }
        }


        return laberint2;
    

    }

    //Mostrar laberint

    public static void mostrarLaberint(char[][] laberint, int vidas){

       
        System.out.println("Vides: " + vidas);

        System.out.println("--------------------------------------------------------------");
        for (int i = 0; i < laberint.length; i++) {
            System.out.print("|");
            for (int j = 0; j < laberint[i].length; j++) {
                System.out.print(laberint[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("--------------------------------------------------------------");
    }

    //Metodo para indicar la accion que se quiere realizar

    public static String accio(){

        Scanner scan = new Scanner(System.in);

        System.out.println("");
        System.out.println("Escriu que vols fer:");
        String input = scan.next();
        input = input.toLowerCase();
        while (!input.equals("amunt") && !input.equals("avall") && !input.equals("esquerra") && !input.equals("dreta") && !input.equals("sortir") && !input.equals("ajuda")) {
            System.out.println("Acció no vàlida, torna a escriure:");
            input = scan.next();
            input = input.toLowerCase();
            
        }

        return input;

    }

    


    public static char[][] moviment(String moviment, char[][] laberint, char[][] laberint2, boolean piqueta){

        //Trobar la posició actual del jugador
        int posicion[] = comprovarPosicion(laberint2);
        int alturaActual = posicion[0];
        int ampladaActual = posicion[1];

        //Comprovar el vei
        char vei = comprovarVei(moviment, laberint, alturaActual, ampladaActual);
        
        if (vei == ' ') {
            
            laberint2= movimentCorrecte(laberint, alturaActual, ampladaActual, moviment, laberint2);

        }else if (vei == '?') { //Troba una piqueta
            
            laberint2= movimentCorrecte(laberint, alturaActual, ampladaActual, moviment, laberint2);
            System.out.println("");
            System.out.println("----------------------------------");
            System.out.println("Has trobat una piqueta! Ara pots travesar les parets!");
            System.out.println("----------------------------------");
            System.out.println("");

        } else if(vei == '@'){ //Troba una bomba
            
            laberint2 = trobarBomba(moviment, alturaActual, ampladaActual, laberint2, vei);

        } else if (vei == '#') { // Mur
            if (piqueta) {
                laberint2= movimentCorrecte(laberint, alturaActual, ampladaActual, moviment, laberint2);
            }else{
                laberint2 = afegirMur(moviment, laberint, alturaActual, ampladaActual, laberint2, vei);
            }

        } else if (vei == 'F') { //Sortida

            laberint2 = trobarSortida(laberint2, alturaActual, ampladaActual, moviment, vei, laberint);
        }

        return laberint2;
        
            
    }

    


    //Comprovar posició del jugador
    public static int[] comprovarPosicion(char[][] laberint2){

        int[] posicion = new int[2];
        int altura = 0;
        int amplada = 0;
        for (int i = 0; i < laberint2.length; i++) {
            for (int j = 0; j < laberint2[i].length; j++) {
                if (laberint2[i][j] == 'J') {
                    altura = i;
                    amplada = j;
                }
            }
        }

        posicion[0] = altura;
        posicion[1] = amplada;

        return posicion;


    }


    //Comprovar la casella que te al costat al que es mou

    public static char comprovarVei(String moviment, char[][] laberint, int alturaActual, int ampladaActual){

        char vei = ' ';

        if (moviment.equals("amunt") && (alturaActual - 1) >= 0){
            vei = laberint[alturaActual - 1][ampladaActual];
        } else if (moviment.equals("avall") && (alturaActual + 1) < laberint.length){
            vei = laberint[alturaActual + 1][ampladaActual];
        } else if (moviment.equals("esquerra") && (ampladaActual - 1) >= 0){
            vei = laberint[alturaActual][ampladaActual - 1];
        } else if (moviment.equals("dreta") && (ampladaActual + 1) < laberint[0].length){
            vei = laberint[alturaActual][ampladaActual + 1];
        }

        return vei;

    }



    //Afegir mur al laberint
    public static char[][] afegirMur(String moviment, char[][] laberint, int alturaActual, int ampladaActual, char[][] laberint2, char vei){

        

        if (moviment.equals("amunt")) {
        
            laberint2[alturaActual - 1][ampladaActual] = vei;

        }else if (moviment.equals("avall")) {
            
            laberint2[alturaActual + 1][ampladaActual] = vei;
        }else if (moviment.equals("esquerra")) {
            
            laberint2[alturaActual][ampladaActual - 1] = vei;
        }else if (moviment.equals("dreta")) {
                
            laberint2[alturaActual][ampladaActual + 1] = vei;
        }
        

        
      
        return laberint2;

    }

    // Dinamica de trobar una bomba 


    public static char[][] trobarBomba(String moviment, int alturaActual, int ampladaActual, char[][] laberint2, char vei){

        laberint2[alturaActual][ampladaActual] = '+';  // Marcar el camí que ha fet el jugador
        switch (moviment) {
            case "amunt":
                laberint2[alturaActual - 1][ampladaActual] = vei;
                break;
            case "avall":
                laberint2[alturaActual + 1][ampladaActual] = vei;
                break;

            case "esquerra":
                laberint2[alturaActual][ampladaActual - 1] = vei;
                break;
            case "dreta":
                laberint2[alturaActual][ampladaActual + 1] = vei;
            break;
        }

        laberint2[1][1] = 'J'; // Posar el jugador a la posició inicial
        System.out.println("Has trobat una bomba! Perds una vida i tornes a la posició inicial.");
        return laberint2;

    }


    //Fer que el jugador es mogui

    public static char[][] movimentCorrecte(char[][] laberint , int alturaActual, int ampladaActual, String moviment, char[][] laberint2){

     

        if ((moviment.equals("amunt")) && ((alturaActual - 1) >= 0)) {
            laberint2[alturaActual - 1][ampladaActual] = 'J';
            laberint2[alturaActual][ampladaActual] = '+';
        } else if (moviment.equals("avall") && ((alturaActual + 1) < laberint.length)) {
            laberint2[alturaActual + 1][ampladaActual] = 'J';
            laberint2[alturaActual][ampladaActual] = '+';
        } else if (moviment.equals("esquerra") && ((ampladaActual - 1) >= 0)){
            laberint2[alturaActual][ampladaActual - 1] = 'J';
            laberint2[alturaActual][ampladaActual] = '+';
        } else if (moviment.equals("dreta") && ((ampladaActual + 1) < laberint[0].length)){
            laberint2[alturaActual][ampladaActual + 1] = 'J';
            laberint2[alturaActual][ampladaActual] = '+';
        }

        return laberint2;

    }



    //Comprovar si el jugador ha trobat la sortida

    public static char[][] trobarSortida(char[][] laberint2, int alturaActual, int ampladaActual, String moviment, char vei, char[][] laberint){

        
        if ((moviment.equals("amunt")) && ((alturaActual - 1) >= 0)) {
            laberint2[alturaActual - 1][ampladaActual] = vei;
        } else if (moviment.equals("avall") && ((alturaActual + 1) < laberint.length)) {
            
            laberint2[alturaActual + 1][ampladaActual] = vei;
        } else if (moviment.equals("esquerra") && ((ampladaActual - 1) >= 0)){
            laberint2[alturaActual][ampladaActual - 1] = vei;
        } else if (moviment.equals("dreta") && ((ampladaActual + 1) < laberint[0].length)){
            laberint2[alturaActual][ampladaActual + 1] = vei;
        }


        return laberint2;
    }




    //Salir del bucle principal al acabar

    public static boolean sortida(char vei){

        boolean sortir = false;

        if (vei == 'F') {
            sortir = true;
            System.out.println("");
            System.out.println("----------------------------------");
            System.out.println("Has trobat la sortida! Felicitats!");
            System.out.println("----------------------------------");
        }



        return sortir;

    }
    


    //Trobar piqueta

    public static boolean piqueta(char vei, boolean piqueta){
        

        if (vei == '?' || piqueta == true) {
            piqueta = true;
          
        }



        return piqueta;

    }


    //Aquest metode serveix per indicar al jugador on es troba la sortida aproximadament

    public static void mostrarAjuda(int alturaActual, int ampladaActual, char[][] laberint){

        int alturaFinal = coordenadasFinal(laberint)[0];
        int ampladaFinal = coordenadasFinal(laberint)[1];

        if(alturaActual < alturaFinal){
            System.out.println("La sortida es troba més avall.");
        }else if(alturaActual > alturaFinal){
            System.out.println("La sortida es troba més amunt.");
        }

        if(ampladaActual < ampladaFinal){
            System.out.println("La sortida es troba més a la dreta.");
        }else if(ampladaActual > ampladaFinal){
            System.out.println("La sortida es troba més a l'esquerra.");
        }

    }


    public static int[] coordenadasFinal(char[][] laberint){

        int[] coordenadas = new int[2];
        int altura = 0;
        int amplada = 0;
        for (int i = 0; i < laberint.length; i++) {
            for (int j = 0; j < laberint[i].length; j++) {
                if (laberint[i][j] == 'F') {

                    altura = i;
                    amplada = j;
                }
            }
        }
        coordenadas[0] = altura;
        coordenadas[1] = amplada;

        return coordenadas;



    }
    



}
