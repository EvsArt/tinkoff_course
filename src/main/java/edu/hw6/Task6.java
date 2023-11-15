package edu.hw6;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task6 {

    private final int checkingPortsCount = 49161;
    private final Duration timeoutDuration = Duration.ofSeconds(10);
    private final String elementTagsRegExp = "</?td>";

    @SuppressWarnings("EmptyBlock")
    public List<PortInfo> getBusyPortsAndProtocols() {

        List<PortInfo> infoList = new ArrayList<>();

        Map<Integer, List<PortInfo>> portsInfoFromWiki = getPortsAndProcessNames();

        for (int port = 0; port <= checkingPortsCount; port++) {

            try (var x = new DatagramSocket(port)) {
            } catch (IOException e) {
                String service = "";
                List<PortInfo> info = portsInfoFromWiki.get(port);
                if (info != null && !info.isEmpty()) {
                    service = info.stream()
                        .filter(it -> it.protocol.equals(Protocol.UDP))
                        .map(PortInfo::service)
                        .findFirst().orElse("");
                }
                infoList.add(new PortInfo(Protocol.UDP, port, service));
            }

            try (var x = new ServerSocket(port)) {
            } catch (IOException e) {
                String service = "";
                List<PortInfo> info = portsInfoFromWiki.get(port);
                if (info != null && !info.isEmpty()) {
                    service = info.stream()
                        .filter(it -> it.protocol.equals(Protocol.TCP))
                        .map(PortInfo::service)
                        .findFirst().orElse("");
                }
                infoList.add(new PortInfo(Protocol.TCP, port, service));
            }

        }

        return infoList;

    }

    protected Map<Integer, List<PortInfo>> getPortsAndProcessNames() {

        Map<Integer, List<PortInfo>> portToInfo = new HashMap<>();

        String page = getPageFromWiki();

        String[] portsInfo = getPortsInfoByBlocks(page);
        // portsInfo[0] is empty string

        for (int i = 0; i < portsInfo.length - 1; i++) {
            List<PortInfo> infos = parseBlock(portsInfo[i + 1]);
            if (infos == null) {
                continue;
            }
            for (PortInfo info : infos) {
                if (!portToInfo.containsKey(info.port)) {
                    portToInfo.put(info.port, new ArrayList<>());
                }
                portToInfo.get(info.port).add(info);
            }
        }

        return portToInfo;
    }

    private String getPageFromWiki() {

        try {
            HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI(
                    "https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D0%BF%D0%BE%D1%80%D1%82%D0%BE%D0%B2_TCP_%D0%B8_UDP"))
                .timeout(timeoutDuration)
                .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();

        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private String[] getPortsInfoByBlocks(String pageHtml) {

        Pattern findPortsInfo = Pattern.compile("^[.\\t\\n\\r\\f\\s\\S\\w\\W]*<tbody><tr>\\n"
            + "<th>Порт/Протокол</th>\\n"
            + "<th>Описание</th>\\n"
            + "<th>Использование\\n"
            + "</th></tr>([.\\t\\n\\r\\f\\s\\S\\w\\W]*)</tbody></table>\\n"
            + "<h2><span[.\\t\\n\\r\\f\\s\\S\\w\\W]*$");
        Matcher portsListMatcher = findPortsInfo.matcher(pageHtml);
        String portsList = "";
        if (portsListMatcher.find()) {
            portsList = portsListMatcher.group(1);
        }

        return portsList.split("<tr");
    }

    private List<PortInfo> parseBlock(String blockHtml) {

        List<PortInfo> res = new ArrayList<>();

        List<Integer> ports = new ArrayList<>();
        List<Protocol> protocols = new ArrayList<>();
        String service;

        List<String> lines = blockHtml.lines().toList();

        String[] portAndProtocol = lines.get(1)
            .replaceAll(elementTagsRegExp, "")
            .replaceAll("<[\\t\\w\\s\\W\\S]*>", "").split("/");
        if (portAndProtocol.length < 2) {
            return null;
        }   // dont't have a protocol or port

        String[] portPart = portAndProtocol[0].split(",");
        for (String portWithoutStrip : portPart) {
            String port = portWithoutStrip.strip();
            if (!port.contains("-") && !port.contains("—")) {
                ports.add(Integer.parseInt(port));
            } else {
                String[] startEndPorts = port.split("[-—]");
                int start = Integer.parseInt(startEndPorts[0]);
                int end = Integer.parseInt(startEndPorts[1]);
                for (int pr = start; pr <= end; pr++) {
                    ports.add(pr);
                }
            }
        }

        String[] curProtocols = portAndProtocol[1].split(",");
        for (String prot : curProtocols) {
            if (prot.equals(Protocol.UDP.name)) {
                protocols.add(Protocol.UDP);
            }
            if (prot.equals(Protocol.TCP.name)) {
                protocols.add(Protocol.TCP);
            }
        }

        service = lines.get(2)
            .replaceAll(elementTagsRegExp, "");

        for (int port : ports) {
            for (Protocol protocol : protocols) {
                res.add(new PortInfo(protocol, port, service));
            }
        }

        return res;

    }

    public record PortInfo(Protocol protocol, int port, String service) {
    }

    enum Protocol {
        TCP("TCP"),
        UDP("UDP");
        final String name;

        Protocol(String name) {
            this.name = name;
        }
    }

}
