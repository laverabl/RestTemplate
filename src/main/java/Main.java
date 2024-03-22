import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class Main {
    public static void main(String[] args) {
        String url = "http://94.198.50.185:7081/api/users";
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> allUsersResponse = restTemplate.getForEntity(url, String.class);
        HttpHeaders headers = new HttpHeaders();
        headers.addAll(allUsersResponse.getHeaders());
        String sessionId = headers.getFirst(HttpHeaders.SET_COOKIE);
        System.out.println(sessionId);

        String newUserJson = "{\"id\":3,\"name\":\"James\",\"lastName\":\"Brown\",\"age\":30}";
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.COOKIE, sessionId);
        HttpEntity<String> addUserRequest = new HttpEntity<>(newUserJson, headers);
        ResponseEntity<String> addUserResponse = restTemplate.exchange(url, HttpMethod.POST, addUserRequest, String.class);
        String part1 = addUserResponse.getBody();


        String updateUserJson = "{\"id\":3,\"name\":\"Thomas\",\"lastName\":\"Shelby\",\"age\":30}";
        HttpEntity<String> updateUserRequest = new HttpEntity<>(updateUserJson, headers);
        ResponseEntity<String> updateUserResponse = restTemplate.exchange(url, HttpMethod.PUT, updateUserRequest, String.class);
        String part2 = updateUserResponse.getBody();


        ResponseEntity<String> deleteUserResponse = restTemplate.exchange(url + "/3", HttpMethod.DELETE, new HttpEntity<>(headers), String.class);
        String part3 = deleteUserResponse.getBody();

        System.out.println(part1 + part2 + part3);
    }
}
