package br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data;

import android.content.Context;
import android.text.format.DateFormat;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by menga on 25/09/17.
 */

public class DataUtil {
    private static Context context = null;
    private static String version = "0.02";
    public static List<Artigo> createFakeListArtigos(int quant) {
        Artigo art;
        LinkedList<Artigo> lst = new LinkedList<>();
        for (int i = 0; i < quant; i++) {
            art = new Artigo();
            art.setAutor("Fulano Ciclano");
            art.setComentarios("Comentários " + i);
            art.setDataInicial(Calendar.getInstance().getTime());
            art.setDestinoPublicacao("Journal of Computer Science");
            art.setNome("Artigo Bla Bla Bla " + i);
            art.setStatusWorkflow("Inicial");
            art.setVersaoAtual("Documento v" + i + ".doc");
            lst.add(art);
        }
        return lst;
    }

    public static String formatDate(Date date) {
        return DateFormat.getDateFormat(context).format(date);
    }

    public static Date parseDate(String date) throws ParseException {
        return DateFormat.getDateFormat(context).parse(date);
    }

    public static void setContext(Context appContext) {
        context = appContext;
    }
}
