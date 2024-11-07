package com.firstclass.praceando.API.Flask;

import java.util.List;

public class EventsIdsResponse {
    private List<Integer> eventos_ids;

    public EventsIdsResponse(List<Integer> eventos_ids) {
        this.eventos_ids = eventos_ids;
    }

    public List<Integer> getEventos_ids() {
        return eventos_ids;
    }

    @Override
    public String toString() {
        return "EventsIdsResponse{" +
                "eventos_ids=" + eventos_ids +
                '}';
    }

}
