package br.com.tibomenga.artigospub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.ParseException;

import br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data.Artigo;
import br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data.DataUtil;

/**
 * A placeholder fragment containing a simple view.
 */
public class ArtigoEditActivityFragment extends Fragment {
    private static String LOG_TAG = ArtigoEditActivityFragment.class.getSimpleName();
    private View rootView;
    private Artigo artigo;

    public ArtigoEditActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_artigo_edit, container, false);
        ((ArtigoEditActivity) getActivity()).setFragment(this);
        artigo = (Artigo) getActivity().getIntent().getSerializableExtra(Intent.ACTION_ATTACH_DATA);
        Log.i(LOG_TAG, artigo.toString());
        updateForm();
        return rootView;
    }

    private void updateForm() {
        ((EditText) rootView.findViewById(R.id.et_nome_artigo)).setText(artigo.getNome());
        ((AutoCompleteTextView) rootView.findViewById(R.id.et_destino_publicacao)).setText(artigo.getDestinoPublicacao());
        ((EditText) rootView.findViewById(R.id.et_autor)).setText(artigo.getAutor());
        ((EditText) rootView.findViewById(R.id.et_data_inicial)).setText(DataUtil.formatDate(artigo.getDataInicial()));
        ((EditText) rootView.findViewById(R.id.et_data_limite)).setText(DataUtil.formatDate(artigo.getDataLimite()));
        Spinner sp = ((Spinner) rootView.findViewById(R.id.sp_status_workflow));
        if (artigo.getStatusWorkflow() < sp.getCount()) {
            sp.setSelection(artigo.getStatusWorkflow());
        } else {
            sp.setSelection(0);
        }
        ((EditText) rootView.findViewById(R.id.et_versao_atual)).setText(artigo.getVersaoAtual());
        ((EditText) rootView.findViewById(R.id.et_comentarios)).setText(artigo.getComentarios());
    }

    public View getRootView() {
        return rootView;
    }

    public void saveValues() {
        try {
            artigo.setNome(((EditText) getRootView().findViewById(R.id.et_nome_artigo)).getText().toString());
            artigo.setDestinoPublicacao(((EditText) getRootView().findViewById(R.id.et_destino_publicacao)).getText().toString());
            artigo.setAutor(((EditText) getRootView().findViewById(R.id.et_autor)).getText().toString());
            artigo.setDataInicial(DataUtil.parseDate(((EditText) getRootView().findViewById(R.id.et_data_inicial)).getText().toString()));
            artigo.setDataLimite(DataUtil.parseDate(((EditText) getRootView().findViewById(R.id.et_data_limite)).getText().toString()));
            artigo.setStatusWorkflow(DataUtil.getWorkflowCode(((Spinner) getRootView().findViewById(R.id.sp_status_workflow)).getSelectedItem().toString()));
            artigo.setVersaoAtual(((EditText) getRootView().findViewById(R.id.et_versao_atual)).getText().toString());
            artigo.setComentarios(((EditText) getRootView().findViewById(R.id.et_comentarios)).getText().toString());
            Intent resultIntent = new Intent();
            resultIntent.putExtra(Intent.ACTION_ATTACH_DATA, artigo);
            getActivity().setResult(Activity.RESULT_OK, resultIntent);
            getActivity().finish();
        } catch (ParseException e) {
            e.printStackTrace();
            Snackbar.make(getRootView(), getString(R.string.valor_invalido), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    public void deleteItem() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(Intent.ACTION_ATTACH_DATA, artigo);
        getActivity().setResult(Activity.RESULT_FIRST_USER, resultIntent);
        getActivity().finish();
    }
}
