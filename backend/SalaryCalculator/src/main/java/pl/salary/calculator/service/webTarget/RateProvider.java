package pl.salary.calculator.service.webTarget;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.salary.calculator.exception.InvalidRateResponseException;
import pl.salary.calculator.model.SupportedCurrencies;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Log
@Service
public class RateProvider {

    private WebTarget webTarget;
    private ObjectMapper objectMapper;

    @Autowired
    public RateProvider(WebTarget webTarget, ObjectMapper objectMapper) {
        this.webTarget = webTarget;
        this.objectMapper = objectMapper;
    }

    public Double getRateForCurrency(SupportedCurrencies currency) {
        log.info("Getting rate for currency " + currency);

        Response response = webTarget
                .path("/" + currency)
                .request()
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .get();

        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new InvalidRateResponseException("Error while getting rate info for currency " + currency);
        }

        return getRateFromJson(response.readEntity(String.class));
    }

    private Double getRateFromJson(String json) {
        try {
            ObjectNode node = objectMapper.readValue(json, ObjectNode.class);
            log.info("Getting rate from node " + node);

            JsonNode rate = node.findPath("mid");
            if (rate.isMissingNode()) {
                throw new InvalidRateResponseException("Error while parsing node " + node);
            } else {
                return rate.asDouble();
            }
        } catch (IOException e) {
            throw new InvalidRateResponseException("Error while parsing json " + json);
        }
    }
}