package br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data.ArtigoContract.ArtigoEntry.COLUMN_NAME_AUTOR;
import static br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data.ArtigoContract.ArtigoEntry.COLUMN_NAME_COMENTARIOS;
import static br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data.ArtigoContract.ArtigoEntry.COLUMN_NAME_DATA_INICIAL;
import static br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data.ArtigoContract.ArtigoEntry.COLUMN_NAME_DATA_LIMITE;
import static br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data.ArtigoContract.ArtigoEntry.COLUMN_NAME_DESTINO_PUBLICACAO;
import static br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data.ArtigoContract.ArtigoEntry.COLUMN_NAME_NOME;
import static br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data.ArtigoContract.ArtigoEntry.COLUMN_NAME_STATUS_WORKFLOW;
import static br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data.ArtigoContract.ArtigoEntry.COLUMN_NAME_VERSAO_ATUAL;
import static br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data.ArtigoContract.ArtigoEntry._ID;

/**
 * Created by menga on 26/09/17.
 */

public class ArtigoController {
    public static final int RESULT_ERROR = -1;
    public static final int RESULT_OK = 0;
    private SQLiteDatabase db;
    private ArtigoContract.ArtigoDbHelper dbHelper;
    private Context context;

    public ArtigoController(Context context) {
        this.context = context;
        dbHelper = new ArtigoContract.ArtigoDbHelper(context);
    }

    public int insert(Artigo artigo) {
        return insert(artigo, false);
    }

    public int insert(Artigo artigo, boolean withId) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (withId) {
            values.put(_ID, artigo.getId());
        }
        values.put(COLUMN_NAME_AUTOR, artigo.getAutor());
        values.put(COLUMN_NAME_COMENTARIOS, artigo.getComentarios());
        values.put(COLUMN_NAME_DATA_INICIAL, DataUtil.formatDateSQL(artigo.getDataInicial()));
        values.put(COLUMN_NAME_DATA_LIMITE, DataUtil.formatDateSQL(artigo.getDataLimite()));
        values.put(COLUMN_NAME_DESTINO_PUBLICACAO, artigo.getDestinoPublicacao());
        values.put(COLUMN_NAME_NOME, artigo.getNome());
        values.put(COLUMN_NAME_STATUS_WORKFLOW, artigo.getStatusWorkflow());
        values.put(COLUMN_NAME_VERSAO_ATUAL, artigo.getVersaoAtual());
        long result = RESULT_ERROR;
        db.beginTransaction();
        try {
            result = db.insert(ArtigoContract.ArtigoEntry.TABLE_NAME, null, values);
            artigo.setId(result);
            if (artigo.isVersaoWorkflowChanged()) {
                Workflow workflow = new Workflow();
                workflow.setIdArtigo(artigo.getId());
                workflow.setDataStatus(Calendar.getInstance().getTime());
                workflow.setVersaoAtual(artigo.getVersaoAtual());
                workflow.setStatusWorkflow(artigo.getStatusWorkflow());
                WorkflowController.insert(workflow, false, db);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        db.close();
        if (result < 0) {
            return RESULT_ERROR;
        } else {
            artigo.setId(result);
            return RESULT_OK;
        }
    }

    public int update(Artigo artigo) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_AUTOR, artigo.getAutor());
        values.put(COLUMN_NAME_COMENTARIOS, artigo.getComentarios());
        values.put(COLUMN_NAME_DATA_INICIAL, DataUtil.formatDateSQL(artigo.getDataInicial()));
        values.put(COLUMN_NAME_DATA_LIMITE, DataUtil.formatDateSQL(artigo.getDataLimite()));
        values.put(COLUMN_NAME_DESTINO_PUBLICACAO, artigo.getDestinoPublicacao());
        values.put(COLUMN_NAME_NOME, artigo.getNome());
        values.put(COLUMN_NAME_STATUS_WORKFLOW, artigo.getStatusWorkflow());
        values.put(COLUMN_NAME_VERSAO_ATUAL, artigo.getVersaoAtual());
        String where = ArtigoContract.ArtigoEntry._ID + "=" + artigo.getId();
        int result = RESULT_ERROR;
        db.beginTransaction();
        try {
            result = db.update(ArtigoContract.ArtigoEntry.TABLE_NAME, values, where, null);
            if (artigo.isVersaoWorkflowChanged()) {
                Workflow workflow = new Workflow();
                workflow.setIdArtigo(artigo.getId());
                workflow.setDataStatus(Calendar.getInstance().getTime());
                workflow.setVersaoAtual(artigo.getVersaoAtual());
                workflow.setStatusWorkflow(artigo.getStatusWorkflow());
                WorkflowController.insert(workflow, false, db);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        db.close();
        if (result < 0) {
            return RESULT_ERROR;
        } else {
            return result;
        }
    }

    public int delete(Artigo artigo) {
        db = dbHelper.getWritableDatabase();
        int result = RESULT_ERROR;
        db.beginTransaction();
        try {
            String where = ArtigoContract.ArtigoEntry._ID + "=" + artigo.getId();
            result = db.delete(ArtigoContract.ArtigoEntry.TABLE_NAME, where, null);
            where = ArtigoContract.WorkflowEntry.COLUMN_NAME_ID_ARTIGO + "=" + artigo.getId();
            result *= db.delete(ArtigoContract.WorkflowEntry.TABLE_NAME, where, null);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        db.close();
        if (result < 0) {
            return RESULT_ERROR;
        } else {
            return result;
        }
    }

    public String[] listDestinosPublicacao() {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT " + ArtigoContract.ArtigoEntry.COLUMN_NAME_DESTINO_PUBLICACAO +
                " FROM " + ArtigoContract.ArtigoEntry.TABLE_NAME, null);
        if (cursor != null) {
            cursor.moveToFirst();
            ArrayList<String> lst = new ArrayList<>(cursor.getCount());
            for (int i = 0; i < cursor.getCount(); i++) {
                lst.add(cursor.getString(0));
                cursor.moveToNext();
            }
            return lst.toArray(new String[] {});
        } else {
            return new String[] {};
        }
    }

    public List<Artigo> list() {
        String[] fields = ArtigoContract.ArtigoEntry.TODOS;
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(ArtigoContract.ArtigoEntry.TABLE_NAME, fields, null, null, null, null, null);
        ArrayList<Artigo> lst = new ArrayList<>();
        if (cursor != null) {
            cursor.moveToFirst();
            lst = new ArrayList<>(cursor.getCount());
            for (int i = 0; i < cursor.getCount(); i++) {
                Artigo artigo = new Artigo();
                artigo.setId(cursor.getLong(0));
                artigo.setNome(cursor.getString(1));
                artigo.setDestinoPublicacao(cursor.getString(2));
                artigo.setAutor(cursor.getString(3));
                try {
                    artigo.setDataInicial(DataUtil.parseDateSQL(cursor.getString(4)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    artigo.setDataLimite(DataUtil.parseDateSQL(cursor.getString(5)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                artigo.setComentarios(cursor.getString(6));
                artigo.setStatusWorkflow(cursor.getInt(7));
                artigo.setVersaoAtual(cursor.getString(8));
                lst.add(artigo);
                cursor.moveToNext();
            }
        }
        db.close();
        return lst;
    }
}
