package com.universtity.orderit.waiter.domain.model;

import java.util.Objects;

public class Table {
    public final int id;
    public final String name;
    public final int state;
    public final String status;
    public final Reserved reserved;

    public Table(int id, String name, int state, String status, Reserved reserved) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.status = status;
        this.reserved = reserved;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getState() {
        return state;
    }

    public String getStatus() {
        return status;
    }

    public Reserved getReserved() {
        return reserved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Table)) return false;
        Table table = (Table) o;
        return id == table.id &&
                state == table.state &&
                Objects.equals(name, table.name) &&
                Objects.equals(status, table.status) &&
                Objects.equals(reserved, table.reserved);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, state, status, reserved);
    }

    public static class Reserved {
        public final int id;
        public final String name;
        public final String time;

        public Reserved(int id, String name, String time) {
            this.id = id;
            this.name = name;
            this.time = time;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getTime() {
            return time;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Reserved)) return false;
            Reserved that = (Reserved) o;
            return id == that.id &&
                    Objects.equals(name, that.name) &&
                    Objects.equals(time, that.time);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, time);
        }
    }
}
