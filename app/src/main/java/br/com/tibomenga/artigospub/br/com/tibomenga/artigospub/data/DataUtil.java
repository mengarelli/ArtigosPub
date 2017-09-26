package br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import br.com.tibomenga.artigospub.R;

/**
 * Created by menga on 25/09/17.
 */

public class DataUtil {
    private static String LOG_TAG = DataUtil.class.getSimpleName();
    private static Context context = null;
    private static String version = "0.03";
    private static SimpleDateFormat dfmSQL = new SimpleDateFormat("yyyy-MM-dd");
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
            art.setStatusWorkflow(0);
            art.setVersaoAtual("Documento v" + i + ".doc");
            lst.add(art);
        }
        return lst;
    }

    public static String getWorkflowDescription(int cod) {
        String[] workflow = context.getResources().getStringArray(R.array.workflow_entries);
        if (cod < workflow.length) {
            return workflow[cod];
        } else {
            Log.e(LOG_TAG, "Descrição para Workflow não encontrada: " + cod);
            return "";
        }
    }

    public static int getWorkflowCode(String description) {
        String[] workflow = context.getResources().getStringArray(R.array.workflow_entries);
        int cod = -1;
        for (int i = 0; i < workflow.length; i++) {
            if (workflow[i].equalsIgnoreCase(description)) {
                cod = i;
                break;
            }
        }
        if (cod < 0) {
            Log.e(LOG_TAG, "Código para Workflow não encontrado: " + description);
        }
        return cod;
    }

    public static String formatDate(Date date) {
        return DateFormat.getDateFormat(context).format(date);
    }

    public static String formatDateSQL(Date date) {
        return dfmSQL.format(date);
    }

    public static Date parseDate(String date) throws ParseException {
        return DateFormat.getDateFormat(context).parse(date);
    }

    public static Date parseDateSQL(String date) throws ParseException {
        return dfmSQL.parse(date);
    }

    public static void setContext(Context appContext) {
        context = appContext;
    }
}
