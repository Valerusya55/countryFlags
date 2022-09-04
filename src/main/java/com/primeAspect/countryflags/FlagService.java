package com.primeAspect.countryflags;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
@Service
@AllArgsConstructor
public class FlagService {
    public void getURLFlags(List<String> countryCode, String pathDirectory, String expansion) throws JsonProcessingException {
        WebClient webClient = WebClient.create("https://restcountries.com/v2");
        String listString = String.join(",", countryCode);
        Mono<String> data = webClient
                .get()
                .uri("/alpha?codes=" + listString)
                .retrieve()
                .bodyToMono(String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(data.block());
        List<JsonNode> listExpansion = jsonNode.findValues(expansion);
        List<JsonNode> listNativeName = jsonNode.findValues("nativeName");
        List<JsonNode> listName = new ArrayList<>();
        for (int i = 0; i < listNativeName.size(); i++) {
            if (i % 2 == 0) {
                listName.add(listNativeName.get(i));
            }
        }
        for (JsonNode name : listName) {
            JsonNode expans = listExpansion.get(listName.indexOf(name));
            try (InputStream in = URI.create(expans.asText()).toURL().openStream()) {
                Files.copy(in, Paths.get(pathDirectory + "\\" + name.asText() + "." + expansion));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
