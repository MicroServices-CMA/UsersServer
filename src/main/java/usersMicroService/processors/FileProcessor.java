package usersMicroService.processors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import usersMicroService.Main;
import usersMicroService.structures.Client;
import usersMicroService.models.Clients;
import usersMicroService.utils.Common;
import usersMicroService.utils.PropertyManager;

import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Contains predefined methods used to perform clients load from and save to file/
 * For details about data Processing from/to file using Gson,
 * @see <a href="www.crunchify.com/how-to-read-json-object-from-file-in-java">this article</a>
 *
 * @author Ханк
 * @version 1.1
 */
public interface FileProcessor {
    /**
     * Loads <code>Client</code> objects from text file in JSON format and save into <code>Clients->peopleTable</code>
     *
     * @return void
     */
    static void loadFromJsonFile() throws Exception {
        try {
            System.out.println("Loading Clients from JSON file...");
            String jsonPath = PropertyManager.getPropertyAsString("clientsData.path2Json", "./clientsData/clients.json");

            Reader reader = Files.newBufferedReader(Paths.get(jsonPath));
            Map<String, Client> hashTable = Common.getPrettyGson().fromJson(reader,
                    new TypeToken<HashMap<String, Client>>() {
                    }.getType());

            Clients.setPeopleTable(hashTable);
            reader.close();
            System.out.println("Load complete!");
        } catch (Exception ex) {
            Main.getLog().error("Error: ", ex);
            throw new Exception(ex.getMessage());
        }
    }
    /**
     * Save <code>Client</code> objects from <code>Clients->peopleTable</code> to text file in JSON format.
     *
     * @return void
     */
    static void save2JsonFile() throws Exception {
        try {
            System.out.println("Saving Clients to JSON file...");
            String jsonPath = PropertyManager.getPropertyAsString("clientsData.path2Json", "./clientsData/clients.json");
            // create a writer
            Writer writer = new FileWriter(jsonPath);
            // convert map to JSON File
            new Gson().toJson(Clients.getPeopleTable(), writer);
            // close the writer
            writer.close();
            System.out.println("Finished saving!");
        } catch (Exception ex) {
            Main.getLog().error("Error: ", ex);
            throw new Exception(ex.getMessage());
        }
    }
}

