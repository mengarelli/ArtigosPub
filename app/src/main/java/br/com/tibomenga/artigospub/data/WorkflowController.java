package br.com.tibomenga.artigospub.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static br.com.tibomenga.artigospub.data.ArtigoContract.WorkflowEntry.COLUMN_NAME_DATA_STATUS;
import static br.com.tibomenga.artigospub.data.ArtigoContract.WorkflowEntry.COLUMN_NAME_ID_ARTIGO;
import static br.com.tibomenga.artigospub.data.ArtigoContract.WorkflowEntry.COLUMN_NAME_STATUS_WORKFLOW;
import static br.com.tibomenga.artigospub.data.ArtigoContract.WorkflowEntry.COLUMN_NAME_VERSAO_ATUAL;
import static br.com.tibomenga.artigospub.data.ArtigoContract.WorkflowEntry._ID;

/**
 * Created by menga on 26/09/17.
 */

public class WorkflowController {
    private static String LOG_TAG = WorkflowController.class.getSimpleName();
    public static final int RESULT_ERROR = -1;
    public static final int RESULT_OK = 0;
    private SQLiteDatabase db;
    private ArtigoContract.ArtigoDbHelper dbHelper;

    public WorkflowController(Context context) {
        dbHelper = new ArtigoContract.ArtigoDbHelper(context);
    }

    public int insert(Workflow workflow) {
        return insert(workflow, false);
    }

    public int insert(Workflow workflow, boolean withId) {
        db = dbHelper.getWritableDatabase();
        int result = insert(workflow, withId, db);
        db.close();
        return result;
    }

    public static int insert(Workflow workflow, boolean withId, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        if (withId) {
            values.put(_ID, workflow.getId());
        }
        values.put(COLUMN_NAME_ID_ARTIGO, workflow.getIdArtigo());
        values.put(COLUMN_NAME_DATA_STATUS, DataUtil.formatDateTimeSQL(workflow.getDataStatus()));
        values.put(COLUMN_NAME_VERSAO_ATUAL, workflow.getVersaoAtual());
        values.put(COLUMN_NAME_STATUS_WORKFLOW, workflow.getStatusWorkflow());
        long result = db.insert(ArtigoContract.WorkflowEntry.TABLE_NAME, null, values);
        if (result < 0) {
            return RESULT_ERROR;
        } else {
            workflow.setId(result);
            return RESULT_OK;
        }
    }

    public int update(Workflow workflow) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_ID_ARTIGO, workflow.getIdArtigo());
        values.put(COLUMN_NAME_DATA_STATUS, DataUtil.formatDateSQL(workflow.getDataStatus()));
        values.put(COLUMN_NAME_VERSAO_ATUAL, workflow.getVersaoAtual());
        values.put(COLUMN_NAME_STATUS_WORKFLOW, workflow.getStatusWorkflow());
        String where = ArtigoContract.WorkflowEntry._ID + "=" + workflow.getId();
        int result = db.update(ArtigoContract.WorkflowEntry.TABLE_NAME, values, where, null);
        db.close();
        if (result < 0) {
            return RESULT_ERROR;
        } else {
            return result;
        }
    }

    public int delete(Workflow workflow) {
        db = dbHelper.getWritableDatabase();
        String where = ArtigoContract.WorkflowEntry._ID + "=" + workflow.getId();
        int result = db.delete(ArtigoContract.WorkflowEntry.TABLE_NAME, where, null);
        db.close();
        if (result < 0) {
            return RESULT_ERROR;
        } else {
            return result;
        }
    }

    public List<Workflow> list() {
        db = dbHelper.getReadableDatabase();
        String sql = "SELECT t1.*, t2." + ArtigoContract.ArtigoEntry.COLUMN_NAME_NOME + ", t2." + ArtigoContract.ArtigoEntry.COLUMN_NAME_AUTOR +
                " FROM " + ArtigoContract.WorkflowEntry.TABLE_NAME + " t1" +
                " INNER JOIN " + ArtigoContract.ArtigoEntry.TABLE_NAME + " t2" +
                " ON t2." + ArtigoContract.ArtigoEntry._ID + " = t1." + ArtigoContract.WorkflowEntry.COLUMN_NAME_ID_ARTIGO +
                " ORDER BY " + ArtigoContract.WorkflowEntry.COLUMN_NAME_DATA_STATUS + " DESC";
        Log.i(LOG_TAG, "SQL: " + sql);
        Cursor cursor = db.rawQuery(sql, null);
        //Cursor cursor = db.query(ArtigoContract.WorkflowEntry.TABLE_NAME, null, null, null, null, null, null);
        ArrayList<Workflow> lst = new ArrayList<>();
        if (cursor != null) {
            cursor.moveToFirst();
            lst = new ArrayList<>(cursor.getCount());
            for (int i = 0; i < cursor.getCount(); i++) {
                int col = 0;
                Workflow workflow = new Workflow();
                workflow.setId(cursor.getLong(col++));
                workflow.setIdArtigo(cursor.getLong(col++));
                workflow.setStatusWorkflow(cursor.getInt(col++));
                try {
                    workflow.setDataStatus(DataUtil.parseDateTimeSQL(cursor.getString(col++)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                workflow.setVersaoAtual(cursor.getString(col++));
                workflow.setArtigo(new Artigo());
                workflow.getArtigo().setNome(cursor.getString(col++));
                workflow.getArtigo().setAutor(cursor.getString(col++));
                lst.add(workflow);
                cursor.moveToNext();
            }
        }
        db.close();
        return lst;
    }
}
