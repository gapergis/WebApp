import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class CrossCheck {

    public Object readJson() throws FileNotFoundException {
        File temp = new File(CrossCheck.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String path = temp.getParent();
        path = path + "\\crossCheck.json";
        return new JSONTokener(new FileReader(path)).nextValue();
    }

    public String checkOrg(ArrayList<Organization> org, Object jsonData) { //elegxos forea
        StringBuilder report = new StringBuilder("Organizations: \n");
        JSONObject data = (JSONObject) jsonData;
        JSONArray orgs = data.getJSONArray("Organizations");

        //Elegxos gia prosthiki forea
        for (Organization o : org) {
            boolean found = false;
            for (Object object : orgs) {
                JsonObject objServ = (JsonObject) JsonParser.parseString(object.toString());
                int objId = objServ.get("id").getAsInt();
                if (o.getId() == objId) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                String newOrg = "new: " + o.getId() + " , " + o.getName() + "\n";
                report.append(newOrg);
            }
        }

        //Elegxos gia diagrafi forea
        for (Object object : orgs) {
            JsonObject objServ = (JsonObject) JsonParser.parseString(object.toString());
            boolean found = false;
            for (Organization o : org) {
                if (o.getId() == objServ.get("id").getAsInt()) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                String remSer = "removed: " + objServ.get("id") + " , " + objServ.get("name").getAsString() + "\n";
                report.append(remSer);
            }
        }
        return report.toString();
    }

    public String checkCat(ArrayList<Category> cat, Object jsonData) { //elegxos katigorias

        StringBuilder report = new StringBuilder("Categories: \n");

        JSONObject data = (JSONObject) jsonData;
        JSONArray cats = data.getJSONArray("Categories");

        //Elegxos gia prosthiki katigorias
        for (Category c : cat) {
            boolean found = false;
            for (Object object : cats) {
                JsonObject objServ = (JsonObject) JsonParser.parseString(object.toString());
                int objId = objServ.get("id").getAsInt();
                if (c.id == objId) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                String newCat = "new" + "^" + c.id + "^" + c.name + "\n";
                report.append(newCat);
            }
        }

        //Elegxos gia diagrafi katigorias
        for (Object object : cats) {
            JsonObject objServ = (JsonObject) JsonParser.parseString(object.toString());
            boolean found = false;
            for (Category c : cat) {
                if (c.id == objServ.get("id").getAsInt()) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                String remCat = "removed: " + "^" + objServ.get("id") + "^" + objServ.get("name").getAsString() + "\n";
                report.append(remCat);
            }
        }
        return report.toString();
    }

    public String checkSub(ArrayList<Subcategory> geg, Object jsonData) { //elegxos gegonos zois

        StringBuilder report = new StringBuilder("Life Events: \n");

        JSONObject data = (JSONObject) jsonData;
        JSONArray gegs = data.getJSONArray("Gegonota");

        //Elegxos gia prosthiki gegonotous
        for (Subcategory g : geg) {
            boolean found = false;
            for (Object object : gegs) {
                JsonObject objServ = (JsonObject) JsonParser.parseString(object.toString());
                int objId = objServ.get("id").getAsInt();
                if (g.getId() == objId) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                String newGeg = "new" + "^" + g.getId() + "^" + g.getName() + "\n";
                report.append(newGeg);
            }
        }

        //Elegxos gia diagrafi gegonotous
        for (Object object : gegs) {
            JsonObject objServ = (JsonObject) JsonParser.parseString(object.toString());
            boolean found = false;
            for (Subcategory g : geg) {
                if (g.getId() == objServ.get("id").getAsInt()) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                String remGeg = "removed" + "^" + objServ.get("id") + "^" + objServ.get("name").getAsString() + "\n";
                report.append(remGeg);
            }
        }
        return report.toString();
    }

    public String checkService(ArrayList<Service> services, Object jsonData) { //elegxos upiresiwn

        StringBuilder report = new StringBuilder("Services: \n");
        JSONObject data = (JSONObject) jsonData;
        JSONArray servs = data.getJSONArray("Services");

        //Elegxos gia prosthiki ypiresias
        ArrayList<Integer> addedSer = new ArrayList<>();
        for (Service s : services) {
            boolean found = false;
            for (Object object : servs) {
                JsonObject objServ = (JsonObject) JsonParser.parseString(object.toString());
                int objId = objServ.get("id").getAsInt();
                if (s.getId() == objId) {
                    found = true;
                    break;
                }
            }
            if (!found && !addedSer.contains(s.getId())) {
                addedSer.add(s.getId());
                String refURI = "Αρχική / " + s.getCategory() + " / " + s.getSubCat() + " / " + s.getName();
                String newSer = "new" + "^" + s.getId() + "^" + "=HYPERLINK(\"" + s.getUrl() + "\",\"" + refURI + "\")" + "^" + s.getOrgTitle() + "\n";
                report.append(newSer);
            }
        }

        //Elegxos gia diagrafi ypiresias
        ArrayList<Integer> removedSer = new ArrayList<>();
        for (Object object : servs) {
            JsonObject objServ = (JsonObject) JsonParser.parseString(object.toString());
            boolean found = false;
            for (Service s : services) {
                if (s.getId() == objServ.get("id").getAsInt()) {
                    found = true;
                    break;
                }
            }
            if (!found && !removedSer.contains(objServ.get("id").getAsInt())) {
                removedSer.add(objServ.get("id").getAsInt());
                String remSer = "removed" + "^" + objServ.get("id") + "^" + objServ.get("refURI").getAsString() + "^" + objServ.get("org").getAsString() + "\n";
                report.append(remSer);
            }
        }

        return report.toString();
    }


    public void updateJson(ArrayList<Organization> org, ArrayList<Category> cat, ArrayList<Subcategory> geg, ArrayList<Service> services) throws FileNotFoundException {

        File temp = new File(CrossCheck.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String path = temp.getParent();
        path = path + "/crossCheck.json";

        JSONArray orgs = new JSONArray();
        for (Organization o : org) {
            JSONObject obj = new JSONObject()
                    .put("id", o.getId())
                    .put("name", o.getName())
                    .put("slug", o.getSlug());
            orgs.put(obj);
        }

        JSONArray cats = new JSONArray();
        for (Category c : cat) {
            JSONObject obj = new JSONObject()
                    .put("id", c.id)
                    .put("name", c.name)
                    .put("slug", c.slug);
            cats.put(obj);
        }

        JSONArray gegs = new JSONArray();
        for (Subcategory g : geg) {
            JSONObject obj = new JSONObject()
                    .put("id", g.getId())
                    .put("name", g.getName())
                    .put("slug", g.getSlug())
                    .put("category", g.getCategory());
            gegs.put(obj);
        }


        JSONArray servs = new JSONArray();
        ArrayList<Integer> unID = new ArrayList<>();
        for (Service s : services) {
            if (!unID.contains(s.getId())) {
                //    System.out.println(s.id);
                JSONObject obj = new JSONObject()
                        .put("id", s.getId())
                        .put("name", s.getName())
                        .put("org", s.getOrgTitle())
                        .put("url", s.getUrl())
                        .put("slug", s.getSlug())
                        .put("refURI", "Αρχική / " + s.getCategory() + " / " + s.getSubCat() + " / " + s.getName());
                servs.put(obj);
                unID.add(s.getId());
            }
        }

        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            String jsonString = new JSONObject()
                    .put("Services", servs)
                    .put("Gegonota", gegs)
                    .put("Categories", cats)
                    .put("Organizations", orgs)
                    .toString();
            out.write(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File checkGov(ArrayList<Organization> org, ArrayList<Category> cat, ArrayList<Subcategory> geg, ArrayList<Service> services) throws FileNotFoundException {

        String report = "Report \n\n";
        System.out.println("Reading json file...");
        Object data = readJson();
        report = report + checkOrg(org, data) + "\n";
        report = report + checkCat(cat, data) + "\n";
        report = report + checkSub(geg, data) + "\n";
        report = report + checkService(services, data);
        System.out.println("Creating report...");
        return createReport(report);
    }

    public File createReport(String report) throws FileNotFoundException {
        File temp = new File(CrossCheck.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String path = temp.getParent();
        File fileName = new File(path + "/crossCheck.csv");
        System.out.println(fileName.getParent());
        FileOutputStream file = new FileOutputStream(fileName);
        try (BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(file, StandardCharsets.UTF_8))) {
            wr.write(report);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return fileName;
    }
}
