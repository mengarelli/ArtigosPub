package br.com.tibomenga.artigospub;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import br.com.tibomenga.artigospub.data.Artigo;
import br.com.tibomenga.artigospub.data.ArtigoController;
import br.com.tibomenga.artigospub.data.DataUtil;
import br.com.tibomenga.artigospub.data.ISearchable;

/**
 * A placeholder fragment containing a simple view.
 */
public class ArtigosActivityFragment extends Fragment implements ISearchable {
    private ArtigosAdapter artigosArrayAdapter = null;
    private LinkedList<Artigo> lstArtigosShow = new LinkedList<>();
    private ListView listView;
    private LinkedList<Artigo> lstArtigosContent = new LinkedList<>();
    private String searchString = null;
    private ArtigoController controller;
    private View frameArtigos = null;
    Snackbar snackbar = null;

    public ArtigosActivityFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        controller = new ArtigoController(getContext());
//        artigosArrayAdapter.clear();
//        artigosArrayAdapter.addAll(DataUtil.createFakeListArtigos(10));
        if (lstArtigosContent.size() == 0) {
            //lstArtigosContent.addAll(DataUtil.createFakeListArtigos(10));
            lstArtigosContent.addAll(controller.list());
            int count = 0;
            if (DataUtil.isAvisos()) {
                try {
                    Calendar cal1 = Calendar.getInstance();
                    cal1.setTime(DataUtil.parseDateSQL(DataUtil.formatDateSQL(cal1.getTime())));
                    Calendar cal2 = Calendar.getInstance();
                    cal2.setTime(cal1.getTime());
                    cal2.add(Calendar.DATE, 7);
                    Calendar calLim = Calendar.getInstance();
                    for (Artigo art : lstArtigosContent) {
                        calLim.setTime(art.getDataLimite());
                        if (!calLim.before(cal1) & !calLim.after(cal2)) {
                            count++;
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (count > 0) {
                    new AlertDialog.Builder(getActivity())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle(getString(R.string.artigos_data_limite))
                            .setMessage(count + " " + getString(R.string.artigos_no_limite_7_dias))
                            .setPositiveButton(getString(R.string.ok), null)
                            .show();

                }
                DataUtil.setAvisos(false);
            }
            refresh();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_artigos, container, false);
        frameArtigos = (View) rootView.findViewById(R.id.frame_artigos);
        artigosArrayAdapter = new ArtigosAdapter(getActivity(), lstArtigosShow);
        listView = (ListView) rootView.findViewById(R.id.listview_artigos);
        listView.setAdapter(artigosArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Artigo item = (Artigo) artigosArrayAdapter.getItem(i);
                Intent intent = new Intent(getActivity(), ArtigoEditActivity.class)
                        .putExtra(Intent.ACTION_ATTACH_DATA, item);
                //startActivity(intent);
                startActivityForResult(intent, 0);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.btn_insert);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Artigo item = new Artigo();
                item.setStatusWorkflow(0);
                item.setVersaoAtual("");
                Intent intent = new Intent(getActivity(), ArtigoEditActivity.class)
                        .putExtra(Intent.ACTION_ATTACH_DATA, item);
                //startActivity(intent);
                startActivityForResult(intent, 0);

            }
        });

        return rootView;
    }

    public void refresh() {
        lstArtigosShow.clear();
        if (searchString != null) {
            String string = searchString.toLowerCase();
            TreeSet<Artigo> set = new TreeSet<>(new ArtigoComparator());
            for (Artigo artigo : lstArtigosContent) {
                if (artigo.toStringSearch().toLowerCase().contains(string)) {
                    set.add(artigo);
                }
            }
            lstArtigosShow.addAll(set);
        } else {
            lstArtigosShow.addAll(lstArtigosContent);
        }
        if (artigosArrayAdapter != null) {
            artigosArrayAdapter.notifyDataSetChanged();
            showSearchInfo();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_FIRST_USER) {
            // delete
            Artigo artigo = (Artigo) data.getSerializableExtra(Intent.ACTION_ATTACH_DATA);
            int pos = lstArtigosContent.indexOf(artigo);
            if (pos >= 0) {
                controller.delete(artigo);
                lstArtigosContent.remove(artigo);
            }
            Toast toast = Toast.makeText(getContext(), getString(R.string.delete_sucess), Toast.LENGTH_SHORT);
            toast.show();
            refresh();
        } else if (resultCode == Activity.RESULT_OK) {
            // insert or update
            Artigo artigo = (Artigo) data.getSerializableExtra(Intent.ACTION_ATTACH_DATA);
            int pos = lstArtigosContent.indexOf(artigo);
            if (pos >= 0) {
                controller.update(artigo);
                lstArtigosContent.set(pos, artigo);
            } else {
                controller.insert(artigo);
                lstArtigosContent.add(artigo);
                pos = lstArtigosContent.size() - 1;
            }
            Toast toast = Toast.makeText(getContext(), getString(R.string.save_sucess), Toast.LENGTH_SHORT);
            toast.show();
            refresh();
            pos = lstArtigosShow.indexOf(artigo);
            if (pos >= 0) {
                listView.setSelection(pos);
            }
        }
    }

    public void showSearchInfo() {
        if (listView != null) {
            if (snackbar != null) {
                snackbar.dismiss();
            }
            if ((searchString != null) && (!searchString.isEmpty())) {
                snackbar = Snackbar.make(frameArtigos, getString(R.string.filter) + ": " + searchString, Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction(R.string.clear_filter, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        search(null);
                    }
                });
                snackbar.show();
            }
        }
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
            ((TextView) convertView.findViewById(R.id.tv_nome_artigo)).setText(artigo.getNome() + " (" + artigo.getAutor() + ")");
            ((TextView) convertView.findViewById(R.id.tv_destino_publicacao)).setText(artigo.getDestinoPublicacao());
            ((TextView) convertView.findViewById(R.id.tv_data_limite)).setText(getString(R.string.data_limite) + ": " + DataUtil.formatDate(artigo.getDataLimite()));
            ((TextView) convertView.findViewById(R.id.tv_status_workflow)).setText(getString(R.string.status) + ": " + DataUtil.getWorkflowDescription(artigo.getStatusWorkflow()));
            ((TextView) convertView.findViewById(R.id.tv_versao_atual)).setText(getString(R.string.versao_documento) + ": " + artigo.getVersaoAtual());
            //((TextView) convertView.findViewById(R.id.tv_)).setText(artigo.get);
            return convertView;
        }
    }

    @Override
    public void search(String string) {
        searchString = string;
        refresh();
    }

    private static class ArtigoComparator implements Comparator<Artigo> {
        @Override
        public int compare(Artigo a1, Artigo a2) {
            if (a1 == null) {
                if (a2 == null) {
                    return 0;
                } else {
                    return -1;
                }
            } else if (a2 == null) {
                return 1;
            } else {
                return (int) (a1.getDataInicial().getTime() - a2.getDataInicial().getTime());
            }
        }
    }
}
