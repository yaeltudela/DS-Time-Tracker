package core.ds.ds_project_timetracker;

import java.util.Collection;
import java.util.Iterator;

import html.Attribute;
import html.Tag;

public class Webpage {
    private Tag webpage = new Tag("html");
    private Tag head = new Tag("head");
    private Tag body = new Tag("body");

    public Webpage() {
        Tag title = new Tag("title");
        title.add("TimeTracker Report");
        head.add(title);
        webpage.add(head);
        webpage.add(body);
    }

    public void addHeader(String string, int size, boolean center) {
        // fa text h1, h2 ... h6
        if (size >= 1 && size <= 6) {
            Tag h = new Tag("h" + (new Integer(size)).toString());
            h.add(string);
            if (center) {
                h.addAttribute(new Attribute("style", "text-align: center;"));
            }
            body.add(h);
        }
    }

    public void addText(String string) {
        body.add(string);
    }

    public void addBR() {
        body.add(new Tag("br", false));
    }

    public void addTable(Collection table,
                         boolean firstRowHeader,
                         boolean firstColHeader) {
        // taula es una llista (files) de llistes (columnes), implementat com un
        // arraylist d'arraylists, encara que aqui per mes generalitat hi posem
        // el tipus generic collection

        /*
         * Exemple : taula amb capsalera a la primera fila
         *
         * <table style= "text-align: left; width: 842px;" border="1" cellpadding="2" cellspacing="2">
         *      <tbody>
         *          <tr>
         *              <th style="background-color: rgb(102, 255, 255);">No.</th>
         *              <th style="background-color: rgb(102, 255, 255);">Projecte</th>
         *              <th style="background-color: rgb(102, 255, 255);">Data d'inici</th>
         *              <th style="background-color: rgb(102, 255, 255);">Data final</th>
         *              <th style="background-color: rgb(102, 255, 255);">Temps total</th>
         *          </tr>
         *          <tr>
         *              <td style="background-color: rgb(204, 255, 255);">1</td>
         *              <td style="background-color: rgb(204, 255, 255);">P&agrave;gina web personal</td>
         *              <td style="background-color: rgb(204, 255, 255);">15/11/2006, 19:00h</td>
         *              <td style="background-color: rgb(204, 255, 255);">25/11/2006, 20:00h</td>
         *              <td style="background-color: rgb(204, 255, 255);">25h 45m 0s</td>
         *          </tr>
         *      </tbody>
         * </table>
         */
        Tag t = new Tag("table");
        t.addAttribute(new Attribute("style", "text-align: left; width: 842px;"));
        t.addAttribute(new Attribute("border", "1"));
        t.addAttribute(new Attribute("cellpadding", "2"));
        t.addAttribute(new Attribute("cellspacing", "2"));

        Tag tbody = new Tag("tbody");
        // les cel.les de capsalera tenen fons en blau fosc
        Attribute estilTh = new Attribute("style", "background-color: rgb(102, 255, 255);");
        // les cel.les de dades, fons en blau clar
        Attribute estilTd = new Attribute("style", "background-color: rgb(204, 255, 255);");

        Iterator itFiles = table.iterator();
        Iterator itColumnes;
        boolean primeraFila = true;
        while (itFiles.hasNext()) {
            Tag tr = new Tag("tr"); // cada fila de la taula
            itColumnes = ((Collection) itFiles.next()).iterator();
            boolean primeraColumna = true;
            while (itColumnes.hasNext()) {
                if ((primeraFila && firstRowHeader) ||
                        (primeraColumna && firstColHeader)) { // th en comptes de td
                    Tag th = new Tag("th");
                    th.addAttribute(estilTh);
                    th.add(itColumnes.next().toString());
                    tr.add(th);
                } else {
                    Tag td = new Tag("td");
                    td.addAttribute(estilTd);
                    td.add(itColumnes.next().toString());
                    tr.add(td);
                }
                primeraColumna = false;
            }
            primeraFila = false;
            tbody.add(tr);
        }
        t.add(tbody);
        body.add(t);
    }

    public void addSeparationLine() {
        Tag hr = new Tag("hr");
        hr.addAttribute(new Attribute("style", "width: 100%; height: 2px;"));
        // <hr style="width: 100%; height: 2px;">
        body.add(hr);
    }


    public void printWebpage() {
        System.out.println(this.webpage);
    }
}
