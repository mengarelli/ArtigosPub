package br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import static br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data.ArtigoContract.ArtigoEntry.COLUMN_NAME_AUTOR;
import static br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data.ArtigoContract.ArtigoEntry.COLUMN_NAME_COMENTARIOS;
import static br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data.ArtigoContract.ArtigoEntry.COLUMN_NAME_DATA_INICIAL;
import static br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data.ArtigoContract.ArtigoEntry.COLUMN_NAME_DATA_LIMITE;
import static br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data.ArtigoContract.ArtigoEntry.COLUMN_NAME_DESTINO_PUBLICACAO;
import static br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data.ArtigoContract.ArtigoEntry.COLUMN_NAME_NOME;
import static br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data.ArtigoContract.ArtigoEntry.COLUMN_NAME_STATUS_WORKFLOW;
import static br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data.ArtigoContract.ArtigoEntry.COLUMN_NAME_VERSAO_ATUAL;
import static br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data.ArtigoContract.ArtigoEntry.TABLE_NAME;

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

    // Declarations
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    ArtigoEntry._ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_NOME + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_DESTINO_PUBLICACAO + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_AUTOR + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_DATA_INICIAL + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_DATA_LIMITE + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_COMENTARIOS + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_STATUS_WORKFLOW + " INTEGER" + COMMA_SEP +
                    COLUMN_NAME_VERSAO_ATUAL + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static class ArtigoDbHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "artigos.db";

        public ArtigoDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
            onCreate(sqLiteDatabase);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }

}
