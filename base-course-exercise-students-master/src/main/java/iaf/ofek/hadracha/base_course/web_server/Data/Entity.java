package iaf.ofek.hadracha.base_course.web_server.Data;

import iaf.ofek.hadracha.base_course.web_server.Utilities.ListOperations;

public interface Entity<T extends Entity> extends Cloneable {
    /**
     * ID of the entity. Negative means unset
     */
    int getId();
    void setId(int id);
    T clone();

    class ByIdEqualizer<T extends Entity> implements ListOperations.Equalizer<T>{

        @Override
        public boolean isEqual(T o1, T o2) {
            return o1.getId()==o2.getId();
        }
    }
}
