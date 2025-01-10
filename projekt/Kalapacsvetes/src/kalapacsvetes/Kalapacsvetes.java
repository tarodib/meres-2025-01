/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package kalapacsvetes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


/**
 *
 * @author tarodib
 */
public class Kalapacsvetes {

    /**
     * @param args the command line arguments
     */
       

     public static void main(String[] args) {
        // TODO code application logic here
        List<Sportolo> sportolok = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("kalapacsvetes.txt"))) {
            String sor;
            boolean isElsoSor = true;
            while ((sor = br.readLine()) != null) {
                if (isElsoSor) {
                    isElsoSor = false;
                    continue;
                }
                String[] parts = sor.split(";");
                int helyezes = Integer.parseInt(parts[0]);
                double eredmeny = Double.parseDouble(parts[1].replace(",", "."));
                String nev = parts[2];
                String orszagKod = parts[3];
                String helyszin = parts[4];
                String datum = parts[5];

                sportolok.add(new Sportolo(helyezes, eredmeny, nev, orszagKod, helyszin, datum));
            }
        } catch (Exception e) {
            System.out.println("Hiba a fájl beolvasása során: " + e.getMessage());
        }

        System.out.println("Dobások száma: " + sportolok.size());

        double osszeg = 0;
        int magyarDobasok = 0;

        for (Sportolo sportolo : sportolok) {
            if (sportolo.getOrszagkod().equals("HUN")) {
                osszeg += sportolo.getEredmeny();
                magyarDobasok++;
            }
        }

        if (magyarDobasok > 0) {
            double atlag = osszeg / magyarDobasok;
            System.out.printf("Magyar sportolók átlagos eredménye: %.2f\n", atlag);
        } else {
            System.out.println("Nincs magyar sportoló eredménye.");
        }


        Scanner scanner = new Scanner(System.in);
        System.out.print("Adj meg egy évszámot: ");
        String ev = scanner.nextLine();

        List<String> evSportolok = new ArrayList<>();

        for (Sportolo sportolo : sportolok) {
            if (sportolo.getDatum().startsWith(ev)) {
                evSportolok.add(sportolo.getNev());
            }
        }

        if (evSportolok.isEmpty()) {
            System.out.println("Az adott évben nem került be egy dobás eredménye sem a legjobbak közé.");
        } else {
            System.out.println("Az adott évben " + evSportolok.size() + " dobás került be a legjobbak közé.");
            System.out.println("Sportolók: " + String.join(", ", evSportolok));
        }

        Map<String, Integer> orszagStatisztika = new HashMap<>();
        

        for (Sportolo sportolo : sportolok) {
            orszagStatisztika.put(sportolo.getOrszagkod(), orszagStatisztika.getOrDefault(sportolo.getOrszagkod(), 0) + 1);
        }
        
        //hashmappel kulcs érték párokat tárol el, kulcs = országkód érték = dobások száma

        System.out.println("Statisztika az országok szerint:");
        for (Map.Entry<String, Integer> entry : orszagStatisztika.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " dobás");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("magyarok.txt", true))) {
            for (Sportolo sportolo : sportolok) {
                if (sportolo.getOrszagkod().equals("HUN")) {
                    writer.write(sportolo.getHelyezes() + ";" + sportolo.getEredmeny() + ";" + sportolo.getNev() + ";" + sportolo.getOrszagkod() + ";" + sportolo.getHelyszin() + ";" + sportolo.getDatum() + "\n");
                }
            }
        } catch (Exception e) {
            System.out.println("Hiba a magyarok.txt fájl írása során: " + e.getMessage());
        }

        scanner.close();
    }
}

    

