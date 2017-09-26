package br.com.tibomenga.artigospub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data.Artigo;
import br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data.DataUtil;

/**
 * A placeholder fragment containing a simple view.
 */
public class ArtigosActivityFragment extends Fragment {
    private ArtigosAdapter artigosArrayAdapter = null;

    public ArtigosActivityFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        artigosArrayAdapter.clear();
        artigosArrayAdapter.addAll(DataUtil.createFakeListArtigos(10));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_artigos, container, false);
        artigosArrayAdapter = new ArtigosAdapter(getActivity(), new ArrayList<Artigo>());
        final ListView listView = (ListView) rootView.findViewById(R.id.listview_artigos);
        listView.setAdapter(artigosArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Artigo item = (Artigo) artigosArrayAdapter.getItem(i);
//                Toast toast = Toast.makeText(getContext(), item.getNome(), Toast.LENGTH_SHORT);
//                toast.show();
                Intent intent = new Intent(getActivity(), ArtigoEditActivity.class)
                        .putExtra(Intent.ACTION_ATTACH_DATA, item);
                startActivity(intent);
            }
        });

        return rootView;
    }

    public class ArtigosAdapter extends ArrayAdapter<Artigo> {

        public ArtigosAdapter(@NonNull Context context, @NonNull List<Artigo> objects) {
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Artigo artigo = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_artigo, parent, false);
            }
            ((TextView) convertView.findViewById(R.id.tv_nome_artigo)).setText(artigo.getNome());
            ((TextView) convertView.findViewById(R.id.tv_destino_publicacao)).setText(artigo.getDestinoPublicacao());
            ((TextView) convertView.findViewById(R.id.tv_data_inicio)).setText(getString(R.string.inicio) + ": " + DataUtil.formatDate(artigo.getDataInicial()));
            ((TextView) convertView.findViewById(R.id.tv_status_workflow)).setText(getString(R.string.status) + ": " + artigo.getStatusWorkflow());
            ((TextView) convertView.findViewById(R.id.tv_versao_atual)).setText(getString(R.string.versao_documento) + ": " + artigo.getVersaoAtual());
            //((TextView) convertView.findViewById(R.id.tv_)).setText(artigo.get);
            return convertView;
        }
    }
}
