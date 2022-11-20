import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import javax.net.ssl.SSLHandshakeException;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CheckController {

    public int getURLResponse(URL url) {
        String urlToCall = url.toString();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(urlToCall).build();
        int code;
        try (Response response = client.newCall(request).execute()) {
            code = response.code();
        } catch (SocketException e) {
            code = 991;
        } catch (SocketTimeoutException e) {
            code = 992;
        } catch (SSLHandshakeException e) {
            String error = "javax.net.ssl.SSLHandshakeException: Server chose TLSv1, but that protocol version is not enabled or not supported by the client.";
            String error2 = "javax.net.ssl.SSLHandshakeException: The server selected protocol version TLS10 is not accepted by client preferences [TLS13, TLS12]";
            String error3 = "javax.net.ssl.SSLHandshakeException: PKIX path validation failed: java.security.cert.CertPathValidatorException: validity check failed";
            String error4 = "javax.net.ssl.SSLHandshakeException: Remote host terminated the handshake";
            if (e.toString().equals(error) || e.toString().equals(error2) || e.toString().equals(error3) || e.toString().equals(error4)) {
                code = 995;
            } else {
                code = 994;
            }
        } catch (UnknownHostException e) {
            code = 993;
        } catch (ProtocolException e) {
            code = 990;
        } catch (IOException e) {
            code = 0;
        }
        return code;
    }

    public File checkServices(Data data) throws IOException {
        ArrayList<ServiceAction> servAct = new ArrayList<>();
        Controller controller= new Controller();
        int red = 0, total = 0;
        Integer code;
        int k = 1;
        for (Service s : data.services) {
            String orgName= controller.getOrgName(data, s.getOrganization());
            for (Object ser : s.service_actions) {
                String jsonInString = new Gson().toJson(ser);
                JSONObject obj = new JSONObject(jsonInString);
                String serUrl = obj.getString("url");
                if (Controller.isUrlValid(serUrl)) {
                    try {
                        URL url = new URL(serUrl);
                        code = getURLResponse(url);
                        if (code.equals(403) || code.equals(400) || code.equals(404) || code.equals(503) || code.equals(991) || code.equals(993) || code.equals(995) || code.equals(996) || code.equals(0)) {
                            ServiceAction serAct = new ServiceAction(s.getId(), s.getTitle(), serUrl, orgName, code);
                            servAct.add(serAct);
                            red++;
                        }
                        total++;
                    } catch (MalformedURLException ex) {
                        ex.printStackTrace();
                    }
                }
                if (k % 100 == 0) {
                    System.out.println("Checking " + k);
                }
                k++;
            }
            for (Object ser : s.regions) {
                String jsonInString = new Gson().toJson(ser);
                JSONObject obj = new JSONObject(jsonInString);
                String serUrl = obj.getString("url");
                if (Controller.isUrlValid(serUrl)) {
                    try {
                        URL url = new URL(serUrl);
                        code = getURLResponse(url);
                        if (code.equals(403) || code.equals(400) || code.equals(404) || code.equals(503) || code.equals(991) || code.equals(993) || code.equals(995) || code.equals(996) || code.equals(0)) {
                            ServiceAction serAct = new ServiceAction(s.getId(), s.getTitle(), serUrl, obj.getString("region_title"), code);
                            servAct.add(serAct);
                            red++;
                        }
                        total++;
                    } catch (MalformedURLException ex) {
                        ex.printStackTrace();
                    }
                }
                if (k % 100 == 0) {
                    System.out.println("Checking " + k);
                }
                k++;
            }
        }
        return ServiceWriter2(servAct, red, total);
    }
    public File checkUsefuls(Data data) throws IOException {
        int red = 0, total = 0;
        Integer code;
        int k = 1;
        for (Service s: data.services) {
            for (Object ul : s.service_region_useful_links) {
                String jsonInString = new Gson().toJson(ul);
                JSONObject obj = new JSONObject(jsonInString);
                Useful usef = new Useful();
                usef.setUrl(obj.getString("url"));
                usef.setTitle(obj.getString("title"));
                usef.setService(s.getId());
                data.usefuls.add(usef);
            }
        }
        for (Useful u : data.usefuls) {
            if (Controller.isUrlValid(u.getUrl())) {
                try {
                    URL url = new URL(u.getUrl());
                    code = getURLResponse(url);
                    u.setCode(code);
                    if (code.equals(403) || code.equals(404) || code.equals(503) || code.equals(991) || code.equals(993) || code.equals(995) || code.equals(996) || code.equals(0)) {
                        u.setStatus("red");
                        red++;
                    }
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                }
            } else {
                u.setStatus("red");
                red++;
                u.setCode(999);
            }
            if (k % 100 == 0) {
                System.out.println("Checking " + k + " / " + data.usefuls.size());
            }
            total++;
            k++;
        }
        return UsefulWriter2(data, red, total);
    }

    public File ServiceWriter2(ArrayList<ServiceAction> servAct, int red, int total) throws IOException {
        File temp = new File(CheckController.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String path = temp.getParent();
        File fileName = new File(path + "/" + "Services_bad.csv");
        FileOutputStream file = new FileOutputStream(fileName);
        try (BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(file, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            sb.append("Statistics")
                    .append('^')
                    .append(getDate())
                    .append("\n\n")
                    .append("Total: ")
                    .append(total)
                    .append('\n')
                    .append("Bad: ")
                    .append(red)
                    .append("\n\n");
            sb.append("Id")
                    .append('^')
                    .append("Title")
                    .append('^')
                    .append("Link")
                    .append('^')
                    .append("Foreas")
                    .append('^')
                    .append("Code")
                    .append('\n');
            for (ServiceAction service : servAct) {
                sb.append('\n')
                    .append(service.getId())
                    .append('^')
                    .append(service.getTitle())
                    .append('^')
                    .append(service.getUrl())
                    .append('^')
                    .append(service.getOrgTitle())
                    .append('^')
                    .append(service.getCode());
            }
            wr.write(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileName;
    }


    public File UsefulWriter2(Data data, int red, int total) throws IOException {
        Controller controller = new Controller();
        File temp = new File(CheckController.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String path = temp.getParent();
        File fileName = new File(path + "/" + "UsefulLinks_bad.csv");
        FileOutputStream file = new FileOutputStream(fileName);
        try (BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(file, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            sb.append("Statistics")
                    .append('^')
                    .append(getDate())
                    .append("\n\n")
                    .append("Total: ")
                    .append(total)
                    .append('\n')
                    .append("Bad: ")
                    .append(red)
                    .append("\n\n");
            sb.append("Id")
                    .append('^')
                    .append("Title")
                    .append('^')
                    .append("Link")
                    .append('^')
                    .append("Service")
                    .append('^')
                    .append("Code")
                    .append('\n');
            for (Useful useful : data.usefuls) {
                if (useful.getStatus().equals("red")) {
                    sb.append('\n')
                        .append(useful.getId())
                        .append('^')
                        .append(useful.getTitle())
                        .append('^')
                        .append(useful.getUrl())
                        .append('^')
                        .append(controller.getServName(data, useful.getService()))
                        .append('^')
                        .append(useful.getCode());
                }
            }
            wr.write(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public String getDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public String onlyDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
