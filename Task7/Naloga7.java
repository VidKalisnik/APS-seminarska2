import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

class Postaja {

  public int index;
  public ArrayList<Povezava> povezave;

  public Postaja(int index) {
    this.index = index;
    povezave = new ArrayList<>();
  }

  public void dodajPovezavo(Postaja postaja, int linija) {
    povezave.add(new Povezava(postaja, linija));
  }
}

class Povezava {

  public Postaja postaja;
  public int linija;

  public Povezava(Postaja postaja, int linija) {
    this.postaja = postaja;
    this.linija = linija;
  }
}

public class Naloga7 {

  public static void main(String[] args) throws IOException {
    String inputFile = args[0];
    String outputFile = args[1];

    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    int stLinij = Integer.parseInt(reader.readLine());
    //mnozica vseh postaj
    Set<Postaja> postaje = new HashSet<>();
    //dodajamo linije
    for (int i = 0; i < stLinij; i++) {
      String[] linija = reader.readLine().split(",");
      //prva postaja linije
      int source = Integer.parseInt(linija[0]);
      //pogleda ce je postaja ze v mnozici
      Postaja sPostaja = poisciIndexPostaje(postaje, source);
      if (sPostaja == null) {
        //ce je ni naredi novo postajo
        sPostaja = new Postaja(source);
        postaje.add(sPostaja);
      }
      //dodajamo se ostale postaje v liniji
      for (int j = 1; j < linija.length; j++) {
        //naredimo postajo
        int destination = Integer.parseInt(linija[j]);
        //pogleda ce je postaja ze v mnozici
        Postaja dPostaja = poisciIndexPostaje(postaje, destination);
        if (dPostaja == null) {
          //ce je ni naredi novo postajo
          dPostaja = new Postaja(destination);
          postaje.add(dPostaja);
        }
        sPostaja.dodajPovezavo(dPostaja, i);
        sPostaja = dPostaja;
      }
    }
    String[] lastLine = reader.readLine().split(",");
    int source = Integer.parseInt(lastLine[0]);
    int destination = Integer.parseInt(lastLine[1]);
    reader.close();

    PrintWriter writer = new PrintWriter(outputFile);
    /*writer.println(minTransfers);
    writer.println(minStops);
    writer.println(minStopsAndTransfers);*/
    writer.close();
  }

  public static Postaja poisciIndexPostaje(Set<Postaja> set, int index) {
    for (Postaja p : set) {
      if (p.index == index) {
        return p;
      }
    }
    return null;
  }
}
