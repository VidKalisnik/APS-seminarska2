import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

class Postaja {

  public int index;
  public ArrayList<Povezava> povezave;

  public int najmStPostaj;
  public int najmStLinij;

  public Postaja prevPostaja;
  public Postaja prevPostajaPrestopanje;

  public Postaja(int index) {
    this.index = index;
    povezave = new ArrayList<>();
    najmStPostaj = Integer.MAX_VALUE;
    najmStLinij = Integer.MAX_VALUE;
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
    int zacetek = Integer.parseInt(lastLine[0]);
    int konec = Integer.parseInt(lastLine[1]);

    Postaja zacetna = poisciIndexPostaje(postaje, zacetek);
    dijkstra(zacetna, null, 0, 0, 0);
    dijkstra2(zacetna, null, 0, 0, 0, postaje);
    reader.close();

    Postaja koncna = poisciIndexPostaje(postaje, konec);
    //System.out.println(koncna.najmStLinij);
    //System.out.println(koncna.najmStPostaj);
    PrintWriter writer = new PrintWriter(outputFile);

    //ce ne pride do konce postaje
    if (koncna.najmStLinij == Integer.MAX_VALUE) {
      writer.println(-1);
      writer.println(-1);
      writer.println(-1);
    } else {
      writer.println(koncna.najmStLinij);
      writer.println(koncna.najmStPostaj);
      writer.println(istaPot(koncna));
    }
    writer.close();
  }

  public static int istaPot(Postaja a) {
    Postaja tmp1 = a.prevPostaja;
    Postaja tmp2 = a.prevPostajaPrestopanje;

    //System.out.println(tmp1.index);
    //System.out.println(tmp2.index);
    while (tmp1 != null || tmp2 != null) {
      if (!tmp1.equals(tmp2)) {
        break;
      }
      //System.out.println(tmp1.index);
      //System.out.println(tmp2.index);
      tmp1 = tmp1.prevPostaja;
      tmp2 = tmp2.prevPostajaPrestopanje;
    }

    if (tmp1 == null && tmp2 == null) {
      return 1;
    }
    return 0;
  }

  public static void dijkstra(
    Postaja a,
    Postaja b,
    int linija,
    int dolzina,
    int prestopi
  ) {
    //System.out.println(a.najmStLinij + " " + a.najmStPostaj);
    //ce je dolzina najmanjsa
    if (dolzina < a.najmStPostaj || dolzina == Integer.MAX_VALUE) {
      a.prevPostaja = b;
      a.najmStPostaj = dolzina;
      //a.najmStLinij = prestopi;

      for (Povezava p : a.povezave) {
        int prestop = 0;
        if (p.linija != linija && !a.equals(b)) {
          prestop = 1;
        }
        dijkstra(p.postaja, a, p.linija, dolzina + 1, prestopi + prestop);
      }
    }
  }

  public static void dijkstra2(
    Postaja a,
    Postaja b,
    int linija,
    int dolzina,
    int prestopi,
    Set postaje
  ) {
    /*if (b != null) System.out.println(
      a.index + " " + b.index + "|" + prestopi + " " + a.najmStLinij
    );*/
    //ce je dolzina najmanjsa
    if (prestopi < a.najmStLinij || a.najmStLinij == Integer.MAX_VALUE) {
      a.prevPostajaPrestopanje = b;
      /*if (b != null) System.out.println(
        "*" + a.index + " " + b.index + "|" + prestopi + " " + a.najmStLinij
      );*/
      a.najmStLinij = prestopi;

      for (Povezava p : a.povezave) {
        int prestop = 0;
        if (p.linija != linija && b != null) {
          prestop = 1;
        }
        dijkstra2(
          p.postaja,
          a,
          p.linija,
          dolzina + 1,
          prestopi + prestop,
          postaje
        );
      }
    }
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
