package com.example.springgpt;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/openai")
public class OpenAIController {

    @PostMapping("/generate")
    public ResponseEntity<String> generateText(@RequestParam(value = "prompt") String prompt) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = System.getenv("OPENAI_API_KEY");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        RestTemplate restTemplate = new RestTemplate();

        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "gpt-3.5-turbo");

        JSONArray messages = new JSONArray();
        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content", prompt);
        messages.put(message);
        requestBody.put("messages", messages);
        System.out.println(requestBody);
        // "{\n" +
        // " \"model\":\"gpt-3.5-turbo\",\n" +
        // " \"messages\":[\n" +
        // " {\"role\": \"user\", \"content\": \"" + prompt + "\"}\n" +
        // " ]\n" +
        // "}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody.toString(), headers);
        ResponseEntity<String> response = restTemplate.postForEntity(
                url,
                requestEntity,
                String.class);

        return response;
    }
}
