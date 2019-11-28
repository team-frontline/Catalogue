package com.frontline.CatalogueService.logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogMessage {
    private int httpStatus;
    private String httpMethod;
    private String path;
    private String clientIp;
    private String response;
    private String authType;
    private String payload;

    @Override
    public String toString() {
        List<Integer> lengths = new ArrayList<>();
        int length = getWidth();

        if (length == 0) {
            lengths.add(path.length());
            lengths.add(payload.length());
            lengths.add(response.length());
            length = Collections.max(lengths) + 10;
        }
        String str = "\n\033[1;34m" + new String(new char[length]).replace('\0', '=') + "\033[0m\n" +
                "\033[0;32mstatus\033[0m : " + httpStatus +
                " \033[0;32mhttpMethod\033[0m : '" + httpMethod + '\'' +
                " \033[0;32mpath\033[0m : '" + path + '\'' +
                " \033[0;32mclientIp\033[0m : '" + clientIp + '\'' +
                " \033[0;32mauthType\033[0m : '" + authType + '\'' +
                "\n\033[0;32mpayload\033[0m : '" + payload + '\'' +
                "\n\033[0;32mresponse\033[0m : '" + response.replace("error", "\033[1;31merror\033[0m") + '\'' +
                "\n\033[1;34m" + new String(new char[length]).replace('\0', '=') + "\033[0m";
        return str;
    }

    public void setPayload(String payload) {
        this.payload = payload.replace("\n", "").replaceAll("  ", " ");
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String execute(String command) {
        StringBuilder sb = new StringBuilder();
        String[] commands = new String[]{"/bin/bash", "-c", command};
        try {
            Process proc = new ProcessBuilder(commands).start();
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(proc.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(proc.getErrorStream()));

            String s = null;
            while ((s = stdInput.readLine()) != null) {
                sb.append(s);
                sb.append("\n");
            }

            while ((s = stdError.readLine()) != null) {
                sb.append(s);
                sb.append("\n");
            }
        } catch (IOException e) {
            return e.getMessage();
        }
        return sb.toString();
    }

    public int getWidth() {
        String osname = System.getProperty("os.name");

        if (osname.contains("nux") || osname.contains("nix")) {
            System.out.println(execute("tput cols"));
            return 150;
           // return Integer.parseInt(execute("tput cols").replaceAll("\n", "").replaceAll(" ", ""));
        } else if (osname.contains("win")) {
            return Integer.parseInt(execute("echo %CONSOLE_WIDTH%").replaceAll("\n", "").replaceAll(" ", ""));
        } else {
            return 0;
        }
    }
}
