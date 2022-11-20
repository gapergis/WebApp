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
                String newOrg = "new" +"^"+ o.getId() + "^" + o.getTitle() + "\n";
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
                String remSer = "removed" + "^" + objServ.get("id") + "^" + objServ.get("name").getAsString() + "\n";
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
                if (c.getId() == objId) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                String newCat = "new" + "^" + c.getId() + "^"+ c.getTitle() + "\n";
                report.append(newCat);
            }
        }

        //Elegxos gia diagrafi katigorias
        for (Object object : cats) {
            JsonObject objServ = (JsonObject) JsonParser.parseString(object.toString());
            boolean found = false;
            for (Category c : cat) {
                if (c.getId() == objServ.get("id").getAsInt()) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                String remCat = "removed" + "^" +  objServ.get("id") + "^"+ objServ.get("name").getAsString() + "\n";
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
                String newGeg = "new" + "^" + g.getId()  + "^" + g.getTitle() + "\n";
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
                String remGeg = "removed" + "^" + objServ.get("id") + "^"+ objServ.get("name").getAsString() + "\n";
                report.append(remGeg);
            }
        }
        return report.toString();
    }

    public String checkService(Data govData, Object jsonData) { //elegxos upiresiwn
        Controller controller = new Controller();
        StringBuilder report = new StringBuilder("Services: \n");

        JSONObject data = (JSONObject) jsonData;
        JSONArray servs = data.getJSONArray("Services");

        //Elegxos gia prosthiki ypiresias
        ArrayList<Integer> addedSer = new ArrayList<>();
        for (Service s : govData.services) {
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
                String refURI = "Αρχική / "+ controller.getCatName(govData.cats,govData.subs, s.getSub_category())+" / "+controller.getSubName(govData.subs, s.getSub_category()) + " / " + s.getTitle();
                String newSer = "new" + "^" + s.getId() + "^" + "=HYPERLINK(\"" + controller.createBread(govData, s.getId()) + "\",\"" + refURI + "\")" + "^" +  controller.getOrgName(govData, s.getOrganization()) + "\n";
                report.append(newSer);
            }
        }

        //Elegxos gia diagrafi ypiresias
        ArrayList<Integer> removedSer = new ArrayList<>();
        for (Object object : servs) {
            JsonObject objServ = (JsonObject) JsonParser.parseString(object.toString());
            boolean found = false;
            for (Service s : govData.services) {
                if (s.getId() == objServ.get("id").getAsInt()) {
                    found = true;
                    break;
                }
            }
            if (!found && !removedSer.contains(objServ.get("id").getAsInt())) {
                removedSer.add(objServ.get("id").getAsInt());
//                String remSer = "removed^ " + objServ.get("id") + "^" + "=HYPERLINK(\"" + objServ.get("url").getAsString() + "\",\"" + objServ.get("refURI").getAsString() + "\")"+ "^" + objServ.get("org").getAsString() + "\n";
                String remSer = "removed"+ "^" + objServ.get("id") + "^" + objServ.get("refURI").getAsString() + "^" + objServ.get("org").getAsString() + "\n";
                report.append(remSer);
            }
        }

        return report.toString();
    }



    public void updateJson(Data govData) throws FileNotFoundException {
        Controller controller = new Controller();
        File temp = new File(CrossCheck.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String path = temp.getParent();
        path = path + "/crossCheck.json";

        JSONArray orgs = new JSONArray();
        for (Organization o : govData.orgs) {
            JSONObject obj = new JSONObject()
                    .put("id", o.getId())
                    .put("name", o.getTitle())
                    .put("slug", o.getSlug());
            orgs.put(obj);
        }

        JSONArray cats = new JSONArray();
        for (Category c : govData.cats) {
            JSONObject obj = new JSONObject()
                    .put("id", c.getId())
                    .put("name", c.getTitle())
                    .put("slug", c.getSlug());
            cats.put(obj);
        }

        JSONArray gegs = new JSONArray();
        for (Subcategory g : govData.subs) {
            JSONObject obj = new JSONObject()
                    .put("id", g.getId())
                    .put("name", g.getTitle())
                    .put("slug", g.getSlug())
                    .put("category", g.getCategory());
            gegs.put(obj);
        }

        JSONArray servs = new JSONArray();
        ArrayList<Integer> unID = new ArrayList<>();
        for (Service s : govData.services) {
            if (!unID.contains(s.getId())) {
                //    System.out.println(s.id);
                JSONObject obj = new JSONObject()
                        .put("id", s.getId())
                        .put("name", s.getTitle())
                        .put ("org", controller.getOrgName(govData, s.getOrganization()))
                        .put("url", controller.createBread(govData, s.getId()))
                        .put("slug", s.getSlug())
                        .put("refURI", "Αρχική / "+ controller.getCatName(govData.cats,govData.subs, s.getSub_category())+" / "+controller.getSubName(govData.subs, s.getSub_category()) + " / " + s.getTitle());
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

    public File checkGov(Data govData) throws FileNotFoundException {

        String report = "Report \n\n";
        System.out.println("Reading json file...");
        Object data = readJson();
        report= report + checkOrg(govData.orgs, data) + "\n";
        report= report + checkCat(govData.cats, data) + "\n";
        report= report + checkSub(govData.subs, data) + "\n";
        report= report + checkService(govData, data);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }
}
