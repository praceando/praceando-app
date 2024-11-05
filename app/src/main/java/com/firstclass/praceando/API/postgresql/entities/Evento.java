package com.firstclass.praceando.API.postgresql.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Evento implements Parcelable {
    private Local local;
    private Anunciante anunciante;
    private String nmEvento;
    private String dsEvento;
    private String dtInicio; // Formato esperado: yyyy-MM-dd
    private String hrInicio; // Formato esperado: HH:mm:ss
    private String dtFim;    // Formato esperado: yyyy-MM-dd
    private String hrFim;    // Formato esperado: HH:mm:ss
    private String urlDocumentacao = "https://www.exemplo.com/documentacao";

    public Evento(long localId, long userId, String nmEvento,
                  String dsEvento, String dtInicio, String hrInicio,
                  String dtFim, String hrFim) {
        this.local = new Local(localId);
        this.anunciante = new Anunciante(userId);
        this.nmEvento = nmEvento;
        this.dsEvento = dsEvento;
        this.dtInicio = formatToISODate(dtInicio);
        this.hrInicio = formatToISOTime(hrInicio);
        this.dtFim = formatToISODate(dtFim);
        this.hrFim = formatToISOTime(hrFim);
    }

    private String formatToISODate(String date) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("d/M/yyyy", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date parsedDate = inputFormat.parse(date);
            return outputFormat.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return date; // Retorna a data original em caso de erro
        }
    }

    // Método para formatar hora diretamente para o formato ISO (HH:mm:ss)
    private String formatToISOTime(String time) {
        if (time.length() == 5) { // Exemplo: "12:00" -> "12:00:00"
            return time + ":00";
        }
        return time; // Presumindo que o input já está no formato correto
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(local, flags);
        dest.writeParcelable(anunciante, flags);
        dest.writeString(nmEvento);
        dest.writeString(dsEvento);
        dest.writeString(dtInicio);
        dest.writeString(hrInicio);
        dest.writeString(dtFim);
        dest.writeString(hrFim);
        dest.writeString(urlDocumentacao);
    }

    protected Evento(Parcel in) {
        local = in.readParcelable(Local.class.getClassLoader());
        anunciante = in.readParcelable(Anunciante.class.getClassLoader());
        nmEvento = in.readString();
        dsEvento = in.readString();
        dtInicio = in.readString();
        hrInicio = in.readString();
        dtFim = in.readString();
        hrFim = in.readString();
        urlDocumentacao = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Evento> CREATOR = new Creator<Evento>() {
        @Override
        public Evento createFromParcel(Parcel in) {
            return new Evento(in);
        }

        @Override
        public Evento[] newArray(int size) {
            return new Evento[size];
        }
    };

    @Override
    public String toString() {
        return "Evento{" +
                "local=" + local +
                ", anunciante=" + anunciante +
                ", nmEvento='" + nmEvento + '\'' +
                ", dsEvento='" + dsEvento + '\'' +
                ", dtInicio='" + dtInicio + '\'' +
                ", hrInicio='" + hrInicio + '\'' +
                ", dtFim='" + dtFim + '\'' +
                ", hrFim='" + hrFim + '\'' +
                ", urlDocumentacao='" + urlDocumentacao + '\'' +
                '}';
    }
}

class Anunciante implements Parcelable {
    private long id;

    public Anunciante(long id) {
        this.id = id;
    }

    protected Anunciante(Parcel in) {
        id = in.readLong();
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Anunciante{id=" + id + '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Anunciante> CREATOR = new Creator<Anunciante>() {
        @Override
        public Anunciante createFromParcel(Parcel in) {
            return new Anunciante(in);
        }

        @Override
        public Anunciante[] newArray(int size) {
            return new Anunciante[size];
        }
    };
}

class Local implements Parcelable {
    private long id;

    public Local(long id) {
        this.id = id;
    }

    protected Local(Parcel in) {
        id = in.readLong();
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Local{id=" + id + '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Local> CREATOR = new Creator<Local>() {
        @Override
        public Local createFromParcel(Parcel in) {
            return new Local(in);
        }

        @Override
        public Local[] newArray(int size) {
            return new Local[size];
        }
    };
}
