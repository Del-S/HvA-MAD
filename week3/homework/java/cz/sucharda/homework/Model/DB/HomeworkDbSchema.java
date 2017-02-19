package cz.sucharda.homework.Model.DB;

/*
 *  Databse schema (table homework with columns)
 */

public class HomeworkDbSchema {
    public static final class HomeworkTable {
        public static final String NAME = "homework";

        public static final class Cols {
            public static final String ID = "_id";
            public static final String NAME = "name";
            public static final String DATE = "date";
            public static final String FINISHED = "finished";
            public static final String SUBJECT = "subject";
        }
    }
}
