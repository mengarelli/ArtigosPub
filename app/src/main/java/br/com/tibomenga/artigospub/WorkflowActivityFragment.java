package br.com.tibomenga.artigospub;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data.DataUtil;
import br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data.ISearchable;
import br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data.Workflow;
import br.com.tibomenga.artigospub.br.com.tibomenga.artigospub.data.WorkflowController;

/**
 * A placeholder fragment containing a simple view.
 */
public class WorkflowActivityFragment extends Fragment implements ISearchable {
    private ItemAdapter itemsArrayAdapter = null;
    private LinkedList<Workflow> lstItemsShow = new LinkedList<>();
    private ListView listView;
    private LinkedList<Workflow> lstItemsContent = new LinkedList<>();
    private String searchString = null;
    private WorkflowController controller;
    private View frameArtigos = null;
    Snackbar snackbar = null;

    public WorkflowActivityFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        controller = new WorkflowController(getContext());
        if (lstItemsContent.size() == 0) {
            lstItemsContent.addAll(controller.list());
            refresh();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_workflow, container, false);
        frameArtigos = rootView.findViewById(R.id.frame_workflow);
        itemsArrayAdapter = new ItemAdapter(getActivity(), lstItemsShow);
        listView = rootView.findViewById(R.id.listview_workflow);
        listView.setAdapter(itemsArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Workflow item = itemsArrayAdapter.getItem(i);
            }
        });
        return rootView;
    }

    public void refresh() {
        lstItemsShow.clear();
        if (searchString != null) {
            String string = searchString.toLowerCase();
            for (Workflow workflow : lstItemsContent) {
                if (workflow.toStringSearch().toLowerCase().contains(string)) {
                    lstItemsShow.add(workflow);
                }
            }
        } else {
            lstItemsShow.addAll(lstItemsContent);
        }
        if (itemsArrayAdapter != null) {
            itemsArrayAdapter.notifyDataSetChanged();
            showSearchInfo();
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

    public class ItemAdapter extends ArrayAdapter<Workflow> {

        public ItemAdapter(@NonNull Context context, @NonNull List<Workflow> objects) {
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Workflow item = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_workflow, parent, false);
            }
            ((TextView) convertView.findViewById(R.id.tv_nome_artigo)).setText(item.getArtigo().getNome() + " (" + item.getArtigo().getAutor() + ")");
            ((TextView) convertView.findViewById(R.id.tv_data_status)).setText(getString(R.string.data_status) + ": " + DataUtil.formatDateTime(item.getDataStatus()));
            ((TextView) convertView.findViewById(R.id.tv_status_workflow)).setText(getString(R.string.status) + ": " + DataUtil.getWorkflowDescription(item.getStatusWorkflow()));
            ((TextView) convertView.findViewById(R.id.tv_versao_atual)).setText(getString(R.string.versao_documento) + ": " + item.getVersaoAtual());
            //((TextView) convertView.findViewById(R.id.tv_)).setText(artigo.get);
            return convertView;
        }
    }

    @Override
    public void search(String string) {
        searchString = string;
        refresh();
    }
}
