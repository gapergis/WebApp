import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.net.ssl.SSLHandshakeException;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CheckController {

    public int getURLResponse(int id, URL url) {
        String urlToCall = url.toString();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(urlToCall).build();
        int code;
        try (Response response = client.newCall(request).execute()) {
            code = response.code();
        } catch (SocketException e) {
            code = 991;
        } catch (SocketTimeoutException e) {
//            String error = "java.net.SocketTimeoutException: Connect timed out";
//            if (e.toString().equals(error)){
//                code = 996;
//            }else{
                code = 992;
//            }
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

    public File checkServices(ArrayList<Service> services) throws IOException {
        int green=0,yellow=0,red=0,total=0;
        	Integer code;
            int k = 1;
            for (Service s : services) {
                if (Controller.isUrlValid(s.getUrlEisodou())) {
                    try {
                        URL url = new URL(s.getUrlEisodou());
                        code = getURLResponse(s.getId(), url);
                        s.setCode(code);
                        if (code.equals(200) || code.equals(401) || code.equals(994)) {
                            s.setStatus("green");
                            green++;
                        } else if (code.equals(406) || code.equals(500) || code.equals(990) || code.equals(992) ) {
                            s.setStatus("yellow");
                            yellow++;
                        } else if (code.equals(403) || code.equals(400) || code.equals(404) || code.equals(503) || code.equals(991)  || code.equals(993) || code.equals(995) || code.equals(996) || code.equals(0)) {
                            s.setStatus("red");
                            red++;
                        }
                        total++;
                    } catch (MalformedURLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    s.setStatus("invalid");
                    s.setCode(999);
                }
                if (k % 100 == 0) {
                    System.out.println("Checking " + k + " / " + services.size());
                }
                k++;
            }
            return ServiceWriter2(services, red, total, "services");
    }
    
    public File checkUsefuls(ArrayList<Useful> usefuls) throws IOException {
    	int green=0,yellow=0,red=0,total=0, invalid=0;
        Integer code;
        int k = 1;
        for (Useful u : usefuls) {
            if (Controller.isUrlValid(u.getUrl())) {
                try {
                    URL url = new URL(u.getUrl());
                    code = getURLResponse(u.getId(), url);
                    u.setCode(code);
                    if (code.equals(200) || code.equals(401) || code.equals(994)) {
                        u.setStatus("green");
                    } else if (code.equals(406) || code.equals(500) || code.equals(990) || code.equals(992)) {
                        u.setStatus("yellow");
                    } else if (code.equals(403) || code.equals(404) || code.equals(503) || code.equals(991) || code.equals(993) || code.equals(995) || code.equals(996) || code.equals(000)) {
                        u.setStatus("red");
                    }
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                }
            } else {
                u.setStatus("invalid");
                u.setCode(999);
            }
            if (k % 100 == 0) {
                System.out.println("Checking " + k + " / " + usefuls.size());
            }
            k++;
        }
        for (Useful u : usefuls) {
            if (u.getStatus().equals("green")) {
                green++;
            } else if (u.getStatus().equals("yellow")) {
                yellow++;
            } else if (u.getStatus().equals("red")) {
                red++;
            } else if (u.getStatus().equals("invalid")) {
                invalid++;
            }
            total++;
         
        }
        return UsefulWriter2(usefuls, red, total, "usefulLinks");
    }
    
  public File ServiceWriter2(List<Service> services, int red, int total, String name) throws IOException {
        File temp = new File(CheckController.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String path = temp.getParent();
    //    String decodedPath = URLDecoder.decode(path, StandardCharsets.UTF_8);
        File fileName = new File(path + "/" + name  + "_bad.csv");
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
                    .append('^')
                    .append("Status")
                    .append('\n');

            for (Service service : services) {
                if (service.getStatus().equals("red")) {
                    sb.append('\n')
                            .append(service.getId())
                            .append('^')
                            .append(service.getName())
                            .append('^')
                            .append(service.getUrlEisodou())
                            .append('^')
                            .append(service.getOrgTitle())
                            .append('^')
                            .append(service.getCode())
                            .append('^')
                            .append(service.getStatus());
                }
            }
            wr.write(sb.toString());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return fileName;
    }


    public File UsefulWriter2(List<Useful> usefuls, int red, int total, String name) throws IOException {
        File temp = new File(CheckController.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String path = temp.getParent();
    //    String decodedPath = URLDecoder.decode(path, StandardCharsets.UTF_8);
        File fileName = new File(path + "/" + name  + "_bad.csv");
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
                    .append("app.Service")
                    .append('^')
                    .append("Code")
                    .append('^')
                    .append("Status")
                    .append('\n');
            for (Useful useful : usefuls) {
                if (useful.getStatus().equals("red")) {
                    sb.append('\n')
                            .append(useful.getId())
                            .append('^')
                            .append(useful.getName())
                            .append('^')
                            .append(useful.getUrl())
                            .append('^')
                            .append(useful.getService())
                            .append('^')
                            .append(useful.getCode())
                            .append('^')
                            .append(useful.getStatus());
                }
            }
            wr.write(sb.toString());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
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
