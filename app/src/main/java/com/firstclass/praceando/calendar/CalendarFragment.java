package com.firstclass.praceando.calendar;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firstclass.praceando.R;
import com.firstclass.praceando.entities.Event;
import com.firstclass.praceando.entities.Tag;
import com.firstclass.praceando.fragments.HeaderFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CalendarFragment extends Fragment {

    TextView dateTxt;
    RecyclerView recyclerView;
    List<Event> eventList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout first
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        // Initialize and commit child fragments
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.calendarFragmentLayout, new CalendarViewFragment());
        fragmentTransaction.add(R.id.headerFragmentLayout, new HeaderFragment());
        fragmentTransaction.commit();

        dateTxt = view.findViewById(R.id.dateText);

        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE d, MMMM", new Locale("pt", "BR"));
        String formattedDate = dateFormat.format(currentDate.getTime());

        dateTxt.setText(formattedDate);

        // Criando e adicionando diferentes eventos com listas de tags
        eventList.add(new Event("9:30h - 17h", "Praça do Samba", "14/07 - 14/07", "Hot Dog Sr. Antonio", "https://s2-oglobo.glbimg.com/DxiOCjddhu1nx9GiDbsAYPh2tKY=/0x0:809x493/1008x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_da025474c0c44edd99332dddb09cabe8/internal_photos/bs/2022/L/d/NfVSGFSTqULzZK7MjveQ/lanche.jpg",
                new Tag[] {
                        new Tag(1L, "Gastronomia"),
                        new Tag(2L, "Comida de Rua"),
                        new Tag(2L, "Comida de Rua"),
                        new Tag(3L, "Popular"),
                        new Tag(3L, "Popular")
                }));

        eventList.add(new Event("10h - 18h", "N. Sra. dos Prazeres", "15/07 - 15/07", "Feira de Artesanato", "https://blog.123milhas.com/wp-content/uploads/2022/08/feira-de-artesanato-ao-ar-livre-redes-outros-objetos-artesanais-conexao123.jpg",
                new Tag[] {
                        new Tag(4L, "Artesanato"),
                        new Tag(5L, "Feira"),
                        new Tag(6L, "Cultura"),
                        new Tag(3L, "Popular")
                }));

        eventList.add(new Event("8h - 12h", "Praia do Futuro", "16/07 - 16/07", "Corrida do Sol", "https://eccobolsas.com.br/wp-content/uploads/2023/05/Quais-sao-os-tipos-de-corrida-de-rua.png",
                new Tag[] {
                        new Tag(7L, "Esporte"),
                        new Tag(8L, "Saúde"),
                        new Tag(9L, "Corrida")
                }));

        eventList.add(new Event("14h - 22h", "Centro Cultural", "17/07 - 17/07", "Mostra de Cinema", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQIe1cF_ZkxAniXv0qqy2XX73u079G2BKXASA&s",
                new Tag[] {
                        new Tag(10L, "Cinema"),
                        new Tag(11L, "Cultura"),
                        new Tag(12L, "Arte")
                }));

        eventList.add(new Event("18h - 23h", "Estádio Municipal", "18/07 - 18/07", "Show de Rock", "https://midias.correio24horas.com.br/2023/04/13/rock-no-parque-da-cidade-em-salvador-1567377.jpg",
                new Tag[] {
                        new Tag(13L, "Música"),
                        new Tag(14L, "Rock"),
                        new Tag(15L, "Entretenimento")
                }));

        eventList.add(new Event("9h - 13h", "Biblioteca Pública", "19/07 - 19/07", "Encontro de Leitores", "https://catracalivre.com.br/wp-content/uploads/2020/04/1028904-02-07-2016-dsc7443-910x544.jpg",
                new Tag[] {
                        new Tag(16L, "Leitura"),
                        new Tag(17L, "Literatura"),
                        new Tag(18L, "Cultura")
                }));

        eventList.add(new Event("20h - 2h", "Centro Histórico", "20/07 - 20/07", "Noite dos Museus", "https://www.blogvambora.com.br/wp-content/uploads/2012/05/39009754_5de61cb610_Hugo.jpg",
                new Tag[] {
                        new Tag(19L, "Museus"),
                        new Tag(20L, "História"),
                        new Tag(21L, "Cultura")
                }));

        recyclerView = view.findViewById(R.id.recyclerViewCalendarEvent);
        CalendarEventItemAdapter calendarEventItemAdaptereventAdapter = new CalendarEventItemAdapter(eventList);
        recyclerView.setAdapter(calendarEventItemAdaptereventAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }

    // Method to update the date TextView from another fragment
    public void updateDateText(String date) {
        if (dateTxt != null) {
            dateTxt.setText(date);
        }
    }
}
