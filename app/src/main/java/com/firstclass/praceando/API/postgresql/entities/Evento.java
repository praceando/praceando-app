package com.firstclass.praceando.API.postgresql.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.firstclass.praceando.entities.Locale;
import com.firstclass.praceando.entities.Tag;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Evento implements Parcelable {
    private long id;
    private Locale local;
    private int qtInteresse;
    private String nmEvento;
    private String dsEvento;
    private String dtInicio;
    private String hrInicio;
    private String dtFim;
    private String hrFim;
    private Tag[] tags;

    public Evento(long id, Locale local, int qtInteresse,
                  String nmEvento, String dsEvento, String dtInicio, String hrInicio,
                  String dtFim, String hrFim, Tag[] tags) {
        this.id = id;
        this.local = local;
        this.qtInteresse = qtInteresse;
        this.nmEvento = nmEvento;
        this.dsEvento = dsEvento;
        this.dtInicio = dtInicio;
        this.hrInicio = hrInicio;
        this.dtFim = dtFim;
        this.hrFim = hrFim;
        this.tags = tags;
    }

    // Formatar data no formato dd/MM/yyyy
    public String getFormattedDtInicio() {
        return formatDate(dtInicio);
    }

    public String getFormattedDtFim() {
        return formatDate(dtFim);
    }

    // Formatar hora no formato HH:mm
    public String getFormattedHrInicio() {
        return formatTime(hrInicio);
    }

    public String getFormattedHrFim() {
        return formatTime(hrFim);
    }

    // Função auxiliar para formatar data no formato dd/MM/yyyy
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

    // Função auxiliar para formatar hora no formato HH:mm
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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(qtInteresse);
        dest.writeString(nmEvento);
        dest.writeString(dsEvento);
        dest.writeString(dtInicio);
        dest.writeString(hrInicio);
        dest.writeString(dtFim);
        dest.writeString(hrFim);
        dest.writeTypedArray(tags, flags);
        dest.writeLong(local.getId());
        dest.writeString(local.getName());
        dest.writeString(local.getOpeningTime());
        dest.writeString(local.getClosingTime());
        dest.writeDouble(local.getNrLat());
        dest.writeDouble(local.getNrLong());
    }

    protected Evento(Parcel in) {
        id = in.readLong(); // Read long type
        qtInteresse = in.readInt(); // Read int type
        nmEvento = in.readString(); // Read String type
        dsEvento = in.readString(); // Read String type
        dtInicio = in.readString(); // Read String type
        hrInicio = in.readString(); // Read String type
        dtFim = in.readString(); // Read String type
        hrFim = in.readString(); // Read String type
        tags = in.createTypedArray(Tag.CREATOR); // Read Tag[] array

        // Reconstruct the Locale object from Parcel
        long localId = in.readLong();
        String localName = in.readString();
        String openingTime = in.readString();
        String closingTime = in.readString();
        double nrLat = in.readDouble();
        double nrLong = in.readDouble();
        local = new Locale(localId, localName, openingTime, closingTime, nrLat, nrLong); // Assuming Locale has a constructor like this
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Locale getLocale() {
        return local;
    }

    public void setLocale(Locale local) {
        this.local = local;
    }

    public int getQtInteresse() {
        return qtInteresse;
    }

    public void setQtInteresse(int qtInteresse) {
        this.qtInteresse = qtInteresse;
    }

    public String getNmEvento() {
        return nmEvento;
    }

    public void setNmEvento(String nmEvento) {
        this.nmEvento = nmEvento;
    }

    public String getDsEvento() {
        return dsEvento;
    }

    public void setDsEvento(String dsEvento) {
        this.dsEvento = dsEvento;
    }

    public Tag[] getTags() {
        return tags;
    }

    public void setTags(Tag[] tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", local=" + local +
                ", qtInteresse=" + qtInteresse +
                ", nmEvento='" + nmEvento + '\'' +
                ", dsEvento='" + dsEvento + '\'' +
                ", dtInicio='" + getFormattedDtInicio() + '\'' +
                ", hrInicio='" + getFormattedHrInicio() + '\'' +
                ", dtFim='" + getFormattedDtFim() + '\'' +
                ", hrFim='" + getFormattedHrFim() + '\'' +
                ", tags=" + Arrays.toString(tags) +
                '}';
    }
}
