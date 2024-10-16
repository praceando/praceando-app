package com.firstclass.praceando.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firstclass.praceando.EventDetails.EventCreationBasicDatas;
import com.firstclass.praceando.Globals;
import com.firstclass.praceando.R;
import com.firstclass.praceando.entities.Event;
import com.firstclass.praceando.entities.Tag;
import com.firstclass.praceando.fragments.HeaderFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private final List<Object> eventList = new ArrayList<>();
    private final List<Tag> tagList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView otherEnventsTab, myEnventsTab;
    private FloatingActionButton addEvent;
    private LinearLayout tabs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        otherEnventsTab = view.findViewById(R.id.otherEnventsTab);
        myEnventsTab = view.findViewById(R.id.myEnventsTab);
        addEvent = view.findViewById(R.id.addEvent);
        tabs = view.findViewById(R.id.tabs);

        Globals globals = new Globals();
        int userRole = globals.getUserRole();

        if (userRole == 0) {
            tabs.setVisibility(View.GONE);
            addEvent.setVisibility(View.GONE);
        }

        // Initialize and commit child fragments
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.headerFragmentLayout, new HeaderFragment());
        fragmentTransaction.commit();

        addTagsInTheList();
        setupAutoCompleteTextView(view);

        recyclerView = view.findViewById(R.id.recyclerViewEvent);
        EventItemAdapter eventItemAdapter = new EventItemAdapter(eventList);
        recyclerView.setAdapter(eventItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        loadEvents(true);

        addEvent.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), EventCreationBasicDatas.class);
            startActivity(intent);
        });


        final AutoCompleteTextView autoCompleteTextView = view.findViewById(R.id.autoCompleteTextView);
        final TextInputLayout textInputLayout = view.findViewById(R.id.dropdownLayout);

        // Altera o ícone quando um item é selecionado
        autoCompleteTextView.setOnItemClickListener((parent, v, position, id) -> {
            textInputLayout.setEndIconDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_x));
        });

        // Configura o listener para o ícone "X"
        textInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (autoCompleteTextView.getText().toString().isEmpty()) {
                    autoCompleteTextView.showDropDown();
                } else {
                    autoCompleteTextView.setText("");
                    textInputLayout.setEndIconDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_dropdown_arrow));
                }
            }
        });

        return view;
    }

    private void setupAutoCompleteTextView(View view) {
        AutoCompleteTextView autoCompleteTextView = view.findViewById(R.id.autoCompleteTextView);
        TagItemAdapter adapter = new TagItemAdapter(getContext(), tagList);
        autoCompleteTextView.setAdapter(adapter);
    }

    private void updateTabColors(boolean isMyEvents) {
        if (isMyEvents) {
            myEnventsTab.setTextColor(getResources().getColor(R.color.rosaEscuraoClaro));
            otherEnventsTab.setTextColor(getResources().getColor(R.color.rosa));
        } else {
            otherEnventsTab.setTextColor(getResources().getColor(R.color.rosaEscuraoClaro));
            myEnventsTab.setTextColor(getResources().getColor(R.color.rosa));
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void addEventsInTheList(boolean isMyEvents) {

        eventList.clear();
        if(isMyEvents){
            eventList.add("Cada tonelada de papel reciclado economiza cerca de 17 árvores, faça sua parte!");

            eventList.add(new Event("9h - 13h", "Biblioteca Pública", "19/07 - 19/07", "Encontro de Leitores", "https://catracalivre.com.br/wp-content/uploads/2020/04/1028904-02-07-2016-dsc7443-910x544.jpg",
                    new Tag[]{
                            new Tag(16L, "Leitura"),
                            new Tag(17L, "Literatura"),
                            new Tag(18L, "Cultura")
                    }));

            eventList.add(new Event("20h - 02h", "Centro Histórico", "20/07 - 20/07", "Noite dos Museus", "https://www.blogvambora.com.br/wp-content/uploads/2012/05/39009754_5de61cb610_Hugo.jpg",
                    new Tag[]{
                            new Tag(19L, "Museus"),
                            new Tag(20L, "História"),
                            new Tag(21L, "Cultura")
                    }));
        } else {

            eventList.add("Cada tonelada de papel reciclado economiza cerca de 17 árvores, faça sua parte!");

            eventList.add(new Event("10h - 18h", "N. Sra. dos Prazeres", "15/07 - 15/07", "Feira de Artesanato", "https://blog.123milhas.com/wp-content/uploads/2022/08/feira-de-artesanato-ao-ar-livre-redes-outros-objetos-artesanais-conexao123.jpg",
                    new Tag[]{
                            new Tag(4L, "Artesanato"),
                            new Tag(5L, "Feira"),
                            new Tag(6L, "Cultura"),
                            new Tag(3L, "Popular")
                    }));

            eventList.add(new Event("8h - 12h", "Praia do Futuro", "16/07 - 16/07", "Corrida do Sol", "https://eccobolsas.com.br/wp-content/uploads/2023/05/Quais-sao-os-tipos-de-corrida-de-rua.png",
                    new Tag[]{
                            new Tag(7L, "Esporte"),
                            new Tag(8L, "Saúde"),
                            new Tag(9L, "Corrida")
                    }));

            eventList.add(new Event("14h - 22h", "Centro Cultural", "17/07 - 17/07", "Mostra de Cinema", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQIe1cF_ZkxAniXv0qqy2XX73u079G2BKXASA&s",
                    new Tag[]{
                            new Tag(10L, "Cinema"),
                            new Tag(11L, "Cultura"),
                            new Tag(12L, "Arte")
                    }));

            eventList.add(new Event("18h - 23h", "Estádio Municipal", "18/07 - 18/07", "Show de Rock", "https://midias.correio24horas.com.br/2023/04/13/rock-no-parque-da-cidade-em-salvador-1567377.jpg",
                    new Tag[]{
                            new Tag(13L, "Música"),
                            new Tag(14L, "Rock"),
                            new Tag(15L, "Entretenimento")
                    }));

            eventList.add(new Event("9h - 13h", "Biblioteca Pública", "19/07 - 19/07", "Encontro de Leitores", "https://catracalivre.com.br/wp-content/uploads/2020/04/1028904-02-07-2016-dsc7443-910x544.jpg",
                    new Tag[]{
                            new Tag(16L, "Leitura"),
                            new Tag(17L, "Literatura"),
                            new Tag(18L, "Cultura")
                    }));

            eventList.add(new Event("20h - 02h", "Centro Histórico", "20/07 - 20/07", "Noite dos Museus", "https://www.blogvambora.com.br/wp-content/uploads/2012/05/39009754_5de61cb610_Hugo.jpg",
                    new Tag[]{
                            new Tag(19L, "Museus"),
                            new Tag(20L, "História"),
                            new Tag(21L, "Cultura")
                    }));

        }
        Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
    }


    private void addTagsInTheList() {
        tagList.add(new Tag(1L, "artesanato"));
        tagList.add(new Tag(2L, "gastronomia"));
        tagList.add(new Tag(3L, "arte"));
        tagList.add(new Tag(4L, "esporte"));
        tagList.add(new Tag(5L, "saude"));
    }

    private void loadEvents(boolean isAppInitialized) {
        if (isAppInitialized) {
            addEventsInTheList(false);
            updateTabColors(false);
        }

        otherEnventsTab.setOnClickListener(v -> {
            addEventsInTheList(false);
            updateTabColors(false);
        });

        myEnventsTab.setOnClickListener(v -> {
            addEventsInTheList(true);
            updateTabColors(true);
        });
    }
}
