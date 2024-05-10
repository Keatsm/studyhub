package backend.studyhub.data;

public class DataAccess {
    private static DataAccess instance;
    private static DataStore dataStore;

    private DataAccess() {
        DataAccess.dataStore = new MemoryDataStore();
    }

    public static synchronized DataAccess getInstance() {
        if (instance == null) {
            instance = new DataAccess();
        }
        return instance;
    }

    public DataStore getDataStore() {
        return dataStore;
    }

    public long getId() {
        return dataStore.getId();
    }
}
