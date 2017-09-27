package br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by menga on 26/09/17.
 */

public class ArtigoContract {
    private ArtigoContract() { }

    public static class ArtigoEntry implements BaseColumns {
        public static final String TABLE_NAME = "artigos";
        public static final String _ID = "id_artigo";
        public static final String COLUMN_NAME_NOME = "nome";
        public static final String COLUMN_NAME_DESTINO_PUBLICACAO = "destino_publicacao";
        public static final String COLUMN_NAME_AUTOR = "autor";
        public static final String COLUMN_NAME_DATA_INICIAL = "data_inicial";
        public static final String COLUMN_NAME_DATA_LIMITE = "data_limite";
        public static final String COLUMN_NAME_COMENTARIOS = "comentarios";
        public static final String COLUMN_NAME_STATUS_WORKFLOW = "status_workflow";
        public static final String COLUMN_NAME_VERSAO_ATUAL = "versao_atual";

        public static final String[] TODOS = {_ID, COLUMN_NAME_NOME, COLUMN_NAME_DESTINO_PUBLICACAO, COLUMN_NAME_AUTOR,
                COLUMN_NAME_DATA_INICIAL, COLUMN_NAME_DATA_LIMITE, COLUMN_NAME_COMENTARIOS,
                COLUMN_NAME_STATUS_WORKFLOW, COLUMN_NAME_VERSAO_ATUAL};

    }

    public static class WorkflowEntry implements BaseColumns {
        public static final String TABLE_NAME = "workflow";
        public static final String _ID = "id_workflow";
        public static final String COLUMN_NAME_ID_ARTIGO = "id_artigo";
        public static final String COLUMN_NAME_STATUS_WORKFLOW = "status_workflow";
        public static final String COLUMN_NAME_DATA_STATUS = "data_status";
        public static final String COLUMN_NAME_VERSAO_ATUAL = "versao_atual";

        public static final String[] TODOS = {_ID, COLUMN_NAME_ID_ARTIGO, COLUMN_NAME_STATUS_WORKFLOW,
                COLUMN_NAME_DATA_STATUS, COLUMN_NAME_VERSAO_ATUAL};

    }

    // Declarations
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ARTIGOS =
            "CREATE TABLE " + ArtigoEntry.TABLE_NAME + " (" +
                    ArtigoEntry._ID + " INTEGER PRIMARY KEY," +
                    ArtigoEntry.COLUMN_NAME_NOME + TEXT_TYPE + COMMA_SEP +
                    ArtigoEntry.COLUMN_NAME_DESTINO_PUBLICACAO + TEXT_TYPE + COMMA_SEP +
                    ArtigoEntry.COLUMN_NAME_AUTOR + TEXT_TYPE + COMMA_SEP +
                    ArtigoEntry.COLUMN_NAME_DATA_INICIAL + TEXT_TYPE + COMMA_SEP +
                    ArtigoEntry.COLUMN_NAME_DATA_LIMITE + TEXT_TYPE + COMMA_SEP +
                    ArtigoEntry.COLUMN_NAME_COMENTARIOS + TEXT_TYPE + COMMA_SEP +
                    ArtigoEntry.COLUMN_NAME_STATUS_WORKFLOW + " INTEGER" + COMMA_SEP +
                    ArtigoEntry.COLUMN_NAME_VERSAO_ATUAL + TEXT_TYPE + " )";

    private static final String SQL_CREATE_WORKFLOW =
            "CREATE TABLE " + WorkflowEntry.TABLE_NAME + " (" +
                    WorkflowEntry._ID + " INTEGER PRIMARY KEY," +
                    WorkflowEntry.COLUMN_NAME_ID_ARTIGO + " INTEGER" + COMMA_SEP +
                    WorkflowEntry.COLUMN_NAME_STATUS_WORKFLOW + " INTEGER" + COMMA_SEP +
                    WorkflowEntry.COLUMN_NAME_DATA_STATUS + TEXT_TYPE + COMMA_SEP +
                    WorkflowEntry.COLUMN_NAME_VERSAO_ATUAL + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ARTIGOS =
            "DROP TABLE IF EXISTS " + ArtigoEntry.TABLE_NAME;

    private static final String SQL_DELETE_WORKFLOW =
            "DROP TABLE IF EXISTS " + WorkflowEntry.TABLE_NAME;

    public static class ArtigoDbHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 2;
        public static final String DATABASE_NAME = "artigos.db";

        public ArtigoDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(SQL_CREATE_ARTIGOS);
            sqLiteDatabase.execSQL(SQL_CREATE_WORKFLOW);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            sqLiteDatabase.execSQL(SQL_DELETE_ARTIGOS);
            sqLiteDatabase.execSQL(SQL_DELETE_WORKFLOW);
            onCreate(sqLiteDatabase);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }

}
