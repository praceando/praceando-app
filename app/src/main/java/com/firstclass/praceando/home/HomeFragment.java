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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firstclass.praceando.API.Flask.EventsIdsCallback;
import com.firstclass.praceando.API.Flask.EventsIdsResponse;
import com.firstclass.praceando.API.Flask.FlaskAPI;
import com.firstclass.praceando.API.postgresql.PostgresqlAPI;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.EventsCallback;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.FraseSustentavelCallback;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.TagsCallback;
import com.firstclass.praceando.API.postgresql.callbackInterfaces.UsuarioConsumidorCallback;
import com.firstclass.praceando.API.postgresql.entities.EventoFeed;
import com.firstclass.praceando.API.postgresql.entities.EventoReadBody;
import com.firstclass.praceando.API.postgresql.entities.FraseSustentavel;
import com.firstclass.praceando.API.postgresql.entities.UsuarioConsumidor;
import com.firstclass.praceando.EventDetails.EventCreationBasicDatas;
import com.firstclass.praceando.Globals;
import com.firstclass.praceando.R;
import com.firstclass.praceando.entities.Gender;
import com.firstclass.praceando.entities.Tag;
import com.firstclass.praceando.firebase.database.Database;
import com.firstclass.praceando.fragments.HeaderFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.microedition.khronos.opengles.GL;

public class HomeFragment extends Fragment {

    private final List<Object> eventList = new ArrayList<>();
    private final List<Tag> tagList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView otherEnventsTab, myEnventsTab, nothingFoundText;
    private FloatingActionButton addEvent;
    private LinearLayout tabs;
    private String fraseSustentavel;
    private PostgresqlAPI postgresqlAPI = new PostgresqlAPI();
    private Globals globals;
    private Tag  selectedTag;
    private boolean isMyEvents = false;
    private TextInputLayout textInputLayout;
    private ProgressBar progressBar;
    private FlaskAPI flaskAPI = new FlaskAPI();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        globals = (Globals) requireActivity().getApplication();
        loadFraseSustevel();

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        globals = (Globals) requireActivity().getApplication();
        otherEnventsTab = view.findViewById(R.id.otherEnventsTab);
        myEnventsTab = view.findViewById(R.id.myEnventsTab);
        addEvent = view.findViewById(R.id.addEvent);
        tabs = view.findViewById(R.id.tabs);
        nothingFoundText = view.findViewById(R.id.nothingFoundText);
        progressBar = view.findViewById(R.id.progressBar);

        int userRole = globals.getUserRole();

        if (userRole == 1) {
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

        addEvent.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), EventCreationBasicDatas.class);
            startActivity(intent);
        });

        final AutoCompleteTextView autoCompleteTextView = view.findViewById(R.id.autoCompleteTextView);
        textInputLayout = view.findViewById(R.id.dropdownLayout);

        autoCompleteTextView.setOnItemClickListener((parent, v, position, id) -> {
            selectedTag = tagList.get(position);
            textInputLayout.setEndIconDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_x));
            loadEvents();
        });

        otherEnventsTab.setOnClickListener(v -> {
            isMyEvents = false;
            addEventsInTheList(isMyEvents);
            updateTabColors(isMyEvents);
            textInputLayout.setVisibility(View.VISIBLE);
        });

        myEnventsTab.setOnClickListener(v -> {
            isMyEvents = true;
            addEventsInTheList(isMyEvents);
            updateTabColors(isMyEvents);
            textInputLayout.setVisibility(View.GONE);
        });

        textInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (autoCompleteTextView.getText().toString().isEmpty()) {
                    autoCompleteTextView.showDropDown();
                } else {
                    autoCompleteTextView.setText("");
                    selectedTag = null;
                    textInputLayout.setEndIconDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_dropdown_arrow));
                    loadEvents();
                }
            }
        });

        return view;
    }

    private void setupAutoCompleteTextView(View view) {
        eventList.clear();
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
        eventList.add(fraseSustentavel);
        progressBar.setVisibility(View.VISIBLE);
        nothingFoundText.setVisibility(View.INVISIBLE);

        if (isMyEvents) {

            postgresqlAPI.getEventsByUserId(globals.getId(), new EventsCallback() {
                @Override
                public void onSuccess(List<EventoFeed> events) {
                    nothingFoundText.setVisibility(View.INVISIBLE);
                    if(events.isEmpty()) {
                        nothingFoundText.setText("Você não tem nenhum evento, poste o seu primeiro! \uD83D\uDE09");
                        nothingFoundText.setVisibility(View.VISIBLE);
                    }

                    eventList.addAll(events);
                    Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onError(String errorMessage) {
                    Log.e("API", errorMessage);
                    nothingFoundText.setText("Você não tem nenhum evento, poste o seu primeiro! \uD83D\uDE09");
                    nothingFoundText.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else if (selectedTag != null) {
            // Se uma tag estiver selecionada, busque eventos por tag
            postgresqlAPI.getEventsByTagId(selectedTag.getId(), new EventsCallback() {
                @Override
                public void onSuccess(List<EventoFeed> events) {
                    nothingFoundText.setVisibility(View.INVISIBLE);
                    if (events.isEmpty()) {

                        nothingFoundText.setText("Nenhum evento encontrado com essa tag \uD83D\uDE14");
                        nothingFoundText.setVisibility(View.VISIBLE);
                    }

                    eventList.addAll(events);
                    Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onError(String errorMessage) {
                    Log.e("API", errorMessage);
                    nothingFoundText.setText("Nenhum evento encontrado com essa tag \uD83D\uDE14");
                    nothingFoundText.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
        }  else {

            flaskAPI.getEventsIds(globals.getId(), new EventsIdsCallback() {
                @Override
                public void onSuccess(EventsIdsResponse response) {
                    Log.e("FLASK" , response+"");
                    postgresqlAPI.getEvents( new EventoReadBody(response.getEventos_ids()),new EventsCallback() {
                        @Override
                        public void onSuccess(List<EventoFeed> events) {
                            nothingFoundText.setVisibility(View.INVISIBLE);

                            if (events.isEmpty()) {
                                nothingFoundText.setText("Nenhum evento encontrado \uD83D\uDE14");
                                nothingFoundText.setVisibility(View.VISIBLE);
                            }

                            eventList.addAll(events);
                            Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onError(String errorMessage) {
                            Log.e("API", errorMessage);
                            nothingFoundText.setText("Nenhum evento encontrado \uD83D\uDE14");
                            nothingFoundText.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }

                @Override
                public void onError(String errorMessage) {
                    Log.e("FLASK" , errorMessage);
                }
            });


        }
    }



    private void addTagsInTheList() {

        postgresqlAPI.getTags(new TagsCallback() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(List<Tag> tags) {
                tagList.addAll(tags);
                Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    private void loadEvents() {
        addEventsInTheList(isMyEvents);
        updateTabColors(isMyEvents);
    }

    private void loadFraseSustevel() {

        postgresqlAPI.getFraseSustentavel(1, new FraseSustentavelCallback() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(FraseSustentavel fraseSustentavelResponse) {
                fraseSustentavel = fraseSustentavelResponse.getDsFrase();

                addEventsInTheList(false);
                Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("ERRO", errorMessage);
            }
        });
    }
}
