package br.com.tibomenga.artigospub.data;

import android.content.Context;
import android.util.Log;

import java.util.LinkedList;

/**
 * Created by menga on 26/09/17.
 */

public class UndoManager {
    private static LinkedList<UndoStateChangeListener> listeners = new LinkedList<>();
    private static LinkedList<UndoAction> lstLastActions = new LinkedList<>();
    private static String LOG_TAG = UndoManager.class.getSimpleName();
    public static final int DELETE_ARTIGO = 1;

    public static void addListener(UndoStateChangeListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public static void notifyListeners() {
        for (UndoStateChangeListener listener : listeners) {
            listener.undoStateChanged();
        }
    }

    public static void push(UndoAction act) {
        lstLastActions.add(act);
    }

    public static void undo(Context context) {
        if (lstLastActions.size() > 0) {
            UndoAction act = lstLastActions.getFirst();
            switch (act.getAction()) {
                case DELETE_ARTIGO:
                    ArtigoController controller = new ArtigoController(context);
                    controller.insert((Artigo) act.getData(), true);
                    break;
                default:
                    Log.e(LOG_TAG, "Undo desconhecido: " + act.getAction());
                    break;
            }
        }
    }

    public interface UndoStateChangeListener {
        void undoStateChanged();
    }

    public static class UndoAction {
        private Object data;
        private int action;

        public UndoAction(Object data, int action) {
            this.data = data;
            this.action = action;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public int getAction() {
            return action;
        }

        public void setAction(int action) {
            this.action = action;
        }
    }
}
