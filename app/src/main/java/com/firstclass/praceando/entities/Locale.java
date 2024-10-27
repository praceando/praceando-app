package com.firstclass.praceando.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Locale implements Parcelable {
    private Long id;
    private String nmLocal;
    private String hrAbertura;
    private String hrFechamento;
    private double nrLat;
    private double nrLong;

    public Locale(Long id, String nmLocal, String hrAbertura, String hrFechamento) {
        this.nmLocal = nmLocal;
        this.id = id;
        this.hrAbertura = hrAbertura;
        this.hrFechamento = hrFechamento;
    }

    public Locale(Long id, String nmLocal, String hrAbertura, String hrFechamento, double nrLat, double nrLong) {
        this.nmLocal = nmLocal;
        this.id = id;
        this.hrAbertura = hrAbertura;
        this.hrFechamento = hrFechamento;
        this.nrLat = nrLat;
        this.nrLong = nrLong;
    }

    protected Locale(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        nmLocal = in.readString();
        hrAbertura = in.readString();
        hrFechamento = in.readString();
    }

    public static final Creator<Locale> CREATOR = new Creator<Locale>() {
        @Override
        public Locale createFromParcel(Parcel in) {
            return new Locale(in);
        }

        @Override
        public Locale[] newArray(int size) {
            return new Locale[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return nmLocal;
    }

    public void setName(String nmLocal) {
        this.nmLocal = nmLocal;
    }

    public String getOpeningTime() {
        return hrAbertura;
    }

    public void setOpeningTime(String hrAbertura) {
        this.hrAbertura = hrAbertura;
    }

    public String getClosingTime() {
        return hrFechamento;
    }

    public void setClosingTime(String hrFechamento) {
        this.hrFechamento = hrFechamento;
    }

    public double getNrLat() {
        return nrLat;
    }

    public void setNrLat(double nrLat) {
        this.nrLat = nrLat;
    }

    public double getNrLong() {
        return nrLong;
    }

    public void setNrLong(double nrLong) {
        this.nrLong = nrLong;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(nmLocal);
        dest.writeString(hrAbertura);
        dest.writeString(hrFechamento);
    }

    @Override
    public String toString() {
        return nmLocal;
    }
}
