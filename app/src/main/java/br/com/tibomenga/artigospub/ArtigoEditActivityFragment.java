package br.com.tibomenga.artigospub;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;

import br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data.Artigo;
import br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data.DataUtil;

/**
 * A placeholder fragment containing a simple view.
 */
public class ArtigoEditActivityFragment extends Fragment {
    private View rootView;

    public ArtigoEditActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_artigo_edit, container, false);
        ((ArtigoEditActivity) getActivity()).setFragment(this);
        Artigo artigo = (Artigo) getActivity().getIntent().getSerializableExtra(Intent.ACTION_ATTACH_DATA);
        updateForm(artigo);
        return rootView;
    }

    private void updateForm(Artigo artigo) {
        ((EditText) rootView.findViewById(R.id.et_nome_artigo)).setText(artigo.getNome());
        ((AutoCompleteTextView) rootView.findViewById(R.id.et_destino_publicacao)).setText(artigo.getDestinoPublicacao());
        ((EditText) rootView.findViewById(R.id.et_data_inicial)).setText(DataUtil.formatDate(artigo.getDataInicial()));
        ((Spinner) rootView.findViewById(R.id.sp_status_workflow)).setSelection(0);
        ((EditText) rootView.findViewById(R.id.et_versao_atual)).setText(artigo.getVersaoAtual());
    }

    public View getRootView() {
        return rootView;
    }

    public void readValues() {
        String nome = ((EditText) getRootView().findViewById(R.id.et_nome_artigo)).getText().toString();
        Snackbar.make(getRootView(), "Save: " + nome, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
