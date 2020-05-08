package UsersMicroService.classes;

import UsersMicroService.Main;
import UsersMicroService.Models.Clients;
import UsersMicroService.utils.Common;
import UsersMicroService.utils.PropertyManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public interface fileProcessor {
    /**
     * @throws IllegalStateException
     * @throws IOException
     * @see "https://crunchify.com/how-to-read-json-object-from-file-in-java/"
     */
    static void loadFromJsonFile() throws Exception {
        try {
            String jsonPath = PropertyManager.getPropertyAsString("clientsData.path2Json", "./clientsData/clients.json");

            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(jsonPath));

            // convert JSON file to hashTable
            Map<String, Client> hashTable = Common.getPrettyGson().fromJson(reader,
                    new TypeToken<Map<String, Client>>() {
                    }.getType());

            Clients.setPeopleTable(hashTable);
            // close reader
            reader.close();

        } catch (Exception ex) {
            Main.getLog().error("Error: ", ex);
            throw new Exception(ex.getMessage());
        }
    }

    static void save2JsonFile() throws IOException {
        try {
            String jsonPath = PropertyManager.getPropertyAsString("clientsData.path2Json", "./clientsData/clients.json");

            // create a writer
            Writer writer = new FileWriter(jsonPath);

            // convert map to JSON File
            new Gson().toJson(Clients.getPeopleTable(), writer);

            // close the writer
            writer.close();
        } catch (Exception ex) {
            Main.getLog().error("Error: ", ex);
        }
    }
}

