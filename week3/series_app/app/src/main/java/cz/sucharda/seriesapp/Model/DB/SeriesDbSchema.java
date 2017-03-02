package cz.sucharda.seriesapp.Model.DB;

/*
 *  Databse schema (table series with columns)
 */

public class SeriesDbSchema {
    public static final class SeriesTable {
        public static final String NAME = "series";
        public static final String DATE_FORMAT = "dd.MM.yyyy";

        public static final class Cols {
            public static final String ID = "_id";
            public static final String NAME = "name";
            public static final String DESCRITPION = "description";
            public static final String DATE_START = "datestart";
            public static final String DATE_FINISH = "datefinish";
            public static final String FINISHED = "finished";
        }
    }
}
