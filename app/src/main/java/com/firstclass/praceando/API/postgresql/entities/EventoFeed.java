package com.firstclass.praceando.API.postgresql.entities;

import android.os.Parcelable;

import com.firstclass.praceando.entities.Tag;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.os.Parcel;

public class EventoFeed implements Parcelable {
    private long idEvento;
    private String nomeEvento;
    private String nomeLocal;
    private String dataInicio;
    private String horaInicio;
    private String dataFim;
    private String horaFim;
    private List<String> tags;

    public EventoFeed(long id, String nomeEvento, String nomeLocal, String dataInicio, String horaInicio, String dataFim, String horaFim, List<String> tags) {
        this.idEvento = id;
        this.nomeEvento = nomeEvento;
        this.nomeLocal = nomeLocal;
        this.dataInicio = dataInicio;
        this.horaInicio = horaInicio;
        this.dataFim = dataFim;
        this.horaFim = horaFim;
        this.tags = tags;
    }

    public String getFormattedDtInicio() {
        return formatDate(dataInicio);
    }

    public String getFormattedDtFim() {
        return formatDate(dataFim);
    }

    public String getFormattedHrInicio() {
        return formatTime(horaInicio);
    }

    public String getFormattedHrFim() {
        return formatTime(horaFim);
    }

    private String formatDate(String date) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault());
            Date parsedDate = inputFormat.parse(date);
            assert parsedDate != null;
            return outputFormat.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    private String formatTime(String time) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm", java.util.Locale.getDefault());
            Date parsedTime = inputFormat.parse(time);
            assert parsedTime != null;
            return outputFormat.format(parsedTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    protected EventoFeed(Parcel in) {
        idEvento = in.readLong();
        nomeEvento = in.readString();
        nomeLocal = in.readString();
        dataInicio = in.readString();
        horaInicio = in.readString();
        dataFim = in.readString();
        horaFim = in.readString();
        tags = in.createStringArrayList();
    }

    public static final Creator<EventoFeed> CREATOR = new Creator<EventoFeed>() {
        @Override
        public EventoFeed createFromParcel(Parcel in) {
            return new EventoFeed(in);
        }

        @Override
        public EventoFeed[] newArray(int size) {
            return new EventoFeed[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(idEvento);
        dest.writeString(nomeEvento);
        dest.writeString(nomeLocal);
        dest.writeString(dataInicio);
        dest.writeString(horaInicio);
        dest.writeString(dataFim);
        dest.writeString(horaFim);
        dest.writeStringList(tags);
    }

    public long getId() {
        return idEvento;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public String getNomeLocal() {
        return nomeLocal;
    }

    public void setNomeLocal(String nomeLocal) {
        this.nomeLocal = nomeLocal;
    }

    public String getDataInicio() {
        return getFormattedDtInicio();
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getHoraInicio() {
        return getFormattedHrInicio();
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getDataFim() {
        return getFormattedDtFim();
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public String getHoraFim() {
        return getFormattedHrFim();
    }

    public void setHoraFim(String horaFim) {
        this.horaFim = horaFim;
    }

    public List<String> getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return "EventoFeed{" +
                "id=" + idEvento +
                ", nomeEvento='" + nomeEvento + '\'' +
                ", nomeLocal='" + nomeLocal + '\'' +
                ", dataInicio='" + dataInicio + '\'' +
                ", horaInicio='" + horaInicio + '\'' +
                ", dataFim='" + dataFim + '\'' +
                ", horaFim='" + horaFim + '\'' +
                ", tags=" + tags +
                '}';
    }
}
